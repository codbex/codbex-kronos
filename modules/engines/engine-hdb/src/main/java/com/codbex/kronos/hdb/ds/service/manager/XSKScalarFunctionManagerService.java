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
package com.codbex.kronos.hdb.ds.service.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.IXSKDataStructureModel;

public class XSKScalarFunctionManagerService extends XSKTableFunctionManagerService {

  private static final Logger logger = LoggerFactory.getLogger(XSKScalarFunctionManagerService.class);

  @Override
  public String getDataStructureType() {
    return IXSKDataStructureModel.FILE_EXTENSION_HDBSCALARFUNCTION;
  }

}

