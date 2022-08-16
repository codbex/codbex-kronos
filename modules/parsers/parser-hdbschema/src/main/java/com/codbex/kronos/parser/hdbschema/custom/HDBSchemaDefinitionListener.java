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

import com.codbex.kronos.parser.hdbschema.core.HDBSchemaBaseListener;
import com.codbex.kronos.parser.hdbschema.core.HDBSchemaParser;
import com.codbex.kronos.parser.hdbschema.models.HDBSchemaDefinitionModel;

/**
 * The listener interface for receiving HDBSchemaCore events.
 * The class that is interested in processing a HDBSchemaCore
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addHDBSchemaCoreListener</code> method. When
 * the HDBSchemaCore event occurs, that object's appropriate
 * method is invoked.
 *
 */
public class HDBSchemaDefinitionListener extends HDBSchemaBaseListener {

  /** The model. */
  private HDBSchemaDefinitionModel model = new HDBSchemaDefinitionModel();

  /**
   * Exit hdbschema definition.
   *
   * @param ctx the ctx
   */
  @Override
  public void exitHdbSchemaDefinition(HDBSchemaParser.HdbSchemaDefinitionContext ctx) {
    if (ctx.schemaNameProp() != null && ctx.schemaNameProp().STRING() != null) {
      model.setSchemaName(handleStringLiteral(ctx.schemaNameProp().STRING().getText()));
    }
  }

  /**
   * Handle string literal.
   *
   * @param value the value
   * @return the string
   */
  private String handleStringLiteral(String value) {
    if (value != null && value.length() > 1) {
      String subStr = value.substring(1, value.length() - 1);
      String escapedQuote = subStr.replace("\\\"", "\"");
      return escapedQuote.replace("\\\\", "\\");
    }
    return null;
  }

  /**
   * Gets the model.
   *
   * @return the model
   */
  public HDBSchemaDefinitionModel getModel() {
    return model;
  }
}