/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.TOMORROW;
import com.caratlane.taskflow.taskgenerator.generator.crud.DataDbSerializer;
import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.generator.rules.TaskAllocator;
import static utils.TestDBConstants.DURATION_SKILL_3_3DMS;
import static utils.TestDBConstants.EMPLOYEE_CL0004;
import static utils.TestDBConstants.NB_PRODUCTS_PROJECT_JADAU_1;
import static utils.TestDBConstants.PROJECT_JADAU_1;
import static utils.TestDBConstants.SKILL_3_3DMS;
import utils.TestDBCrud;
import utils.TestTaskGeneratorException;
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
public class SerializeTasks3Test {

    final static boolean test = true;

    private static EmployeeData employeeData = null;
    private static ProjectData projectData = null;

    static Integer ID_SKILL = null;
    static Integer ID_PROJECT = null;
    static Integer ID_EMPLOYEE = null;

    public SerializeTasks3Test() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() throws TestTaskGeneratorException {

        projectData = null;
        employeeData = null;

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

        ID_EMPLOYEE = TestDBCrud.serializeEmployee(employee);
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

        // create the project PROJECT_JADAU_1       
        final Project project
                = new Project(
                        PROJECT_JADAU_1.getReference(),
                        PROJECT_JADAU_1.getTemplateId(),
                        PROJECT_JADAU_1.getNbProducts(),
                        PROJECT_JADAU_1.getPriority(),
                        PROJECT_JADAU_1.getStartDate(test),
                        PROJECT_JADAU_1.getEndDate(test),
                        PROJECT_JADAU_1.getOpen(test)
                );

        ID_PROJECT = TestDBCrud.serializeProject(project);
        projectData = new ProjectData(project);
        projectData.addSkill(ID_SKILL);
        Projects.getInstance().addProjectData(test, projectData);
    }

    @After
    public void tearDown() {

        projectData = null;
        employeeData = null;

    }

    /**
     * Test of generate method, of class Generator. No previous allocations in
     * the database. Re-allocate twice the same task, we should see no change
     * after the 2nd allocation. This test simulates 2 successive calls to the
     * TaskGenerator program, the 1st generates and serializes tasks and
     * allocations, the 2nd re-allocate the same tasks and allocations.
     *
     * @throws TestTaskGeneratorException
     * @throws TaskGeneratorException
     */
    @Test
    public void testAllocateTask_1() throws TestTaskGeneratorException, TaskGeneratorException {

        // allocate for the 1st time
        _1st_allocation();

        // remove data from memory
        Employees.getInstance().clearEmployeeData(test);
        Skills.getInstance().clearSkills(test);
        Projects.getInstance().clearProjectsData(test);
        Employees.getInstance().clearEmployeeData(test);
        Tasks.getInstance().clearTask(test);

        // create the employee CL0004
        final Employee employee = new Employee(
                ID_EMPLOYEE,
                EMPLOYEE_CL0004.getEmployeeId(),
                EMPLOYEE_CL0004.getProductivity(),
                EMPLOYEE_CL0004.getEmployementType()
        );

        employeeData = new EmployeeData(employee);
        Employees.getInstance().addEmployeeData(test, employeeData);

        // re create a skill
        final Skill skill = new Skill(
                ID_SKILL,
                SKILL_3_3DMS.getReference(),
                SKILL_3_3DMS.getDesignation(),
                SKILL_3_3DMS.getDuration(),
                SKILL_3_3DMS.getOpen()
        );

        final Skills skills = Skills.getInstance();
        skills.addSkill(skill);

        // attach the skill to the employee
        employeeData.addSkill(ID_SKILL);

        // create the project PROJECT_JADAU_1       
        final Project project
                = new Project(
                        ID_PROJECT,
                        PROJECT_JADAU_1.getReference(),
                        PROJECT_JADAU_1.getTemplateId(),
                        PROJECT_JADAU_1.getNbProducts(),
                        PROJECT_JADAU_1.getPriority(),
                        PROJECT_JADAU_1.getStartDate(test),
                        PROJECT_JADAU_1.getEndDate(test),
                        PROJECT_JADAU_1.getOpen(test)
                );

        projectData = new ProjectData(project);
        projectData.addSkill(ID_SKILL);
        Projects.getInstance().addProjectData(test, projectData);

        _2nd_allocation();
    }

    public void _1st_allocation() throws TestTaskGeneratorException, TaskGeneratorException {

        (new TaskAllocator(TOMORROW)).allocate(projectData);

        // expect only one task
        final LinkedList<Task> tasks = projectData.getTasks();
        final int nbExpectedTasks = 1;
        final int nbTasks = tasks.size();
        assertEquals(nbExpectedTasks, nbTasks);

        final Task task = tasks.get(0);

        // task of duration 240 mins ( 4 hours )
//        final int totalDuration = task.getTotalDuration();
        final int expectedTotalDuration = NB_PRODUCTS_PROJECT_JADAU_1 * DURATION_SKILL_3_3DMS;  // 2 * 120 = 240 mins
//        assertEquals(expectedTotalDuration, totalDuration);

        // expect only one allocation of 240 mins ( 4 hours )
        final LinkedList<TaskAllocation> allocations
                = employeeData.getTaskAllocations().getAllocations();
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
        DataDbSerializer.serialize();

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
                = TestDBCrud.getEmployeeTaskAllocations(generatedTaskId, employee_id).getAllocations();

        final int nbExpectedGeneratedTaskAllocations = 1;
        final int nbGenetatedTaskAllocations = generatedTaskAllocations.size();
        assertEquals(nbExpectedGeneratedTaskAllocations, nbGenetatedTaskAllocations);
    }

    public void _2nd_allocation() throws TestTaskGeneratorException, TaskGeneratorException {

        (new TaskAllocator(TOMORROW)).allocate(projectData);

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

        // no previous allocation, so expected to be the first allocation tomorrow
        final LocalDateTime startDate = allocation.getStartDate();
        final LocalDateTime expectedstartDate = TOMORROW;
        assertEquals(expectedstartDate, startDate);

        // allocation expected of 4 hours ( which is also the total duration )
        final int duration = allocation.getDuration();
        final int expectedDuration = NB_PRODUCTS_PROJECT_JADAU_1 * DURATION_SKILL_3_3DMS;  // 2 * 120 = 240 mins
        assertEquals(expectedDuration, duration);

        // serialize tasks in the database.
        DataDbSerializer.serialize();

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
                = TestDBCrud.getEmployeeTaskAllocations(generatedTaskId, employee_id).getAllocations();

        final int nbExpectedGeneratedTaskAllocations = 1;
        final int nbGenetatedTaskAllocations = generatedTaskAllocations.size();
        assertEquals(nbExpectedGeneratedTaskAllocations, nbGenetatedTaskAllocations);
    }
}
