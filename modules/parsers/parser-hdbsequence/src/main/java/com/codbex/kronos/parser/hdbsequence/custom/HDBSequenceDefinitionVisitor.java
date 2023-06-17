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
package com.codbex.kronos.parser.hdbsequence.custom;

import com.codbex.kronos.parser.hdbsequence.core.HDBSequenceBaseVisitor;
import com.codbex.kronos.parser.hdbsequence.core.HDBSequenceParser.*;
import com.codbex.kronos.parser.hdbsequence.exceptions.HDBSequenceDuplicatePropertyException;
import com.codbex.kronos.parser.hdbsequence.utils.HDBSequenceConstants;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashSet;
import java.util.List;


/**
 * The Class HdbsequenceVisitor.
 */
public class HDBSequenceDefinitionVisitor extends HDBSequenceBaseVisitor<JsonElement> {

  /** The visited properties. */
  private HashSet<String> visitedProperties = new HashSet<>();

  /**
   * Check for property repetition.
   *
   * @param property the property
   * @throws HDBSequenceDuplicatePropertyException the HDB sequence duplicate property exception
   */
  private void checkForPropertyRepetition(String property) throws HDBSequenceDuplicatePropertyException {
    if (!visitedProperties.contains(property)) {
      visitedProperties.add(property);
    } else {

      throw new HDBSequenceDuplicatePropertyException(String.format("Property %s is already declared!", property));
    }
  }

  /**
   * Visit hdbsequence.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitHdbSequenceDefinition(HdbSequenceDefinitionContext ctx) {
    JsonObject parsedObj = new JsonObject();
    List<ParseTree> ctxList = ctx.children;
    for (ParseTree tree : ctxList) {
      if (tree.getChild(0) instanceof SchemaContext) {
        parsedObj.add(HDBSequenceConstants.SCHEMA_PROPERTY, visitSchema((SchemaContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof PubliccContext) {
        parsedObj.add(HDBSequenceConstants.PUBLIC_PROPERTY, visitPublicc((PubliccContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof MaxValueContext) {
        parsedObj.add(HDBSequenceConstants.MAXVALUE_PROPERTY, visitMaxValue((MaxValueContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof NoMaxValueContext) {
        parsedObj.add(HDBSequenceConstants.NOMAXVALUE_PROPERTY, visitNoMaxValue((NoMaxValueContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof NoMinValueContext) {
        parsedObj.add(HDBSequenceConstants.NOMINVALUE_PROPERTY, visitNoMinValue((NoMinValueContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof CyclesContext) {
        parsedObj.add(HDBSequenceConstants.CYCLES_PROPERTY, visitCycles((CyclesContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof MinValueContext) {
        parsedObj.add(HDBSequenceConstants.MINVALUE_PROPERTY, visitMinValue((MinValueContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof ResetByContext) {
        parsedObj.add(HDBSequenceConstants.RESET_BY_PROPERTY, visitResetBy((ResetByContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof IncrementByContext) {
        parsedObj
            .add(HDBSequenceConstants.INCREMENT_BY_PROPERTY, visitIncrementBy((IncrementByContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof StartWithContext) {
        parsedObj.add(HDBSequenceConstants.START_WITH_PROPERTY, visitStartWith((StartWithContext) tree.getChild(0)));
     } else if (tree.getChild(0) instanceof DependsOnTableContext) {
        parsedObj.add(HDBSequenceConstants.DEPENDS_ON_TABLE_PROPERTY, visitDependsOnTable((DependsOnTableContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof DependsOnViewContext) {
        parsedObj.add(HDBSequenceConstants.DEPENDS_ON_VIEW_PROPERTY, visitDependsOnView((DependsOnViewContext) tree.getChild(0)));
      }
    }
    return parsedObj;
  }

  /**
   * Visit schema.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitSchema(SchemaContext ctx) {
    checkForPropertyRepetition(HDBSequenceConstants.SCHEMA_PROPERTY);
    return (ctx != null && ctx.STRING() != null)
        ? new JsonPrimitive(ctx.STRING().getText())
        : null;

  }

  /**
   * Visit publicc.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitPublicc(PubliccContext ctx) {
    checkForPropertyRepetition(HDBSequenceConstants.PUBLIC_PROPERTY);
    return (ctx != null && ctx.BOOLEAN() != null)
        ? new JsonPrimitive(Boolean.parseBoolean(ctx.BOOLEAN().getText()))
        : new JsonPrimitive(HDBSequenceConstants.PUBLIC_DEFAULT_VALUE);
  }

  /**
   * Visit maxvalue.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitMaxValue(MaxValueContext ctx) {
    checkForPropertyRepetition(HDBSequenceConstants.MAXVALUE_PROPERTY);
    return (ctx != null && ctx.INT() != null)
        ? new JsonPrimitive(Integer.parseInt(ctx.INT().getText()))
        : null;
  }

  /**
   * Visit nomaxvalue.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitNoMaxValue(NoMaxValueContext ctx) {
    checkForPropertyRepetition(HDBSequenceConstants.NOMAXVALUE_PROPERTY);
    return (ctx != null && ctx.BOOLEAN() != null)
        ? new JsonPrimitive(Boolean.parseBoolean(ctx.BOOLEAN().getText()))
        : new JsonPrimitive(HDBSequenceConstants.NOMAXVALUE_DEFAULT_VALUE);
  }
  
  /**
   * Visit nominvalue.
   *
   * @param ctx the ctx
   * @return the json element
   * @throws HDBSequenceDuplicatePropertyException the HDB sequence duplicate property exception
   */
  @Override
  public JsonElement visitNoMinValue(NoMinValueContext ctx) throws HDBSequenceDuplicatePropertyException {
    checkForPropertyRepetition(HDBSequenceConstants.NOMINVALUE_PROPERTY);
    return (ctx != null && ctx.BOOLEAN() != null)
        ? new JsonPrimitive(Boolean.parseBoolean(ctx.BOOLEAN().getText()))
        : new JsonPrimitive(HDBSequenceConstants.NOMINVALUE_DEFAULT_VALUE);
  }

