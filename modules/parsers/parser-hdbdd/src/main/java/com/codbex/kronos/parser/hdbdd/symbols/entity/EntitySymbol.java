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
package com.codbex.kronos.parser.hdbdd.symbols.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;

/**
 * The Class EntitySymbol.
 */
public class EntitySymbol extends Symbol implements Scope {
    
    /** The elements. */
    private Map<String, Symbol> elements;

  /**
   * Instantiates a new entity symbol.
   *
   * @param symbol the symbol
   */
  public EntitySymbol(Symbol symbol) {
    super(symbol);
    elements = new LinkedHashMap<>();
  }

  /**
   * Instantiates a new entity symbol.
   *
   * @param name the name
   * @param scope the scope
   */
  public EntitySymbol(String name, Scope scope) {
        super(name, scope);
        elements = new LinkedHashMap<>();
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
        elements.put(sym.getName(), sym);
    }

    /**
     * Resolve.
     *
     * @param name the name
     * @return the symbol
     */
    @Override
    public Symbol resolve(String name) {
        return elements.get(name);
    }

    /**
     * Checks if is duplicate name.
     *
     * @param id the id
     * @return true, if is duplicate name
     */
    @Override
    public boolean isDuplicateName(String id) {
        return elements.containsKey(id);
    }

    /**
     * Gets the keys.
     *
     * @return the keys
     */
    public List<EntityElementSymbol> getKeys() {
        return elements.values().stream()
                .filter(e -> e instanceof EntityElementSymbol)
                .map(e -> (EntityElementSymbol) e)
                .filter(EntityElementSymbol::isKey)
                .collect(Collectors.toList());
    }

  /**
   * Gets the elements.
   *
   * @return the elements
   */
  public List<EntityElementSymbol> getElements() {
    return elements.values().stream()
        .filter(e -> e instanceof EntityElementSymbol)
        .map(e -> (EntityElementSymbol) e)
        .collect(Collectors.toList());
  }

  /**
   * Gets the associations.
   *
   * @return the associations
   */
  public List<AssociationSymbol> getAssociations() {
    return elements.values().stream()
        .filter(e -> e instanceof AssociationSymbol)
        .map(e -> (AssociationSymbol) e)
        .collect(Collectors.toList());
  }
}
