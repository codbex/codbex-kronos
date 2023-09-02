/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
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
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.api.IDataStructureModel;
import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hdbview.core.HDBViewLexer;
import com.codbex.kronos.parser.hdbview.core.HDBViewParser;
import com.codbex.kronos.parser.hdbview.custom.HDBViewDefinitionListener;
import com.codbex.kronos.parser.hdbview.custom.HDBViewErrorListener;
import com.codbex.kronos.parser.hdbview.models.HDBViewDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class HDBViewParser.
 */
@Component
public class HDBViewDataStructureParser implements HDBDataStructureParser<HDBView> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBViewDataStructureParser.class);
  
  /**
   * Parses the hdbview file.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB view model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public HDBView parse(HDBParameters parametersModel)
      throws DataStructuresException, IOException, ArtifactParserException {
    Pattern pattern = Pattern.compile("^(\\t\\n)*(\\s)*VIEW", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(parametersModel.getContent().trim().toUpperCase(Locale.ROOT));
    boolean matchFound = matcher.find();
    return (matchFound)
        ? parseHanaXSAdvancedContent(parametersModel.getLocation(), parametersModel.getContent())
        : parseHanaXSClassicContent(parametersModel.getLocation(), parametersModel.getContent());
  }

  /**
   * Parses the hana XS advanced content.
   *
   * @param location the location
   * @param content the content
   * @return the data structure HDB view model
   */
  private HDBView parseHanaXSAdvancedContent(String location, String content) {
    logger.debug("Parsing HDB View as Hana XS Advanced format");
    HDBView hdbViewModel = new HDBView();
    HDBUtils.populateDataStructureModel(location, content, hdbViewModel, IDataStructureModel.TYPE_HDB_VIEW, false);
    hdbViewModel.setContent(content);
    return hdbViewModel;
  }

  /**
   * Parses the hana XS classic content.
   *
   * @param location the location
   * @param content the content
   * @return the data structure HDB view model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  private HDBView parseHanaXSClassicContent(String location, String content)
      throws DataStructuresException, IOException, ArtifactParserException {
    logger.debug("Parsing HDB View as Hana XS Classic format");
    HDBView hdbViewModel = new HDBView();
    HDBUtils.populateDataStructureModel(location, content, hdbViewModel, IDataStructureModel.TYPE_HDB_VIEW, true);

    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);

    HDBViewLexer hdbViewLexer = new HDBViewLexer(inputStream);
    HDBViewErrorListener lexerErrorListener = new HDBViewErrorListener();
    hdbViewLexer.removeErrorListeners(); // Remove the ConsoleErrorListener
    hdbViewLexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbViewLexer);

    HDBViewErrorListener parserErrorListener = new HDBViewErrorListener();
    HDBViewParser hdbViewParser = new HDBViewParser(tokenStream);
    hdbViewParser.setBuildParseTree(true);
    hdbViewParser.removeErrorListeners();
    hdbViewParser.addErrorListener(parserErrorListener);

    ParseTree parseTree = hdbViewParser.hdbViewDefinition();
    CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location, CommonsConstants.HDB_VIEW_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location, CommonsConstants.HDB_VIEW_PARSER);

    HDBViewDefinitionListener hdbViewDefinitionListener = new HDBViewDefinitionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbViewDefinitionListener, parseTree);

    HDBViewDefinitionModel antlr4Model = hdbViewDefinitionListener.getModel();
    try {
      antlr4Model.checkForAllMandatoryFieldsPresence();
    } catch (Exception e) {
      CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
          CommonsConstants.EXPECTED_FIELDS, CommonsConstants.HDB_VIEW_PARSER,CommonsConstants.MODULE_PARSERS,
          CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
//      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location),location, VIEW_ARTEFACT, ArtefactState.FAILED_CREATE, e.getMessage());
      throw new DataStructuresException(String.format("Wrong format of HDB View: [%s] during parsing. [%s]", location, e.getMessage()));
    }
    hdbViewModel.setQuery(antlr4Model.getQuery());
    hdbViewModel.setSchema(antlr4Model.getSchema());
    hdbViewModel.setPublic(antlr4Model.isPublic());
    hdbViewModel.setDependsOn(antlr4Model.getDependsOn());
    hdbViewModel.setDependsOnTable(antlr4Model.getDependsOnTable());
    hdbViewModel.setDependsOnView(antlr4Model.getDependsOnView());

    // TODO to support back the dependencies
//    // Here we do not know if the artifact is table or view, will be set as none
//    if (antlr4Model.getDependsOn() != null) {
//      antlr4Model.getDependsOn().forEach(el -> {
//        DataStructureDependencyModel dependencyModel = new DataStructureDependencyModel(el, "none");
//        if (!hdbViewModel.getDependencies().contains(dependencyModel)) {
//          hdbViewModel.getDependencies().add(dependencyModel);
//        }
//      });
//    }
//    if (antlr4Model.getDependsOnTable() != null) {
//      antlr4Model.getDependsOnTable().forEach(el -> {
//        DataStructureDependencyModel dependencyModel = new DataStructureDependencyModel(el, "TABLE");
//        if (!hdbViewModel.getDependencies().contains(dependencyModel)) {
//          hdbViewModel.getDependencies().add(dependencyModel);
//        }
//      });
//    }
//    if (antlr4Model.getDependsOnView() != null) {
//      antlr4Model.getDependsOnView().forEach(el -> {
//        DataStructureDependencyModel dependencyModel = new DataStructureDependencyModel(el, "VIEW");
//        if (!hdbViewModel.getDependencies().contains(dependencyModel)) {
//          hdbViewModel.getDependencies().add(dependencyModel);
//        }
//      });
//    }

    return hdbViewModel;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDB_VIEW;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class<HDBView> getDataStructureClass() {
    return HDBView.class;
  }

}