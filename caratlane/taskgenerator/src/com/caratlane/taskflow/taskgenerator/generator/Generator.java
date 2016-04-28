/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import java.util.logging.Level;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorRuntimeException;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.TOMORROW;
import com.caratlane.taskflow.taskgenerator.generator.crud.TaskDbReseter;
import com.caratlane.taskflow.taskgenerator.generator.crud.DataDbSerializer;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers._D_TOMORROW;
import com.caratlane.taskflow.taskgenerator.generator.rules.ProjectUpdator;
import com.caratlane.taskflow.taskgenerator.generator.rules.TaskAllocator;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 *
 * @author wdmtraining
 */
public class Generator {

    public Generator() {

    }

    /**
     *
     * @throws TaskGeneratorException
     */
    public void reset() throws TaskGeneratorException {

        TaskDbReseter.resetTasks();
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    public void generate() throws TaskGeneratorException {

        // ===========================================================================
        LogManager.getLogger().log(Level.FINE, "Starting generation ...");
        // ===========================================================================

        this.initialize();

        try {
            final LocalDateTime allocation_from = TOMORROW;
            final TaskAllocator taskAllocator = new TaskAllocator(allocation_from);
            final ProjectUpdator projectUpdator = new ProjectUpdator();

            for (int priority = 10; priority >= 1; priority--) {

                // ---------------------------------------------------------------------
                final String msg = "Projects with priority : " + priority;
                LogManager.getLogger().log(Level.FINE, msg);
                // ---------------------------------------------------------------------

                final LinkedList<ProjectData> projects
                        = Projects.getInstance().getSortedProjectsData(priority);

                // for each project ...
                projects.stream().forEach((ProjectData projectData) -> {

                    // ---------------------------------------------------------------------
                    final String msg2 = "Open project : " + projectData.getProject().toString();
                    LogManager.getLogger().log(Level.FINE, msg2);
                    // ---------------------------------------------------------------------

                    try {
                        taskAllocator.allocate(projectData);

                    } catch (TaskGeneratorException ex) {
                        // ---------------------------------------------------------------------
                        LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
                        // ---------------------------------------------------------------------
                        throw new TaskGeneratorRuntimeException(ex);
                    }

                });
            }

            // update projects before serializing 
            projectUpdator.updateProjects();

            // serialize projects and tasks in the database.
            DataDbSerializer.serialize();

        } catch (TaskGeneratorRuntimeException ex) {

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
            // ---------------------------------------------------------------------    
            throw new TaskGeneratorException(ex);

        } finally {

            // ===========================================================================
            LogManager.getLogger().log(Level.FINE, "... generation terminated !");
            // ===========================================================================
        }
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    private void initialize() throws TaskGeneratorException {

        // initialize entities need to geenrate allocations.
        // modules have to be initialzed in this order.
        // ---------------------------------------------
        // non working days to come 
        // ---------------------------------------------
        NonWorkingDays.initialize(_D_TOMORROW);

        // ---------------------------------------------
        // skills
        // ---------------------------------------------
        Skills.initialize();

        // ---------------------------------------------
        // all open projects
        // ---------------------------------------------
        Projects.initialize();

        // ---------------------------------------------
        // all employees
        // ---------------------------------------------
        Employees.initialize(_D_TOMORROW);

        // ---------------------------------------------
        // All tasks from all open projects
        // ---------------------------------------------
        Tasks.initialize();

    }

}
