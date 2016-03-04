/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caratlane.taskflow.taskgenerator.generator.crud;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author wdmtraining
 */
public class ExtractorDbHelpers {

    public final static Date _D_TODAY;
    public final static Date _D_TOMORROW;
    public final static Date _D_IN_TWO_DAYS;
    public final static Date _D_IN_THREE_DAYS;
    public final static Date _D_IN_FOUR_DAYS;
    public final static Date _D_IN_THREE_MONTH;

    public final static LocalDateTime TODAY;
    public final static LocalDateTime TOMORROW;
    public final static LocalDateTime IN_TWO_DAYS;
    public final static LocalDateTime IN_THREE_DAYS;
    public final static LocalDateTime IN_FOUR_DAYS;

    public static enum NWDType {

        WEEKEND, NON_WORKING
    }

    public static enum EmployementType {

        Intern, FTE
    }

    static {
        // Hibernate maps the mySQl date type using the java Date type.
        // Because Date is now almost depreciated, we do the magic
        // using the java 8+ LocalDate type, and finally re-ajust.

        final LocalTime midnight = LocalTime.MIDNIGHT;
        final LocalDate today = LocalDate.now();
        final LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        final LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        final LocalDateTime in2daysMidnight = todayMidnight.plusDays(2);
        final LocalDateTime in3daysMidnight = todayMidnight.plusDays(3);
        final LocalDateTime in4daysMidnight = todayMidnight.plusDays(4);
        final LocalDateTime in3months = todayMidnight.plusMonths(3);

        _D_TODAY = Date.from(todayMidnight.atZone(ZoneId.systemDefault()).toInstant());
        _D_TOMORROW = Date.from(tomorrowMidnight.atZone(ZoneId.systemDefault()).toInstant());
        _D_IN_TWO_DAYS = Date.from(in2daysMidnight.atZone(ZoneId.systemDefault()).toInstant());
        _D_IN_THREE_DAYS = Date.from(in3daysMidnight.atZone(ZoneId.systemDefault()).toInstant());
        _D_IN_FOUR_DAYS = Date.from(in4daysMidnight.atZone(ZoneId.systemDefault()).toInstant());
        _D_IN_THREE_MONTH = Date.from(in3months.atZone(ZoneId.systemDefault()).toInstant());

        TODAY = todayMidnight;
        TOMORROW = todayMidnight.plusDays(1);
        IN_TWO_DAYS = todayMidnight.plusDays(2);
        IN_THREE_DAYS = todayMidnight.plusDays(3);
        IN_FOUR_DAYS = todayMidnight.plusDays(4);

    }

    /**
     *
     * @param <T>
     * @param itemClass
     * @param suffix
     * @return
     */
    public static <T> String getQueryName(Class<T> itemClass, final String suffix) {

        String queryName = null;

        final NamedQueries namedQueries[] = itemClass.getAnnotationsByType(NamedQueries.class);

        for (NamedQueries nQs : namedQueries) {
            for (NamedQuery namedQuerie : nQs.value()) {
                if (namedQuerie.name().endsWith(suffix)) {
                    queryName = namedQuerie.name();
                    break;
                }
            }
        }

        return queryName;
    }
}
