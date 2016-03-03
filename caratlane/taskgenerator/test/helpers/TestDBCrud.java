/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.caratlane.taskflow.taskgenerator.db.DBConnection;
import com.caratlane.taskflow.taskgenerator.db.DBException;
import com.caratlane.taskflow.taskgenerator.db.DBManager;
import com.caratlane.taskflow.taskgenerator.db.DBTransaction;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.crud.ProjectsDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.crud.TaskAllocationsDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import static helpers.TestDBConstants.DELETE_TABLES_SQL;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class TestDBCrud {

    /**
     *
     * @throws TestTaskGeneratorException
     */
    public static void truncateTables() throws TestTaskGeneratorException {

        DBConnection con = null;
        DBTransaction transaction = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();
            transaction = con.getTransaction();

            transaction.begin();

            for (String sql : DELETE_TABLES_SQL) {
                try {
                    transaction.executeNativeSQL(sql);
                } catch (Throwable ex) {
                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
                    // ---------------------------------------------------------------------
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
            final String msg = "Failed to truncate tables.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TestTaskGeneratorException(msg, ex);

        } finally {

            if (con != null) {
                con.close();
            }
        }
    }

    /**
     *
     * @param project_id
     * @return
     * @throws TaskGeneratorException
     */
    public static List<Task> getProjectTasks(final Integer project_id)
            throws TaskGeneratorException {

        return ProjectsDbExtractor.getProjectTasks(project_id);
    }

    /**
     *
     * @param task_id
     * @param employee_id
     * @return
     * @throws TaskGeneratorException
     */
    public static List<TaskAllocation> getEmployeeTaskAllocations(
            final Integer task_id,
            final Integer employee_id
    ) throws TaskGeneratorException {

        return TaskAllocationsDbExtractor.getEmployeeTaskAllocations(task_id, employee_id);
    }

}
