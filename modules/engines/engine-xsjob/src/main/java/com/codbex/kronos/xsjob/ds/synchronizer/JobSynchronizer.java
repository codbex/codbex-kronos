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
package com.codbex.kronos.xsjob.ds.synchronizer;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.repository.api.IResource;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.xsjob.ds.api.IJobCoreService;
import com.codbex.kronos.xsjob.ds.api.IJobModel;
import com.codbex.kronos.xsjob.ds.model.JobArtifact;
import com.codbex.kronos.xsjob.ds.model.JobDefinition;
import com.codbex.kronos.xsjob.ds.scheduler.KronosSchedulerManager;
import com.codbex.kronos.xsjob.ds.service.JobCoreService;
import com.codbex.kronos.xsjob.ds.transformer.JobToKronosJobDefinitionTransformer;

public class JobSynchronizer extends AbstractSynchronizer {

  public static final String KRONOS_SYNCHRONIZER_XSJOB_ENABLED = "KRONOS_SYNCHRONIZER_XSJOB_ENABLED";
  private static final Logger logger = LoggerFactory.getLogger(JobSynchronizer.class);

  private static final Map<String, JobDefinition> JOBS_PREDELIVERED = Collections
      .synchronizedMap(new HashMap<String, JobDefinition>());

  private static final List<String> JOBS_SYNCHRONIZED = Collections.synchronizedList(new ArrayList<String>());
  private final String SYNCHRONIZER_NAME = this.getClass().getCanonicalName();
  private JobCoreService schedulerCoreService = new JobCoreService();

  private JobToKronosJobDefinitionTransformer jobToKronosJobDefinitionTransformer = new JobToKronosJobDefinitionTransformer();

