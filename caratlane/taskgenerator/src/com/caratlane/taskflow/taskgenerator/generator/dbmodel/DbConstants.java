/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.dbmodel;

/**
 *
 * @author wdmtraining
 */
public interface DbConstants {

    public static final String FIND_PROJECT_SUFFIX = ".findProjects";
    public static final String FIND_OPEN_PROJECTS_SUFFIX = ".findOpenProjects";
    public static final String FIND_PROJECT_SKILLS_SUFFIX = ".findProjectSkills";

    public static final String FIND_NWD_SUFFIX = ".findNonWorkingDays";
    public static final String FIND_OPEN_SKILLS_SUFFIX = ".findOpenSkills";

    public static final String FIND_EMPLOYEE_SUFFIX = ".findEmployee";
    public static final String FIND_ALL_EMPLOYEES_SUFFIX = ".findAllEmployees";
    public static final String FIND_EMPLOYEE_HOLIDAYS_SUFFIX = ".findEmployeeHolidays";
    public static final String FIND_EMPLOYEE_SKILLS_SUFFIX = ".findEmployeeSkills";

    public static final String FIND_PROJECT_TASKS_SUFFIX = ".findProjectTasks";
    public static final String FIND_EMPLOYEE_TASK_ALLOCATIONS_SUFFIX = ".findEmployeeTaskAllocations";

    // =====================================================
    // - project entities
    // =====================================================
    String PROJECT_ENTITY_NAME = "project";

    String PROJECT_TABLE_NAME = "v_projects";

    String PROJECT_ID_COL_NAME = "id";
    String PROJECT_REFERENCE_COL_NAME = "reference";
    String PROJECT_TEMPLATE_ID_COL_NAME = "template_id";
    String PROJECT_NB_PRODUCTS_COL_NAME = "nb_products";
    String PROJECT_PRIORITY_COL_NAME = "priority";
    String PROJECT_START_DATE_COL_NAME = "start_date";
    String PROJECT_OPEN_COL_NAME = "open";

    String PROJECT_FIND_OPEN_QUERY
            = "SELECT p FROM " + PROJECT_ENTITY_NAME + " p "
            + "WHERE p.open  = :open";

    String PROJECT_FIND_QUERY
            = "SELECT p FROM " + PROJECT_ENTITY_NAME + " p "
            + "WHERE p.id = :project_id";

    // =====================================================
    // - projects' skills entities
    // =====================================================
    String PROJECT_SKILL_ENTITY_NAME = "project_skill";

    String PROJECT_SKILL_TABLE_NAME = "v_project_skills";

    String PROJECT_SKILL_ID_COL_NAME = "id";
    String PROJECT_SKILL_PROJECT_ID_COL_NAME = "project_id";
    String PROJECT_SKILL_DURATION_COL_NAME = "duration";

    String PROJECT_SKILL_FIND_QUERY
            = "SELECT ps FROM " + PROJECT_SKILL_ENTITY_NAME + " ps "
            + " WHERE ps.project_id = :project_id";

    // =====================================================
    // - non working days entities
    // =====================================================
    String NWD_ENTITY_NAME = "non_working_day";

    String NWD_TABLE_NAME = "non_working_days";

    String NWD_ID_COL_NAME = "id";
    String NWD_TITLE_COL_NAME = "title";
    String NWD_TYPE_COL_NAME = "type";
    String NWD_DATE_COL_NAME = "date";
    String NWD_MORNING_SHIFT_COL_NAME = "morning_shift";
    String NWD_AFTERNOON_SHIFT_COL_NAME = "afternoon_shift";

    String NWD_TYPE_WEEKEND = "WEEKEND";
    String NWD_TYPE_NON_WORKING = "NON-WORKING";

    String FIND_NWD_QUERY = "SELECT nwd FROM " + NWD_ENTITY_NAME + " nwd WHERE nwd.date BETWEEN :now AND :max_date";

    // =====================================================
    // - holidays
    // =====================================================
    String HOLIDAY_ENTITY_NAME = "holiday";

    String HOLIDAY_TABLE_NAME = "holidays";

    String HOLIDAY_ID_COL_NAME = "id";
    String HOLIDAY_TITLE_COL_NAME = "title";
    String HOLIDAY_EMPLOYEE_ID_COL_NAME = "employee_id";
    String HOLIDAY_START_DATE_COL_NAME = "start_date";
    String HOLIDAY_START_MORNING_SHIFT_COL_NAME = "start_morning_shift";
    String HOLIDAY_START_AFTERNOON_SHIFT_COL_NAME = "start_afternoon_shift";
    String HOLIDAY_END_DATE_COL_NAME = "end_date";
    String HOLIDAY_END_MORNING_SHIFT_COL_NAME = "end_morning_shift";
    String HOLIDAY_END_AFTERNOON_SHIFT_COL_NAME = "end_afternoon_shift";

