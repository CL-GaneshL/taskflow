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

                final EmployeeData employeeData
                        = EmployeeSkillSuitability.findMostSuitableEmployeeWithSkill(
                                skill_id, this.allocation_from);

                final Project project = projectData.getProject();
                final Integer nb_products = project.getNbProducts();

                if (taskOptional.isPresent()) {
                    // if the task already exists, we only need to reallocate
                    // the allocations that are not completed or partially completed.
                    final Task task = taskOptional.get();
                    this.reallocateTask(task, employeeData, nb_products);
                } else {
                    // the task has to be created as well as its allocations.
                    this.allocateNewTask(projectData, employeeData, skill_id, nb_products);
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
            final EmployeeData employeeData,
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
                    = this.allocateNewTaskAllocation(task, employeeData, remainder);

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
    private void allocateNewTask(
            final ProjectData projectData,
            final EmployeeData employeeData,
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
                    = this.allocateNewTaskAllocation(task, employeeData, remainder);

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
    private Integer allocateNewTaskAllocation(
            final Task task,
            final EmployeeData employeeData,
            final Integer duration
    ) throws TaskGeneratorException {

        Integer allocated;
        TaskAllocation taskAllocation;

        LocalDateTime av_start_date
                = EmployeeAvailability.getNextAvailability(employeeData, this.allocation_from);

        // how many minutes available in that shift
        final LocalDateTime endShift = getDateAt8am(av_start_date);
        final Integer available = getLocalDateTimeDiff(av_start_date, endShift);

        final Integer employee_id = employeeData.getEmployee().getId();

        // corrected duration depends of the employee productivity
        final Integer correctedDuration
                = EmployeeProductivity.getCorrectedDuration(employeeData, duration);

        // ---------------------------------------------------------------------
        // LogManager.getLogger().log(Level.FINE, " duration = {0}, corrected => {1}", new Object[]{duration, correctedDuration});
        // ---------------------------------------------------------------------
        if (correctedDuration <= available) {

            // the task is allocated for this employee for a correctedDuration amount of time
            taskAllocation
                    = TaskAllocation.createNewTaskAllocation(employee_id, av_start_date, correctedDuration);

            addTaskAllocation(employeeData, task, taskAllocation);

            // the actual allocated time was the duration (before correction)
            // that will be substract this.allocation_from the task's totalDuration,
            // irrespectively of the employees' productivity.
            allocated = duration;
        } else {
            // create an allocation whith the whole available time for that shift
            // we allocate the remainder time available in that shift.
            taskAllocation
                    = TaskAllocation.createNewTaskAllocation(employee_id, av_start_date, available);

            addTaskAllocation(employeeData, task, taskAllocation);

            // however it has to be down scaled in order to take into accont
            // the employee's productivity.
            final Integer downscaledDuration
                    = EmployeeProductivity.getDownscaledDuration(employeeData, available);

            // ---------------------------------------------------------------------
            // LogManager.getLogger().log(Level.FINE, " available = {0}, downscaled => {1}", new Object[]{available, downscaledDuration});
            // ---------------------------------------------------------------------
            allocated = downscaledDuration;
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
    private static void addTaskAllocation(
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
     * For testing purpose
     *
     * @param test
     * @param projectData
     * @param employeeData
     * @param skill_id
     * @param nb_products
     * @throws TaskGeneratorException
     */
    public void allocate(
            final Boolean test,
            final ProjectData projectData,
            final EmployeeData employeeData,
            final Integer skill_id,
            final Integer nb_products
    ) throws TaskGeneratorException {

        this.allocateNewTask(projectData, employeeData, skill_id, nb_products);
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

    private static Integer getLocalDateTimeDiff(final LocalDateTime fromDateTime, final LocalDateTime toDateTime) {

        final LocalDateTime diffDateTime = LocalDateTime.from(fromDateTime);
        final Integer diff = toIntExact(diffDateTime.until(toDateTime, ChronoUnit.MINUTES));

        return diff;
    }
}
