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
package com.codbex.kronos.xsjob.ds.facade;

import com.codbex.kronos.xsjob.ds.model.XSKJobArtifact;
import com.codbex.kronos.xsjob.ds.model.XSKJobDefinition;
import com.codbex.kronos.xsjob.ds.scheduler.XSKSchedulerManager;
import com.codbex.kronos.xsjob.ds.service.XSKJobCoreService;
import com.codbex.kronos.xsjob.ds.transformer.XSKJobToXSKJobDefinitionTransformer;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;

public class XSKJobFacade {

  private static XSKJobCoreService jobService = new XSKJobCoreService();

  public static final ArrayList<String> newJob(String job) throws ParseException, SchedulerException {
    XSKJobArtifact xskJobArtifact = jobService.parseJob(job);
    XSKJobToXSKJobDefinitionTransformer xskJobToXSKJobDefinitionTransformer = new XSKJobToXSKJobDefinitionTransformer();
    ArrayList<XSKJobDefinition> xskJobDefinitions = xskJobToXSKJobDefinitionTransformer.transform(xskJobArtifact);
    ArrayList<String> scheduleNames = new ArrayList<>();
    for (XSKJobDefinition jobDefinition : xskJobDefinitions) {
      if (!jobService.existsJob(jobDefinition.getName())) {
        jobService.createJob(jobDefinition.getName(), jobDefinition.getGroup(), jobDefinition.getDescription(),
            jobDefinition.getModule(), jobDefinition.getFunction(), jobDefinition.getCronExpression(), jobDefinition.getParametersAsMap());
      }
      scheduleNames.add(jobDefinition.getName());
    }

    return scheduleNames;
  }

  public static final void activate(ArrayList<String> names) throws SchedulerException {
    for (String name : names) {
      XSKJobDefinition jobDefinition = jobService.getJob(name);

      XSKSchedulerManager.scheduleJob(jobDefinition);
    }
  }

  public static final void deactivate(ArrayList<String> names) throws SchedulerException {
    for (String name : names) {
      XSKJobDefinition jobDefinition = jobService.getJob(name);

      XSKSchedulerManager.unscheduleJob(name, jobDefinition.getGroup());
    }
  }

  public static final void configure(ArrayList<String> names, boolean status, Timestamp startAt, Timestamp endAt)
      throws SchedulerException {
    deactivate(names);

    for (String name : names) {
      XSKJobDefinition jobDefinition = jobService.getJob(name);

      jobService.updateJob(jobDefinition.getName(), jobDefinition.getGroup(), jobDefinition.getDescription(),
          jobDefinition.getModule(), jobDefinition.getFunction(), jobDefinition.getCronExpression(), startAt,
          endAt,
          jobDefinition.getParametersAsMap());
    }

    if (status) {
      activate(names);
    }
  }

  public static final XSKJobDefinition getConfiguration(String name) throws SchedulerException {
    return jobService.getJob(name);
  }

  public static final boolean isActive(String name) throws SchedulerException {
    return XSKSchedulerManager.existsJob(name);
  }

}
