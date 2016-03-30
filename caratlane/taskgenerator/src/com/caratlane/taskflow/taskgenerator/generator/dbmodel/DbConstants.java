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

    // =====================================================
    // - template entities
    // =====================================================
    String TEMPLATE_ENTITY_NAME = "project_template";

    String TEMPLATE_TABLE_NAME = "project_templates";

    String TEMPLATE_ID_COL_NAME = "id";
    String TEMPLATE_REFERENCE_COL_NAME = "reference";
    String TEMPLATE_DESIGNATION_COL_NAME = "designation";
    String TEMPLATE_OPEN_COL_NAME = "open";

    // =====================================================
    // - template have skills entities
    // =====================================================
    String TEMPLATE_HAVE_SKILLS_ENTITY_NAME = "project_templates_have_skills";

    String TEMPLATE_HAVE_SKILLS_TABLE_NAME = "project_templates_have_skills";

    String TEMPLATE_HAVE_SKILLS_TEMPLATE_ID_COL_NAME = "template_id";
    String TEMPLATE_HAVE_SKILLS_SKILL_ID_COL_NAME = "skill_id";

    // =====================================================
    // - projects' skills entities
    // =====================================================
    String PROJECT_SKILL_ENTITY_NAME = "project_skill";

    String PROJECT_SKILL_TABLE_NAME = "v_project_skills";

    String PROJECT_SKILL_ID_COL_NAME = "id";
    String PROJECT_SKILL_PROJECT_ID_COL_NAME = "project_id";
    String PROJECT_SKILL_DURATION_COL_NAME = "duration";

    // =====================================================
    // - non working days entities
    // =====================================================
    String NWD_ENTITY_NAME = "non_working_day";

    String NWD_TABLE_NAME = "non_working_days";

    String NWD_ID_COL_NAME = "id";
    String NWD_TITLE_COL_NAME = "title";
    String NWD_TYPE_COL_NAME = "type";
    String NWD_DATE_COL_NAME = "date";

    String NWD_WEEKEND = "WEEKEND";
    String NWD_NON_WORKING = "NON-WORKING";

    // =====================================================
    // - holidays
    // =====================================================
    String HOLIDAY_ENTITY_NAME = "holiday";

    String HOLIDAY_TABLE_NAME = "holidays";

    String HOLIDAY_ID_COL_NAME = "id";
    String HOLIDAY_TITLE_COL_NAME = "title";
    String HOLIDAY_EMPLOYEE_ID_COL_NAME = "employee_id";
    String HOLIDAY_START_DATE_COL_NAME = "start_date";
    String HOLIDAY_END_DATE_COL_NAME = "end_date";

    // =====================================================
    // - skills
    // =====================================================
    String SKILL_ENTITY_NAME = "skill";

    String SKILL_TABLE_NAME = "skills";

    String SKILL_ID_COL_NAME = "id";
    String SKILL_REFERENCE_COL_NAME = "reference";
    String SKILL_DESIGNATION_COL_NAME = "designation";
    String SKILL_DURATION_COL_NAME = "duration";
    String SKILL_OPEN_COL_NAME = "open";

    // =====================================================
    // - employee's skills
    // =====================================================
    String EMPLOYEE_SKILL_ENTITY_NAME = "employee_skill";

    String EMPLOYEE_SKILL_TABLE_NAME = "v_skill_employees";

    String EMPLOYEE_SKILL_EMPLOYEE_ID_COL_NAME = "employee_id";
    String EMPLOYEE_SKILL_SKILL_ID_COL_NAME = "skill_id";

 // =====================================================
    // - employee have skills
    // =====================================================
    String EMPLOYEE_HAVE_SKILLS_ENTITY_NAME = "employees_have_skills";

    String EMPLOYEE_HAVE_SKILLS_TABLE_NAME = "employees_have_skills";

    String EMPLOYEE_HAVE_SKILLS_EMPLOYEE_ID_COL_NAME = "employeeId";
    String EMPLOYEE_HAVE_SKILLS_ID_COL_NAME = "skillId";

    // =====================================================
    // - employees
    // =====================================================
    String EMPLOYEE_ENTITY_NAME = "employee";

    String EMPLOYEE_TABLE_NAME = "employees";

    String EMPLOYEE_ID_COL_NAME = "id";
    String EMPLOYEE_EMPLOYEEID_COL_NAME = "employeeId";
    String EMPLOYEE_PRODUCTIVITY_COL_NAME = "productivity";
    String EMPLOYEE_EMPLOYEMENT_TYPE_COL_NAME = "employementType";
//    String EMPLOYEE_MORNING_SHIFT_COL_NAME = "morning_shift";

    String EMPLOYEE_EMPLOYEMENT_TYPE_FTE = "FTE";
    String EMPLOYEE_EMPLOYEMENT_TYPE_INTERN = "Intern";

    // =====================================================
    // - Task entities
    // =====================================================
    String TASK_ENTITY_NAME = "task";

    String TASK_TABLE_NAME = "tasks";

    String TASK_ID_COL_NAME = "id";
    String TASK_SKILL_ID_COL_NAME = "skill_id";
    String TASK_PROJECT_ID_COL_NAME = "project_id";

    // =====================================================
    // - Task allocations entities
    // =====================================================
    String TASK_ALLOCATION_ENTITY_NAME = "task_allocation";

    String TASK_ALLOCATION_TABLE_NAME = "task_allocations";

    String TASK_ALLOCATION_ID_COL_NAME = "id";
    String TASK_ALLOCATION_TASK_ID_COL_NAME = "task_id";
    String TASK_ALLOCATION_EMPLOYEE_ID_COL_NAME = "employee_id";
    String TASK_ALLOCATION_START_DATE_COL_NAME = "start_date";
    String TASK_ALLOCATION_COMPLETION_COL_NAME = "completion";
    String TASK_ALLOCATION_NB_PRODUCTS_COMPLETED_COL_NAME = "nb_products_completed";
    String TASK_ALLOCATION_COMPLETED_COL_NAME = "completed";
    String TASK_ALLOCATION_DURATION_COL_NAME = "duration";

}
