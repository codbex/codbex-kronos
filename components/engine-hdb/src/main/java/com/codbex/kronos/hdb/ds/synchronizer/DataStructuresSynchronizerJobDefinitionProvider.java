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
package com.codbex.kronos.hdb.ds.synchronizer;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider;
import org.eclipse.dirigible.core.scheduler.api.ISchedulerCoreService;
import org.eclipse.dirigible.core.scheduler.service.definition.JobDefinition;

/**
 * The Data Structures Synchronizer Job Definition Provider.
 */
public class DataStructuresSynchronizerJobDefinitionProvider implements IJobDefinitionProvider {

  /** The Constant DATA_STRUCTURES_SYNCHRONIZER_JOB. */
  static final String DATA_STRUCTURES_SYNCHRONIZER_JOB = "Kronos Data Structures Synchronizer Job";
  
  /** The Constant KRONOS_JOB_EXPRESSION_DATA_STRUCTURE. */
  private static final String KRONOS_JOB_EXPRESSION_DATA_STRUCTURE = "KRONOS_JOB_EXPRESSION_DATA_STRUCTURE";
  
  /** The Constant KRONOS_DATA_STRUCTURES_SYNCHRONIZER_JOB. */
  private static final String KRONOS_DATA_STRUCTURES_SYNCHRONIZER_JOB = "kronos-data-structures-synchronizer-job";

  /**
   * Gets the job definition.
   *
   * @return the job definition
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider#getJobDefinition()
   */
  @Override
  public JobDefinition getJobDefinition() {
    JobDefinition jobDefinition = new JobDefinition();
    jobDefinition.setName(KRONOS_DATA_STRUCTURES_SYNCHRONIZER_JOB);
    jobDefinition.setGroup(ISchedulerCoreService.JOB_GROUP_INTERNAL);
    jobDefinition.setClazz(DataStructuresSynchronizerJob.class.getCanonicalName());
    jobDefinition.setDescription(DATA_STRUCTURES_SYNCHRONIZER_JOB);
    jobDefinition.setExpression(Configuration.get(KRONOS_JOB_EXPRESSION_DATA_STRUCTURE, "0/30 * * * * ?"));
    jobDefinition.setSingleton(true);
    return jobDefinition;
  }

}
