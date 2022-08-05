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

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdbti.model.TableImportConfigurationDefinition;
import com.codbex.kronos.parser.hdbti.exception.HDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportConfigModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IHDBTIProcessor {

  void process(TableImportConfigurationDefinition tableImportConfigurationDefinition, Connection connection)
      throws IOException, SQLException, DataStructuresException, TableImportException;

  List<HDBTIImportConfigModel> parseHDBTIToJSON(String location, byte[] file)
      throws ArtifactParserException, IOException, HDBTISyntaxErrorException;

  String parseJSONtoHdbti(ArrayList<HDBTIImportConfigModel> json);
}
