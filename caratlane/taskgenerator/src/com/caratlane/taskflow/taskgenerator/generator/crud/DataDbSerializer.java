/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.crud;

import com.caratlane.taskflow.taskgenerator.db.DBConnection;
import com.caratlane.taskflow.taskgenerator.db.DBException;
import com.caratlane.taskflow.taskgenerator.db.DBManager;
import com.caratlane.taskflow.taskgenerator.db.DBTransaction;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorRuntimeException;
import com.caratlane.taskflow.taskgenerator.generator.ProjectData;
import com.caratlane.taskflow.taskgenerator.generator.Projects;
import com.caratlane.taskflow.taskgenerator.generator.Tasks;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.getQueryName;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbProject;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.DELETE_TASK_ALLOCATIONS_SUFFIX;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTask;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTaskAllocation;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import static java.lang.Math.toIntExact;
import java.util.LinkedList;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class DataDbSerializer {

    private static DBConnection connection = null;
    private static DBTransaction transaction = null;

    /**
     * Top level function in charge of begin and commit a transaction. In case
     * of failure while serializing tasks and allocations, a rollback is call so
     * no damage is done to the current data.
     *
     * @throws TaskGeneratorException
     */
//    public static void serialize(final List<ProjectData> projects) throws TaskGeneratorException {
    public static void serialize() throws TaskGeneratorException {

        try {
            // start a new db transaction
            connection = DBManager.getDatabaseInstance().getConnection().open();
            transaction = connection.getTransaction();

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.FINE, "============================================");
            LogManager.getLogger().log(Level.FINE, "Starting serialization ...");
            LogManager.getLogger().log(Level.FINE, "============================================");
            // ---------------------------------------------------------------------

            transaction.begin();
            serializeTasks();
            updateProjects();
            transaction.commit();

        } catch (DBException | TaskGeneratorRuntimeException ex) {

            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (DBException ignore) {
                }
            }

            // ---------------------------------------------------------------------
            final String msg = "Failed to serialize Tasks in the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(msg, ex);

        } finally {

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.FINE, "============================================");
            LogManager.getLogger().log(Level.FINE, "End of serialization.");
            LogManager.getLogger().log(Level.FINE, "============================================");
            // ---------------------------------------------------------------------

            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     *
     * @param transaction
     * @throws DBException
     * @throws TaskGeneratorRuntimeException
     */
    private static void serializeTasks() throws TaskGeneratorRuntimeException {

        final LinkedList<Task> tasks = Tasks.getInstance().getTasks();

        tasks.stream().forEach(task -> {
            try {
                final boolean isCreated = task.isCreated();
                final boolean isModified = task.isModified();

                if (isCreated) {
                    // the task does not exist in the database 
                    // we create the task and its allocations
                    final Integer task_id = persistTask(task);

                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.FINE, "--------------------------------------");
                    LogManager.getLogger().log(Level.FINE, " + New Task = {0}", task);
                    LogManager.getLogger().log(Level.FINE, "--------------------------------------");
                    // ---------------------------------------------------------------------

                    persistAllocations(task, task_id);
                } else if (isModified) {

                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.FINE, "--------------------------------------");
                    LogManager.getLogger().log(Level.FINE, " * Modified Task = {0}", task);
                    LogManager.getLogger().log(Level.FINE, "--------------------------------------");
                    // ---------------------------------------------------------------------

                    // the task does already exist in the database.
                    // we only create the allocations. We also have to 
                    // delete previously existing allocations.                     
                    persistAllocations(task);
                }

            } catch (DBException ex) {
                throw new TaskGeneratorRuntimeException(ex);
            }
        });

    }

    /**
     *
     * @param task
     * @param transaction
     * @throws TaskGeneratorException
     */
    private static Integer persistTask(final Task task) throws DBException {

        final DbTask dbTask = task.getDbTask();
        long task_id = transaction.persistGetId(dbTask);
        final Integer int_task_id = toIntExact(task_id);
        return int_task_id;
    }

    /**
     *
     * @param task
     * @param transaction
     * @throws TaskGeneratorException
     */
    private static void persistAllocations(
            final Task task,
            final Integer task_id
    ) throws DBException {

        final LinkedList<TaskAllocation> allocations = task.getTaskAllocations();
        for (TaskAllocation allocation : allocations) {

            // serialize the task allocation entity
            final DbTaskAllocation dbTaskAllocation
                    = allocation.getDbTaskAllocation();

            dbTaskAllocation.setTask_id(task_id);

            // -------------------------------------------------
            LogManager.getLogger().log(Level.FINE, " - Persisting Allocation : {0}", dbTaskAllocation);
            // -------------------------------------------------

            transaction.persist(dbTaskAllocation);

        }

    }

    /**
     *
     * @param task
     * @throws DBException
     */
    private static void persistAllocations(final Task task) throws DBException {

        final Integer task_id = task.getId();
        final String queryName = getQueryName(DbTaskAllocation.class, DELETE_TASK_ALLOCATIONS_SUFFIX);
        transaction.delete(queryName, "task_id", task_id);

        final LinkedList<TaskAllocation> allocations = task.getTaskAllocations();
        for (TaskAllocation allocation : allocations) {

            // serialize the task allocation entity
            final DbTaskAllocation dbTaskAllocation
                    = allocation.getDbTaskAllocation();

            dbTaskAllocation.setTask_id(task_id);

            // -------------------------------------------------
            LogManager.getLogger().log(Level.FINE, " - Persiting Allocation : {0}", dbTaskAllocation);
            // -------------------------------------------------

            transaction.persist(dbTaskAllocation);
        }

    }

    /**
     *
     * @throws TaskGeneratorRuntimeException
     */
    private static void updateProjects() throws TaskGeneratorRuntimeException {

        final LinkedList<ProjectData> projects = Projects.getInstance().getProjectsData();

        projects.stream().forEach(projectData -> {

            final Project project = projectData.getProject();

            try {
                final DbProject dbProject = project.getDbProject();

                // -------------------------------------------------
                LogManager.getLogger().log(Level.FINE, " - Persisting Project : {0}", dbProject);
                // -------------------------------------------------

                transaction.update(dbProject);

            } catch (DBException ex) {
                throw new TaskGeneratorRuntimeException(ex);
            }
        });
    }

}
