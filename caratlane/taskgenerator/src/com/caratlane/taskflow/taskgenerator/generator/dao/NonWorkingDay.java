/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbNonWorkingDays;
import com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.NWDType;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.NWDType.NON_WORKING;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.NWDType.WEEKEND;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_NON_WORKING;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_WEEKEND;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class NonWorkingDay implements EmployeeNonWorkingDay {

    /**
     *
     */
    private final DbNonWorkingDays dbNonWorkingDays;

    public NonWorkingDay(DbNonWorkingDays dbNonWorkingDays) {
        this.dbNonWorkingDays = dbNonWorkingDays;
    }

    public NonWorkingDay(
            String title,
            NWDType type,
            Date date
    ) {
        String strType = null;
        switch (type) {
            case WEEKEND:
                strType = NWD_WEEKEND;
                break;
            case NON_WORKING:
                strType = NWD_NON_WORKING;
                break;
        }

        this.dbNonWorkingDays = new DbNonWorkingDays(
                title,
                strType,
                date
        );
    }

    public Integer getId() {
        return dbNonWorkingDays.getId();
    }

    public String getTitle() {
        return dbNonWorkingDays.getTitle();
    }

    public NWDType getType() {

        NWDType ret = null;

        final String type = dbNonWorkingDays.getType();

        switch (type) {
            case NWD_WEEKEND:
                ret = WEEKEND;
                break;
            case NWD_NON_WORKING:
                ret = NON_WORKING;
                break;
        }

        return ret;
    }

    @Override
    public LocalDateTime getDate() {

        final Date date = dbNonWorkingDays.getDate();
        final LocalDateTime start_date
                = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return start_date;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.dbNonWorkingDays);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NonWorkingDay other = (NonWorkingDay) obj;
        return Objects.equals(this.dbNonWorkingDays, other.dbNonWorkingDays);
    }

    @Override
    public String toString() {
        return "NonWorkingDays{" + "dbNonWorkingDays=" + dbNonWorkingDays + '}';
    }

}
