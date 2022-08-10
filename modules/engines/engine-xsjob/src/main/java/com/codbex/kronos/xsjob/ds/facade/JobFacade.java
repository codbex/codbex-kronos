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

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;

import com.codbex.kronos.xsjob.ds.model.JobArtifact;
import com.codbex.kronos.xsjob.ds.model.JobDefinition;
import com.codbex.kronos.xsjob.ds.scheduler.SchedulerManager;
import com.codbex.kronos.xsjob.ds.service.JobCoreService;
import com.codbex.kronos.xsjob.ds.transformer.JobToKronosJobDefinitionTransformer;

/**
 * The Class JobFacade.
 */
public class JobFacade {

  /** The job service. */
  private static JobCoreService jobService = new JobCoreService();

  /**
   * New job.
   *
   * @param job the job
   * @return the array list
   * @throws ParseException the parse exception
   * @throws SchedulerException the scheduler exception
   */
  public static final ArrayList<String> newJob(String job) throws ParseException, SchedulerException {
    JobArtifact jobArtifact = jobService.parseJob(job);
    JobToKronosJobDefinitionTransformer jobToKronosJobDefinitionTransformer = new JobToKronosJobDefinitionTransformer();
    ArrayList<JobDefinition> jobDefinitions = jobToKronosJobDefinitionTransformer.transform(jobArtifact);
    ArrayList<String> scheduleNames = new ArrayList<>();
    for (JobDefinition jobDefinition : jobDefinitions) {
      if (!jobService.existsJob(jobDefinition.getName())) {
        jobService.createJob(jobDefinition.getName(), jobDefinition.getGroup(), jobDefinition.getDescription(),
            jobDefinition.getModule(), jobDefinition.getFunction(), jobDefinition.getCronExpression(), jobDefinition.getParametersAsMap());
      }
      scheduleNames.add(jobDefinition.getName());
    }

    return scheduleNames;
  }

  /**
   * Activate.
   *
   * @param names the names
   * @throws SchedulerException the scheduler exception
   */
  public static final void activate(ArrayList<String> names) throws SchedulerException {
    for (String name : names) {
      JobDefinition jobDefinition = jobService.getJob(name);

      SchedulerManager.scheduleJob(jobDefinition);
    }
  }

  /**
   * Deactivate.
   *
   * @param names the names
   * @throws SchedulerException the scheduler exception
   */
  public static final void deactivate(ArrayList<String> names) throws SchedulerException {
    for (String name : names) {
      JobDefinition jobDefinition = jobService.getJob(name);

      SchedulerManager.unscheduleJob(name, jobDefinition.getGroup());
    }
  }

  /**
   * Configure.
   *
   * @param names the names
   * @param status the status
   * @param startAt the start at
   * @param endAt the end at
   * @throws SchedulerException the scheduler exception
   */
  public static final void configure(ArrayList<String> names, boolean status, Timestamp startAt, Timestamp endAt)
      throws SchedulerException {
    deactivate(names);

    for (String name : names) {
      JobDefinition jobDefinition = jobService.getJob(name);

      jobService.updateJob(jobDefinition.getName(), jobDefinition.getGroup(), jobDefinition.getDescription(),
          jobDefinition.getModule(), jobDefinition.getFunction(), jobDefinition.getCronExpression(), startAt,
          endAt,
          jobDefinition.getParametersAsMap());
    }

    if (status) {
      activate(names);
    }
  }

  /**
   * Gets the configuration.
   *
   * @param name the name
   * @return the configuration
   * @throws SchedulerException the scheduler exception
   */
  public static final JobDefinition getConfiguration(String name) throws SchedulerException {
    return jobService.getJob(name);
  }

  /**
   * Checks if is active.
   *
   * @param name the name
   * @return true, if is active
   * @throws SchedulerException the scheduler exception
   */
  public static final boolean isActive(String name) throws SchedulerException {
    return SchedulerManager.existsJob(name);
  }

}
