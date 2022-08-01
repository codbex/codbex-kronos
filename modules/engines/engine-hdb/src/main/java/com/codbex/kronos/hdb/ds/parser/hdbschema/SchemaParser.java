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

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBSchemaSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbschema.HDBSchemaDataStructureModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.parser.hdbschema.core.HdbschemaLexer;
import com.codbex.kronos.parser.hdbschema.core.HdbschemaParser;
import com.codbex.kronos.parser.hdbschema.custom.HDBSchemaCoreListener;
import com.codbex.kronos.parser.hdbschema.custom.HDBSchemaSyntaxErrorListener;
import com.codbex.kronos.parser.hdbschema.models.HDBSchemaDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaParser implements DataStructureParser<HDBSchemaDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(SchemaParser.class);
  private static final HDBSchemaSynchronizationArtefactType SCHEMA_ARTEFACT = new HDBSchemaSynchronizationArtefactType();
  private static final DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  @Override
  public String getType() {
    return HDBDataStructureModel.TYPE_HDB_SCHEMA;
  }

  @Override
  public Class<HDBSchemaDataStructureModel> getDataStructureClass() {
    return HDBSchemaDataStructureModel.class;
  }

  @Override
  public HDBSchemaDataStructureModel parse(DataStructureParametersModel parametersModel)
      throws DataStructuresException, IOException, ArtifactParserException {
    HDBSchemaDataStructureModel hdbSchemaModel = new HDBSchemaDataStructureModel();
    HDBUtils.populateDataStructureModel(parametersModel.getLocation(), parametersModel.getContent(), hdbSchemaModel,
        HDBDataStructureModel.TYPE_HDB_SCHEMA, DBContentType.XS_CLASSIC);

    ByteArrayInputStream is = new ByteArrayInputStream(parametersModel.getContent().getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HdbschemaLexer lexer = new HdbschemaLexer(inputStream);
    HDBSchemaSyntaxErrorListener lexerErrorListener = new HDBSchemaSyntaxErrorListener();
    lexer.removeErrorListeners();
    lexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HdbschemaParser hdbschemaParser = new HdbschemaParser(tokenStream);
    hdbschemaParser.setBuildParseTree(true);
    HDBSchemaSyntaxErrorListener parserErrorListener = new HDBSchemaSyntaxErrorListener();
    hdbschemaParser.removeErrorListeners();
    hdbschemaParser.addErrorListener(parserErrorListener);

    ParseTree parseTree = hdbschemaParser.hdbschemaDefinition();
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

    HDBSchemaCoreListener hdbSchemaCoreListener = new HDBSchemaCoreListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbSchemaCoreListener, parseTree);

    HDBSchemaDefinitionModel antlr4Model = hdbSchemaCoreListener.getModel();
    hdbSchemaModel.setSchema(antlr4Model.getSchemaName());

    return hdbSchemaModel;
  }
}
