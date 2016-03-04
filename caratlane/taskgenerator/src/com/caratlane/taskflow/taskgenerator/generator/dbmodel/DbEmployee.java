/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_EMPLOYEEID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_EMPLOYEMENT_TYPE_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_EMPLOYEMENT_TYPE_FTE;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_EMPLOYEMENT_TYPE_INTERN;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_ID_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_PRODUCTIVITY_COL_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_TABLE_NAME;
import com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.EmployementType;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_ALL_EMPLOYEES_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_ALL_EMPLOYEES_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_EMPLOYEE_QUERY;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_EMPLOYEE_SUFFIX;
import java.io.Serializable;
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
@Entity(name = EMPLOYEE_ENTITY_NAME)
@Table(name = EMPLOYEE_TABLE_NAME)
@NamedQueries(
        {
            @NamedQuery(name = EMPLOYEE_ENTITY_NAME + FIND_EMPLOYEE_SUFFIX, query = FIND_EMPLOYEE_QUERY),
            @NamedQuery(name = EMPLOYEE_ENTITY_NAME + FIND_ALL_EMPLOYEES_SUFFIX, query = FIND_ALL_EMPLOYEES_QUERY)
        }
)
public class DbEmployee implements Serializable {

    /**
     * Index of the task in its database table.
     */
    @Id
    @Min(value = 1)
    @Column(name = EMPLOYEE_ID_COL_NAME, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * employeeId
     */
    @NotNull
    @Column(name = EMPLOYEE_EMPLOYEEID_COL_NAME, insertable = true, updatable = false)
    private String employeeId;

    /**
     * productivity
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 8)
    @Column(name = EMPLOYEE_PRODUCTIVITY_COL_NAME, insertable = true, updatable = false)
    private Double productivity;

    /**
     * employement type
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = EMPLOYEE_EMPLOYEMENT_TYPE_COL_NAME,
            columnDefinition = "enum('" + EMPLOYEE_EMPLOYEMENT_TYPE_FTE + "','" + EMPLOYEE_EMPLOYEMENT_TYPE_INTERN + "')",
            insertable = true,
            updatable = false
    )
    private EmployementType employement_type;

    /**
     * morning shift
     */
//    @NotNull
//    @Min(value = 0)
//    @Max(value = 1)
//    @Column(name = EMPLOYEE_MORNING_SHIFT_COL_NAME, insertable = true, updatable = false)
//    private Byte morning_shift;
    /**
     * Default constructor.
     */
    public DbEmployee() {
    }

    /**
     *
     * @param employeeId
     * @param productivity
     * @param employement_type
     * @param morning_shift
     */
    public DbEmployee(
            String employeeId,
            Double productivity,
            EmployementType employement_type
    //            Byte morning_shift
    ) {
        this.employeeId = employeeId;
        this.productivity = productivity;
        this.employement_type = employement_type;
//        this.morning_shift = morning_shift;
    }

    /**
     * For testing purpose.
     *
     * @param id
     * @param employeeId
     * @param productivity
     * @param employement_type
     * @param morning_shift
     */
    public DbEmployee(
            Integer id,
            String employeeId,
            Double productivity,
            EmployementType employement_type
    //            Byte morning_shift
    ) {
        this.id = id;
        this.employeeId = employeeId;
        this.productivity = productivity;
        this.employement_type = employement_type;
//        this.morning_shift = morning_shift;
    }

    public Integer getId() {
        return id;
    }

    public Double getProductivity() {
        return productivity;
    }

    public EmployementType getEmployement_type() {
        return employement_type;
    }

    public String getEmployeeId() {
        return employeeId;
    }

//    public Byte getMorning_shift() {
//        return morning_shift;
//    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.employeeId);
        hash = 97 * hash + Objects.hashCode(this.productivity);
        hash = 97 * hash + Objects.hashCode(this.employement_type);
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
        final DbEmployee other = (DbEmployee) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.employeeId, other.employeeId)) {
            return false;
        }
        if (!Objects.equals(this.productivity, other.productivity)) {
            return false;
        }
        return this.employement_type == other.employement_type;
    }

    @Override
    public String toString() {
        return "DbEmployee{" + "id=" + id + ", employeeId=" + employeeId + ", productivity=" + productivity + ", employement_type=" + employement_type + '}';
    }

}
