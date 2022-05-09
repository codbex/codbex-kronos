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

import com.codbex.kronos.xssecurestore.ds.api.IXSKSecureStoreModel;

public class XSKSecureStoreClasspathContentHandler extends AbstractClasspathContentHandler {

  private static final Logger logger = LoggerFactory.getLogger(XSKSecureStoreClasspathContentHandler.class);

  @Override
  protected boolean isValid(String path) {

    if (path.endsWith(IXSKSecureStoreModel.FILE_EXTENSION_XSSECURESTORE)) {
      return true;
    }
    return false;
  }

  @Override
  protected Logger getLogger() {
    return logger;
  }
}
