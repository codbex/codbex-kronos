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
package com.codbex.kronos.parser.xsodata.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * The Enum HDBXSODataBindingType.
 */
public enum XSODataBindingType {
    
    /** The principal. */
    PRINCIPAL("principal"),
    
    /** The dependent. */
    DEPENDENT("dependent");

    /** The text. */
    private final String text;

    /**
     * Instantiates a new HDBXSO data binding type.
     *
     * @param text the text
     */
    XSODataBindingType(String text) {
        this.text = text;
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
     * From value.
     *
     * @param text the text
     * @return the optional
     */
    public static Optional<XSODataBindingType> fromValue(String text) {
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
