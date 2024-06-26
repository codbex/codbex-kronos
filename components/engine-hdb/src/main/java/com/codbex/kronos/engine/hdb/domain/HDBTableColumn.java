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
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The Class TableColumn.
 */
@Entity
@Table(name = "KRONOS_TABLE_COLUMNS")
public class HDBTableColumn extends HDBColumn {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLUMN_ID", nullable = false)
    @Expose
    private Long id;

    /** The table. */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TABLE_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HDBTable table;

    /**
     * Instantiates a new table column.
     *
     * @param name the name
     * @param type the type
     * @param length the length
     * @param nullable the nullable
     * @param primaryKey the primary key
     * @param defaultValue the default value
     * @param scale the scale
     * @param unique the unique
     * @param table the table
     */
    public HDBTableColumn(String name, String type, String length, boolean nullable, boolean primaryKey, String defaultValue, String scale,
            boolean unique, HDBTable table) {
        super(name, type, length, nullable, primaryKey, defaultValue, scale, unique);
        this.table = table;
        this.table.getColumns()
                  .add(this);
    }

    /**
     * Instantiates a new table column.
     *
     * @param name the name
     * @param type the type
     * @param length the length
     * @param table the table
     */
    public HDBTableColumn(String name, String type, String length, HDBTable table) {
        this(name, type, length, true, false, null, "0", false, table);
    }

    /**
     * Instantiates a new table column.
     *
     * @param name the name
     * @param type the type
     * @param length the length
     * @param nullable the nullable
     * @param primaryKey the primary key
     * @param table the table
     */
    public HDBTableColumn(String name, String type, String length, boolean nullable, boolean primaryKey, HDBTable table) {
        this(name, type, length, nullable, primaryKey, null, "0", false, table);
    }

    /**
     * Instantiates a new table column.
     */
    public HDBTableColumn() {
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
     * Gets the table.
     *
     * @return the table
     */
    public HDBTable getTable() {
        return table;
    }

    /**
     * Sets the table.
     *
     * @param table the table to set
     */
    public void setTable(HDBTable table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "HDBTableColumn{" + "id=" + id + ", table=" + (table == null ? null : table.getName()) + '}';
    }
}
