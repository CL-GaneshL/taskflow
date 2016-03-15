/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.IN_THREE_DAYS;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.IN_TWO_DAYS;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.TOMORROW;
import com.caratlane.taskflow.taskgenerator.generator.rules.TaskAllocator;
import helpers.TestTaskGeneratorException;
import static helpers.TestDBConstants.DURATION_SKILL_3_3DMS;
import static helpers.TestDBConstants.EMPLOYEE_CL0004;
import static helpers.TestDBConstants.ID_SKILL_3_3DMS;
import static helpers.TestDBConstants.NB_PRODUCTS_PROJECT_JADAU_1;
import static helpers.TestDBConstants.NB_PRODUCTS_PROJECT_JADAU_2;
import static helpers.TestDBConstants.NB_PRODUCTS_PROJECT_JADAU_3;
import static helpers.TestDBConstants.NB_PRODUCTS_PROJECT_JADAU_4;
import static helpers.TestDBConstants.NB_PRODUCTS_PROJECT_JADAU_5;
import static helpers.TestDBConstants.NWD_1;
import static helpers.TestDBConstants.PROJECT_JADAU_1;
import static helpers.TestDBConstants.PROJECT_JADAU_2;
import static helpers.TestDBConstants.PROJECT_JADAU_3;
import static helpers.TestDBConstants.PROJECT_JADAU_4;
import static helpers.TestDBConstants.PROJECT_JADAU_5;
import static helpers.TestDBConstants.SKILL_3_3DMS;
import static helpers.TestDBConstants.SKILL_5_3RenC;
import java.time.LocalDateTime;
import java.util.LinkedList;
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
public class EmployeeDataAllocateTask1Test {

    final static boolean test = true;

    private static EmployeeData employeeData = null;
    private static ProjectData projectData = null;

    public EmployeeDataAllocateTask1Test() {
    }

    @BeforeClass
    public static void setUpClass() {

        // company's non working days
        final NonWorkingDays nwdsInstance = NonWorkingDays.getInstance();
        nwdsInstance.addNwd(test, NWD_1);       // tomorrow

        final Skills skills = Skills.getInstance();
        skills.addSkill(SKILL_3_3DMS);      // id = 3
        skills.addSkill(SKILL_5_3RenC);     // id = 5

        // create one employee CL0004
        employeeData = new EmployeeData(EMPLOYEE_CL0004);
        employeeData.addSkill(ID_SKILL_3_3DMS);     // id = 3

        final Employees employeesInstance = Employees.getInstance();
        employeesInstance.addEmployeeData(test, employeeData);

        // set up non working days for that employee
        // non non working days, no holidays
    }

    @AfterClass
    public static void tearDownClass() {

        projectData = null;
        employeeData = null;

        final Skills skillsInstance = Skills.getInstance();
        skillsInstance.clearSkills(test);

        final NonWorkingDays nwdsInstance = NonWorkingDays.getInstance();
        nwdsInstance.clearNwds(test);

        final Employees employeesInstance = Employees.getInstance();
        employeesInstance.clearEmployeeData(test);
    }

    @Before
    public void setUp() throws TestTaskGeneratorException {

        // clean up existing tasks from previous tests.
        employeeData.clearTaskAllocations(test);
    }

    @After
    public void tearDown() {

        // make sure a new project is used for every new test
        projectData = null;
    }

    // ==================================================================
    // - Test series of the allocateTask() method.
    // - Using a unique skill ID_SKILL_3_3DMS with a duration of 
    // - 2 hours and a number of products respectively equals to 
    // - 2, 4, 6, 8 and 10. We therefore expect total durations
    // - respectively equals to 4, 8, 12, 16 and 20 hours. These
    // - durations are multiple of 4 ( half a shift of 8 hours ),
    // - so we exercice the allocation of full shift ( 8 hours ).
    // - The expected result for each test is :
    // - [ ([4 hours] | [8hours])+ ]
    // ==================================================================
    /**
     * Test of allocateTask method, of class EmployeeData.
     *
     * @throws helpers.TestTaskGeneratorException
     * @throws TaskGeneratorException
     */
    @Test
    public void testAllocateTask_1() throws TestTaskGeneratorException, TaskGeneratorException {

        projectData = new ProjectData(PROJECT_JADAU_1);

        final Integer skill_id = ID_SKILL_3_3DMS;   // id = 3
        final Integer nb_products = NB_PRODUCTS_PROJECT_JADAU_1;    // nb probucts = 2

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, skill_id, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        final Task task = tasks.get(0);

        // task of duration 240 mins ( 4 hours )
        final int totalDuration = task.getTotalDuration();
        final int expectedTotalDuration = NB_PRODUCTS_PROJECT_JADAU_1 * DURATION_SKILL_3_3DMS;  // 2 * 120 = 240 mins
        assertEquals(expectedTotalDuration, totalDuration);

        // expect only one allocation of 240 mins ( 4 hours )
        final LinkedList<TaskAllocation> allocations = employeeData.getTaskAllocations();
        final int nbExpectedAllocations = 1;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation = allocations.getLast();

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate = allocation.getStartDate();
        final LocalDateTime expectedstartDate = TOMORROW;
        assertEquals(expectedstartDate, startDate);

        // allocation expected of 4 hours ( which is also the total duration )
        final int duration = allocation.getDuration();
        final int expectedDuration = expectedTotalDuration;  // 2 * 120 = 240 mins
        assertEquals(expectedDuration, duration);
    }

