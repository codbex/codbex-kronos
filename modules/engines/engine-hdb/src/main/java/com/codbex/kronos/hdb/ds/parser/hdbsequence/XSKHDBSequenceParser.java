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

import com.codbex.kronos.exceptions.XSKArtifactParserException;
import com.codbex.kronos.hdb.ds.api.IXSKDataStructureModel;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdb.ds.model.XSKDBContentType;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureModel;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbsequence.XSKDataStructureHDBSequenceModel;
import com.codbex.kronos.hdb.ds.parser.XSKDataStructureParser;
import com.codbex.kronos.parser.hdbsequence.custom.HdbsequenceVisitor;
import com.codbex.kronos.parser.hdbsequence.custom.XSKHDBSEQUENCEModelAdapter;
import com.codbex.kronos.parser.hdbsequence.custom.XSKHDBSEQUENCESyntaxErrorListener;
import com.codbex.kronos.parser.hdbsequence.models.XSKHDBSEQUENCEModel;
import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;
import com.codbex.kronos.utils.XSKHDBUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceBaseVisitor;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceLexer;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceParser;

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

public class XSKHDBSequenceParser implements XSKDataStructureParser {


  private static final Logger logger = LoggerFactory.getLogger(XSKHDBSequenceParser.class);

  @Override
  public XSKDataStructureModel parse(XSKDataStructureParametersModel parametersModel)
      throws XSKDataStructuresException, IOException, XSKArtifactParserException {
    Pattern pattern = Pattern.compile("^(\\t\\n)*(\\s)*SEQUENCE", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(parametersModel.getContent().trim().toUpperCase(Locale.ROOT));
    boolean matchFound = matcher.find();
    return (matchFound)
        ? parseHanaXSAdvancedContent(parametersModel.getLocation(), parametersModel.getContent())
        : parseHanaXSClassicContent(parametersModel.getLocation(), parametersModel.getContent());
  }


  @Override
  public String getType() {
    return IXSKDataStructureModel.TYPE_HDB_SEQUENCE;
  }


  @Override
  public Class<XSKDataStructureHDBSequenceModel> getDataStructureClass() {
    return XSKDataStructureHDBSequenceModel.class;
  }

  private XSKDataStructureModel parseHanaXSClassicContent(String location, String content)
      throws IOException, XSKArtifactParserException {
    logger.debug("Parsing hdbsequence as Hana XS Classic format");
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);

    HdbsequenceLexer lexer = new HdbsequenceLexer(inputStream);
    XSKHDBSEQUENCESyntaxErrorListener lexerErrorListener = new XSKHDBSEQUENCESyntaxErrorListener();
    lexer.removeErrorListeners();
    lexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HdbsequenceParser parser = new HdbsequenceParser((tokenStream));
    parser.setBuildParseTree(true);
    parser.removeErrorListeners();
    XSKHDBSEQUENCESyntaxErrorListener parserErrorListener = new XSKHDBSEQUENCESyntaxErrorListener();
    parser.removeErrorListeners();
    parser.addErrorListener(parserErrorListener);

    ParseTree parseTree = parser.hdbsequence();
    XSKCommonsUtils.logParserErrors(parserErrorListener.getErrors(), XSKCommonsConstants.PARSER_ERROR, location, XSKCommonsConstants.HDB_SEQUENCE_PARSER);
    XSKCommonsUtils.logParserErrors(lexerErrorListener.getErrors(), XSKCommonsConstants.LEXER_ERROR, location, XSKCommonsConstants.HDB_SEQUENCE_PARSER);

    HdbsequenceBaseVisitor<JsonElement> visitor = new HdbsequenceVisitor();
    JsonElement parsedResult = visitor.visit(parseTree);
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(XSKHDBSEQUENCEModel.class, new XSKHDBSEQUENCEModelAdapter())
        .create();
    XSKHDBSEQUENCEModel antlr4Model = gson.fromJson(parsedResult, XSKHDBSEQUENCEModel.class);

    XSKDataStructureHDBSequenceModel hdbSequenceModel = new XSKDataStructureHDBSequenceModel(antlr4Model);

    XSKHDBUtils.populateXSKDataStructureModel(location, content, hdbSequenceModel, IXSKDataStructureModel.TYPE_HDB_SEQUENCE,
        XSKDBContentType.XS_CLASSIC);

    return hdbSequenceModel;
  }

  private XSKDataStructureModel parseHanaXSAdvancedContent(String location, String content) {
    logger.debug("Parsing hdbsequence as Hana XS Advanced format");
    XSKDataStructureHDBSequenceModel hdbSequenceModel = new XSKDataStructureHDBSequenceModel();
    XSKHDBUtils.populateXSKDataStructureModel(location, content, hdbSequenceModel, IXSKDataStructureModel.TYPE_HDB_SEQUENCE,
        XSKDBContentType.OTHERS);
    hdbSequenceModel.setRawContent(content);
    return hdbSequenceModel;
  }
}
