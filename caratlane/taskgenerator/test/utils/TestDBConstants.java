/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers;
import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.Holiday;
import com.caratlane.taskflow.taskgenerator.generator.dao.NonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.EmployementType.FTE;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.EmployementType.Intern;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.NWDType.NON_WORKING;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author wdmtraining
 */
public interface TestDBConstants {

    // ------------------------------------------------------------
    // Database constants
    // ------------------------------------------------------------
    String TEST_DB_HOST = "127.0.0.1";
    String TEST_DB_PORT = "3306";
    String TEST_DB_DATABASE = "testschema";
    String TEST_DB_USERNAME = "testuser";
    String TEST_DB_PASSWORD = "testuser";

//    String TEST_DB_HOST = "localhost";
//    String TEST_DB_PORT = "33060";
//    String TEST_DB_DATABASE = "homestead";
//    String TEST_DB_USERNAME = "homestead";
//    String TEST_DB_PASSWORD = "secret";
    // ------------------------------------------------------------
    // general constants
    // ------------------------------------------------------------
    public final static Date _D_TODAY = ExtractorDbHelpers._D_TODAY;
    public final static Date _D_TOMORROW = ExtractorDbHelpers._D_TOMORROW;
    public final static Date _D_IN_TWO_DAYS = ExtractorDbHelpers._D_IN_TWO_DAYS;

    public final static LocalDateTime TODAY = ExtractorDbHelpers.TODAY;
    public final static LocalDateTime TOMORROW = ExtractorDbHelpers.TOMORROW;
    public final static LocalDateTime IN_TWO_DAYS = ExtractorDbHelpers.IN_TWO_DAYS;

    // ------------------------------------------------------------
    // SQL statements
    // ------------------------------------------------------------
    public static final String DELETE_TASK_ALLOCATION_TABLE_SQL
            = "DELETE FROM `task_allocations` WHERE `task_allocations`.`id` > 0";

    public static final String DELETE_TASKS_TABLE_SQL
            = "DELETE FROM `tasks` WHERE `tasks`.`id` > 0";

    public static final String DELETE_SKILLS_TABLE_SQL
            = "DELETE FROM `skills` WHERE `skills`.`id` > 0";

    public static final String DELETE_PROJECTS_TABLE_SQL
            = "DELETE FROM `projects` WHERE `projects`.`id` > 0";

    public static final String DELETE_TEMPLATES_TABLE_SQL
            = "DELETE FROM `project_templates` WHERE `project_templates`.`id` > 0";

    public static final String DELETE_EMPLOYEES_TABLE_SQL
            = "DELETE FROM `employees` WHERE `employees`.`id` > 0";

    public static final String DELETE_HOLIDAYS_TABLE_SQL
            = "DELETE FROM `holidays` WHERE `holidays`.`id` > 0";

    public static final String DELETE_NWDS_TABLE_SQL
            = "DELETE FROM `non_working_days` WHERE `non_working_days`.`id` > 0";

    // ------------------------------------------------------------
    // list of truncate statements
    // ------------------------------------------------------------
    public static final String[] DELETE_TABLES_SQL = {
        DELETE_HOLIDAYS_TABLE_SQL,
        DELETE_NWDS_TABLE_SQL,
        DELETE_TASK_ALLOCATION_TABLE_SQL,
        DELETE_TASKS_TABLE_SQL,
        DELETE_TEMPLATES_TABLE_SQL,
        DELETE_PROJECTS_TABLE_SQL,
        DELETE_SKILLS_TABLE_SQL,
        DELETE_EMPLOYEES_TABLE_SQL
    };

    // ------------------------------------------------------------
    // employees
    // ------------------------------------------------------------
    public static final Integer ID_EMPLOYEE_CL0004 = 1;
    public static final Double PRODUCTIVITY_EMPLOYEE_CL0004 = 8.0;

    public static final Employee EMPLOYEE_CL0004
            = new Employee(
                    ID_EMPLOYEE_CL0004,
                    "CL0004",
                    PRODUCTIVITY_EMPLOYEE_CL0004,
                    FTE
            );

    public static final Integer ID_EMPLOYEE_CL0148 = 2;
    public static final Double PRODUCTIVITY_EMPLOYEE_CL0148 = 4.0;
    public static final Employee EMPLOYEE_CL0148
            = new Employee(
                    ID_EMPLOYEE_CL0148,
                    "CL0148",
                    PRODUCTIVITY_EMPLOYEE_CL0148,
                    Intern
            );

    // ------------------------------------------------------------
    // projects
    // ------------------------------------------------------------
    public static final Integer ID_PROJECT_JADAU_1 = 1;
    public static final String REF_PROJECT_JADAU_1 = "JADAU_1";
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_1 = 2;
    public static final Date START_DATE_PROJECT_JADAU_1 = _D_TODAY;

    public static final Project PROJECT_JADAU_1
            = new Project(
                    ID_PROJECT_JADAU_1,
                    REF_PROJECT_JADAU_1,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_1, // nb_products,
                    9, // priority,
                    START_DATE_PROJECT_JADAU_1, // start_date,
                    null, // end_date
                    (byte) 1 //  open
            );

    public static final Integer ID_PROJECT_JADAU_2 = 2;
    public static final String REF_PROJECT_JADAU_2 = "JADAU_2";
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_2 = 4;
    public static final Date START_DATE_PROJECT_JADAU_2 = _D_TODAY;

