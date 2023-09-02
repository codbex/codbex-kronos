/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.xsjob.ds.scheduler.handler;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dirigible.components.engine.javascript.service.JavascriptService;
import org.eclipse.dirigible.repository.api.RepositoryPath;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.xsjob.ds.scheduler.SchedulerManager;

/**
 * The Class JobHandler.
 */
public class JobHandler implements Job {

  /** The Constant XSJOB_HANDLER. */
  private static final String XSJOB_HANDLER = "xsjob/wrappers/handler.xsjs";

  /** The javascript service. */
  @Autowired
  private JavascriptService javascriptService;

  /**
   * Execute.
   *
   * @param context the context
   * @throws JobExecutionException the job execution exception
   */
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    String module = (String) context.getJobDetail().getJobDataMap().get(SchedulerManager.KRONOS_JOB_MODULE);
    Map<String, String> parametersAsMap = (Map<String, String>) context.getJobDetail().getJobDataMap()
        .get(SchedulerManager.KRONOS_JOB_PARAMETERS);
    Object[] parametersArray = parametersAsMap.values().toArray();

    Map<Object, Object> executionContext = new HashMap<>();
    executionContext.put(SchedulerManager.KRONOS_JOB_FUNCTION, context.getJobDetail().getJobDataMap().get(SchedulerManager.KRONOS_JOB_FUNCTION));
    executionContext.put(SchedulerManager.KRONOS_JOB_PARAMETERS, parametersArray);
    executionContext.put(SchedulerManager.KRONOS_JOB_MODULE, module);
    executionContext.put(SchedulerManager.KRONOS_JOB_DESCRIPTION , context.getJobDetail().getDescription());
    try {
    	RepositoryPath path = new RepositoryPath(XSJOB_HANDLER);
		javascriptService.handleRequest(path.getSegments()[0], path.constructPathFrom(1), null, executionContext, false);
    } catch (Exception e) {
      CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, context.getJobDetail().getDescription(), CommonsConstants.JOB_PARSER);
      throw new JobExecutionException(e);
    }
  }
}