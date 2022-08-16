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
package com.codbex.kronos.parser.hdbdd.annotation.metadata;

import java.util.HashSet;
import java.util.Set;

import com.codbex.kronos.parser.hdbdd.symbols.CDSLiteralEnum;

/**
 * The Class AnnotationEnum.
 */
public class AnnotationEnum extends AbstractAnnotationValue {
    
    /** The enum values. */
    private Set<String> enumValues;
    
    /**
     * Instantiates a new annotation enum.
     */
    public AnnotationEnum() {
        super(CDSLiteralEnum.ENUM.getLiteralType());
        enumValues = new HashSet<>();
    }

    /**
     * Adds the.
     *
     * @param enumValue the enum value
     */
    public void add(String enumValue) {
        this.enumValues.add(enumValue);
    }

    /**
     * Checks for value.
     *
     * @param enumValue the enum value
     * @return true, if successful
     */
    public boolean hasValue(String enumValue) {
        return this.enumValues.contains(enumValue);
    }
}
