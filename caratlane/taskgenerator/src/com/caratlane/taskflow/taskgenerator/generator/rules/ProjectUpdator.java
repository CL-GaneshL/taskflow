/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.generator.ProjectData;
import com.caratlane.taskflow.taskgenerator.generator.Projects;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class ProjectUpdator {

    public ProjectUpdator() {
    }

    /**
     * Update the project's end date.
     */
    public void updateProjects() {

        // ---------------------------------------------------------------------             
        LogManager.getLogger().log(Level.FINE, "    Updating Projects ...");
        // ---------------------------------------------------------------------

        final LinkedList<ProjectData> projects
                = Projects.getInstance().getProjectsData();

        projects.stream().forEach((ProjectData projectData) -> {

            final LinkedList<Task> tasks = projectData.getTasks();
            final Project project = projectData.getProject();

            // merge all allocations for that project
            final LinkedList<TaskAllocation> allAllocations = new LinkedList<>();
            tasks.stream().forEach(task -> {
                final LinkedList<TaskAllocation> allocations = task.getTaskAllocations();
                allAllocations.addAll(allocations);
            });

            // find the latest allocation
            final Optional<LocalDateTime> latest = allAllocations.stream()
                    .map(TaskAllocation::getStartDate)
                    .max((d1, d2) -> {
                        return d1.compareTo(d2);
                    });

            if (latest.isPresent()) {

                // latest allocation
                final LocalDateTime latestDataTime = latest.get();

                // update the project's end date               
                project.setEndDate(latestDataTime);

                // ---------------------------------------------------------------------             
                LogManager.getLogger().log(Level.FINE, "        -> Update Project = {0}, ", project);
                LogManager.getLogger().log(Level.FINE, "          - project s end date = {0}, ", latestDataTime);
                // ---------------------------------------------------------------------

            } else {

                // ---------------------------------------------------------------------             
                LogManager.getLogger().log(Level.FINE, "        -> Update Project = {0}, ", project);
                LogManager.getLogger().log(Level.FINE, "          - no end date !!!!");
                // ---------------------------------------------------------------------         
            }

        });

    }
}
