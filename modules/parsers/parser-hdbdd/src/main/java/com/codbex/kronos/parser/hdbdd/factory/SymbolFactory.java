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
package com.codbex.kronos.parser.hdbdd.factory;

import com.codbex.kronos.parser.hdbdd.core.CdsParser.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

import com.codbex.kronos.parser.hdbdd.exception.CDSRuntimeException;
import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.SymbolTypeEnum;
import com.codbex.kronos.parser.hdbdd.symbols.context.ContextSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import com.codbex.kronos.parser.hdbdd.symbols.entity.AssociationSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.entity.CardinalityEnum;
import com.codbex.kronos.parser.hdbdd.symbols.entity.EntityElementSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.entity.EntitySymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.custom.DataTypeSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.custom.StructuredDataTypeSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.FieldSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.view.ViewSymbol;
import com.codbex.kronos.parser.hdbdd.util.HDBDDUtils;

/**
 * A factory for creating Symbol objects.
 */
public class SymbolFactory {

  /**
   * Gets the symbol.
   *
   * @param contextRuleContext the context rule context
   * @param currentScope the current scope
   * @param schema the schema
   * @return the symbol
   */
  public Symbol getSymbol(ContextRuleContext contextRuleContext, Scope currentScope, String schema) {
    return getSymbol(currentScope, schema, contextRuleContext.artifactName, contextRuleContext.artifactType);
  }

  /**
   * Gets the symbol.
   *
   * @param structuredTypeRuleContext the structured type rule context
   * @param currentScope the current scope
   * @param schema the schema
   * @return the symbol
   */
  public Symbol getSymbol(StructuredTypeRuleContext structuredTypeRuleContext, Scope currentScope, String schema) {
    return getSymbol(currentScope, schema, structuredTypeRuleContext.artifactName, structuredTypeRuleContext.artifactType);
  }

  /**
   * Gets the symbol.
   *
   * @param entityRuleContext the entity rule context
   * @param currentScope the current scope
   * @param schema the schema
   * @return the symbol
   */
  public Symbol getSymbol(EntityRuleContext entityRuleContext, Scope currentScope, String schema) {
    return getSymbol(currentScope, schema, entityRuleContext.artifactName, entityRuleContext.artifactType);
  }

  /**
   * Gets the symbol.
   *
   * @param viewRuleContext the view rule context
   * @param currentScope the current scope
   * @param schema the schema
   * @return the symbol
   */
  public Symbol getSymbol(ViewRuleContext viewRuleContext, Scope currentScope, String schema) {
    return getSymbol(currentScope, schema, viewRuleContext.artifactName, viewRuleContext.artifactType);
  }

  /**
   * Gets the symbol.
   *
   * @param currentScope the current scope
   * @param schema the schema
   * @param artifactName the artifact name
   * @param artifactType the artifact type
   * @return the symbol
   */
  private Symbol getSymbol(Scope currentScope, String schema, IdentifierContext artifactName, Token artifactType) {
    String symbolId = HDBDDUtils.processEscapedSymbolName(artifactName.getText());
    checkForDuplicateName(symbolId, currentScope, artifactName.start.getLine());
    SymbolTypeEnum symbolTypeEnum = parseToSymbolTypeEnum(artifactType);

    Symbol newSymbol = createSymbol(symbolId, currentScope, schema, artifactName);
    switch (symbolTypeEnum) {
      case CONTEXT:
        ContextSymbol contextSymbol = new ContextSymbol(newSymbol);
        currentScope.define(contextSymbol);
        return contextSymbol;
      case ENTITY:
        EntitySymbol entitySymbol = new EntitySymbol(newSymbol);
        currentScope.define(entitySymbol);
        return entitySymbol;
      case TYPE:
        StructuredDataTypeSymbol dataTypeSymbol = new StructuredDataTypeSymbol(newSymbol);
        currentScope.define(dataTypeSymbol);
        return dataTypeSymbol;
      case VIEW:
        ViewSymbol viewSymbol = new ViewSymbol(newSymbol);
        currentScope.define(viewSymbol);
        return viewSymbol;
      default:
        return null;
    }
  }

  /**
   * Gets the data type symbol.
   *
   * @param ctx the ctx
   * @param currentScope the current scope
   * @param schema the schema
   * @return the data type symbol
   */
  public DataTypeSymbol getDataTypeSymbol(DataTypeRuleContext ctx, Scope currentScope, String schema) {
    String typeId = HDBDDUtils.processEscapedSymbolName(ctx.artifactName.getText());
    checkForDuplicateName(typeId, currentScope, ctx.artifactName.start.getLine());
    SymbolTypeEnum symbolTypeEnum = parseToSymbolTypeEnum(ctx.artifactType);
    Symbol newSymbol = createSymbol(typeId, currentScope, schema, ctx.artifactName);
    if (symbolTypeEnum.equals(SymbolTypeEnum.TYPE)) {
      return new DataTypeSymbol(newSymbol);
    }

    throw new CDSRuntimeException(String
        .format("Error at line: %s  - Invalid type provided: '%s' Data type syntax is only allowed for keyword 'type'", ctx.artifactName.start.getLine(),
            ctx.artifactType.getText()));
  }

