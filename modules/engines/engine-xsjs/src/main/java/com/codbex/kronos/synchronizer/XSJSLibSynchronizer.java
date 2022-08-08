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
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer;
import org.eclipse.dirigible.core.scheduler.api.IOrderedSynchronizerContribution;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.engine.js.graalvm.processor.GraalVMJavascriptEngineExecutor;
import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.utils.CommonsConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class XSJSLibSynchronizer.
 */
public class XSJSLibSynchronizer extends AbstractSynchronizer implements IOrderedSynchronizerContribution {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(XSJSLibSynchronizer.class);

  /** The Constant XSJSLIB_GENERATION_RUNNER_LOCATION. */
  private static final String XSJSLIB_GENERATION_RUNNER_LOCATION = "/exports/XSJSLibRunner.mjs";

  /** The Constant XSJSLIB_SYNCHRONIZER_STATE_TABLE_NAME. */
  public static final String XSJSLIB_SYNCHRONIZER_STATE_TABLE_NAME = "PROCESSED_XSJSLIB_ARTEFACTS";

  /** The Constant DONE_SYNCHRONIZING_LOG_MESSAGE. */
  private static final String DONE_SYNCHRONIZING_LOG_MESSAGE = "Done synchronizing XSJSLibs.";

  /** The Constant repository. */
  private static final IRepository repository = (IRepository) StaticObjects.get(StaticObjects.REPOSITORY);

  /** The synchronizer target. */
  private XSJSLibSynchronizerRegistryEntity synchronizerTarget = null;

  /**
   * Instantiates a new XSJS lib synchronizer.
   */
  public XSJSLibSynchronizer() {
    this(CommonsConstants.REGISTRY_PUBLIC);
  }

  /**
   * Instantiates a new XSJS lib synchronizer.
   *
   * @param targetRegistryPath the target registry path
   */
  public XSJSLibSynchronizer(String targetRegistryPath) {
    synchronizerTarget = new XSJSLibSynchronizerRegistryEntity(targetRegistryPath, repository);
  }

  /**
   * Force synchronization.
   *
   * @param targetRegistryPath the target registry path
   */
  public static void forceSynchronization(String targetRegistryPath) {
    XSJSLibSynchronizer synchronizer = new XSJSLibSynchronizer(targetRegistryPath);
    synchronizer.setForcedSynchronization(true);
    try {
      synchronizer.synchronize();
    } finally {
      synchronizer.setForcedSynchronization(false);
    }
  }

  /**
   * Synchronize XSJS libs.
   */
  public void synchronizeXSJSLibs() {
    logger.trace("Synchronizing XSJSLibs...");

    if(!synchronizerTarget.isSynchronizable()) {
      logger.trace(DONE_SYNCHRONIZING_LOG_MESSAGE);
      return;
    }

    Map<Object, Object> context = buildContext();
    GraalVMJavascriptEngineExecutor graalVMJavascriptEngineExecutor = new GraalVMJavascriptEngineExecutor();
    graalVMJavascriptEngineExecutor.executeService(
        XSJSLIB_GENERATION_RUNNER_LOCATION,
        context,
        true,
        false
    );

    logger.trace(DONE_SYNCHRONIZING_LOG_MESSAGE);
  }

  /**
   * Builds the context.
   *
   * @return the map
   */
  private Map<Object, Object> buildContext() {
    Map<Object, Object> context = new HashMap<>();
    context.put("synchronizerTarget", synchronizerTarget);
    context.put("stateTableName", XSJSLIB_SYNCHRONIZER_STATE_TABLE_NAME);
    return context;
  }

  /**
   * Gets the priority.
   *
   * @return the priority
   */
  @Override
  public int getPriority() {
    return 666;
  }

  /**
   * Synchronize resource.
   *
   * @param iResource the i resource
   * @throws SynchronizationException the synchronization exception
   */
  @Override
  protected void synchronizeResource(IResource iResource)
      throws SynchronizationException {
    // Not used.
  }

  /**
   * Synchronize.
   */
  @Override
  public void synchronize() {
    synchronized (XSJSLibSynchronizer.class) {
      if (beforeSynchronizing()) {
        logger.trace("Synchronizing XSJSLibs...");
        synchronizeXSJSLibs();
        afterSynchronizing();
        logger.trace(DONE_SYNCHRONIZING_LOG_MESSAGE);
      }
    }
  }
}
