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
package com.codbex.kronos.hdi.ds.synchronizer;

import org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler;
import org.eclipse.dirigible.commons.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.IEnvironmentVariables;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;

/**
 * The Data Structures HDI Classpath Content Handler.
 */
public class DataStructuresHDIClasspathContentHandler extends AbstractClasspathContentHandler {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(DataStructuresHDIClasspathContentHandler.class);

  /** The data structures HDI synchronizer. */
  private DataStructuresHDISynchronizer dataStructuresHDISynchronizer = new DataStructuresHDISynchronizer();

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

      boolean hdiSupported = Boolean.parseBoolean(Configuration.get(IEnvironmentVariables.KRONOS_HDI_SUPPORTED, "true"));
      if (hdiSupported) {
        try {
          if (path.endsWith(IDataStructureModel.FILE_EXTENSION_HDI)) {
            dataStructuresHDISynchronizer.registerPredeliveredHDI(path);
            return true;
          }
        } catch (DataStructuresException e) {
          logger.error("Predelivered hdi artifact is not valid");
          logger.error(e.getMessage());
        }
      }

    } catch (Exception e) {
      logger.error("Predelivered Artifact is not valid", e);
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
