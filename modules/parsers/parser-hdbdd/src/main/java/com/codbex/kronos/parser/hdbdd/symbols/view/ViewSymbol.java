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
package com.codbex.kronos.parser.hdbdd.symbols.view;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import java.util.ArrayList;
import java.util.List;

public class ViewSymbol extends Symbol implements Scope {

  List<Symbol> selectStatements = new ArrayList<>();
  String packageId;
  String context;

  public ViewSymbol(Symbol symbol) {
    super(symbol);
  }

  public List<Symbol> getSelectStatements() {
    return selectStatements;
  }

  public String getPackageId() {
    return packageId;
  }

  public void setPackageId(String packageId) {
    this.packageId = packageId;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  @Override
  public Scope getEnclosingScope() {
    return this.getScope();
  }

  @Override
  public void define(Symbol sym) {
      selectStatements.add(sym);
  }

  @Override
  public Symbol resolve(String name) {
    return null;
  }

  @Override
  public boolean isDuplicateName(String id) {
    return false;
  }
}
