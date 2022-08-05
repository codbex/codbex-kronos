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
package com.codbex.kronos.parser.hdbti.custom;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hdbti.core.HdbtiLexer;
import com.codbex.kronos.parser.hdbti.core.HdbtiParser;
import com.codbex.kronos.parser.hdbti.exception.HDBTIMissingPropertyException;
import com.codbex.kronos.parser.hdbti.exception.HDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDBTIParser implements IHDBTIParser {

  private static final Logger logger = LoggerFactory.getLogger(HDBTIParser.class);

  public HDBTIImportModel parse(String location, String content)
      throws IOException, HDBTISyntaxErrorException, ArtifactParserException {
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HdbtiLexer hdbtiLexer = new HdbtiLexer(inputStream);
    HDBTISyntaxErrorListener lexerErrorListener = new HDBTISyntaxErrorListener();
    hdbtiLexer.removeErrorListeners();
    hdbtiLexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbtiLexer);

    HdbtiParser hdbtiParser = new HdbtiParser(tokenStream);
    hdbtiParser.setBuildParseTree(true);
    HDBTISyntaxErrorListener parseErrorListener = new HDBTISyntaxErrorListener();
    hdbtiParser.removeErrorListeners();
    hdbtiParser.addErrorListener(parseErrorListener);

    ParseTree parseTree = hdbtiParser.importArr();
    CommonsUtils.logParserErrors(parseErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location, CommonsConstants.HDBTI_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location, CommonsConstants.HDBTI_PARSER);

    HDBTICoreListener hdbtiCoreListener = new HDBTICoreListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbtiCoreListener, parseTree);

    HDBTIImportModel importModel = hdbtiCoreListener.getImportModel();
    try {
      importModel.checkMandatoryFieldsInAllConfigModels();
    } catch (Exception e) {
      CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
          CommonsConstants.EXPECTED_FIELDS, CommonsConstants.HDBTI_PARSER,CommonsConstants.MODULE_PARSERS,
          CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
      throw new HDBTIMissingPropertyException(String.format("Wrong format of hdbti definition: [%s]. [%s]", location, e.getMessage()));
    }

    return importModel;
  }
}
