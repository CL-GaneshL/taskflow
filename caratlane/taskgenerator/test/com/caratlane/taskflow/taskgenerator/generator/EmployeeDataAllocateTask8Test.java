/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.IN_FIVE_DAYS;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.IN_FOUR_DAYS;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.IN_THREE_DAYS;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.TOMORROW;
import com.caratlane.taskflow.taskgenerator.generator.dao.Holiday;
import com.caratlane.taskflow.taskgenerator.generator.dao.NonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.rules.TaskAllocator;
import helpers.TestTaskGeneratorException;
import static helpers.TestDBConstants.DURATION_SKILL_3_3DMS;
import static helpers.TestDBConstants.EMPLOYEE_CL0004;
import static helpers.TestDBConstants.HOLIDAY_2;
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
public class EmployeeDataAllocateTask8Test {

    final static boolean test = true;

    private static EmployeeData employeeData = null;
    private static ProjectData projectData = null;

    public EmployeeDataAllocateTask8Test() {
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
        final LinkedList<NonWorkingDay> nwds = NonWorkingDays.getInstance().getNwds();
        final LinkedList<Holiday> holidays = new LinkedList<>();
        holidays.add(HOLIDAY_2);    // holidays in two days
        employeeData.setEmployeeNonWorkingDays(holidays, nwds);

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
    // - Using the same data as EmployeeDataAllocateTask1Test,
    // - however introducting an holiday day.
    // - IN_TWO_DAYS is an holiday day, tasks are expected to be allocated
    // - in IN_THREE_DAYS onwards.
    // - we expecte the exact same results as EmployeeDataAllocateTask4Test,
    // - as the non working day is replaced by an holiday day.
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

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, employeeData, skill_id, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        // expect only one allocation of 240 mins ( 4 hours )
        final LinkedList<TaskAllocation> allocations
                = employeeData.getTaskAllocations().getAllocations();
        final int nbExpectedAllocations = 1;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation = allocations.getLast();

        // no previous allocation, so expected to be the first 
        // allocation in 2 days ( tomorrow is Non Working Day)
        final LocalDateTime startDate = allocation.getStartDate();
        final LocalDateTime expectedstartDate = IN_THREE_DAYS;
        assertEquals(expectedstartDate, startDate);

        // allocation expected of 4 hours ( which is also the total duration )
        final int duration = allocation.getDuration();
        final int expectedDuration = NB_PRODUCTS_PROJECT_JADAU_1 * DURATION_SKILL_3_3DMS;  // 2 * 120 = 240 mins
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

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, employeeData, skill_id, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        // expect only one allocation of 480 mins ( 8 hours )
        final LinkedList<TaskAllocation> allocations
                = employeeData.getTaskAllocations().getAllocations();
        final int nbExpectedAllocations = 1;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation = allocations.getLast();

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate = allocation.getStartDate();
        final LocalDateTime expectedstartDate = IN_THREE_DAYS;
        assertEquals(expectedstartDate, startDate);

        // allocation expected of 8 hours ( which is also the total duration )
        final int duration = allocation.getDuration();
        final int expectedDuration = NB_PRODUCTS_PROJECT_JADAU_2 * DURATION_SKILL_3_3DMS;  // 4 * 120 = 480 mins
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

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, employeeData, skill_id, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        // expect 2 allocations
        final LinkedList<TaskAllocation> allocations
                = employeeData.getTaskAllocations().getAllocations();
        final int nbExpectedAllocations = 2;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation0 = allocations.get(0);

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate0 = allocation0.getStartDate();
        final LocalDateTime expectedstartDate0 = IN_THREE_DAYS;
        assertEquals(expectedstartDate0, startDate0);

        // allocation expected of 8 hours ( full shift allocation )
        final int duration0 = allocation0.getDuration();
        final int expectedDuration0 = 8 * 60;
        assertEquals(expectedDuration0, duration0);

        final TaskAllocation allocation1 = allocations.get(1);

        // the 2nd allocation should start in 3 days ( tomorrow + 1 non working day )
        final LocalDateTime startDate1 = allocation1.getStartDate();
        final LocalDateTime expectedstartDate1 = IN_FOUR_DAYS;
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

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, employeeData, skill_id, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        // expect 2 allocations
        final LinkedList<TaskAllocation> allocations
                = employeeData.getTaskAllocations().getAllocations();
        final int nbExpectedAllocations = 2;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation0 = allocations.get(0);

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate0 = allocation0.getStartDate();
        final LocalDateTime expectedstartDate0 = IN_THREE_DAYS;
        assertEquals(expectedstartDate0, startDate0);

        // allocation expected of 8 hours ( full shift allocation )
        final int duration0 = allocation0.getDuration();
        final int expectedDuration0 = 8 * 60;
        assertEquals(expectedDuration0, duration0);

        final TaskAllocation allocation1 = allocations.get(1);

        // the 2nd allocation should start in 3 days ( tomorrow + 1 non working day )
        final LocalDateTime startDate1 = allocation1.getStartDate();
        final LocalDateTime expectedstartDate1 = IN_FOUR_DAYS;
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

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, employeeData, skill_id, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        // expect 3 allocations
        final LinkedList<TaskAllocation> allocations
                = employeeData.getTaskAllocations().getAllocations();
        final int nbExpectedAllocations = 3;
        assertEquals(nbExpectedAllocations, allocations.size());

        final TaskAllocation allocation0 = allocations.get(0);

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate0 = allocation0.getStartDate();
        final LocalDateTime expectedstartDate0 = IN_THREE_DAYS;
        assertEquals(expectedstartDate0, startDate0);

        // allocation expected of 8 hours ( full shift allocation )
        final int duration0 = allocation0.getDuration();
        final int expectedDuration0 = 8 * 60;
        assertEquals(expectedDuration0, duration0);

        final TaskAllocation allocation1 = allocations.get(1);

        // the 2nd allocation should start in 3 days ( tomorrow + 1 non working day )
        final LocalDateTime startDate1 = allocation1.getStartDate();
        final LocalDateTime expectedstartDate1 = IN_FOUR_DAYS;
        assertEquals(expectedstartDate1, startDate1);

        // allocation expected of 8 hours
        final int duration1 = allocation1.getDuration();
        final int expectedDuration1 = 8 * 60;
        assertEquals(expectedDuration1, duration1);

        final TaskAllocation allocation2 = allocations.get(2);

        // the 3nd allocation
        final LocalDateTime startDate2 = allocation2.getStartDate();
        final LocalDateTime expectedstartDate2 = IN_FIVE_DAYS;
        assertEquals(expectedstartDate2, startDate2);

        // allocation expected of 4 hours ( the remaining )
        final int duration2 = allocation2.getDuration();
        final int expectedDuration2 = 4 * 60;
        assertEquals(expectedDuration2, duration2);
    }

}
