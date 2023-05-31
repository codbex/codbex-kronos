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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.api.IDataStructureModel;
import com.codbex.kronos.engine.hdb.domain.HDBTableStructure;
import com.codbex.kronos.engine.hdb.domain.HDBTableStructurePrimaryKey;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hdbtable.core.HDBTableLexer;
import com.codbex.kronos.parser.hdbtable.core.HDBTableParser;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableDefinitionVisitor;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableSyntaxErrorListener;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;
import com.codbex.kronos.parser.hdbtable.model.HDBTableColumnsModel;
import com.codbex.kronos.parser.hdbtable.model.HDBTableDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * The Class HDBTableTypeParser.
 */
@Component
public class HDBDataStructureDataStructureParser implements HDBDataStructureParser<HDBTableStructure> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBDataStructureDataStructureParser.class);
  
  /** The column model transformer. */
  private final HDBTableStructureColumnModelTransformer columnModelTransformer = new HDBTableStructureColumnModelTransformer();

  /**
   * Parses the hdbstructure file.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB structure model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public HDBTableStructure parse(HDBParameters parametersModel)
      throws DataStructuresException, IOException, ArtifactParserException {
    ByteArrayInputStream is = new ByteArrayInputStream(parametersModel.getContent().getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HDBTableLexer hdbTableLexer = new HDBTableLexer(inputStream);
    HDBTableSyntaxErrorListener lexerErrorListener = new HDBTableSyntaxErrorListener();
    hdbTableLexer.removeErrorListeners();
    hdbTableLexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbTableLexer);

    HDBTableParser hdbTableParser = new HDBTableParser(tokenStream);
    hdbTableParser.setBuildParseTree(true);
    hdbTableParser.removeErrorListeners();

    HDBTableSyntaxErrorListener parserErrorListener = new HDBTableSyntaxErrorListener();
    hdbTableParser.addErrorListener(parserErrorListener);
    ParseTree parseTree = hdbTableParser.hdbTableDefinition();
    CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, parametersModel.getLocation(),
        CommonsConstants.HDB_STRUCTURE_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, parametersModel.getLocation(),
        CommonsConstants.HDB_STRUCTURE_PARSER);

    HDBTableDefinitionVisitor hdbtableCoreVisitor = new HDBTableDefinitionVisitor();

    JsonElement parsedResult = hdbtableCoreVisitor.visit(parseTree);

    Gson gson = new Gson();

    HDBTableDefinitionModel hdbTableDefinitionModel = gson.fromJson(parsedResult, HDBTableDefinitionModel.class);

    try {
      hdbTableDefinitionModel.checkForAllMandatoryFieldsPresence();
    } catch (Exception e) {
      CommonsUtils.logCustomErrors(parametersModel.getLocation(), CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
          CommonsConstants.EXPECTED_FIELDS, CommonsConstants.HDB_STRUCTURE_PARSER, CommonsConstants.MODULE_PARSERS,
          CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
      throw new HDBTableMissingPropertyException(
          String.format("Wrong format of table definition: [%s]. [%s]", parametersModel.getLocation(), e.getMessage()));
    }

    HDBTableStructure hdbStructureModel = new HDBTableStructure();

    HDBUtils.populateDataStructureModel(parametersModel.getLocation(), parametersModel.getContent(), hdbStructureModel, IDataStructureModel.TYPE_HDB_STRUCTURE,
        true);
    hdbStructureModel.setSchema(hdbTableDefinitionModel.getSchemaName());
    hdbStructureModel.setIsPublic(hdbTableDefinitionModel.isPublic());
    hdbStructureModel.setContent(parametersModel.getContent());
    hdbStructureModel.setType(getType());
    hdbStructureModel.setColumns(columnModelTransformer.transform(hdbTableDefinitionModel, parametersModel.getLocation()));

    HDBTableStructurePrimaryKey primaryKey = new HDBTableStructurePrimaryKey();
    primaryKey.setColumns(hdbTableDefinitionModel.getPkColumns().toArray(String[]::new));
    primaryKey.setName("PK_" + hdbStructureModel.getName());
    hdbStructureModel.setPrimaryKey(primaryKey);

    hdbTableDefinitionModel.getPkColumns().forEach(key -> {
      List<HDBTableColumnsModel> foundMatchKey = hdbTableDefinitionModel.getColumns().stream().filter(x -> x.getName().equals(key))
          .collect(Collectors.toList());
      if (foundMatchKey.size() != 1) {
        String errMsg = String.format("%s: the column does not have a definition but is specified as a primary key", key);
        CommonsUtils.logCustomErrors(parametersModel.getLocation(), CommonsConstants.PARSER_ERROR, "", "", errMsg,
            "", CommonsConstants.HDB_STRUCTURE_PARSER, CommonsConstants.MODULE_PARSERS,
            CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
        throw new IllegalStateException(errMsg);
      }
    });

    return hdbStructureModel;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDB_STRUCTURE;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class<HDBTableStructure> getDataStructureClass() {
    return HDBTableStructure.class;
  }
}
