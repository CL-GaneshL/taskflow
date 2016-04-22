/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorRuntimeException;
import com.caratlane.taskflow.taskgenerator.generator.crud.EmployeesDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.EmployeeSkill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Holiday;
import com.caratlane.taskflow.taskgenerator.generator.dao.NonWorkingDay;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class Employees {

    private static LinkedList<EmployeeData> employeData = null;

    private Employees() {
    }

    /**
     *
     * @return
     */
    public static Employees getInstance() {
        return EmployeesHolder.INSTANCE;
    }

    /**
     *
     */
    private static class EmployeesHolder {

        private static final Employees INSTANCE = new Employees();
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    public static void initialize(final Date from) throws TaskGeneratorException {

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "-+++++++++++++++++++++++++++++++++++++++++++++");
        LogManager.getLogger().log(Level.FINE, "Initializing employees ...");
        LogManager.getLogger().log(Level.FINE, "-+++++++++++++++++++++++++++++++++++++++++++++");
        // ---------------------------------------------------------------------

        employeData = new LinkedList<>();

        try {

            final List<Employee> empls = EmployeesDbExtractor.getAllEmployees();
            empls.stream().forEach((Employee employee) -> {

                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.FINE, "-------------------------------------------");
                LogManager.getLogger().log(Level.FINE, "Employee : {0}", employee);
                LogManager.getLogger().log(Level.FINE, "-------------------------------------------");
                // ---------------------------------------------------------------------

                try {
                    // create an employee record
                    final EmployeeData employeeData = new EmployeeData(employee);
                    employeData.add(employeeData);

                    // the unique employee id
                    final Integer employee_id = employee.getId();

                    // extract the list of skills for this employee
                    final List<EmployeeSkill> employeeSkills
                            = EmployeesDbExtractor.getEmployeeSkills(employee_id);

                    employeeSkills.stream().forEach((EmployeeSkill skill) -> {
                        employeeData.addSkill(skill.getSkillId());
                    });

                    // holidays for this employee
                    final LinkedList<Holiday> holidays
                            = EmployeesDbExtractor.getEmployeeHolidays(employee_id, from);

                    final LinkedList<NonWorkingDay> nwds = NonWorkingDays.getInstance().getNwds();
                    employeeData.setEmployeeNonWorkingDays(holidays, nwds);

                } catch (TaskGeneratorException ex) {
                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
                    // ---------------------------------------------------------------------
                    throw new TaskGeneratorRuntimeException(ex);
                }
            });

        } catch (TaskGeneratorRuntimeException | TaskGeneratorException ex) {

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
            // ---------------------------------------------------------------------    

            throw new TaskGeneratorException(ex);
        }

    }

    /**
     *
     * @return
     */
    public LinkedList<EmployeeData> getEmployeeData() {
        return employeData;
    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @throws TaskGeneratorException
     */
    public static void initialize(boolean test) throws TaskGeneratorException {

        employeData = new LinkedList<>();
    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @param employee
     * @para
     */
    public void addEmployeeData(boolean test, final EmployeeData employee) {
        employeData.add(employee);
    }

    /**
     * For testing purpose only.
     *
     * @param test
     */
    public void clearEmployeeData(boolean test) {
        employeData.clear();
    }
}
