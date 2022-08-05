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
package com.codbex.kronos.synchronizer;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizerJob;
import org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider;
import org.eclipse.dirigible.core.scheduler.api.ISchedulerCoreService;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizer;
import org.eclipse.dirigible.core.scheduler.service.definition.JobDefinition;

public class XSJSLibSynchronizerJob extends AbstractSynchronizerJob implements IJobDefinitionProvider {

  private static final String XSJSLIB_SYNCHRONIZER_JOB = "Kronos XSJSLib Synchronizer Job";
  private static final String KRONOS_JOB_EXPRESSION_XSJSLIB = "KRONOS_JOB_EXPRESSION_XSJSLIB";
  private static final String KRONOS_XSJSLIB_SYNCHRONIZER_JOB = "kronos-xsjslib-synchronizer-job";

  @Override
  public String getName() {
    return XSJSLIB_SYNCHRONIZER_JOB;
  }

  @Override
  public ISynchronizer getSynchronizer() {
    return new XSJSLibSynchronizer();
  }

  @Override
  public JobDefinition getJobDefinition() {
    JobDefinition jobDefinition = new JobDefinition();
    jobDefinition.setName(KRONOS_XSJSLIB_SYNCHRONIZER_JOB);
    jobDefinition.setGroup(ISchedulerCoreService.JOB_GROUP_INTERNAL);
    jobDefinition.setClazz(XSJSLibSynchronizerJob.class.getCanonicalName());
    jobDefinition.setDescription(XSJSLIB_SYNCHRONIZER_JOB);
    jobDefinition.setExpression(Configuration.get(KRONOS_JOB_EXPRESSION_XSJSLIB, "0/55 * * * * ?"));
    jobDefinition.setSingleton(true);
    return jobDefinition;
  }
}