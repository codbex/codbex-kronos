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
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The Class TableColumn.
 */
@Entity
@jakarta.persistence.Table(name = "KRONOS_TABLETYPE_COLUMNS")
public class HDBTableTypeColumn extends HDBColumn {

    /**
     * The id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLUMN_ID", nullable = false)
    @Expose
    private Long id;

    /**
     * The tabletype.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TABLETYPE_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HDBTableType tableType;

    /**
     * Instantiates a new table type column.
     *
     * @param name the name
     * @param type the type
     * @param length the length
     * @param nullable the nullable
     * @param primaryKey the primary key
     * @param defaultValue the default value
     * @param scale the scale
     * @param unique the unique
     * @param tableType the tableType
     */
    public HDBTableTypeColumn(String name, String type, String length, boolean nullable, boolean primaryKey, String defaultValue,
            String scale, boolean unique, HDBTableType tableType) {
        super(name, type, length, nullable, primaryKey, defaultValue, scale, unique);
        this.tableType = tableType;
        this.tableType.getColumns()
                      .add(this);
    }

    /**
     * Instantiates a new table type column.
     *
     * @param name the name
     * @param type the type
     * @param length the length
     * @param tableType the tableType
     */
    public HDBTableTypeColumn(String name, String type, String length, HDBTableType tableType) {
        this(name, type, length, true, false, null, "0", false, tableType);
    }

    /**
     * Instantiates a new table type column.
     *
     * @param name the name
     * @param type the type
     * @param length the length
     * @param nullable the nullable
     * @param primaryKey the primary key
     * @param tableType the tableType
     */
    public HDBTableTypeColumn(String name, String type, String length, boolean nullable, boolean primaryKey, HDBTableType tableType) {
        this(name, type, length, nullable, primaryKey, null, "0", false, tableType);
    }

    /**
     * Instantiates a new table type column.
     */
    public HDBTableTypeColumn() {}

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
     * Gets the table type.
     *
     * @return the table type
     */
    public HDBTableType getTableType() {
        return tableType;
    }

    /**
     * Sets the table type.
     *
     * @param tableType the new table type
     */
    public void setTableType(HDBTableType tableType) {
        this.tableType = tableType;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "HDBTableColumn [id=" + id + ", getName()=" + getName() + ", getType()=" + getType() + ", getLength()=" + getLength()
                + ", isNullable()=" + isNullable() + ", isPrimaryKey()=" + isPrimaryKey() + ", getDefaultValue()=" + getDefaultValue()
                + ", getPrecision()=" + getPrecision() + ", getScale()=" + getScale() + ", isUnique()=" + isUnique()
                + ", isDefaultValueDateTimeFunction()=" + isDefaultValueDateTimeFunction() + ", getComment()=" + getComment()
                + ", getAlias()=" + getAlias() + ", isFuzzySearchIndex()=" + isFuzzySearchIndex() + ", getStatement()=" + getStatement()
                + ", isCalculatedColumn()=" + isCalculatedColumn() + "]";
    }

}
