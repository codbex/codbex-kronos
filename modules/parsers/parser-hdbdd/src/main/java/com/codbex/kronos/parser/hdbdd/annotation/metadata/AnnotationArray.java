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

import com.codbex.kronos.parser.hdbdd.symbols.CDSLiteralEnum;
import java.util.ArrayList;
import java.util.List;

public class AnnotationArray extends AbstractAnnotationValue {
    private List<AbstractAnnotationValue> values;
    private AbstractAnnotationValue arrValueType;

    public AnnotationArray(AbstractAnnotationValue arrValueType) {
        this(CDSLiteralEnum.ARRAY.getLiteralType());
        this.arrValueType = arrValueType;
    }

    public AnnotationArray(int valueType) {
        super(valueType);
        this.values = new ArrayList<>();
    }

    public List<AbstractAnnotationValue> getValues() {
        return values;
    }

    public void addValue(AbstractAnnotationValue annotationValue) {
        this.values.add(annotationValue);
    }

    public AbstractAnnotationValue getArrValueType() {
        return arrValueType;
    }
}
