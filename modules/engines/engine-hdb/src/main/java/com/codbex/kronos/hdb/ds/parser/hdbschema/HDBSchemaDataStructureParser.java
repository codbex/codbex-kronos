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
package com.codbex.kronos.hdb.ds.parser.hdbschema;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.codbex.kronos.parser.hdbschema.core.HDBSchemaParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.artefacts.HDBSchemaSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbschema.DataStructureHDBSchemaModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.parser.hdbschema.core.HDBSchemaLexer;
import com.codbex.kronos.parser.hdbschema.custom.HDBSchemaDefinitionListener;
import com.codbex.kronos.parser.hdbschema.custom.HDBSchemaSyntaxErrorListener;
import com.codbex.kronos.parser.hdbschema.models.HDBSchemaDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;

/**
 * The Class SchemaParser.
 */
public class HDBSchemaDataStructureParser implements DataStructureParser<DataStructureHDBSchemaModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBSchemaDataStructureParser.class);
  
  /** The Constant SCHEMA_ARTEFACT. */
  private static final HDBSchemaSynchronizationArtefactType SCHEMA_ARTEFACT = new HDBSchemaSynchronizationArtefactType();
  
  /** The Constant dataStructuresSynchronizer. */
  private static final DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDB_SCHEMA;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class<DataStructureHDBSchemaModel> getDataStructureClass() {
    return DataStructureHDBSchemaModel.class;
  }

  /**
   * Parses the hdbschema file.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB schema model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public DataStructureHDBSchemaModel parse(DataStructureParametersModel parametersModel)
      throws DataStructuresException, IOException, ArtifactParserException {
    DataStructureHDBSchemaModel hdbSchemaModel = new DataStructureHDBSchemaModel();
    HDBUtils.populateDataStructureModel(parametersModel.getLocation(), parametersModel.getContent(), hdbSchemaModel,
        IDataStructureModel.TYPE_HDB_SCHEMA, DBContentType.XS_CLASSIC);

    ByteArrayInputStream is = new ByteArrayInputStream(parametersModel.getContent().getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HDBSchemaLexer hdbSchemaLexer = new HDBSchemaLexer(inputStream);
    HDBSchemaSyntaxErrorListener lexerErrorListener = new HDBSchemaSyntaxErrorListener();
    hdbSchemaLexer.removeErrorListeners();
    hdbSchemaLexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbSchemaLexer);

    HDBSchemaParser hdbSchemaParser = new HDBSchemaParser(tokenStream);
    hdbSchemaParser.setBuildParseTree(true);
    HDBSchemaSyntaxErrorListener parserErrorListener = new HDBSchemaSyntaxErrorListener();
    hdbSchemaParser.removeErrorListeners();
    hdbSchemaParser.addErrorListener(parserErrorListener);

    ParseTree parseTree = hdbSchemaParser.hdbSchemaDefinition();
    if(parserErrorListener.getErrors().size() !=0 ){
      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(parametersModel.getLocation()),
          parametersModel.getLocation(),SCHEMA_ARTEFACT, ArtefactState.FAILED_CREATE, parserErrorListener.getErrors().get(0).getMsg());
    }
    if(lexerErrorListener.getErrors().size() != 0) {
      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(parametersModel.getLocation()),
          parametersModel.getLocation(),SCHEMA_ARTEFACT, ArtefactState.FAILED_CREATE, lexerErrorListener.getErrors().get(0).getMsg());
    }
    CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, parametersModel.getLocation(),
        CommonsConstants.HDB_SCHEMA_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, parametersModel.getLocation(),
        CommonsConstants.HDB_SCHEMA_PARSER);

    HDBSchemaDefinitionListener hdbSchemaCoreListener = new HDBSchemaDefinitionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbSchemaCoreListener, parseTree);

    HDBSchemaDefinitionModel antlr4Model = hdbSchemaCoreListener.getModel();
    hdbSchemaModel.setSchema(antlr4Model.getSchemaName());

    return hdbSchemaModel;
  }
}
