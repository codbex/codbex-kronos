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
package com.codbex.kronos.parser.xsodata.model;

/**
 * The Enum HDBXSODataParameterType.
 */
public enum XSODataParameterType {

    /** The key and entity. */
    KEY_AND_ENTITY,

    /** The entity. */
    ENTITY;

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
     * @return the HDBXSO data parameter type
     */
    public static XSODataParameterType fromValue(String v) {
        return valueOf(v);
    }
}
