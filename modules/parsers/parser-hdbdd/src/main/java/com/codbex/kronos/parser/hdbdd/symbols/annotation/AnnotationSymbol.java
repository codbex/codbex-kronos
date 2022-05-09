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
package com.codbex.kronos.parser.hdbdd.symbols.annotation;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;

import java.util.HashMap;
import java.util.Map;

public class AnnotationSymbol extends Symbol implements Scope {
    private Map<String, Symbol> elements = new HashMap<>();

    public AnnotationSymbol(String name) {
        super(name);
    }

    public AnnotationSymbol(String name, Scope scope) {
        super(name, scope);
    }

    @Override
    public Scope getEnclosingScope() {
        return null;
    }

    @Override
    public void define(Symbol sym) {
        elements.put(sym.getName(), sym);
    }

    @Override
    public Symbol resolve(String name) {
        return elements.get(name);
    }

    @Override
    public boolean isDuplicateName(String id) {
        return false;
    }
}
