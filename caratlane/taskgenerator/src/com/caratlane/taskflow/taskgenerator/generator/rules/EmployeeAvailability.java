/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.generator.EmployeeData;
import java.time.LocalDateTime;

/**
 *
 * @author wdmtraining
 */
public interface EmployeeAvailability {

    public LocalDateTime getNextAvailability(
            final EmployeeData employeeData,
            final LocalDateTime from
    );

    public LocalDateTime getNextAvailabilityWithReserve(
            final EmployeeData employeeData,
            final LocalDateTime from,
            final Integer reserve
    );
}
