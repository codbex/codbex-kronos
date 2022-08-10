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
package com.codbex.kronos.xssecurestore.ds.synchronizer;

import org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.xssecurestore.ds.api.ISecureStoreModel;

/**
 * The Class SecureStoreClasspathContentHandler.
 */
public class SecureStoreClasspathContentHandler extends AbstractClasspathContentHandler {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(SecureStoreClasspathContentHandler.class);

  /**
   * Checks if is valid.
   *
   * @param path the path
   * @return true, if is valid
   */
  @Override
  protected boolean isValid(String path) {

    if (path.endsWith(ISecureStoreModel.FILE_EXTENSION_XSSECURESTORE)) {
      return true;
    }
    return false;
  }

  /**
   * Gets the logger.
   *
   * @return the logger
   */
  @Override
  protected Logger getLogger() {
    return logger;
  }
}
