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
package com.codbex.kronos.synchronizer;

import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.core.publisher.api.handlers.MetadataPublisherHandler;
import org.eclipse.dirigible.repository.api.IRepository;

import com.codbex.kronos.synchronizer.cleaners.XSJSLibSynchronizerCleaner;
import com.codbex.kronos.synchronizer.cleaners.XSJSLibSynchronizerDBCleaner;
import com.codbex.kronos.synchronizer.cleaners.XSJSLibSynchronizerFileCleaner;

import javax.sql.DataSource;

public class XSJSLibSynchronizerPublisherHandler extends MetadataPublisherHandler {
  private static final DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
  private static final IRepository repository = (IRepository) StaticObjects.get(StaticObjects.REPOSITORY);

  private final XSJSLibSynchronizerCleaner dbCleaner = new XSJSLibSynchronizerDBCleaner(dataSource);
  private final XSJSLibSynchronizerCleaner fileCleaner = new XSJSLibSynchronizerFileCleaner(repository);
  private final XSJSLibSynchronizerUnpublisher unpublisher = new XSJSLibSynchronizerUnpublisher(fileCleaner, dbCleaner);

  @Override
  public void afterPublish(String workspaceLocation, String registryLocation) {
    XSJSLibSynchronizer.forceSynchronization(registryLocation);
  }

  @Override
  public void beforeUnpublish(String location) {
    XSJSLibSynchronizerRegistryEntity entity = new XSJSLibSynchronizerRegistryEntity(location, repository, true);
    unpublisher.unpublish(entity);
  }
}
