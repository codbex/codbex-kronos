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
package com.codbex.kronos.xsodata.ds.synchronizer;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider;
import org.eclipse.dirigible.core.scheduler.api.ISchedulerCoreService;
import org.eclipse.dirigible.core.scheduler.service.definition.JobDefinition;

/**
 * The Data Structures Synchronizer Job Definition Provider.
 */
public class ODataSynchronizerJobDefinitionProvider implements IJobDefinitionProvider {

  static final String ODATA_SYNCHRONIZER_JOB = "Kronos OData Synchronizer Job";
  private static final String KRONOS_JOB_EXPRESSION_ODATA = "KRONOS_JOB_EXPRESSION_ODATA";
  private static final String KRONOS_ODATA_SYNCHRONIZER_JOB = "kronos-odata-synchronizer-job";

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider#getJobDefinition()
   */
  @Override
  public JobDefinition getJobDefinition() {
    JobDefinition jobDefinition = new JobDefinition();
    jobDefinition.setName(KRONOS_ODATA_SYNCHRONIZER_JOB);
    jobDefinition.setGroup(ISchedulerCoreService.JOB_GROUP_INTERNAL);
    jobDefinition.setClazz(ODataSynchronizerJob.class.getCanonicalName());
    jobDefinition.setDescription(ODATA_SYNCHRONIZER_JOB);
    jobDefinition.setExpression(Configuration.get(KRONOS_JOB_EXPRESSION_ODATA, "0/55 * * * * ?"));
    jobDefinition.setSingleton(true);
    return jobDefinition;
  }

}
