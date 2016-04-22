/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator;

import com.caratlane.taskflow.taskgenerator.generator.dao.TaskAllocation;
import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 *
 * @author wdmtraining
 */
public class TaskAllocations {

    private LinkedList<TaskAllocation> allocations = null;

    /**
     *
     */
    public TaskAllocations() {
        this.allocations = new LinkedList<>();
    }

    /**
     *
     * @param allocation
     */
    public void addAllocation(final TaskAllocation allocation) {
        this.allocations.add(allocation);
    }

    /**
     *
     * @return
     */
    public LinkedList<TaskAllocation> getAllocations() {
        return this.allocations;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getLatestAllocationDateTime() {

        final LocalDateTime latest = this.allocations.stream()
                .map(TaskAllocation::getStartDate)
                .max((d1, d2) -> {
                    return d1.compareTo(d2);
                }).get();

        return latest;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getEarliestAllocationDateTime() {

        final LocalDateTime earliest = this.allocations.stream()
                .map(TaskAllocation::getStartDate)
                .min((d1, d2) -> {
                    return d1.compareTo(d2);
                }).get();

        return earliest;
    }

}
