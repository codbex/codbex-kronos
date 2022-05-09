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
package com.codbex.kronos.hdb.ds.parser.hdi;

import com.codbex.kronos.hdb.ds.api.IXSKDataStructureModel;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdi.XSKDataStructureHDIModel;
import com.codbex.kronos.hdb.ds.parser.XSKDataStructureParser;
import com.codbex.kronos.hdb.ds.parser.hdi.deserializer.XSKHDIModelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;

public class XSKHDIParser implements XSKDataStructureParser {

  @Override
  public XSKDataStructureHDIModel parse(XSKDataStructureParametersModel parametersModel) throws XSKDataStructuresException, IOException {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(XSKDataStructureHDIModel.class, new XSKHDIModelAdapter())
        .create();

    XSKDataStructureHDIModel hdiModel = gson.fromJson(parametersModel.getContent(), XSKDataStructureHDIModel.class);
    hdiModel.setName(new File(parametersModel.getLocation()).getName());
    hdiModel.setLocation(parametersModel.getLocation());
    hdiModel.setType(getType());
    hdiModel.setHash(DigestUtils.md5Hex(parametersModel.getContent()));
    hdiModel.setCreatedBy(UserFacade.getName());
    hdiModel.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    hdiModel.setContent(parametersModel.getContent());
    return hdiModel;
  }

  @Override
  public String getType() {
    return IXSKDataStructureModel.TYPE_HDI;
  }

  @Override
  public Class getDataStructureClass() {
    return XSKDataStructureHDIModel.class;
  }
}
