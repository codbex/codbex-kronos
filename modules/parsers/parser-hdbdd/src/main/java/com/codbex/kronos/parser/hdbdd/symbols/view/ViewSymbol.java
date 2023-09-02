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
package com.codbex.kronos.parser.hdbdd.symbols.view;

import java.util.ArrayList;
import java.util.List;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;

/**
 * The Class ViewSymbol.
 */
public class ViewSymbol extends Symbol implements Scope {

  /** The select statements. */
  List<Symbol> selectStatements = new ArrayList<>();
  
  /** The package id. */
  String packageId;
  
  /** The context. */
  String context;

  /**
   * Instantiates a new view symbol.
   *
   * @param symbol the symbol
   */
  public ViewSymbol(Symbol symbol) {
    super(symbol);
  }

  /**
   * Gets the select statements.
   *
   * @return the select statements
   */
  public List<Symbol> getSelectStatements() {
    return selectStatements;
  }

  /**
   * Gets the package id.
   *
   * @return the package id
   */
  public String getPackageId() {
    return packageId;
  }

  /**
   * Sets the package id.
   *
   * @param packageId the new package id
   */
  public void setPackageId(String packageId) {
    this.packageId = packageId;
  }

  /**
   * Gets the context.
   *
   * @return the context
   */
  public String getContext() {
    return context;
  }

  /**
   * Sets the context.
   *
   * @param context the new context
   */
  public void setContext(String context) {
    this.context = context;
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
      selectStatements.add(sym);
  }

  /**
   * Resolve.
   *
   * @param name the name
   * @return the symbol
   */
  @Override
  public Symbol resolve(String name) {
    return null;
  }

  /**
   * Checks if is duplicate name.
   *
   * @param id the id
   * @return true, if is duplicate name
   */
  @Override
  public boolean isDuplicateName(String id) {
    return false;
  }
}
