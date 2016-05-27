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
public class EmployeeAvailabilityFactory {

    private final String type;

    public EmployeeAvailabilityFactory(String type) {
        this.type = type;
    }

    public EmployeeAvailability getInstance() {

        EmployeeAvailability instance = null;

        switch (this.type) {

            case TIME_BASED:
                instance = new EmployeeAvailabilityTimeBased();
                break;

            case PRODUCT_BASED:
                instance = new EmployeeAvailabilityProductBased();
                break;

            default:

        }

        return instance;
    }

}
