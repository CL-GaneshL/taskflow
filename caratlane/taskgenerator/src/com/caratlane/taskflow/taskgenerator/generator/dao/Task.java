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
    final private LinkedList<TaskAllocation> taskAllocations = new LinkedList<>();
    private boolean created = false;
    private boolean modified = false;

    /**
     * Constructor.
     *
     * @param dbTask
     */
    public Task(DbTask dbTask) {
        this.dbTask = dbTask;
    }

    public Task(
            Integer skill_id,
            Integer project_id
    ) {
        this.dbTask = new DbTask(
                skill_id,
                project_id
        );

    }

    public static Task newTask(
            Integer skill_id,
            Integer project_id) {

        final Task t = new Task(skill_id, project_id);
        t.created = true;

        return t;
    }

    public boolean isCreated() {
        return created;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
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

    public LinkedList<TaskAllocation> getTaskAllocations() {
        return taskAllocations;
    }

    public void addTaskAllocation(final TaskAllocation taskAllocation) {
        this.taskAllocations.add(taskAllocation);
    }

    /**
     *
     * @param task
     * @param taskAllocation
     */
    public static void addNewAllocation(
            final Task task,
            final TaskAllocation taskAllocation
    ) {
        task.taskAllocations.add(taskAllocation);
        task.modified = true;
    }

    /**
     *
     * @param task
     * @param taskAllocation
     */
    public static void removeAllocation(
            final Task task,
            final TaskAllocation taskAllocation
    ) {
        task.taskAllocations.remove(taskAllocation);
        task.modified = true;
    }

    public DbTask getDbTask() {
        return dbTask;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.dbTask);
        hash = 53 * hash + (this.created ? 1 : 0);
        hash = 53 * hash + (this.modified ? 1 : 0);
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
        if (this.created != other.created) {
            return false;
        }
        return this.modified == other.modified;
    }

    @Override
    public String toString() {
        return "Task = {" + dbTask + ", taskAllocations=" + taskAllocations + ", created=" + created + ", modified=" + modified + '}';
    }

}
