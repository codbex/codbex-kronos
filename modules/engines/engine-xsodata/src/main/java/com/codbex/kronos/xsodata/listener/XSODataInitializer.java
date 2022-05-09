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
package com.codbex.kronos.xsodata.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class XSODataInitializer implements ServletContextListener {

  private final ODataNamesValidationPatternPatcher odataNamesValidationPatternPatcher = new ODataNamesValidationPatternPatcher();

  public void contextInitialized(ServletContextEvent sce) {
    try {
      odataNamesValidationPatternPatcher.patch();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to replace default Olingo OData parameter name pattern.", e);
    }
  }
}