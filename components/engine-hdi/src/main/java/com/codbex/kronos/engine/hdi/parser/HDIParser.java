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
package com.codbex.kronos.engine.hdi.parser;

import java.io.File;
import java.io.IOException;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.api.IDataStructureModel;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureParser;
import com.codbex.kronos.engine.hdb.parser.HDBParameters;
import com.codbex.kronos.engine.hdi.domain.HDI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class HDIParser.
 */
public class HDIParser implements HDBDataStructureParser {

  /**
   * Parses the hdi artefact.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDI model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public HDI parse(HDBParameters parametersModel) {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(HDI.class, new HDIModelAdapter())
        .create();

    HDI hdiModel = gson.fromJson(parametersModel.getContent(), HDI.class);
    hdiModel.setName(new File(parametersModel.getLocation()).getName());
    hdiModel.setLocation(parametersModel.getLocation());
    hdiModel.setType(getType());
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
    return HDI.class;
  }
}
