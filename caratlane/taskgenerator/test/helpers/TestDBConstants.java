/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.Holiday;
import com.caratlane.taskflow.taskgenerator.generator.dao.NonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.EmployementType.FTE;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.EmployementType.Intern;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.NWDType.NON_WORKING_DAY;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers._D_IN_TWO_DAYS;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers._D_TOMORROW;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import java.util.Date;

/**
 *
 * @author wdmtraining
 */
public interface TestDBConstants {

    // ------------------------------------------------------------
    // Database constants
    // ------------------------------------------------------------
    String TEST_DB_HOST = "localhost";
    String TEST_DB_PORT = "33060";
    String TEST_DB_DATABASE = "homestead";
    String TEST_DB_USERNAME = "homestead";
    String TEST_DB_PASSWORD = "secret";

    // ------------------------------------------------------------
    // SQL statements
    // ------------------------------------------------------------
    public static final String DELETE_TASK_ALLOCATION_TABLE_SQL
            = "DELETE FROM `task_allocations` WHERE `task_allocations`.`id` > 0";

    public static final String DELETE_TASKS_TABLE_SQL
            = "DELETE FROM `tasks` WHERE `tasks`.`id` > 0";

    // ------------------------------------------------------------
    // list of truncate statements
    // ------------------------------------------------------------
    public static final String[] DELETE_TABLES_SQL = {
        DELETE_TASK_ALLOCATION_TABLE_SQL,
        DELETE_TASKS_TABLE_SQL
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
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_1 = 2;
    public static final Project PROJECT_JADAU_1
            = new Project(
                    ID_PROJECT_JADAU_1,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_1, // nb_products,
                    9, // priority,
                    null, // start_date,
                    (byte) 1 //  open
            );

    public static final Integer ID_PROJECT_JADAU_2 = 2;
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_2 = 4;
    public static final Project PROJECT_JADAU_2
            = new Project(
                    ID_PROJECT_JADAU_2,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_2, // nb_products,
                    9, // priority,
                    null, // start_date,
                    (byte) 1 //  open
            );

    public static final Integer ID_PROJECT_JADAU_3 = 3;
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_3 = 6;
    public static final Project PROJECT_JADAU_3
            = new Project(
                    ID_PROJECT_JADAU_3,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_3, // nb_products,
                    9, // priority,
                    null, // start_date,
                    (byte) 1 //  open
            );

    public static final Integer ID_PROJECT_JADAU_4 = 4;
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_4 = 8;
    public static final Project PROJECT_JADAU_4
            = new Project(
                    ID_PROJECT_JADAU_4,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_4, // nb_products,
                    9, // priority,
                    null, // start_date,
                    (byte) 1 //  open
            );

    public static final Integer ID_PROJECT_JADAU_5 = 5;
    public static final Integer NB_PRODUCTS_PROJECT_JADAU_5 = 10;
    public static final Project PROJECT_JADAU_5
            = new Project(
                    ID_PROJECT_JADAU_5,
                    1, // template_id,
                    NB_PRODUCTS_PROJECT_JADAU_5, // nb_products,
                    9, // priority,
                    null, // start_date,
                    (byte) 1 //  open
            );

    // ------------------------------------------------------------
    // skills
    // ------------------------------------------------------------
    public static final Integer ID_SKILL_3_3DMS = 3;
    public static final Integer ID_SKILL_5_3RenC = 5;

    public static final Integer DURATION_SKILL_3_3DMS = 2 * 60;
    public static final Integer DURATION_SKILL_5_3RenC = 1 * 60 + 45;

    public static final Byte OPEN_SKILL = (byte) 1;

    public static final Skill SKILL_3_3DMS
            = new Skill(ID_SKILL_3_3DMS, DURATION_SKILL_3_3DMS, OPEN_SKILL);

    public static final Skill SKILL_5_3RenC
            = new Skill(ID_SKILL_5_3RenC, DURATION_SKILL_5_3RenC, OPEN_SKILL);

    // ------------------------------------------------------------
    // Non Working Days
    // ------------------------------------------------------------
    public static final Date DATE_NWD_1 = _D_TOMORROW;
    public static final Date DATE_NWD_2 = _D_IN_TWO_DAYS;

    // tomorrow full day
    public static final NonWorkingDay NWD_1
            = new NonWorkingDay("nwd 1", NON_WORKING_DAY, DATE_NWD_1, (byte) 0, (byte) 0);

    // in two days, full day
    public static final NonWorkingDay NWD_2
            = new NonWorkingDay("nwd 2", NON_WORKING_DAY, DATE_NWD_2, (byte) 0, (byte) 0);

    // ------------------------------------------------------------
    // holidays
    // ------------------------------------------------------------
    // employee 1, one day holidays in 2 days
    public static final Holiday HOLIDAY_1
            = new Holiday(
                    1, // employee_id ,
                    _D_IN_TWO_DAYS, // start_date ,
                    (byte) 0, // start_morning_shift ,
                    (byte) 0, // start_afternoon_shift ,
                    _D_IN_TWO_DAYS, // end_date ,
                    (byte) 0, // end_morning_shift ,
                    (byte) 0 // end_afternoon_shift

            );

       // ------------------------------------------------------------
    // tasks
    // ------------------------------------------------------------
}
