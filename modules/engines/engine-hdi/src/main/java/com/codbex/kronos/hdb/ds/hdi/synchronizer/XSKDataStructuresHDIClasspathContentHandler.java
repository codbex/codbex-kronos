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
package com.codbex.kronos.hdb.ds.hdi.synchronizer;

import org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler;
import org.eclipse.dirigible.commons.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.IXSKDataStructureModel;
import com.codbex.kronos.hdb.ds.api.IXSKEnvironmentVariables;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;

/**
 * The XSK Data Structures HDI Classpath Content Handler.
 */
public class XSKDataStructuresHDIClasspathContentHandler extends AbstractClasspathContentHandler {

  private static final Logger logger = LoggerFactory.getLogger(XSKDataStructuresHDIClasspathContentHandler.class);

  private XSKDataStructuresHDISynchronizer dataStructuresHDISynchronizer = new XSKDataStructuresHDISynchronizer();

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler#isValid(java.lang.String)
   */
  @Override
  protected boolean isValid(String path) {

    try {

      boolean hdiSupported = Boolean.parseBoolean(Configuration.get(IXSKEnvironmentVariables.XSK_HDI_SUPPORTED, "true"));
      if (hdiSupported) {
        try {
          if (path.endsWith(IXSKDataStructureModel.FILE_EXTENSION_HDI)) {
            dataStructuresHDISynchronizer.registerPredeliveredHDI(path);
            return true;
          }
        } catch (XSKDataStructuresException e) {
          logger.error("Predelivered hdi artifact is not valid");
          logger.error(e.getMessage());
        }
      }

    } catch (Exception e) {
      logger.error("Predelivered Artifact is not valid", e);
    }

    return false;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler#getLogger()
   */
  @Override
  protected Logger getLogger() {
    return logger;
  }

}
