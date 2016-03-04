/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbEmployee;
import com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.EmployementType;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class Employee {

    /**
     *
     */
    private final DbEmployee dbEmployee;

    public Employee(DbEmployee dbEmployee) {
        this.dbEmployee = dbEmployee;
    }

    /**
     *
     * @param employeeId
     * @param productivity
     * @param employementType
     * @param morning_shift
     */
    public Employee(
            String employeeId,
            Double productivity,
            EmployementType employementType
    //            Boolean morning_shift
    ) {
        this.dbEmployee = new DbEmployee(
                employeeId,
                productivity,
                employementType
        //                (byte) (morning_shift == false ? 0 : 1)
        );
    }

    /**
     * For testing purposes.
     *
     * @param id
     * @param employeeId
     * @param productivity
     * @param employementType
     */
    public Employee(
            Integer id,
            String employeeId,
            Double productivity,
            EmployementType employementType
    ) {
        this.dbEmployee = new DbEmployee(
                id,
                employeeId,
                productivity,
                employementType
        //                (byte) 0
        );
    }

    public Integer getId() {
        return dbEmployee.getId();
    }

    public String getEmployeeId() {
        return dbEmployee.getEmployeeId();
    }

    public Double getProductivity() {
        return dbEmployee.getProductivity();
    }

    public EmployementType getEmployementType() {
        return dbEmployee.getEmployement_type();
    }

//    public Boolean getMorningShift() {
//        return dbEmployee.getMorning_shift() == 1;
//    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.dbEmployee);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        return Objects.equals(this.dbEmployee, other.dbEmployee);
    }

    @Override
    public String toString() {
        return "Employee{" + "dbEmployee=" + dbEmployee + '}';
    }

}
