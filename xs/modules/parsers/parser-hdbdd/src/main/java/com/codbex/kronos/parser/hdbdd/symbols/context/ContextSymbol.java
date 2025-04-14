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

import org.jetbrains.annotations.Nullable;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Class ContextSymbol.
 */
public class ContextSymbol extends Symbol implements Scope {

    /** The symbols. */
    private Map<String, Symbol> symbols = new LinkedHashMap<>();

    /**
     * Instantiates a new context symbol.
     *
     * @param symbol the symbol
     */
    public ContextSymbol(Symbol symbol) {
        super(symbol);
    }

    /**
     * Instantiates a new context symbol.
     *
     * @param name the name
     * @param scope the scope
     */
    public ContextSymbol(String name, Scope scope) {
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
        this.symbols.put(sym.getName(), sym);
        sym.setScope(this);
    }

    /**
     * Resolve.
     *
     * @param name the name
     * @return the symbol
     */
    @Override
    @Nullable
    public Symbol resolve(String name) {
        if (name.contains(".")) {
            String[] identifiers = name.split("\\.");
            Symbol outerContext = findSymbol(identifiers[0]);

            return resolveByFullSymbolName(identifiers, outerContext);
        }

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

    /**
     * Gets the symbols.
     *
     * @return the symbols
     */
    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    /**
     * Checks if is duplicate name.
     *
     * @param id the id
     * @return true, if is duplicate name
     */
    @Override
    public boolean isDuplicateName(String id) {
        return symbols.containsKey(id) || getName().equals(id);
    }

    /**
     * Find symbol.
     *
     * @param name the name
     * @return the symbol
     */
    private Symbol findSymbol(String name) {
        Scope currentScope = this;
        Symbol resolvedSymbol = this.getSymbols()
                                    .get(name);
        while (resolvedSymbol == null) {
            if (currentScope instanceof CDSFileScope) {
                return null;
            }

            currentScope = currentScope.getEnclosingScope();
            resolvedSymbol = currentScope.resolve(name);
        }

        return resolvedSymbol;
    }

    /**
     * Resolve by full symbol name.
     *
     * @param contexts the contexts
     * @param outerContext the outer context
     * @return the symbol
     */
    private Symbol resolveByFullSymbolName(String[] contexts, Symbol outerContext) {
        if (!(outerContext instanceof ContextSymbol)) {
            return null;
        }

        Symbol resolvedSymbol = outerContext;

        for (int i = 1; i < contexts.length; i++) {
            resolvedSymbol = ((ContextSymbol) resolvedSymbol).getSymbols()
                                                             .get(contexts[i]);
            if (resolvedSymbol == null) {
                return null;
            }
        }

        return resolvedSymbol;

    }
}
