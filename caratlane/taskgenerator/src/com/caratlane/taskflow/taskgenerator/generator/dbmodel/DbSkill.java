/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_DESIGNATION_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_DURATION_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_OPEN_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_REFERENCE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_TABLE_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_SKILLS_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_SKILLS_SUFFIX;
import java.io.Serializable;
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
@Entity(name = SKILL_ENTITY_NAME)
@Table(name = SKILL_TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = SKILL_ENTITY_NAME + FIND_SKILLS_SUFFIX, query = FIND_SKILLS_QUERY),})
@SuppressWarnings("ValidAttributes")
public class DbSkill implements Serializable {

    private static final long serialVersionUID = -5148545953159306838L;

    /**
     * Index of the task in its database table.
     */
    @Id
    @Min(value = 1)
    @Column(name = SKILL_ID_COL_NAME, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * reference
     */
    @NotNull
    @Column(name = SKILL_REFERENCE_COL_NAME, insertable = true, updatable = true)
    private String reference;

    /**
     * reference
     */
    @NotNull
    @Column(name = SKILL_DESIGNATION_COL_NAME, insertable = true, updatable = true)
    private String designation;

    /**
     * duration
     */
    @NotNull
    @Min(value = 0)
    @Column(name = SKILL_DURATION_COL_NAME, insertable = true, updatable = true)
    private Integer duration;

    /**
     * open
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = SKILL_OPEN_COL_NAME, insertable = true, updatable = true)
    private Byte open;

    /**
     * Default constructor.
     */
    public DbSkill() {
    }

    /**
     *
     * @param reference
     * @param designation
     * @param duration
     * @param open
     */
    public DbSkill(
            final String reference,
            final String designation,
            final Integer duration,
            final Byte open) {
        this.reference = reference;
        this.designation = designation;
        this.duration = duration;
        this.open = open;
    }

    /**
     *
     * @param id
     * @param reference
     * @param designation
     * @param duration
     * @param open
     */
    public DbSkill(
            final Integer id,
            final String reference,
            final String designation,
            final Integer duration,
            final Byte open) {
        this.id = id;
        this.reference = reference;
        this.designation = designation;
        this.duration = duration;
        this.open = open;
    }

    public Integer getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public String getDesignation() {
        return designation;
    }

    public Integer getDuration() {
        return duration;
    }

    public Byte getOpen() {
        return open;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.reference);
        hash = 29 * hash + Objects.hashCode(this.designation);
        hash = 29 * hash + Objects.hashCode(this.duration);
        hash = 29 * hash + Objects.hashCode(this.open);
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
        final DbSkill other = (DbSkill) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.reference, other.reference)) {
            return false;
        }
        if (!Objects.equals(this.designation, other.designation)) {
            return false;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        return Objects.equals(this.open, other.open);
    }

    @Override
    public String toString() {
        return "DbSkill{" + "id=" + id + ", reference=" + reference + ", designation=" + designation + ", duration=" + duration + ", open=" + open + '}';
    }

}
