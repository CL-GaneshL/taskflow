/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_DURATION_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_OPEN_COL_NAME;
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

    /**
     * Index of the task in its database table.
     */
    @Id
    @Min(value = 1)
    @Column(name = SKILL_ID_COL_NAME, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * duration
     */
    @NotNull
    @Min(value = 0)
    @Column(name = SKILL_DURATION_COL_NAME, insertable = false, updatable = false)
    private Integer duration;

    /**
     * open
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = SKILL_OPEN_COL_NAME, insertable = false, updatable = false)
    private Byte open;

    /**
     * Default constructor.
     */
    public DbSkill() {
    }

    public DbSkill(Integer duration, Byte open) {
        this.id = 0;
        this.duration = duration;
        this.open = open;
    }

    public DbSkill(Integer id, Integer duration, Byte open) {
        this.id = id;
        this.duration = duration;
        this.open = open;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDuration() {
        return duration;
    }

    public Byte getOpen() {
        return open;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.duration);
        hash = 97 * hash + Objects.hashCode(this.open);
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
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        return Objects.equals(this.open, other.open);
    }

    @Override
    public String toString() {
        return "DbSkill{" + "id=" + id + ", duration=" + duration + ", open=" + open + '}';
    }

}
