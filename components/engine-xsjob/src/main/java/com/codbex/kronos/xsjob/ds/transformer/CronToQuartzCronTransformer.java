/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.xsjob.ds.transformer;

import com.codbex.kronos.engine.xsjob.exceptions.CronExpressionException;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

import java.text.ParseException;
import java.util.List;

/**
 * The Class CronToQuartzCronTransformer.
 */
public class CronToQuartzCronTransformer {

    /** The Constant KRONOS_CRON_YEAR. */
    private static final int KRONOS_CRON_YEAR = 0;

    /** The Constant KRONOS_CRON_MONTH. */
    private static final int KRONOS_CRON_MONTH = 1;

    /** The Constant KRONOS_CRON_DAY. */
    private static final int KRONOS_CRON_DAY = 2;

    /** The Constant KRONOS_CRON_DAY_OF_WEEK. */
    private static final int KRONOS_CRON_DAY_OF_WEEK = 3;

    /** The Constant KRONOS_CRON_HOUR. */
    private static final int KRONOS_CRON_HOUR = 4;

    /** The Constant KRONOS_CRON_MINUTE. */
    private static final int KRONOS_CRON_MINUTE = 5;

    /** The Constant KRONOS_CRON_SECOND. */
    private static final int KRONOS_CRON_SECOND = 6;

    /** The cron expression arr. */
    private String[] cronExpressionArr;

    /**
     * Instantiates a new cron to quartz cron transformer.
     */
    public CronToQuartzCronTransformer() {}

    /**
     * Transform.
     *
     * @param cronExpression the cron expression
     * @return the string
     * @throws ParseException the parse exception
     */
    public String transform(String cronExpression) throws ParseException {

        cronExpressionArr = cronExpression.split(" ");

        QuartzCronExpression quartzCronExpression = new QuartzCronExpression();
        try {
            quartzCronExpression.setYear(parseRange(cronExpressionArr[KRONOS_CRON_YEAR]));
            quartzCronExpression.setMonth(parseRange(cronExpressionArr[KRONOS_CRON_MONTH]));
            quartzCronExpression.setDayOfMonth(checkDayOfWeekAndDayOfMonth());

            String quartzDayOfWeek = parseRange(cronExpressionArr[KRONOS_CRON_DAY_OF_WEEK]);
            quartzDayOfWeek = parseDayOfWeekElement(quartzDayOfWeek);
            quartzCronExpression.setDayOfWeek(quartzDayOfWeek);

            quartzCronExpression.setHours(parseRange(cronExpressionArr[KRONOS_CRON_HOUR]));
            quartzCronExpression.setMinutes(parseRange(cronExpressionArr[KRONOS_CRON_MINUTE]));
            quartzCronExpression.setSeconds(parseRange(cronExpressionArr[KRONOS_CRON_SECOND]));
        } catch (Exception e) {
            CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, cronExpression, CommonsConstants.JOB_PARSER);
            throw e;
        }
        return quartzCronExpression.toString();
    }

    /**
     * Parses the range.
     *
     * @param cronElement the cron element
     * @return the string
     */
    private String parseRange(String cronElement) {
        if (!cronElement.contains(":")) {
            return cronElement;
        }

        String[] splitXscCronElement = cronElement.split(":");

        return String.join("-", splitXscCronElement);
    }

    /**
     * Parses the day of week element.
     *
     * @param dayOfWeekElement the day of week element
     * @return the string
     * @throws ParseException the parse exception
     */
    private String parseDayOfWeekElement(String dayOfWeekElement) throws ParseException {
        if (dayOfWeekElement.equals("*") && cronExpressionArr[KRONOS_CRON_DAY].equals("*")) {
            return "?";
        } else if (!dayOfWeekElement.contains(".")) {
            return dayOfWeekElement.toUpperCase();
        }

        String[] splitDayOfWeekElement = dayOfWeekElement.split(".");
        String dayOfWeek = splitDayOfWeekElement[1].toUpperCase();

        int occurrence = Integer.parseInt(splitDayOfWeekElement[0]);

        if (occurrence >= 0) {
            return dayOfWeek + "#" + occurrence;
        } else if (occurrence == -1) {
            return dayOfWeek + "L";
        } else if (occurrence < -1 && occurrence >= -5) {
            return dayOfWeek + "L" + "-" + occurrence;
        }

        throw new CronExpressionException(String.join(" ", cronExpressionArr), KRONOS_CRON_DAY_OF_WEEK);
    }

    /**
     * Check day of week and day of month.
     *
     * @return the string
     */
    private String checkDayOfWeekAndDayOfMonth() {
        final List<String> cronExpressionDayOfWeekArr =
                List.of("mon", "tue", "wed", "thu", "fri", "sat", "sun", "1", "2", "3", "4", "5", "6", "7");
        final List<String> dayOfWeekList = List.of(cronExpressionArr[KRONOS_CRON_DAY_OF_WEEK].split(","));

        boolean isEveryDay = cronExpressionArr[KRONOS_CRON_DAY].equalsIgnoreCase("*");
        boolean hasDayOfWeek = dayOfWeekList.stream()
                                            .map(String::toLowerCase)
                                            .anyMatch(cronExpressionDayOfWeekArr::contains);

        return isEveryDay && hasDayOfWeek ? "?" : parseRange(cronExpressionArr[KRONOS_CRON_DAY]);
    }
}