    public static final Project PROJECT_JADAU_2
            = new Project(
                    ID_PROJECT_JADAU_2,
                    REF_PROJECT_JADAU_2,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_2, // nb_products,
                    9, // priority,
                    START_DATE_PROJECT_JADAU_2, // start_date,
                    null, // end_date
                    (byte) 1 //  open
            );

    public static final Integer ID_PROJECT_JADAU_3 = 3;
    public static final String REF_PROJECT_JADAU_3 = "JADAU_3";
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_3 = 6;
    public static final Date START_DATE_PROJECT_JADAU_3 = _D_TODAY;

    public static final Project PROJECT_JADAU_3
            = new Project(
                    ID_PROJECT_JADAU_3,
                    REF_PROJECT_JADAU_3,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_3, // nb_products,
                    9, // priority,
                    START_DATE_PROJECT_JADAU_3, // start_date,
                    null, // end_date
                    (byte) 1 //  open
            );

    public static final Integer ID_PROJECT_JADAU_4 = 4;
    public static final String REF_PROJECT_JADAU_4 = "JADAU_4";
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_4 = 8;
    public static final Date START_DATE_PROJECT_JADAU_4 = _D_TODAY;

    public static final Project PROJECT_JADAU_4
            = new Project(
                    ID_PROJECT_JADAU_4,
                    REF_PROJECT_JADAU_4,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_4, // nb_products,
                    9, // priority,
                    START_DATE_PROJECT_JADAU_4, // start_date,
                    null, // end_date
                    (byte) 1 //  open
            );

    public static final Integer ID_PROJECT_JADAU_5 = 5;
    public static final String REF_PROJECT_JADAU_5 = "JADAU_5";
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_5 = 10;
    public static final Date START_DATE_PROJECT_JADAU_5 = _D_TODAY;

    public static final Project PROJECT_JADAU_5
            = new Project(
                    ID_PROJECT_JADAU_5,
                    REF_PROJECT_JADAU_5,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_5, // nb_products,
                    9, // priority,
                    START_DATE_PROJECT_JADAU_5, // start_date,
                    null, // end_date
                    (byte) 1 //  open
            );

    public static final Integer ID_PROJECT_JADAU_6 = 6;
    public static final String REF_PROJECT_JADAU_6 = "JADAU_6";
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_6 = 2;
    public static final Date START_DATE_PROJECT_JADAU_6 = _D_IN_TWO_DAYS;

    public static final Project PROJECT_JADAU_6
            = new Project(
                    ID_PROJECT_JADAU_6,
                    REF_PROJECT_JADAU_6,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_6, // nb_products,
                    9, // priority,
                    START_DATE_PROJECT_JADAU_6, // start_date,
                    null, // end_date
                    (byte) 1 //  open
            );

    // ------------------------------------------------------------
    // project templates
    // ------------------------------------------------------------
    public static final String REF_TEMPLATE_JADAU_1 = "REF JADAU_1";
    public static final String DES_TEMPLATE_JADAU_1 = "TEMPLATE JADAU_1";
    public static final Byte OPEN_TEMPLATE_JADAU_1 = (byte) 1;

    // ------------------------------------------------------------
    // skills
    // ------------------------------------------------------------
    public static final Integer ID_SKILL_3_3DMS = 3;
    public static final Integer ID_SKILL_5_3RenC = 5;

    public static final String REF_SKILL_3_3DMS = "3_3DMS";
    public static final String REF_SKILL_5_3RenC = "5_3RenC";

    public static final String DES_SKILL_3_3DMS = "3_3DMS";
    public static final String DES_SKILL_5_3RenC = "5_3RenC";

    public static final Integer DURATION_SKILL_3_3DMS = 2 * 60;
    public static final Integer DURATION_SKILL_5_3RenC = 1 * 60 + 45;

    public static final Byte OPEN_SKILL = (byte) 1;

    public static final Skill SKILL_3_3DMS
            = new Skill(
                    ID_SKILL_3_3DMS,
                    REF_SKILL_3_3DMS,
                    DES_SKILL_3_3DMS,
                    DURATION_SKILL_3_3DMS,
                    OPEN_SKILL
            );

    public static final Skill SKILL_5_3RenC
            = new Skill(
                    ID_SKILL_5_3RenC,
                    REF_SKILL_5_3RenC,
                    DES_SKILL_5_3RenC,
                    DURATION_SKILL_5_3RenC,
                    OPEN_SKILL
            );

    // ------------------------------------------------------------
    // Non Working Days
    // ------------------------------------------------------------
    public static final Date DATE_NWD_1 = _D_TOMORROW;
    public static final Date DATE_NWD_2 = _D_IN_TWO_DAYS;

    // tomorrow full day
    public static final NonWorkingDay NWD_1
            = new NonWorkingDay("nwd 1", NON_WORKING, DATE_NWD_1);

    // in two days, full day
    public static final NonWorkingDay NWD_2
            = new NonWorkingDay("nwd 2", NON_WORKING, DATE_NWD_2);

    // ------------------------------------------------------------
    // holidays
    // ------------------------------------------------------------
    // one day holidays tomorrow
    public static final Holiday HOLIDAY_1
            = new Holiday(
                    1, // employee_id ,
                    _D_TOMORROW, // start_date ,
                    _D_TOMORROW // end_date ,
            );

    // one day holidays in two days
    public static final Holiday HOLIDAY_2
            = new Holiday(
                    1, // employee_id ,
                    _D_IN_TWO_DAYS, // start_date ,
                    _D_IN_TWO_DAYS // end_date ,
            );

       // ------------------------------------------------------------
    // tasks
    // ------------------------------------------------------------
}
