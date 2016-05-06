/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TEMPLATE_DESIGNATION_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TEMPLATE_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TEMPLATE_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TEMPLATE_OPEN_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TEMPLATE_REFERENCE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TEMPLATE_TABLE_NAME;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author wdmtraining
 */
@Entity(name = TEMPLATE_ENTITY_NAME)
@Table(name = TEMPLATE_TABLE_NAME)
@SuppressWarnings("ValidAttributes")
public class DbTemplate implements Serializable {

    private static final long serialVersionUID = -3756823678114106052L;

    /**
     * Index of the task in its database table.
     */
    @Id
    @Min(value = 1)
    @Column(name = TEMPLATE_ID_COL_NAME, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * template id
     */
    @NotNull
    @Column(name = TEMPLATE_REFERENCE_COL_NAME, insertable = true, updatable = true)
    private String reference;

    /**
     * template id
     */
    @NotNull
    @Min(value = 1)
    @Column(name = TEMPLATE_DESIGNATION_COL_NAME, insertable = true, updatable = true)
    private String designation;

    /**
     * open
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = TEMPLATE_OPEN_COL_NAME, insertable = true, updatable = true)
    private Byte open;

    /**
     * Default constructor.
     */
    public DbTemplate() {
    }

    public DbTemplate(
            final String reference,
            final String designation,
            final Byte open
    ) {
        this.reference = reference;
        this.designation = designation;
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

    public Byte getOpen() {
        return open;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.reference);
        hash = 37 * hash + Objects.hashCode(this.designation);
        hash = 37 * hash + Objects.hashCode(this.open);
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
        final DbTemplate other = (DbTemplate) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.reference, other.reference)) {
            return false;
        }
        if (!Objects.equals(this.designation, other.designation)) {
            return false;
        }
        return Objects.equals(this.open, other.open);
    }

    @Override
    public String toString() {
        return "DbTemplate{" + "id=" + id + ", reference=" + reference + ", designation=" + designation + ", open=" + open + '}';
    }

}
