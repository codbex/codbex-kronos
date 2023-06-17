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
package com.codbex.kronos.parser.hdbdd.symbols.annotation;

import java.util.HashMap;
import java.util.Map;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;

/**
 * The Class AnnotationSymbol.
 */
public class AnnotationSymbol extends Symbol implements Scope {
    
    /** The elements. */
    private Map<String, Symbol> elements = new HashMap<>();

    /**
     * Instantiates a new annotation symbol.
     *
     * @param name the name
     */
    public AnnotationSymbol(String name) {
        super(name);
    }

    /**
     * Instantiates a new annotation symbol.
     *
     * @param name the name
     * @param scope the scope
     */
    public AnnotationSymbol(String name, Scope scope) {
        super(name, scope);
    }

    /**
     * Gets the enclosing scope.
     *
     * @return the enclosing scope
     */
    @Override
    public Scope getEnclosingScope() {
        return null;
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
        return false;
    }
}
