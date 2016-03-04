/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTaskAllocation;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class TaskAllocation {

    private final DbTaskAllocation dbTaskAllocation;
    private boolean created = true;

    public TaskAllocation(
            final Integer employee_id,
            final LocalDateTime start_date,
            final Integer duration
    ) {

        final Instant instant = start_date.atZone(ZoneId.systemDefault()).toInstant();
        final Date d_start_date = Date.from(instant);

        this.dbTaskAllocation = new DbTaskAllocation(
                employee_id,
                d_start_date,
                0, //completion,
                0, //nb_products_completed,
                (byte) 0, // completed,
                duration
        );
    }

    public static TaskAllocation createNewTaskAllocation(
            final Integer employee_id,
            final LocalDateTime start_date,
            final Integer duration) {

        final TaskAllocation taskAllocation = new TaskAllocation(
                employee_id,
                start_date,
                duration
        );

        taskAllocation.created = true;

        return taskAllocation;
    }

    public TaskAllocation(DbTaskAllocation dbTaskAllocation) {
        this.dbTaskAllocation = dbTaskAllocation;
    }

    public DbTaskAllocation getDbTaskAllocation() {
        return dbTaskAllocation;
    }

    public Integer getId() {
        return dbTaskAllocation.getId();
    }

    public Integer getTaskId() {
        return dbTaskAllocation.getTask_id();
    }

    public Integer getEmployeeId() {
        return dbTaskAllocation.getEmployee_id();
    }

    public LocalDateTime getStartDate() {

        final Date start_date = this.dbTaskAllocation.getStart_date();
        final LocalDateTime start_date_ld
                = start_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return start_date_ld;
    }

    public Integer getCompletion() {
        return dbTaskAllocation.getCompletion();
    }

    public Integer getNbProductsCompleted() {
        return dbTaskAllocation.getNb_products_completed();
    }

    public Boolean isCompleted() {
        return this.dbTaskAllocation.getCompleted() == 1;
    }

    public Integer getDuration() {
        return dbTaskAllocation.getDuration();
    }

    public void setTaskId(Integer task_id) {
        dbTaskAllocation.setTask_id(task_id);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.dbTaskAllocation);
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
        final TaskAllocation other = (TaskAllocation) obj;
        return Objects.equals(this.dbTaskAllocation, other.dbTaskAllocation);
    }

    @Override
    public String toString() {
        return "TaskAllocation{" + "dbTaskAllocation=" + dbTaskAllocation + '}';
    }

}
