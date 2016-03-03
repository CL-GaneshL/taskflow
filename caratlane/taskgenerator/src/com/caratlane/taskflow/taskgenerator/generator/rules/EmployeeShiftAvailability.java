/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.generator.EmployeeData;
import com.caratlane.taskflow.taskgenerator.generator.dao.EmployeeNonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import static com.caratlane.taskflow.taskgenerator.generator.crud.ExtractorDbHelpers.TOMORROW;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author wdmtraining
 */
public class EmployeeShiftAvailability {

    /**
     *
     * @param employeeData
     * @return
     */
    public static LocalDateTime getNextAvailability(final EmployeeData employeeData) {

        LocalDateTime av_start_date;

        // get the very last allocation
        final TaskAllocation allocation = employeeData.getLastTaskAllocation();

        if (allocation == null) {
            // no shift yet, then the employee is available
            // as soon as tomorrow morning first hour.
            av_start_date = getNextWorkingShift(employeeData, TOMORROW);

        } else {

            final LocalDateTime start_date = allocation.getStartDate();
            final Integer duration = allocation.getDuration();

            // start date of the last allocation plus its duration
            // will give us the date and time of the end of this allocation
            // and therefore the start of the next allocation.
            final LocalDateTime end_allocation = start_date.plusMinutes(duration);

            final LocalDateTime start_date_at_midnight = getDateAtMidnight(start_date);
            final LocalDateTime end_shift = start_date_at_midnight.plusHours(8);

            if (end_allocation.isBefore(end_shift)) {

                // same shift, just carry on after the previous allocation.
                av_start_date = end_allocation;

            } else {

                // the next available working shift from tomorrow
                final LocalDateTime tomorrowAtMidnight = getTomorrowAtMidnight(end_allocation);
                av_start_date = getNextWorkingShift(employeeData, tomorrowAtMidnight);
            }
        }

//         System.out.println("allocateTask : av_start_date : " + av_start_date);
        return av_start_date;
    }

    /**
     *
     * @param employeeData
     * @param date
     * @return
     */
    private static LocalDateTime getNextWorkingShift(
            final EmployeeData employeeData,
            final LocalDateTime fromDateAtMidnight
    ) {

        LocalDateTime nextWorkingShift;

        final LinkedList<EmployeeNonWorkingDay> nonWorkingDays
                = employeeData.getEmployeeNonWorkingDays();

        nextWorkingShift = fromDateAtMidnight;
        final Iterator<EmployeeNonWorkingDay> it = nonWorkingDays.iterator();
        while (it.hasNext()) {

            final LocalDateTime day = it.next().getDate();

            if (nextWorkingShift.isBefore(day)) {
                continue;
            }

            if (nextWorkingShift.isAfter(day)) {
                break;
            }

            if (nextWorkingShift.isEqual(day)) {
                // this proposed nextWorkingShift day is actually a non 
                // working day, so we have to try and see the following day
                nextWorkingShift = nextWorkingShift.plusDays(1);
            }
        }

        return nextWorkingShift;
    }

    /**
     *
     * @param date
     * @return
     */
    private static LocalDateTime getTomorrowAtMidnight(LocalDateTime date) {

        final LocalTime midnight = LocalTime.MIDNIGHT;
        final LocalDateTime todayMidnight = LocalDateTime.of(date.toLocalDate(), midnight);
        final LocalDateTime tomorrowAtMidnight = todayMidnight.plusDays(1);

        return tomorrowAtMidnight;
    }

    /**
     *
     * @param date
     * @return
     */
    private static LocalDateTime getDateAtMidnight(LocalDateTime date) {

        final LocalTime midnight = LocalTime.MIDNIGHT;
        final LocalDateTime dateAtMidnight = LocalDateTime.of(date.toLocalDate(), midnight);

        return dateAtMidnight;
    }

}
