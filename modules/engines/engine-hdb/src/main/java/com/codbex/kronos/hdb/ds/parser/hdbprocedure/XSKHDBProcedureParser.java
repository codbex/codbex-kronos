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
package com.codbex.kronos.hdb.ds.parser.hdbprocedure;

import java.sql.Timestamp;

import com.codbex.kronos.hdb.ds.api.IXSKDataStructureModel;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbprocedure.XSKDataStructureHDBProcedureModel;
import com.codbex.kronos.hdb.ds.parser.XSKDataStructureParser;
import com.codbex.kronos.utils.XSKHDBUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;

public class XSKHDBProcedureParser implements XSKDataStructureParser {

  @Override
  public XSKDataStructureHDBProcedureModel parse(XSKDataStructureParametersModel parametersModel) throws XSKDataStructuresException {
    XSKDataStructureHDBProcedureModel hdbProcedure = new XSKDataStructureHDBProcedureModel();
    hdbProcedure.setName(XSKHDBUtils.extractProcedureNameFromContent(parametersModel.getContent(),
        parametersModel.getLocation()));
    hdbProcedure.setLocation(parametersModel.getLocation());
    hdbProcedure.setType(getType());
    hdbProcedure.setHash(DigestUtils.md5Hex(parametersModel.getContent()));
    hdbProcedure.setCreatedBy(UserFacade.getName());
    hdbProcedure.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    hdbProcedure.setContent(parametersModel.getContent());
    return hdbProcedure;
  }

  @Override
  public String getType() {
    return IXSKDataStructureModel.TYPE_HDB_PROCEDURE;
  }

  @Override
  public Class getDataStructureClass() {
    return XSKDataStructureHDBProcedureModel.class;
  }
}
