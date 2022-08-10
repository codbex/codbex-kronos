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
package com.codbex.kronos.xsodata.ds.synchronizer;

import org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.xsodata.ds.api.IODataModel;

/**
 * The OData Classpath Content Handler.
 */
public class ODataClasspathContentHandler extends AbstractClasspathContentHandler {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(ODataClasspathContentHandler.class);

  /** The odata synchronizer. */
  private final ODataSynchronizer odataSynchronizer = new ODataSynchronizer();

  /**
   * Checks if is valid.
   *
   * @param path the path
   * @return true, if is valid
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler#isValid(java.lang.String)
   */
  @Override
  protected boolean isValid(String path) {

    try {
      if (path.endsWith(IODataModel.FILE_EXTENSION_XSODATA)) {
        odataSynchronizer.registerPredeliveredOData(path);
        return true;
      }
    } catch (Exception e) {
      logger.error("Predelivered Table or View is not valid", e);
    }

    return false;
  }

  /**
   * Gets the logger.
   *
   * @return the logger
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler#getLogger()
   */
  @Override
  protected Logger getLogger() {
    return logger;
  }

}
