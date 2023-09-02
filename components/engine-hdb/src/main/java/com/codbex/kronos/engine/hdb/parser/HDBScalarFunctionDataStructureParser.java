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
package com.codbex.kronos.engine.hdb.parser;

import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.api.IDataStructureModel;
import com.codbex.kronos.engine.hdb.domain.HDBScalarFunction;
import com.codbex.kronos.utils.CommonsConstants;

/**
 * The Class HDBScalarFunctionParser.
 */
@Component
public class HDBScalarFunctionDataStructureParser implements HDBDataStructureParser<HDBScalarFunction> {

  /**
   * Parses the hdbscalarfunction file.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB scalar function model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public HDBScalarFunction parse(HDBParameters parametersModel) throws DataStructuresException {
    HDBScalarFunction hdbScalarFunctionModel = new HDBScalarFunction();
    hdbScalarFunctionModel.setName(HDBUtils.extractTableFunctionNameFromContent(parametersModel.getContent(), parametersModel.getLocation(),
        CommonsConstants.HDB_SCALAR_FUNCTION_PARSER));
    hdbScalarFunctionModel.setLocation(parametersModel.getLocation());
    hdbScalarFunctionModel.setType(getType());
    hdbScalarFunctionModel.setContent(parametersModel.getContent());
    return hdbScalarFunctionModel;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDB_SCALAR_FUNCTION;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class<HDBScalarFunction> getDataStructureClass() {
    return HDBScalarFunction.class;
  }
}



