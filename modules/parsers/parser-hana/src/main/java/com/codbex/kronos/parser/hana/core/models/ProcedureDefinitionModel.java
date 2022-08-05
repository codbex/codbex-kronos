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
package com.codbex.kronos.parser.hana.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.codbex.kronos.parser.hana.core.exceptions.ProcedureMissingPropertyException;

public class ProcedureDefinitionModel extends DefinitionModel {

  private List<UpdateStatementDefinitionModel> updateStatements;

  public ProcedureDefinitionModel(String schema, String name) {
    super(schema, name);
    this.updateStatements = new ArrayList<>();
  }

  public List<UpdateStatementDefinitionModel> getUpdateStatements() {
    return updateStatements;
  }

  public void addUpdateStatement(UpdateStatementDefinitionModel updateStatement) {
    this.updateStatements.add(updateStatement);
  }

  public void checkForAllMandatoryFieldsPresence() {
    checkPresence(this.getName(), "name");
  }

  private <T> void checkPresence(T field, String fieldName) {
    if (Objects.isNull(field)) {
      throw new ProcedureMissingPropertyException("Missing mandatory field " + fieldName);
    }
  }
}
