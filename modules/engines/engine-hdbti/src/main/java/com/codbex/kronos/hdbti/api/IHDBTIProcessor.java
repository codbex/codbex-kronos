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

/**
 * The Interface IHDBTIProcessor.
 */
public interface IHDBTIProcessor {

  /**
   * Process.
   *
   * @param tableImportConfigurationDefinition the table import configuration definition
   * @param connection the connection
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SQLException the SQL exception
   * @throws DataStructuresException the data structures exception
   * @throws TableImportException the table import exception
   */
  void process(TableImportConfigurationDefinition tableImportConfigurationDefinition, Connection connection)
      throws IOException, SQLException, DataStructuresException, TableImportException;

  /**
   * Parses the HDBTI to JSON.
   *
   * @param location the location
   * @param file the file
   * @return the list
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws HDBTISyntaxErrorException the HDBTI syntax error exception
   */
  List<HDBTIImportConfigModel> parseHDBTIToJSON(String location, byte[] file)
      throws ArtifactParserException, IOException, HDBTISyntaxErrorException;

  /**
   * Parses the JSO nto hdbti.
   *
   * @param json the json
   * @return the string
   */
  String parseJSONtoHdbti(ArrayList<HDBTIImportConfigModel> json);
}
