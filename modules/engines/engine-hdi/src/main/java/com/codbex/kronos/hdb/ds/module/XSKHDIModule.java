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

import com.codbex.kronos.hdb.ds.api.IXSKDataStructureModel;
import com.codbex.kronos.hdb.ds.module.XSKHDBModule;
import com.codbex.kronos.hdb.ds.parser.XSKDataStructureParser;
import com.codbex.kronos.hdb.ds.parser.hdi.XSKHDIParser;


public class XSKHDIModule extends AbstractDirigibleModule {

  @Override
  public void configure() {

    bindParsersToFileExtension();
  }

  private void bindParsersToFileExtension() {
//    MapBinder<String, XSKDataStructureParser> mapBinder
//        = MapBinder.newMapBinder(binder(), String.class, XSKDataStructureParser.class);

	  Map<String, XSKDataStructureParser> parserServices = XSKHDBModule.getParserServices();
      parserServices.put(IXSKDataStructureModel.TYPE_HDI, new XSKHDIParser());
  }


  @Override
  public String getName() {
    return "XSK HDBTI Module";
  }
}
