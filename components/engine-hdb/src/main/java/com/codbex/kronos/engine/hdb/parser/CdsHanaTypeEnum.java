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
package com.codbex.kronos.engine.hdb.parser;

/**
 * These types should respond to hanaBuiltInTypes in SymbolTable class
 */
public enum CdsHanaTypeEnum {

    /** The varchar. */
    VARCHAR("hana.VARCHAR"),

    /** The alphanumeric. */
    ALPHANUMERIC("hana.ALPHANUM"),

    /** The smallint. */
    SMALLINT("hana.SMALLINT"),

    /** The tinyint. */
    TINYINT("hana.TINYINT"),

    /** The smalldecimal. */
    SMALLDECIMAL("hana.SMALLDECIMAL"),

    /** The clob. */
    CLOB("hana.CLOB"),

    /** The binary. */
    BINARY("hana.BINARY"),

    /** The st point. */
    ST_POINT("hana.ST_POINT"),

    /** The st geometry. */
    ST_GEOMETRY("hana.ST_GEOMETRY");

    /** The prefixed name. */
    public final String prefixedName;

    /**
     * Instantiates a new cds hana type enum.
     *
     * @param prefixedName the prefixed name
     */
    private CdsHanaTypeEnum(String prefixedName) {
        this.prefixedName = prefixedName;
    }

    /**
     * Gets the SQL name.
     *
     * @param prefixedName the prefixed name
     * @return the SQL name
     */
    public static CdsHanaTypeEnum getSQLName(String prefixedName) {
        for (CdsHanaTypeEnum e : values()) {
            if (e.prefixedName.equals(prefixedName)) {
                return e;
            }
        }
        return null;
    }
}
