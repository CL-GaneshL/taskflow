/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.dao.NonWorkingDay;
import java.util.LinkedList;

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
    public static void initialize() throws TaskGeneratorException {

        employeData = new LinkedList<>();
    }

    /**
     *
     * @param employee
     */
    public void addEmployeeData(final EmployeeData employee) {
        employeData.add(employee);
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
