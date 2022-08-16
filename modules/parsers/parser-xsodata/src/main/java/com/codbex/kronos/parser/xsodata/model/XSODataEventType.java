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
public enum XSODataEventType {
    
    /** The before. */
    BEFORE("before", "before"),
    
    /** The after. */
    AFTER("after", "after"),
    
    /** The precommit. */
    PRECOMMIT("precommit", ""),
    
    /** The postcommit. */
    POSTCOMMIT("postcommit", "");

    /** The text. */
    private final String text;
    
    /** The odata handler type. */
    private final String odataHandlerType;

    /**
     * Instantiates a new HDBXSO data event type.
     *
     * @param text the text
     * @param odataHandlerType the odata handler type
     */
    XSODataEventType(String text, String odataHandlerType) {
        this.text = text;
        this.odataHandlerType = odataHandlerType;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Gets the odata handler type.
     *
     * @return the odata handler type
     */
    public String getOdataHandlerType() {
        return odataHandlerType;
    }

    /**
     * From value.
     *
     * @param text the text
     * @return the optional
     */
    public static Optional<XSODataEventType> fromValue(String text) {
        return Arrays.stream(values())
                .filter(bl -> bl.text.equalsIgnoreCase(text))
                .findFirst();
    }

    /**
     * Value.
     *
     * @return the string
     */
    public String value() {
        return name();
    }

}