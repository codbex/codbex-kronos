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
package com.codbex.kronos.parser.xsodata.model;

/**
 * The Enum HDBXSODataAggregationType.
 */
public enum HDBXSODataAggregationType {
    
    /** The explicit. */
    EXPLICIT,
    
    /** The implicit. */
    IMPLICIT;

    /**
     * Value.
     *
     * @return the string
     */
    public String value() {
        return name();
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the HDBXSO data aggregation type
     */
    public static HDBXSODataAggregationType fromValue(String v) {
        return valueOf(v);
    }
}
