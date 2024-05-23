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
package com.codbex.kronos.parser.hdbdd.symbols.entity;

import java.util.ArrayList;
import java.util.List;

import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.FieldSymbol;

/**
 * The Class AssociationSymbol.
 */
public class AssociationSymbol extends FieldSymbol {

    /** The cardinality. */
    private CardinalityEnum cardinality;

    /** The target. */
    private EntitySymbol target;

    /** The foreign keys. */
    private List<EntityElementSymbol> foreignKeys = new ArrayList<>();

    /** The is managed. */
    private boolean isManaged;

    /** The is key. */
    private boolean isKey;

    /** The is not null. */
    private boolean isNotNull;

    /**
     * Instantiates a new association symbol.
     *
     * @param name the name
     */
    public AssociationSymbol(String name) {
        super(name);
    }

    /**
     * Instantiates a new association symbol.
     *
     * @param name the name
     * @param scope the scope
     */
    public AssociationSymbol(String name, Scope scope) {
        super(name, scope);
    }

    /**
     * Gets the cardinality.
     *
     * @return the cardinality
     */
    public CardinalityEnum getCardinality() {
        return cardinality;
    }

    /**
     * Sets the cardinality.
     *
     * @param cardinality the new cardinality
     */
    public void setCardinality(CardinalityEnum cardinality) {
        this.cardinality = cardinality;
    }

    /**
     * Gets the foreign keys.
     *
     * @return the foreign keys
     */
    public List<EntityElementSymbol> getForeignKeys() {
        return foreignKeys;
    }

    /**
     * Sets the foreign keys.
     *
     * @param foreignKeys the new foreign keys
     */
    public void setForeignKeys(List<EntityElementSymbol> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    /**
     * Gets the target.
     *
     * @return the target
     */
    public EntitySymbol getTarget() {
        return target;
    }

    /**
     * Sets the target.
     *
     * @param target the new target
     */
    public void setTarget(EntitySymbol target) {
        this.target = target;
    }

    /**
     * Adds the foreign key.
     *
     * @param elementSymbol the element symbol
     */
    public void addForeignKey(EntityElementSymbol elementSymbol) {
        this.foreignKeys.add(elementSymbol);
    }

    /**
     * Checks if is managed.
     *
     * @return true, if is managed
     */
    public boolean isManaged() {
        return isManaged;
    }

    /**
     * Sets the managed.
     *
     * @param managed the new managed
     */
    public void setManaged(boolean managed) {
        isManaged = managed;
    }

    /**
     * Checks if is not null.
     *
     * @return true, if is not null
     */
    public boolean isNotNull() {
        return isNotNull;
    }

    /**
     * Sets the not null.
     *
     * @param notNull the new not null
     */
    public void setNotNull(boolean notNull) {
        isNotNull = notNull;
    }

    /**
     * Checks if is key.
     *
     * @return true, if is key
     */
    public boolean isKey() {
        return isKey;
    }

    /**
     * Sets the key.
     *
     * @param key the new key
     */
    public void setKey(boolean key) {
        isKey = key;
    }
}
