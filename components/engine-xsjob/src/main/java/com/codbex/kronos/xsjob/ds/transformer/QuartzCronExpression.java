/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.xsjob.ds.transformer;

/**
 * The Class QuartzCronExpression.
 */
public class QuartzCronExpression {

  /** The seconds. */
  private String seconds;
  
  /** The minutes. */
  private String minutes;
  
  /** The hours. */
  private String hours;
  
  /** The day of month. */
  private String dayOfMonth;
  
  /** The month. */
  private String month;
  
  /** The day of week. */
  private String dayOfWeek;
  
  /** The year. */
  private String year;

  /**
   * Instantiates a new quartz cron expression.
   */
  public QuartzCronExpression() {
  }

  /**
   * Gets the seconds.
   *
   * @return the seconds
   */
  public String getSeconds() {
    return seconds;
  }

  /**
   * Sets the seconds.
   *
   * @param seconds the new seconds
   */
  public void setSeconds(String seconds) {
    this.seconds = seconds;
  }

  /**
   * Gets the minutes.
   *
   * @return the minutes
   */
  public String getMinutes() {
    return minutes;
  }

  /**
   * Sets the minutes.
   *
   * @param minutes the new minutes
   */
  public void setMinutes(String minutes) {
    this.minutes = minutes;
  }

  /**
   * Gets the hours.
   *
   * @return the hours
   */
  public String getHours() {
    return hours;
  }

  /**
   * Sets the hours.
   *
   * @param hours the new hours
   */
  public void setHours(String hours) {
    this.hours = hours;
  }

  /**
   * Gets the day of month.
   *
   * @return the day of month
   */
  public String getDayOfMonth() {
    return dayOfMonth;
  }

  /**
   * Sets the day of month.
   *
   * @param dayOfMonth the new day of month
   */
  public void setDayOfMonth(String dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  /**
   * Gets the month.
   *
   * @return the month
   */
  public String getMonth() {
    return month;
  }

  /**
   * Sets the month.
   *
   * @param month the new month
   */
  public void setMonth(String month) {
    this.month = month;
  }

  /**
   * Gets the day of week.
   *
   * @return the day of week
   */
  public String getDayOfWeek() {
    return dayOfWeek;
  }

  /**
   * Sets the day of week.
   *
   * @param dayOfWeek the new day of week
   */
  public void setDayOfWeek(String dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  /**
   * Gets the year.
   *
   * @return the year
   */
  public String getYear() {
    return year;
  }

  /**
   * Sets the year.
   *
   * @param year the new year
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    StringBuilder cronExpression = new StringBuilder();
    cronExpression.append(seconds + " ");
    cronExpression.append(minutes + " ");
    cronExpression.append(hours + " ");
    cronExpression.append(dayOfMonth + " ");
    cronExpression.append(month + " ");
    cronExpression.append(dayOfWeek + " ");
    cronExpression.append(year);

    return cronExpression.toString();
  }
}
