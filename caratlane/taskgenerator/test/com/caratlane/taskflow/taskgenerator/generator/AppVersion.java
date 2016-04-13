/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import static com.caratlane.taskflow.taskgenerator.Version.APP_AUTHOR;
import static com.caratlane.taskflow.taskgenerator.Version.APP_COPYRIGHT;
import static com.caratlane.taskflow.taskgenerator.Version.APP_NAME;
import static com.caratlane.taskflow.taskgenerator.Version.APP_VERSION;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author wdmtraining
 */
public class AppVersion {

    public AppVersion() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testVersion() {

        final String expectedAppName = "TaskGenerator";
        assertEquals(expectedAppName, APP_NAME);

        final String expectedAppAuthor = "William Desheulles";
        assertEquals(expectedAppAuthor, APP_AUTHOR);

        final String expectedAppCopyrigth = "©2016, Caratlane";
        assertEquals(expectedAppCopyrigth, APP_COPYRIGHT);

        final String expectedAppVersion = "1.0.0";
        assertEquals(expectedAppVersion, APP_VERSION);

    }

}
