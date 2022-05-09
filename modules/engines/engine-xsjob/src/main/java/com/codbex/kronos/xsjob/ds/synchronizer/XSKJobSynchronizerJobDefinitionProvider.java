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

public class XSKJobSynchronizerJobDefinitionProvider implements IJobDefinitionProvider {

  static final String XSK_JOB_SYNCHRONIZER_JOB = "XSK Job Synchronizer Job";
  private static final String DIRIGIBLE_JOB_EXPRESSION_XSK_JOB = "DIRIGIBLE_JOB_EXPRESSION_XSK_JOB";
  private static final String DIRIGIBLE_INTERNAL_XSK_JOB_SYNCHRONIZER_JOB = "dirigible-internal-xsk-job-synchronizer-job";

  @Override
  public JobDefinition getJobDefinition() {
    JobDefinition jobDefinition = new JobDefinition();
    jobDefinition.setName(DIRIGIBLE_INTERNAL_XSK_JOB_SYNCHRONIZER_JOB);
    jobDefinition.setGroup(ISchedulerCoreService.JOB_GROUP_INTERNAL);
    jobDefinition.setClazz(XSKJobSynchronizerJob.class.getCanonicalName());
    jobDefinition.setDescription(XSK_JOB_SYNCHRONIZER_JOB);
    jobDefinition.setExpression(Configuration.get(DIRIGIBLE_JOB_EXPRESSION_XSK_JOB, "0/20 * * * * ?"));
    jobDefinition.setSingleton(true);
    return jobDefinition;
  }
}
