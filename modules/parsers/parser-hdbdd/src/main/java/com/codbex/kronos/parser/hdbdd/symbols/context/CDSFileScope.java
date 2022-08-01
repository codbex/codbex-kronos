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

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import java.util.LinkedHashMap;
import java.util.Map;

public class CDSFileScope implements Scope {
    Scope enclosingScope; // null if global (outermost) com.codbex.kronos.parser.hdbdd.symbols.scope
    Map<String, Symbol> symbols = new LinkedHashMap<>();

    public CDSFileScope() {
    }

    public Symbol resolve(String name) {
        return symbols.get(name);
    }

    @Override
    public boolean isDuplicateName(String id) {
        return symbols.containsKey(id);
    }

    public void define(Symbol sym) {
        symbols.put(sym.getName(), sym);
        sym.setScope(this); // track the com.codbex.kronos.parser.hdbdd.symbols.scope in each symbol
    }

    public void defineWithCustomName(String customName, Symbol symbol) {
        symbols.put(customName, symbol);
    }

    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    public String toString() {
        return symbols.keySet().toString();
    }
}
