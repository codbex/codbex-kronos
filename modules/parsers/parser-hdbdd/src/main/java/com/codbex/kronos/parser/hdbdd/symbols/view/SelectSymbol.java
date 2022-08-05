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

import java.util.ArrayList;
import java.util.List;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;

public class SelectSymbol extends Symbol implements Scope {

  List<Symbol> joinStatements = new ArrayList<>();

  Boolean isUnion = false;
  Boolean isDistinct = false;
  String columnsSql = null;
  String whereSql = null;
  String dependsOnTable = null;
  String dependingTableAlias = null;

  public List<Symbol> getJoinStatements() {
    return joinStatements;
  }

  public Boolean getUnion() {
    return isUnion;
  }

  public void setUnion(Boolean union) {
    isUnion = union;
  }

  public Boolean getDistinct() {
    return isDistinct;
  }

  public void setDistinct(Boolean distinct) {
    isDistinct = distinct;
  }

  public String getColumnsSql() {
    return columnsSql;
  }

  public void setColumnsSql(String columnsSql) {
    this.columnsSql = columnsSql;
  }

  public String getWhereSql() {
    return whereSql;
  }

  public void setWhereSql(String whereSql) {
    this.whereSql = whereSql;
  }

  public String getDependsOnTable() {
    return dependsOnTable;
  }

  public void setDependsOnTable(String dependsOnTable) {
    this.dependsOnTable = dependsOnTable;
  }

  public String getDependingTableAlias() {
    return dependingTableAlias;
  }

  public void setDependingTableAlias(String dependingTableAlias) {
    this.dependingTableAlias = dependingTableAlias;
  }

  @Override
  public Scope getEnclosingScope() {
    return this.getScope();
  }

  @Override
  public void define(Symbol sym) {
    joinStatements.add(sym);
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