    String FIND_EMPLOYEES_HOLIDAY_QUERY
            = "SELECT h FROM " + HOLIDAY_ENTITY_NAME + " h "
            + "WHERE (h.employee_id = :employee_id)"
            + "AND (h.start_date BETWEEN :now AND :max_date)";

    // =====================================================
    // - skills
    // =====================================================
    String SKILL_ENTITY_NAME = "skill";

    String SKILL_TABLE_NAME = "skills";

    String SKILL_ID_COL_NAME = "id";
    String SKILL_DURATION_COL_NAME = "duration";
    String SKILL_OPEN_COL_NAME = "open";

    String FIND_OPEN_SKILLS_QUERY
            = "SELECT s FROM " + SKILL_ENTITY_NAME + " s "
            + "WHERE s.open = :open";

    // =====================================================
    // - employee's skills
    // =====================================================
    String EMPLOYEE_SKILL_ENTITY_NAME = "employee_skill";

    String EMPLOYEE_SKILL_TABLE_NAME = "v_skill_employees";

    String EMPLOYEE_SKILL_EMPLOYEE_ID_COL_NAME = "employee_id";
    String EMPLOYEE_SKILL_SKILL_ID_COL_NAME = "skill_id";

    String FIND_EMPLOYEE_SKILLS_QUERY
            = "SELECT es FROM " + EMPLOYEE_SKILL_ENTITY_NAME + " es "
            + "WHERE es.employee_id = :employee_id";

    // =====================================================
    // - employees
    // =====================================================
    String EMPLOYEE_ENTITY_NAME = "employee";

    String EMPLOYEE_TABLE_NAME = "employees";

    String EMPLOYEE_ID_COL_NAME = "id";
    String EMPLOYEE_EMPLOYEEID_COL_NAME = "employeeId";
    String EMPLOYEE_PRODUCTIVITY_COL_NAME = "productivity";
    String EMPLOYEE_EMPLOYEMENT_TYPE_COL_NAME = "employementType";

    String EMPLOYEE_EMPLOYEMENT_TYPE_FTE = "FTE";
    String EMPLOYEE_EMPLOYEMENT_TYPE_INTERN = "Intern";

    String FIND_ALL_EMPLOYEES_QUERY = "SELECT e FROM " + EMPLOYEE_ENTITY_NAME + " e ";

    String FIND_EMPLOYEE_QUERY
            = "SELECT e FROM " + EMPLOYEE_ENTITY_NAME + " e "
            + "WHERE e.id = :employee_id";

    // =====================================================
    // - Task entities
    // =====================================================
    String TASK_ENTITY_NAME = "task";

    String TASK_TABLE_NAME = "tasks";

    String TASK_ID_COL_NAME = "id";
    String TASK_SKILL_ID_COL_NAME = "skill_id";
    String TASK_PROJECT_ID_COL_NAME = "project_id";
    String TASK_COMPLETED_COL_NAME = "completed";

    String TASK_FIND_PROJECT_TASKS_QUERY
            = "SELECT t FROM " + TASK_ENTITY_NAME + " t "
            + "WHERE t.project_id = :project_id";

    // =====================================================
    // - Task allocations entities
    // =====================================================
    String TASK_ALLOCATION_ENTITY_NAME = "task_allocation";

    String TASK_ALLOCATION_TABLE_NAME = "task_allocations";

    String TASK_ALLOCATION_ID_COL_NAME = "id";
    String TASK_ALLOCATION_TASK_ID_COL_NAME = "task_id";
    String TASK_ALLOCATION_EMPLOYEE_ID_COL_NAME = "employee_id";
    String TASK_ALLOCATION_START_DATE_COL_NAME = "start_date";
    String TASK_ALLOCATION_START_TIME_COL_NAME = "start_time";
    String TASK_ALLOCATION_COMPLETION_COL_NAME = "completion";
    String TASK_ALLOCATION_NB_PRODUCTS_COMPLETED_COL_NAME = "nb_products_completed";
    String TASK_ALLOCATION_COMPLETED_COL_NAME = "completed";
    String TASK_ALLOCATION_DURATION_COL_NAME = "duration";

    String TASK_FIND_EMPLOYEE_TASK_ALLOCATIONS_QUERY
            = "SELECT ta FROM " + TASK_ALLOCATION_ENTITY_NAME + " ta "
            + " WHERE ( ta.task_id = :task_id )"
            + " AND ( ta.employee_id = :employee_id )";
}
