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

public interface IJobCoreService {

  String KRONOS_DEFINED_GROUP = "kronos-defined";

  String KRONOS_JOB_PARAMETERS = "kronos-job-parameters";

  String KRONOS_JOB_FUNCTION = "kronos-job-function";

  String KRONOS_JOB_MODULE = "kronos-job-module";

  String KRONOS_JOB_DESCRIPTION = "jobDescription";

  JobDefinition createJob(String name, String group, String description, String module, String action, String cronExpression,
      Map<String, String> parametersAsMap) throws SchedulerException;

  JobDefinition updateJob(String name, String group, String description, String module, String action, String cronExpression,
      Timestamp startAt, Timestamp endAt,
      Map<String, String> parametersAsMap) throws SchedulerException;

  JobDefinition getJob(String name) throws SchedulerException;

  void removeJob(String name) throws SchedulerException;

  List<JobDefinition> getJobs() throws SchedulerException;

  boolean existsJob(String name) throws SchedulerException;

  JobArtifact parseJob(String json);

  JobArtifact parseJob(byte[] content);
}
