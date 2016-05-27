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
import static com.caratlane.taskflow.taskgenerator.generator.rules.Constants.PRODUCT_BASED;
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
class TaskAllocatorProductBased implements TaskAllocator {

    final private LocalDateTime allocation_from;
    final static int TWO_DECIMALS = 2;

    /**
     *
     * @param allocation_from
     */
    public TaskAllocatorProductBased(final LocalDateTime allocation_from) {
        this.allocation_from = allocation_from;
    }

    /**
     *
     * @param projectData
     * @throws TaskGeneratorException
     */
    @Override
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

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "  - re-allocate Task = {0}", task);
        // --------------------------------------------------------------------- 

        // only completed or partially completed allocations 
        // were kept when initializing Tasks.
        final Integer skill_id = task.getSkillId();
        final Skill skill = Skills.getInstance().getSkill(skill_id);
        final Integer skill_duration = skill.getDuration();

        final Integer project_id = task.getProjectId();

        // get all allocations for that task
        final LinkedList<TaskAllocation> taskAllocations
                = task.getTaskAllocations();

        int total_duration = nb_products * skill_duration;

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "        o project id = {0}, ", project_id);
        LogManager.getLogger().log(Level.FINE, "        o total nb products = {0}, ", nb_products);
        LogManager.getLogger().log(Level.FINE, "        o skill duration = {0}, ", skill_duration);
        LogManager.getLogger().log(Level.FINE, "        o total duration = {0}, ", total_duration);
        // ---------------------------------------------------------------------  

        // remains to allocate
        // the remaining total duration to allocate
        int already_allocated_nb_products = taskAllocations.stream()
                .mapToInt(TaskAllocation::getNbProductsPlanned)
                .sum();

        int nb_products_remaining = nb_products - already_allocated_nb_products;

        // ---------------------------------------------------------------------             
        LogManager.getLogger().log(Level.FINE, "          . nb products remaining = {0}, ", nb_products_remaining);
        // ---------------------------------------------------------------------             

        do { // re-allocate 
            final TaskAllocation allocatedTaskAllocation
                    = this.allocateTaskAllocation(task, nb_products_remaining);

            final Integer allocated_duration = allocatedTaskAllocation.getDuration();
            final Integer allocated_nb_products = allocatedTaskAllocation.getNbProductsPlanned();

            nb_products_remaining -= allocated_nb_products;

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.FINE, "          . allocated_nb_products = {0}, ", allocated_nb_products);
            LogManager.getLogger().log(Level.FINE, "          . allocated duration = {0}, ", allocated_duration);
            LogManager.getLogger().log(Level.FINE, "          . nb products remaining = {0}, ", nb_products_remaining);
            // ---------------------------------------------------------------------             

        } while (nb_products_remaining > 0);

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

        // total quantity of labor in minutes
        int total_duration = nb_products * skill_duration;

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "  - allocate Task = {0}", task);
        LogManager.getLogger().log(Level.FINE, "        o project id = {0}, ", project_id);
        LogManager.getLogger().log(Level.FINE, "        o total nb products = {0}, ", nb_products);
        LogManager.getLogger().log(Level.FINE, "        o skill duration = {0}, ", skill_duration);
        LogManager.getLogger().log(Level.FINE, "        o total duration = {0}, ", total_duration);
        // ---------------------------------------------------------------------         

        // add the newly created task to its project
        projectData.addTask(task);

        // also add the task to the global list of tasks
        Tasks.getInstance().addTask(task);

        // remains to allocate
        int nb_products_remaining = nb_products;

        // ---------------------------------------------------------------------             
        LogManager.getLogger().log(Level.FINE, "          . nb products remaining = {0}, ", nb_products_remaining);
        // ---------------------------------------------------------------------             

        do {
            final TaskAllocation allocatedTaskAllocation
                    = this.allocateTaskAllocation(task, nb_products_remaining);

            final Integer allocated_duration = allocatedTaskAllocation.getDuration();
            final Integer allocated_nb_products = allocatedTaskAllocation.getNbProductsPlanned();

            nb_products_remaining -= allocated_nb_products;

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.FINE, "          . allocated_nb_products = {0}, ", allocated_nb_products);
            LogManager.getLogger().log(Level.FINE, "          . allocated duration = {0}, ", allocated_duration);
            LogManager.getLogger().log(Level.FINE, "          . nb products remaining = {0}, ", nb_products_remaining);
            // ---------------------------------------------------------------------             

        } while (nb_products_remaining > 0);
    }

    /**
     *
     * @param project_id
     * @param skill_id
     * @param nb_products
     * @throws TaskGeneratorException
     */
    private TaskAllocation allocateTaskAllocation(
            final Task task,
            final Integer nb_products
    ) throws TaskGeneratorException {

        Integer allocated;
        TaskAllocation allocatedTaskAllocation;

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "  = calculating allocation parameters");
        // ---------------------------------------------------------------------

        final Integer task_id = task.getId();
        final Integer skill_id = task.getSkillId();

        // skill duration : tim needed to complete one product
        final Skill skill = Skills.getInstance().getSkill(skill_id);
        final Integer skill_duration = skill.getDuration();

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "  = skill duration : {0}", skill_duration);
        // ---------------------------------------------------------------------

        // need to give the time for at least one product to be completed
        final int reserve = skill_duration;

        final EmployeeSkillSuitability employeeSkillSuitability
                = (new EmployeeSkillSuitabilityFactory(PRODUCT_BASED)).getInstance();

        final EmployeeData employeeData
                = employeeSkillSuitability.findMostSuitableEmployeeWithReserve(
                        skill_id, this.allocation_from, reserve);

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "  = most suitable employee : {0}", employeeData);
        // ---------------------------------------------------------------------        

        final Integer employee_id = employeeData.getEmployee().getId();

        final EmployeeAvailability employeeAvailability
                = (new EmployeeAvailabilityFactory(PRODUCT_BASED)).getInstance();

        LocalDateTime av_start_date = employeeAvailability
                .getNextAvailabilityWithReserve(employeeData, this.allocation_from, reserve);

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "  = employee s availability : {0}", av_start_date.toString());
        // ---------------------------------------------------------------------            

        // minutes available in that shift
        final LocalDateTime endShift = getDateAt8am(av_start_date);
        final Integer available = getLocalDateTimeDiff(av_start_date, endShift);

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "  = mins available before end of shift : {0}", available);
        // ---------------------------------------------------------------------          

        // how many products can be achieved during that time ?
        // corrected available time
        final Integer correctedAvailable
                = EmployeeProductivity.getDownscaledDuration(employeeData, available);

        // the reserve time gave us enough time for at least one product 
        // to be completed. Integer division, no decimals.
        final int max_nb_products_planned = (int) (correctedAvailable / skill_duration);

        // make sure we do not allocate more products than needed
        final int nb_products_planned = max_nb_products_planned < nb_products
                ? max_nb_products_planned : nb_products;

        final int allocation_duration = nb_products_planned * skill_duration;

        // ---------------------------------------------------------------------
        final Double productivity = employeeData.getEmployee().getProductivity();
        LogManager.getLogger().log(Level.FINE, "  = employee s productivity : {0}", productivity);
        LogManager.getLogger().log(Level.FINE, "  = corrected available in mins : {0}", correctedAvailable);
        LogManager.getLogger().log(Level.FINE, "  = nb products planned : {0}", nb_products_planned);
        LogManager.getLogger().log(Level.FINE, "  = allocated duration : {0}", allocation_duration);
        // ---------------------------------------------------------------------   

        // task allocated for this employee for 
        //  - allocation_duration amount of time
        //  - nb_products_planned nb of products planned
        allocatedTaskAllocation
                = TaskAllocation.createNewTaskAllocation(
                        employee_id,
                        task_id,
                        av_start_date,
                        nb_products_planned,
                        allocation_duration);

        this.addTaskAllocation(employeeData, task, allocatedTaskAllocation);

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "    + allocation = {0}, ", allocatedTaskAllocation);
        // ---------------------------------------------------------------------  

        return allocatedTaskAllocation;

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

    @Override
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
    private double round(double value, int places) {

        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
