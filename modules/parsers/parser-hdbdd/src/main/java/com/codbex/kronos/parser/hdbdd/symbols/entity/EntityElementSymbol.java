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

import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.FieldSymbol;

/**
 * The Class EntityElementSymbol.
 */
public class EntityElementSymbol extends FieldSymbol {

    /** The default value. */
    private String defaultValue;

    /** The is default value date time function. */
    private boolean isDefaultValueDateTimeFunction;

    /** The is key. */
    private boolean isKey;

    /** The is not null. */
    private boolean isNotNull;

    /** The is calculated column. */
    private boolean isCalculatedColumn;

    /** The statement. */
    private String statement;

    /**
     * Instantiates a new entity element symbol.
     *
     * @param name the name
     * @param scope the scope
     */
    public EntityElementSymbol(String name, Scope scope) {
        super(name, scope);
    }

    /**
     * Instantiates a new entity element symbol.
     *
     * @param entityElementSymbol the entity element symbol
     */
    public EntityElementSymbol(EntityElementSymbol entityElementSymbol) {
        super(entityElementSymbol.getType(), entityElementSymbol.getReference(), entityElementSymbol.getName(),
                entityElementSymbol.getScope(), entityElementSymbol.getIdToken(), entityElementSymbol.getFullName(),
                entityElementSymbol.getAnnotations(), entityElementSymbol.getSchema());
        this.defaultValue = entityElementSymbol.getDefaultValue();
        this.isDefaultValueDateTimeFunction = entityElementSymbol.isDefaultValueDateTimeFunction();
        this.isKey = entityElementSymbol.isKey();
        this.isNotNull = entityElementSymbol.isNotNull();
    }

    /**
     * Gets the default value.
     *
     * @return the default value
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the default value.
     *
     * @param value the new default value
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Checks if is default value date time function.
     *
     * @return true, if is default value date time function
     */
    public boolean isDefaultValueDateTimeFunction() {
        return isDefaultValueDateTimeFunction;
    }

    /**
     * Sets the default value date time function.
     *
     * @param defaultValueDateTimeFunction the new default value date time function
     */
    public void setDefaultValueDateTimeFunction(boolean defaultValueDateTimeFunction) {
        isDefaultValueDateTimeFunction = defaultValueDateTimeFunction;
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

    /**
     * Checks if is calculated column.
     *
     * @return true, if is calculated column
     */
    public boolean isCalculatedColumn() {
        return isCalculatedColumn;
    }

    /**
     * Sets the calculated column.
     *
     * @param calculatedColumn the new calculated column
     */
    public void setCalculatedColumn(boolean calculatedColumn) {
        isCalculatedColumn = calculatedColumn;
    }

    /**
     * Gets the statement.
     *
     * @return the statement
     */
    public String getStatement() {
        return statement;
    }

    /**
     * Sets the statement.
     *
     * @param statement the new statement
     */
    public void setStatement(String statement) {
        this.statement = statement;
    }
}
