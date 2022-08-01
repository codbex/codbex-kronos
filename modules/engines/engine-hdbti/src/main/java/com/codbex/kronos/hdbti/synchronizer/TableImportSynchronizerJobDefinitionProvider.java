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
package com.codbex.kronos.hdbti.synchronizer;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider;
import org.eclipse.dirigible.core.scheduler.api.ISchedulerCoreService;
import org.eclipse.dirigible.core.scheduler.service.definition.JobDefinition;

public class TableImportSynchronizerJobDefinitionProvider implements IJobDefinitionProvider {

  static final String KRONOS_TABLE_IMPORT_SYNCHRONIZER_JOB_DESCRIPTION = "Kronos Table Import Synchronizer Job";
  private static final String KRONOS_TABLE_IMPORT_JOB_EXPRESSION = "KRONOS_TABLE_IMPORT_JOB_EXPRESSION";
  private static final String KRONOS_TABLE_IMPORT_SYNCHRONIZER_JOB = "kronos-table-import-synchronizer-job";

  @Override
  public JobDefinition getJobDefinition() {
    JobDefinition jobDefinition = new JobDefinition();
    jobDefinition.setName(KRONOS_TABLE_IMPORT_SYNCHRONIZER_JOB);
    jobDefinition.setGroup(ISchedulerCoreService.JOB_GROUP_INTERNAL);
    jobDefinition.setClazz(TableImportSynchronizerJob.class.getCanonicalName());
    jobDefinition.setDescription(KRONOS_TABLE_IMPORT_SYNCHRONIZER_JOB_DESCRIPTION);
    jobDefinition.setExpression(Configuration.get(KRONOS_TABLE_IMPORT_JOB_EXPRESSION, "0/45 * * * * ?"));
    jobDefinition.setSingleton(true);
    return jobDefinition;
  }
}
