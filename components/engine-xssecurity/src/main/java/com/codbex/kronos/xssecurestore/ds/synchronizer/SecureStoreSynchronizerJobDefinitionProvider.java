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
package com.codbex.kronos.xssecurestore.ds.synchronizer;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider;
import org.eclipse.dirigible.core.scheduler.api.ISchedulerCoreService;
import org.eclipse.dirigible.core.scheduler.service.definition.JobDefinition;

/**
 * The Class SecureStoreSynchronizerJobDefinitionProvider.
 */
public class SecureStoreSynchronizerJobDefinitionProvider implements IJobDefinitionProvider {

  /** The Constant SECURE_STORE_SYNCHRONIZER_JOB. */
  static final String SECURE_STORE_SYNCHRONIZER_JOB = "Kronos Secure Store Synchronizer Job";
  
  /** The Constant KRONOS_JOB_EXPRESSION_SECURE_STORE. */
  private static final String KRONOS_JOB_EXPRESSION_SECURE_STORE = "KRONOS_JOB_EXPRESSION_SECURE_STORE";
  
  /** The Constant KRONOS_SECURE_CREATE_SECURE_STORE_VALUE_SYNCHRONIZER_JOB. */
  private static final String KRONOS_SECURE_CREATE_SECURE_STORE_VALUE_SYNCHRONIZER_JOB = "kronos-secure-store-synchronizer-job";

  /**
   * Gets the job definition.
   *
   * @return the job definition
   */
  @Override
  public JobDefinition getJobDefinition() {
    JobDefinition jobDefinition = new JobDefinition();
    jobDefinition.setName(KRONOS_SECURE_CREATE_SECURE_STORE_VALUE_SYNCHRONIZER_JOB);
    jobDefinition.setGroup(ISchedulerCoreService.JOB_GROUP_INTERNAL);
    jobDefinition.setClazz(SecureStoreSynchronizerJob.class.getCanonicalName());
    jobDefinition.setDescription(SECURE_STORE_SYNCHRONIZER_JOB);
    jobDefinition.setExpression(Configuration.get(KRONOS_JOB_EXPRESSION_SECURE_STORE, "0/25 * * * * ?"));
    jobDefinition.setSingleton(true);
    return jobDefinition;
  }
}
