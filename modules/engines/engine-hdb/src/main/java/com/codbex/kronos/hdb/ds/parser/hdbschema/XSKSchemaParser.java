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

import com.codbex.kronos.exceptions.XSKArtifactParserException;
import com.codbex.kronos.hdb.ds.api.IXSKDataStructureModel;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBSchemaSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.XSKDBContentType;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbschema.XSKDataStructureHDBSchemaModel;
import com.codbex.kronos.hdb.ds.parser.XSKDataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.XSKDataStructuresSynchronizer;
import com.codbex.kronos.parser.hdbschema.custom.XSKHDBSCHEMACoreListener;
import com.codbex.kronos.parser.hdbschema.custom.XSKHDBSCHEMASyntaxErrorListener;
import com.codbex.kronos.parser.hdbschema.models.XSKHDBSCHEMADefinitionModel;
import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;
import com.codbex.kronos.utils.XSKHDBUtils;
import com.codbex.kronos.parser.hdbschema.core.HdbschemaLexer;
import com.codbex.kronos.parser.hdbschema.core.HdbschemaParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XSKSchemaParser implements XSKDataStructureParser<XSKDataStructureHDBSchemaModel> {

  private static final Logger logger = LoggerFactory.getLogger(XSKSchemaParser.class);
  private static final HDBSchemaSynchronizationArtefactType SCHEMA_ARTEFACT = new HDBSchemaSynchronizationArtefactType();
  private static final XSKDataStructuresSynchronizer dataStructuresSynchronizer = new XSKDataStructuresSynchronizer();

  @Override
  public String getType() {
    return IXSKDataStructureModel.TYPE_HDB_SCHEMA;
  }

  @Override
  public Class<XSKDataStructureHDBSchemaModel> getDataStructureClass() {
    return XSKDataStructureHDBSchemaModel.class;
  }

  @Override
  public XSKDataStructureHDBSchemaModel parse(XSKDataStructureParametersModel parametersModel)
      throws XSKDataStructuresException, IOException, XSKArtifactParserException {
    XSKDataStructureHDBSchemaModel hdbSchemaModel = new XSKDataStructureHDBSchemaModel();
    XSKHDBUtils.populateXSKDataStructureModel(parametersModel.getLocation(), parametersModel.getContent(), hdbSchemaModel,
        IXSKDataStructureModel.TYPE_HDB_SCHEMA, XSKDBContentType.XS_CLASSIC);

    ByteArrayInputStream is = new ByteArrayInputStream(parametersModel.getContent().getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HdbschemaLexer lexer = new HdbschemaLexer(inputStream);
    XSKHDBSCHEMASyntaxErrorListener lexerErrorListener = new XSKHDBSCHEMASyntaxErrorListener();
    lexer.removeErrorListeners();
    lexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HdbschemaParser hdbschemaParser = new HdbschemaParser(tokenStream);
    hdbschemaParser.setBuildParseTree(true);
    XSKHDBSCHEMASyntaxErrorListener parserErrorListener = new XSKHDBSCHEMASyntaxErrorListener();
    hdbschemaParser.removeErrorListeners();
    hdbschemaParser.addErrorListener(parserErrorListener);

    ParseTree parseTree = hdbschemaParser.hdbschemaDefinition();
    if(parserErrorListener.getErrors().size() !=0 ){
      dataStructuresSynchronizer.applyArtefactState(XSKCommonsUtils.getRepositoryBaseObjectName(parametersModel.getLocation()),
          parametersModel.getLocation(),SCHEMA_ARTEFACT, ArtefactState.FAILED_CREATE, parserErrorListener.getErrors().get(0).getMsg());
    }
    if(lexerErrorListener.getErrors().size() != 0) {
      dataStructuresSynchronizer.applyArtefactState(XSKCommonsUtils.getRepositoryBaseObjectName(parametersModel.getLocation()),
          parametersModel.getLocation(),SCHEMA_ARTEFACT, ArtefactState.FAILED_CREATE, lexerErrorListener.getErrors().get(0).getMsg());
    }
    XSKCommonsUtils.logParserErrors(parserErrorListener.getErrors(), XSKCommonsConstants.PARSER_ERROR, parametersModel.getLocation(),
        XSKCommonsConstants.HDB_SCHEMA_PARSER);
    XSKCommonsUtils.logParserErrors(lexerErrorListener.getErrors(), XSKCommonsConstants.LEXER_ERROR, parametersModel.getLocation(),
        XSKCommonsConstants.HDB_SCHEMA_PARSER);

    XSKHDBSCHEMACoreListener XSKHDBSCHEMACoreListener = new XSKHDBSCHEMACoreListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(XSKHDBSCHEMACoreListener, parseTree);

    XSKHDBSCHEMADefinitionModel antlr4Model = XSKHDBSCHEMACoreListener.getModel();
    hdbSchemaModel.setSchema(antlr4Model.getSchemaName());

    return hdbSchemaModel;
  }
}
