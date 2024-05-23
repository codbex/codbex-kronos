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
package com.codbex.kronos.parser.hana.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.codbex.kronos.parser.hana.exceptions.ProcedureMissingPropertyException;

/**
 * The Class ProcedureDefinitionModel.
 */
public class ProcedureDefinitionModel extends DefinitionModel {

    /** The update statements. */
    private List<UpdateStatementDefinitionModel> updateStatements;

    /**
     * Instantiates a new procedure definition model.
     *
     * @param schema the schema
     * @param name the name
     */
    public ProcedureDefinitionModel(String schema, String name) {
        super(schema, name);
        this.updateStatements = new ArrayList<>();
    }

    /**
     * Gets the update statements.
     *
     * @return the update statements
     */
    public List<UpdateStatementDefinitionModel> getUpdateStatements() {
        return updateStatements;
    }

    /**
     * Adds the update statement.
     *
     * @param updateStatement the update statement
     */
    public void addUpdateStatement(UpdateStatementDefinitionModel updateStatement) {
        this.updateStatements.add(updateStatement);
    }

    /**
     * Check for all mandatory fields presence.
     */
    public void checkForAllMandatoryFieldsPresence() {
        checkPresence(this.getName(), "name");
    }

    /**
     * Check presence.
     *
     * @param <T> the generic type
     * @param field the field
     * @param fieldName the field name
     */
    private <T> void checkPresence(T field, String fieldName) {
        if (Objects.isNull(field)) {
            throw new ProcedureMissingPropertyException("Missing mandatory field " + fieldName);
        }
    }
}
