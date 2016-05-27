/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import static com.caratlane.taskflow.taskgenerator.generator.rules.Constants.PRODUCT_BASED;
import static com.caratlane.taskflow.taskgenerator.generator.rules.Constants.TIME_BASED;
import java.time.LocalDateTime;

/**
 *
 * @author wdmtraining
 */
public class TaskAllocatorFactory {

    private final String type;

    public TaskAllocatorFactory(String type) {
        this.type = type;
    }

    public TaskAllocator getInstance(final LocalDateTime allocation_from) {

        TaskAllocator instance = null;

        switch (this.type) {

            case TIME_BASED:
                instance = new TaskAllocatorTimeBased(allocation_from);
                break;

            case PRODUCT_BASED:
                instance = new TaskAllocatorProductBased(allocation_from);
                break;

            default:

        }

        return instance;

    }

}
