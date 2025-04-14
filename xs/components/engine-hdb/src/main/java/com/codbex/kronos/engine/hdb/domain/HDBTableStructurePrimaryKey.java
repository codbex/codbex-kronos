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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Arrays;
import org.eclipse.dirigible.components.base.converters.ArrayOfStringsToCsvConverter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The Class HDBTableTypePrimaryKey.
 */
@Entity
@Table(name = "KRONOS_TABLESTRUCTURE_PRIMARYKEYS")
public class HDBTableStructurePrimaryKey {

    /**
     * The id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRIMARYKEY_ID", nullable = false)
    private Long id;

    /**
     * The name.
     */
    @Column(name = "PRIMARYKEY_NAME", columnDefinition = "VARCHAR", nullable = true, length = 255)
    @Nullable
    @Expose
    protected String name;

    /**
     * The modifiers.
     */
    @Column(name = "PRIMARYKEY_MODIFIERS", columnDefinition = "VARCHAR", nullable = true, length = 2000)
    @Nullable
    // @ElementCollection
    // @OrderColumn
    @Convert(converter = ArrayOfStringsToCsvConverter.class)
    @Expose
    protected String[] modifiers;

    /**
     * The columns.
     */
    @Column(name = "PRIMARYKEY_COLUMNS", columnDefinition = "VARCHAR", nullable = true, length = 2000)
    @Nullable
    // @ElementCollection
    // @OrderColumn
    @Convert(converter = ArrayOfStringsToCsvConverter.class)
    @Expose
    protected String[] columns;

    /**
     * The constraints.
     */
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TABLESTRUCTURE_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    protected HDBTableStructure tableStructure;

    /**
     * Instantiates a new HDB table structure primary key.
     *
     * @param name the name
     * @param modifiers the modifiers
     * @param columns the columns
     * @param tableStructure the table structure
     */
    public HDBTableStructurePrimaryKey(String name, String[] modifiers, String[] columns, HDBTableStructure tableStructure) {
        this.tableStructure = tableStructure;
        this.tableStructure.setPrimaryKey(this);
    }

    /**
     * Instantiates a new table constraint primary key.
     */
    public HDBTableStructurePrimaryKey() {}

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
     * @param name the new name
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
     * @param modifiers the new modifiers
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
     * @param columns the new columns
     */
    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    /**
     * Gets the table structure.
     *
     * @return the table structure
     */
    public HDBTableStructure getTableStructure() {
        return tableStructure;
    }

    /**
     * Sets the table structure.
     *
     * @param tableStructure the new table structure
     */
    public void setTableStructure(HDBTableStructure tableStructure) {
        this.tableStructure = tableStructure;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "HDBTableStructurePrimaryKey [id=" + id + ", name=" + name + ", modifiers=" + Arrays.toString(modifiers) + ", columns="
                + Arrays.toString(columns) + "]";
    }

}
