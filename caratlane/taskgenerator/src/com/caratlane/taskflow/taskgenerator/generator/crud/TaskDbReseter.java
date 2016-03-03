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
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class TaskDbReseter {

    public static final String DELETE_TASK_ALLOCATION_TABLE_SQL
            = "DELETE FROM `task_allocations` WHERE `task_allocations`.`id` > 0";

    public static final String DELETE_TASKS_TABLE_SQL
            = "DELETE FROM `tasks` WHERE `tasks`.`id` > 0";

    public static void resetTasks() throws TaskGeneratorException {

        DBConnection con = null;
        DBTransaction transaction = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();
            transaction = con.getTransaction();

            transaction.begin();

            // -------------------------------------------------
            LogManager.getLogger().log(Level.FINE, "Delete task_allocations : {0}", DELETE_TASK_ALLOCATION_TABLE_SQL);
            // -------------------------------------------------

            transaction.executeNativeSQL(DELETE_TASK_ALLOCATION_TABLE_SQL);

            // -------------------------------------------------
            LogManager.getLogger().log(Level.FINE, "Delete tasks : {0}", DELETE_TASK_ALLOCATION_TABLE_SQL);
            // -------------------------------------------------

            transaction.executeNativeSQL(DELETE_TASKS_TABLE_SQL);

            transaction.commit();

        } catch (DBException ex) {

            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (DBException ignore) {
                }
            }

            // ---------------------------------------------------------------------
            final String msg = "Failed to reset tasks.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(msg, ex);

        } finally {

            if (con != null) {
                con.close();
            }
        }

    }
}
