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
package com.codbex.kronos.hdi.ds.parser;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdi.ds.model.DataStructureHDIModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdi.ds.parser.deserializer.HDIModelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;

/**
 * The Class HDIParser.
 */
public class HDIParser implements DataStructureParser {

  /**
   * Parses the.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDI model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public DataStructureHDIModel parse(DataStructureParametersModel parametersModel) throws DataStructuresException, IOException {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(DataStructureHDIModel.class, new HDIModelAdapter())
        .create();

    DataStructureHDIModel hdiModel = gson.fromJson(parametersModel.getContent(), DataStructureHDIModel.class);
    hdiModel.setName(new File(parametersModel.getLocation()).getName());
    hdiModel.setLocation(parametersModel.getLocation());
    hdiModel.setType(getType());
    hdiModel.setHash(DigestUtils.md5Hex(parametersModel.getContent()));
    hdiModel.setCreatedBy(UserFacade.getName());
    hdiModel.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    hdiModel.setContent(parametersModel.getContent());
    return hdiModel;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDI;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class getDataStructureClass() {
    return DataStructureHDIModel.class;
  }
}