  /**
   * Force synchronization.
   */
  public static final void forceSynchronization() {
    JobSynchronizer synchronizer = new JobSynchronizer();
    synchronizer.setForcedSynchronization(true);
    try {
      synchronizer.synchronize();
    } finally {
      synchronizer.setForcedSynchronization(false);
    }
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.ISynchronizer#synchronize()
   */
  @Override
  public void synchronize() {
    if (Boolean.parseBoolean(Configuration.get(KRONOS_SYNCHRONIZER_XSJOB_ENABLED, "false"))) {
      synchronized (JobSynchronizer.class) {
        if (beforeSynchronizing()) {
          logger.trace("Synchronizing Jobs...");
          try {
            startSynchronization(SYNCHRONIZER_NAME);
            clearCache();
            synchronizePredelivered();
            synchronizeRegistry();
            startJobs();
            int immutableCount = JOBS_PREDELIVERED.size();
            int mutableCount = JOBS_SYNCHRONIZED.size();
            cleanup();
            clearCache();
            successfulSynchronization(SYNCHRONIZER_NAME, format("Immutable: {0}, Mutable: {1}", immutableCount, mutableCount));
          } catch (Exception e) {
            logger.error("Synchronizing process for Jobs failed.", e);
            try {
              failedSynchronization(SYNCHRONIZER_NAME, e.getMessage());
            } catch (SchedulerException e1) {
              logger.error("Synchronizing process for Jobs files failed in registering the state log.", e);
            }
          }
          logger.trace("Done synchronizing Jobs.");
          afterSynchronizing();
        }
      }
    }
  }


  /**
   * Register predelivered job.
   *
   * @param jobPath the job path
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void registerPredeliveredJob(String jobPath) throws IOException, ParseException {
    InputStream in = JobSynchronizer.class.getResourceAsStream(jobPath);

    try {
      String json = IOUtils.toString(in, StandardCharsets.UTF_8);
      JobArtifact jobArtifact = schedulerCoreService.parseJob(json);
      ArrayList<JobDefinition> jobDefinitions = jobToKronosJobDefinitionTransformer.transform(jobArtifact);
      for (JobDefinition jobDefinition : jobDefinitions) {
        jobDefinition.setName(jobPath);
        JOBS_PREDELIVERED.put(jobPath, jobDefinition);
      }
    } finally {
      if (in != null) {
        in.close();
      }
    }
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#synchronizeRegistry()
   */
  @Override
  protected void synchronizeRegistry() throws SynchronizationException {
    logger.trace("Synchronizing Jobs from Registry...");

    super.synchronizeRegistry();

    logger.trace("Done synchronizing Jobs from Registry.");
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#synchronizeResource(com.codbex.kronos.hdb.ds.parser.DataStructureParser
   * repository.api.IResource)
   */
  @Override
  protected void synchronizeResource(IResource resource) throws SynchronizationException {
    String resourceName = resource.getName();
    if (resourceName.endsWith(IJobModel.KRONOS_JOB_FILE_EXTENSION)) {
      JobArtifact jobArtifact = schedulerCoreService.parseJob(resource.getContent());
      try {
        ArrayList<JobDefinition> jobDefinitions = jobToKronosJobDefinitionTransformer.transform(jobArtifact);
        for (JobDefinition jobDefinition : jobDefinitions) {
          jobDefinition.setGroup(IJobCoreService.KRONOS_DEFINED_GROUP);
          synchronizeJob(jobDefinition);
        }
      } catch (ParseException e) {
        throw new SynchronizationException();
      }
    }

  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#cleanup()
   */
  @Override
  protected void cleanup() throws SynchronizationException {
    logger.trace("Cleaning up Jobs...");

    try {
      List<JobDefinition> jobDefinitions = schedulerCoreService.getJobs();
      for (JobDefinition jobDefinition : jobDefinitions) {
        if (!JOBS_SYNCHRONIZED.contains(jobDefinition.getName())) {
          schedulerCoreService.removeJob(jobDefinition.getName());
          logger.warn("Cleaned up Job [{}] from group: {}", jobDefinition.getName(), jobDefinition.getGroup());
        }
      }
    } catch (SchedulerException e) {
      throw new SynchronizationException(e);
    }

    logger.trace("Done cleaning up Jobs.");
  }

  private void startJobs() throws SchedulerException {
    logger.trace("Start Jobs...");

    for (String jobName : JOBS_SYNCHRONIZED) {
      if (!KronosSchedulerManager.existsJob(jobName)) {
        try {
          JobDefinition jobDefinition = schedulerCoreService.getJob(jobName);
          KronosSchedulerManager.scheduleJob(jobDefinition);
        } catch (SchedulerException e) {
          logger.error(e.getMessage(), e);
        }
      }
    }

    Set<TriggerKey> runningJobs = KronosSchedulerManager.listJobs();
    for (TriggerKey jobKey : runningJobs) {
      try {
        if (!JOBS_SYNCHRONIZED.contains(jobKey.getName())) {
          KronosSchedulerManager.unscheduleJob(jobKey.getName(), jobKey.getGroup());
        }
      } catch (SchedulerException e) {
        logger.error(e.getMessage(), e);
      }
    }

    logger.trace("Running Jobs: " + runningJobs.size());
    logger.trace("Done starting Jobs.");
  }

  private void clearCache() {
    JOBS_SYNCHRONIZED.clear();
  }

  private void synchronizePredelivered() throws SynchronizationException {
    logger.trace("Synchronizing predelivered Jobs...");

    // Jobs
    for (JobDefinition jobDefinition : JOBS_PREDELIVERED.values()) {
      synchronizeJob(jobDefinition);
    }

    logger.trace("Done synchronizing predelivered Jobs.");
  }

  private void synchronizeJob(JobDefinition job) throws SynchronizationException {
    try {
      if (!schedulerCoreService.existsJob(job.getName())) {
        schedulerCoreService.createJob(job.getName(), job.getGroup(), job.getDescription(),
            job.getModule(), job.getFunction(), job.getCronExpression(), job.getParametersAsMap());
        logger.info("Synchronized a new Job [{}] from group: {}", job.getName(), job.getGroup());
      } else {
        JobDefinition existing = schedulerCoreService.getJob(job.getName());
        if (!job.equals(existing)) {
          schedulerCoreService.updateJob(job.getName(), job.getGroup(), job.getDescription(),
              job.getModule(), job.getFunction(), job.getCronExpression(), job.getStartAt(), job.getEndAt(),
              job.getParametersAsMap());
          logger.info("Synchronized a modified Job [{}] from group: {}", job.getName(), job.getGroup());
        }
      }
      JOBS_SYNCHRONIZED.add(job.getName());
    } catch (SchedulerException e) {
      throw new SynchronizationException(e);
    }
  }
}
