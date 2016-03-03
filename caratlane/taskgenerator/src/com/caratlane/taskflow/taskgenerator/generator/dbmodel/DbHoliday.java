/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.FIND_EMPLOYEES_HOLIDAY_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.FIND_EMPLOYEE_HOLIDAYS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_EMPLOYEE_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_END_AFTERNOON_SHIFT_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_END_DATE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_END_MORNING_SHIFT_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_START_AFTERNOON_SHIFT_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_START_DATE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_START_MORNING_SHIFT_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_TABLE_NAME;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author wdmtraining
 */
@Entity(name = HOLIDAY_ENTITY_NAME)
@Table(name = HOLIDAY_TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = HOLIDAY_ENTITY_NAME + FIND_EMPLOYEE_HOLIDAYS_SUFFIX, query = FIND_EMPLOYEES_HOLIDAY_QUERY),})
@SuppressWarnings("ValidAttributes")
public class DbHoliday implements Serializable {

    /**
     * Index of the task in its database table.
     */
    @Id
    @Min(value = 1)
    @Column(name = HOLIDAY_ID_COL_NAME, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * employee id
     */
    @NotNull
    @Min(value = 1)
    @Column(name = HOLIDAY_EMPLOYEE_ID_COL_NAME, insertable = false, updatable = false)
    private Integer employee_id;

    /**
     * start date
     */
    @NotNull
    @Column(name = HOLIDAY_START_DATE_COL_NAME, insertable = false, updatable = false)
    private Date start_date;

    /**
     * start morning shift
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = HOLIDAY_START_MORNING_SHIFT_COL_NAME, insertable = true, updatable = false)
    private Byte start_morning_shift;

    /**
     * start afternoon shift
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = HOLIDAY_START_AFTERNOON_SHIFT_COL_NAME, insertable = true, updatable = false)
    private Byte start_afternoon_shift;

    /**
     * start date
     */
    @NotNull
    @Column(name = HOLIDAY_END_DATE_COL_NAME, insertable = false, updatable = false)
    private Date end_date;

    /**
     * start morning shift
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = HOLIDAY_END_MORNING_SHIFT_COL_NAME, insertable = true, updatable = false)
    private Byte end_morning_shift;

    /**
     * start afternoon shift
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = HOLIDAY_END_AFTERNOON_SHIFT_COL_NAME, insertable = true, updatable = false)
    private Byte end_afternoon_shift;

    /**
     * Default constructor.
     */
    public DbHoliday() {
    }

    public DbHoliday(Integer employee_id, Date start_date, Byte start_morning_shift, Byte start_afternoon_shift, Date end_date, Byte end_morning_shift, Byte end_afternoon_shift) {
        this.id = 0;
        this.employee_id = employee_id;
        this.start_date = start_date;
        this.start_morning_shift = start_morning_shift;
        this.start_afternoon_shift = start_afternoon_shift;
        this.end_date = end_date;
        this.end_morning_shift = end_morning_shift;
        this.end_afternoon_shift = end_afternoon_shift;
    }

    public Integer getId() {
        return id;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Byte getStart_morning_shift() {
        return start_morning_shift;
    }

    public Byte getStart_afternoon_shift() {
        return start_afternoon_shift;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public Byte getEnd_morning_shift() {
        return end_morning_shift;
    }

    public Byte getEnd_afternoon_shift() {
        return end_afternoon_shift;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.employee_id);
        hash = 97 * hash + Objects.hashCode(this.start_date);
        hash = 97 * hash + Objects.hashCode(this.start_morning_shift);
        hash = 97 * hash + Objects.hashCode(this.start_afternoon_shift);
        hash = 97 * hash + Objects.hashCode(this.end_date);
        hash = 97 * hash + Objects.hashCode(this.end_morning_shift);
        hash = 97 * hash + Objects.hashCode(this.end_afternoon_shift);
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
        final DbHoliday other = (DbHoliday) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.employee_id, other.employee_id)) {
            return false;
        }
        if (!Objects.equals(this.start_date, other.start_date)) {
            return false;
        }
        if (!Objects.equals(this.start_morning_shift, other.start_morning_shift)) {
            return false;
        }
        if (!Objects.equals(this.start_afternoon_shift, other.start_afternoon_shift)) {
            return false;
        }
        if (!Objects.equals(this.end_date, other.end_date)) {
            return false;
        }
        if (!Objects.equals(this.end_morning_shift, other.end_morning_shift)) {
            return false;
        }
        return Objects.equals(this.end_afternoon_shift, other.end_afternoon_shift);
    }

    @Override
    public String toString() {
        return "DbHolidays{" + "id=" + id + ", employee_id=" + employee_id + ", start_date=" + start_date + ", start_morning_shift=" + start_morning_shift + ", start_afternoon_shift=" + start_afternoon_shift + ", end_date=" + end_date + ", end_morning_shift=" + end_morning_shift + ", end_afternoon_shift=" + end_afternoon_shift + '}';
    }

}
