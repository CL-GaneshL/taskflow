/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.TOMORROW;
import com.caratlane.taskflow.taskgenerator.generator.crud.TasksDbSerializer;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.generator.rules.TaskAllocationDistribution;
import static helpers.TestDBConstants.DURATION_SKILL_3_3DMS;
import static helpers.TestDBConstants.EMPLOYEE_CL0004;
import static helpers.TestDBConstants.ID_SKILL_3_3DMS;
import static helpers.TestDBConstants.NB_PRODUCTS_PROJECT_JADAU_1;
import static helpers.TestDBConstants.NWD_1;
import static helpers.TestDBConstants.PROJECT_JADAU_1;
import helpers.TestDBCrud;
import helpers.TestTaskGeneratorException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wdmtraining
 */
public class SerializeTasksTest {

    private static EmployeeData employeeData = null;
    private static ProjectData projectData = null;

    public SerializeTasksTest() {
    }

    @BeforeClass
    public static void setUpClass() {

        // company's non working days
        final boolean test = true;
        final NonWorkingDays nwdsInstance = NonWorkingDays.getInstance();
        nwdsInstance.addNwd(test, NWD_1);       // tomorrow

        // create one employee CL0004
        employeeData = new EmployeeData(EMPLOYEE_CL0004);
        employeeData.addSkill(ID_SKILL_3_3DMS);     // id = 3

        final Employees employeesInstance = Employees.getInstance();
        employeesInstance.addEmployeeData(employeeData);

        // set up non working days for that employee
        // non non working days, no holidays
    }

    @AfterClass
    public static void tearDownClass() {

        projectData = null;
        employeeData = null;

        final boolean test = true;
        final NonWorkingDays nwdsInstance = NonWorkingDays.getInstance();
        nwdsInstance.clearNwd(test);

        final Employees employeesInstance = Employees.getInstance();
        employeesInstance.clearEmployeeData(test);
    }

    @Before
    public void setUp() throws TestTaskGeneratorException {

        // remove any entities that could interfer 
        TestDBCrud.truncateTables();

        // clean up existing tasks from previous tests.
        final boolean test = true;
        employeeData.clearTaskAllocations(test);
    }

    @After
    public void tearDown() {

        // make sure a new project is used for every new test
        projectData = null;
    }

    /**
     * Test of generate method, of class Generator.
     *
     * @throws TestTaskGeneratorException
     * @throws TaskGeneratorException
     */
    @Test
    public void testAllocateTask_1() throws TestTaskGeneratorException, TaskGeneratorException {

        projectData = new ProjectData(PROJECT_JADAU_1);

        final Integer skill_id = ID_SKILL_3_3DMS;   // id = 3
        final Integer nb_products = NB_PRODUCTS_PROJECT_JADAU_1;    // nb probucts = 2

        TaskAllocationDistribution.allocateTask(projectData, skill_id, nb_products);

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

        // serialize tasks in the database.
        final LinkedList<ProjectData> projects = new LinkedList<>();
        projects.add(projectData);

        TasksDbSerializer.serialize(projects);

        // extract the data inserted in the db and check their accuraty
        final Integer project_id = projectData.getProject().getId();
        final List<Task> generatedTasks = TestDBCrud.getProjectTasks(project_id);

        final int nbExpectedGeneratedTasks = 1;
        final int nbGenetatedTasks = tasks.size();
        assertEquals(nbExpectedGeneratedTasks, nbGenetatedTasks);

        final Task generatedTask = generatedTasks.get(0);
        final Integer employee_id = employeeData.getEmployee().getId();
        final Integer generatedTaskId = generatedTask.getId();
        final List<TaskAllocation> generatedTaskAllocations
                = TestDBCrud.getEmployeeTaskAllocations(generatedTaskId, employee_id);

        final int nbExpectedGeneratedTaskAllocations = 1;
        final int nbGenetatedTaskAllocations = generatedTaskAllocations.size();
        assertEquals(nbExpectedGeneratedTaskAllocations, nbGenetatedTaskAllocations);
    }

}
