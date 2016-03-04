/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class ProjectData {

    private final Project project;
    private final List<Integer> skills;
    private final LinkedList<Task> tasks;

    public ProjectData(final Project project) {
        this.project = project;
        this.skills = new LinkedList<>();
        this.tasks = new LinkedList<>();
    }

    public void addSkill(final Integer skill) {
        this.skills.add(skill);
    }

    public Project getProject() {
        return project;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    /**
     *
     * @param task
     */
    public void addTask(final Task task) {
        this.tasks.add(task);
    }

    public LinkedList<Task> getTasks() {
        return tasks;
    }

    /**
     * For test purposes only.
     *
     * @param test
     */
    public void clearTasks(boolean test) {
        this.tasks.clear();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.project);
        hash = 89 * hash + Objects.hashCode(this.skills);
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
        final ProjectData other = (ProjectData) obj;
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        return Objects.equals(this.skills, other.skills);
    }

    @Override
    public String toString() {
        return "ProjectData{" + "project=" + project + ", skills=" + skills + '}';
    }

}
