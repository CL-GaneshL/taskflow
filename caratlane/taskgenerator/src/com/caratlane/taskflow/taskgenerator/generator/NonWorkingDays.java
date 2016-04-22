/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.dao.NonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.crud.NwdsDbExtractor;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author wdmtraining
 */
public class NonWorkingDays {

    private static LinkedList<NonWorkingDay> nwds = null;

    private NonWorkingDays() {
    }

    public static NonWorkingDays getInstance() {
        return NonWorkingDaysHolder.INSTANCE;
    }

    private static class NonWorkingDaysHolder {

        private static final NonWorkingDays INSTANCE = new NonWorkingDays();
    }

//    public List<NonWorkingDays> getNonWokingDays() {
//        return nwds;
//    }
    public static void initialize(final Date from) throws TaskGeneratorException {

        nwds = NwdsDbExtractor.getNonWorkingDays(from);
    }

    /**
     *
     * @return
     */
    public LinkedList<NonWorkingDay> getNwds() {
        return nwds;
    }

    // ==========================================================
    // - helpers for testing purposes only 
    // ==========================================================
    /**
     * For testing purpose only.
     *
     * @param test
     * @throws TaskGeneratorException
     */
    public static void initialize(boolean test) throws TaskGeneratorException {

        nwds = new LinkedList<>();
    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @param nwd
     * @para
     */
    public void addNwd(boolean test, final NonWorkingDay nwd) {

        nwds.add(nwd);

        // ---------------------------------------------------------------------
//        LogManager.getLogger().log(Level.FINE, "Added Non Working Day = {0}", nwd);
        // ---------------------------------------------------------------------
    }

    /**
     * For testing purpose only.
     *
     * @param test
     */
    public void clearNwds(boolean test) {
        nwds.clear();
    }
}
