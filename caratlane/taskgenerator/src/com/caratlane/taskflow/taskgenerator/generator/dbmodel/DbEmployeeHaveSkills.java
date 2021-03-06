/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_HAVE_SKILLS_EMPLOYEE_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_HAVE_SKILLS_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_HAVE_SKILLS_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_HAVE_SKILLS_TABLE_NAME;
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
@Entity(name = EMPLOYEE_HAVE_SKILLS_ENTITY_NAME)
@Table(name = EMPLOYEE_HAVE_SKILLS_TABLE_NAME)
public class DbEmployeeHaveSkills implements Serializable {

    private static final long serialVersionUID = 5598298539897382250L;

    /**
     * employee id
     */
    @Id
    @NotNull
    @Min(value = 1)
    @Column(name = EMPLOYEE_HAVE_SKILLS_EMPLOYEE_ID_COL_NAME, insertable = true, updatable = true)
    private Integer employee_id;

    /**
     * skill id
     */
    @Id
    @NotNull
    @Min(value = 1)
    @Column(name = EMPLOYEE_HAVE_SKILLS_ID_COL_NAME, insertable = true, updatable = true)
    private Integer skill_id;

    /**
     * Default constructor.
     */
    public DbEmployeeHaveSkills() {
    }

    public DbEmployeeHaveSkills(
            final Integer employee_id,
            final Integer skill_id
    ) {
        this.employee_id = employee_id;
        this.skill_id = skill_id;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public Integer getSkill_id() {
        return skill_id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.employee_id);
        hash = 47 * hash + Objects.hashCode(this.skill_id);
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
        final DbEmployeeHaveSkills other = (DbEmployeeHaveSkills) obj;
        if (!Objects.equals(this.employee_id, other.employee_id)) {
            return false;
        }
        return Objects.equals(this.skill_id, other.skill_id);
    }

    @Override
    public String toString() {
        return "DbEmployeeSkill{" + "employee_id=" + employee_id + ", skill_id=" + skill_id + '}';
    }

}