  /**
   * Gets the entity element symbol.
   *
   * @param ctx the ctx
   * @param currentScope the current scope
   * @return the entity element symbol
   */
  public EntityElementSymbol getEntityElementSymbol(ElementDeclRuleContext ctx, Scope currentScope) {
    String elementId = HDBDDUtils.processEscapedSymbolName(ctx.name.getText());
    checkForDuplicateName(elementId, currentScope, ctx.name.start.getLine());

    EntityElementSymbol elementSymbol = new EntityElementSymbol(elementId, currentScope);
    elementSymbol.setIdToken(ctx.name);
    if (ctx.key != null && !ctx.key.getText().equalsIgnoreCase("key")) {
      throw new CDSRuntimeException(String
          .format("Error at line: %s  - Before an entity element declaration only the 'key' keyword is allowed", ctx.key.start.getLine()));
    }
    boolean isKey = ctx.key != null;
    elementSymbol.setKey(isKey);

    return elementSymbol;
  }

  /**
   * Gets the calculated column symbol.
   *
   * @param ctx the ctx
   * @param currentScope the current scope
   * @return the calculated column symbol
   */
  public EntityElementSymbol getCalculatedColumnSymbol(CalculatedAssociationContext ctx, Scope currentScope) {
    String elementId = HDBDDUtils.processEscapedSymbolName(ctx.ascId.getText());
    checkForDuplicateName(elementId, currentScope, ctx.ascId.start.getLine());

    EntityElementSymbol elementSymbol = new EntityElementSymbol(elementId, currentScope);
    elementSymbol.setIdToken(ctx.ascId);
    elementSymbol.setCalculatedColumn(true);
    elementSymbol.setStatement(getStringWithSpaces(ctx.statement()));

    return elementSymbol;
  }

  /**
   * Gets the field symbol.
   *
   * @param ctx the ctx
   * @param currentScope the current scope
   * @return the field symbol
   */
  public FieldSymbol getFieldSymbol(FieldDeclRuleContext ctx, Scope currentScope) {
    String filedId = HDBDDUtils.processEscapedSymbolName(ctx.identifier().getText());
    checkForDuplicateName(filedId, currentScope, ctx.identifier().start.getLine());
    FieldSymbol fieldSymbol = new FieldSymbol(filedId, currentScope);
    fieldSymbol.setIdToken(ctx.identifier());

    return fieldSymbol;
  }

  /**
   * Gets the association symbol.
   *
   * @param ctx the ctx
   * @param currentScope the current scope
   * @return the association symbol
   */
  public AssociationSymbol getAssociationSymbol(AssociationContext ctx, Scope currentScope) {
    String associationId = HDBDDUtils.processEscapedSymbolName(ctx.ascId.getText());
    checkForDuplicateName(associationId, currentScope, ctx.ascId.start.getLine());

    AssociationSymbol associationSymbol = new AssociationSymbol(associationId, currentScope);
    associationSymbol.setIdToken(ctx.ascId);
    currentScope.define(associationSymbol);

    if (ctx.cardinality() == null) {
      associationSymbol.setCardinality(CardinalityEnum.ONE_TO_ONE);
    }

    return associationSymbol;
  }

  /**
   * Creates a new Symbol object.
   *
   * @param symbolId the symbol id
   * @param currentScope the current scope
   * @param schema the schema
   * @param idToken the id token
   * @return the symbol
   */
  private Symbol createSymbol(String symbolId, Scope currentScope, String schema, IdentifierContext idToken) {
    Symbol symbol = new Symbol(symbolId, currentScope);
    symbol.setIdToken(idToken);
    symbol.setSchema(schema);

    return symbol;
  }

  /**
   * Check for duplicate name.
   *
   * @param contextId the context id
   * @param currentScope the current scope
   * @param line the line
   */
  private void checkForDuplicateName(String contextId, Scope currentScope, int line) {
    if (currentScope.isDuplicateName(contextId)) {
      throw new CDSRuntimeException(String.format("Error at line: %s  - Duplicate name for: %s", line, contextId));
    }
  }

  /**
   * Parses the to symbol type enum.
   *
   * @param artifactType the artifact type
   * @return the symbol type enum
   */
  private SymbolTypeEnum parseToSymbolTypeEnum(Token artifactType) {
    try {
      return SymbolTypeEnum.valueOf(artifactType.getText().toUpperCase());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new CDSRuntimeException(
          String.format("Error at line: %s  - '%s' is not a valid artifact type.", artifactType.getLine(), artifactType.getText()));
    }
  }

  /**
   * Gets the string with spaces.
   *
   * @param ctx the ctx
   * @return the string with spaces
   */
  private String getStringWithSpaces(ParserRuleContext ctx) {
    int startIndex = ctx.start.getStartIndex();
    int stopIndex = ctx.stop.getStopIndex();
    Interval selectedColumnsRuleSqlInterval = new Interval(startIndex, stopIndex);
    return ctx.start.getInputStream().getText(selectedColumnsRuleSqlInterval);
  }
}
