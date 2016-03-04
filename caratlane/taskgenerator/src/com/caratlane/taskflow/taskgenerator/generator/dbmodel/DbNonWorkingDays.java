/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_DATE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_TABLE_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_TITLE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_TYPE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_NWD_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_NWD_SUFFIX;
import java.io.Serializable;
import java.util.Date;
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
    @Column(name = NWD_TYPE_COL_NAME, insertable = false, updatable = false)
    private String type;

    /**
     * title
     */
    @NotNull
    @Column(name = NWD_DATE_COL_NAME, insertable = false, updatable = false)
    private Date date;

    /**
     * Default constructor.
     */
    public DbNonWorkingDays() {
    }

    /**
     *
     * @param title
     * @param type
     * @param date
     */
    public DbNonWorkingDays(String title, String type, Date date) {
        this.id = 0;
        this.title = title;
        this.type = type;
        this.date = date;

    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.title);
        hash = 23 * hash + Objects.hashCode(this.type);
        hash = 23 * hash + Objects.hashCode(this.date);
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
        if (!this.type.equals(other.type)) {
            return false;
        }
        return Objects.equals(this.date, other.date);
    }

    @Override
    public String toString() {
        return "DbNonWorkingDays{" + "id=" + id + ", title=" + title + ", type=" + type + ", date=" + date + '}';
    }

}
