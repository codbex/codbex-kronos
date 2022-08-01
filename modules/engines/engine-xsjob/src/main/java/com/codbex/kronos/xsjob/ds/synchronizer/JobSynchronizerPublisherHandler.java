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

import com.codbex.kronos.xsjob.ds.model.JobDefinition;
import org.eclipse.dirigible.core.publisher.api.handlers.MetadataPublisherHandler;
import org.eclipse.dirigible.core.publisher.api.handlers.MetadataPublisherHandler;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.database.persistence.PersistenceManager;

public class JobSynchronizerPublisherHandler extends MetadataPublisherHandler {

  @Override
  public void beforePublish(String location) {

  }

  @Override
  public void afterPublish(String workspaceLocation, String registryLocation) {

  }

  @Override
  public void beforeUnpublish(String location) {

  }

  @Override
  public void afterUnpublish(String location) throws SchedulerException {
    removeMetadata(new PersistenceManager<JobDefinition>(), "KRONOS_JOBS", "MODULE", location, false);
  }
}
