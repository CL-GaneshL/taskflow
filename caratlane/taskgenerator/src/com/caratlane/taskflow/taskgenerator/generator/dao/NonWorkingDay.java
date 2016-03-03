/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbNonWorkingDays;
import com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.NWDType;
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
            Date date,
            Byte morning_shift,
            Byte afternoon_shift
    ) {

        this.dbNonWorkingDays = new DbNonWorkingDays(
                title,
                type,
                date,
                morning_shift,
                afternoon_shift
        );
    }

    public Integer getId() {
        return dbNonWorkingDays.getId();
    }

    public String getTitle() {
        return dbNonWorkingDays.getTitle();
    }

    public NWDType getType() {
        return dbNonWorkingDays.getType();
    }

    @Override
    public LocalDateTime getDate() {

        final Date date = dbNonWorkingDays.getDate();
        final LocalDateTime start_date
                = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return start_date;
    }

    public Boolean getMorningShift() {
        return dbNonWorkingDays.getMorning_shift() == (byte) 1;
    }

    public Boolean getAfternoonShift() {
        return dbNonWorkingDays.getAfternoon_shift() == (byte) 1;
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
