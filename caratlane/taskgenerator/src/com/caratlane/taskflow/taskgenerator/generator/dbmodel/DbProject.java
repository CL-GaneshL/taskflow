/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_END_DATE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_NB_PRODUCTS_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_OPEN_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_PRIORITY_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_REFERENCE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_START_DATE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_TABLE_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_TEMPLATE_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_OPEN_PROJECTS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_PROJECT_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.PROJECT_FIND_OPEN_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.PROJECT_FIND_QUERY;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author wdmtraining
 */
@Entity(name = PROJECT_ENTITY_NAME)
@Table(name = PROJECT_TABLE_NAME)
@NamedQueries(
        {
            @NamedQuery(name = PROJECT_ENTITY_NAME + FIND_PROJECT_SUFFIX, query = PROJECT_FIND_QUERY),
            @NamedQuery(name = PROJECT_ENTITY_NAME + FIND_OPEN_PROJECTS_SUFFIX, query = PROJECT_FIND_OPEN_QUERY)
        }
)
@SuppressWarnings("ValidAttributes")
public class DbProject implements Serializable {

    /**
     * Index of the task in its database table.
     */
    @Id
    @Min(value = 1)
    @Column(name = PROJECT_ID_COL_NAME, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * template id
     */
    @NotNull
    @Column(name = PROJECT_REFERENCE_COL_NAME, insertable = true, updatable = true)
    private String reference;

    /**
     * template id
     */
    @NotNull
    @Min(value = 1)
    @Column(name = PROJECT_TEMPLATE_ID_COL_NAME, insertable = true, updatable = true)
    private Integer template_id;

    /**
     * nb products
     */
    @NotNull
    @Min(value = 1)
    @Column(name = PROJECT_NB_PRODUCTS_COL_NAME, insertable = true, updatable = true)
    private Integer nb_products;

    /**
     * priority
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    @Column(name = PROJECT_PRIORITY_COL_NAME, insertable = true, updatable = true)
    private Integer priority;

    /**
     * start date
     */
    @NotNull
    @Column(name = PROJECT_START_DATE_COL_NAME, insertable = true, updatable = true)
    private Date start_date;

    /**
     * end date
     *
     * Insertable = false in order to use the column null value by default.
     */
    @NotNull
    @Column(name = PROJECT_END_DATE_COL_NAME, insertable = false, updatable = true)
    private Date end_date;

    /**
     * open
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = PROJECT_OPEN_COL_NAME, insertable = true, updatable = true)
    private Byte open;

    /**
     * Default constructor.
     */
    public DbProject() {
    }

    /**
     * For testing purposes only.
     *
     * @param project_id
     * @param reference
     * @param template_id
     * @param nb_products
     * @param priority
     * @param start_date
     * @param end_date
     * @param open
     */
    public DbProject(
            final Integer project_id,
            final String reference,
            final Integer template_id,
            final Integer nb_products,
            final Integer priority,
            final Date start_date,
            final Date end_date,
            final Byte open
    ) {
        this.id = project_id;
        this.reference = reference;
        this.template_id = template_id;
        this.nb_products = nb_products;
        this.priority = priority;
        this.start_date = start_date;
        this.end_date = end_date;
        this.open = open;
    }

    /**
     * For testing purposes only.
     *
     * @param reference
     * @param template_id
     * @param nb_products
     * @param priority
     * @param start_date
     * @param end_date
     * @param open
     */
    public DbProject(
            final String reference,
            final Integer template_id,
            final Integer nb_products,
            final Integer priority,
            final Date start_date,
            final Date end_date,
            final Byte open
    ) {
        this.reference = reference;
        this.template_id = template_id;
        this.nb_products = nb_products;
        this.priority = priority;
        this.start_date = start_date;
        this.end_date = end_date;
        this.open = open;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    /**
     *
     * @return
     */
    public Integer getTemplate_id() {
        return template_id;
    }

    /**
     *
     * @return
     */
    public Integer getNb_products() {
        return nb_products;
    }

    /**
     *
     * @return
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     *
     * @return
     */
    public Date getStart_date() {
        return start_date;
    }

    /**
     *
     * @return
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     *
     * @return
     */
    public Byte getOpen() {
        return open;
    }

    /**
     *
     * @param end_date
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.reference);
        hash = 37 * hash + Objects.hashCode(this.template_id);
        hash = 37 * hash + Objects.hashCode(this.nb_products);
        hash = 37 * hash + Objects.hashCode(this.priority);
        hash = 37 * hash + Objects.hashCode(this.start_date);
        hash = 37 * hash + Objects.hashCode(this.end_date);
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
        final DbProject other = (DbProject) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.reference, other.reference)) {
            return false;
        }
        if (!Objects.equals(this.template_id, other.template_id)) {
            return false;
        }
        if (!Objects.equals(this.nb_products, other.nb_products)) {
            return false;
        }
        if (!Objects.equals(this.priority, other.priority)) {
            return false;
        }
        if (!Objects.equals(this.start_date, other.start_date)) {
            return false;
        }
        if (!Objects.equals(this.end_date, other.end_date)) {
            return false;
        }
        return Objects.equals(this.open, other.open);
    }

    @Override
    public String toString() {
        return "DbProject{" + "id=" + id + ", reference=" + reference + ", template_id=" + template_id + ", nb_products=" + nb_products + ", priority=" + priority + ", start_date=" + start_date + ", end_date=" + end_date + ", open=" + open + '}';
    }

}
