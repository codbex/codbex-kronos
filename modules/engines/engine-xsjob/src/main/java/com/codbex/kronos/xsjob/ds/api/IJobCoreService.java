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
package com.codbex.kronos.xsjob.ds.api;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.eclipse.dirigible.core.scheduler.api.SchedulerException;

import com.codbex.kronos.xsjob.ds.model.JobArtifact;
import com.codbex.kronos.xsjob.ds.model.JobDefinition;

/**
 * The Interface IJobCoreService.
 */
public interface IJobCoreService {

  /** The kronos defined group. */
  String KRONOS_DEFINED_GROUP = "kronos-defined";

  /** The kronos job parameters. */
  String KRONOS_JOB_PARAMETERS = "kronos-job-parameters";

  /** The kronos job function. */
  String KRONOS_JOB_FUNCTION = "kronos-job-function";

  /** The kronos job module. */
  String KRONOS_JOB_MODULE = "kronos-job-module";

  /** The kronos job description. */
  String KRONOS_JOB_DESCRIPTION = "jobDescription";

  /**
   * Creates the job.
   *
   * @param name the name
   * @param group the group
   * @param description the description
   * @param module the module
   * @param action the action
   * @param cronExpression the cron expression
   * @param parametersAsMap the parameters as map
   * @return the job definition
   * @throws SchedulerException the scheduler exception
   */
  JobDefinition createJob(String name, String group, String description, String module, String action, String cronExpression,
      Map<String, String> parametersAsMap) throws SchedulerException;

  /**
   * Update job.
   *
   * @param name the name
   * @param group the group
   * @param description the description
   * @param module the module
   * @param action the action
   * @param cronExpression the cron expression
   * @param startAt the start at
   * @param endAt the end at
   * @param parametersAsMap the parameters as map
   * @return the job definition
   * @throws SchedulerException the scheduler exception
   */
  JobDefinition updateJob(String name, String group, String description, String module, String action, String cronExpression,
      Timestamp startAt, Timestamp endAt,
      Map<String, String> parametersAsMap) throws SchedulerException;

  /**
   * Gets the job.
   *
   * @param name the name
   * @return the job
   * @throws SchedulerException the scheduler exception
   */
  JobDefinition getJob(String name) throws SchedulerException;

  /**
   * Removes the job.
   *
   * @param name the name
   * @throws SchedulerException the scheduler exception
   */
  void removeJob(String name) throws SchedulerException;

  /**
   * Gets the jobs.
   *
   * @return the jobs
   * @throws SchedulerException the scheduler exception
   */
  List<JobDefinition> getJobs() throws SchedulerException;

  /**
   * Exists job.
   *
   * @param name the name
   * @return true, if successful
   * @throws SchedulerException the scheduler exception
   */
  boolean existsJob(String name) throws SchedulerException;

  /**
   * Parses the job.
   *
   * @param json the json
   * @return the job artifact
   */
  JobArtifact parseJob(String json);

  /**
   * Parses the job.
   *
   * @param content the content
   * @return the job artifact
   */
  JobArtifact parseJob(byte[] content);
}
