/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.generator.EmployeeData;
import com.caratlane.taskflow.taskgenerator.generator.dao.EmployeeNonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author wdmtraining
 */
public class EmployeeAvailability {

    /**
     *
     * @param employeeData
     * @param from
     * @return
     */
    public static LocalDateTime getNextAvailability(
            final EmployeeData employeeData,
            final LocalDateTime from
    ) {

        LocalDateTime av_start_date;

        // get the very last allocation
        final TaskAllocation allocation = employeeData.getLastTaskAllocation();

        if (allocation == null) {
            // no shift yet, then the employee is available immediatly
            av_start_date = getMostImmediateAvailability(employeeData, from);

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

                // the next available working shift from the next day
                final LocalDateTime nextDay
                        = getDateAtMidnight(end_allocation.plusDays(1));

                av_start_date = getNextWorkingShift(employeeData, nextDay);
            }
        }

        return av_start_date;
    }

    /**
     *
     * @param employeeData
     * @return
     */
    private static LocalDateTime getMostImmediateAvailability(
            final EmployeeData employeeData,
            final LocalDateTime from
    ) {
        // the allocation can be done any time during the day
        // the employee may already have started their shift
        // and therefore the allocation would be dated in the past.
        // e.g. intern employee start at 6am, current time is 3pm
        // the employee first allocation is at 0am, 1st hour of 
        // the shift, so 6am for that employee. At 3pm this
        // employee is already finished !!
        return getNextWorkingShift(employeeData, from); // TOMORROW
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

        // start iterating working days
        nextWorkingShift = fromDateAtMidnight;

        final Iterator<EmployeeNonWorkingDay> it = nonWorkingDays.iterator();
        while (it.hasNext()) {

            // the next non working day in the list
            final LocalDateTime nwd = it.next().getDate();

            if (nextWorkingShift.isBefore(nwd)) {
                continue;
            }

            if (nextWorkingShift.isAfter(nwd)) {
                // the proposed nextWorkingShift day is not a non 
                // working day, so we have it !
                break;
            }

            if (nextWorkingShift.isEqual(nwd)) {
                // the proposed nextWorkingShift day is actually a non 
                // working day, so we continue and try the following day
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
    private static LocalDateTime getDateAtMidnight(LocalDateTime date) {

        final LocalTime midnight = LocalTime.MIDNIGHT;
        final LocalDateTime dateAtMidnight = LocalDateTime.of(date.toLocalDate(), midnight);

        return dateAtMidnight;
    }

}
