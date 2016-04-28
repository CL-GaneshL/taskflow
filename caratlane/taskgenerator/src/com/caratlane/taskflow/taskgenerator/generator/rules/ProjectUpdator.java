/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.generator.ProjectData;
import com.caratlane.taskflow.taskgenerator.generator.Projects;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;

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

        final LinkedList<ProjectData> projects = Projects.getInstance().getProjectsData();

        projects.stream().forEach((ProjectData projectData) -> {

            final LinkedList<Task> tasks = projectData.getTasks();

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
                projectData.getProject().setEndDate(latestDataTime);
            }

        });

    }
}
