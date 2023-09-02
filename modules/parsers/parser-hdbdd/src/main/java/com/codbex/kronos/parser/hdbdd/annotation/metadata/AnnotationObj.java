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
package com.codbex.kronos.parser.hdbdd.annotation.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codbex.kronos.parser.hdbdd.symbols.CDSLiteralEnum;

/**
 * The Class AnnotationObj.
 */
public class AnnotationObj extends AbstractAnnotationValue {
    
    /** The key value pairs. */
    private Map<String, AbstractAnnotationValue> keyValuePairs;
    
    /** The name. */
    private String name;
    
    /** The allowed for symbols. */
    private List<Class> allowedForSymbols;
    
    /** The is top level. */
    private boolean isTopLevel;

    /**
     * Instantiates a new annotation obj.
     */
    public AnnotationObj() {
        super(CDSLiteralEnum.OBJECT.getLiteralType());
        this.keyValuePairs = new HashMap<>();
    }

    /**
     * Define.
     *
     * @param key the key
     * @param value the value
     */
    public void define(String key, AbstractAnnotationValue value) {
        keyValuePairs.put(key, value);
    }

    /**
     * Gets the value.
     *
     * @param key the key
     * @return the value
     */
    public AbstractAnnotationValue getValue(String key) {
        return this.keyValuePairs.get(key);
    }

    /**
     * Gets the key value pairs.
     *
     * @return the key value pairs
     */
    public Map<String, AbstractAnnotationValue> getKeyValuePairs() {
        return keyValuePairs;
    }

    /**
     * Gets the keys number.
     *
     * @return the keys number
     */
    public int getKeysNumber() {
        return this.keyValuePairs.size();
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the allowed for symbols.
     *
     * @return the allowed for symbols
     */
    public List<Class> getAllowedForSymbols() {
        return allowedForSymbols;
    }

    /**
     * Sets the allowed for symbols.
     *
     * @param allowedForSymbols the new allowed for symbols
     */
    public void setAllowedForSymbols(List<Class> allowedForSymbols) {
        this.allowedForSymbols = allowedForSymbols;
    }

    /**
     * Checks if is top level.
     *
     * @return true, if is top level
     */
    public boolean isTopLevel() {
        return isTopLevel;
    }

    /**
     * Sets the top level.
     *
     * @param topLevel the new top level
     */
    public void setTopLevel(boolean topLevel) {
        isTopLevel = topLevel;
    }
}
