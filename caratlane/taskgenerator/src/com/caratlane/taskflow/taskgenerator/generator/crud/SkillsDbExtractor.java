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
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbSkill;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.getQueryName;
import static com.caratlane.taskflow.taskgenerator.generator.dbmodel.DbQueries.FIND_SKILLS_SUFFIX;
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
public class SkillsDbExtractor {

    private static final Byte OPEN_SKILL = 1;

    public static List<Skill> getAllSkills() throws TaskGeneratorException {

        final List<Skill> skills = new LinkedList<>();

        final String queryName = getQueryName(DbSkill.class, FIND_SKILLS_SUFFIX);
        final List<DbSkill> dbSkills;
        DBConnection con = null;

        try {
            con = DBManager.getDatabaseInstance().getConnection().open();

            // query the database
            dbSkills = con.query(queryName, DbSkill.class, "open", OPEN_SKILL);

            // create a Task fom a DbTask
            final Function<DbSkill, Skill> mapper = (DbSkill t) -> new Skill(t);

            // build the list of Tasks to be returned
            final Consumer<Skill> action = (Skill t) -> {
                skills.add(t);
            };

            dbSkills.stream().map(mapper).forEach(action);

        } catch (DBException ex) {

            // ---------------------------------------------------------------------
            final String msg = "Failed to retrieve opened Skills from the database.";
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

   
}
