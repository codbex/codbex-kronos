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
package com.codbex.kronos.parser.hdbsequence.custom;

import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceBaseVisitor;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceParser;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceParser.DependsOnTableContext;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceParser.DependsOnViewContext;
import com.codbex.kronos.parser.hdbsequence.exceptions.HDBSequenceDuplicatePropertyException;
import com.codbex.kronos.parser.hdbsequence.utils.HDBSequenceConstants;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashSet;
import java.util.List;


/**
 * The Class HdbsequenceVisitor.
 */
public class HdbsequenceVisitor extends HdbsequenceBaseVisitor<JsonElement> {

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
  public JsonElement visitHdbsequence(@NotNull HdbsequenceParser.HdbsequenceContext ctx) {
    JsonObject parsedObj = new JsonObject();
    List<ParseTree> ctxList = ctx.children;
    for (ParseTree tree : ctxList) {
      if (tree.getChild(0) instanceof HdbsequenceParser.SchemaContext) {
        parsedObj.add(HDBSequenceConstants.SCHEMA_PROPERTY, visitSchema((HdbsequenceParser.SchemaContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof HdbsequenceParser.PubliccContext) {
        parsedObj.add(HDBSequenceConstants.PUBLIC_PROPERTY, visitPublicc((HdbsequenceParser.PubliccContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof HdbsequenceParser.MaxvalueContext) {
        parsedObj.add(HDBSequenceConstants.MAXVALUE_PROPERTY, visitMaxvalue((HdbsequenceParser.MaxvalueContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof HdbsequenceParser.NomaxvalueContext) {
        parsedObj.add(HDBSequenceConstants.NOMAXVALUE_PROPERTY, visitNomaxvalue((HdbsequenceParser.NomaxvalueContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof HdbsequenceParser.NominvalueContext) {
        parsedObj.add(HDBSequenceConstants.NOMINVALUE_PROPERTY, visitNominvalue((HdbsequenceParser.NominvalueContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof HdbsequenceParser.CyclesContext) {
        parsedObj.add(HDBSequenceConstants.CYCLES_PROPERTY, visitCycles((HdbsequenceParser.CyclesContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof HdbsequenceParser.MinvalueContext) {
        parsedObj.add(HDBSequenceConstants.MINVALUE_PROPERTY, visitMinvalue((HdbsequenceParser.MinvalueContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof HdbsequenceParser.Reset_byContext) {
        parsedObj.add(HDBSequenceConstants.RESET_BY_PROPERTY, visitReset_by((HdbsequenceParser.Reset_byContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof HdbsequenceParser.Increment_byContext) {
        parsedObj
            .add(HDBSequenceConstants.INCREMENT_BY_PROPERTY, visitIncrement_by((HdbsequenceParser.Increment_byContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof HdbsequenceParser.Start_withContext) {
        parsedObj.add(HDBSequenceConstants.START_WITH_PROPERTY, visitStart_with((HdbsequenceParser.Start_withContext) tree.getChild(0)));
     } else if (tree.getChild(0) instanceof HdbsequenceParser.DependsOnTableContext) {
        parsedObj.add(HDBSequenceConstants.DEPENDS_ON_TABLE_PROPERTY, visitDependsOnTable((HdbsequenceParser.DependsOnTableContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof HdbsequenceParser.DependsOnViewContext) {
        parsedObj.add(HDBSequenceConstants.DEPENDS_ON_VIEW_PROPERTY, visitDependsOnView((HdbsequenceParser.DependsOnViewContext) tree.getChild(0)));
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
  public JsonElement visitSchema(@NotNull HdbsequenceParser.SchemaContext ctx) {
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
  public JsonElement visitPublicc(@NotNull HdbsequenceParser.PubliccContext ctx) {
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
  public JsonElement visitMaxvalue(HdbsequenceParser.MaxvalueContext ctx) {
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
  public JsonElement visitNomaxvalue(HdbsequenceParser.NomaxvalueContext ctx) {
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
  public JsonElement visitNominvalue(HdbsequenceParser.NominvalueContext ctx) throws HDBSequenceDuplicatePropertyException {
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
  public JsonElement visitCycles(HdbsequenceParser.CyclesContext ctx) throws HDBSequenceDuplicatePropertyException {
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
  public JsonElement visitMinvalue(HdbsequenceParser.MinvalueContext ctx) throws HDBSequenceDuplicatePropertyException {
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
  public JsonElement visitReset_by(HdbsequenceParser.Reset_byContext ctx) throws HDBSequenceDuplicatePropertyException {
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
  public JsonElement visitIncrement_by(HdbsequenceParser.Increment_byContext ctx) throws HDBSequenceDuplicatePropertyException {
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
  public JsonElement visitStart_with(HdbsequenceParser.Start_withContext ctx) throws HDBSequenceDuplicatePropertyException {
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