    /**
     * Test of allocateTask method, of class EmployeeData.
     *
     * @throws helpers.TestTaskGeneratorException
     * @throws TaskGeneratorException
     */
    @Test
    public void testAllocateTask_2() throws TestTaskGeneratorException, TaskGeneratorException {

        projectData = new ProjectData(PROJECT_JADAU_2);

        final Integer skill_id = ID_SKILL_3_3DMS;   // id = 3
        final Integer nb_products = NB_PRODUCTS_PROJECT_JADAU_2;    // nb probucts = 4

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, skill_id, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        final Task task = tasks.get(0);

        // task of duration 480 mins ( 8 hours )
        final int totalDuration = task.getTotalDuration();
        final int expectedTotalDuration = NB_PRODUCTS_PROJECT_JADAU_2 * DURATION_SKILL_3_3DMS;  // 4 * 120 = 480 mins
        assertEquals(expectedTotalDuration, totalDuration);

        // expect only one allocation of 480 mins ( 8 hours )
        final LinkedList<TaskAllocation> allocations = employeeData.getTaskAllocations();
        final int nbExpectedAllocations = 1;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation = allocations.getLast();

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate = allocation.getStartDate();
        final LocalDateTime expectedstartDate = TOMORROW;
        assertEquals(expectedstartDate, startDate);

        // allocation expected of 8 hours ( which is also the total duration )
        final int duration = allocation.getDuration();
        final int expectedDuration = expectedTotalDuration;  // 4 * 120 = 480 mins
        assertEquals(expectedDuration, duration);
    }

    /**
     * Test of allocateTask method, of class EmployeeData.
     *
     * @throws helpers.TestTaskGeneratorException
     * @throws TaskGeneratorException
     */
    @Test
    public void testAllocateTask_3() throws TestTaskGeneratorException, TaskGeneratorException {

        projectData = new ProjectData(PROJECT_JADAU_3);

        final Integer skill_id = ID_SKILL_3_3DMS;   // id = 3
        final Integer nb_products = NB_PRODUCTS_PROJECT_JADAU_3;    // nb probucts = 6

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, skill_id, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        final Task task = tasks.get(0);

        // task of duration 720 mins ( 12 hours )
        final int totalDuration = task.getTotalDuration();
        final int expectedTotalDuration = NB_PRODUCTS_PROJECT_JADAU_3 * DURATION_SKILL_3_3DMS;  // 6 * 120 = 720 mins
        assertEquals(expectedTotalDuration, totalDuration);

        // expect 2 allocations
        final LinkedList<TaskAllocation> allocations = employeeData.getTaskAllocations();
        final int nbExpectedAllocations = 2;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation0 = allocations.get(0);

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate0 = allocation0.getStartDate();
        final LocalDateTime expectedstartDate0 = TOMORROW;
        assertEquals(expectedstartDate0, startDate0);

        // allocation expected of 8 hours ( full shift allocation )
        final int duration0 = allocation0.getDuration();
        final int expectedDuration0 = 8 * 60;
        assertEquals(expectedDuration0, duration0);

        final TaskAllocation allocation1 = allocations.get(1);

        // the 2nd allocation should start the day after tomorrow
        final LocalDateTime startDate1 = allocation1.getStartDate();
        final LocalDateTime expectedstartDate1 = IN_TWO_DAYS;
        assertEquals(expectedstartDate1, startDate1);

        // allocation expected of 4 hours ( the remaining )
        final int duration1 = allocation1.getDuration();
        final int expectedDuration1 = 4 * 60;
        assertEquals(expectedDuration1, duration1);
    }

