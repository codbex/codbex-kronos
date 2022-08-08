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
package com.codbex.kronos.hdb.ds.parser.hdbsequence;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbsequence.DataStructureHDBSequenceModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceBaseVisitor;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceLexer;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceParser;
import com.codbex.kronos.parser.hdbsequence.custom.HdbsequenceVisitor;
import com.codbex.kronos.parser.hdbsequence.custom.HDBSequenceModelAdapter;
import com.codbex.kronos.parser.hdbsequence.custom.HDBSequenceSyntaxErrorListener;
import com.codbex.kronos.parser.hdbsequence.models.HDBSequenceModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HDBSequenceParser.
 */
public class HDBSequenceParser implements DataStructureParser {


  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBSequenceParser.class);

  /**
   * Parses the.
   *
   * @param parametersModel the parameters model
   * @return the data structure model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public DataStructureModel parse(DataStructureParametersModel parametersModel)
      throws DataStructuresException, IOException, ArtifactParserException {
    Pattern pattern = Pattern.compile("^(\\t\\n)*(\\s)*SEQUENCE", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(parametersModel.getContent().trim().toUpperCase(Locale.ROOT));
    boolean matchFound = matcher.find();
    return (matchFound)
        ? parseHanaXSAdvancedContent(parametersModel.getLocation(), parametersModel.getContent())
        : parseHanaXSClassicContent(parametersModel.getLocation(), parametersModel.getContent());
  }


  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDB_SEQUENCE;
  }


  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class<DataStructureHDBSequenceModel> getDataStructureClass() {
    return DataStructureHDBSequenceModel.class;
  }

  /**
   * Parses the hana XS classic content.
   *
   * @param location the location
   * @param content the content
   * @return the data structure model
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  private DataStructureModel parseHanaXSClassicContent(String location, String content)
      throws IOException, ArtifactParserException {
    logger.debug("Parsing hdbsequence as Hana XS Classic format");
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);

    HdbsequenceLexer lexer = new HdbsequenceLexer(inputStream);
    HDBSequenceSyntaxErrorListener lexerErrorListener = new HDBSequenceSyntaxErrorListener();
    lexer.removeErrorListeners();
    lexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HdbsequenceParser parser = new HdbsequenceParser((tokenStream));
    parser.setBuildParseTree(true);
    parser.removeErrorListeners();
    HDBSequenceSyntaxErrorListener parserErrorListener = new HDBSequenceSyntaxErrorListener();
    parser.removeErrorListeners();
    parser.addErrorListener(parserErrorListener);

    ParseTree parseTree = parser.hdbsequence();
    CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location, CommonsConstants.HDB_SEQUENCE_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location, CommonsConstants.HDB_SEQUENCE_PARSER);

    HdbsequenceBaseVisitor<JsonElement> visitor = new HdbsequenceVisitor();
    JsonElement parsedResult = visitor.visit(parseTree);
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(HDBSequenceModel.class, new HDBSequenceModelAdapter())
        .create();
    HDBSequenceModel antlr4Model = gson.fromJson(parsedResult, HDBSequenceModel.class);

    DataStructureHDBSequenceModel hdbSequenceModel = new DataStructureHDBSequenceModel(antlr4Model);

    HDBUtils.populateDataStructureModel(location, content, hdbSequenceModel, IDataStructureModel.TYPE_HDB_SEQUENCE,
        DBContentType.XS_CLASSIC);

    return hdbSequenceModel;
  }

  /**
   * Parses the hana XS advanced content.
   *
   * @param location the location
   * @param content the content
   * @return the data structure model
   */
  private DataStructureModel parseHanaXSAdvancedContent(String location, String content) {
    logger.debug("Parsing hdbsequence as Hana XS Advanced format");
    DataStructureHDBSequenceModel hdbSequenceModel = new DataStructureHDBSequenceModel();
    HDBUtils.populateDataStructureModel(location, content, hdbSequenceModel, IDataStructureModel.TYPE_HDB_SEQUENCE,
        DBContentType.OTHERS);
    hdbSequenceModel.setRawContent(content);
    return hdbSequenceModel;
  }
}
