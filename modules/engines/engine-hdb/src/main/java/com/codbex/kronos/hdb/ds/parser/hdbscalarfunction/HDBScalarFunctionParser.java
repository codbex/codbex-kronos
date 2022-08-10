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
package com.codbex.kronos.hdb.ds.parser.hdbscalarfunction;

import java.sql.Timestamp;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbscalarfunction.DataStructureHDBScalarFunctionModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.HDBUtils;

/**
 * The Class HDBScalarFunctionParser.
 */
public class HDBScalarFunctionParser implements DataStructureParser<DataStructureHDBScalarFunctionModel> {

  /**
   * Parses the.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB scalar function model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public DataStructureHDBScalarFunctionModel parse(DataStructureParametersModel parametersModel) throws DataStructuresException {
	  DataStructureHDBScalarFunctionModel hdbScalarFunction = new DataStructureHDBScalarFunctionModel();
    hdbScalarFunction.setName(HDBUtils.extractTableFunctionNameFromContent(parametersModel.getContent(), parametersModel.getLocation(),
        CommonsConstants.HDB_SCALAR_FUNCTION_PARSER));
    hdbScalarFunction.setLocation(parametersModel.getLocation());
    hdbScalarFunction.setType(getType());
    hdbScalarFunction.setHash(DigestUtils.md5Hex(parametersModel.getContent()));
    hdbScalarFunction.setCreatedBy(UserFacade.getName());
    hdbScalarFunction.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    hdbScalarFunction.setRawContent(parametersModel.getContent());
    return hdbScalarFunction;
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
  public Class<DataStructureHDBScalarFunctionModel> getDataStructureClass() {
    return DataStructureHDBScalarFunctionModel.class;
  }
}



