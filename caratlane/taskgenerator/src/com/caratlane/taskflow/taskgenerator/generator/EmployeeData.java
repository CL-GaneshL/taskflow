/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.generator.dao.Employee;
import com.caratlane.taskflow.taskgenerator.generator.dao.EmployeeNonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.dao.Holiday;
import com.caratlane.taskflow.taskgenerator.generator.dao.NonWorkingDay;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.generator.rules.EmployeeShiftAvailability;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author wdmtraining
 */
public class EmployeeData {

    final private static LocalTime MIDNIGHT = LocalTime.MIDNIGHT;

    private final Employee employee;
    private final LinkedList<Integer> skills;
    private final LinkedList<EmployeeNonWorkingDay> employeeNonWorkingDays;
//    private final LinkedList<Task> taskInfos;
    final private LinkedList<TaskAllocation> taskAllocations;

    public EmployeeData(Employee employee) {
        this.employee = employee;
        this.skills = new LinkedList<>();
        this.employeeNonWorkingDays = new LinkedList<>();
//        this.taskInfos = new LinkedList<>();
        this.taskAllocations = new LinkedList<>();
    }

    public void setEmployeeNonWorkingDays(
            final LinkedList<Holiday> holidays,
            final LinkedList<NonWorkingDay> nwds
    ) {

        // In order to optimize the search for non working days
        // in the allocateTask and getNextAvailability methods,
        // we create a unique date sorted list employeeNonWorkingDays.
        final LinkedList<EmployeeNonWorkingDay> castedHolidays
                = castToEmployeeNonWorkingDay(holidays);

        this.employeeNonWorkingDays.addAll(castedHolidays);
        this.employeeNonWorkingDays.addAll(nwds);

        Collections.sort(
                this.employeeNonWorkingDays,
                (d1, d2) -> d1.getDate().compareTo(d2.getDate())
        );

    }

    public LinkedList<EmployeeNonWorkingDay> getEmployeeNonWorkingDays() {
        return employeeNonWorkingDays;
    }

//    public void setTaskInfos(List<Task> tasks) {
//        this.taskInfos.clear();
//        this.taskInfos.addAll(tasks);
//    }
    public void addSkill(Integer skill) {
        this.skills.add(skill);
    }

    public Boolean hasSkill(Integer skill) {
        return this.skills.contains(skill);
    }

    public Employee getEmployee() {
        return employee;
    }

    /**
     *
     * @param taskAllocation
     */
    public void addTaskAllocation(final TaskAllocation taskAllocation) {
        this.taskAllocations.add(taskAllocation);
    }

    /**
     * For test purposes only.
     *
     * @param test
     */
    public void clearTaskAllocations(boolean test) {
        this.taskAllocations.clear();
    }

    /**
     *
     * @return
     */
    public LocalDateTime getEarliestAvailability() {
        return EmployeeShiftAvailability.getNextAvailability(this);
    }

    /**
     *
     * @return
     */
    public TaskAllocation getLastTaskAllocation() {

        TaskAllocation last = null;
        try {
            last = this.taskAllocations.getLast();
        } catch (Exception ignore) {
        }

        return last;
    }

    /**
     *
     * @return
     */
    public LinkedList<TaskAllocation> getTaskAllocations() {
        return taskAllocations;
    }

    /**
     *
     * @param holidays
     */
    private LinkedList<EmployeeNonWorkingDay> castToEmployeeNonWorkingDay(final List<Holiday> holidays) {

        final LinkedList<EmployeeNonWorkingDay> castedHolidays = new LinkedList<>();

        holidays.stream().forEach(new Consumer<Holiday>() {

            @Override
            public void accept(Holiday holiday) {
                final LocalDateTime start_date = holiday.getStartDate();
                final LocalDateTime end_date = holiday.getEndDate();
                // create N employee days, one per holiday day
                LocalDateTime current = start_date;
                do {
                    final LocalDateTime day_Nth_date = LocalDateTime.from(current);

                    final EmployeeNonWorkingDay employeeNonWorkingDay
                            = new EmployeeNonWorkingDay() {
                                final LocalDateTime date = day_Nth_date;

                                @Override
                                public LocalDateTime getDate() {
                                    return this.date;
                                }
                            };

                    castedHolidays.add(employeeNonWorkingDay);
                    current = current.plusDays(1);

                } while (current.isBefore(end_date) || current.isEqual(end_date));
            }
        });

        return castedHolidays;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.employee);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmployeeData other = (EmployeeData) obj;
        return Objects.equals(this.employee, other.employee);
    }

    @Override
    public String toString() {
        return "EmployeeData{" + "employee=" + employee + ", skills=" + skills + ", employeeNonWorkingDays=" + employeeNonWorkingDays + ", taskAllocations=" + taskAllocations + '}';
    }

}
