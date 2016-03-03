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
import com.caratlane.taskflow.taskgenerator.generator.ProjectData;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTask;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTaskAllocation;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import static java.lang.Math.toIntExact;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class TasksDbSerializer {

    public static void serialize(final List<ProjectData> projects) throws TaskGeneratorException {

        DBConnection connection = null;
        DBTransaction transaction = null;

        try {
            // start a new db transaction
            connection = DBManager.getDatabaseInstance().getConnection().open();
            transaction = connection.getTransaction();
            transaction.begin();

            for (ProjectData project : projects) {

                LinkedList<Task> tasks = project.getTasks();

                for (Task task : tasks) {
                    TasksDbSerializer.insertTask(task, transaction);
                }
            }

            transaction.commit();

        } catch (DBException ex) {

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

            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     *
     * @param task
     * @param transaction
     * @throws TaskGeneratorException
     */
    private static void insertTask(
            final Task task,
            final DBTransaction transaction
    ) throws TaskGeneratorException {

        try {

            // start a new db transaction
            // serialize the task entity
            final DbTask dbTask = task.getDbTask();

            // -------------------------------------------------
            LogManager.getLogger().log(Level.FINE, "===> {0}", dbTask);
            // -------------------------------------------------

            final long task_id = transaction.persistGetId(dbTask);
            final Integer int_task_id = toIntExact(task_id);

            final LinkedList<TaskAllocation> allocations = task.getTaskAllocations();
            for (TaskAllocation allocation : allocations) {

                // serialize the task allocation entity
                final DbTaskAllocation dbTaskAllocation
                        = allocation.getDbTaskAllocation();

                dbTaskAllocation.setTask_id(int_task_id);

                // -------------------------------------------------
                LogManager.getLogger().log(Level.FINE, "===> {0}", dbTaskAllocation);
                // -------------------------------------------------

                transaction.persist(dbTaskAllocation);
            }

        } catch (DBException ex) {

            // ---------------------------------------------------------------------
            final String msg = "Failed to insert Tasks in the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(msg, ex);

        }
    }

}
