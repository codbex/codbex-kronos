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
package com.codbex.kronos.parser.hdbschema.custom;

import com.codbex.kronos.parser.hdbschema.core.HdbschemaBaseListener;
import com.codbex.kronos.parser.hdbschema.core.HdbschemaParser;
import com.codbex.kronos.parser.hdbschema.models.XSKHDBSCHEMADefinitionModel;

public class XSKHDBSCHEMACoreListener extends HdbschemaBaseListener {

  private XSKHDBSCHEMADefinitionModel model = new XSKHDBSCHEMADefinitionModel();

  @Override
  public void exitHdbschemaDefinition(HdbschemaParser.HdbschemaDefinitionContext ctx) {
    if (ctx.schemaNameProp() != null && ctx.schemaNameProp().STRING() != null) {
      model.setSchemaName(handleStringLiteral(ctx.schemaNameProp().STRING().getText()));
    }
  }

  private String handleStringLiteral(String value) {
    if (value != null && value.length() > 1) {
      String subStr = value.substring(1, value.length() - 1);
      String escapedQuote = subStr.replace("\\\"", "\"");
      return escapedQuote.replace("\\\\", "\\");
    }
    return null;
  }

  public XSKHDBSCHEMADefinitionModel getModel() {
    return model;
  }
}