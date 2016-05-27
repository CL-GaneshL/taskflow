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

    /**
     *
     * @param employee_id
     * @param start_date
     * @param end_date
     */
    public Holiday(
            Integer employee_id,
            Date start_date,
            Date end_date
    ) {
        this.dbHolidays = new DbHoliday(
                employee_id,
                start_date,
                end_date
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

    public LocalDateTime getEndDate() {

        final Date date = dbHolidays.getEnd_date();
        final LocalDateTime end_date
                = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return end_date;
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
        return "Holidays = {" + dbHolidays + '}';
    }

}
