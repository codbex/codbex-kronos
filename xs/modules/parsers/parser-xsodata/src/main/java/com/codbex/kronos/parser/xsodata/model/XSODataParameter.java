/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.parser.xsodata.model;

import java.util.Objects;

/**
 * The Class HDBXSODataParameter.
 */
public class XSODataParameter {

    /** The parameter entity set name. */
    private String parameterEntitySetName;

    /** The parameter results property. */
    private String parameterResultsProperty;

    /**
     * Gets the parameter entity set name.
     *
     * @return the parameter entity set name
     */
    public String getParameterEntitySetName() {
        return parameterEntitySetName;
    }

    /**
     * Sets the parameter entity set name.
     *
     * @param parameterEntitySetName the parameter entity set name
     * @return the HDBXSO data parameter
     */
    public XSODataParameter setParameterEntitySetName(String parameterEntitySetName) {
        this.parameterEntitySetName = parameterEntitySetName;
        return this;
    }

    /**
     * Gets the parameter results property.
     *
     * @return the parameter results property
     */
    public String getParameterResultsProperty() {
        return parameterResultsProperty;
    }

    /**
     * Sets the parameter results property.
     *
     * @param parameterResultsProperty the parameter results property
     * @return the HDBXSO data parameter
     */
    public XSODataParameter setParameterResultsProperty(String parameterResultsProperty) {
        this.parameterResultsProperty = parameterResultsProperty;
        return this;
    }

    /**
     * Equals.
     *
     * @param o the o
     * @return true, if successful
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        XSODataParameter that = (XSODataParameter) o;
        return Objects.equals(parameterEntitySetName, that.parameterEntitySetName)
                && Objects.equals(parameterResultsProperty, that.parameterResultsProperty);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(parameterEntitySetName, parameterResultsProperty);
    }
}
