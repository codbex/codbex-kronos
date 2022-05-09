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
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;

import com.codbex.kronos.xsjob.ds.model.XSKJobArtifact;
import com.codbex.kronos.xsjob.ds.model.XSKJobDefinition;

public interface IXSKJobCoreService {

  String XSK_DEFINED_GROUP = "xsk-defined";

  String XSK_JOB_PARAMETERS = "xsk-job-parameters";

  String XSK_JOB_FUNCTION = "xsk-job-function";

  String XSK_JOB_MODULE = "xsk-job-module";

  String XSK_JOB_DESCRIPTION = "jobDescription";

  XSKJobDefinition createJob(String name, String group, String description, String module, String action, String cronExpression,
      Map<String, String> parametersAsMap) throws SchedulerException;

  XSKJobDefinition updateJob(String name, String group, String description, String module, String action, String cronExpression,
      Timestamp startAt, Timestamp endAt,
      Map<String, String> parametersAsMap) throws SchedulerException;

  XSKJobDefinition getJob(String name) throws SchedulerException;

  void removeJob(String name) throws SchedulerException;

  List<XSKJobDefinition> getJobs() throws SchedulerException;

  boolean existsJob(String name) throws SchedulerException;

  XSKJobArtifact parseJob(String json);

  XSKJobArtifact parseJob(byte[] content);
}
