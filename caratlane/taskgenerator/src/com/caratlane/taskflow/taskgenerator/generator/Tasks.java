/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.crud.ProjectsDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.crud.TaskAllocationsDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class Tasks {

    private static LinkedList<Task> tasks = null;

    private Tasks() {
    }

    public static Tasks getInstance() {
        return TasksHolder.INSTANCE;
    }

    private static class TasksHolder {

        private static final Tasks INSTANCE = new Tasks();
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    public static void initialize() throws TaskGeneratorException {

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        LogManager.getLogger().log(Level.FINE, "Initializing tasks ...");
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        // ---------------------------------------------------------------------

        tasks = new LinkedList<>();

        // extract tasks for each projects
        final LinkedList<ProjectData> projects
                = Projects.getInstance().getProjectsData();

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "  -> Existing Tasks per Project.");
        // --------------------------------------------------------------------- 

        // ---------------------------------------------------------------------
        if (LogManager.isTestLoggable()) {
            LogManager.logTestMsg(Level.INFO, "Existing Tasks per Project : ");
        } // ---------------------------------------------------------------------  

        for (ProjectData projectData : projects) {

            final Project project = projectData.getProject();

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.FINE, "  {0}", project);
            // --------------------------------------------------------------------- 

            // ---------------------------------------------------------------------
            if (LogManager.isTestLoggable()) {
                LogManager.logTestMsg(Level.INFO, "  {0}", project.toString());
            } // ---------------------------------------------------------------------              

            final Integer project_id = project.getId();
            final List<Task> extractedTasks = ProjectsDbExtractor.getProjectTasks(project_id);

            if (!extractedTasks.isEmpty()) {
                extractedTasks.stream().forEach((Task task) -> {

                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.FINE, "    {0}", task);
                    // --------------------------------------------------------------------- 

                    // ---------------------------------------------------------------------
                    if (LogManager.isTestLoggable()) {
                        LogManager.logTestMsg(Level.INFO, "    {0}", task.toString());
                    } // --------------------------------------------------------------------- 

                    // add the task to the global list of task
                    tasks.add(task);

                    // also add the task to the project list of task
                    projectData.addTask(task);
                });
            } else {
                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.FINE, "   - Project has no tasks.");
                // --------------------------------------------------------------------- 

                // ---------------------------------------------------------------------
                if (LogManager.isTestLoggable()) {
                    LogManager.logTestMsg(Level.INFO, "   - Project has no tasks.");
                } // ---------------------------------------------------------------------                 
            }
        }

        // create relationships between tasks and allocations
        // notice that tasks are conceptually related to projects, whereas
        // allocations are related to employee. Employees may have allocations 
        // that are not related to any tasks owned by projects, however, 
        // these allocations must be taken into account in order to find the
        // "next available" start date when creating a new allocation.
        final LinkedList<EmployeeData> employees
                = Employees.getInstance().getEmployeeData();

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "  -> Existing Allocations per Employee.");
        // --------------------------------------------------------------------- 

        // ---------------------------------------------------------------------
        if (LogManager.isTestLoggable()) {
            LogManager.logTestMsg(Level.INFO, "Allocations per Employee : ");
        } // ---------------------------------------------------------------------          

        for (EmployeeData employeeData : employees) {

            final Employee employee = employeeData.getEmployee();

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.FINE, "  {0}", employee);
            // --------------------------------------------------------------------- 

            // ---------------------------------------------------------------------
            if (LogManager.isTestLoggable()) {
                LogManager.logTestMsg(Level.INFO, "  {0}", employee.toString());
            } // ---------------------------------------------------------------------             

            final Integer employee_id = employee.getId();

            final TaskAllocations taskAllocations
                    = TaskAllocationsDbExtractor.getEmployeeTaskAllocations(employee_id);

            if (taskAllocations.getAllocations().isEmpty()) {
                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.FINE, "   Employee has no Allocations.");
                // --------------------------------------------------------------------- 

                // ---------------------------------------------------------------------
                if (LogManager.isTestLoggable()) {
                    LogManager.logTestMsg(Level.INFO, "   Employee has no Allocations.");
                } // ---------------------------------------------------------------------                 
            }

            taskAllocations.getAllocations().stream().forEach((allocation) -> {

                // check if this allocation belongs to a task already retrieved from the db
                if (!isOwnedByTask(allocation)) {

                    // reject this allocation as it is not part of an open project
                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.FINE, "    Reject allocation = {0}", allocation);
                    // --------------------------------------------------------------------- 

                    // ---------------------------------------------------------------------
//                    if (LogManager.isTestLoggable()) {
//                        LogManager.logTestMsg(Level.WARNING, "    x Reject allocation = {0}", allocation.toString());
//                    } // ---------------------------------------------------------------------                      

                } else {

                    // only interrested in completed or partially completed allocations. 
                    // Pristine allocations ( not work done) will be re-allocated.
                    final boolean isCompleted = allocation.isCompleted();
                    final Integer completion = allocation.getCompletion();
                    final Integer nbProductsCompleted = allocation.getNbProductsCompleted();

                    final boolean isPristine = (!isCompleted)
                            && (completion.equals(0))
                            && (nbProductsCompleted.equals(0));

                    final Task ownerTask = getOwnerTask(allocation);

                    if (isPristine) {

                        // this allocation will be re-allocated to Employees
                        // for them to complete the work. Set the Task modified
                        // to make sure the task is correctly serialized.
                        ownerTask.setModified(true);

                        // ---------------------------------------------------------------------
                        LogManager.getLogger().log(Level.FINE, "    Pristine allocation = {0}", allocation);
                        // --------------------------------------------------------------------- 

                        // ---------------------------------------------------------------------
                        if (LogManager.isTestLoggable()) {
                            LogManager.logTestMsg(Level.INFO, "    Pristine allocation = {0}", allocation.toString());
                        } // --------------------------------------------------------------------- 

                    } else {

                        // this allocation will be re-allocated to any Employee
                        // add the allocation to its employee
                        employeeData.addTaskAllocation(allocation);

                        // attach the allocation to its task
                        ownerTask.addTaskAllocation(allocation);

                        // ---------------------------------------------------------------------
                        LogManager.getLogger().log(Level.FINE, "    Non pristine allocation = {0}", allocation);
                        // --------------------------------------------------------------------- 

                        // ---------------------------------------------------------------------
                        if (LogManager.isTestLoggable()) {
                            LogManager.logTestMsg(Level.INFO, "    Non pristine allocation = {0}", allocation.toString());
                        } // --------------------------------------------------------------------- 

                    }
                }
            });
        }

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, " Checking Tasks integrity ...");
        // --------------------------------------------------------------------- 

        // ---------------------------------------------------------------------
        if (LogManager.isTestLoggable()) {
            LogManager.logTestMsg(Level.INFO, " Checking Tasks integrity.");
        } // --------------------------------------------------------------------- 

        // assert Tasks against Projects, Employees and Skills
        tasks.stream().forEach((Task task) -> {

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.FINE, " {0}", task);
            // ---------------------------------------------------------------------

            // ---------------------------------------------------------------------
            if (LogManager.isTestLoggable()) {
                LogManager.logTestMsg(Level.FINE, " {0}", task.toString());
            } // --------------------------------------------------------------------- 

            final Integer project_id = task.getProjectId();
            final Integer skill_id = task.getSkillId();

            // check the skill id against the database
            if (skillExists(skill_id)) {

                // check the skill against the project
                if (!projectHasSkill(project_id, skill_id)) {

                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.FINE,
                            "  Skill not attached to this Project , project id = {0}, skill id = {1}.",
                            new Object[]{project_id, skill_id});
                    // --------------------------------------------------------------------- 

                    // ---------------------------------------------------------------------
                    if (LogManager.isTestLoggable()) {
                        LogManager.logTestMsg(Level.SEVERE,
                                "  Skill not attached to this Project , project id = {0}, skill id = {1}.",
                                project_id.toString(), skill_id.toString());
                    } // --------------------------------------------------------------------- 
                }

            } else {

                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.FINE,
                        "  Skill does not exist in database, skill id = {0}", skill_id);
                // --------------------------------------------------------------------- 

                // ---------------------------------------------------------------------
                if (LogManager.isTestLoggable()) {
                    LogManager.logTestMsg(Level.SEVERE,
                            "  Skill does not exist in database, skill id = {0}", skill_id.toString());
                } // --------------------------------------------------------------------- 
            }

        });

        // assert Allocations against Tasks, Employees and Skills
        tasks.stream().forEach((Task task) -> {

            final Integer skill_id = task.getSkillId();
            final LinkedList<TaskAllocation> allocations = task.getTaskAllocations();

            allocations.forEach((TaskAllocation allocation) -> {
                final Integer employee_id = allocation.getEmployeeId();

                if (!employeeHasSkill(employee_id, skill_id)) {
                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.FINE, " - allocation = {0}", allocation);
                    LogManager.getLogger().log(Level.FINE,
                            "  Employee does not have this Skill , employee id = {0}, skill id = {1}.",
                            new Object[]{employee_id, skill_id});
                    // --------------------------------------------------------------------- 

                    // ---------------------------------------------------------------------
                    if (LogManager.isTestLoggable()) {
                        LogManager.logTestMsg(Level.SEVERE,
                                "  Employee does not have this Skill , employee id = {0}, skill id = {1}.",
                                employee_id.toString(), skill_id.toString());
                    } // ---------------------------------------------------------------------                     
                }
            });
        });

    }

    /**
     *
     * @param task
     */
    public void addTask(final Task task) {
        tasks.add(task);
    }

    /**
     *
     * @return
     */
    public LinkedList<Task> getTasks() {
        return tasks;
    }

    /**
     *
     * @param employee_id
     * @param skill_id
     * @return
     */
    private static boolean employeeHasSkill(final Integer employee_id, final Integer skill_id) {

        final LinkedList<EmployeeData> employeeData
                = Employees.getInstance().getEmployeeData();

        final Predicate<EmployeeData> filter
                = (EmployeeData ed) -> ed.getEmployee().getId().equals(employee_id);

        final Optional<EmployeeData> opt = employeeData.stream().filter(filter).findFirst();

        return opt.get().hasSkill(skill_id);
    }

    /**
     *
     * @param project_id
     * @param skill_id
     * @return
     */
    private static boolean projectHasSkill(final Integer project_id, final Integer skill_id) {

        final LinkedList<ProjectData> projectData = Projects.getInstance().getProjectsData();

        final Predicate<ProjectData> filter = (ProjectData pd) -> {
            final Predicate<Integer> filter1 = (Integer sid) -> sid.equals(skill_id);
            final Optional<Integer> opt = pd.getSkills().stream().filter(filter1).findFirst();
            return opt.isPresent();
        };

        final Optional<ProjectData> opt = projectData.stream().filter(filter).findFirst();

        return opt.isPresent();
    }

    /**
     *
     * @param skill_id
     * @return
     */
    private static boolean skillExists(final Integer skill_id) {
        return Skills.getInstance().getSkill(skill_id) != null;
    }

    /**
     *
     * @param allocation
     * @return
     */
    private static boolean isOwnedByTask(final TaskAllocation allocation) {

        final Integer alloc_task_id = allocation.getTaskId();
        final Predicate<Task> filter = (Task t) -> t.getId().equals(alloc_task_id);
        final Optional<Task> opt = tasks.stream().filter(filter).findFirst();

        return opt.isPresent();
    }

    /**
     *
     * @param allocation
     * @return
     */
    private static Task getOwnerTask(final TaskAllocation allocation) {

        Task task = null;

        final Integer alloc_task_id = allocation.getTaskId();
        final Predicate<Task> filter = (Task t) -> t.getId().equals(alloc_task_id);
        final Optional<Task> opt = tasks.stream().filter(filter).findFirst();

        if (opt.isPresent()) {
            task = opt.get();
        }

        return task;
    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @throws TaskGeneratorException
     */
    public static void initialize(boolean test) throws TaskGeneratorException {

        tasks = new LinkedList<>();
    }

    /**
     *
     * @param test
     */
    public void clearTask(boolean test) {
        tasks.clear();
    }

}
