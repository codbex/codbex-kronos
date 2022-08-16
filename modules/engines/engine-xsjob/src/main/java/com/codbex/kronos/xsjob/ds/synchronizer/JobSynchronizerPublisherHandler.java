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

import org.eclipse.dirigible.core.publisher.api.handlers.MetadataPublisherHandler;
import org.eclipse.dirigible.core.publisher.api.handlers.MetadataPublisherHandler;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.database.persistence.PersistenceManager;

import com.codbex.kronos.xsjob.ds.model.JobDefinition;

/**
 * The Class JobSynchronizerPublisherHandler.
 */
public class JobSynchronizerPublisherHandler extends MetadataPublisherHandler {

  /**
   * Before publish.
   *
   * @param location the location
   */
  @Override
  public void beforePublish(String location) {

  }

  /**
   * After publish.
   *
   * @param workspaceLocation the workspace location
   * @param registryLocation the registry location
   */
  @Override
  public void afterPublish(String workspaceLocation, String registryLocation) {

  }

  /**
   * Before unpublish.
   *
   * @param location the location
   */
  @Override
  public void beforeUnpublish(String location) {

  }

  /**
   * After unpublish.
   *
   * @param location the location
   * @throws SchedulerException the scheduler exception
   */
  @Override
  public void afterUnpublish(String location) throws SchedulerException {
    removeMetadata(new PersistenceManager<JobDefinition>(), "KRONOS_JOBS", "MODULE", location, false);
  }
}
