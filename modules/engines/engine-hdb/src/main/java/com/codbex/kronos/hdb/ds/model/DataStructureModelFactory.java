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
package com.codbex.kronos.hdb.ds.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.hdbschema.DataStructureHDBSchemaModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.DataStructureHDBSynonymModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.model.hdbview.DataStructureHDBViewModel;
import com.codbex.kronos.hdb.ds.parser.hdbdd.HDBDDParser;
import com.codbex.kronos.hdb.ds.parser.hdbschema.SchemaParser;
import com.codbex.kronos.hdb.ds.parser.hdbsynonym.HDBSynonymParser;
import com.codbex.kronos.hdb.ds.parser.hdbtable.HDBTableParser;
import com.codbex.kronos.hdb.ds.parser.hdbtabletype.HDBTableTypeParser;
import com.codbex.kronos.hdb.ds.parser.hdbview.HDBViewParser;
import com.codbex.kronos.utils.CommonsConstants;

/**
 * The factory for creation of the data structure models from source content.
 */
public class DataStructureModelFactory {

  /** The types map. */
  static Map<String, String> TYPES_MAP = new HashMap<>();

  static {
    TYPES_MAP.put("String", "VARCHAR");
    TYPES_MAP.put("UTCTimestamp", "TIMESTAMP");
  }

  /**
   * Creates a table model from the raw content.
   *
   * @param location the location
   * @param content the table definition
   * @return the table model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataStructureHDBTableModel parseTable(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBTableParser parser = new HDBTableParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    return parser.parse(parametersModel);
  }

  /**
   * Creates a table model from the raw content.
   *
   * @param location the location
   * @param bytes the table definition
   * @return the table model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataStructureHDBTableModel parseTable(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseTable(location, new String(bytes));
  }

  /**
   * Creates a view model from the raw content.
   *
   * @param location the location
   * @param content the view definition
   * @return the view model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataStructureHDBViewModel parseView(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBViewParser parser = new HDBViewParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    return parser.parse(parametersModel);
  }

  /**
   * Parses the hdbdd.
   *
   * @param location the location
   * @param content the content
   * @return the data structure model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataStructureModel parseHdbdd(String location, String content) throws DataStructuresException, IOException {
    HDBDDParser parser = new HDBDDParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, CommonsConstants.REGISTRY_PUBLIC);
    return parser.parse(parametersModel);
  }

  /**
   * Creates a view model from the raw content.
   *
   * @param location the location
   * @param bytes the view definition
   * @return the view model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataStructureHDBViewModel parseView(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseView(location, new String(bytes));
  }

  /**
   * Creates a synonym model from the raw content.
   *
   * @param location the location
   * @param content the synonym definition
   * @return the synonym model instance
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataStructureHDBSynonymModel parseSynonym(String location, String content)
      throws DataStructuresException, IOException {
    HDBSynonymParser parser = new HDBSynonymParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    return parser.parse(parametersModel);
  }

  /**
   * Creates a schema model from the raw content.
   *
   * @param location the location
   * @param content the schema definition
   * @return the schema model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataStructureHDBSchemaModel parseSchema(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    SchemaParser parser = new SchemaParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    return parser.parse(parametersModel);
  }

  /**
   * Creates a schema model from the raw content.
   *
   * @param location the location
   * @param bytes the schema definition
   * @return the schema model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataStructureHDBSchemaModel parseSchema(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseSchema(location, new String(bytes));
  }

  /**
   * Creates a table type model from the raw content.
   *
   * @param location the location
   * @param content the table type definition
   * @return the table type model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataStructureHDBTableTypeModel parseTableType(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBTableTypeParser parser = new HDBTableTypeParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    return parser.parse(parametersModel);
  }

  /**
   * Creates a table type model from the raw content.
   *
   * @param location the location
   * @param bytes the table type definition
   * @return the table type model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataStructureHDBTableTypeModel parseTableType(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseTableType(location, new String(bytes));
  }
}