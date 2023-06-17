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
package com.codbex.kronos.parser.hdbdd.symbols.type;

import java.util.ArrayList;
import java.util.List;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;

/**
 * The Class BuiltInTypeSymbol.
 */
public class BuiltInTypeSymbol extends Symbol implements Type {

  /** The args count. */
  private int argsCount;
  
  /** The value type. */
  private List<Integer> valueType;
  
  /** The is hana type. */
  private boolean isHanaType;
  
  /** The args values. */
  private List<Integer> argsValues;

  /**
   * Instantiates a new built in type symbol.
   *
   * @param name the name
   * @param argsCount the args count
   * @param valueType the value type
   */
  public BuiltInTypeSymbol(String name, int argsCount, List<Integer> valueType) {
    this(name, valueType);
    this.argsCount = argsCount;
  }

  /**
   * Instantiates a new built in type symbol.
   *
   * @param name the name
   * @param argsCount the args count
   * @param valueType the value type
   * @param isHanaType the is hana type
   */
  public BuiltInTypeSymbol(String name, int argsCount, List<Integer> valueType, boolean isHanaType) {
    this(name, argsCount, valueType);
    this.isHanaType = isHanaType;
  }

  /**
   * Instantiates a new built in type symbol.
   *
   * @param name the name
   * @param valueType the value type
   */
  public BuiltInTypeSymbol(String name, List<Integer> valueType) {
    super(name);
    this.argsValues = new ArrayList<>();
    this.valueType = valueType;
  }

  /**
   * Adds the arg value.
   *
   * @param value the value
   */
  public void addArgValue(int value) {
    this.argsValues.add(value);
  }

  /**
   * Gets the args count.
   *
   * @return the args count
   */
  public int getArgsCount() {
    return argsCount;
  }

  /**
   * Gets the value type.
   *
   * @return the value type
   */
  public List<Integer> getValueType() {
    return valueType;
  }

  /**
   * Checks if is hana type.
   *
   * @return true, if is hana type
   */
  public boolean isHanaType() {
    return isHanaType;
  }

  /**
   * Sets the hana type.
   *
   * @param hanaType the new hana type
   */
  public void setHanaType(boolean hanaType) {
    isHanaType = hanaType;
  }

  /**
   * Gets the args values.
   *
   * @return the args values
   */
  public List<Integer> getArgsValues() {
    return argsValues;
  }
}
