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
package com.codbex.kronos.hdbti.module;

import org.eclipse.dirigible.commons.api.module.IDirigibleModule;

/**
 * The Class HDBTIModule.
 */
public class HDBTIModule implements IDirigibleModule {

  /**
   * Configure.
   */
  @Override
  public void configure() {
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return "Kronos HDBTI Module";
  }

  @Override
  public int getPriority() {
    return PRIORITY_ENGINE;
  }
}
