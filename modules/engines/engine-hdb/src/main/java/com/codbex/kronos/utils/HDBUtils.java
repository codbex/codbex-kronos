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
package com.codbex.kronos.utils;

import static com.codbex.kronos.utils.CommonsConstants.HDB_PROCEDURE_PARSER;
import static com.codbex.kronos.utils.CommonsConstants.MODULE_PARSERS;
import static com.codbex.kronos.utils.CommonsConstants.PARSER_ERROR;
import static com.codbex.kronos.utils.CommonsConstants.PROGRAM_KRONOS;
import static com.codbex.kronos.utils.CommonsConstants.SOURCE_PUBLISH_REQUEST;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.config.Configuration;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbdd.DataStructureEntityModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.DataStructureHDBSynonymModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDefinitionModel;
import com.codbex.kronos.hdb.ds.service.manager.IDataStructureManager;
import com.codbex.kronos.parser.hana.core.HanaLexer;
import com.codbex.kronos.parser.hana.core.HanaParser;

/**
 * The Class HDBUtils.
 */
public class HDBUtils {

  /** The Constant commentRegex. */
  private static final String commentRegex = "(/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/)|(--.*)";
  
  /** The Constant ESCAPE_SYMBOL. */
  private static final String ESCAPE_SYMBOL = "\"";

  /**
   * Instantiates a new HDB utils.
   */
  private HDBUtils() {
  }

  /**
   * Gets the table name.
   *
   * @param model the model
   * @return the table name
   */
  public static String getTableName(DataStructureEntityModel model) {
    return getTableName(model, model.getName());
  }

  /**
   * Gets the table name.
   *
   * @param model the model
   * @param tableName the table name
   * @return the table name
   */
  public static String getTableName(DataStructureEntityModel model, String tableName) {
    return new StringBuilder()
        .append(model.getNamespace()).append("::").append(model.getContext()).append(".").append(tableName)
        .toString();
  }

  /**
   * Escape artifact name if DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE is activated.
   *
   * @param artifactName name of the artifact
   * @param schemaName   name of the schema that will be assembled to the artifact name
   * @return escaped in quotes artifact name
   */
  public static String escapeArtifactName(String artifactName, String schemaName) {
    boolean caseSensitive = Boolean.parseBoolean(Configuration.get("DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE", "true"));
    if (!artifactName.startsWith(ESCAPE_SYMBOL)) {
      if (caseSensitive) {
        artifactName = ESCAPE_SYMBOL + artifactName + ESCAPE_SYMBOL;
      }
    }

    if (schemaName != null && !schemaName.trim().isEmpty()) {
      if (!schemaName.startsWith(ESCAPE_SYMBOL)) {
        if (caseSensitive) {
          schemaName = ESCAPE_SYMBOL + schemaName + ESCAPE_SYMBOL + ".";
        } else {
          schemaName = schemaName + ".";
        }
      }
    } else {
      schemaName = "";
    }

    return schemaName + artifactName;
  }

  /**
   * See also {@link #escapeArtifactName(String, String)}.
   *
   * @param artifactName the artifact name
   * @return the string
   */
  public static String escapeArtifactName(String artifactName) {
    return escapeArtifactName(artifactName, null);
  }

  /**
   * Populate data structure model.
   *
   * @param location the location
   * @param content the content
   * @param model the model
   * @param artifactType the artifact type
   * @param DbContentType the db content type
   */
  public static void populateDataStructureModel(String location, String content, DataStructureModel model, String artifactType,
      DBContentType DbContentType) {
    model.setName(CommonsUtils.getRepositoryBaseObjectName(location));
    model.setLocation(location);
    model.setType(artifactType);
    model.setHash(DigestUtils.md5Hex(content));
    model.setCreatedBy(UserFacade.getName());
    model.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    model.setDbContentType(DbContentType);
  }

  /**
   * Creates the public synonym for artifact.
   *
   * @param synonymManagerService the synonym manager service
   * @param artifactName the artifact name
   * @param artifactSchema the artifact schema
   * @param connection the connection
   * @throws SQLException the SQL exception
   */
  public static void createPublicSynonymForArtifact(IDataStructureManager<DataStructureModel> synonymManagerService,
      String artifactName, String artifactSchema, Connection connection)
      throws SQLException {
    synonymManagerService.createDataStructure(connection, assemblePublicSynonym(artifactName, artifactSchema));
  }

