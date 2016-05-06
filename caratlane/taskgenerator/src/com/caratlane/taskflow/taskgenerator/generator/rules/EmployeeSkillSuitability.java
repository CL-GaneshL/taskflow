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

    /**
     *
     * @param skill_id
     * @param from
     * @return
     */
    public static EmployeeData findMostSuitableEmployeeWithSkill(
            final Integer skill_id,
            final LocalDateTime from
    ) {

        EmployeeData employeeData = null;

        final Employees instance = Employees.getInstance();
        final LinkedList<EmployeeData> employees = instance.getEmployeeData();

        // get the list of all employees that have that skill
        final Stream<EmployeeData> skilledEmployees
                = employees.stream().filter((EmployeeData ed) -> {
                    return ed.hasSkill(skill_id);
                });

        // sort employees by their earliest availability
        final Optional<EmployeeData> first
                = skilledEmployees.sorted((EmployeeData ed1, EmployeeData ed2) -> {

                    final LocalDateTime start_date1
                    = EmployeeAvailability.getNextAvailability(ed1, from);

                    final LocalDateTime start_date2
                    = EmployeeAvailability.getNextAvailability(ed2, from);

                    return start_date1.compareTo(start_date2);
                })
                .findFirst();

        if (first.isPresent()) {
            employeeData = first.get();
        }

        return employeeData;
    }

}
