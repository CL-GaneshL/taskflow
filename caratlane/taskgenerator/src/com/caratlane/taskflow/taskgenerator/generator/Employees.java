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
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
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
     * @param from
     * @throws TaskGeneratorException
     */
    public static void initialize(final Date from) throws TaskGeneratorException {

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        LogManager.getLogger().log(Level.FINE, "Initializing employees ...");
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        // ---------------------------------------------------------------------

        Employees.employeData = new LinkedList<>();

        try {

            final List<Employee> empls = EmployeesDbExtractor.getAllEmployees();

            // ---------------------------------------------------------------------
            if (LogManager.isTestLoggable()) {
                LogManager.logTestMsg(Level.INFO, "Employees created : ");
            } // ---------------------------------------------------------------------  

            if (empls.isEmpty()) {
                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                LogManager.getLogger().log(Level.FINE, " No Employees in the database !!!!!!!!!!!!!");
                LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                // ---------------------------------------------------------------------     

                // ---------------------------------------------------------------------
                if (LogManager.isTestLoggable()) {
                    LogManager.logTestMsg(Level.SEVERE, " - No Employees found in the Database !");
                } // ---------------------------------------------------------------------
            }

            empls.stream().forEach((Employee employee) -> {

                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.FINE, "-------------------------------------------");
                LogManager.getLogger().log(Level.FINE, " + {0}", employee);
                LogManager.getLogger().log(Level.FINE, "-------------------------------------------");
                // ---------------------------------------------------------------------

                // ---------------------------------------------------------------------
                if (LogManager.isTestLoggable()) {
                    LogManager.logTestMsg(Level.INFO, "  " + employee);
                } // ---------------------------------------------------------------------

                try {
                    // create an employee record
                    final EmployeeData employeeData = new EmployeeData(employee);
                    Employees.employeData.add(employeeData);

                    // the unique employee id
                    final Integer employee_id = employee.getId();

                    // extract the list of skills for this employee
                    final List<EmployeeSkill> employeeSkills
                            = EmployeesDbExtractor.getEmployeeSkills(employee_id);

                    // ---------------------------------------------------------------------
                    if (LogManager.isTestLoggable()) {
                        LogManager.logTestMsg(Level.INFO, "  Employee s skills : ");
                    } // ---------------------------------------------------------------------  

                    if (employeeSkills.isEmpty()) {
                        // ---------------------------------------------------------------------
                        LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                        LogManager.getLogger().log(Level.FINE, " + Employee has no Skills !!!!!!!!!!!!!");
                        LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                        // ---------------------------------------------------------------------

                        // ---------------------------------------------------------------------
                        if (LogManager.isTestLoggable()) {
                            LogManager.logTestMsg(Level.WARNING, "   - Employee has no Skills associated.");
                        } // ---------------------------------------------------------------------
                    } else {
                        employeeSkills.stream().forEach((EmployeeSkill employeeSkill) -> {

                            final Integer skill_id = employeeSkill.getSkillId();

                            // check that the skill exists in the db
                            final Skill skill = Skills.getInstance().getSkill(skill_id);

                            if (skill == null) {
                                // ---------------------------------------------------------------------
                                LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                                LogManager.getLogger().log(Level.FINE, " Skill = {0} not exist in the database !!!!!!!!!!!", skill_id);
                                LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                                // ---------------------------------------------------------------------  

                                // ---------------------------------------------------------------------
                                if (LogManager.isTestLoggable()) {
                                    LogManager.logTestMsg(Level.SEVERE, "   Skill Id = " + skill_id + " not in the database. Check with DB Administrator.");
                                } // ---------------------------------------------------------------------
                            
                            } else {
                                final boolean open = skill.getOpen() == (byte) 1;

                                if (open) {
                                    // ---------------------------------------------------------------------
                                    LogManager.getLogger().log(Level.FINE, "  {0}", skill);
                                    // ---------------------------------------------------------------------

                                    // ---------------------------------------------------------------------
                                    if (LogManager.isTestLoggable()) {
                                        LogManager.logTestMsg(Level.INFO, "  " + skill);
                                    } // ---------------------------------------------------------------------  
                                } else {
                                    // ---------------------------------------------------------------------
                                    LogManager.getLogger().log(Level.FINE, "  CLOSED SKILL : {0}", skill);
                                    // ---------------------------------------------------------------------

                                    // ---------------------------------------------------------------------
                                    if (LogManager.isTestLoggable()) {
                                        LogManager.logTestMsg(Level.SEVERE, "  CLOSED SKILL - REMOVE SKILL FROM EMPLOYEE : " + skill);
                                    } // ---------------------------------------------------------------------     
                                }
                            }

                            employeeData.addSkill(skill_id);
                        });
                    }

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
        return Employees.employeData;
    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @throws TaskGeneratorException
     */
    public static void initialize(boolean test) throws TaskGeneratorException {
        Employees.employeData = new LinkedList<>();
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
