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
package com.codbex.kronos.parser.hdbdd.symbols.context;

import java.util.LinkedHashMap;
import java.util.Map;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;

/**
 * The Class CDSFileScope.
 */
public class CDSFileScope implements Scope {

    /** The enclosing scope. */
    Scope enclosingScope; // null if global (outermost) com.codbex.kronos.parser.hdbdd.symbols.scope

    /** The symbols. */
    Map<String, Symbol> symbols = new LinkedHashMap<>();

    /**
     * Instantiates a new CDS file scope.
     */
    public CDSFileScope() {}

    /**
     * Resolve.
     *
     * @param name the name
     * @return the symbol
     */
    public Symbol resolve(String name) {
        return symbols.get(name);
    }

    /**
     * Checks if is duplicate name.
     *
     * @param id the id
     * @return true, if is duplicate name
     */
    @Override
    public boolean isDuplicateName(String id) {
        return symbols.containsKey(id);
    }

    /**
     * Define.
     *
     * @param sym the sym
     */
    public void define(Symbol sym) {
        symbols.put(sym.getName(), sym);
        sym.setScope(this); // track the com.codbex.kronos.parser.hdbdd.symbols.scope in each symbol
    }

    /**
     * Define with custom name.
     *
     * @param customName the custom name
     * @param symbol the symbol
     */
    public void defineWithCustomName(String customName, Symbol symbol) {
        symbols.put(customName, symbol);
    }

    /**
     * Gets the enclosing scope.
     *
     * @return the enclosing scope
     */
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    /**
     * To string.
     *
     * @return the string
     */
    public String toString() {
        return symbols.keySet()
                      .toString();
    }
}
