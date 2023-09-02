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
package com.codbex.kronos.parser.hdbdd.symbols;

import com.codbex.kronos.parser.hdbdd.core.CdsLexer;

/**
 * The Enum CDSLiteralEnum.
 */
public enum CDSLiteralEnum {
    
    /** The integer. */
    INTEGER(CdsLexer.INTEGER),
    
    /** The decimal. */
    DECIMAL(CdsLexer.DECIMAL),
    
    /** The string. */
    STRING(CdsLexer.STRING),
    
    /** The boolean. */
    BOOLEAN(CdsLexer.BOOLEAN),
    
    /** The object. */
    OBJECT(4),
    
    /** The array. */
    ARRAY(5),
    
    /** The enum. */
    ENUM(6);

    /** The literal type. */
    private int literalType;

    /**
     * Instantiates a new CDS literal enum.
     *
     * @param literalType the literal type
     */
    CDSLiteralEnum(int literalType) {
        this.literalType = literalType;
    }

    /**
     * Gets the literal type.
     *
     * @return the literal type
     */
    public int getLiteralType() {
        return literalType;
    }
}
