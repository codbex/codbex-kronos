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
package com.codbex.kronos.parser.hdbtable.custom;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import com.codbex.kronos.parser.models.BaseParserErrorsModel;

import java.util.ArrayList;

public class XSKHDBTABLESyntaxErrorListener extends BaseErrorListener {

  private final ArrayList<BaseParserErrorsModel> errors = new ArrayList<>();

  @Override
  public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg,
                          RecognitionException e) {

    this.errors.add(new BaseParserErrorsModel(line, charPositionInLine, offendingSymbol == null? "" : offendingSymbol.toString(), msg));
  }

  public ArrayList<BaseParserErrorsModel> getErrors() {
    return errors;
  }
}
