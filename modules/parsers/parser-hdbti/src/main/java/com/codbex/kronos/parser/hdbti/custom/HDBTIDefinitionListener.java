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
package com.codbex.kronos.parser.hdbti.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.codbex.kronos.parser.hdbti.core.HDBTIBaseListener;
import com.codbex.kronos.parser.hdbti.core.HDBTIParser.*;
import com.codbex.kronos.parser.hdbti.exception.DuplicateFieldNameException;
import com.codbex.kronos.parser.hdbti.exception.TablePropertySyntaxException;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportConfigModel;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportModel;

/**
 * The listener interface for receiving HDBTICore events.
 * The class that is interested in processing a HDBTICore
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addHDBTICoreListener</code> method. When
 * the HDBTICore event occurs, that object's appropriate
 * method is invoked.
 *
 */
public class HDBTIDefinitionListener extends HDBTIBaseListener {

  /** The import model. */
  private final HDBTIImportModel importModel = new HDBTIImportModel();
  
  /** The used fields. */
  private final Set<String> usedFields = new HashSet<>();
  
  /** The config model. */
  private HDBTIImportConfigModel configModel;

  /**
   * Enter obj config.
   *
   * @param ctx the ctx
   */
  @Override
  public void enterObjConfig(ObjConfigContext ctx) {
    configModel = new HDBTIImportConfigModel();
  }

  /**
   * Exit obj config.
   *
   * @param ctx the ctx
   */
  @Override
  public void exitObjConfig(ObjConfigContext ctx) {
    List<HDBTIImportConfigModel.Pair> pairs = new ArrayList<>();

    configModel.setUseHeaderNames(false);
    configModel.setHeader(false);
    configModel.setDistinguishEmptyFromNull(true);

    for (AssignExpressionContext expressionContext :
        ctx.assignExpression()) {
      if (expressionContext.assignTable() != null) {
        String tableName = expressionContext.assignTable().STRING().getText();
        configModel.setTableName(handleStringLiteral(tableName));
      } else if (expressionContext.assignSchema() != null) {
        String schemaName = expressionContext.assignSchema().STRING().getText();
        configModel.setSchemaName(handleStringLiteral(schemaName));
      } else if (expressionContext.assignFile() != null) {
        String fileName = expressionContext.assignFile().STRING().getText();
        configModel.setFileName(handleStringLiteral(fileName));
      } else if (expressionContext.assignHeader() != null) {
        String header = expressionContext.assignHeader().BOOLEAN().getText();
        configModel.setHeader(Boolean.parseBoolean(header));
      } else if (expressionContext.assignUseHeaderNames() != null) {
        String useHeaderNames = expressionContext.assignUseHeaderNames().BOOLEAN().getText();
        configModel.setUseHeaderNames(Boolean.parseBoolean(useHeaderNames));
      } else if (expressionContext.assignDelimField() != null) {
        String delimField = expressionContext.assignDelimField().STRING().getText();
        configModel.setDelimField(handleStringLiteral(delimField));
      } else if (expressionContext.assignDelimEnclosing() != null) {
        String delimEnclosing = expressionContext.assignDelimEnclosing().STRING().toString();
        configModel.setDelimEnclosing(handleStringLiteral(delimEnclosing));
      } else if (expressionContext.assignDistinguishEmptyFromNull() != null) {
        String distinguishEmptyFromNull = expressionContext.assignDistinguishEmptyFromNull().BOOLEAN().getText();
        configModel.setDistinguishEmptyFromNull(Boolean.parseBoolean(distinguishEmptyFromNull));
      } else if (expressionContext.assignKeys() != null) {
        List<HDBTIImportConfigModel.Pair> pairsToBeAdd = new ArrayList<>();
        expressionContext.assignKeys().keyArr().pair().forEach(el -> {
          if (pairs.isEmpty()) {
            HDBTIImportConfigModel.Pair newPair = new HDBTIImportConfigModel.Pair(handleStringLiteral(el.pairKey().getText()),
                new ArrayList<>(Collections.singletonList(handleStringLiteral(el.pairValue().getText()))));
            pairs.add(newPair);
          } else {
            for (HDBTIImportConfigModel.Pair pair : pairs) {
              if (pair.getColumn().equals(handleStringLiteral(el.pairKey().getText()))) {
                pair.getValues().add(handleStringLiteral(el.pairValue().getText()));
              } else {
                HDBTIImportConfigModel.Pair newPair = new HDBTIImportConfigModel.Pair(handleStringLiteral(el.pairKey().getText()),
                    new ArrayList<>(Collections.singletonList(handleStringLiteral(el.pairValue().getText()))));
                pairsToBeAdd.add(newPair);
              }
            }
          }
          pairs.addAll(pairsToBeAdd);
        });
      }
    }

    configModel.setKeys(pairs);
    importModel.getConfigModels().add(configModel);
    usedFields.clear();
  }

  /**
   * Enter assign expression.
   *
   * @param ctx the ctx
   */
  @Override
  public void enterAssignExpression(AssignExpressionContext ctx) {
    String currentObjField = ctx.getChild(0).getChild(0).getText();

    if (currentObjField.equals("table") || currentObjField.equals("hdbtable") || currentObjField.equals("cdstable")) {
      currentObjField = "table";
    }

    if (usedFields.contains(currentObjField)) {
      if (currentObjField.equals("table")) {
        throw new DuplicateFieldNameException(
            String.format("Invalid combination of table declarations found, you may only use one of [cdstable | hdbtable | table]."));
      } else {
        throw new DuplicateFieldNameException(String.format("Duplicate fields: Field with name: '%s' is already in use.", currentObjField));
      }
    } else {
      usedFields.add(currentObjField);
    }
  }

  /**
   * Gets the import model.
   *
   * @return the import model
   */
  public HDBTIImportModel getImportModel() {
    return importModel;
  }

  /**
   * Enter assign table.
   *
   * @param ctx the ctx
   */
  @Override
  public void enterAssignTable(AssignTableContext ctx) {
    String tableProperty = handleStringLiteral(ctx.STRING().getText());

    if (!isCorrectTablePropertySyntax(tableProperty)) {
      throw new TablePropertySyntaxException(String.format("Table property contains unsupported symbols: %s", tableProperty));
    }
  }

  /**
   * Check if the table property has proper syntax.
   *
   * @param tableProperty the table property
   * @return true, if is correct table property syntax
   * @throws IllegalArgumentException the illegal argument exception
   */
  public static boolean isCorrectTablePropertySyntax(String tableProperty) throws IllegalArgumentException {
    String regex = new String();

    if (tableProperty.contains("::")) {
      regex = "^[A-Za-z0-9_\\-$.]+::[A-Za-z0-9_\\-$.]+$";
    } else {
      regex = "^[A-Za-z0-9_\\-$.]+$";
    }

    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(tableProperty.trim());

    return matcher.find();
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
    return value;
  }
}
