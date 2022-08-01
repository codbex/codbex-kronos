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

public enum HDBXSODataMultiplicityType {
    ONE("1"),
    ZERO_TO_ONE("0..1"),
    ONE_TO_MANY("1..*"),
    MANY("*");

    private final String text;

    HDBXSODataMultiplicityType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Optional<HDBXSODataMultiplicityType> fromValue(String text) {
        return Arrays.stream(values())
                .filter(bl -> bl.text.equalsIgnoreCase(text))
                .findFirst();
    }

    public String value() {
        return name();
    }

}