  /**
   * Visit cycles.
   *
   * @param ctx the ctx
   * @return the json element
   * @throws HDBSequenceDuplicatePropertyException the HDB sequence duplicate property exception
   */
  @Override
  public JsonElement visitCycles(CyclesContext ctx) throws HDBSequenceDuplicatePropertyException {
    checkForPropertyRepetition(HDBSequenceConstants.CYCLES_PROPERTY);
    return (ctx != null && ctx.BOOLEAN() != null)
        ? new JsonPrimitive(Boolean.parseBoolean(ctx.BOOLEAN().getText()))
        : null;
  }

  /**
   * Visit minvalue.
   *
   * @param ctx the ctx
   * @return the json element
   * @throws HDBSequenceDuplicatePropertyException the HDB sequence duplicate property exception
   */
  @Override
  public JsonElement visitMinValue(MinValueContext ctx) throws HDBSequenceDuplicatePropertyException {
    checkForPropertyRepetition(HDBSequenceConstants.MINVALUE_PROPERTY);
    return (ctx != null && ctx.INT() != null)
        ? new JsonPrimitive(Integer.parseInt(ctx.INT().getText()))
        : null;
  }

  /**
   * Visit reset by.
   *
   * @param ctx the ctx
   * @return the json element
   * @throws HDBSequenceDuplicatePropertyException the HDB sequence duplicate property exception
   */
  @Override
  public JsonElement visitResetBy(ResetByContext ctx) throws HDBSequenceDuplicatePropertyException {
    checkForPropertyRepetition(HDBSequenceConstants.RESET_BY_PROPERTY);
    return (ctx != null && ctx.STRING() != null)
        ? new JsonPrimitive(ctx.STRING().getText())
        : null;
  }

  /**
   * Visit increment by.
   *
   * @param ctx the ctx
   * @return the json element
   * @throws HDBSequenceDuplicatePropertyException the HDB sequence duplicate property exception
   */
  @Override
  public JsonElement visitIncrementBy(IncrementByContext ctx) throws HDBSequenceDuplicatePropertyException {
    checkForPropertyRepetition(HDBSequenceConstants.INCREMENT_BY_PROPERTY);
    return (ctx != null && ctx.INT() != null)
        ? new JsonPrimitive(Integer.parseInt(ctx.INT().getText()))
        : new JsonPrimitive(HDBSequenceConstants.INCREMENT_BY_DEFAULT_VALUE);
  }

  /**
   * Visit start with.
   *
   * @param ctx the ctx
   * @return the json element
   * @throws HDBSequenceDuplicatePropertyException the HDB sequence duplicate property exception
   */
  @Override
  public JsonElement visitStartWith(StartWithContext ctx) throws HDBSequenceDuplicatePropertyException {
    checkForPropertyRepetition(HDBSequenceConstants.START_WITH_PROPERTY);
    return (ctx != null && ctx.INT() != null)
        ? new JsonPrimitive(Integer.parseInt(ctx.INT().getText()))
        : new JsonPrimitive(HDBSequenceConstants.START_WITH_DEFAULT_VALUE);
  }

  /**
   * Visit depends on table.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitDependsOnTable(DependsOnTableContext ctx) {
    checkForPropertyRepetition(HDBSequenceConstants.DEPENDS_ON_TABLE_PROPERTY);
    return (ctx != null && ctx.STRING() != null)
            ? new JsonPrimitive(ctx.STRING().getText())
            : null;
  }

  /**
   * Visit depends on view.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitDependsOnView(DependsOnViewContext ctx) {
    checkForPropertyRepetition(HDBSequenceConstants.DEPENDS_ON_VIEW_PROPERTY);
    return (ctx != null && ctx.STRING() != null)
            ? new JsonPrimitive(ctx.STRING().getText())
            : null;
  }
}
