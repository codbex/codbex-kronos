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

import java.util.Arrays;
import java.util.Optional;
/**
 * The text should reflect the values from org.eclipse.dirigible.engine.odata2.definition.ODataHandlerTypes
 */
public enum HDBXSODataEventType {
    BEFORE("before", "before"),
    AFTER("after", "after"),
    PRECOMMIT("precommit", ""),
    POSTCOMMIT("postcommit", "");

    private final String text;
    private final String odataHandlerType;

    HDBXSODataEventType(String text, String odataHandlerType) {
        this.text = text;
        this.odataHandlerType = odataHandlerType;
    }

    public String getText() {
        return this.text;
    }

    public String getOdataHandlerType() {
        return odataHandlerType;
    }

    public static Optional<HDBXSODataEventType> fromValue(String text) {
        return Arrays.stream(values())
                .filter(bl -> bl.text.equalsIgnoreCase(text))
                .findFirst();
    }

    public String value() {
        return name();
    }

}