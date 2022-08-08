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
package com.codbex.kronos.xsodata.ds.service;

import java.sql.SQLException;
import java.util.List;
import org.eclipse.dirigible.engine.odata2.definition.ODataDefinition;
import org.eclipse.dirigible.engine.odata2.definition.ODataHandlerDefinition;

import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class OData2ODataHTransformer.
 */
public class OData2ODataHTransformer {

  /** The o data 2 O data H transformer. */
  private org.eclipse.dirigible.engine.odata2.transformers.OData2ODataHTransformer oData2ODataHTransformer = new org.eclipse.dirigible.engine.odata2.transformers.OData2ODataHTransformer();

  /**
   * Transform.
   *
   * @param oDataDefinition the o data definition
   * @return the list
   * @throws SQLException the SQL exception
   */
  public List<ODataHandlerDefinition> transform(ODataDefinition oDataDefinition) throws SQLException {
    try {
      return oData2ODataHTransformer.transform(oDataDefinition);
    } catch (Exception e) {
      CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, oDataDefinition.getLocation(), CommonsConstants.ODATA_PARSER);
      throw e;
    }
  }
}
