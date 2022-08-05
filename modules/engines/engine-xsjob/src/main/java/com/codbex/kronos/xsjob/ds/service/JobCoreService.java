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

import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.database.persistence.PersistenceManager;

import com.codbex.kronos.utils.Utils;
import com.codbex.kronos.xsjob.ds.api.IJobCoreService;
import com.codbex.kronos.xsjob.ds.model.JobArtifact;
import com.codbex.kronos.xsjob.ds.model.JobDefinition;

public class JobCoreService implements IJobCoreService {

  private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);

  private PersistenceManager<JobDefinition> persistenceManager = new PersistenceManager<JobDefinition>();

  public JobCoreService() {
  }

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

        connection = dataSource.getConnection();
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

  @Override
  public JobDefinition updateJob(String name, String group, String description, String module, String action, String cronExpression,
      Timestamp startAt, Timestamp endAt,
      Map<String, String> parametersAsMap) throws SchedulerException {
    try {
      Connection connection = null;
      try {
        connection = dataSource.getConnection();
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

  @Override
  public JobDefinition getJob(String name) throws SchedulerException {
    try {
      Connection connection = null;
      try {
        connection = dataSource.getConnection();
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

  @Override
  public void removeJob(String name) throws SchedulerException {
    try {
      Connection connection = null;
      try {
        connection = dataSource.getConnection();
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

  @Override
  public List<JobDefinition> getJobs() throws SchedulerException {
    try {
      Connection connection = null;
      try {
        connection = dataSource.getConnection();
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

  @Override
  public boolean existsJob(String name) throws SchedulerException {
    return getJob(name) != null;
  }

  @Override
  public JobArtifact parseJob(String json) {
    JobArtifact jobDefinition = GsonHelper.GSON.fromJson(json, JobArtifact.class);
    return jobDefinition;
  }

  @Override
  public JobArtifact parseJob(byte[] content) {
    JobArtifact jobDefinition = GsonHelper.GSON
        .fromJson(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8),
            JobArtifact.class);

    return jobDefinition;
  }
}
