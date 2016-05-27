/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.dao.NonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.crud.NwdsDbExtractor;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;

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

    /**
     *
     * @param from
     * @throws TaskGeneratorException
     */
    public static void initialize(final Date from) throws TaskGeneratorException {

        NonWorkingDays.nwds = NwdsDbExtractor.getNonWorkingDays(from);

        // ---------------------------------------------------------------------
        if (LogManager.isTestLoggable()) {

            LogManager.logTestMsg(Level.INFO, "Non Working Days recorded : ");

            if (NonWorkingDays.nwds.isEmpty()) {
                LogManager.logTestMsg(Level.SEVERE, " - No Non Working Days found in the Database !");
            } else {
                NonWorkingDays.nwds.stream().forEach((NonWorkingDay nwd) -> {
                    LogManager.logTestMsg(Level.INFO, "  " + nwd);
                });
            }
        } // ---------------------------------------------------------------------
    }

    /**
     *
     * @return
     */
    public LinkedList<NonWorkingDay> getNwds() {
        return NonWorkingDays.nwds;
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

        NonWorkingDays.nwds = new LinkedList<>();
    }

    /**
     * For testing purpose only.
     *
     * @param test
     * @param nwd
     * @para
     */
    public void addNwd(boolean test, final NonWorkingDay nwd) {

        NonWorkingDays.nwds.add(nwd);

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
        NonWorkingDays.nwds.clear();
    }
}
