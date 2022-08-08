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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.synchronizer.cleaners.XSJSLibSynchronizerCleaner;

/**
 * The Class XSJSLibSynchronizerUnpublisher.
 */
public class XSJSLibSynchronizerUnpublisher {
  
  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(XSJSLibSynchronizerUnpublisher.class);

  /** The file cleaner. */
  private final XSJSLibSynchronizerCleaner fileCleaner;

  /** The db cleaner. */
  private final XSJSLibSynchronizerCleaner dbCleaner;

  /**
   * Instantiates a new XSJS lib synchronizer unpublisher.
   *
   * @param fileCleaner the file cleaner
   * @param dbCleaner the db cleaner
   */
  XSJSLibSynchronizerUnpublisher(
      XSJSLibSynchronizerCleaner fileCleaner,
      XSJSLibSynchronizerCleaner dbCleaner
  ) {
    this.fileCleaner = fileCleaner;
    this.dbCleaner = dbCleaner;
  }

  /**
   * Unpublish.
   *
   * @param entity the entity
   */
  public void unpublish(XSJSLibSynchronizerRegistryEntity entity) {
    if(entity.isSynchronizable()) {
      String registryPath = entity.getEntity().getPath();
      if(entity.isCollection()) {
        dbCleaner.cleanup(registryPath);
      }
      else {
        dbCleaner.cleanup(registryPath);
        fileCleaner.cleanup(registryPath);
      }
    }
    else {
      logger.info("XSJSLibSynchronizer: Nothing to cleanup.");
    }
  }
}
