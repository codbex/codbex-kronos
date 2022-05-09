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

public enum XSKHDBXSODATAStorageType {
    NO_STORAGE("nostorage"),
    STORAGE_ON_PRINCIPAL("storageonprincipal"),
    STORAGE_ON_DEPENDENT("storageondependent");

    private final String text;

    XSKHDBXSODATAStorageType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Optional<XSKHDBXSODATAStorageType> fromValue(String text) {
        return Arrays.stream(values())
                .filter(bl -> bl.text.equalsIgnoreCase(text))
                .findFirst();
    }

    public String value() {
        return name();
    }

}
