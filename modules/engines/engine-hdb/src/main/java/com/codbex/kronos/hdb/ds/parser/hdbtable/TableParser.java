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
package com.codbex.kronos.hdb.ds.parser.hdbtable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableConstraintPrimaryKeyDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableConstraintUniqueDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableConstraintsDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableIndexDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableDataStructureModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.hdb.ds.transformer.HDBTableDefinitionModelToHDBTableColumnModelTransformer;
import com.codbex.kronos.parser.hdbtable.core.HdbtableLexer;
import com.codbex.kronos.parser.hdbtable.core.HdbtableParser;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableCoreVisitor;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableSyntaxErrorListener;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;
import com.codbex.kronos.parser.hdbtable.model.HDBTableColumnsModel;
import com.codbex.kronos.parser.hdbtable.model.HDBTableDefinitionModel;
import com.codbex.kronos.parser.hdbtable.model.HDBTableIndexesModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Constants;
import com.codbex.kronos.utils.HDBUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableParser implements DataStructureParser<HDBTableDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(TableParser.class);
  private HDBTableDefinitionModelToHDBTableColumnModelTransformer columnModelTransformer = new HDBTableDefinitionModelToHDBTableColumnModelTransformer();
  private static final HDBTableSynchronizationArtefactType TABLE_ARTEFACT = new HDBTableSynchronizationArtefactType();
  private static final DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  @Override
  public String getType() {
    return HDBDataStructureModel.TYPE_HDB_TABLE;
  }

  @Override
  public Class<HDBTableDataStructureModel> getDataStructureClass() {
    return HDBTableDataStructureModel.class;
  }

  @Override
  public HDBTableDataStructureModel parse(DataStructureParametersModel parametersModel)
      throws DataStructuresException, IOException, ArtifactParserException {
    Pattern pattern = Pattern.compile("^(\\t\\n)*(\\s)*(COLUMN)(\\t\\n)*(\\s)*(TABLE)", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(parametersModel.getContent().trim().toUpperCase(Locale.ROOT));
    boolean matchFound = matcher.find();
    return (matchFound)
        ? parseHanaXSAdvancedContent(parametersModel.getLocation(), parametersModel.getContent())
        : parseHanaXSClassicContent(parametersModel.getLocation(), parametersModel.getContent());
  }

  private HDBTableDataStructureModel parseHanaXSClassicContent(String location, String content)
      throws IOException, ArtifactParserException {
    logger.debug("Parsing hdbtable as Hana XS Classic format");
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
    CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location, CommonsConstants.HDB_TABLE_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location, CommonsConstants.HDB_TABLE_PARSER);

    HDBTableCoreVisitor hdbTableCoreVisitor = new HDBTableCoreVisitor();

    JsonElement parsedResult = hdbTableCoreVisitor.visit(parseTree);

    Gson gson = new Gson();

    HDBTableDefinitionModel hdbTableDefinitionModel = gson.fromJson(parsedResult, HDBTableDefinitionModel.class);
    String artefactName = CommonsUtils.getRepositoryBaseObjectName(location);
    try {
      hdbTableDefinitionModel.checkForAllMandatoryFieldsPresence();
    } catch (Exception e) {
      CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
          CommonsConstants.EXPECTED_FIELDS, CommonsConstants.HDB_TABLE_PARSER, CommonsConstants.MODULE_PARSERS,
          CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
      dataStructuresSynchronizer.applyArtefactState(artefactName,location,TABLE_ARTEFACT, ArtefactState.FAILED_CREATE, e.getMessage());
      throw new HDBTableMissingPropertyException(
          String.format("Wrong format of table definition: [%s]. [%s]", location, e.getMessage()));
    }

    HDBTableDataStructureModel dataStructureHDBTableModel = new HDBTableDataStructureModel();

    HDBUtils.populateDataStructureModel(location, content, dataStructureHDBTableModel, HDBDataStructureModel.TYPE_HDB_TABLE,
        DBContentType.XS_CLASSIC);
    dataStructureHDBTableModel.setSchema(hdbTableDefinitionModel.getSchemaName());
    dataStructureHDBTableModel.setDescription(hdbTableDefinitionModel.getDescription());
    dataStructureHDBTableModel.setLoggingType(hdbTableDefinitionModel.getLoggingType());
    dataStructureHDBTableModel.setPublicProp(hdbTableDefinitionModel.isPublic());
    dataStructureHDBTableModel.setTemporary(hdbTableDefinitionModel.getTemporary());
    dataStructureHDBTableModel.setTableType(hdbTableDefinitionModel.getTableType());
    dataStructureHDBTableModel.setRawContent(content);
    dataStructureHDBTableModel.setColumns(columnModelTransformer.transform(hdbTableDefinitionModel, location));
    dataStructureHDBTableModel.setConstraints(new HDBTableConstraintsDataStructureModel());

    HDBTableConstraintPrimaryKeyDataStructureModel primaryKey = new HDBTableConstraintPrimaryKeyDataStructureModel();
    primaryKey.setColumns(hdbTableDefinitionModel.getPkColumns().toArray(String[]::new));
    primaryKey.setName("PK_" + dataStructureHDBTableModel.getName());
    dataStructureHDBTableModel.getConstraints().setPrimaryKey(primaryKey);

    hdbTableDefinitionModel.getPkColumns().forEach(key -> {
      List<HDBTableColumnsModel> foundMatchKey = hdbTableDefinitionModel.getColumns().stream().filter(x -> x.getName().equals(key))
          .collect(Collectors.toList());
      if (foundMatchKey.size() != 1) {
        String errMsg = String.format("%s: the column does not have a definition but is specified as a primary key", key);
        CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", errMsg,
            "", CommonsConstants.HDB_TABLE_PARSER,CommonsConstants.MODULE_PARSERS,
            CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
        dataStructuresSynchronizer.applyArtefactState(artefactName,location,TABLE_ARTEFACT, ArtefactState.FAILED_CREATE, errMsg);
        throw new IllegalStateException(errMsg);
      }
    });

    List<HDBTableConstraintUniqueDataStructureModel> uniqueIndices = new ArrayList<>();
    List<HDBTableIndexDataStructureModel> indexes = new ArrayList<>();

    if (hdbTableDefinitionModel.getIndexes() != null) {
      for (HDBTableIndexesModel index : hdbTableDefinitionModel.getIndexes()) {
        validateIndex(hdbTableDefinitionModel, location, index);
        if (index.isUnique()) {
          HDBTableConstraintUniqueDataStructureModel uniqueIndexModel = new HDBTableConstraintUniqueDataStructureModel();
          if(index.getIndexName() != null){
            uniqueIndexModel.setIndexName(index.getIndexName());
          }
          if (index.getIndexType() != null) {
            uniqueIndexModel.setIndexType(this.convertIfHanaClassicSyntax(index));
          }
          if (index.getOrder() != null) {
            uniqueIndexModel
                .setOrder(index.getOrder().equals(Constants.HDBTABLE_INDEX_ORDER_HANA_V1_DSC) ? Constants.HDBTABLE_INDEX_ORDER_HANA_DESC : index.getOrder());
          }
          uniqueIndexModel.setColumns(index.getIndexColumns().toArray(String[]::new));

          uniqueIndices.add(uniqueIndexModel);
        }else{
          HDBTableIndexDataStructureModel tableIndexModel = new HDBTableIndexDataStructureModel();
          if(index.getIndexName() != null){
            tableIndexModel.setIndexName(index.getIndexName());
          }
          if (index.getIndexType() != null) {
            tableIndexModel.setIndexType(convertIfHanaClassicSyntax(index));
          }
          tableIndexModel.setUnique(index.isUnique());
          if (index.getOrder() != null) {
            tableIndexModel
                .setOrder(index.getOrder().equals(Constants.HDBTABLE_INDEX_ORDER_HANA_V1_DSC) ? Constants.HDBTABLE_INDEX_ORDER_HANA_DESC : index.getOrder());
          }
          tableIndexModel.setIndexColumns(index.getIndexColumns());
          indexes.add(tableIndexModel);
        }
      }
      dataStructureHDBTableModel.getConstraints().setUniqueIndices(uniqueIndices);
      dataStructureHDBTableModel.setIndexes(indexes);
    }

    return dataStructureHDBTableModel;
  }

  private HDBTableDataStructureModel parseHanaXSAdvancedContent(String location, String content) {
    logger.debug("Parsing hdbtable as Hana XS Advanced format");
    HDBTableDataStructureModel dataStructureHDBTableModel = new HDBTableDataStructureModel();
    HDBUtils.populateDataStructureModel(location, content, dataStructureHDBTableModel, HDBDataStructureModel.TYPE_HDB_TABLE,
        DBContentType.OTHERS);
    dataStructureHDBTableModel.setRawContent(content);
    return dataStructureHDBTableModel;
  }

  private void validateIndex(HDBTableDefinitionModel hdbtableDefinitionModel, String location, HDBTableIndexesModel index) {
    String artefactName = CommonsUtils.getRepositoryBaseObjectName(location);
    try {
      index.checkForAllIndexMandatoryFieldsPresence();
    } catch (Exception e) {
      CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
          CommonsConstants.EXPECTED_FIELDS, CommonsConstants.HDB_TABLE_PARSER, CommonsConstants.MODULE_PARSERS,
          CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
      dataStructuresSynchronizer.applyArtefactState(artefactName,location,TABLE_ARTEFACT, ArtefactState.FAILED_CREATE, e.getMessage());
      throw new HDBTableMissingPropertyException(
          String.format("Wrong format of table definition: [%s]. [%s]", location, e.getMessage()));
    }

    index.getIndexColumns().forEach(col -> {
      List<HDBTableColumnsModel> foundMatch = hdbtableDefinitionModel.getColumns().stream().filter(x -> x.getName().equals(col))
          .collect(Collectors.toList());
      if (foundMatch.size() != 1) {
        String errMsg = String.format("%s: the column does not have a definition but is specified as an index", col);
        CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", errMsg,
            "", CommonsConstants.HDB_TABLE_PARSER, CommonsConstants.MODULE_PARSERS,
            CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
        dataStructuresSynchronizer.applyArtefactState(artefactName,location,TABLE_ARTEFACT, ArtefactState.FAILED_CREATE, errMsg);
        throw new IllegalStateException(errMsg);
      }
    });
  }

  private String convertIfHanaClassicSyntax(HDBTableIndexesModel index) {
    if (index.getIndexType().equals("CPB_TREE")) {
      return "CPBTREE";
    } else if (index.getIndexType().equals("B_TREE")) {
      return "BTREE";
    } else {
      return index.getIndexType();
    }
  }

}

