/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorRuntimeException;
import com.caratlane.taskflow.taskgenerator.generator.EmployeeData;
import com.caratlane.taskflow.taskgenerator.generator.ProjectData;
import com.caratlane.taskflow.taskgenerator.generator.Skills;
import com.caratlane.taskflow.taskgenerator.generator.Tasks;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import static java.lang.Math.toIntExact;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.time.LocalTime.MIDNIGHT;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class TaskAllocator {

    final private LocalDateTime allocation_from;
    final static int TWO_DECIMALS = 2;

    /**
     *
     * @param allocation_from
     */
    public TaskAllocator(final LocalDateTime allocation_from) {
        this.allocation_from = allocation_from;
    }

    /**
     *
     * @param projectData
     * @throws TaskGeneratorException
     */
    public void allocate(final ProjectData projectData) throws TaskGeneratorException {

        final List<Integer> projectSkills = projectData.getSkills();
        projectSkills.stream().forEach((Integer skill_id) -> {

            try {
                // find out if a task is already existing for that skill
                final LinkedList<Task> tasks = projectData.getTasks();
                final Optional<Task> taskOptional = tasks.stream()
                        .filter(t -> {
                            return t.getSkillId().equals(skill_id);
                        }).findFirst();

                final Project project = projectData.getProject();
                final Integer nb_products = project.getNbProducts();

                if (taskOptional.isPresent()) {
                    // if the task already exists, we only have to reallocate
                    // the allocations that are not completed or partially completed.
                    final Task task = taskOptional.get();
                    this.reallocateTask(task, nb_products);
                } else {
                    // the task has to be created as well as its allocations.
                    this.allocateTask(projectData, skill_id, nb_products);
                }

            } catch (TaskGeneratorException ex) {
                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
                // ---------------------------------------------------------------------
                throw new TaskGeneratorRuntimeException(ex);
            }
        });

    }

    /**
     *
     * @param task
     * @throws TaskGeneratorException
     */
    private void reallocateTask(
            final Task task,
            final Integer nb_products
    ) throws TaskGeneratorException {

        // only completed or partially completed allocations 
        // were kept when initializing Tasks.
        final Integer skill_id = task.getSkillId();
        final Skill skill = Skills.getInstance().getSkill(skill_id);
        final Integer skill_duration = skill.getDuration();

        // get all allocations for that task
        final LinkedList<TaskAllocation> taskAllocations
                = task.getTaskAllocations();

        // the remaining total duration to allocate
        int sum = taskAllocations.stream()
                .mapToInt(TaskAllocation::getDuration)
                .sum();

        // total quantity of labor in minutes
        int remainder = nb_products * skill_duration - sum;

        // re-allocate 
        do {
            final int allocated
                    = this.allocateTaskAllocation(
                            task,
                            remainder);

            remainder = remainder - allocated;

        } while (remainder > 0);

    }

    /**
     *
     * @param projectData
     * @param skill_id
     * @param nb_products
     * @throws TaskGeneratorException
     */
    private void allocateTask(
            final ProjectData projectData,
            final Integer skill_id,
            final Integer nb_products
    ) throws TaskGeneratorException {

        final Skill skill = Skills.getInstance().getSkill(skill_id);
        final Integer skill_duration = skill.getDuration();

        final Project project = projectData.getProject();
        final Integer project_id = project.getId();

        // create the task and allocate it to its project.
        final Task task = Task.newTask(skill_id, project_id);

        // add the newly created task to its project
        projectData.addTask(task);

        // also add the task to the global list of tasks
        Tasks.getInstance().addTask(task);

        // total quantity of labor in minutes
        int remainder = nb_products * skill_duration;

        do {
            final int allocated
                    = this.allocateTaskAllocation(
                            task,
                            remainder);

            remainder = remainder - allocated;

        } while (remainder > 0);
    }

    /**
     *
     * @param project_id
     * @param skill_id
     * @param nb_products
     * @throws TaskGeneratorException
     */
    private Integer allocateTaskAllocation(
            final Task task,
            final Integer duration
    ) throws TaskGeneratorException {

        Integer allocated;
        TaskAllocation taskAllocation;

        final Integer skill_id = task.getSkillId();

        final EmployeeData employeeData
                = EmployeeSkillSuitability.findMostSuitableEmployeeWithSkill(
                        skill_id, this.allocation_from);

        LocalDateTime av_start_date
                = EmployeeAvailability.getNextAvailability(employeeData, this.allocation_from);

        // minutes available in that shift
        final LocalDateTime endShift = getDateAt8am(av_start_date);
        final Integer available = getLocalDateTimeDiff(av_start_date, endShift);

        final Integer employee_id = employeeData.getEmployee().getId();

        // corrected duration; depends on the employee productivity
        final Integer correctedDuration
                = EmployeeProductivity.getCorrectedDuration(employeeData, duration);

        // skill duration in order to calculate the nb planned products        
        final Skill skill = Skills.getInstance().getSkill(skill_id);
        final Integer skill_duration = skill.getDuration();

        // task id
        final Integer task_id = task.getId();

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE,
                " duration = {0}, corrected Duration => {1}",
                new Object[]{duration, correctedDuration});
        // ---------------------------------------------------------------------

        if (correctedDuration <= available) {

            // actual allocated time irrespectively of the employees' productivity.
            allocated = duration;

            // nb planned products, rounded to 2 decimals
            double nb_products_planned = this.round(
                    (double) duration / skill_duration, TWO_DECIMALS);

            // task allocated for this employee for a correctedDuration amount of time
            taskAllocation = TaskAllocation.createNewTaskAllocation(
                    employee_id,
                    task_id,
                    av_start_date,
                    nb_products_planned,
                    correctedDuration);

            this.addTaskAllocation(employeeData, task, taskAllocation);

            // ---------------------------------------------------------------------
            final Double productivity = employeeData.getEmployee().getProductivity();
            LogManager.getLogger().log(Level.FINE,
                    "**** Allocation : duration = {0}, skill_duration = {1}, nb_products_planned => {2}, productivity = {3}",
                    new Object[]{duration, skill_duration, nb_products_planned, productivity});
            // ---------------------------------------------------------------------

        } else {

            // actual allocated time irrespectively of the employees' productivity.
            final Integer downscaledDuration
                    = EmployeeProductivity.getDownscaledDuration(employeeData, available);

            allocated = downscaledDuration;

            // nb planned products, rounded to 2 decimals
            double nb_products_planned = this.round(
                    (double) available / skill_duration, TWO_DECIMALS);

            // create an allocation whith the whole available time for that shift
            // we allocate the remainder time available in that shift.
            taskAllocation = TaskAllocation.createNewTaskAllocation(
                    employee_id,
                    task_id,
                    av_start_date,
                    nb_products_planned,
                    available);

            this.addTaskAllocation(employeeData, task, taskAllocation);

            // ---------------------------------------------------------------------
            final Double productivity = employeeData.getEmployee().getProductivity();
            LogManager.getLogger().log(Level.FINE,
                    "**** Allocation : available = {0}, skill_duration = {1}, nb_products_planned => {2}, productivity = {3}",
                    new Object[]{available, skill_duration, nb_products_planned, productivity});
            // ---------------------------------------------------------------------
        }

        return allocated;

    }

    /**
     * Add a new shift allocation. The start date of the newly added shift must
     * be greater than the start date of the last shift entered.
     *
     * @param taskAllocation
     * @throws TaskGeneratorException
     */
    private void addTaskAllocation(
            final EmployeeData employeeData,
            final Task task,
            final TaskAllocation taskAllocation
    )
            throws TaskGeneratorException {

        final TaskAllocation last = employeeData.getLastTaskAllocation();

        if (last != null) {

            final LocalDateTime last_start_date = last.getStartDate();
            final Integer last_duration = last.getDuration();
            final LocalDateTime last_end_date = last_start_date.plusMinutes(last_duration);

            final LocalDateTime new_start_date = taskAllocation.getStartDate();

            // a new allocation can only start at the end of the last 
            // allocation or days after, if holidays, non working days ...
            if (new_start_date.isBefore(last_end_date)) {

                final String msg = MessageFormat.format("last = {0}, add = {1}", last, taskAllocation);
                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.SEVERE, msg);
                // ---------------------------------------------------------------------
                throw new TaskGeneratorException(msg);
            }
        }

        // add the newly created allocation to its task
        Task.addNewAllocation(task, taskAllocation);

        // add the allocation to its employee
        employeeData.addTaskAllocation(taskAllocation);
    }

    /**
     *
     * @param date
     * @return
     */
    private static LocalDateTime getDateAt8am(final LocalDateTime date) {

        final LocalDate ld_date = date.toLocalDate();
        final LocalDateTime atMidnight = LocalDateTime.of(ld_date, MIDNIGHT);
        final LocalDateTime at8am = atMidnight.plusHours(8);

        return at8am;
    }

    /**
     *
     * @param fromDateTime
     * @param toDateTime
     * @return
     */
    private static Integer getLocalDateTimeDiff(final LocalDateTime fromDateTime, final LocalDateTime toDateTime) {

        final LocalDateTime diffDateTime = LocalDateTime.from(fromDateTime);
        final Integer diff = toIntExact(diffDateTime.until(toDateTime, ChronoUnit.MINUTES));

        return diff;
    }

    /**
     * For testing purpose
     *
     * @param test
     * @param projectData
     * @param skill_id
     * @param nb_products
     * @throws TaskGeneratorException
     */
    public void allocate(
            final Boolean test,
            final ProjectData projectData,
            final Integer skill_id,
            final Integer nb_products
    ) throws TaskGeneratorException {

        this.allocateTask(projectData, skill_id, nb_products);
    }

    /**
     *
     * @param value
     * @param places
     * @return
     */
    public double round(double value, int places) {

        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
