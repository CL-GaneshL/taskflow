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
import static com.caratlane.taskflow.taskgenerator.generator.rules.Constants.PRODUCT_BASED;
import com.caratlane.taskflow.taskgenerator.generator.rules.ProjectUpdator;
import com.caratlane.taskflow.taskgenerator.generator.rules.TaskAllocator;
import com.caratlane.taskflow.taskgenerator.generator.rules.TaskAllocatorFactory;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 *
 * @author wdmtraining
 */
public class Generator {

    // be passed in as a parameter in the future ?
    private static final String GENERATION_TYPE = PRODUCT_BASED;

    // the re-allocation starts from tomorrow 0am.
    private final LocalDateTime ALLOCATION_START_DATE = TOMORROW;

    // generation check mode
    private final int CHECK_MODE = 0;

    // generation serialize mode
    private final int SERIALIZE_MODE = 1;

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
    public void check() throws TaskGeneratorException {
        this.generate(CHECK_MODE);
    }

    public void generate() throws TaskGeneratorException {
        this.generate(SERIALIZE_MODE);
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    private void generate(final int mode) throws TaskGeneratorException {

        this.initialize();

        // ===========================================================================
        LogManager.getLogger().log(Level.FINE, "=========================================");
        LogManager.getLogger().log(Level.FINE, "Generator starting generation ...");
        LogManager.getLogger().log(Level.FINE, "=========================================");
        // ===========================================================================

        try {

            final TaskAllocator taskAllocator
                    = (new TaskAllocatorFactory(GENERATION_TYPE))
                    .getInstance(ALLOCATION_START_DATE);

            final ProjectUpdator projectUpdator = new ProjectUpdator();

            for (int priority = 10; priority >= 1; priority--) {

                // ---------------------------------------------------------------------
                final String msg = " + Projects with priority : " + priority;
                LogManager.getLogger().log(Level.FINE, ".......................................");
                LogManager.getLogger().log(Level.FINE, msg);
                LogManager.getLogger().log(Level.FINE, ".......................................");
                // ---------------------------------------------------------------------

                final LinkedList<ProjectData> projects
                        = Projects.getInstance().getSortedProjectsData(priority);

                // for each project ...
                projects.stream().forEach((ProjectData projectData) -> {

                    // ---------------------------------------------------------------------
                    final String msg2 = " + project : " + projectData.getProject().toString();
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

            if (mode == SERIALIZE_MODE) {
                // serialize projects and tasks in the database.
                DataDbSerializer.serialize();
            }

        } catch (TaskGeneratorRuntimeException ex) {

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
            // ---------------------------------------------------------------------    
            throw new TaskGeneratorException(ex);

        } finally {

            // ===========================================================================
            LogManager.getLogger().log(Level.FINE, "=========================================");
            LogManager.getLogger().log(Level.FINE, "... Generator has terminated generation !");
            LogManager.getLogger().log(Level.FINE, "=========================================");
            // ===========================================================================
        }
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    private void initialize() throws TaskGeneratorException {

        // ===========================================================================
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        LogManager.getLogger().log(Level.FINE, "Initializing Generator ...");
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        // ===========================================================================

        // ---------------------------------------------
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

        // ===========================================================================
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        LogManager.getLogger().log(Level.FINE, "... Generator initialization terminated.");
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        // ===========================================================================

    }

}
