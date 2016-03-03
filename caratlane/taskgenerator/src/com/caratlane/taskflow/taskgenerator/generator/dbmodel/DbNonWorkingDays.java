/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.FIND_NWD_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.FIND_NWD_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_AFTERNOON_SHIFT_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_DATE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_MORNING_SHIFT_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_TABLE_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_TITLE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_TYPE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_TYPE_NON_WORKING;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_TYPE_WEEKEND;
import com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.NWDType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Entity(name = NWD_ENTITY_NAME)
@Table(name = NWD_TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = NWD_ENTITY_NAME + FIND_NWD_SUFFIX, query = FIND_NWD_QUERY),})
@SuppressWarnings("ValidAttributes")
public class DbNonWorkingDays implements Serializable {

    /**
     * Index of the task in its database table.
     */
    @Id
    @Min(value = 1)
    @Column(name = NWD_ID_COL_NAME, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * title
     */
    @NotNull
    @Column(name = NWD_TITLE_COL_NAME, insertable = false, updatable = false)
    private String title;

    /**
     * type
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = NWD_TYPE_COL_NAME,
            columnDefinition = "enum('" + NWD_TYPE_WEEKEND + "','" + NWD_TYPE_NON_WORKING + "')",
            insertable = false,
            updatable = false
    )
    private NWDType type;

    /**
     * title
     */
    @NotNull
    @Column(name = NWD_DATE_COL_NAME, insertable = false, updatable = false)
    private Date date;

    /**
     * morning shift
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = NWD_MORNING_SHIFT_COL_NAME, insertable = true, updatable = false)
    private Byte morning_shift;

    /**
     * afternoon shift
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = NWD_AFTERNOON_SHIFT_COL_NAME, insertable = true, updatable = false)
    private Byte afternoon_shift;

    /**
     * Default constructor.
     */
    public DbNonWorkingDays() {
    }

    public DbNonWorkingDays(String title, NWDType type, Date date, Byte morning_shift, Byte afternoon_shift) {
        this.id = 0;
        this.title = title;
        this.type = type;
        this.date = date;
        this.morning_shift = morning_shift;
        this.afternoon_shift = afternoon_shift;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public NWDType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public Byte getMorning_shift() {
        return morning_shift;
    }

    public Byte getAfternoon_shift() {
        return afternoon_shift;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.title);
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + Objects.hashCode(this.date);
        hash = 37 * hash + Objects.hashCode(this.morning_shift);
        hash = 37 * hash + Objects.hashCode(this.afternoon_shift);
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
        final DbNonWorkingDays other = (DbNonWorkingDays) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.morning_shift, other.morning_shift)) {
            return false;
        }
        return Objects.equals(this.afternoon_shift, other.afternoon_shift);
    }

    @Override
    public String toString() {
        return "DbNonWorkingDays{" + "id=" + id + ", title=" + title + ", type=" + type + ", date=" + date + ", morning_shift=" + morning_shift + ", afternoon_shift=" + afternoon_shift + '}';
    }

}
