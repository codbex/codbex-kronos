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
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The Class TableConstraints.
 */
@Entity
@Table(name = "KRONOS_TABLE_CONSTRAINTS")
public class HDBTableConstraints {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONSTRAINTS_ID", nullable = false)
    private Long id;

    /** The primary key. */
    @OneToOne(mappedBy = "constraints", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @Nullable
    @Expose
    private HDBTableConstraintPrimaryKey primaryKey;

    /** The foreign keys. */
    @OneToMany(mappedBy = "constraints", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Nullable
    @Expose
    private List<HDBTableConstraintForeignKey> foreignKeys = new ArrayList<HDBTableConstraintForeignKey>();

    /** The unique indices. */
    @OneToMany(mappedBy = "constraints", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Nullable
    @Expose
    private List<HDBTableConstraintUnique> uniqueIndexes = new ArrayList<HDBTableConstraintUnique>();

    /** The checks. */
    @OneToMany(mappedBy = "constraints", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Nullable
    @Expose
    private List<HDBTableConstraintCheck> checks = new ArrayList<HDBTableConstraintCheck>();

    /** The table. */
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "TABLE_ID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HDBTable table;

    /**
     * Instantiates a new table constraints.
     *
     * @param table the table
     */
    public HDBTableConstraints(HDBTable table) {
        this();
        this.table = table;
    }

    /**
     * Instantiates a new table constraints.
     */
    public HDBTableConstraints() {
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
     * Gets the primary key.
     *
     * @return the primaryKey
     */
    public HDBTableConstraintPrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Sets the primary key.
     *
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(HDBTableConstraintPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Gets the foreign keys.
     *
     * @return the foreignKeys
     */
    public List<HDBTableConstraintForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    /**
     * Sets the foreign keys.
     *
     * @param foreignKeys the foreignKeys to set
     */
    public void setForeignKeys(List<HDBTableConstraintForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    /**
     * Get the foreignKey by name.
     *
     * @return the foreignKey
     */
    public HDBTableConstraintForeignKey getForeignKey(String name) {
        final List<HDBTableConstraintForeignKey> foreignKeysList = foreignKeys;
        if (foreignKeysList != null) {
            for (HDBTableConstraintForeignKey fk : foreignKeysList) {
                if (fk.getName()
                      .equals(name)) {
                    return fk;
                }
            }
        }
        return null;
    }

    /**
     * Gets the unique indexes.
     *
     * @return the uniqueIndexes
     */
    public List<HDBTableConstraintUnique> getUniqueIndexes() {
        return uniqueIndexes;
    }

    /**
     * Sets the unique indexes.
     *
     * @param uniqueIndexes the uniqueIndexes to set
     */
    public void setUniqueIndexes(List<HDBTableConstraintUnique> uniqueIndexes) {
        this.uniqueIndexes = uniqueIndexes;
    }

    /**
     * Get the uniqueIndex by name.
     *
     * @return the uniqueIndex
     */
    public HDBTableConstraintUnique getUniqueIndex(String name) {
        final List<HDBTableConstraintUnique> uniqueIndexesList = uniqueIndexes;
        if (uniqueIndexesList != null) {
            for (HDBTableConstraintUnique ui : uniqueIndexesList) {
                if (ui.getName()
                      .equals(name)) {
                    return ui;
                }
            }
        }
        return null;
    }

    /**
     * Gets the checks.
     *
     * @return the checks
     */
    public List<HDBTableConstraintCheck> getChecks() {
        return checks;
    }

    /**
     * Sets the checks.
     *
     * @param checks the checks to set
     */
    public void setChecks(List<HDBTableConstraintCheck> checks) {
        this.checks = checks;
    }

    /**
     * Get the checks by name.
     *
     * @return the checks
     */
    public HDBTableConstraintCheck getCheck(String name) {
        final List<HDBTableConstraintCheck> checksList = checks;
        if (checksList != null) {
            for (HDBTableConstraintCheck ck : checksList) {
                if (ck.getName()
                      .equals(name)) {
                    return ck;
                }
            }
        }
        return null;
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
        return "HDBTableConstraints{" + "id=" + id + ", primaryKey=" + primaryKey + ", foreignKeys=" + foreignKeys + ", uniqueIndexes="
                + uniqueIndexes + ", checks=" + checks + ", table=" + (table == null ? null : table.getName()) + '}';
    }
}
