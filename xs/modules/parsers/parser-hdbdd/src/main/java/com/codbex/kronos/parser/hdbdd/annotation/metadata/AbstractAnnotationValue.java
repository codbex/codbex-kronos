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
package com.codbex.kronos.parser.hdbdd.annotation.metadata;

/**
 * The Class AbstractAnnotationValue.
 */
public abstract class AbstractAnnotationValue {

    /** The value type. */
    private int valueType;

    /** The value. */
    private String value;

    /**
     * Instantiates a new abstract annotation value.
     *
     * @param valueType the value type
     */
    public AbstractAnnotationValue(int valueType) {
        this.valueType = valueType;
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
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
