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
import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.EmployeeSkill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Holiday;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbEmployee;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbEmployeeSkill;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbHoliday;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers._D_IN_THREE_MONTH;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.getQueryName;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_ALL_EMPLOYEES_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_EMPLOYEE_HOLIDAYS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_EMPLOYEE_SKILLS_SUFFIX;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class EmployeesDbExtractor {

    /**
     *
     * @return @throws TaskGeneratorException
     */
    public static List<Employee> getAllEmployees() throws TaskGeneratorException {

        final List<Employee> employees = new LinkedList<>();

        final String queryName = getQueryName(DbEmployee.class, FIND_ALL_EMPLOYEES_SUFFIX);
        final List<DbEmployee> dbEmployees;
        DBConnection con = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();

            // query the database
            dbEmployees = con.query(queryName, DbEmployee.class);

            // create a Task fom a DbTask
            final Function<DbEmployee, Employee> mapper = (DbEmployee t) -> new Employee(t);

            // build the list of Tasks to be returned
            final Consumer<Employee> action = (Employee t) -> {
                employees.add(t);
            };

            dbEmployees.stream().map(mapper).forEach(action);

        } catch (DBException ex) {

            // ---------------------------------------------------------------------
            final String msg = "Failed to retrieve Employees from the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(msg, ex);

        } finally {

            if (con != null) {
                con.close();
            }
        }

        return employees;
    }

    /**
     *
     * @param employee_id
     * @return
     * @throws TaskGeneratorException
     */
    public static List<EmployeeSkill> getEmployeeSkills(Integer employee_id)
            throws TaskGeneratorException {

        final List<EmployeeSkill> employeeSkills = new LinkedList<>();

        final String queryName = getQueryName(DbEmployeeSkill.class, FIND_EMPLOYEE_SKILLS_SUFFIX);
        final List<DbEmployeeSkill> dbEmployeeSkills;
        DBConnection con = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();

            // query the database
            dbEmployeeSkills = con.query(queryName, DbEmployeeSkill.class, "employee_id", employee_id);

            // create a Task fom a DbTask
            final Function<DbEmployeeSkill, EmployeeSkill> mapper
                    = (DbEmployeeSkill t) -> new EmployeeSkill(t);

            // build the list of Tasks to be returned
            final Consumer<EmployeeSkill> action = (EmployeeSkill t) -> {
                employeeSkills.add(t);
            };

            dbEmployeeSkills.stream().map(mapper).forEach(action);

        } catch (DBException ex) {

            // ---------------------------------------------------------------------
            final String msg = "Failed to retrieve Employees' Skills from the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(msg, ex);

        } finally {

            if (con != null) {
                con.close();
            }
        }

        return employeeSkills;
    }

    /**
     *
     * @param employee_id
     * @param from
     * @return
     * @throws TaskGeneratorException
     */
    public static LinkedList<Holiday> getEmployeeHolidays(
            final Integer employee_id,
            final Date from
    )
            throws TaskGeneratorException {

        final LinkedList<Holiday> holidays = new LinkedList<>();

        final String queryName = getQueryName(DbHoliday.class, FIND_EMPLOYEE_HOLIDAYS_SUFFIX);
        final List<DbHoliday> dbNwds;
        DBConnection con = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();

            // query the database
            dbNwds = con.query(queryName,
                    DbHoliday.class,
                    "employee_id", employee_id,
                    "now", from,
                    "max_date", _D_IN_THREE_MONTH
            );

            // create a Task fom a DbTask
            final Function<DbHoliday, Holiday> mapper
                    = (DbHoliday t) -> new Holiday(t);

            // build the list of Tasks to be returned
            final Consumer<Holiday> action = (Holiday t) -> {
                holidays.add(t);
            };

            dbNwds.stream().map(mapper).forEach(action);

        } catch (DBException ex) {

            // ---------------------------------------------------------------------
            final String pattern = "Failed to retrieve Non Working Days from the database.";
            LogManager.getLogger().log(Level.SEVERE, pattern);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(ex);

        } finally {

            if (con != null) {
                con.close();
            }
        }

        return holidays;
    }

}
