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
package com.codbex.kronos.hdb.ds.hdi.synchronizer;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider;
import org.eclipse.dirigible.core.scheduler.api.ISchedulerCoreService;
import org.eclipse.dirigible.core.scheduler.service.definition.JobDefinition;

/**
 * The XSK Data Structures HDI Synchronizer Job Definition Provider.
 */
public class XSKDataStructuresHDISynchronizerJobDefinitionProvider implements IJobDefinitionProvider {

  static final String XSK_DATA_STRUCTURES_HDI_SYNCHRONIZER_JOB = "XSK Data Structures HDI Synchronizer Job";
  private static final String DIRIGIBLE_JOB_EXPRESSION_XSK_DATA_HDI_STRUCTURE = "DIRIGIBLE_JOB_EXPRESSION_XSK_DATA_STRUCTURE_HDI";
  private static final String DIRIGIBLE_INTERNAL_XSK_DATA_STRUCTURES_HDI_SYNCHRONIZER_JOB = "dirigible-internal-xsk-data-structures-hdi-synchronizer-job";

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider#getJobDefinition()
   */
  @Override
  public JobDefinition getJobDefinition() {
    JobDefinition jobDefinition = new JobDefinition();
    jobDefinition.setName(DIRIGIBLE_INTERNAL_XSK_DATA_STRUCTURES_HDI_SYNCHRONIZER_JOB);
    jobDefinition.setGroup(ISchedulerCoreService.JOB_GROUP_INTERNAL);
    jobDefinition.setClazz(XSKDataStructuresHDISynchronizerJob.class.getCanonicalName());
    jobDefinition.setDescription(XSK_DATA_STRUCTURES_HDI_SYNCHRONIZER_JOB);
    jobDefinition.setExpression(Configuration.get(DIRIGIBLE_JOB_EXPRESSION_XSK_DATA_HDI_STRUCTURE, "0/55 * * * * ?"));
    jobDefinition.setSingleton(true);
    return jobDefinition;
  }

}
