/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.crud.TasksDbSerializer;
import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.generator.rules.TaskAllocator;
import static helpers.TestDBConstants.DURATION_SKILL_3_3DMS;
import static helpers.TestDBConstants.EMPLOYEE_CL0004;
import static helpers.TestDBConstants.NB_PRODUCTS_PROJECT_JADAU_6;
import static helpers.TestDBConstants.PROJECT_JADAU_6;
import static helpers.TestDBConstants.SKILL_3_3DMS;
import static helpers.TestDBConstants.TOMORROW;
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
public class SerializeTasks2Test {

    final static boolean test = true;

    private static EmployeeData employeeData = null;
    private static ProjectData projectData = null;

    static Integer ID_SKILL = null;

    public SerializeTasks2Test() {
    }

    @BeforeClass
    public static void setUpClass() {

//        // company's non working days
//        final NonWorkingDays nwdsInstance = NonWorkingDays.getInstance();
//        nwdsInstance.addNwd(test, NWD_1);       // tomorrow
//
//        // set up non working days for that employee
//        // non non working days, no holidays
    }

    @AfterClass
    public static void tearDownClass() throws TestTaskGeneratorException {

        projectData = null;
        employeeData = null;

//        final NonWorkingDays nwdsInstance = NonWorkingDays.getInstance();
//        nwdsInstance.clearNwd(test);
        // de-serialize
        TestDBCrud.truncateTables();
    }

    @Before
    public void setUp() throws TestTaskGeneratorException {

        // de-serialize
        TestDBCrud.truncateTables();

        // remove data from memory
        Employees.getInstance().clearEmployeeData(test);
        Skills.getInstance().clearSkills(test);
        Projects.getInstance().clearProjectsData(test);
        Employees.getInstance().clearEmployeeData(test);
        Tasks.getInstance().clearTask(test);

        // create the employee CL0004
        final Employee employee = new Employee(
                EMPLOYEE_CL0004.getEmployeeId(),
                EMPLOYEE_CL0004.getProductivity(),
                EMPLOYEE_CL0004.getEmployementType()
        );

        TestDBCrud.serializeEmployee(employee);
        employeeData = new EmployeeData(employee);
        Employees.getInstance().addEmployeeData(test, employeeData);

        // create a skill
        final Skill skill = new Skill(
                SKILL_3_3DMS.getReference(),
                SKILL_3_3DMS.getDesignation(),
                SKILL_3_3DMS.getDuration(),
                SKILL_3_3DMS.getOpen()
        );

        ID_SKILL = TestDBCrud.serializeSkill(skill);
        final Skills skills = Skills.getInstance();
        skills.addSkill(skill);

        // attach the skill to the employee
        employeeData.addSkill(ID_SKILL);

        // create the project PROJECT_JADAU_6       
        final Project project
                = new Project(
                        PROJECT_JADAU_6.getReference(),
                        PROJECT_JADAU_6.getTemplateId(),
                        PROJECT_JADAU_6.getNbProducts(),
                        PROJECT_JADAU_6.getPriority(),
                        PROJECT_JADAU_6.getStartDate(test),
                        PROJECT_JADAU_6.getOpen(test)
                );

        TestDBCrud.serializeProject(project);
        projectData = new ProjectData(project);
        Projects.getInstance().addProjectData(test, projectData);

    }

    @After
    public void tearDown() {

        projectData = null;
        employeeData = null;

    }

    /**
     * Test of generate method, of class Generator. No previous allocation in
     * the database, the project's start date is in 2 days.
     *
     * @throws TestTaskGeneratorException
     * @throws TaskGeneratorException
     */
    @Test
    public void testAllocateTask_1() throws TestTaskGeneratorException, TaskGeneratorException {

        final Integer nb_products = NB_PRODUCTS_PROJECT_JADAU_6;    // nb probucts = 2

        (new TaskAllocator(TOMORROW)).allocate(test, projectData, employeeData, ID_SKILL, nb_products);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

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
        final int expectedDuration = NB_PRODUCTS_PROJECT_JADAU_6 * DURATION_SKILL_3_3DMS;  // 2 * 120 = 240 mins
        assertEquals(expectedDuration, duration);

        // serialize tasks in the database.
        final LinkedList<ProjectData> projects = Projects.getInstance().getProjectsData();
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
