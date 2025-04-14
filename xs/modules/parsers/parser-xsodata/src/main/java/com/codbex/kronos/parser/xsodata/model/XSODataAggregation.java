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
 * The Class HDBXSODataAggregation.
 */
public class XSODataAggregation {

    /** The aggregate function. */
    private String aggregateFunction;

    /** The aggregate column name. */
    private String aggregateColumnName;

    /**
     * Gets the aggregate function.
     *
     * @return the aggregate function
     */
    public String getAggregateFunction() {
        return aggregateFunction;
    }

    /**
     * Sets the aggregate function.
     *
     * @param aggregateFunction the aggregate function
     * @return the HDBXSO data aggregation
     */
    public XSODataAggregation setAggregateFunction(String aggregateFunction) {
        this.aggregateFunction = aggregateFunction;
        return this;
    }

    /**
     * Gets the aggregate column name.
     *
     * @return the aggregate column name
     */
    public String getAggregateColumnName() {
        return aggregateColumnName;
    }

    /**
     * Sets the aggregate column name.
     *
     * @param aggregateColumnName the aggregate column name
     * @return the HDBXSO data aggregation
     */
    public XSODataAggregation setAggregateColumnName(String aggregateColumnName) {
        this.aggregateColumnName = aggregateColumnName;
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
        XSODataAggregation that = (XSODataAggregation) o;
        return Objects.equals(aggregateFunction, that.aggregateFunction) && Objects.equals(aggregateColumnName, that.aggregateColumnName);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(aggregateFunction, aggregateColumnName);
    }
}
