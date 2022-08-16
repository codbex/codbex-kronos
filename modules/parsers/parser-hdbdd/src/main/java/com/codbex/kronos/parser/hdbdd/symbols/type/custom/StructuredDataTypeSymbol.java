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
package com.codbex.kronos.parser.hdbdd.symbols.type.custom;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import com.codbex.kronos.parser.hdbdd.symbols.type.Type;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.FieldSymbol;

/**
 * The Class StructuredDataTypeSymbol.
 */
public class StructuredDataTypeSymbol extends Symbol implements Type, Scope, CustomDataType {

  /** The fields. */
  private Map<String, Symbol> fields = new LinkedHashMap<>();

  /**
   * Instantiates a new structured data type symbol.
   *
   * @param symbol the symbol
   */
  public StructuredDataTypeSymbol(Symbol symbol) {
    super(symbol);
  }

  /**
   * Instantiates a new structured data type symbol.
   *
   * @param name the name
   * @param scope the scope
   */
  public StructuredDataTypeSymbol(String name, Scope scope) {
    super(name, scope);
  }

  /**
   * Gets the enclosing scope.
   *
   * @return the enclosing scope
   */
  @Override
  public Scope getEnclosingScope() {
    return this.getScope();
  }

  /**
   * Define.
   *
   * @param sym the sym
   */
  @Override
  public void define(Symbol sym) {
    fields.put(sym.getName(), sym);
    sym.setScope(this);
  }

  /**
   * Resolve.
   *
   * @param name the name
   * @return the symbol
   */
  @Override
  public Symbol resolve(String name) {
    return fields.get(name);
  }

  /**
   * Checks if is duplicate name.
   *
   * @param id the id
   * @return true, if is duplicate name
   */
  @Override
  public boolean isDuplicateName(String id) {
    return fields.containsKey(id) || getName().equals(id);
  }

  /**
   * Gets the fields.
   *
   * @return the fields
   */
  public List<FieldSymbol> getFields() {
    return fields.values().stream().map(f -> (FieldSymbol) f).collect(Collectors.toList());
  }

}
