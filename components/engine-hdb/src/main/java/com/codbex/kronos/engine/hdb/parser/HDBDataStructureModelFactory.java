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
package com.codbex.kronos.engine.hdb.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBDD;
import com.codbex.kronos.engine.hdb.domain.HDBProcedure;
import com.codbex.kronos.engine.hdb.domain.HDBScalarFunction;
import com.codbex.kronos.engine.hdb.domain.HDBSchema;
import com.codbex.kronos.engine.hdb.domain.HDBSequence;
import com.codbex.kronos.engine.hdb.domain.HDBSynonymGroup;
import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableFunction;
import com.codbex.kronos.engine.hdb.domain.HDBTableStructure;
import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The factory for creation of the data structure models from source content.
 */
@Component
public class HDBDataStructureModelFactory implements InitializingBean {
	
  /** The instance. */
  private static HDBDataStructureModelFactory INSTANCE;
  
  /** The hdb table data structure parser. */
  @Autowired
  private HDBTableDataStructureParser hdbTableDataStructureParser;
  
  /** The hdb view data structure parser. */
  @Autowired
  private HDBViewDataStructureParser hdbViewDataStructureParser;
  
  /** The hdb procedure data structure parser. */
  @Autowired
  private HDBProcedureDataStructureParser hdbProcedureDataStructureParser;
  
  /** The hdb scalar function data structure parser. */
  @Autowired
  private HDBScalarFunctionDataStructureParser hdbScalarFunctionDataStructureParser;
  
  /** The hdb table function data structure parser. */
  @Autowired
  private HDBTableFunctionDataStructureParser hdbTableFunctionDataStructureParser;
  
  /** The hdb sequence data structure parser. */
  @Autowired
  private HDBSequenceDataStructureParser hdbSequenceDataStructureParser;
  
  /** The hdbdd data structure parser. */
  @Autowired
  private HDBDDDataStructureParser hdbddDataStructureParser;
  
  /** The hdb synonym data structure parser. */
  @Autowired
  private HDBSynonymDataStructureParser hdbSynonymDataStructureParser;
  
  /** The hdb schema data structure parser. */
  @Autowired
  private HDBSchemaDataStructureParser hdbSchemaDataStructureParser;
  
  /** The hdb table type data structure parser. */
  @Autowired
  private HDBTableTypeDataStructureParser hdbTableTypeDataStructureParser;
  
  /** The hdb data structure data structure parser. */
  @Autowired
  private HDBDataStructureDataStructureParser hdbDataStructureDataStructureParser;

  /** The types map. */
  static Map<String, String> TYPES_MAP = new HashMap<>();

  static {
    TYPES_MAP.put("String", "VARCHAR");
    TYPES_MAP.put("UTCTimestamp", "TIMESTAMP");
  }
  
  /**
	 * Instantiates a new HDB data structure model factory.
	 */
  @Autowired
  public HDBDataStructureModelFactory() {
  }
  
  /**
   * After properties set.
   *
   * @throws Exception the exception
   */
  @Override
  public void afterPropertiesSet() throws Exception {
      INSTANCE = this;
  }

  /**
   * Gets the.
   *
   * @return the HDBDataStructureModelFactory instance
   */
  public static HDBDataStructureModelFactory get() {
      return INSTANCE;
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
  public static HDBTable parseTable(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    return get().hdbTableDataStructureParser.parse(parametersModel);
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
  public static HDBTable parseTable(String location, byte[] bytes)
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
  public static HDBView parseView(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    return get().hdbViewDataStructureParser.parse(parametersModel);
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
  public static HDBView parseView(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseView(location, new String(bytes));
  }
  
  /**
   * Creates a procedure model from the raw content.
   *
   * @param location the location
   * @param content the procedure definition
   * @return the procedure model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static HDBProcedure parseProcedure(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    return get().hdbProcedureDataStructureParser.parse(parametersModel);
  }

  /**
   * Creates a procedure model from the raw content.
   *
   * @param location the location
   * @param bytes the procedure definition
   * @return the procedure model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static HDBProcedure parseProcedure(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseProcedure(location, new String(bytes));
  }
  
  /**
   * Creates a ScalarFunction model from the raw content.
   *
   * @param location the location
   * @param content the ScalarFunction definition
   * @return the ScalarFunction model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static HDBScalarFunction parseScalarFunction(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    return get().hdbScalarFunctionDataStructureParser.parse(parametersModel);
  }

  /**
   * Creates a ScalarFunction model from the raw content.
   *
   * @param location the location
   * @param bytes the ScalarFunction definition
   * @return the ScalarFunction model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static HDBScalarFunction parseScalarFunction(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseScalarFunction(location, new String(bytes));
  }
  
  /**
   * Creates a TableFunction model from the raw content.
   *
   * @param location the location
   * @param content the TableFunction definition
   * @return the TableFunction model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static HDBTableFunction parseTableFunction(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    return get().hdbTableFunctionDataStructureParser.parse(parametersModel);
  }

  /**
   * Creates a TableFunction model from the raw content.
   *
   * @param location the location
   * @param bytes the TableFunction definition
   * @return the TableFunction model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static HDBTableFunction parseTableFunction(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseTableFunction(location, new String(bytes));
  }
  
  /**
   * Creates a Sequence model from the raw content.
   *
   * @param location the location
   * @param content the Sequence definition
   * @return the Sequence model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static HDBSequence parseSequence(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    return get().hdbSequenceDataStructureParser.parse(parametersModel);
  }

  /**
   * Creates a Sequence model from the raw content.
   *
   * @param location the location
   * @param bytes the Sequence definition
   * @return the Sequence model instance
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static HDBSequence parseSequence(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseSequence(location, new String(bytes));
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
  public static HDBDD parseHdbdd(String location, String content) throws DataStructuresException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, IRepositoryStructure.PATH_REGISTRY_PUBLIC + IRepositoryStructure.SEPARATOR);
    return get().hdbddDataStructureParser.parse(parametersModel);
  }
  
  /**
   * Parses the hdbdd.
   *
   * @param location the location
   * @param bytes the content
   * @return the data structure model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static HDBDD parseHdbdd(String location, byte[] bytes) throws DataStructuresException, IOException {
    return parseHdbdd(location, new String(bytes));
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
  public static HDBSynonymGroup parseSynonym(String location, String content)
      throws DataStructuresException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    return get().hdbSynonymDataStructureParser.parse(parametersModel);
  }
  
  /**
   * Creates a synonym model from the raw content.
   *
   * @param location the location
   * @param bytes the synonym definition
   * @return the synonym model instance
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static HDBSynonymGroup parseSynonym(String location, byte[] bytes)
      throws DataStructuresException, IOException {
	  return parseSynonym(location, new String(bytes));
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
  public static HDBSchema parseSchema(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    return get().hdbSchemaDataStructureParser.parse(parametersModel);
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
  public static HDBSchema parseSchema(String location, byte[] bytes)
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
  public static HDBTableType parseTableType(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    return get().hdbTableTypeDataStructureParser.parse(parametersModel);
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
  public static HDBTableType parseTableType(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseTableType(location, new String(bytes));
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
  public static HDBTableStructure parseStructure(String location, String content)
      throws DataStructuresException, ArtifactParserException, IOException {
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    return get().hdbDataStructureDataStructureParser.parse(parametersModel);
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
  public static HDBTableStructure parseStructure(String location, byte[] bytes)
      throws DataStructuresException, ArtifactParserException, IOException {
    return parseStructure(location, new String(bytes));
  }
}