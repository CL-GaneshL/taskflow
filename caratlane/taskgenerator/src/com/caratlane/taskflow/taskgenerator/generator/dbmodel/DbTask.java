/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_PROJECT_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_SKILL_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_TABLE_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_PROJECT_TASKS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_PROJECT_TASKS_QUERY;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author wdmtraining
 */
@Entity(name = TASK_ENTITY_NAME)
@Table(name = TASK_TABLE_NAME)
@NamedQueries({
    @NamedQuery(
            name = TASK_ENTITY_NAME + FIND_PROJECT_TASKS_SUFFIX,
            query = FIND_PROJECT_TASKS_QUERY
    )
})
@SuppressWarnings("ValidAttributes")
public class DbTask implements Serializable {

    /**
     * Index of the task in its database table.
     */
    @Id
    @NotNull
    @Min(value = 1)
    @Column(name = TASK_ID_COL_NAME, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * skill id
     */
    @NotNull
    @Min(value = 1)
    @Column(name = TASK_SKILL_ID_COL_NAME, insertable = true, updatable = false)
    private Integer skill_id;

    /**
     * project id
     */
    @NotNull
    @Min(value = 1)
    @Column(name = TASK_PROJECT_ID_COL_NAME, insertable = true, updatable = false)
    private Integer project_id;

    /**
     * Default constructor.
     */
    public DbTask() {
    }

    public DbTask(
            Integer skill_id,
            Integer project_id
    ) {
        this.skill_id = skill_id;
        this.project_id = project_id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSkill_id() {
        return skill_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.skill_id);
        hash = 53 * hash + Objects.hashCode(this.project_id);
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
        final DbTask other = (DbTask) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.skill_id, other.skill_id)) {
            return false;
        }
        return Objects.equals(this.project_id, other.project_id);
    }

}
