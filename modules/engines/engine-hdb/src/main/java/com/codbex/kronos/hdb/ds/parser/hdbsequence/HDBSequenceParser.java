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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbsequence.HDBSequenceDataStructureModel;
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

public class HDBSequenceParser implements DataStructureParser {

  private static final Logger logger = LoggerFactory.getLogger(HDBSequenceParser.class);

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


  @Override
  public String getType() {
    return HDBDataStructureModel.TYPE_HDB_SEQUENCE;
  }


  @Override
  public Class<HDBSequenceDataStructureModel> getDataStructureClass() {
    return HDBSequenceDataStructureModel.class;
  }

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

    HDBSequenceDataStructureModel hdbSequenceModel = new HDBSequenceDataStructureModel(antlr4Model);

    HDBUtils.populateDataStructureModel(location, content, hdbSequenceModel, HDBDataStructureModel.TYPE_HDB_SEQUENCE,
        DBContentType.XS_CLASSIC);

    return hdbSequenceModel;
  }

  private DataStructureModel parseHanaXSAdvancedContent(String location, String content) {
    logger.debug("Parsing hdbsequence as Hana XS Advanced format");
    HDBSequenceDataStructureModel hdbSequenceModel = new HDBSequenceDataStructureModel();
    HDBUtils.populateDataStructureModel(location, content, hdbSequenceModel, HDBDataStructureModel.TYPE_HDB_SEQUENCE,
        DBContentType.OTHERS);
    hdbSequenceModel.setRawContent(content);
    return hdbSequenceModel;
  }
}