  /**
   * Drop public synonym for artifact.
   *
   * @param synonymManagerService the synonym manager service
   * @param artifactName the artifact name
   * @param artifactSchema the artifact schema
   * @param connection the connection
   * @throws SQLException the SQL exception
   */
  public static void dropPublicSynonymForArtifact(IDataStructureManager<DataStructureModel> synonymManagerService,
      String artifactName, String artifactSchema, Connection connection)
      throws SQLException {
    synonymManagerService.dropDataStructure(connection, assemblePublicSynonym(artifactName, artifactSchema));
  }

  /**
   * Assemble public synonym.
   *
   * @param artifactName the artifact name
   * @param artifactSchema the artifact schema
   * @return the data structure HDB synonym model
   */
  public static DataStructureHDBSynonymModel assemblePublicSynonym(String artifactName, String artifactSchema) {
    DataStructureHDBSynonymModel model = new DataStructureHDBSynonymModel();

    HDBSynonymDefinitionModel defModel = new HDBSynonymDefinitionModel();
    defModel.setSynonymSchema(Constants.SYNONYM_PUBLIC_SCHEMA);
    HDBSynonymDefinitionModel.Target target = new HDBSynonymDefinitionModel.Target();
    target.setObject(artifactName);
    target.setSchema(artifactSchema);
    defModel.setTarget(target);

    model.getSynonymDefinitions().put(artifactName, defModel);
    model.setType(IDataStructureModel.TYPE_HDB_SYNONYM);
    model.setCreatedBy(UserFacade.getName());
    model.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    return model;
  }

  /**
   * Extract procedure name from content.
   *
   * @param content the content
   * @param location the location
   * @return the string
   * @throws DataStructuresException the data structures exception
   */
  public static String extractProcedureNameFromContent(String content, String location) throws DataStructuresException {
    content = removeSqlCommentsFromContent(content);
    int indexOfEndOfProcKeyword = content.toLowerCase().indexOf("procedure") + "procedure".length();
    int indexOfBracket = content.indexOf('(', indexOfEndOfProcKeyword);
    if (indexOfEndOfProcKeyword < 0 || indexOfBracket < 0) {
      String errorMessage = "HDB Procedure file not correct";
      CommonsUtils.logCustomErrors(location, PARSER_ERROR, "", "", errorMessage, "", HDB_PROCEDURE_PARSER, MODULE_PARSERS,
          SOURCE_PUBLISH_REQUEST, PROGRAM_KRONOS);
      throw new DataStructuresException(errorMessage);
    }
    String procedureName = content.substring(indexOfEndOfProcKeyword, indexOfBracket);
    return procedureName.replace("\\s", "").trim();
  }

  /**
   * Extract table function name from content.
   *
   * @param content the content
   * @param location the location
   * @param parser the parser
   * @return the string
   * @throws DataStructuresException the data structures exception
   */
  public static String extractTableFunctionNameFromContent(String content, String location, String parser)
      throws DataStructuresException {
    content = removeSqlCommentsFromContent(content);
    int indexOfBracket = content.indexOf('(');
    int indexOfEndOfProcKeyword = content.toLowerCase().indexOf("function") + "function".length();
    if (indexOfBracket > -1 && indexOfEndOfProcKeyword > -1) {
      String procNameWithWhiteSymbols = content.substring(indexOfEndOfProcKeyword, indexOfBracket);
      return procNameWithWhiteSymbols.replace("\\s", "").trim();
    }
    String errMsg = "HDB Table Function file not correct";
    CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", errMsg,
        "", parser, CommonsConstants.MODULE_PARSERS,
        CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
    throw new DataStructuresException(errMsg);
  }

  /**
   * Removes the sql comments from content.
   *
   * @param content the content
   * @return the string
   */
  public static String removeSqlCommentsFromContent(String content) {
    return content.replaceAll(commentRegex, "").trim();
  }
  
  /**
   * Gets the parsed three.
   *
   * @param parametersModel the parameters model
   * @return the parsed three
   * @throws ArtifactParserException the artifact parser exception
   */
  public static ParseTree getParsedThree (DataStructureParametersModel parametersModel) throws ArtifactParserException {

    CharStream inputStream;
    try (ByteArrayInputStream is = new ByteArrayInputStream(parametersModel.getContent().getBytes())) {
      inputStream = CharStreams.fromStream(is);
    }catch (IOException exception){
      throw new ArtifactParserException("Cannot get parsed tree",exception);
    }

    HanaLexer lexer = new HanaLexer(inputStream);
    lexer.removeErrorListeners();
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HanaParser parser = new HanaParser(tokenStream);
    parser.setBuildParseTree(true);
    parser.removeErrorListeners();

    return parser.sql_script();

  }

  /**
   * Gets the timestamp.
   *
   * @return the timestamp
   */
  public static Timestamp getTimestamp(){
    return new Timestamp(new java.util.Date().getTime());
  }

}
