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
package com.codbex.kronos.hdb.ds.service.parser;

import com.codbex.kronos.exceptions.XSKArtifactParserException;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureModel;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureParametersModel;
import com.codbex.kronos.hdb.ds.module.XSKHDBModule;
import com.codbex.kronos.hdb.ds.parser.XSKDataStructureParser;

import java.io.IOException;
import java.util.Map;

public class XSKCoreParserService implements IXSKCoreParserService {

  private Map<String, XSKDataStructureParser> parserServices = XSKHDBModule.getParserServices();

  @Override
  public XSKDataStructureModel parseDataStructure(XSKDataStructureParametersModel parametersModel)
      throws XSKDataStructuresException, IOException, XSKArtifactParserException {

    XSKDataStructureParser<?> parser = parserServices.get(parametersModel.getType());
    return parser.parse(parametersModel);
  }

  @Override
  public Class<XSKDataStructureModel> getDataStructureClass(String type) {
    return (Class<XSKDataStructureModel>) parserServices.get(type).getDataStructureClass();
  }
}
