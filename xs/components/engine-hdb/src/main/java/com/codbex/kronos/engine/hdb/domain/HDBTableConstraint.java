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

import java.util.Arrays;

import jakarta.annotation.Nullable;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;

import org.eclipse.dirigible.components.base.converters.ArrayOfStringsToCsvConverter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

/**
 * The Class TableConstraint.
 */
@MappedSuperclass
public abstract class HDBTableConstraint {

    /** The name. */
    @Column(name = "CONSTRAINT_NAME", columnDefinition = "VARCHAR", nullable = true, length = 255)
    @Nullable
    @Expose
    protected String name;

    /** The modifiers. */
    @Column(name = "CONSTRAINT_MODIFIERS", columnDefinition = "VARCHAR", nullable = true, length = 2000)
    @Nullable
    @Convert(converter = ArrayOfStringsToCsvConverter.class)
    @Expose
    protected String[] modifiers;

    /** The columns. */
    @Column(name = "CONSTRAINT_COLUMNS", columnDefinition = "VARCHAR", nullable = true, length = 2000)
    @Nullable
    @Convert(converter = ArrayOfStringsToCsvConverter.class)
    @Expose
    protected String[] columns;

    /** The constraints. */
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "CONSTRAINTS_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    protected HDBTableConstraints constraints;

    /**
     * Instantiates a new table constraint.
     *
     * @param name the name
     * @param modifiers the modifiers
     * @param columns the columns
     * @param constraints the constraints
     */
    public HDBTableConstraint(String name, String[] modifiers, String[] columns, HDBTableConstraints constraints) {
        super();
        this.name = name;
        this.modifiers = modifiers;
        this.columns = columns;
        this.constraints = constraints;
    }

    /**
     * Instantiates a new table constraint.
     */
    public HDBTableConstraint() {
        super();
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the modifiers.
     *
     * @return the modifiers
     */
    public String[] getModifiers() {
        return modifiers;
    }

    /**
     * Sets the modifiers.
     *
     * @param modifiers the modifiers to set
     */
    public void setModifiers(String[] modifiers) {
        this.modifiers = modifiers;
    }

    /**
     * Gets the columns.
     *
     * @return the columns
     */
    public String[] getColumns() {
        return columns;
    }

    /**
     * Sets the columns.
     *
     * @param columns the columns to set
     */
    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    /**
     * Gets the constraints.
     *
     * @return the constraints
     */
    public HDBTableConstraints getConstraints() {
        return constraints;
    }

    /**
     * Sets the constraints.
     *
     * @param constraints the constraints to set
     */
    public void setConstraints(HDBTableConstraints constraints) {
        this.constraints = constraints;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "TableConstraint [name=" + name + ", modifiers=" + Arrays.toString(modifiers) + ", columns=" + Arrays.toString(columns)
                + "]";
    }

}
