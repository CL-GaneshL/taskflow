/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import static com.caratlane.taskflow.taskgenerator.generator.rules.Constants.PRODUCT_BASED;
import static com.caratlane.taskflow.taskgenerator.generator.rules.Constants.TIME_BASED;

/**
 *
 * @author wdmtraining
 */
public class EmployeeSkillSuitabilityFactory {

    private final String type;

    public EmployeeSkillSuitabilityFactory(String type) {
        this.type = type;
    }

    public EmployeeSkillSuitability getInstance() {

         EmployeeSkillSuitability instance = null;

        switch (this.type) {

            case TIME_BASED:
                instance = new EmployeeSkillSuitabilityTimeBased();
                break;

            case PRODUCT_BASED:
                instance = new EmployeeSkillSuitabilityProductBased();
                break;

            default:

        }

        return instance;
    }

}
