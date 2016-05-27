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
class EmployeeAvailabilityProductBased implements EmployeeAvailability {

    /**
     *
     * @param employeeData
     * @param from allocation start date, from tomorrow 0am.
     * @return
     */
    @Override
    public LocalDateTime getNextAvailabilityWithReserve(
            final EmployeeData employeeData,
            final LocalDateTime from,
            final Integer reserve
    ) {

        LocalDateTime av_start_date;

        // get the very last allocation
        final TaskAllocation allocation = employeeData.getLastTaskAllocation();

        if (allocation == null) {
            // no allocation so far, just get the next immediate availability
            av_start_date = this.getNextWorkingShift(employeeData, from);

        } else {

            // check that the end of the allocation (start of the next
            // availability) is anterior to from (the allocation start date).
            // start, end and duration of the last allocation
            final LocalDateTime start_date = allocation.getStartDate();
            final Integer duration = allocation.getDuration();
            final LocalDateTime end_allocation = start_date.plusMinutes(duration);

            if (end_allocation.isBefore(from)) {

                // have to wait after the allocation start "from" anyway
                // let's get the next availability from the allocation start date.
                av_start_date = this.getNextWorkingShift(employeeData, from);

            } else {

                // need to take into account the employee's productivity
                final Integer correctedReservedDuration
                        = EmployeeProductivity.getCorrectedDuration(employeeData, reserve);

                final LocalDateTime end_allocation_with_reserve = end_allocation.plusMinutes(correctedReservedDuration);
                final LocalDateTime end_allocation_at_0am = getDateAtZeroAM(end_allocation); // at 0am
                final LocalDateTime end_shift = end_allocation_at_0am.plusHours(8); // at 8am

                if (end_allocation_with_reserve.isBefore(end_shift)) {

                    av_start_date = end_allocation;

                } else {
                    final LocalDateTime nextDay
                            = getDateAtZeroAM(end_allocation.plusDays(1));

                    av_start_date = this.getNextWorkingShift(employeeData, nextDay);
                }
            }
        }

        return av_start_date;
    }

    /**
     *
     * @param employeeData
     * @param from allocation start date, from tomorrow 0am.
     * @return
     */
    private LocalDateTime getNextWorkingShift(
            final EmployeeData employeeData,
            final LocalDateTime from
    ) {

        LocalDateTime nextWorkingShift;

        final LinkedList<EmployeeNonWorkingDay> nonWorkingDays
                = employeeData.getEmployeeNonWorkingDays();

        // start iterating working days
        nextWorkingShift = from;

        final Iterator<EmployeeNonWorkingDay> it = nonWorkingDays.iterator();
        while (it.hasNext()) {

            // the next non working day in the list
            final LocalDateTime nwd = it.next().getDate();

            if (nextWorkingShift.isBefore(nwd)) {
                // nextWorkingShift is a working day
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
    private LocalDateTime getDateAtZeroAM(LocalDateTime date) {

        final LocalTime midnight = LocalTime.MIDNIGHT;
        final LocalDateTime dateAtMidnight = LocalDateTime.of(date.toLocalDate(), midnight);

        return dateAtMidnight;
    }

    @Override
    public LocalDateTime getNextAvailability(EmployeeData employeeData, LocalDateTime from) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
