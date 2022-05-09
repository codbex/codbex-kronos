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
package com.codbex.kronos.parser.hdbdd.annotation.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codbex.kronos.parser.hdbdd.symbols.CDSLiteralEnum;

public class AnnotationObj extends AbstractAnnotationValue {
    private Map<String, AbstractAnnotationValue> keyValuePairs;
    private String name;
    private List<Class> allowedForSymbols;
    private boolean isTopLevel;

    public AnnotationObj() {
        super(CDSLiteralEnum.OBJECT.getLiteralType());
        this.keyValuePairs = new HashMap<>();
    }

    public void define(String key, AbstractAnnotationValue value) {
        keyValuePairs.put(key, value);
    }

    public AbstractAnnotationValue getValue(String key) {
        return this.keyValuePairs.get(key);
    }

    public Map<String, AbstractAnnotationValue> getKeyValuePairs() {
        return keyValuePairs;
    }

    public int getKeysNumber() {
        return this.keyValuePairs.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Class> getAllowedForSymbols() {
        return allowedForSymbols;
    }

    public void setAllowedForSymbols(List<Class> allowedForSymbols) {
        this.allowedForSymbols = allowedForSymbols;
    }

    public boolean isTopLevel() {
        return isTopLevel;
    }

    public void setTopLevel(boolean topLevel) {
        isTopLevel = topLevel;
    }
}
