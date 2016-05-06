/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_COMPLETED_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_COMPLETION_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_DURATION_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_EMPLOYEE_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_NB_PRODUCTS_COMPLETED_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_NB_PRODUCTS_PLANNED_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_START_DATE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_TABLE_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_TASK_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.DELETE_TASK_ALLOCATIONS_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.DELETE_TASK_ALLOCATIONS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_EMPLOYEE_TASK_ALLOCATIONS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_TASK_EMPLOYEE_TASK_ALLOCATIONS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_EMPLOYEE_TASK_ALLOCATIONS_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_TASK_EMPLOYEE_TASK_ALLOCATIONS_QUERY;
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
import javax.persistence.Temporal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author wdmtraining
 */
@Entity(name = TASK_ALLOCATION_ENTITY_NAME)
@Table(name = TASK_ALLOCATION_TABLE_NAME)
@NamedQueries({
    @NamedQuery(
            name = TASK_ALLOCATION_ENTITY_NAME + FIND_TASK_EMPLOYEE_TASK_ALLOCATIONS_SUFFIX,
            query = FIND_TASK_EMPLOYEE_TASK_ALLOCATIONS_QUERY
    ),
    @NamedQuery(
            name = TASK_ALLOCATION_ENTITY_NAME + FIND_EMPLOYEE_TASK_ALLOCATIONS_SUFFIX,
            query = FIND_EMPLOYEE_TASK_ALLOCATIONS_QUERY
    ),
    @NamedQuery(
            name = TASK_ALLOCATION_ENTITY_NAME + DELETE_TASK_ALLOCATIONS_SUFFIX,
            query = DELETE_TASK_ALLOCATIONS_QUERY
    )
})
@SuppressWarnings("ValidAttributes")
public class DbTaskAllocation implements Serializable {

    private static final long serialVersionUID = 3185083304707253883L;

    /**
     * Index of the task in its database table.
     */
    @Id
    @NotNull
    @Min(value = 1)
    @Column(name = TASK_ALLOCATION_ID_COL_NAME, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *
     */
    @NotNull
    @Min(value = 1)
    @Column(name = TASK_ALLOCATION_TASK_ID_COL_NAME, insertable = true, updatable = false)
    private Integer task_id;

    /**
     * employee id
     */
    @NotNull
    @Min(value = 1)
    @Column(name = TASK_ALLOCATION_EMPLOYEE_ID_COL_NAME, insertable = true, updatable = false)
    private Integer employee_id;

    /**
     * start date
     */
    @NotNull
    @Column(
            name = TASK_ALLOCATION_START_DATE_COL_NAME,
            columnDefinition = "DATETIME",
            insertable = true, updatable = false
    )
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date start_date;

    /**
     * completion
     */
    @NotNull
    @Min(value = 0)
    @Column(name = TASK_ALLOCATION_COMPLETION_COL_NAME, insertable = true, updatable = false)
    private Integer completion;

    /**
     * nb products completed
     */
    @NotNull
    @Min(value = 0)
    @Column(name = TASK_ALLOCATION_NB_PRODUCTS_COMPLETED_COL_NAME, insertable = true, updatable = false)
    private Integer nb_products_completed;

    /**
     * nb planned products
     */
    @NotNull
    @Min(value = 0)
    @Column(name = TASK_ALLOCATION_NB_PRODUCTS_PLANNED_COL_NAME, insertable = true, updatable = false)
    private Double nb_products_planned;

    /**
     * completed
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = TASK_ALLOCATION_COMPLETED_COL_NAME, insertable = true, updatable = false)
    private Byte completed;

    /**
     * duration
     */
    @NotNull
    @Min(value = 0)
    @Column(name = TASK_ALLOCATION_DURATION_COL_NAME, insertable = true, updatable = false)
    private Integer duration;

    public DbTaskAllocation() {
    }

    public DbTaskAllocation(
            final Integer employee_id,
            final Integer task_id,
            final Date start_date,
            final Integer completion,
            final Integer nb_products_completed,
            final Double nb_products_planned,
            final Byte completed,
            final Integer duration
    ) {
        this.employee_id = employee_id;
        this.task_id = task_id;
        this.start_date = start_date;
        this.completion = completion;
        this.nb_products_completed = nb_products_completed;
        this.nb_products_planned = nb_products_planned;
        this.completed = completed;
        this.duration = duration;

    }

    public Integer getId() {
        return id;
    }

    public Integer getTask_id() {
        return task_id;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Integer getCompletion() {
        return completion;
    }

    public Integer getNb_products_completed() {
        return nb_products_completed;
    }

    public Double getNb_products_planned() {
        return nb_products_planned;
    }

    public Byte getCompleted() {
        return completed;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.task_id);
        hash = 23 * hash + Objects.hashCode(this.employee_id);
        hash = 23 * hash + Objects.hashCode(this.task_id);
        hash = 23 * hash + Objects.hashCode(this.start_date);
        hash = 23 * hash + Objects.hashCode(this.completion);
        hash = 23 * hash + Objects.hashCode(this.nb_products_completed);
        hash = 23 * hash + Objects.hashCode(this.nb_products_planned);
        hash = 23 * hash + Objects.hashCode(this.completed);
        hash = 23 * hash + Objects.hashCode(this.duration);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DbTaskAllocation other = (DbTaskAllocation) obj;
        if (!Objects.equals(this.task_id, other.task_id)) {
            return false;
        }
        if (!Objects.equals(this.employee_id, other.employee_id)) {
            return false;
        }
        if (!Objects.equals(this.employee_id, other.task_id)) {
            return false;
        }
        if (!Objects.equals(this.start_date, other.start_date)) {
            return false;
        }
        if (!Objects.equals(this.completion, other.completion)) {
            return false;
        }
        if (!Objects.equals(this.nb_products_completed, other.nb_products_completed)) {
            return false;
        }
        if (!Objects.equals(this.nb_products_planned, other.nb_products_planned)) {
            return false;
        }
        if (!Objects.equals(this.completed, other.completed)) {
            return false;
        }
        return Objects.equals(this.duration, other.duration);
    }

    @Override
    public String toString() {
        return "DbTaskAllocation{" + "id=" + id + ", task_id=" + task_id + ", employee_id=" + employee_id + ", start_date=" + start_date + ", completion=" + completion + ", nb_products_completed=" + nb_products_completed + ", nb_products_planned=" + nb_products_planned + ", completed=" + completed + ", duration=" + duration + '}';
    }

}
