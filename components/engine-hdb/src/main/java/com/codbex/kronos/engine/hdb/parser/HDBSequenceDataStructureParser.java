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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.api.IDataStructureModel;
import com.codbex.kronos.engine.hdb.domain.HDBSequence;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hdbsequence.core.HDBSequenceBaseVisitor;
import com.codbex.kronos.parser.hdbsequence.core.HDBSequenceLexer;
import com.codbex.kronos.parser.hdbsequence.custom.HDBSequenceDefinitionVisitor;
import com.codbex.kronos.parser.hdbsequence.custom.HDBSequenceModelAdapter;
import com.codbex.kronos.parser.hdbsequence.custom.HDBSequenceSyntaxErrorListener;
import com.codbex.kronos.parser.hdbsequence.models.HDBSequenceModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * The Class HDBSequenceParser.
 */
@Component
public class HDBSequenceDataStructureParser implements HDBDataStructureParser<HDBSequence> {


  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBSequenceDataStructureParser.class);

  /**
   * Parses the hdbsequence file.
   *
   * @param parametersModel the parameters model
   * @return the data structure model
   * @throws DataStructuresException the data structures exception
   * @throws IOException             Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public HDBSequence parse(HDBParameters parametersModel)
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
  public Class<HDBSequence> getDataStructureClass() {
    return HDBSequence.class;
  }

  /**
   * Parses the hana XS classic content.
   *
   * @param location the location
   * @param content  the content
   * @return the data structure model
   * @throws IOException             Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  private HDBSequence parseHanaXSClassicContent(String location, String content)
      throws IOException, ArtifactParserException {
    logger.debug("Parsing HDB Sequence as Hana XS Classic format");
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);

    HDBSequenceLexer hdbSequenceLexer = new HDBSequenceLexer(inputStream);
    HDBSequenceSyntaxErrorListener lexerErrorListener = new HDBSequenceSyntaxErrorListener();
    hdbSequenceLexer.removeErrorListeners();
    hdbSequenceLexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbSequenceLexer);

    com.codbex.kronos.parser.hdbsequence.core.HDBSequenceParser hdbSequenceParser = new com.codbex.kronos.parser.hdbsequence.core.HDBSequenceParser((tokenStream));
    hdbSequenceParser.setBuildParseTree(true);
    hdbSequenceParser.removeErrorListeners();
    HDBSequenceSyntaxErrorListener parserErrorListener = new HDBSequenceSyntaxErrorListener();
    hdbSequenceParser.removeErrorListeners();
    hdbSequenceParser.addErrorListener(parserErrorListener);

    ParseTree parseTree = hdbSequenceParser.hdbSequenceDefinition();
    CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location,
        CommonsConstants.HDB_SEQUENCE_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location,
        CommonsConstants.HDB_SEQUENCE_PARSER);

    HDBSequenceBaseVisitor<JsonElement> visitor = new HDBSequenceDefinitionVisitor();
    JsonElement parsedResult = visitor.visit(parseTree);
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(HDBSequenceModel.class, new HDBSequenceModelAdapter())
        .create();
    HDBSequenceModel antlr4Model = gson.fromJson(parsedResult, HDBSequenceModel.class);

    HDBSequence hdbSequenceModel = new HDBSequence(antlr4Model);

    HDBUtils.populateDataStructureModel(location, content, hdbSequenceModel, IDataStructureModel.TYPE_HDB_SEQUENCE,
        true);

    return hdbSequenceModel;
  }

  /**
   * Parses the hana XS advanced content.
   *
   * @param location the location
   * @param content  the content
   * @return the data structure model
   */
  private HDBSequence parseHanaXSAdvancedContent(String location, String content) {
    logger.debug("Parsing HDB Sequence as Hana XS Advanced format");
    HDBSequence hdbSequenceModel = new HDBSequence();
    HDBUtils.populateDataStructureModel(location, content, hdbSequenceModel, IDataStructureModel.TYPE_HDB_SEQUENCE, false);
    hdbSequenceModel.setContent(content);
    return hdbSequenceModel;
  }
}
