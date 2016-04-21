/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.TOMORROW;
import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbEmployeeHaveSkills;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTemplate;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTemplateHaveSkills;
import static helpers.TestDBConstants.DES_TEMPLATE_JADAU_1;
import static helpers.TestDBConstants.EMPLOYEE_CL0004;
import static helpers.TestDBConstants.OPEN_TEMPLATE_JADAU_1;
import static helpers.TestDBConstants.PROJECT_JADAU_1;
import static helpers.TestDBConstants.REF_TEMPLATE_JADAU_1;
import static helpers.TestDBConstants.SKILL_3_3DMS;
import helpers.TestDBCrud;
import helpers.TestTaskGeneratorException;
import java.time.LocalDateTime;
import java.util.List;
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
public class Generator1Test {

    private final static boolean test = true;

    private static Integer ID_EMPLOYEE = null;
    private static Integer ID_PROJECT = null;
    private static Integer ID_TEMPLATE = null;
    private static Integer ID_SKILL = null;

    private static int NB_PRODUCTS = 0;
    private static int SKILL_DURATION = 0;

    public Generator1Test() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() throws TestTaskGeneratorException {

    }

    @Before
    public void setUp() throws TestTaskGeneratorException {

        // -------------------------------------------------------
        // de-serialize
        TestDBCrud.truncateTables();

        // -------------------------------------------------------
        // create a skill
        final Skill skill = new Skill(
                SKILL_3_3DMS.getReference(),
                SKILL_3_3DMS.getDesignation(),
                SKILL_3_3DMS.getDuration(),
                SKILL_3_3DMS.getOpen()
        );

        SKILL_DURATION = SKILL_3_3DMS.getDuration();
        ID_SKILL = TestDBCrud.serializeSkill(skill);

        // -------------------------------------------------------
        // create an employee
        final Employee employee = new Employee(
                EMPLOYEE_CL0004.getEmployeeId(),
                EMPLOYEE_CL0004.getProductivity(),
                EMPLOYEE_CL0004.getEmployementType()
        );

        ID_EMPLOYEE = TestDBCrud.serializeEmployee(employee);

        // -------------------------------------------------------
        // attach skills to employees
        final DbEmployeeHaveSkills dbEmployeeHaveSkills
                = new DbEmployeeHaveSkills(
                        ID_EMPLOYEE,
                        ID_SKILL
                );

        TestDBCrud.serializeEmployeeHaveSkills(dbEmployeeHaveSkills);

        // -------------------------------------------------------
        // create a project template
        final DbTemplate dbTemplate = new DbTemplate(
                REF_TEMPLATE_JADAU_1,
                DES_TEMPLATE_JADAU_1,
                OPEN_TEMPLATE_JADAU_1
        );
        ID_TEMPLATE = TestDBCrud.serializeTemplate(dbTemplate);

        // -------------------------------------------------------
        // attach skills for that template
        final DbTemplateHaveSkills dbTemplateHaveSkills
                = new DbTemplateHaveSkills(
                        ID_TEMPLATE,
                        ID_SKILL
                );

        TestDBCrud.serializeTemplateHaveSkills(dbTemplateHaveSkills);

        // -------------------------------------------------------
        // create the project PROJECT_JADAU_1       
        final Project project
                = new Project(
                        PROJECT_JADAU_1.getReference(),
                        ID_TEMPLATE,
                        PROJECT_JADAU_1.getNbProducts(),
                        PROJECT_JADAU_1.getPriority(),
                        PROJECT_JADAU_1.getStartDate(test),
                        PROJECT_JADAU_1.getEndDate(test),
                        PROJECT_JADAU_1.getOpen(test)
                );

        NB_PRODUCTS = PROJECT_JADAU_1.getNbProducts();
        ID_PROJECT = TestDBCrud.serializeProject(project);

    }

    @After
    public void tearDown() {

    }

    /**
     *
     * @throws TaskGeneratorException
     */
    @Test
    public void testGenerator_1() throws TaskGeneratorException {

        final Generator generator = new Generator();
        generator.generate();

        final List<Task> tasks = TestDBCrud.getProjectTasks(ID_PROJECT);

        // expect 1 task
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        final List<TaskAllocation> allocations
                = TestDBCrud.getEmployeeTaskAllocations(ID_EMPLOYEE);

        final int nbExpectedAllocations = 1;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation = allocations.get(0);

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate = allocation.getStartDate();
        final LocalDateTime expectedstartDate = TOMORROW;
        assertEquals(expectedstartDate, startDate);

        // allocation expected of 4 hours ( which is also the total duration )
        final int duration = allocation.getDuration();
        final int expectedDuration = NB_PRODUCTS * SKILL_DURATION;  // 2 * 120 = 240 mins
        assertEquals(expectedDuration, duration);
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    @Test
    public void testGenerator_2() throws TaskGeneratorException {

        // ---------------------------------------------------
        // - generate tasks for that project
        // ---------------------------------------------------
        Generator generator = new Generator();
        generator.generate();

        List<Task> tasks = TestDBCrud.getProjectTasks(ID_PROJECT);

        // expect 1 task
        int nbExpectedTasks = 1;
        int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        List<TaskAllocation> allocations
                = TestDBCrud.getEmployeeTaskAllocations(ID_EMPLOYEE);

        int nbExpectedAllocations = 1;
        assertEquals(nbExpectedAllocations, allocations.size());

        TaskAllocation allocation = allocations.get(0);

        // no previous allocation, so expected to be the first allocation tomorrow
        LocalDateTime startDate = allocation.getStartDate();
        LocalDateTime expectedstartDate = TOMORROW;
        assertEquals(expectedstartDate, startDate);

        // allocation expected of 4 hours ( which is also the total duration )
        int duration = allocation.getDuration();
        int expectedDuration = NB_PRODUCTS * SKILL_DURATION;  // 2 * 120 = 240 mins
        assertEquals(expectedDuration, duration);

        // ---------------------------------------------------
        // - generate tasks for the same project une second time
        // ---------------------------------------------------
        generator = new Generator();
        generator.generate();

        tasks = TestDBCrud.getProjectTasks(ID_PROJECT);

        // expect 1 task
        nbExpectedTasks = 1;
        nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        allocations = TestDBCrud.getEmployeeTaskAllocations(ID_EMPLOYEE);

        nbExpectedAllocations = 1;
        assertEquals(nbExpectedAllocations, allocations.size());

        allocation = allocations.get(0);

        // no previous allocation, so expected to be the first allocation tomorrow
        startDate = allocation.getStartDate();
        expectedstartDate = TOMORROW;
        assertEquals(expectedstartDate, startDate);

        // allocation expected of 4 hours ( which is also the total duration )
        duration = allocation.getDuration();
        expectedDuration = NB_PRODUCTS * SKILL_DURATION;  // 2 * 120 = 240 mins
        assertEquals(expectedDuration, duration);

    }

}
