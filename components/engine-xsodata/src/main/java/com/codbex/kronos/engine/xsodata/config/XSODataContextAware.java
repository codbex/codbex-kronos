/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.xsodata.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * A factory for creating XSODataContextAware objects.
 */
public class XSODataContextAware implements ApplicationContextAware {

  /**
   * The odata names validation pattern patcher.
   */
  private final ODataNamesValidationPatternPatcher odataNamesValidationPatternPatcher = new ODataNamesValidationPatternPatcher();

  /**
   * Sets the application context.
   *
   * @param applicationContext the new application context
   * @throws BeansException the beans exception
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    try {
      odataNamesValidationPatternPatcher.patch();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to replace default Olingo OData parameter name pattern.", e);
    }
  }


}
