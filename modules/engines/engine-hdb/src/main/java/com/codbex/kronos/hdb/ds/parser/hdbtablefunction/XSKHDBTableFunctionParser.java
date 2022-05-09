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
package com.codbex.kronos.hdb.ds.parser.hdbtablefunction;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;

import com.codbex.kronos.parser.hana.core.HanaLexer;
import com.codbex.kronos.parser.hana.core.HanaParser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;

import com.codbex.kronos.exceptions.XSKArtifactParserException;
import com.codbex.kronos.hdb.ds.api.IXSKDataStructureModel;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.XSKDataStructureHDBTableFunctionModel;
import com.codbex.kronos.hdb.ds.parser.XSKDataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.XSKDataStructuresSynchronizer;
import com.codbex.kronos.parser.hana.custom.HanaTableFunctionListener;
import com.codbex.kronos.parser.hana.models.TableFunctionDefinitionModel;
import com.codbex.kronos.parser.hana.models.TableFunctionMissingPropertyException;
import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;

import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;

public class XSKHDBTableFunctionParser implements XSKDataStructureParser<XSKDataStructureHDBTableFunctionModel> {

  private final XSKDataStructuresSynchronizer dataStructuresSynchronizer;
  private final HDBTableFunctionSynchronizationArtefactType tableFunctionSynchronizationArtefactType;
  private final XSKHDBTableFunctionLogger tableFunctionLogger;

  public XSKHDBTableFunctionParser(
      XSKDataStructuresSynchronizer dataStructuresSynchronizer,
      HDBTableFunctionSynchronizationArtefactType tableFunctionSynchronizationArtefactType,
      XSKHDBTableFunctionLogger tableFunctionLogger
  ) {
    this.dataStructuresSynchronizer = dataStructuresSynchronizer;
    this.tableFunctionSynchronizationArtefactType = tableFunctionSynchronizationArtefactType;
    this.tableFunctionLogger = tableFunctionLogger;
  }

  @Override
  public XSKDataStructureHDBTableFunctionModel parse(XSKDataStructureParametersModel parametersModel)
      throws IOException, XSKDataStructuresException, XSKArtifactParserException {

    String location = parametersModel.getLocation();

    CharStream inputStream;
    try (ByteArrayInputStream is = new ByteArrayInputStream(parametersModel.getContent().getBytes())) {
      inputStream = CharStreams.fromStream(is);
    }

    HanaLexer lexer = new HanaLexer(inputStream);
    lexer.removeErrorListeners();
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HanaParser parser = new HanaParser(tokenStream);
    parser.setBuildParseTree(true);
    parser.removeErrorListeners();

    ParseTree parseTree = parser.sql_script();

    HanaTableFunctionListener listener = new HanaTableFunctionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(listener, parseTree);

    TableFunctionDefinitionModel antlr4Model = listener.getModel();
    validateAntlrModel(antlr4Model, location);

    return createModel(antlr4Model, parametersModel);
  }

  private XSKDataStructureHDBTableFunctionModel createModel(TableFunctionDefinitionModel antlrModel,
      XSKDataStructureParametersModel params) {

    XSKDataStructureHDBTableFunctionModel model = new XSKDataStructureHDBTableFunctionModel();
    model.setSchema(antlrModel.getSchema());
    model.setName(antlrModel.getName());
    model.setLocation(params.getLocation());
    model.setType(getType());
    model.setHash(DigestUtils.md5Hex(params.getContent())); //NOSONAR
    model.setCreatedBy(UserFacade.getName());
    model.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    model.setContent(params.getContent());
    model.setRawContent(params.getContent());

    return model;
  }

  private void validateAntlrModel(TableFunctionDefinitionModel antlrModel, String location) throws XSKDataStructuresException {
    try {
      antlrModel.checkForAllMandatoryFieldsPresence();
    } catch (TableFunctionMissingPropertyException e) {
      tableFunctionLogger.logError(location, XSKCommonsConstants.EXPECTED_FIELDS, e.getMessage());
      dataStructuresSynchronizer.applyArtefactState(XSKCommonsUtils.getRepositoryBaseObjectName(location),
          location,
          tableFunctionSynchronizationArtefactType,
          ArtefactState.FAILED_CREATE,
          e.getMessage());

      throw new XSKDataStructuresException("Wrong format of HDB Table Function: " + location + " during parsing. ", e);
    }
  }

  @Override
  public String getType() {
    return IXSKDataStructureModel.TYPE_HDB_TABLE_FUNCTION;
  }

  @Override
  public Class<XSKDataStructureHDBTableFunctionModel> getDataStructureClass() {
    return XSKDataStructureHDBTableFunctionModel.class;
  }
}
