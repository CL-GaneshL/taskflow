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
public interface EmployeeSkillSuitability {

    public EmployeeData findMostSuitableEmployee(
            final Integer skill_id,
            final LocalDateTime from
    );

    public EmployeeData findMostSuitableEmployeeWithReserve(
            final Integer skill_id,
            final LocalDateTime from,
            final Integer reserve
    );
}
