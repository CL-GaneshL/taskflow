/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.EMPLOYEE_SKILL_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.HOLIDAY_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.NWD_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.PROJECT_SKILL_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.SKILL_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ALLOCATION_ENTITY_NAME;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbConstants.TASK_ENTITY_NAME;

/**
 *
 * @author wdmtraining
 */
public interface DbQueries {

    public static final String FIND_PROJECT_SUFFIX = ".findProjects";
    public static final String FIND_OPEN_PROJECTS_SUFFIX = ".findOpenProjects";
    public static final String FIND_PROJECT_SKILLS_SUFFIX = ".findProjectSkills";

    public static final String FIND_NWD_SUFFIX = ".findNonWorkingDays";
    public static final String FIND_SKILLS_SUFFIX = ".findSkills";

    public static final String FIND_EMPLOYEE_SUFFIX = ".findEmployee";
    public static final String FIND_ALL_EMPLOYEES_SUFFIX = ".findAllEmployees";
    public static final String FIND_EMPLOYEE_HOLIDAYS_SUFFIX = ".findEmployeeHolidays";
    public static final String FIND_EMPLOYEE_SKILLS_SUFFIX = ".findEmployeeSkills";

    public static final String FIND_PROJECT_TASKS_SUFFIX = ".findProjectTasks";
    public static final String FIND_TASK_EMPLOYEE_TASK_ALLOCATIONS_SUFFIX = ".findTaskEmployeeTaskAllocations";
    public static final String FIND_EMPLOYEE_TASK_ALLOCATIONS_SUFFIX = ".findEmployeeTaskAllocations";
    public static final String DELETE_TASK_ALLOCATIONS_SUFFIX = ".deleteTaskAllocations";

    // =====================================================
    // - project entities
    // =====================================================
    String PROJECT_FIND_OPEN_QUERY
            = "SELECT p FROM " + PROJECT_ENTITY_NAME + " p "
            + "WHERE p.open  = :open";

    String PROJECT_FIND_QUERY
            = "SELECT p FROM " + PROJECT_ENTITY_NAME + " p "
            + "WHERE p.id = :project_id";

    // =====================================================
    // - projects' skills entities
    // =====================================================
    String PROJECT_SKILL_FIND_QUERY
            = "SELECT ps FROM " + PROJECT_SKILL_ENTITY_NAME + " ps "
            + " WHERE ps.project_id = :project_id";

    // =====================================================
    // - non working days entities
    // =====================================================
    String FIND_NWD_QUERY = "SELECT nwd FROM " + NWD_ENTITY_NAME + " nwd "
            + " WHERE nwd.date BETWEEN :now AND :max_date";

    // =====================================================
    // - holidays
    // =====================================================
    String FIND_EMPLOYEES_HOLIDAY_QUERY
            = "SELECT h FROM " + HOLIDAY_ENTITY_NAME + " h "
            + "WHERE (h.employee_id = :employee_id)"
            + "AND (h.start_date BETWEEN :now AND :max_date)";

    // =====================================================
    // - skills
    // =====================================================
    String FIND_SKILLS_QUERY
            = "SELECT s FROM " + SKILL_ENTITY_NAME + " s "
            + "WHERE s.open = :open";

    // =====================================================
    // - employee's skills
    // =====================================================
    String FIND_EMPLOYEE_SKILLS_QUERY
            = "SELECT es FROM " + EMPLOYEE_SKILL_ENTITY_NAME + " es "
            + "WHERE es.employee_id = :employee_id";

    // =====================================================
    // - employees
    // =====================================================
    String FIND_ALL_EMPLOYEES_QUERY
            = "SELECT e FROM " + EMPLOYEE_ENTITY_NAME + " e ";

    String FIND_EMPLOYEE_QUERY
            = "SELECT e FROM " + EMPLOYEE_ENTITY_NAME + " e "
            + "WHERE e.id = :employee_id";

    // =====================================================
    // - Task entities
    // =====================================================
    String FIND_PROJECT_TASKS_QUERY
            = "SELECT t FROM " + TASK_ENTITY_NAME + " t "
            + "WHERE t.project_id = :project_id";

    // =====================================================
    // - Task allocations entities
    // =====================================================
    String FIND_TASK_EMPLOYEE_TASK_ALLOCATIONS_QUERY
            = "SELECT ta FROM " + TASK_ALLOCATION_ENTITY_NAME + " ta "
            + " WHERE ( ta.task_id = :task_id )"
            + " AND ( ta.employee_id = :employee_id )";

    String FIND_EMPLOYEE_TASK_ALLOCATIONS_QUERY
            = "SELECT ta FROM " + TASK_ALLOCATION_ENTITY_NAME + " ta "
            + " WHERE  ta.employee_id = :employee_id";

    String DELETE_TASK_ALLOCATIONS_QUERY
            = "DELETE FROM " + TASK_ALLOCATION_ENTITY_NAME + " ta "
            + " WHERE  ta.task_id = :task_id";

}
