/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.rules;

import com.caratlane.taskflow.taskgenerator.exceptions.TaskGeneratorException;
import com.caratlane.taskflow.taskgenerator.generator.EmployeeData;
import com.caratlane.taskflow.taskgenerator.generator.ProjectData;
import com.caratlane.taskflow.taskgenerator.generator.Skills;
import com.caratlane.taskflow.taskgenerator.generator.dao.Project;
import com.caratlane.taskflow.taskgenerator.generator.dao.Skill;
import com.caratlane.taskflow.taskgenerator.generator.dao.Task;
import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import com.caratlane.taskflow.taskgenerator.logging.LogManager;
import static java.lang.Math.toIntExact;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.time.LocalTime.MIDNIGHT;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;

/**
 *
 * @author wdmtraining
 */
public class TaskAllocationDistribution {

    /**
     *
     * @param projectData
     * @param skill_id
     * @param nb_products
     * @throws TaskGeneratorException
     */
    public static void allocateTask(
            final ProjectData projectData,
            final Integer skill_id,
            final Integer nb_products
    ) throws TaskGeneratorException {

        final Skill skill = Skills.getInstance().getSkill(skill_id);
        final Integer skill_duration = skill.getDuration();

        final Project project = projectData.getProject();
        final Integer project_id = project.getId();

        // total quantity of labor in minutes
        final Integer totalDuration = nb_products * skill_duration;

        final Task task = new Task(skill_id, project_id, totalDuration);
        projectData.addTask(task);

        int remainder = totalDuration;

        do {
            final EmployeeData employeeData
                    = EmployeeSkillSuitability.findMostSuitableEmployeeWithSkill(skill_id);

            final int allocated = allocateEmployeeTask(
                    task,
                    employeeData,
                    remainder
            );

            remainder = remainder - allocated;

        } while (remainder > 0);
    }

    /**
     *
     * @param project_id
     * @param skill_id
     * @param nb_products
     * @throws TaskGeneratorException
     */
    private static Integer allocateEmployeeTask(
            final Task task,
            final EmployeeData employeeData,
            final Integer duration
    ) throws TaskGeneratorException {

        Integer allocated;

        LocalDateTime av_start_date
                = EmployeeShiftAvailability.getNextAvailability(employeeData);

        TaskAllocation taskAllocation;

        // how many minutes available in that shift
        final LocalDateTime endShift = getDateAt8am(av_start_date);
        final Integer available = getLocalDateTimeDiff(av_start_date, endShift);

        final Integer employee_id = employeeData.getEmployee().getId();

        // corrected duration depends of the employee productivity
        final Integer correctedDuration
                = EmployeeProductivity.getCorrectedDuration(employeeData, duration);

        // ---------------------------------------------------------------------
        // LogManager.getLogger().log(Level.FINE, " duration = {0}, corrected => {1}", new Object[]{duration, correctedDuration});
        // ---------------------------------------------------------------------
        if (correctedDuration <= available) {

            // the task is allocated for this employee for a correctedDuration amount of time
            taskAllocation = new TaskAllocation(employee_id, av_start_date, correctedDuration);
            addTaskAllocation(employeeData, task, taskAllocation);

            // the actual allocated time was the duration (before correction)
            // that will be substract from the task's totalDuration,
            // irrespectively of the employees' productivity.
            allocated = duration;
        } else {
            // create an allocation whith the whole available time for that shift
            // we allocate the remainder time available in that shift.
            taskAllocation = new TaskAllocation(employee_id, av_start_date, available);
            addTaskAllocation(employeeData, task, taskAllocation);

            // however it has to be down scaled in order to take into accont
            // the employee's productivity.
            final Integer downscaledDuration
                    = EmployeeProductivity.getDownscaledDuration(employeeData, available);

            // ---------------------------------------------------------------------
            // LogManager.getLogger().log(Level.FINE, " available = {0}, downscaled => {1}", new Object[]{available, downscaledDuration});
            // ---------------------------------------------------------------------
            allocated = downscaledDuration;
        }

        return allocated;

    }

    /**
     * Add a new shift allocation. The start date of the newly added shift must
     * be greater than the start date of the last shift entered.
     *
     * @param taskAllocation
     * @throws TaskGeneratorException
     */
    private static void addTaskAllocation(
            final EmployeeData employeeData,
            final Task task,
            final TaskAllocation taskAllocation
    )
            throws TaskGeneratorException {

        final TaskAllocation last = employeeData.getLastTaskAllocation();

        if (last != null) {

            final LocalDateTime last_start_date = last.getStartDate();
            final Integer last_duration = last.getDuration();
            final LocalDateTime last_end_date = last_start_date.plusMinutes(last_duration);

            final LocalDateTime new_start_date = taskAllocation.getStartDate();

            // a new allocation can only start at the end of the last 
            // allocation or days after, if holidays, non working days ...
            if (new_start_date.isBefore(last_end_date)) {

                final String msg = MessageFormat.format("last = {0}, add = {1}", last, taskAllocation);
                // ---------------------------------------------------------------------
                LogManager.getLogger().log(Level.SEVERE, msg);
                // ---------------------------------------------------------------------
                throw new TaskGeneratorException(msg);
            }
        }

        task.addTaskAllocation(taskAllocation);
        employeeData.addTaskAllocation(taskAllocation);
    }

    /**
     *
     * @param date
     * @return
     */
    private static LocalDateTime getDateAt8am(final LocalDateTime date) {

        final LocalDate ld_date = date.toLocalDate();
        final LocalDateTime atMidnight = LocalDateTime.of(ld_date, MIDNIGHT);
        final LocalDateTime at8am = atMidnight.plusHours(8);

        return at8am;
    }

    private static Integer getLocalDateTimeDiff(final LocalDateTime fromDateTime, final LocalDateTime toDateTime) {

        final LocalDateTime diffDateTime = LocalDateTime.from(fromDateTime);
        final Integer diff = toIntExact(diffDateTime.until(toDateTime, ChronoUnit.MINUTES));

        return diff;
    }
}
