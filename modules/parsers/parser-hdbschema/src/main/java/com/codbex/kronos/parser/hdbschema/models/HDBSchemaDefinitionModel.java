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
package com.codbex.kronos.parser.hdbschema.models;

import java.util.Objects;

/**
 * The Class HDBSchemaDefinitionModel.
 */
public class HDBSchemaDefinitionModel {

    /** The schema name. */
    private String schemaName;

    /**
     * Instantiates a new HDB schema definition model.
     */
    public HDBSchemaDefinitionModel() {}

    /**
     * Gets the schema name.
     *
     * @return the schema name
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets the schema name.
     *
     * @param schemaName the new schema name
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    /**
     * Equals.
     *
     * @param o the o
     * @return true, if successful
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HDBSchemaDefinitionModel that = (HDBSchemaDefinitionModel) o;
        return Objects.equals(schemaName, that.schemaName);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(schemaName);
    }
}
