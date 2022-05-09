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
package com.codbex.kronos.parser.hdbview.custom;

import com.codbex.kronos.parser.hdbview.core.HdbviewBaseListener;
import com.codbex.kronos.parser.hdbview.core.HdbviewParser;
import com.codbex.kronos.parser.hdbview.models.XSKHDBVIEWDefinitionModel;
import java.util.stream.Collectors;

public class XSKHDBVIEWCoreListener extends HdbviewBaseListener {

  private final XSKHDBVIEWDefinitionModel model = new XSKHDBVIEWDefinitionModel();

  @Override
  public void exitHdbviewDefinition(HdbviewParser.HdbviewDefinitionContext ctx) {
    ctx.property().forEach(node -> {
      if (node.schemaProp() != null) {
        model.setSchema(handleStringLiteral(node.schemaProp().STRING().getText()));
      }
      if (node.queryProp() != null) {
        model.setQuery(handleStringLiteral(node.queryProp().STRING().getText()));
      }
      if (node.dependsOnProp() != null) {
        model
            .setDependsOn(node.dependsOnProp().STRING().stream().map(el -> handleStringLiteral(el.getText())).collect(Collectors.toList()));
      }
      if (node.dependsOnTable() != null) {
        model.setDependsOnTable(
            node.dependsOnTable().STRING().stream().map(el -> handleStringLiteral(el.getText())).collect(Collectors.toList()));
      }
      if (node.dependsOnView() != null) {
        model.setDependsOnView(
            node.dependsOnView().STRING().stream().map(el -> handleStringLiteral(el.getText())).collect(Collectors.toList()));
      }
      if (node.publicProp() != null) {
        model.setPublic(Boolean.parseBoolean(node.publicProp().getText()));
      } else {
        model.setPublic(true);
      }
    });
  }

  private String handleStringLiteral(String value) {
    if (value != null && value.length() > 1) {
      String subStr = value.substring(1, value.length() - 1);
      String escapedQuote = subStr.replace("\\\"", "\"");//escaped double quote
      escapedQuote = escapedQuote.replace("\\'", "'");//escape single quote
      return escapedQuote.replace("\\\\", "\\");
    }
    return null;
  }

  public XSKHDBVIEWDefinitionModel getModel() {
    return model;
  }
}
