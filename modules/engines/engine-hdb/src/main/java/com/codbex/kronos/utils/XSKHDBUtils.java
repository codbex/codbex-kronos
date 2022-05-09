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

import com.codbex.kronos.hdb.ds.api.IXSKDataStructureModel;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdb.ds.model.XSKDBContentType;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbdd.XSKDataStructureEntityModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.XSKDataStructureHDBSynonymModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.XSKHDBSYNONYMDefinitionModel;
import com.codbex.kronos.hdb.ds.service.manager.IXSKDataStructureManager;
import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;

import static com.codbex.kronos.utils.XSKCommonsConstants.HDB_PROCEDURE_PARSER;
import static com.codbex.kronos.utils.XSKCommonsConstants.MODULE_PARSERS;
import static com.codbex.kronos.utils.XSKCommonsConstants.PARSER_ERROR;
import static com.codbex.kronos.utils.XSKCommonsConstants.PROGRAM_XSK;
import static com.codbex.kronos.utils.XSKCommonsConstants.SOURCE_PUBLISH_REQUEST;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;

public class XSKHDBUtils {

  private static final String commentRegex = "(/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/)|(--.*)";
  private static final String ESCAPE_SYMBOL = "\"";

  private XSKHDBUtils() {
  }

  public static String getTableName(XSKDataStructureEntityModel model) {
    return getTableName(model, model.getName());
  }

  public static String getTableName(XSKDataStructureEntityModel model, String tableName) {
    return new StringBuilder()
        .append(model.getNamespace()).append("::").append(model.getContext()).append(".").append(tableName)
        .toString();
  }

  /**
   * Escape artifact name if DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE is activated
   *
   * @param artifactName name of the artifact
   * @param schemaName   name of the schema that will be assembled to the artifact name
   * @return escaped in quotes artifact name
   */
  public static String escapeArtifactName(String artifactName, String schemaName) {
    boolean caseSensitive = Boolean.parseBoolean(Configuration.get(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true"));
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
   */
  public static String escapeArtifactName(String artifactName) {
    return escapeArtifactName(artifactName, null);
  }

  public static void populateXSKDataStructureModel(String location, String content, XSKDataStructureModel model, String artifactType,
      XSKDBContentType DbContentType) {
    model.setName(XSKCommonsUtils.getRepositoryBaseObjectName(location));
    model.setLocation(location);
    model.setType(artifactType);
    model.setHash(DigestUtils.md5Hex(content));
    model.setCreatedBy(UserFacade.getName());
    model.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    model.setDbContentType(DbContentType);
  }

  public static void createPublicSynonymForArtifact(IXSKDataStructureManager<XSKDataStructureModel> xskSynonymManagerService,
      String artifactName, String artifactSchema, Connection connection)
      throws SQLException {
    xskSynonymManagerService.createDataStructure(connection, assemblePublicSynonym(artifactName, artifactSchema));
  }

  public static void dropPublicSynonymForArtifact(IXSKDataStructureManager<XSKDataStructureModel> xskSynonymManagerService,
      String artifactName, String artifactSchema, Connection connection)
      throws SQLException {
    xskSynonymManagerService.dropDataStructure(connection, assemblePublicSynonym(artifactName, artifactSchema));
  }

  public static XSKDataStructureHDBSynonymModel assemblePublicSynonym(String artifactName, String artifactSchema) {
    XSKDataStructureHDBSynonymModel model = new XSKDataStructureHDBSynonymModel();

    XSKHDBSYNONYMDefinitionModel defModel = new XSKHDBSYNONYMDefinitionModel();
    defModel.setSynonymSchema(XSKConstants.XSK_SYNONYM_PUBLIC_SCHEMA);
    XSKHDBSYNONYMDefinitionModel.Target target = new XSKHDBSYNONYMDefinitionModel.Target();
    target.setObject(artifactName);
    target.setSchema(artifactSchema);
    defModel.setTarget(target);

    model.getSynonymDefinitions().put(artifactName, defModel);
    model.setType(IXSKDataStructureModel.TYPE_HDB_SYNONYM);
    model.setCreatedBy(UserFacade.getName());
    model.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    return model;
  }

  public static String extractProcedureNameFromContent(String content, String location) throws XSKDataStructuresException {
    content = removeSqlCommentsFromContent(content);
    int indexOfEndOfProcKeyword = content.toLowerCase().indexOf("procedure") + "procedure".length();
    int indexOfBracket = content.indexOf('(', indexOfEndOfProcKeyword);
    if (indexOfEndOfProcKeyword < 0 || indexOfBracket < 0) {
      String errorMessage = "HDB Procedure file not correct";
      XSKCommonsUtils.logCustomErrors(location, PARSER_ERROR, "", "", errorMessage, "", HDB_PROCEDURE_PARSER, MODULE_PARSERS,
          SOURCE_PUBLISH_REQUEST, PROGRAM_XSK);
      throw new XSKDataStructuresException(errorMessage);
    }
    String procedureName = content.substring(indexOfEndOfProcKeyword, indexOfBracket);
    return procedureName.replace("\\s", "").trim();
  }

  public static String extractTableFunctionNameFromContent(String content, String location, String parser)
      throws XSKDataStructuresException {
    content = removeSqlCommentsFromContent(content);
    int indexOfBracket = content.indexOf('(');
    int indexOfEndOfProcKeyword = content.toLowerCase().indexOf("function") + "function".length();
    if (indexOfBracket > -1 && indexOfEndOfProcKeyword > -1) {
      String procNameWithWhiteSymbols = content.substring(indexOfEndOfProcKeyword, indexOfBracket);
      return procNameWithWhiteSymbols.replace("\\s", "").trim();
    }
    String errMsg = "HDB Table Function file not correct";
    XSKCommonsUtils.logCustomErrors(location, XSKCommonsConstants.PARSER_ERROR, "", "", errMsg,
        "", parser, XSKCommonsConstants.MODULE_PARSERS,
        XSKCommonsConstants.SOURCE_PUBLISH_REQUEST, XSKCommonsConstants.PROGRAM_XSK);
    throw new XSKDataStructuresException(errMsg);
  }

  public static String removeSqlCommentsFromContent(String content) {
    return content.replaceAll(commentRegex, "").trim();
  }
}
