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
package com.codbex.kronos.parser.hdbdd.symbols.type.custom;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import com.codbex.kronos.parser.hdbdd.symbols.type.Type;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.Typeable;

/**
 * The Class DataTypeSymbol.
 */
public class DataTypeSymbol extends Symbol implements Type, CustomDataType, Typeable {

    /** The type. */
    private Type type;

    /** The reference. */
    private String reference;

    /**
     * Instantiates a new data type symbol.
     *
     * @param name the name
     * @param scope the scope
     */
    public DataTypeSymbol(String name, Scope scope) {
        super(name, scope);
    }

    /**
     * Instantiates a new data type symbol.
     *
     * @param symbol the symbol
     */
    public DataTypeSymbol(Symbol symbol) {
        super(symbol);
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    @Override
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Sets the reference.
     *
     * @param reference the new reference
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Type getType() {
        return type;
    }

    /**
     * Gets the reference.
     *
     * @return the reference
     */
    @Override
    public String getReference() {
        return reference;
    }
}
