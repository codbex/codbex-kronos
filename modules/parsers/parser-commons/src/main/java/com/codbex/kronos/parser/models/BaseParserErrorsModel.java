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
package com.codbex.kronos.parser.models;

public class BaseParserErrorsModel {

  private int line;
  private int charPositionInLine;
  private String offendingSymbol;
  private String msg;

  public BaseParserErrorsModel(int line, int charPositionInLine, String offendingSymbol, String msg) {
    this.line = line;
    this.charPositionInLine = charPositionInLine;
    this.offendingSymbol = offendingSymbol;
    this.msg = msg;
  }

  public int getLine() {
    return line;
  }

  public void setLine(int line) {
    this.line = line;
  }

  public int getCharPositionInLine() {
    return charPositionInLine;
  }

  public String getOffendingSymbol() {
    return offendingSymbol;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
