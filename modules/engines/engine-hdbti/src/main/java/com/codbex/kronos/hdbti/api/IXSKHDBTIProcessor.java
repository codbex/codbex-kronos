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
package com.codbex.kronos.hdbti.api;

import com.codbex.kronos.exceptions.XSKArtifactParserException;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdbti.model.XSKTableImportConfigurationDefinition;
import com.codbex.kronos.parser.hdbti.exception.XSKHDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.models.XSKHDBTIImportConfigModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IXSKHDBTIProcessor {

  void process(XSKTableImportConfigurationDefinition tableImportConfigurationDefinition, Connection connection)
      throws IOException, SQLException, XSKDataStructuresException, XSKTableImportException;

  List<XSKHDBTIImportConfigModel> parseHdbtiToJSON(String location, byte[] file)
      throws XSKArtifactParserException, IOException, XSKHDBTISyntaxErrorException;

  String parseJSONtoHdbti(ArrayList<XSKHDBTIImportConfigModel> json);
}
