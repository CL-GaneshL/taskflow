/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbHoliday;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class Holiday {

    /**
     *
     */
    private final DbHoliday dbHolidays;

    public Holiday(
            Integer employee_id,
            Date start_date,
            Byte start_morning_shift,
            Byte start_afternoon_shift,
            Date end_date,
            Byte end_morning_shift,
            Byte end_afternoon_shift
    ) {
        this.dbHolidays = new DbHoliday(
                employee_id,
                start_date,
                start_morning_shift,
                start_afternoon_shift,
                end_date,
                end_morning_shift,
                end_afternoon_shift
        );
    }

    public Holiday(DbHoliday dbHolidays) {
        this.dbHolidays = dbHolidays;
    }

    public Integer getId() {
        return dbHolidays.getId();
    }

    public Integer getEmployeeId() {
        return dbHolidays.getEmployee_id();
    }

    public LocalDateTime getStartDate() {
        final Date date = dbHolidays.getStart_date();
        final LocalDateTime start_date
                = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return start_date;
    }

    public Byte getStartMorningShift() {
        return dbHolidays.getStart_morning_shift();
    }

    public Byte getStartAfternoonShift() {
        return dbHolidays.getStart_afternoon_shift();
    }

    public LocalDateTime getEndDate() {

        final Date date = dbHolidays.getEnd_date();
        final LocalDateTime end_date
                = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return end_date;
    }

    public Byte getEndMorningShift() {
        return dbHolidays.getEnd_morning_shift();
    }

    public Byte getEndAfternoonShift() {
        return dbHolidays.getEnd_afternoon_shift();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.dbHolidays);
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
        final Holiday other = (Holiday) obj;
        return Objects.equals(this.dbHolidays, other.dbHolidays);
    }

    @Override
    public String toString() {
        return "Holidays{" + "dbHolidays=" + dbHolidays + '}';
    }

}
