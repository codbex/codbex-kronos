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
package com.codbex.kronos.hdi.ds.module;

import java.util.Map;

import com.codbex.kronos.engine.hdb.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.module.HDBModule;
import com.codbex.kronos.hdi.ds.parser.HDIParser;

import org.eclipse.dirigible.commons.api.module.IDirigibleModule;

/**
 * The Class HDIModule.
 */
public class HDIModule implements IDirigibleModule {

  /**
   * Configure.
   */
  @Override
  public void configure() {
    bindParsersToFileExtension();
  }

  /**
   * Bind parsers to file extension.
   */
  private void bindParsersToFileExtension() {
    // MapBinder<String, DataStructureParser> mapBinder = MapBinder.newMapBinder(binder(), String.class, DataStructureParser.class);

    Map<String, DataStructureParser> parserServices = HDBModule.getParserServices();
    parserServices.put(IDataStructureModel.TYPE_HDI, new HDIParser());
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return "Kronos HDI Module";
  }

  @Override
  public int getPriority() {
    return PRIORITY_ENGINE;
  }
}
