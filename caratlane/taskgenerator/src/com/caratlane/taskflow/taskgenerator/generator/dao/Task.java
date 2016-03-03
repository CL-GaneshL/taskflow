/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTask;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class Task {

    private final DbTask dbTask;
    private final Integer totalDuration;
    final private LinkedList<TaskAllocation> taskAllocations = new LinkedList<>();

    /**
     * Constructor.
     *
     * @param dbTask
     */
    public Task(DbTask dbTask) {
        this.dbTask = dbTask;
        this.totalDuration = 0;
    }

    public Task(
            Integer skill_id,
            Integer project_id,
            Integer totalDuration
    ) {

        this.totalDuration = totalDuration;

        this.dbTask = new DbTask(
                skill_id,
                project_id,
                (byte) 0 // completed
        );
    }

    public Integer getId() {
        return dbTask.getId();
    }

    public Integer getSkillId() {
        return this.dbTask.getSkill_id();
    }

    public Integer getProjectId() {
        return this.dbTask.getProject_id();
    }

    public Boolean getCompleted() {
        return this.dbTask.getCompleted() == 1;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public LinkedList<TaskAllocation> getTaskAllocations() {
        return taskAllocations;
    }

    public void addTaskAllocation(final TaskAllocation taskAllocation) {
        this.taskAllocations.add(taskAllocation);
    }

    public DbTask getDbTask() {
        return dbTask;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.dbTask);
        hash = 17 * hash + Objects.hashCode(this.totalDuration);
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
        final Task other = (Task) obj;
        if (!Objects.equals(this.dbTask, other.dbTask)) {
            return false;
        }
        return Objects.equals(this.totalDuration, other.totalDuration);
    }

    @Override
    public String toString() {
        return "Task{" + "dbTask=" + dbTask + ", totalDuration=" + totalDuration + ", taskAllocations=" + taskAllocations + '}';
    }

}
