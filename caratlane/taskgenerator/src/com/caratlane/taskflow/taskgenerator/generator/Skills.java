/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import com.caratlane.taskflow.taskgenerator.generator.crud.SkillsDbExtractor;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class Skills {

    private static List<Skill> skills = null;

    private Skills() {
    }

    /**
     *
     * @return
     */
    public static Skills getInstance() {
        return SkillsHolder.INSTANCE;
    }

    /**
     *
     */
    private static class SkillsHolder {

        private static final Skills INSTANCE = new Skills();
    }

    /**
     *
     * @throws TaskGeneratorException
     */
    public static void initialize() throws TaskGeneratorException {

        // ---------------------------------------------------------------------
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        LogManager.getLogger().log(Level.FINE, "Initializing skills ...");
        LogManager.getLogger().log(Level.FINE, "-----------------------------------------");
        // ---------------------------------------------------------------------

        Skills.skills = SkillsDbExtractor.getAllSkills();

        // ---------------------------------------------------------------------
        if (LogManager.isTestLoggable()) {
            LogManager.logTestMsg(Level.INFO, "Skills created : ");
        } // ---------------------------------------------------------------------  

        // ---------------------------------------------------------------------
        if (Skills.skills.isEmpty()) {

            if (LogManager.getLogger().isLoggable(Level.FINE)) {
                LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
                LogManager.getLogger().log(Level.FINE, " No Skills in the database !!!!!!!!!!!!!");
                LogManager.getLogger().log(Level.FINE, "??????????????????????????????????????????");
            }

            if (LogManager.isTestLoggable()) {
                LogManager.logTestMsg(Level.SEVERE, " - No Skills found in the Database !");
            }
        } // ---------------------------------------------------------------------

        // ---------------------------------------------------------------------
        Skills.skills.stream().forEach((skill) -> {

            final boolean open = skill.getOpen() == (byte) 1;

            if (open) {
                LogManager.getLogger().log(Level.FINE, "  {0}", skill.toString());

                if (LogManager.isTestLoggable()) {
                    LogManager.logTestMsg(Level.INFO, "  " + skill);
                }
            } else {
                LogManager.getLogger().log(Level.FINE, "  CLOSED : {0}", skill.toString());

                if (LogManager.isTestLoggable()) {
                    LogManager.logTestMsg(Level.INFO, "  CLOSED :" + skill);
                }
            }

        }); // ---------------------------------------------------------------------

    }

    /**
     *
     * @param skill_id
     * @return
     */
    public Skill getSkill(final Integer skill_id) {

        Skill skill = null;

        final Predicate<Skill> filter = (Skill s) -> s.getId().equals(skill_id);
        final Optional<Skill> opt = Skills.skills.stream().filter(filter).findFirst();

        if (opt.isPresent()) {
            skill = opt.get();
        }

        // return null if not skill found
        return skill;
    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @throws TaskGeneratorException
     */
    public static void initialize(boolean test) throws TaskGeneratorException {

        Skills.skills = new LinkedList<>();
    }

    /**
     *
     * @param skill
     */
    public void addSkill(final Skill skill) {
        Skills.skills.add(skill);
    }

    /**
     * For testing purpose only.
     *
     * @param test
     */
    public void clearSkills(boolean test) {
        Skills.skills.clear();
    }

}
