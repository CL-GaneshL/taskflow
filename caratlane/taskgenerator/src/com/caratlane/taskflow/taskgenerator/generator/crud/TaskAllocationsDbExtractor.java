/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.crud;

import com.caratlane.taskflow.taskgenerator.db.DBConnection;
import com.caratlane.taskflow.taskgenerator.db.DBException;
import com.caratlane.taskflow.taskgenerator.db.DBManager;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.getQueryName;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.FIND_EMPLOYEE_TASK_ALLOCATIONS_SUFFIX;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTaskAllocation;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class TaskAllocationsDbExtractor {

    public static List<TaskAllocation> getEmployeeTaskAllocations(
            final Integer task_id,
            final Integer employee_id
    ) throws TaskGeneratorException {

        final List<TaskAllocation> taskAllocations = new LinkedList<>();

        final String queryName
                = getQueryName(DbTaskAllocation.class, FIND_EMPLOYEE_TASK_ALLOCATIONS_SUFFIX);

        final List<DbTaskAllocation> dbTasks;
        DBConnection con = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();

            // query the database
            dbTasks = con.query(
                    queryName,
                    DbTaskAllocation.class,
                    "task_id", task_id,
                    "employee_id", employee_id
            );

            // create a Task fom a DbTask
            final Function<DbTaskAllocation, TaskAllocation> mapper
                    = (DbTaskAllocation t) -> new TaskAllocation(t);

            // build the list of Tasks to be returned
            final Consumer<TaskAllocation> action = (TaskAllocation t) -> {
                taskAllocations.add(t);
            };

            dbTasks.stream().map(mapper).forEach(action);

        } catch (DBException ex) {

            // ---------------------------------------------------------------------
            final String msg = "Failed to retrieve TaskAllocations from the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(msg, ex);

        } finally {

            if (con != null) {
                con.close();
            }
        }

        return taskAllocations;
    }
}
