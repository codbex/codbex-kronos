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
package com.codbex.kronos.xsjob.ds.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.dirigible.components.api.security.UserFacade;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.PersistenceManager;

import com.codbex.kronos.utils.Utils;
import com.codbex.kronos.xsjob.ds.api.IJobCoreService;
import com.codbex.kronos.xsjob.ds.model.JobArtifact;
import com.codbex.kronos.xsjob.ds.model.JobDefinition;

/**
 * The Class JobCoreService.
 */
public class JobCoreService implements IJobCoreService {

	/** The data source. */
    public DataSource getDataSource() {
    	return (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
    }

  /** The persistence manager. */
  private PersistenceManager<JobDefinition> persistenceManager = new PersistenceManager<JobDefinition>();

  /**
   * Instantiates a new job core service.
   */
  public JobCoreService() {
  }

  /**
   * Creates the job.
   *
   * @param name the name
   * @param group the group
   * @param description the description
   * @param module the module
   * @param action the action
   * @param cronExpression the cron expression
   * @param parametersAsMap the parameters as map
   * @return the job definition
   * @throws SchedulerException the scheduler exception
   */
  @Override
  public JobDefinition
  createJob(String name, String group, String description, String module, String action, String cronExpression,
      Map<String, String> parametersAsMap) throws SchedulerException {

    try {
      Connection connection = null;
      try {
        JobDefinition jobDefinition = new JobDefinition();
        jobDefinition.setName(name);
        jobDefinition.setGroup(KRONOS_DEFINED_GROUP);
        jobDefinition.setDescription(description);
        jobDefinition.setModule(module);
        jobDefinition.setFunction(action);
        jobDefinition.setCronExpression(cronExpression);
        jobDefinition.setParameters(Utils.objectToByteArray(parametersAsMap));
        jobDefinition.setCreatedBy(UserFacade.getName());
        jobDefinition.setCreatedAt(new Timestamp(new java.util.Date().getTime()));

        connection = getDataSource().getConnection();
        persistenceManager.insert(connection, jobDefinition);
        return jobDefinition;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException | IOException e) {
      throw new SchedulerException(e);
    }
  }

  /**
   * Update job.
   *
   * @param name the name
   * @param group the group
   * @param description the description
   * @param module the module
   * @param action the action
   * @param cronExpression the cron expression
   * @param startAt the start at
   * @param endAt the end at
   * @param parametersAsMap the parameters as map
   * @return the job definition
   * @throws SchedulerException the scheduler exception
   */
  @Override
  public JobDefinition updateJob(String name, String group, String description, String module, String action, String cronExpression,
      Timestamp startAt, Timestamp endAt,
      Map<String, String> parametersAsMap) throws SchedulerException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        JobDefinition jobDefinition = getJob(name);
        jobDefinition.setGroup(group);
        jobDefinition.setDescription(description);
        jobDefinition.setModule(module);
        jobDefinition.setFunction(action);
        jobDefinition.setCronExpression(cronExpression);
        jobDefinition.setStartAt(startAt);
        jobDefinition.setEndAt(endAt);
        jobDefinition.setParameters(Utils.objectToByteArray(parametersAsMap));
        persistenceManager.update(connection, jobDefinition);

        return jobDefinition;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException | IOException e) {
      throw new SchedulerException(e);
    }
  }

  /**
   * Gets the job.
   *
   * @param name the name
   * @return the job
   * @throws SchedulerException the scheduler exception
   */
  @Override
  public JobDefinition getJob(String name) throws SchedulerException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        JobDefinition jobDefinition = persistenceManager.find(connection, JobDefinition.class, name);
        if (jobDefinition != null) {
          Map<String, String> parametersMap = Utils.byteArrayToObject(jobDefinition.getParameters());
          jobDefinition.setParametersAsMap(parametersMap);
        }

        return jobDefinition;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException | IOException | ClassNotFoundException e) {
      throw new SchedulerException(e);
    }
  }

  /**
   * Removes the job.
   *
   * @param name the name
   * @throws SchedulerException the scheduler exception
   */
  @Override
  public void removeJob(String name) throws SchedulerException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        persistenceManager.delete(connection, JobDefinition.class, name);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new SchedulerException(e);
    }
  }

  /**
   * Gets the jobs.
   *
   * @return the jobs
   * @throws SchedulerException the scheduler exception
   */
  @Override
  public List<JobDefinition> getJobs() throws SchedulerException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        List<JobDefinition> foundJobs = persistenceManager.findAll(connection, JobDefinition.class);
        for (JobDefinition jobDefinition : foundJobs) {

          Map<String, String> parametersMap = Utils.byteArrayToObject(jobDefinition.getParameters());
          jobDefinition.setParametersAsMap(parametersMap);
        }

        return foundJobs;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException | IOException | ClassNotFoundException e) {
      throw new SchedulerException(e);
    }
  }

  /**
   * Exists job.
   *
   * @param name the name
   * @return true, if successful
   * @throws SchedulerException the scheduler exception
   */
  @Override
  public boolean existsJob(String name) throws SchedulerException {
    return getJob(name) != null;
  }

  /**
   * Parses the job.
   *
   * @param json the json
   * @return the job artifact
   */
  @Override
  public JobArtifact parseJob(String json) {
    JobArtifact jobDefinition = GsonHelper.fromJson(json, JobArtifact.class);
    return jobDefinition;
  }

  /**
   * Parses the job.
   *
   * @param content the content
   * @return the job artifact
   */
  @Override
  public JobArtifact parseJob(byte[] content) {
    JobArtifact jobDefinition = GsonHelper
        .fromJson(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8),
            JobArtifact.class);

    return jobDefinition;
  }
}
