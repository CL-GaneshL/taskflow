/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorRuntimeException;
import com.caratlane.taskflow.taskgenerator.generator.crud.ProjectsDbExtractor;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.ProjectSkill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Stream;

/**
 *
 * @author wdmtraining
 */
public class Projects {

    private static LinkedList<ProjectData> projects = null;

    private Projects() {
    }

    /**
     *
     * @return
     */
    public static Projects getInstance() {
        return ProjectsHolder.INSTANCE;
    }

    /**
     *
     */
    private static class ProjectsHolder {

        private static final Projects INSTANCE = new Projects();
    }

    /**
     *
     * @return
     */
    public LinkedList<ProjectData> getProjectsData() {
        return projects;
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    public static void initialize() throws TaskGeneratorException {

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        LogManager.getLogger().log(Level.FINE, "Initializing projects ...");
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        // ---------------------------------------------------------------------

        projects = new LinkedList<>();

        try {
            // extract all open projects
            final List<Project> openProjects
                    = ProjectsDbExtractor.getOpenProjects();

            // ---------------------------------------------------------------------
            if (LogManager.isTestLoggable()) {
                LogManager.logTestMsg(Level.INFO, "Projects created : ");
            } // ---------------------------------------------------------------------     

            if (openProjects.isEmpty()) {
                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                LogManager.getLogger().log(Level.FINE, " No open Project !!!!!!!!!!!!!");
                LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                // ---------------------------------------------------------------------  

                // ---------------------------------------------------------------------
                if (LogManager.isTestLoggable()) {
                    LogManager.logTestMsg(Level.SEVERE, " - No Projects found in the Database !");
                } // ---------------------------------------------------------------------     
            }

            openProjects.forEach((Project project) -> {

                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.FINE, " {0}", project.toString());
                // --------------------------------------------------------------------- 

                // ---------------------------------------------------------------------
                if (LogManager.isTestLoggable()) {
                    LogManager.logTestMsg(Level.INFO, "  " + project);
                } // ---------------------------------------------------------------------         

                try {
                    final ProjectData projectData = new ProjectData(project);
                    projects.add(projectData);

                    final Integer project_id = project.getId();

                    // extract the list of skills for that project
                    final List<ProjectSkill> projectSkills
                            = ProjectsDbExtractor.getProjectSkills(project_id);

                    // ---------------------------------------------------------------------
                    if (LogManager.isTestLoggable()) {
                        LogManager.logTestMsg(Level.INFO, "  Skills associated to Projects : ");
                    } // ---------------------------------------------------------------------  

                    if (projectSkills.isEmpty()) {
                        // ---------------------------------------------------------------------
                        LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                        LogManager.getLogger().log(Level.FINE, " Project has no Skills !!!!!!!!!!!!!");
                        LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                        // ---------------------------------------------------------------------

                        // ---------------------------------------------------------------------
                        if (LogManager.isTestLoggable()) {
                            LogManager.logTestMsg(Level.WARNING, "  Project has no associated Skills.");
                        } // ---------------------------------------------------------------------  
                    }

                    projectSkills.stream().forEach((ProjectSkill projectSkill) -> {

                        final Integer skill_id = projectSkill.getSkillId();

                        // check that the skill exists in the db
                        final Skill skill = Skills.getInstance().getSkill(skill_id);

                        if (skill == null) {
                            // ---------------------------------------------------------------------
                            LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                            LogManager.getLogger().log(Level.FINE, " Skill = {0} does not exist in the database !!!!!!!!!!!", skill_id);
                            LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                            // ---------------------------------------------------------------------  
                            
                            // ---------------------------------------------------------------------
                            if (LogManager.isTestLoggable()) {
                                LogManager.logTestMsg(Level.SEVERE, "   Skill Id = " + skill_id + " not in the database. Check with DB Administrator.");
                            } // ---------------------------------------------------------------------
                            
                        } else {

                            final boolean open = skill.getOpen() == (byte) 1;

                            if (open) {
                                // ---------------------------------------------------------------------
                                LogManager.getLogger().log(Level.FINE, "  {0}", skill.toString());
                                // ---------------------------------------------------------------------

                                // ---------------------------------------------------------------------
                                if (LogManager.isTestLoggable()) {
                                    LogManager.logTestMsg(Level.INFO, "  " + skill);
                                }  // ---------------------------------------------------------------------
                            } else {

                                // ---------------------------------------------------------------------
                                LogManager.getLogger().log(Level.FINE, "  CLOSED SKILL : {0}", skill.toString());
                                // ---------------------------------------------------------------------

                                // ---------------------------------------------------------------------
                                if (LogManager.isTestLoggable()) {
                                    LogManager.logTestMsg(Level.INFO, "  CLOSED SKILL - REMOVE SKILL FROM EMPLOYEE : " + skill);
                                } // ---------------------------------------------------------------------
                            }
                        }

                        // add the skill to the project
                        projectData.addSkill(skill_id);
                    });

                } catch (TaskGeneratorException ex) {
                    // ---------------------------------------------------------------------
                    LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
                    // ---------------------------------------------------------------------
                    throw new TaskGeneratorRuntimeException(ex);
                }
            });

        } catch (TaskGeneratorRuntimeException ex) {

            // ---------------------------------------------------------------------
            LogManager.getLogger().log(Level.SEVERE, ex.getMessage());
            // ---------------------------------------------------------------------    

            throw new TaskGeneratorException(ex);
        }
    }

    /**
     *
     * @param priority
     * @return
     */
    public LinkedList<ProjectData> getSortedProjectsData(final Integer priority) {

        final LinkedList<ProjectData> sortedProjects = new LinkedList<>();

        // search for project with priority 
        final Stream<ProjectData> prioritized
                = projects.stream().filter((ProjectData project) -> {
                    final Integer projectPriority = project.getProject().getPriority();
                    return projectPriority.equals(priority);
                });

        // sort them by date desc
        final Stream<ProjectData> sorted
                = prioritized.sorted((ProjectData p1, ProjectData p2) -> {

                    final LocalDateTime start_date1 = p1.getProject().getStartDate();
                    final LocalDateTime start_date2 = p2.getProject().getStartDate();

                    // by desc order
                    return start_date1.compareTo(start_date2);
                });

        // return a ordered linked list
        sorted.forEach((ProjectData project) -> {
            sortedProjects.add(project);
        });

        return sortedProjects;
    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @throws TaskGeneratorException
     */
    public static void initialize(boolean test) throws TaskGeneratorException {

        projects = new LinkedList<>();
    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @param project
     * @para
     */
    public void addProjectData(boolean test, final ProjectData project) {
        projects.add(project);
    }

    /**
     * For testing purpose only.
     *
     * @param test
     */
    public void clearProjectsData(boolean test) {
        projects.clear();
    }

}
