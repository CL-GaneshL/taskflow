/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.crud;

import com.caratlane.taskflow.taskgenerator.db.DBConnection;
import com.caratlane.taskflow.taskgenerator.db.DBException;
import com.caratlane.taskflow.taskgenerator.db.DBManager;
import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.getQueryName;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.ProjectSkill;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbProject;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbProjectSkill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_OPEN_PROJECTS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_PROJECT_SKILLS_SUFFIX;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_PROJECT_TASKS_SUFFIX;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbTask;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class ProjectsDbExtractor {

    private static final Byte OPEN_PROJECT = 1;

    /**
     *
     * @return @throws TaskGeneratorException
     */
    public static List<Project> getOpenProjects() throws TaskGeneratorException {

        final List<Project> projects = new LinkedList<>();

        final String queryName = getQueryName(DbProject.class, FIND_OPEN_PROJECTS_SUFFIX);
        final List<DbProject> dbProjects;
        DBConnection con = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();

            // query the database
            dbProjects = con.query(queryName, DbProject.class, "open", OPEN_PROJECT);

            // create a Task fom a DbTask
            final Function<DbProject, Project> mapper = (DbProject t) -> new Project(t);

            // build the list of Tasks to be returned
            final Consumer<Project> action = (Project t) -> {
                projects.add(t);
            };

            dbProjects.stream().map(mapper).forEach(action);

        } catch (DBException ex) {

            // ---------------------------------------------------------------------
            final String msg = "Failed to retrieve open Projects from the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(msg, ex);

        } finally {

            if (con != null) {
                con.close();
            }
        }

        return projects;
    }

    /**
     *
     * @param project_id
     * @return
     * @throws TaskGeneratorException
     */
    public static List<ProjectSkill> getProjectSkills(Integer project_id) throws TaskGeneratorException {

        final List<ProjectSkill> skills = new LinkedList<>();

        final String queryName = getQueryName(DbProjectSkill.class, FIND_PROJECT_SKILLS_SUFFIX);
        final List<DbProjectSkill> dbSkills;
        DBConnection con = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();

            // query the database
            dbSkills = con.query(queryName, DbProjectSkill.class, "project_id", project_id);

            // create a Task fom a DbTask
            final Function<DbProjectSkill, ProjectSkill> mapper = (DbProjectSkill t) -> new ProjectSkill(t);

            // build the list of Tasks to be returned
            final Consumer<ProjectSkill> action = (ProjectSkill t) -> {
                skills.add(t);
            };

            dbSkills.stream().map(mapper).forEach(action);

        } catch (DBException ex) {

            // ---------------------------------------------------------------------
            final String msg = "Failed to retrieve Project's Skills from the database.";
            LogManager.getLogger().log(Level.SEVERE, msg);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(msg, ex);

        } finally {

            if (con != null) {
                con.close();
            }
        }

        return skills;
    }

    /**
     *
     * @param project_id
     * @return
     * @throws TaskGeneratorException
     */
    public static List<Task> getProjectTasks(final Integer project_id)
            throws TaskGeneratorException {

        final List<Task> tasks = new LinkedList<>();

        final String queryName = getQueryName(DbTask.class, FIND_PROJECT_TASKS_SUFFIX);
        final List<DbTask> dbTasks;
        DBConnection con = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();

            // query the database
            dbTasks = con.query(queryName, DbTask.class, "project_id", project_id);

            // create a Task fom a DbTask
            final Function<DbTask, Task> mapper = (DbTask t) -> new Task(t);

            // build the list of Tasks to be returned
            final Consumer<Task> action = (Task t) -> {
                tasks.add(t);
            };

            dbTasks.stream().map(mapper).forEach(action);

        } catch (DBException ex) {

            // ---------------------------------------------------------------------
            final String pattern = "Failed to retrieve Tasks for Project {0}.";
            LogManager.getLogger().log(Level.SEVERE, pattern, project_id);
            // ---------------------------------------------------------------------

            throw new TaskGeneratorException(ex);

        } finally {

            if (con != null) {
                con.close();
            }
        }

        return tasks;
    }

}
