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
package com.codbex.kronos.xsjob.ds.synchronizer;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider;
import org.eclipse.dirigible.core.scheduler.api.ISchedulerCoreService;
import org.eclipse.dirigible.core.scheduler.service.definition.JobDefinition;

/**
 * The Class JobSynchronizerJobDefinitionProvider.
 */
public class JobSynchronizerJobDefinitionProvider implements IJobDefinitionProvider {

  /** The Constant JOB_SYNCHRONIZER_JOB. */
  static final String JOB_SYNCHRONIZER_JOB = "Kronos Job Synchronizer Job";
  
  /** The Constant KRONOS_JOB_EXPRESSION_JOB. */
  private static final String KRONOS_JOB_EXPRESSION_JOB = "KRONOS_JOB_EXPRESSION_JOB";
  
  /** The Constant KRONOS_JOB_SYNCHRONIZER_JOB. */
  private static final String KRONOS_JOB_SYNCHRONIZER_JOB = "kronos-job-synchronizer-job";

  /**
   * Gets the job definition.
   *
   * @return the job definition
   */
  @Override
  public JobDefinition getJobDefinition() {
    JobDefinition jobDefinition = new JobDefinition();
    jobDefinition.setName(KRONOS_JOB_SYNCHRONIZER_JOB);
    jobDefinition.setGroup(ISchedulerCoreService.JOB_GROUP_INTERNAL);
    jobDefinition.setClazz(JobSynchronizerJob.class.getCanonicalName());
    jobDefinition.setDescription(JOB_SYNCHRONIZER_JOB);
    jobDefinition.setExpression(Configuration.get(KRONOS_JOB_EXPRESSION_JOB, "0/20 * * * * ?"));
    jobDefinition.setSingleton(true);
    return jobDefinition;
  }
}
