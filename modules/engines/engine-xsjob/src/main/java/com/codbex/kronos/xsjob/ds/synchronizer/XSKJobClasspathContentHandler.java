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

import java.io.IOException;
import java.text.ParseException;

import org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.xsjob.ds.api.IXSKJobModel;

public class XSKJobClasspathContentHandler extends AbstractClasspathContentHandler {

  private static final Logger logger = LoggerFactory.getLogger(XSKJobClasspathContentHandler.class);

  private XSKJobSynchronizer jobSynchronizer = new XSKJobSynchronizer();

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler#isValid(java.lang.String)
   */
  @Override
  protected boolean isValid(String path) {
    boolean isValid = false;

    try {
      if (path.endsWith(IXSKJobModel.XSK_JOB_FILE_EXTENSION)) {
        isValid = true;
        jobSynchronizer.registerPredeliveredJob(path);
      }
    } catch (IOException | ParseException e) {
      logger.error("Predelivered Job is not valid", e);
    }

    return isValid;
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
