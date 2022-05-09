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
package com.codbex.kronos.parser.hdbdd.symbols.context;

import java.util.LinkedHashMap;
import java.util.Map;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;

public class ContextSymbol extends Symbol implements Scope {

  private Map<String, Symbol> symbols = new LinkedHashMap<>();

  public ContextSymbol(Symbol symbol) {
    super(symbol);
  }

  public ContextSymbol(String name, Scope scope) {
    super(name, scope);
  }

  @Override
  public Scope getEnclosingScope() {
    return this.getScope();
  }

  @Override
  public void define(Symbol sym) {
    this.symbols.put(sym.getName(), sym);
    sym.setScope(this);
  }

  @Override
  public Symbol resolve(String name) {
    Symbol symbol = symbols.get(name);
    if (symbol != null) {
      return symbol;
    }

    // if not here, check any enclosing com.codbex.kronos.parser.hdbdd.symbols.scope
    if (getEnclosingScope() != null) {
      return getEnclosingScope().resolve(name);
    }

    return null; // not found
  }

  public Map<String, Symbol> getSymbols() {
    return symbols;
  }

  @Override
  public boolean isDuplicateName(String id) {
    return symbols.containsKey(id) || getName().equals(id);
  }
}