    /**
     * Test of allocateTask method, of class EmployeeData.
     *
     * @throws helpers.TestTaskGeneratorException
     * @throws TaskGeneratorException
     */
    @Test
    public void testAllocateTask_4() throws TestTaskGeneratorException, TaskGeneratorException {

        projectData = new ProjectData(PROJECT_JADAU_4);

        final Integer skill_id = ID_SKILL_3_3DMS;   // id = 3
        final Integer nb_products = NB_PRODUCTS_PROJECT_JADAU_4;    // nb probucts = 8

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, skill_id, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        final Task task = tasks.get(0);

        // task of duration 960 mins ( 16 hours )
        final int totalDuration = task.getTotalDuration();
        final int expectedTotalDuration = NB_PRODUCTS_PROJECT_JADAU_4 * DURATION_SKILL_3_3DMS;  // 8 * 120 = 960 mins
        assertEquals(expectedTotalDuration, totalDuration);

        // expect 2 allocations
        final LinkedList<TaskAllocation> allocations = employeeData.getTaskAllocations();
        final int nbExpectedAllocations = 2;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation0 = allocations.get(0);

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate0 = allocation0.getStartDate();
        final LocalDateTime expectedstartDate0 = TOMORROW;
        assertEquals(expectedstartDate0, startDate0);

        // allocation expected of 8 hours ( full shift allocation )
        final int duration0 = allocation0.getDuration();
        final int expectedDuration0 = 8 * 60;
        assertEquals(expectedDuration0, duration0);

        final TaskAllocation allocation1 = allocations.get(1);

        // the 2nd allocation should start the day after tomorrow
        final LocalDateTime startDate1 = allocation1.getStartDate();
        final LocalDateTime expectedstartDate1 = IN_TWO_DAYS;
        assertEquals(expectedstartDate1, startDate1);

        // allocation expected of 8 hours ( the remaining )
        final int duration1 = allocation1.getDuration();
        final int expectedDuration1 = 8 * 60;
        assertEquals(expectedDuration1, duration1);
    }

    /**
     * Test of allocateTask method, of class EmployeeData.
     *
     * @throws helpers.TestTaskGeneratorException
     * @throws TaskGeneratorException
     */
    @Test
    public void testAllocateTask_5() throws TestTaskGeneratorException, TaskGeneratorException {

        projectData = new ProjectData(PROJECT_JADAU_5);

        final Integer skill_id = ID_SKILL_3_3DMS;   // id = 3
        final Integer nb_products = NB_PRODUCTS_PROJECT_JADAU_5;    // nb probucts = 10

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, skill_id, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        final Task task = tasks.get(0);

        // task of duration 1200 mins ( 20 hours )
        final int totalDuration = task.getTotalDuration();
        final int expectedTotalDuration = NB_PRODUCTS_PROJECT_JADAU_5 * DURATION_SKILL_3_3DMS;  // 10 * 120 = 1200 mins
        assertEquals(expectedTotalDuration, totalDuration);

        // expect 3 allocations
        final LinkedList<TaskAllocation> allocations = employeeData.getTaskAllocations();
        final int nbExpectedAllocations = 3;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation0 = allocations.get(0);

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate0 = allocation0.getStartDate();
        final LocalDateTime expectedstartDate0 = TOMORROW;
        assertEquals(expectedstartDate0, startDate0);

        // allocation expected of 8 hours ( full shift allocation )
        final int duration0 = allocation0.getDuration();
        final int expectedDuration0 = 8 * 60;
        assertEquals(expectedDuration0, duration0);

        final TaskAllocation allocation1 = allocations.get(1);

        // the 2nd allocation should start the day after tomorrow
        final LocalDateTime startDate1 = allocation1.getStartDate();
        final LocalDateTime expectedstartDate1 = IN_TWO_DAYS;
        assertEquals(expectedstartDate1, startDate1);

        // allocation expected of 8 hours
        final int duration1 = allocation1.getDuration();
        final int expectedDuration1 = 8 * 60;
        assertEquals(expectedDuration1, duration1);

        final TaskAllocation allocation2 = allocations.get(2);

        // the 3nd allocation
        final LocalDateTime startDate2 = allocation2.getStartDate();
        final LocalDateTime expectedstartDate2 = IN_THREE_DAYS;
        assertEquals(expectedstartDate2, startDate2);

        // allocation expected of 4 hours ( the remaining )
        final int duration2 = allocation2.getDuration();
        final int expectedDuration2 = 4 * 60;
        assertEquals(expectedDuration2, duration2);
    }

}
