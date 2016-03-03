/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbEmployeeSkill;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class EmployeeSkill {

    /**
     *
     */
    private final DbEmployeeSkill dbEmployeeSkill;

    public EmployeeSkill(DbEmployeeSkill dbEmployeeSkill) {
        this.dbEmployeeSkill = dbEmployeeSkill;
    }

    public Integer getEmployeeId() {
        return dbEmployeeSkill.getEmployee_id();
    }

    public Integer getSkillId() {
        return dbEmployeeSkill.getSkill_id();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.dbEmployeeSkill);
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
        final EmployeeSkill other = (EmployeeSkill) obj;
        return Objects.equals(this.dbEmployeeSkill, other.dbEmployeeSkill);
    }

    @Override
    public String toString() {
        return "EmployeeSkill{" + "dbEmployeeSkill=" + dbEmployeeSkill + '}';
    }

}
