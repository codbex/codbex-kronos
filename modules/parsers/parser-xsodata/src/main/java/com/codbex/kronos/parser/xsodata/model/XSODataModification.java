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
 * The Class HDBXSODataModification.
 */
public class XSODataModification {

    /** The method. */
    private XSODataHandlerMethod method;

    /** The specification. */
    private XSODataModificationSpec specification;

    /**
     * Gets the method.
     *
     * @return the method
     */
    public XSODataHandlerMethod getMethod() {
        return method;
    }

    /**
     * Sets the method.
     *
     * @param method the method
     * @return the HDBXSO data modification
     */
    public XSODataModification setMethod(XSODataHandlerMethod method) {
        this.method = method;
        return this;
    }

    /**
     * Gets the specification.
     *
     * @return the specification
     */
    public XSODataModificationSpec getSpecification() {
        return specification;
    }

    /**
     * Sets the specification.
     *
     * @param specification the specification
     * @return the HDBXSO data modification
     */
    public XSODataModification setSpecification(XSODataModificationSpec specification) {
        this.specification = specification;
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
        XSODataModification that = (XSODataModification) o;
        return method == that.method && Objects.equals(specification, that.specification);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(method, specification);
    }
}
