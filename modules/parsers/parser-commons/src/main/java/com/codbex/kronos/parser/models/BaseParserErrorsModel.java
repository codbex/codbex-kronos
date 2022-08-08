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

/**
 * The Class BaseParserErrorsModel.
 */
public class BaseParserErrorsModel {

  /** The line. */
  private int line;
  
  /** The char position in line. */
  private int charPositionInLine;
  
  /** The offending symbol. */
  private String offendingSymbol;
  
  /** The msg. */
  private String msg;

  /**
   * Instantiates a new base parser errors model.
   *
   * @param line the line
   * @param charPositionInLine the char position in line
   * @param offendingSymbol the offending symbol
   * @param msg the msg
   */
  public BaseParserErrorsModel(int line, int charPositionInLine, String offendingSymbol, String msg) {
    this.line = line;
    this.charPositionInLine = charPositionInLine;
    this.offendingSymbol = offendingSymbol;
    this.msg = msg;
  }

  /**
   * Gets the line.
   *
   * @return the line
   */
  public int getLine() {
    return line;
  }

  /**
   * Sets the line.
   *
   * @param line the new line
   */
  public void setLine(int line) {
    this.line = line;
  }

  /**
   * Gets the char position in line.
   *
   * @return the char position in line
   */
  public int getCharPositionInLine() {
    return charPositionInLine;
  }

  /**
   * Gets the offending symbol.
   *
   * @return the offending symbol
   */
  public String getOffendingSymbol() {
    return offendingSymbol;
  }

  /**
   * Gets the msg.
   *
   * @return the msg
   */
  public String getMsg() {
    return msg;
  }

  /**
   * Sets the msg.
   *
   * @param msg the new msg
   */
  public void setMsg(String msg) {
    this.msg = msg;
  }
}
