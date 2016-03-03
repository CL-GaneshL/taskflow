/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.caratlane.taskflow.taskgenerator.db.DBException;
import com.caratlane.taskflow.taskgenerator.db.DBManager;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.Employees;
import com.caratlane.taskflow.taskgenerator.generator.NonWorkingDays;
import com.caratlane.taskflow.taskgenerator.generator.Skills;
import static helpers.TestDBConstants.SKILL_3_3DMS;
import static helpers.TestDBConstants.SKILL_5_3RenC;
import static helpers.TestDBConstants.TEST_DB_DATABASE;
import static helpers.TestDBConstants.TEST_DB_HOST;
import static helpers.TestDBConstants.TEST_DB_PASSWORD;
import static helpers.TestDBConstants.TEST_DB_PORT;
import static helpers.TestDBConstants.TEST_DB_USERNAME;

/**
 *
 * @author wdmtraining
 */
public class TestDBManager {

    /**
     * Sole constructor (private). Preventing Singleton object instantiation
     * from outside
     */
    private TestDBManager() {
    }

    /**
     *
     * @throws DBException
     * @throws TestTaskGeneratorException
     * @throws TaskGeneratorException
     */
    public static void initialize() throws DBException, TestTaskGeneratorException, TaskGeneratorException {

        DBManager.initialize(
                TEST_DB_HOST,
                TEST_DB_PORT,
                TEST_DB_DATABASE,
                TEST_DB_USERNAME,
                TEST_DB_PASSWORD
        );

        // initialize Skills for testing purpose
        final boolean test = true;
        Skills.initialize(test);

        // create a list of available skills in the db
        final Skills skills = Skills.getInstance();
        skills.addSkill(test, SKILL_3_3DMS);      // id = 3
        skills.addSkill(test, SKILL_5_3RenC);     // id = 5

        // initialize NonWorkingDay for testing purpose
        NonWorkingDays.initialize(test);

        // initialize Employees for testing purposes
        Employees.initialize(test);
    }

    /**
     *
     */
    public static void shutdown() {

        DBManager.shutdown();
    }

}
