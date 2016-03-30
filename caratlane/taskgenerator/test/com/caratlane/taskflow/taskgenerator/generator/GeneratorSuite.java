/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import helpers.TestDBManager;
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
            EmployeeDataAllocateTask1Test.class,
            EmployeeDataAllocateTask2Test.class,
            EmployeeDataAllocateTask3Test.class,
            EmployeeDataAllocateTask4Test.class,
            EmployeeDataAllocateTask5Test.class,
            EmployeeDataAllocateTask6Test.class,
            SerializeTasks1Test.class,
            SerializeTasks2Test.class,
            SerializeTasks3Test.class,
            Generator1Test.class
        }
)
public class GeneratorSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TestDBManager.initialize();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        TestDBManager.shutdown();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
