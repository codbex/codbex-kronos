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
package com.codbex.kronos.hdb.ds.parser.hdbview;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.codbex.kronos.hdb.ds.artefacts.HDBViewSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureDependencyModel;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbview.DataStructureHDBViewModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.parser.hdbview.core.HdbviewLexer;
import com.codbex.kronos.parser.hdbview.core.HdbviewParser;
import com.codbex.kronos.parser.hdbview.custom.HDBViewCoreListener;
import com.codbex.kronos.parser.hdbview.custom.HDBViewErrorListener;
import com.codbex.kronos.parser.hdbview.models.HDBViewDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;

/**
 * The Class HDBViewParser.
 */
public class HDBViewParser implements DataStructureParser<DataStructureHDBViewModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBViewParser.class);
  
  /** The Constant VIEW_ARTEFACT. */
  private static final HDBViewSynchronizationArtefactType VIEW_ARTEFACT = new HDBViewSynchronizationArtefactType();
  
  /** The Constant dataStructuresSynchronizer. */
  private static final DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  /**
   * Parses the.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB view model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public DataStructureHDBViewModel parse(DataStructureParametersModel parametersModel)
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
  private DataStructureHDBViewModel parseHanaXSAdvancedContent(String location, String content) {
    logger.debug("Parsing hdbview as Hana XS Advanced format");
    DataStructureHDBViewModel hdbViewModel = new DataStructureHDBViewModel();
    HDBUtils
        .populateDataStructureModel(location, content, hdbViewModel, IDataStructureModel.TYPE_HDB_VIEW, DBContentType.OTHERS);
    hdbViewModel.setRawContent(content);
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
  private DataStructureHDBViewModel parseHanaXSClassicContent(String location, String content)
      throws DataStructuresException, IOException, ArtifactParserException {
    logger.debug("Parsing hdbview as Hana XS Classic format");
    DataStructureHDBViewModel hdbViewModel = new DataStructureHDBViewModel();
    HDBUtils
        .populateDataStructureModel(location, content, hdbViewModel, IDataStructureModel.TYPE_HDB_VIEW, DBContentType.XS_CLASSIC);

    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);

    HdbviewLexer lexer = new HdbviewLexer(inputStream);
    HDBViewErrorListener lexerErrorListener = new HDBViewErrorListener();
    lexer.removeErrorListeners();//remove the ConsoleErrorListener
    lexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HDBViewErrorListener parserErrorListener = new HDBViewErrorListener();
    HdbviewParser parser = new HdbviewParser(tokenStream);
    parser.setBuildParseTree(true);
    parser.removeErrorListeners();
    parser.addErrorListener(parserErrorListener);

    ParseTree parseTree = parser.hdbviewDefinition();
    CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location, CommonsConstants.HDB_VIEW_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location, CommonsConstants.HDB_VIEW_PARSER);

    HDBViewCoreListener hdbViewCoreListener = new HDBViewCoreListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbViewCoreListener, parseTree);

    HDBViewDefinitionModel antlr4Model = hdbViewCoreListener.getModel();
    try {
      antlr4Model.checkForAllMandatoryFieldsPresence();
    } catch (Exception e) {
      CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
          CommonsConstants.EXPECTED_FIELDS, CommonsConstants.HDB_VIEW_PARSER,CommonsConstants.MODULE_PARSERS,
          CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location),location, VIEW_ARTEFACT, ArtefactState.FAILED_CREATE, e.getMessage());
      throw new DataStructuresException(String.format("Wrong format of HDB View: [%s] during parsing. [%s]", location, e.getMessage()));
    }
    hdbViewModel.setQuery(antlr4Model.getQuery());
    hdbViewModel.setSchema(antlr4Model.getSchema());
    hdbViewModel.setPublic(antlr4Model.isPublic());
    hdbViewModel.setDependsOn(antlr4Model.getDependsOn());
    hdbViewModel.setDependsOnTable(antlr4Model.getDependsOnTable());
    hdbViewModel.setDependsOnView(antlr4Model.getDependsOnView());

    //here we do not know if the artifact is table or view, will be set as none
    if (antlr4Model.getDependsOn() != null) {
      antlr4Model.getDependsOn().forEach(el -> {
        DataStructureDependencyModel dependencyModel = new DataStructureDependencyModel(el, "none");
        if (!hdbViewModel.getDependencies().contains(dependencyModel)) {
          hdbViewModel.getDependencies().add(dependencyModel);
        }
      });
    }
    if (antlr4Model.getDependsOnTable() != null) {
      antlr4Model.getDependsOnTable().forEach(el -> {
        DataStructureDependencyModel dependencyModel = new DataStructureDependencyModel(el, "TABLE");
        if (!hdbViewModel.getDependencies().contains(dependencyModel)) {
          hdbViewModel.getDependencies().add(dependencyModel);
        }
      });
    }
    if (antlr4Model.getDependsOnView() != null) {
      antlr4Model.getDependsOnView().forEach(el -> {
        DataStructureDependencyModel dependencyModel = new DataStructureDependencyModel(el, "VIEW");
        if (!hdbViewModel.getDependencies().contains(dependencyModel)) {
          hdbViewModel.getDependencies().add(dependencyModel);
        }
      });
    }

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
  public Class<DataStructureHDBViewModel> getDataStructureClass() {
    return DataStructureHDBViewModel.class;
  }

}