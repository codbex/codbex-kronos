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
package com.codbex.kronos.engine.hdb.domain;

import com.google.gson.annotations.Expose;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * The Class TableConstraintUnique.
 */
@Entity
@jakarta.persistence.Table(name = "KRONOS_TABLE_UNIQUES")
public class HDBTableConstraintUnique extends HDBTableConstraint {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIQUE_ID", nullable = false)
    private Long id;

    /** The index type. */
    @Column(name = "UNIQUE_INDEXTYPE", columnDefinition = "VARCHAR", nullable = true, length = 255)
    @Nullable
    @Expose
    private String indexType;

    /** The order. */
    @Column(name = "UNIQUE_ORDER", columnDefinition = "VARCHAR", nullable = true, length = 255)
    @Nullable
    @Expose
    private String order;

    /**
     * Instantiates a new table constraint unique.
     *
     * @param name the name
     * @param modifiers the modifiers
     * @param columns the columns
     * @param constraints the constraints
     * @param indexType the index type
     * @param order the order
     */
    public HDBTableConstraintUnique(String name, String[] modifiers, String[] columns, HDBTableConstraints constraints, String indexType,
            String order) {
        super(name, modifiers, columns, constraints);
        this.indexType = indexType;
        this.order = order;
        this.constraints.getUniqueIndexes()
                        .add(this);
    }

    /**
     * Instantiates a new table constraint unique.
     */
    public HDBTableConstraintUnique() {
        super();
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the index type.
     *
     * @return the indexType
     */
    public String getIndexType() {
        return indexType;
    }

    /**
     * Sets the index type.
     *
     * @param indexType the indexType to set
     */
    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    /**
     * Gets the order.
     *
     * @return the order
     */
    public String getOrder() {
        return order;
    }

    /**
     * Sets the order.
     *
     * @param order the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "HDBTableConstraintUnique{" + "id=" + id + ", indexType='" + indexType + '\'' + ", order='" + order + '\'' + '}';
    }
}
