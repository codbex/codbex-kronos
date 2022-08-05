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
package com.codbex.kronos.hdbti.synchronizer;

import java.io.IOException;
import org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdbti.api.ITableImportModel;

public class TableImportClasspathContentHandler extends AbstractClasspathContentHandler {

  private static final Logger logger = LoggerFactory.getLogger(com.codbex.kronos.hdbti.synchronizer.TableImportClasspathContentHandler.class);

  private TableImportSynchronizer tableImportSynchronizer = new TableImportSynchronizer();

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler#isValid(java.lang.String)
   */
  @Override
  protected boolean isValid(String path) {
    try {
      if (path.endsWith(ITableImportModel.FILE_EXTENSION_TABLE_IMPORT)) {
        tableImportSynchronizer.registerPredeliveredTableImports(path);
        return true;
      }
    } catch (IOException e) {
      logger.error("Predelvered table import is not valid", e);
    } catch (Exception e) {
      logger.error("Predelvered table import is not valid", e);
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
