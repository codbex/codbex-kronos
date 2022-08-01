/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package models;


import java.util.Objects;

public class HDBProcedureDefinitionModel extends DefinitionModel {

    public HDBProcedureDefinitionModel(String schema, String name) {
        super(schema, name);

    }

    public void checkForAllMandatoryFieldsPresence() {
        checkPresence(this.getName(), "name");
    }

    private <T> void checkPresence(T field, String fieldName) {
        if (Objects.isNull(field)) {
            throw new HDBProcedureMissingPropertyException("Missing mandatory field " + fieldName);
        }
    }
}

