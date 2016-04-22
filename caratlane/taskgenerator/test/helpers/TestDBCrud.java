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
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorRuntimeException;
import com.caratlane.taskflow.taskgenerator.generator.TaskAllocations;
import com.caratlane.taskflow.taskgenerator.generator.crud.ProjectsDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.crud.TaskAllocationsDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbEmployee;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbEmployeeHaveSkills;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbProject;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbSkill;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTemplate;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTemplateHaveSkills;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import static helpers.TestDBConstants.DELETE_TABLES_SQL;
import static java.lang.Math.toIntExact;
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
    public static TaskAllocations getEmployeeTaskAllocations(
            final Integer task_id,
            final Integer employee_id
    ) throws TaskGeneratorException {

        return TaskAllocationsDbExtractor.getEmployeeTaskAllocations(task_id, employee_id);
    }

    /**
     *
     * @param employee_id
     * @return
     * @throws TaskGeneratorException
     */
    public static TaskAllocations getEmployeeTaskAllocations(
            final Integer employee_id) throws TaskGeneratorException {

        return TaskAllocationsDbExtractor.getEmployeeTaskAllocations(employee_id);
    }

    /**
     *
     * @param skill
     * @return
     * @throws helpers.TestTaskGeneratorException
     */
    public static Integer serializeSkill(final Skill skill) throws TestTaskGeneratorException {

        DBConnection connection = null;
        DBTransaction transaction = null;

        Integer int_skill_id = 0;

        try {
            // start a new db transaction
            connection = TestDBManager.getDatabaseInstance().getConnection().open();
            transaction = connection.getTransaction();
            transaction.begin();

            final DbSkill dbSkill = skill.getDbSkill();
            long skill_id = transaction.persistGetId(dbSkill);
            int_skill_id = toIntExact(skill_id);

            transaction.commit();

        } catch (DBException | TaskGeneratorRuntimeException ex) {

            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (DBException ignore) {
                }
            }

            // ---------------------------------------------------------------------
            final String msg = "Failed to serialize Skill in the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TestTaskGeneratorException(msg, ex);

        } finally {

            if (connection != null) {
                connection.close();
            }
        }

        return int_skill_id;

    }

    /**
     *
     * @param project
     * @return
     * @throws helpers.TestTaskGeneratorException
     */
    public static Integer serializeProject(final Project project) throws TestTaskGeneratorException {

        DBConnection connection = null;
        DBTransaction transaction = null;

        Integer int_project_id = 0;

        try {
            // start a new db transaction
            connection = TestDBManager.getDatabaseInstance().getConnection().open();
            transaction = connection.getTransaction();
            transaction.begin();

            final DbProject dbProject = project.getDbProject();
            long project_id = transaction.persistGetId(dbProject);
            int_project_id = toIntExact(project_id);

            transaction.commit();

        } catch (DBException | TaskGeneratorRuntimeException ex) {

            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (DBException ignore) {
                }
            }

            // ---------------------------------------------------------------------
            final String msg = "Failed to serialize Project in the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TestTaskGeneratorException(msg, ex);

        } finally {

            if (connection != null) {
                connection.close();
            }
        }

        return int_project_id;

    }

    /**
     *
     * @param employee
     * @return
     * @throws TestTaskGeneratorException
     */
    public static Integer serializeEmployee(final Employee employee)
            throws TestTaskGeneratorException {

        DBConnection connection = null;
        DBTransaction transaction = null;

        Integer int_employee_id = 0;

        try {
            // start a new db transaction
            connection = TestDBManager.getDatabaseInstance().getConnection().open();
            transaction = connection.getTransaction();
            transaction.begin();

            final DbEmployee dbEmployee = employee.getDbEmployee();
            long employee_id = transaction.persistGetId(dbEmployee);
            int_employee_id = toIntExact(employee_id);

            transaction.commit();

        } catch (DBException | TaskGeneratorRuntimeException ex) {

            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (DBException ignore) {
                }
            }

            // ---------------------------------------------------------------------
            final String msg = "Failed to serialize Employee in the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TestTaskGeneratorException(msg, ex);

        } finally {

            if (connection != null) {
                connection.close();
            }
        }

        return int_employee_id;

    }

    /**
     *
     * @param dbTemplate
     * @return
     * @throws TestTaskGeneratorException
     */
    public static Integer serializeTemplate(final DbTemplate dbTemplate)
            throws TestTaskGeneratorException {

        DBConnection connection = null;
        DBTransaction transaction = null;

        Integer int_template_id = 0;

        try {
            // start a new db transaction
            connection = TestDBManager.getDatabaseInstance().getConnection().open();
            transaction = connection.getTransaction();
            transaction.begin();

            long template_id = transaction.persistGetId(dbTemplate);
            int_template_id = toIntExact(template_id);

            transaction.commit();

        } catch (DBException | TaskGeneratorRuntimeException ex) {

            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (DBException ignore) {
                }
            }

            // ---------------------------------------------------------------------
            final String msg = "Failed to serialize Template in the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TestTaskGeneratorException(msg, ex);

        } finally {

            if (connection != null) {
                connection.close();
            }
        }

        return int_template_id;

    }

    /**
     *
     * @param dbTemplateHaveSkills
     * @throws TestTaskGeneratorException
     */
    public static void serializeTemplateHaveSkills(
            final DbTemplateHaveSkills dbTemplateHaveSkills)
            throws TestTaskGeneratorException {

        DBConnection connection = null;
        DBTransaction transaction = null;

        try {
            // start a new db transaction
            connection = TestDBManager.getDatabaseInstance().getConnection().open();
            transaction = connection.getTransaction();
            transaction.begin();

            transaction.persist(dbTemplateHaveSkills);

            transaction.commit();

        } catch (DBException | TaskGeneratorRuntimeException ex) {

            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (DBException ignore) {
                }
            }

            // ---------------------------------------------------------------------
            final String msg = "Failed to serialize Template Have Skills in the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TestTaskGeneratorException(msg, ex);

        } finally {

            if (connection != null) {
                connection.close();
            }
        }

    }

    /**
     *
     * @param dbEmployeeHaveSkills
     * @throws TestTaskGeneratorException
     */
    public static void serializeEmployeeHaveSkills(
            final DbEmployeeHaveSkills dbEmployeeHaveSkills)
            throws TestTaskGeneratorException {

        DBConnection connection = null;
        DBTransaction transaction = null;

        try {
            // start a new db transaction
            connection = TestDBManager.getDatabaseInstance().getConnection().open();
            transaction = connection.getTransaction();
            transaction.begin();

            transaction.persist(dbEmployeeHaveSkills);

            transaction.commit();

        } catch (DBException | TaskGeneratorRuntimeException ex) {

            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (DBException ignore) {
                }
            }

            // ---------------------------------------------------------------------
            final String msg = "Failed to serialize Employee Have Skills in the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TestTaskGeneratorException(msg, ex);

        } finally {

            if (connection != null) {
                connection.close();
            }
        }

    }

}
