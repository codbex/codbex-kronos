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

/**
 * The Class TableImportSynchronizerJobDefinitionProvider.
 */
public class TableImportSynchronizerJobDefinitionProvider implements IJobDefinitionProvider {

  /** The Constant TABLE_IMPORT_SYNCHRONIZER_JOB. */
  static final String TABLE_IMPORT_SYNCHRONIZER_JOB = "Kronos Table Import Synchronizer Job";
  
  /** The Constant KRONOS_JOB_EXPRESSION_TABLE_IMPORT. */
  private static final String KRONOS_JOB_EXPRESSION_TABLE_IMPORT = "KRONOS_JOB_EXPRESSION_TABLE_IMPORT";
  
  /** The Constant KRONOS_TABLE_IMPORT_SYNCHRONIZER_JOB. */
  private static final String KRONOS_TABLE_IMPORT_SYNCHRONIZER_JOB = "kronos-table-import-synchronizer-job";

  /**
   * Gets the job definition.
   *
   * @return the job definition
   */
  @Override
  public JobDefinition getJobDefinition() {
    JobDefinition jobDefinition = new JobDefinition();
    jobDefinition.setName(KRONOS_TABLE_IMPORT_SYNCHRONIZER_JOB);
    jobDefinition.setGroup(ISchedulerCoreService.JOB_GROUP_INTERNAL);
    jobDefinition.setClazz(TableImportSynchronizerJob.class.getCanonicalName());
    jobDefinition.setDescription(TABLE_IMPORT_SYNCHRONIZER_JOB);
    jobDefinition.setExpression(Configuration.get(KRONOS_JOB_EXPRESSION_TABLE_IMPORT, "0/45 * * * * ?"));
    jobDefinition.setSingleton(true);
    return jobDefinition;
  }
}
