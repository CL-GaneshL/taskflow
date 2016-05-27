/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbProjectSkill;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class ProjectSkill {

    /**
     *
     */
    private final DbProjectSkill dbProjectSkill;

    public ProjectSkill(DbProjectSkill dbProjectSkill) {
        this.dbProjectSkill = dbProjectSkill;
    }

    public Integer getSkillId() {
        return dbProjectSkill.getSkill_id();
    }

    public Integer getProjectId() {
        return dbProjectSkill.getProject_id();
    }

    public Integer getDuration() {
        return dbProjectSkill.getDuration();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.dbProjectSkill);
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
        final ProjectSkill other = (ProjectSkill) obj;
        if (!Objects.equals(this.dbProjectSkill, other.dbProjectSkill)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjectSkill = {" + dbProjectSkill + '}';
    }

}
