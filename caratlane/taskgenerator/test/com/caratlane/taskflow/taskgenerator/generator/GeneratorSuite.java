/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.DEBUG_MODE;
import static com.caratlane.taskflow.taskgenerator.CommandLineConstants.NULL_LOGPATH;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import utils.TestDBManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author wdmtraining
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
            AppVersion.class,
            EmployeeDataAllocateTask1Test.class,
            EmployeeDataAllocateTask2Test.class,
//            EmployeeDataAllocateTask3Test.class,
//            EmployeeDataAllocateTask4Test.class,
//            EmployeeDataAllocateTask5Test.class,
//            EmployeeDataAllocateTask6Test.class,
//            EmployeeDataAllocateTask7Test.class,
//            EmployeeDataAllocateTask8Test.class,
//            SerializeTasks1Test.class,
//            SerializeTasks2Test.class,
//            SerializeTasks3Test.class,
//            GeneratorGenerate1Test.class,
//            GeneratorCheck1Test.class
        }
)
public class GeneratorSuite {

    final static String LOGPATH = NULL_LOGPATH;
    final static String MODE = DEBUG_MODE;

    @BeforeClass
    public static void setUpClass() throws Exception {
        LogManager.initialize(LOGPATH, MODE);
        TestDBManager.initialize();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        TestDBManager.shutdown();
        LogManager.shutdown();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
