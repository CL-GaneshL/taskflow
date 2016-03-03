/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import com.caratlane.taskflow.taskgenerator.generator.crud.SkillsDbExtractor;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

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

        skills = SkillsDbExtractor.getOpenSkills();
    }

    /**
     *
     * @param skill_id
     * @return
     */
    public Skill getSkill(final Integer skill_id) {

        final Predicate<Skill> filter = (Skill s) -> s.getId().equals(skill_id);
        return skills.stream().filter(filter).findFirst().get();

    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @throws TaskGeneratorException
     */
    public static void initialize(boolean test) throws TaskGeneratorException {

        skills = new LinkedList<>();
    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @param skill
     * @para
     */
    public void addSkill(boolean test, final Skill skill) {
        skills.add(skill);
    }

}
