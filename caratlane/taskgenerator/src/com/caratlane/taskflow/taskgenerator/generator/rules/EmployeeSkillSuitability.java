/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.generator.EmployeeData;
import com.caratlane.taskflow.taskgenerator.generator.Employees;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author wdmtraining
 */
public class EmployeeSkillSuitability {

    public static EmployeeData findMostSuitableEmployeeWithSkill(
            final Integer skill
    ) {

        EmployeeData employeeData = null;

        final Employees instance = Employees.getInstance();
        final LinkedList<EmployeeData> employees = instance.getEmployeeData();

        // get the list of all employees that have that skill
        final Stream<EmployeeData> skilledEmployees
                = employees.stream().filter((EmployeeData ed) -> {
                    return ed.hasSkill(skill);
                });

        // sort employees by their earliest availability
        final Stream<EmployeeData> sortedEmployees
                = skilledEmployees.sorted((EmployeeData ed1, EmployeeData ed2) -> {

                    final LocalDateTime start_date1 = ed1.getEarliestAvailability();
                    final LocalDateTime start_date2 = ed2.getEarliestAvailability();

                    return start_date1.compareTo(start_date2);
                });

        final Optional<EmployeeData> first = sortedEmployees.findFirst();

        if (first.isPresent()) {
            employeeData = first.get();
        }

        return employeeData;
    }

}
