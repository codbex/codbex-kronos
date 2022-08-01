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
package com.codbex.kronos.parser.hdbdd.factory;

import com.codbex.kronos.parser.hdbdd.core.CdsParser.AssociationContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.ContextRuleContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.DataTypeRuleContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.ElementDeclRuleContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.EntityRuleContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.FieldDeclRuleContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.IdentifierContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.StructuredTypeRuleContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.ViewRuleContext;
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
import com.codbex.kronos.parser.hdbdd.util.HdbddUtils;
import org.antlr.v4.runtime.Token;

public class SymbolFactory {

  public Symbol getSymbol(ContextRuleContext contextRuleContext, Scope currentScope, String schema) {
    return getSymbol(currentScope, schema, contextRuleContext.artifactName, contextRuleContext.artifactType);
  }

  public Symbol getSymbol(StructuredTypeRuleContext structuredTypeRuleContext, Scope currentScope, String schema) {
    return getSymbol(currentScope, schema, structuredTypeRuleContext.artifactName, structuredTypeRuleContext.artifactType);
  }

  public Symbol getSymbol(EntityRuleContext entityRuleContext, Scope currentScope, String schema) {
    return getSymbol(currentScope, schema, entityRuleContext.artifactName, entityRuleContext.artifactType);
  }

  public Symbol getSymbol(ViewRuleContext viewRuleContext, Scope currentScope, String schema) {
    return getSymbol(currentScope, schema, viewRuleContext.artifactName, viewRuleContext.artifactType);
  }

  private Symbol getSymbol(Scope currentScope, String schema, IdentifierContext artifactName, Token artifactType) {
    String symbolId = HdbddUtils.processEscapedSymbolName(artifactName.getText());
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

  public DataTypeSymbol getDataTypeSymbol(DataTypeRuleContext ctx, Scope currentScope, String schema) {
    String typeId = HdbddUtils.processEscapedSymbolName(ctx.artifactName.getText());
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

  public EntityElementSymbol getEntityElementSymbol(ElementDeclRuleContext ctx, Scope currentScope) {
    String elementId = HdbddUtils.processEscapedSymbolName(ctx.name.getText());
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

  public FieldSymbol getFieldSymbol(FieldDeclRuleContext ctx, Scope currentScope) {
    String filedId = HdbddUtils.processEscapedSymbolName(ctx.identifier().getText());
    checkForDuplicateName(filedId, currentScope, ctx.identifier().start.getLine());
    FieldSymbol fieldSymbol = new FieldSymbol(filedId, currentScope);
    fieldSymbol.setIdToken(ctx.identifier());

    return fieldSymbol;
  }

  public AssociationSymbol getAssociationSymbol(AssociationContext ctx, Scope currentScope) {
    String associationId = HdbddUtils.processEscapedSymbolName(ctx.ascId.getText());
    checkForDuplicateName(associationId, currentScope, ctx.ascId.start.getLine());

    AssociationSymbol associationSymbol = new AssociationSymbol(associationId, currentScope);
    associationSymbol.setIdToken(ctx.ascId);
    currentScope.define(associationSymbol);

    if (ctx.cardinality() == null) {
      associationSymbol.setCardinality(CardinalityEnum.ONE_TO_ONE);
    }

    return associationSymbol;
  }

  private Symbol createSymbol(String symbolId, Scope currentScope, String schema, IdentifierContext idToken) {
    Symbol symbol = new Symbol(symbolId, currentScope);
    symbol.setIdToken(idToken);
    symbol.setSchema(schema);

    return symbol;
  }

  private void checkForDuplicateName(String contextId, Scope currentScope, int line) {
    if (currentScope.isDuplicateName(contextId)) {
      throw new CDSRuntimeException(String.format("Error at line: %s  - Duplicate name for: %s", line, contextId));
    }
  }

  private SymbolTypeEnum parseToSymbolTypeEnum(Token artifactType) {
    try {
      return SymbolTypeEnum.valueOf(artifactType.getText().toUpperCase());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new CDSRuntimeException(
          String.format("Error at line: %s  - '%s' is not a valid artifact type.", artifactType.getLine(), artifactType.getText()));
    }
  }
}
