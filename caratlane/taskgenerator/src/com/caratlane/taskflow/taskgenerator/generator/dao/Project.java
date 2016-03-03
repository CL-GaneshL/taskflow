/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbProject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class Project {

    /**
     *
     */
    private final DbProject dbProject;

    /**
     *
     * @param dbProject
     */
    public Project(DbProject dbProject) {
        this.dbProject = dbProject;
    }

    /**
     *
     * @param project_id
     * @param template_id
     * @param nb_products
     * @param priority
     * @param start_date
     * @param open
     */
    public Project(
            Integer project_id,
            Integer template_id,
            Integer nb_products,
            Integer priority,
            Date start_date,
            Byte open
    ) {

        this.dbProject = new DbProject(
                project_id,
                template_id,
                nb_products,
                priority,
                start_date,
                open
        );
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return this.dbProject.getId();
    }

    /**
     *
     * @return
     */
    public Integer getTemplateId() {
        return this.dbProject.getTemplate_id();
    }

    /**
     *
     * @return
     */
    public Integer getNbProducts() {
        return this.dbProject.getNb_products();
    }

    /**
     *
     * @return
     */
    public Integer getPriority() {
        return this.dbProject.getPriority();
    }

    /**
     *
     * @return
     */
    public LocalDateTime getStartDate() {

        final Date start_date = this.dbProject.getStart_date();
        final LocalDateTime start_date_ld
                = start_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return start_date_ld;
    }

    /**
     *
     * @return
     */
    public Boolean getOpen() {
        return this.dbProject.getOpen() == 1;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.dbProject);
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
        final Project other = (Project) obj;
        return Objects.equals(this.dbProject, other.dbProject);
    }

    @Override
    public String toString() {
        return "Project{" + "dbProject=" + dbProject + '}';
    }

}
