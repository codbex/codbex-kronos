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
package com.codbex.kronos.hdb.ds.parser.hdbtabletype;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypePrimaryKeyModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.transformer.HDBTableDefinitionModelToHDBTableColumnModelTransformer;
import com.codbex.kronos.parser.hdbtable.core.HdbtableLexer;
import com.codbex.kronos.parser.hdbtable.core.HdbtableParser;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableCoreVisitor;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableSyntaxErrorListener;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;
import com.codbex.kronos.parser.hdbtable.model.HDBTableColumnsModel;
import com.codbex.kronos.parser.hdbtable.model.HDBTableDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * The Class HDBTableTypeParser.
 */
public class HDBTableTypeParser implements DataStructureParser<DataStructureHDBTableTypeModel> {

  // TYPE (?:["'](.*)["'].)?["'](.*)["']
  // uses non-capturing group in order to handle the
  /** The Constant TABLE_TYPE_SCHEMA_AND_NAME_PATTERN. */
  // possible case of a table type with only a name and no schema
  private static final Pattern TABLE_TYPE_SCHEMA_AND_NAME_PATTERN = Pattern.compile("TYPE\\s+(?:[\"'](.*)[\"'].)?[\"'](.*)[\"']");
  
  /** The Constant XS_ADVANCED_TABLE_TYPE_PATTERN. */
  private static final Pattern XS_ADVANCED_TABLE_TYPE_PATTERN = Pattern.compile("^(\\t\\n)*(\\s)*TYPE", Pattern.CASE_INSENSITIVE);
  
  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBTableTypeParser.class);
  
  /** The column model transformer. */
  private final HDBTableDefinitionModelToHDBTableColumnModelTransformer columnModelTransformer = new HDBTableDefinitionModelToHDBTableColumnModelTransformer();

  /**
   * Parses the.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB table type model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public DataStructureHDBTableTypeModel parse(DataStructureParametersModel parametersModel)
      throws DataStructuresException, IOException, ArtifactParserException {
    String contentWithoutPossibleComments = HDBUtils.removeSqlCommentsFromContent(parametersModel.getContent());
    Matcher matcher = XS_ADVANCED_TABLE_TYPE_PATTERN.matcher(contentWithoutPossibleComments.trim());
    boolean matchFound = matcher.find();
    return (matchFound)
        ? parseHanaXSAdvancedContent(parametersModel.getLocation(), parametersModel.getContent())
        : parseHanaXSClassicContent(parametersModel.getLocation(), parametersModel.getContent());
  }

  /**
   * Parses the hana XS classic content.
   *
   * @param location the location
   * @param content the content
   * @return the data structure HDB table type model
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  private DataStructureHDBTableTypeModel parseHanaXSClassicContent(String location, String content)
      throws IOException, ArtifactParserException {
    logger.debug("Parsing hdbstructure in Hana XS Classic format");
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HdbtableLexer lexer = new HdbtableLexer(inputStream);
    HDBTableSyntaxErrorListener lexerErrorListener = new HDBTableSyntaxErrorListener();
    lexer.removeErrorListeners();
    lexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HdbtableParser hdbtableParser = new HdbtableParser(tokenStream);
    hdbtableParser.setBuildParseTree(true);
    hdbtableParser.removeErrorListeners();

    HDBTableSyntaxErrorListener parserErrorListener = new HDBTableSyntaxErrorListener();
    hdbtableParser.addErrorListener(parserErrorListener);
    ParseTree parseTree = hdbtableParser.hdbtableDefinition();
    CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location,
        CommonsConstants.HDB_TABLE_TYPE_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location,
        CommonsConstants.HDB_TABLE_TYPE_PARSER);

    HDBTableCoreVisitor hdbtableCoreVisitor = new HDBTableCoreVisitor();

    JsonElement parsedResult = hdbtableCoreVisitor.visit(parseTree);

    Gson gson = new Gson();

    HDBTableDefinitionModel hdbtableDefinitionModel = gson.fromJson(parsedResult, HDBTableDefinitionModel.class);
    try {
      hdbtableDefinitionModel.checkForAllMandatoryFieldsPresence();
    } catch (Exception e) {
      CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
          CommonsConstants.EXPECTED_FIELDS, CommonsConstants.HDB_TABLE_TYPE_PARSER, CommonsConstants.MODULE_PARSERS,
          CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
      throw new HDBTableMissingPropertyException(
          String.format("Wrong format of table definition: [%s]. [%s]", location, e.getMessage()));
    }

    DataStructureHDBTableTypeModel dataStructureHDBTableTypeModel = new DataStructureHDBTableTypeModel();

    HDBUtils.populateDataStructureModel(location, content, dataStructureHDBTableTypeModel, IDataStructureModel.TYPE_HDB_TABLE_TYPE,
        DBContentType.XS_CLASSIC);
    dataStructureHDBTableTypeModel.setSchema(hdbtableDefinitionModel.getSchemaName());
    dataStructureHDBTableTypeModel.setPublicProp(hdbtableDefinitionModel.isPublic());
    dataStructureHDBTableTypeModel.setRawContent(content);
    dataStructureHDBTableTypeModel.setColumns(columnModelTransformer.transform(hdbtableDefinitionModel, location));

    DataStructureHDBTableTypePrimaryKeyModel primaryKey = new DataStructureHDBTableTypePrimaryKeyModel();
    primaryKey.setPrimaryKeyColumns(hdbtableDefinitionModel.getPkColumns().toArray(String[]::new));
    primaryKey.setName("PK_" + dataStructureHDBTableTypeModel.getName());
    dataStructureHDBTableTypeModel.setPrimaryKey(primaryKey);

    hdbtableDefinitionModel.getPkColumns().forEach(key -> {
      List<HDBTableColumnsModel> foundMatchKey = hdbtableDefinitionModel.getColumns().stream().filter(x -> x.getName().equals(key))
          .collect(Collectors.toList());
      if (foundMatchKey.size() != 1) {
        String errMsg = String.format("%s: the column does not have a definition but is specified as a primary key", key);
        CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", errMsg,
            "", CommonsConstants.HDB_TABLE_TYPE_PARSER, CommonsConstants.MODULE_PARSERS,
            CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
        throw new IllegalStateException(errMsg);
      }
    });

    return dataStructureHDBTableTypeModel;
  }

  /**
   * Parses the hana XS advanced content.
   *
   * @param location the location
   * @param content the content
   * @return the data structure HDB table type model
   */
  private DataStructureHDBTableTypeModel parseHanaXSAdvancedContent(String location, String content) {
    logger.debug("Parsing hdbstructure as Hana XS Advanced format");
    DataStructureHDBTableTypeModel dataStructureHDBTableTypeModel = new DataStructureHDBTableTypeModel();

    HDBUtils.populateDataStructureModel(location, content, dataStructureHDBTableTypeModel, IDataStructureModel.TYPE_HDB_TABLE_TYPE,
        DBContentType.OTHERS);
    Pair<String, String> schemaAndNamePair = extractTableTypeSchemaAndName(content);

    dataStructureHDBTableTypeModel.setSchema(schemaAndNamePair.getLeft());
    dataStructureHDBTableTypeModel.setName(schemaAndNamePair.getRight());
    dataStructureHDBTableTypeModel.setRawContent(content);

    return dataStructureHDBTableTypeModel;
  }

  /**
   * Extract table type schema and name.
   *
   * @param content the content
   * @return the pair
   */
  private Pair<String, String> extractTableTypeSchemaAndName(String content) {
    String contentWithoutPossibleComments = HDBUtils.removeSqlCommentsFromContent(content);
    Matcher matcher = TABLE_TYPE_SCHEMA_AND_NAME_PATTERN.matcher(contentWithoutPossibleComments);

    if (!matcher.find()) {
      throw new IllegalStateException("Couldn't extract table type schema and name from content: " + content);
    }

    return Pair.of(matcher.group(1), matcher.group(2));
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDB_TABLE_TYPE;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class<DataStructureHDBTableTypeModel> getDataStructureClass() {
    return DataStructureHDBTableTypeModel.class;
  }
}
