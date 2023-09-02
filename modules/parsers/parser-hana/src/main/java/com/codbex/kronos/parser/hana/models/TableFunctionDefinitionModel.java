/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.parser.hana.models;

import com.codbex.kronos.parser.hana.exceptions.TableFunctionMissingPropertyException;

/**
 * The Class TableFunctionDefinitionModel.
 */
public class TableFunctionDefinitionModel extends DefinitionModel {


    /**
     * Instantiates a new table function definition model.
     *
     * @param schema the schema
     * @param name the name
     */
    public TableFunctionDefinitionModel(String schema, String name) {
        super(schema, name);
    }

    /**
     * Check for all mandatory fields presence.
     */
    public void checkForAllMandatoryFieldsPresence() {
        if (this.getName() == null) {
            throw new TableFunctionMissingPropertyException("Missing mandatory field name");
        }

    }

}
