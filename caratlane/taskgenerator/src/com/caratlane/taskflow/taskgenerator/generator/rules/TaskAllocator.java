/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.ProjectData;

/**
 *
 * @author wdmtraining
 */
public interface TaskAllocator {

    /**
     *
     * @param projectData
     * @throws TaskGeneratorException
     */
    public void allocate(final ProjectData projectData) throws TaskGeneratorException;

    /**
     *
     * @param test
     * @param projectData
     * @param skill_id
     * @param nb_products
     * @throws TaskGeneratorException
     */
    public void allocate(
            final Boolean test,
            final ProjectData projectData,
            final Integer skill_id,
            final Integer nb_products
    ) throws TaskGeneratorException;
}
