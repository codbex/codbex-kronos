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

//import org.eclipse.dirigible.commons.config.StaticObjects;
//import org.eclipse.dirigible.core.publisher.api.handlers.MetadataPublisherHandler;
//import org.eclipse.dirigible.repository.api.IRepository;
//
//import com.codbex.kronos.synchronizer.cleaners.XSJSLibSynchronizerCleaner;
//import com.codbex.kronos.synchronizer.cleaners.XSJSLibSynchronizerDBCleaner;
//import com.codbex.kronos.synchronizer.cleaners.XSJSLibSynchronizerFileCleaner;
//
//import javax.sql.DataSource;

/**
 * The Class XSJSLibSynchronizerPublisherHandler.
 */
public class XSJSLibSynchronizerPublisherHandler { //extends MetadataPublisherHandler {
  
//  /** The Constant repository. */
//  private static IRepository getRepository() {
//	  return (IRepository) StaticObjects.get(StaticObjects.REPOSITORY);
//  }
//
//  /** The db cleaner. */
//  private final XSJSLibSynchronizerCleaner dbCleaner = new XSJSLibSynchronizerDBCleaner(getDataSource());
//  
//  /** The file cleaner. */
//  private final XSJSLibSynchronizerCleaner fileCleaner = new XSJSLibSynchronizerFileCleaner(getRepository());
//  
//  /** The unpublisher. */
//  private final XSJSLibSynchronizerUnpublisher unpublisher = new XSJSLibSynchronizerUnpublisher(fileCleaner, dbCleaner);
//
//  /**
//   * After publish.
//   *
//   * @param workspaceLocation the workspace location
//   * @param registryLocation the registry location
//   */
//  @Override
//  public void afterPublish(String workspaceLocation, String registryLocation) {
//    XSJSLibSynchronizer.forceSynchronization(registryLocation);
//  }
//
//  /**
//   * Before unpublish.
//   *
//   * @param location the location
//   */
//  @Override
//  public void beforeUnpublish(String location) {
//    XSJSLibSynchronizerRegistryEntity entity = new XSJSLibSynchronizerRegistryEntity(location, getRepository(), true);
//    unpublisher.unpublish(entity);
//  }
}
