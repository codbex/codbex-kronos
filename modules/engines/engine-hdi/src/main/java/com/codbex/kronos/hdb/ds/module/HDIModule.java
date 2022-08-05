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
package com.codbex.kronos.hdb.ds.module;

import java.util.Map;

import org.eclipse.dirigible.commons.api.module.AbstractDirigibleModule;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.module.HDBModule;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.parser.hdi.HDIParser;


public class HDIModule extends AbstractDirigibleModule {

  @Override
  public void configure() {

    bindParsersToFileExtension();
  }

  private void bindParsersToFileExtension() {
//    MapBinder<String, DataStructureParser> mapBinder
//        = MapBinder.newMapBinder(binder(), String.class, DataStructureParser.class);

	  Map<String, DataStructureParser> parserServices = HDBModule.getParserServices();
      parserServices.put(IDataStructureModel.TYPE_HDI, new HDIParser());
  }


  @Override
  public String getName() {
    return "Kronos HDBTI Module";
  }
}
