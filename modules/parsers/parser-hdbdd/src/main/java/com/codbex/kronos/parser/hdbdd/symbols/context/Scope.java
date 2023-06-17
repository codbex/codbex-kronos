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
package com.codbex.kronos.parser.hdbdd.symbols.context;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;

/**
 * The Interface Scope.
 */
public interface Scope {

    /**
     * Gets the enclosing scope.
     *
     * @return the enclosing scope
     */
    Scope getEnclosingScope(); // am I nested in another?

    /**
     * Define.
     *
     * @param sym the sym
     */
    void define(Symbol sym); // define sym in this com.codbex.kronos.parser.hdbdd.symbols.scope

    /**
     * Resolve.
     *
     * @param name the name
     * @return the symbol
     */
    Symbol resolve(String name); //

    /**
     * Checks if is duplicate name.
     *
     * @param id the id
     * @return true, if is duplicate name
     */
    boolean isDuplicateName(String id);
}
