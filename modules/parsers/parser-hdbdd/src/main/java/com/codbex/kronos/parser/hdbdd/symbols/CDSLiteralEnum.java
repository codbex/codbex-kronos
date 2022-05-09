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
package com.codbex.kronos.parser.hdbdd.symbols;

import com.codbex.kronos.parser.hdbdd.core.CdsParser;

public enum CDSLiteralEnum {
    INTEGER(CdsParser.INTEGER),
    DECIMAL(CdsParser.DECIMAL),
    STRING(CdsParser.STRING),
    BOOLEAN(CdsParser.BOOLEAN),
    OBJECT(4),
    ARRAY(5),
    ENUM(6);

    private int literalType;

    CDSLiteralEnum(int literalType) {
        this.literalType = literalType;
    }

    public int getLiteralType() {
        return literalType;
    }
}
