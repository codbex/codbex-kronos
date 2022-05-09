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

import com.codbex.kronos.exceptions.XSKArtifactParserException;
import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;
import com.codbex.kronos.parser.hdbti.core.HdbtiLexer;
import com.codbex.kronos.parser.hdbti.core.HdbtiParser;
import com.codbex.kronos.parser.hdbti.exception.XSKHDBTIMissingPropertyException;
import com.codbex.kronos.parser.hdbti.exception.XSKHDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.models.XSKHDBTIImportModel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XSKHDBTIParser implements IXSKHDBTIParser {

  private static final Logger logger = LoggerFactory.getLogger(XSKHDBTIParser.class);

  public XSKHDBTIImportModel parse(String location, String content)
      throws IOException, XSKHDBTISyntaxErrorException, XSKArtifactParserException {
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HdbtiLexer hdbtiLexer = new HdbtiLexer(inputStream);
    XSKHDBTISyntaxErrorListener lexerErrorListener = new XSKHDBTISyntaxErrorListener();
    hdbtiLexer.removeErrorListeners();
    hdbtiLexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbtiLexer);

    HdbtiParser hdbtiParser = new HdbtiParser(tokenStream);
    hdbtiParser.setBuildParseTree(true);
    XSKHDBTISyntaxErrorListener parseErrorListener = new XSKHDBTISyntaxErrorListener();
    hdbtiParser.removeErrorListeners();
    hdbtiParser.addErrorListener(parseErrorListener);

    ParseTree parseTree = hdbtiParser.importArr();
    XSKCommonsUtils.logParserErrors(parseErrorListener.getErrors(), XSKCommonsConstants.PARSER_ERROR, location, XSKCommonsConstants.HDBTI_PARSER);
    XSKCommonsUtils.logParserErrors(lexerErrorListener.getErrors(), XSKCommonsConstants.LEXER_ERROR, location, XSKCommonsConstants.HDBTI_PARSER);

    XSKHDBTICoreListener XSKHDBTICoreListener = new XSKHDBTICoreListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(XSKHDBTICoreListener, parseTree);

    XSKHDBTIImportModel importModel = XSKHDBTICoreListener.getImportModel();
    try {
      importModel.checkMandatoryFieldsInAllConfigModels();
    } catch (Exception e) {
      XSKCommonsUtils.logCustomErrors(location, XSKCommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
          XSKCommonsConstants.EXPECTED_FIELDS, XSKCommonsConstants.HDBTI_PARSER,XSKCommonsConstants.MODULE_PARSERS,
          XSKCommonsConstants.SOURCE_PUBLISH_REQUEST, XSKCommonsConstants.PROGRAM_XSK);
      throw new XSKHDBTIMissingPropertyException(String.format("Wrong format of hdbti definition: [%s]. [%s]", location, e.getMessage()));
    }

    return importModel;
  }
}
