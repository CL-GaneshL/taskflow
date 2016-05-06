/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_SKILL_DURATION_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_SKILL_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_SKILL_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_SKILL_PROJECT_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_SKILL_TABLE_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_PROJECT_SKILLS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.PROJECT_SKILL_FIND_QUERY;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity(name = PROJECT_SKILL_ENTITY_NAME)
@Table(name = PROJECT_SKILL_TABLE_NAME)
@NamedQueries({
    @NamedQuery(
            name = PROJECT_SKILL_ENTITY_NAME + FIND_PROJECT_SKILLS_SUFFIX,
            query = PROJECT_SKILL_FIND_QUERY
    )
})
public class DbProjectSkill implements Serializable {

    private static final long serialVersionUID = 8056771753760415576L;

    /**
     * skill id
     */
    @Id
    @NotNull
    @Min(value = 1)
    @Column(name = PROJECT_SKILL_ID_COL_NAME, insertable = false, updatable = false)
    private Integer skill_id;

    /**
     * project id
     */
    @Id
    @NotNull
    @Min(value = 1)
    @Column(name = PROJECT_SKILL_PROJECT_ID_COL_NAME, insertable = false, updatable = false)
    private Integer project_id;

    /**
     * duration
     */
    @Id
    @NotNull
    @Min(value = 0)
    @Column(name = PROJECT_SKILL_DURATION_COL_NAME, insertable = false, updatable = false)
    private Integer duration;

    /**
     * Default constructor.
     */
    public DbProjectSkill() {
    }

    public DbProjectSkill(Integer skill_id, Integer project_id, Integer duration) {
        this.skill_id = skill_id;
        this.project_id = project_id;
        this.duration = duration;
    }

    public Integer getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(Integer skill_id) {
        this.skill_id = skill_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.skill_id);
        hash = 71 * hash + Objects.hashCode(this.project_id);
        hash = 71 * hash + Objects.hashCode(this.duration);
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
        final DbProjectSkill other = (DbProjectSkill) obj;
        if (!Objects.equals(this.skill_id, other.skill_id)) {
            return false;
        }
        if (!Objects.equals(this.project_id, other.project_id)) {
            return false;
        }
        return Objects.equals(this.duration, other.duration);
    }

    @Override
    public String toString() {
        return "DbProjectSkill{" + "skill_id=" + skill_id + ", project_id=" + project_id + ", duration=" + duration + '}';
    }

}
