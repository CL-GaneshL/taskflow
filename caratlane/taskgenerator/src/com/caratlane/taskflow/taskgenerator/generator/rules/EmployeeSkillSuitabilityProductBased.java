/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.generator.EmployeeData;
import com.caratlane.taskflow.taskgenerator.generator.Employees;
import static com.caratlane.taskflow.taskgenerator.generator.rules.Constants.PRODUCT_BASED;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author wdmtraining
 */
class EmployeeSkillSuitabilityProductBased implements EmployeeSkillSuitability {

    /**
     *
     * @param skill_id
     * @param from
     * @return
     */
    @Override
    public EmployeeData findMostSuitableEmployeeWithReserve(
            final Integer skill_id,
            final LocalDateTime from,
            final Integer reserve
    ) {

        EmployeeData employeeData = null;

        final Employees instance = Employees.getInstance();
        final LinkedList<EmployeeData> employees = instance.getEmployeeData();

        // get the list of all employees that have that skill
        final Stream<EmployeeData> skilledEmployees
                = employees.stream().filter((EmployeeData ed) -> {
                    return ed.hasSkill(skill_id);
                });

        final EmployeeAvailability employeeAvailability
                = (new EmployeeAvailabilityFactory(PRODUCT_BASED)).getInstance();

        // sort employees by their earliest availability
        final Optional<EmployeeData> first
                = skilledEmployees.sorted((EmployeeData ed1, EmployeeData ed2) -> {

                    final LocalDateTime start_date1
                            = employeeAvailability.getNextAvailabilityWithReserve(ed1, from, reserve);

                    final LocalDateTime start_date2
                            = employeeAvailability.getNextAvailabilityWithReserve(ed2, from, reserve);

                    return start_date1.compareTo(start_date2);
                })
                .findFirst();

        if (first.isPresent()) {
            employeeData = first.get();
        }

        return employeeData;
    }

    @Override
    public EmployeeData findMostSuitableEmployee(Integer skill_id, LocalDateTime from) {
        throw new UnsupportedOperationException("Not supported yet."); }

}
