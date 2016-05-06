/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_EMPLOYEE_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_END_DATE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_START_DATE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_TABLE_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_EMPLOYEES_HOLIDAY_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_EMPLOYEE_HOLIDAYS_SUFFIX;
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

    private static final long serialVersionUID = -8126746374294452506L;

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
     * start date
     */
    @NotNull
    @Column(name = HOLIDAY_END_DATE_COL_NAME, insertable = false, updatable = false)
    private Date end_date;

    /**
     * Default constructor.
     */
    public DbHoliday() {
    }

    /**
     *
     * @param employee_id
     * @param start_date
     * @param end_date
     */
    public DbHoliday(
            Integer employee_id,
            Date start_date,
            Date end_date
    ) {
        this.id = 0;
        this.employee_id = employee_id;
        this.start_date = start_date;
        this.end_date = end_date;
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

    public Date getEnd_date() {
        return end_date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.employee_id);
        hash = 59 * hash + Objects.hashCode(this.start_date);
        hash = 59 * hash + Objects.hashCode(this.end_date);
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
        return Objects.equals(this.end_date, other.end_date);
    }

    @Override
    public String toString() {
        return "DbHoliday{" + "id=" + id + ", employee_id=" + employee_id + ", start_date=" + start_date + ", end_date=" + end_date + '}';
    }

}
