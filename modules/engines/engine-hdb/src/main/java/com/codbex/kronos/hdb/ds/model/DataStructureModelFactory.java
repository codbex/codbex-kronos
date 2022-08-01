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
import com.codbex.kronos.hdb.ds.model.hdbschema.HDBSchemaDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.HDBTableTypeDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbview.HDBViewDataStructureModel;
import com.codbex.kronos.hdb.ds.parser.hdbdd.HDBDDParser;
import com.codbex.kronos.hdb.ds.parser.hdbschema.SchemaParser;
import com.codbex.kronos.hdb.ds.parser.hdbsynonym.SynonymParser;
import com.codbex.kronos.hdb.ds.parser.hdbtable.TableParser;
import com.codbex.kronos.hdb.ds.parser.hdbtabletype.TableTypeParser;
import com.codbex.kronos.hdb.ds.parser.hdbview.ViewParser;
import com.codbex.kronos.utils.CommonsConstants;

/**
 * The factory for creation of the data structure models from source content.
 */
public class DataStructureModelFactory {

  static Map<String, String> TYPES_MAP = new HashMap<>();

  static {
    TYPES_MAP.put("String", "VARCHAR");
    TYPES_MAP.put("UTCTimestamp", "TIMESTAMP");
  }

  /**
   * Creates a table model from the raw content.
   *
   * @param content the table definition
   * @return the table model instance
   */
  public static HDBTableDataStructureModel parseTable(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    TableParser parser = new TableParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    return parser.parse(parametersModel);
  }

  /**
   * Creates a table model from the raw content.
   *
   * @param bytes the table definition
   * @return the table model instance
   */
  public static HDBTableDataStructureModel parseTable(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseTable(location, new String(bytes));
  }

  /**
   * Creates a view model from the raw content.
   *
   * @param content the view definition
   * @return the view model instance
   */
  public static HDBViewDataStructureModel parseView(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    ViewParser parser = new ViewParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    return parser.parse(parametersModel);
  }

  public static DataStructureModel parseHdbdd(String location, String content) throws DataStructuresException, IOException {
    HDBDDParser parser = new HDBDDParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, CommonsConstants.REGISTRY_PUBLIC);
    return parser.parse(parametersModel);
  }

  /**
   * Creates a view model from the raw content.
   *
   * @param bytes the view definition
   * @return the view model instance
   */
  public static HDBViewDataStructureModel parseView(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseView(location, new String(bytes));
  }

  /**
   * Creates a synonym model from the raw content.
   *
   * @param content the synonym definition
   * @return the synonym model instance
   */
  public static HDBSynonymDataStructureModel parseSynonym(String location, String content)
      throws DataStructuresException, IOException {
    SynonymParser parser = new SynonymParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    return parser.parse(parametersModel);
  }

  /**
   * Creates a schema model from the raw content.
   *
   * @param content the schema definition
   * @return the schema model instance
   */
  public static HDBSchemaDataStructureModel parseSchema(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    SchemaParser parser = new SchemaParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    return parser.parse(parametersModel);
  }

  /**
   * Creates a schema model from the raw content.
   *
   * @param bytes the schema definition
   * @return the schema model instance
   */
  public static HDBSchemaDataStructureModel parseSchema(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseSchema(location, new String(bytes));
  }

  /**
   * Creates a table type model from the raw content.
   *
   * @param content the table type definition
   * @return the table type model instance
   */
  public static HDBTableTypeDataStructureModel parseTableType(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    TableTypeParser parser = new TableTypeParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    return parser.parse(parametersModel);
  }

  /**
   * Creates a table type model from the raw content.
   *
   * @param bytes the table type definition
   * @return the table type model instance
   */
  public static HDBTableTypeDataStructureModel parseTableType(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseTableType(location, new String(bytes));
  }
}