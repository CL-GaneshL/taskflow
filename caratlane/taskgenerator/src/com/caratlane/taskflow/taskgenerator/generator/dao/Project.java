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
     * @param reference
     * @param template_id
     * @param nb_products
     * @param priority
     * @param start_date
     * @param end_date
     * @param open
     */
    public Project(
            final Integer project_id,
            final String reference,
            final Integer template_id,
            final Integer nb_products,
            final Integer priority,
            final Date start_date,
            final Date end_date,
            final Byte open
    ) {

        this.dbProject = new DbProject(
                project_id,
                reference,
                template_id,
                nb_products,
                priority,
                start_date,
                end_date,
                open
        );
    }

    /**
     *
     * @param reference
     * @param template_id
     * @param nb_products
     * @param priority
     * @param start_date
     * @param end_date
     * @param open
     */
    public Project(
            final String reference,
            final Integer template_id,
            final Integer nb_products,
            final Integer priority,
            final Date start_date,
            final Date end_date,
            final Byte open
    ) {

        this.dbProject = new DbProject(
                reference,
                template_id,
                nb_products,
                priority,
                start_date,
                end_date,
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

    public String getReference() {
        return dbProject.getReference();
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
    public LocalDateTime getEndDate() {

        final Date end_date = this.dbProject.getEnd_date();
        final LocalDateTime end_date_ld
                = end_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return end_date_ld;
    }

    /**
     *
     * @param l_end_date
     */
    public void setEndDate(LocalDateTime l_end_date) {
        final Date end_date = Date.from(l_end_date.atZone(ZoneId.systemDefault()).toInstant());
        dbProject.setEnd_date(end_date);
    }

    /**
     * For test purposes only.
     *
     * @param test
     * @return
     */
    public Date getStartDate(boolean test) {
        return this.dbProject.getStart_date();
    }

    public Date getEndDate(boolean test) {
        return this.dbProject.getEnd_date();
    }

    public DbProject getDbProject() {
        return dbProject;
    }

    /**
     *
     * @return
     */
    public Boolean getOpen() {
        return this.dbProject.getOpen() == 1;
    }

    /**
     * For test purposes only.
     *
     * @param test
     * @return
     */
    public Byte getOpen(boolean test) {
        return this.dbProject.getOpen();
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
