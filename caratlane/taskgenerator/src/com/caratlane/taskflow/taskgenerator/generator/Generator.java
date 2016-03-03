/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import java.util.logging.Level;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorRuntimeException;
import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.EmployeeSkill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Holiday;
import com.caratlane.taskflow.taskgenerator.generator.dao.NonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.ProjectSkill;
import com.caratlane.taskflow.taskgenerator.generator.crud.EmployeesDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.crud.ProjectsDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.crud.TaskDbReseter;
import com.caratlane.taskflow.taskgenerator.generator.crud.TasksDbSerializer;
import com.caratlane.taskflow.taskgenerator.generator.rules.TaskAllocationDistribution;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author wdmtraining
 */
public class Generator {

    private List<ProjectData> projects = null;

    public Generator() {
        this.projects = new LinkedList<>();
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    public void reset() throws TaskGeneratorException {
        
        TaskDbReseter.resetTasks();
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    public void generate() throws TaskGeneratorException {

        // ===========================================================================
        LogManager.getLogger().log(Level.FINE, "Starting generation ...");
        // ===========================================================================

        this.initialize();

        try {

            // for each project ...
            this.projects.stream().forEach((ProjectData projectData) -> {

                final Project project = projectData.getProject();
                final List<Integer> projectSkills = projectData.getSkills();

//                final Integer project_id = project.getId();
                final Integer nb_products = project.getNbProducts();

                // ... find the most suitable employee for a particular skill and allocate the task.
                projectSkills.stream().forEach((Integer skill_id) -> {

                    try {
                        TaskAllocationDistribution.allocateTask(projectData, skill_id, nb_products);

                    } catch (TaskGeneratorException ex) {
                        // ---------------------------------------------------------------------
                        LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
                        // ---------------------------------------------------------------------
                        throw new TaskGeneratorRuntimeException(ex);
                    }
                });

            });

            // serialize tasks in the database.
            TasksDbSerializer.serialize(projects);

        } catch (TaskGeneratorRuntimeException ex) {

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
            // ---------------------------------------------------------------------    
            throw new TaskGeneratorException(ex);

        } finally {

            // ===========================================================================
            LogManager.getLogger().log(Level.FINE, "... generation terminated !");
            // ===========================================================================
        }
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    private void initialize() throws TaskGeneratorException {

        try {
            // ---------------------------------------------
            // non working days to come 
            // ---------------------------------------------
            NonWorkingDays.initialize();

            // ---------------------------------------------
            // list of all available skills
            // ---------------------------------------------
            Skills.initialize();

            // ---------------------------------------------
            // list of all employees
            // ---------------------------------------------
            Employees.initialize();

            // ---------------------------------------------
            // build project's information
            // ---------------------------------------------
            // project are sorted by higthest priority and earliest start_date 
            List<Project> openProjects = ProjectsDbExtractor.getOpenProjects();
            openProjects.stream().forEach((Project project) -> {

                try {
                    // create a project record
                    final ProjectData projectData = new ProjectData(project);
                    this.projects.add(projectData);

                    final Integer project_id = project.getId();

                    // extract the list of skills for that project
                    final List<ProjectSkill> projectSkills
                            = ProjectsDbExtractor.getProjectSkills(project_id);

                    projectSkills.stream().forEach((ProjectSkill skill) -> {
                        projectData.addSkill(skill.getSkillId());
                    });

                } catch (TaskGeneratorException ex) {
                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
                    // ---------------------------------------------------------------------
                    throw new TaskGeneratorRuntimeException(ex);
                }
            });

            // ---------------------------------------------
            // build employee's information
            // ---------------------------------------------
            final List<Employee> empls = EmployeesDbExtractor.getAllEmployees();
            empls.stream().forEach((Employee employee) -> {

                try {
                    // create an employee record
                    final EmployeeData employeeData = new EmployeeData(employee);
                    final Employees instance = Employees.getInstance();
                    instance.addEmployeeData(employeeData);

                    // the unique employee id
                    final Integer employee_id = employee.getId();

                    // extract the list of skills for this employee
                    final List<EmployeeSkill> employeeSkills
                            = EmployeesDbExtractor.getEmployeeSkills(employee_id);

                    employeeSkills.stream().forEach((EmployeeSkill skill) -> {
                        employeeData.addSkill(skill.getSkillId());
                    });

                    // holidays for this employee
                    final LinkedList<Holiday> holidays
                            = EmployeesDbExtractor.getEmployeeHolidays(employee_id);

                    final LinkedList<NonWorkingDay> nwds = NonWorkingDays.getInstance().getNwds();
                    employeeData.setEmployeeNonWorkingDays(holidays, nwds);

                    // tasks for this employees
//                    final List<Task> tasks
//                            = EmployeesDbExtractor.getEmployeeTaskInfos(employee_id);
//
//                    employeeData.setTaskInfos(tasks);
                } catch (TaskGeneratorException ex) {
                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
                    // ---------------------------------------------------------------------
                    throw new TaskGeneratorRuntimeException(ex);
                }

            });

        } catch (TaskGeneratorRuntimeException | TaskGeneratorException ex) {

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
            // ---------------------------------------------------------------------    

            throw new TaskGeneratorException(ex);
        }
    }

}
