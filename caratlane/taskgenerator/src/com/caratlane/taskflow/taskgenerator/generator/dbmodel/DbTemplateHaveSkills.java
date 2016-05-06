/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TEMPLATE_HAVE_SKILLS_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TEMPLATE_HAVE_SKILLS_SKILL_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TEMPLATE_HAVE_SKILLS_TABLE_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TEMPLATE_HAVE_SKILLS_TEMPLATE_ID_COL_NAME;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author wdmtraining
 */
@Entity(name = TEMPLATE_HAVE_SKILLS_ENTITY_NAME)
@Table(name = TEMPLATE_HAVE_SKILLS_TABLE_NAME)
public class DbTemplateHaveSkills implements Serializable {

    private static final long serialVersionUID = -4055199339046083932L;

    /**
     * Index of the task in its database table.
     */
    @Id
    @NotNull
    @Min(value = 1)
    @Column(name = TEMPLATE_HAVE_SKILLS_TEMPLATE_ID_COL_NAME, insertable = true, updatable = false)
    private Integer template_id;

    /**
     * template id
     */
    @Id
    @NotNull
    @Min(value = 1)
    @Column(name = TEMPLATE_HAVE_SKILLS_SKILL_ID_COL_NAME, insertable = true, updatable = true)
    private Integer skill_id;

    /**
     * Default constructor.
     */
    public DbTemplateHaveSkills() {
    }

    public DbTemplateHaveSkills(Integer template_id, Integer skill_id) {
        this.template_id = template_id;
        this.skill_id = skill_id;
    }

    public Integer getTemplate_id() {
        return template_id;
    }

    public Integer getSkill_id() {
        return skill_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.template_id);
        hash = 37 * hash + Objects.hashCode(this.skill_id);
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
        final DbTemplateHaveSkills other = (DbTemplateHaveSkills) obj;
        if (!Objects.equals(this.template_id, other.template_id)) {
            return false;
        }
        return Objects.equals(this.skill_id, other.skill_id);
    }

    @Override
    public String toString() {
        return "DbTemplateHaveSkills{" + "template_id=" + template_id + ", skill_id=" + skill_id + '}';
    }

}
