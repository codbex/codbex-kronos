/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
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
 * The Class SelectSymbol.
 */
public class SelectSymbol extends Symbol implements Scope {

    /** The join statements. */
    List<Symbol> joinStatements = new ArrayList<>();

    /** The is union. */
    Boolean isUnion = false;

    /** The is distinct. */
    Boolean isDistinct = false;

    /** The columns sql. */
    String columnsSql = null;

    /** The where sql. */
    String whereSql = null;

    /** The depends on table. */
    String dependsOnTable = null;

    /** The depending table alias. */
    String dependingTableAlias = null;

    /**
     * Gets the join statements.
     *
     * @return the join statements
     */
    public List<Symbol> getJoinStatements() {
        return joinStatements;
    }

    /**
     * Gets the union.
     *
     * @return the union
     */
    public Boolean getUnion() {
        return isUnion;
    }

    /**
     * Sets the union.
     *
     * @param union the new union
     */
    public void setUnion(Boolean union) {
        isUnion = union;
    }

    /**
     * Gets the distinct.
     *
     * @return the distinct
     */
    public Boolean getDistinct() {
        return isDistinct;
    }

    /**
     * Sets the distinct.
     *
     * @param distinct the new distinct
     */
    public void setDistinct(Boolean distinct) {
        isDistinct = distinct;
    }

    /**
     * Gets the columns sql.
     *
     * @return the columns sql
     */
    public String getColumnsSql() {
        return columnsSql;
    }

    /**
     * Sets the columns sql.
     *
     * @param columnsSql the new columns sql
     */
    public void setColumnsSql(String columnsSql) {
        this.columnsSql = columnsSql;
    }

    /**
     * Gets the where sql.
     *
     * @return the where sql
     */
    public String getWhereSql() {
        return whereSql;
    }

    /**
     * Sets the where sql.
     *
     * @param whereSql the new where sql
     */
    public void setWhereSql(String whereSql) {
        this.whereSql = whereSql;
    }

    /**
     * Gets the depends on table.
     *
     * @return the depends on table
     */
    public String getDependsOnTable() {
        return dependsOnTable;
    }

    /**
     * Sets the depends on table.
     *
     * @param dependsOnTable the new depends on table
     */
    public void setDependsOnTable(String dependsOnTable) {
        this.dependsOnTable = dependsOnTable;
    }

    /**
     * Gets the depending table alias.
     *
     * @return the depending table alias
     */
    public String getDependingTableAlias() {
        return dependingTableAlias;
    }

    /**
     * Sets the depending table alias.
     *
     * @param dependingTableAlias the new depending table alias
     */
    public void setDependingTableAlias(String dependingTableAlias) {
        this.dependingTableAlias = dependingTableAlias;
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
        joinStatements.add(sym);
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
