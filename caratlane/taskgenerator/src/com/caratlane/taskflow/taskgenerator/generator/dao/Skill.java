/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dao;

import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbSkill;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class Skill {

    /**
     *
     */
    private final DbSkill dbSkill;

    public Skill(DbSkill dbSkill) {
        this.dbSkill = dbSkill;
    }

    public Skill(
            final String reference,
            final String designation,
            final Integer duration,
            final Byte open) {
        this.dbSkill = new DbSkill(reference, designation, duration, open);
    }

    public Skill(
            final Integer id,
            final String reference,
            final String designation,
            final Integer duration,
            final Byte open) {
        this.dbSkill = new DbSkill(id, reference, designation, duration, open);
    }

    public Integer getId() {
        return dbSkill.getId();
    }

    public String getReference() {
        return dbSkill.getReference();
    }

    public String getDesignation() {
        return dbSkill.getDesignation();
    }

    public Integer getDuration() {
        return dbSkill.getDuration();
    }

    public Byte getOpen() {
        return dbSkill.getOpen();
    }

    public DbSkill getDbSkill() {
        return dbSkill;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.dbSkill);
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
        final Skill other = (Skill) obj;
        return Objects.equals(this.dbSkill, other.dbSkill);
    }

    @Override
    public String toString() {
        return "Skill{" + "dbSkill=" + dbSkill + '}';
    }

}
