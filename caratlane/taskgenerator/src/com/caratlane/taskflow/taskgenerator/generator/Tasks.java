/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.crud.ProjectsDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.crud.TaskAllocationsDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

        tasks = new LinkedList<>();

        // extract tasks for each projects
        final LinkedList<ProjectData> projects
                = Projects.getInstance().getProjectsData();

        for (ProjectData projectData : projects) {

            final Integer project_id = projectData.getProject().getId();
            final List<Task> extractedTasks = ProjectsDbExtractor.getProjectTasks(project_id);

            extractedTasks.stream().forEach((Task task) -> {

                // add the task to the global list of task
                tasks.add(task);

                // also add the task to the project list of task
                projectData.addTask(task);
            });
        }

        // create relationships between tasks and allocations
        // notice that tasks are conceptually related to projects, whereas
        // allocations are related to employee. Employees may have allocations 
        // that are not related to any tasks owned by projects, however, 
        // these allocations must be taken into account in order to find the
        // "next available" start date when creating a new allocation.
        final LinkedList<EmployeeData> employees
                = Employees.getInstance().getEmployeeData();

        for (EmployeeData employeeData : employees) {

            final Integer employee_id = employeeData.getEmployee().getId();

            final List<TaskAllocation> taskAllocations
                    = TaskAllocationsDbExtractor.getEmployeeTaskAllocations(employee_id);

            taskAllocations.stream()
                    .filter((allocation) -> {

                        // only interrested in completed or partially completed allocations. 
                        // The "un-touched" allocations will be re-allocated.
                        final boolean isCompleted = allocation.isCompleted();
                        final Integer completion = allocation.getCompletion();
                        final Integer nbProductsCompleted = allocation.getNbProductsCompleted();

                        final boolean toKeep = isCompleted
                        || !completion.equals(0)
                        || !nbProductsCompleted.equals(0);

                        return toKeep;
                    })
                    .forEach((allocation) -> {

                        // add the allocation to its owner
                        employeeData.addTaskAllocation(allocation);

                        final Integer task_id = allocation.getTaskId();

                        // link to its task if 
                        final Optional<Task> value = tasks.stream()
                        .filter(task -> task.getId().equals(task_id)).findFirst();

                        value.ifPresent(task -> task.addTaskAllocation(allocation));
                    });
        }

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
