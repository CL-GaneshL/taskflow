/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.generator.EmployeeData;
import java.util.Objects;

/**
 *
 * @author wdmtraining
 */
public class EmployeeProductivity {

    private static final Double FULL_PRODUCTIVITY = 8.0;
    private static final Double FULL_PRODUCTIVITY_IN_MINS = FULL_PRODUCTIVITY * 60;
    private static final Integer QUARTER = 15;
    private static final Integer HALF_QUARTER = 8;

    /**
     * Productivity ranges from 0 to 8 by 0.15 steeps e.g. 6.15. An employee
     * rated 6.15 has a 8/6.15 work ratio. A 2 hours task will be undertaken in
     * 2 hours by an full productivity employee (productivity 8), and 2 * 8 /
     * 6.15 = 2.60.
     *
     * @param employeeData
     * @param duration
     * @return
     */
    public static Integer getCorrectedDuration(
            final EmployeeData employeeData,
            final Integer duration
    ) {

        Integer correctedDuration;

        final Double productivity = employeeData.getEmployee().getProductivity();

        if (Objects.equals(productivity, FULL_PRODUCTIVITY)) {
            correctedDuration = duration;
        } else {

            // e.g. 6.15, duration = 2 * 60 = 120 mins
            final int integral = (int) Math.floor(productivity);    // => 6
            final double fractional = productivity - integral;  // => 0.15
            final int productivityInMins = integral * 60 + (int) (fractional * 100);    // 375 mins

            // the corrected durection in mins =>  120 * 8*60 / 375 = 153.6 mins
            final float f_correctedDuration
                    = (float) (duration * FULL_PRODUCTIVITY_IN_MINS / productivityInMins);

            // round to the closest quarter of minutes
            final int i_correctedDuration = (int) Math.floor(f_correctedDuration); // => 153 mins
            int mod = i_correctedDuration % QUARTER;    // => 3 mins

            correctedDuration = i_correctedDuration
                    + ((mod < HALF_QUARTER) ? (-1 * mod) : (QUARTER - mod));    // => 150 mins

        }

        return correctedDuration;
    }

    /**
     *
     * @param employeeData
     * @param duration
     * @return
     */
    public static Integer getDownscaledDuration(
            final EmployeeData employeeData,
            final Integer duration
    ) {

        Integer downscaledDuration;

        final Double productivity = employeeData.getEmployee().getProductivity();

        if (Objects.equals(productivity, FULL_PRODUCTIVITY)) {
            downscaledDuration = duration;
        } else {

            // e.g. 6.15, duration = 2 * 60 = 120 mins
            final int integral = (int) Math.floor(productivity);    // => 6
            final double fractional = productivity - integral;  // => 0.15
            final int productivityInMins = integral * 60 + (int) (fractional * 100);    // 375 mins

            // the corrected durection in mins =>  120 * 375 / 8*60 = 93.75 mins
            final float f_downscaledDuration
                    = (float) (duration * productivityInMins / FULL_PRODUCTIVITY_IN_MINS);

            // round to the closest quarter of minutes
            final int i_downscaledDuration = (int) Math.floor(f_downscaledDuration); // => 93 mins
            int mod = i_downscaledDuration % QUARTER;    // => 3 mins

            downscaledDuration = i_downscaledDuration
                    + ((mod < HALF_QUARTER) ? (-1 * mod) : (QUARTER - mod));    // => 90 mins

        }

        return downscaledDuration;
    }

}
