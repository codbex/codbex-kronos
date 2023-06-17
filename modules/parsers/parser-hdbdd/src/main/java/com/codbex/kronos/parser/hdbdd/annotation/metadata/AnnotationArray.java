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
package com.codbex.kronos.parser.hdbdd.annotation.metadata;

import java.util.ArrayList;
import java.util.List;

import com.codbex.kronos.parser.hdbdd.symbols.CDSLiteralEnum;

/**
 * The Class AnnotationArray.
 */
public class AnnotationArray extends AbstractAnnotationValue {
    
    /** The values. */
    private List<AbstractAnnotationValue> values;
    
    /** The arr value type. */
    private AbstractAnnotationValue arrValueType;

    /**
     * Instantiates a new annotation array.
     *
     * @param arrValueType the arr value type
     */
    public AnnotationArray(AbstractAnnotationValue arrValueType) {
        this(CDSLiteralEnum.ARRAY.getLiteralType());
        this.arrValueType = arrValueType;
    }

    /**
     * Instantiates a new annotation array.
     *
     * @param valueType the value type
     */
    public AnnotationArray(int valueType) {
        super(valueType);
        this.values = new ArrayList<>();
    }

    /**
     * Gets the values.
     *
     * @return the values
     */
    public List<AbstractAnnotationValue> getValues() {
        return values;
    }

    /**
     * Adds the value.
     *
     * @param annotationValue the annotation value
     */
    public void addValue(AbstractAnnotationValue annotationValue) {
        this.values.add(annotationValue);
    }

    /**
     * Gets the arr value type.
     *
     * @return the arr value type
     */
    public AbstractAnnotationValue getArrValueType() {
        return arrValueType;
    }
}
