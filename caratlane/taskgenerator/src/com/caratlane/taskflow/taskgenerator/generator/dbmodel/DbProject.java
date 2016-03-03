/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.FIND_OPEN_PROJECTS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.FIND_PROJECT_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_FIND_OPEN_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_FIND_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_NB_PRODUCTS_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_OPEN_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_PRIORITY_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_START_DATE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_TABLE_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_TEMPLATE_ID_COL_NAME;
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
    @Column(name = PROJECT_ID_COL_NAME, insertable = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * template id
     */
    @NotNull
    @Min(value = 1)
    @Column(name = PROJECT_TEMPLATE_ID_COL_NAME, insertable = true, updatable = false)
    private Integer template_id;

    /**
     * template id
     */
    @NotNull
    @Min(value = 1)
    @Column(name = PROJECT_NB_PRODUCTS_COL_NAME, insertable = true, updatable = false)
    private Integer nb_products;

    /**
     * priority
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    @Column(name = PROJECT_PRIORITY_COL_NAME, insertable = true, updatable = false)
    private Integer priority;

    /**
     * start date
     */
    @NotNull
    @Column(name = PROJECT_START_DATE_COL_NAME, insertable = true, updatable = false)
    private Date start_date;

    /**
     * open
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = PROJECT_OPEN_COL_NAME, insertable = true, updatable = false)
    private Byte open;

    /**
     * Default constructor.
     */
    public DbProject() {
    }

    /**
     * For testing purposes only.
     * @param project_id
     * @param template_id
     * @param nb_products
     * @param priority
     * @param start_date
     * @param open
     */
    public DbProject(
            Integer project_id,
            Integer template_id,
            Integer nb_products,
            Integer priority,
            Date start_date,
            Byte open
    ) {
        this.id = project_id;
        this.template_id = template_id;
        this.nb_products = nb_products;
        this.priority = priority;
        this.start_date = start_date;
        this.open = open;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
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
    public Byte getOpen() {
        return open;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.template_id);
        hash = 67 * hash + Objects.hashCode(this.nb_products);
        hash = 67 * hash + Objects.hashCode(this.priority);
        hash = 67 * hash + Objects.hashCode(this.start_date);
        hash = 67 * hash + Objects.hashCode(this.open);
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
        return Objects.equals(this.open, other.open);
    }

    @Override
    public String toString() {
        return "DbProject{" + "id=" + id + ", template_id=" + template_id + ", nb_products=" + nb_products + ", priority=" + priority + ", start_date=" + start_date + ", open=" + open + '}';
    }

}
