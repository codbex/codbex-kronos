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

import java.util.List;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;

/**
 * The Class ArraySymbol.
 */
public class ArraySymbol extends Symbol {
    
    /** The values. */
    private List<String> values;
    
    /** The value type. */
    private int valueType;

    /**
     * Instantiates a new array symbol.
     *
     * @param name the name
     */
    public ArraySymbol(String name) {
        super(name);
    }

    /**
     * Instantiates a new array symbol.
     *
     * @param name the name
     * @param scope the scope
     */
    public ArraySymbol(String name, Scope scope) {
        super(name, scope);
    }

    /**
     * Gets the values.
     *
     * @return the values
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * Adds the value.
     *
     * @param value the value
     */
    public void addValue(String value) {
        this.values.add(value);
    }

    /**
     * Gets the value type.
     *
     * @return the value type
     */
    public int getValueType() {
        return valueType;
    }

    /**
     * Sets the value type.
     *
     * @param valueType the new value type
     */
    public void setValueType(int valueType) {
        this.valueType = valueType;
    }
}
