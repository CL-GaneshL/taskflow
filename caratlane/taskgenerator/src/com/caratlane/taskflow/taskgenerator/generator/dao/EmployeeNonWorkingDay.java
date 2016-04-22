/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers;
import java.time.LocalDateTime;

/**
 *
 * @author wdmtraining
 */
public interface EmployeeNonWorkingDay {

    public Integer getId();

    public String getTitle();

    public ExtractorDbHelpers.NWDType getType();

    public LocalDateTime getDate();

}
