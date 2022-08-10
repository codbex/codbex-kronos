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
package com.codbex.kronos.parser.hdbdd.custom;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import com.codbex.kronos.parser.hdbdd.core.CdsBaseListener;
import com.codbex.kronos.parser.hdbdd.core.CdsLexer;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.AssignTypeContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.AssociationContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.AssociationTargetContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.DefaultValueContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.ForeignKeyContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.IdentifierContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.ManagedForeignKeysContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.UnmanagedForeignKeyContext;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.UsingRuleContext;
import com.codbex.kronos.parser.hdbdd.exception.CDSRuntimeException;
import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.SymbolTable;
import com.codbex.kronos.parser.hdbdd.symbols.context.CDSFileScope;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import com.codbex.kronos.parser.hdbdd.symbols.entity.AssociationSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.entity.CardinalityEnum;
import com.codbex.kronos.parser.hdbdd.symbols.entity.EntityElementSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.entity.EntitySymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.BuiltInTypeSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.Type;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.FieldSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.Typeable;
import com.codbex.kronos.parser.hdbdd.util.HdbddUtils;

/**
 * The listener interface for receiving referenceResolving events.
 * The class that is interested in processing a referenceResolving
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addReferenceResolvingListener</code> method. When
 * the referenceResolving event occurs, that object's appropriate
 * method is invoked.
 *
 */
public class ReferenceResolvingListener extends CdsBaseListener {

  /** The Constant UNMANAGED_ASSOCIATION_MARKER. */
  private static final String UNMANAGED_ASSOCIATION_MARKER = "@";

  /** The symbol table. */
  private SymbolTable symbolTable;
  
  /** The cds file scope. */
  private CDSFileScope cdsFileScope = new CDSFileScope();
  
  /** The entity elements. */
  private ParseTreeProperty<EntityElementSymbol> entityElements;
  
  /** The typeables. */
  private ParseTreeProperty<Typeable> typeables;
  
  /** The associations. */
  private ParseTreeProperty<AssociationSymbol> associations;

  /**
   * Enter using rule.
   *
   * @param ctx the ctx
   */
  @Override
  public void enterUsingRule(UsingRuleContext ctx) {
    String packagePath = ctx.pack.stream().map(IdentifierContext::getText).map(HdbddUtils::processEscapedSymbolName)
        .collect(Collectors.joining("."));
    String memberPath = ctx.members.stream().map(IdentifierContext::getText).map(HdbddUtils::processEscapedSymbolName)
        .collect(Collectors.joining("."));
    String fullSymbolName = packagePath + "::" + memberPath;
    Symbol externalSymbol = this.symbolTable.getSymbol(fullSymbolName);
    if (externalSymbol == null) {
      throw new CDSRuntimeException(String.format(
          "Error at line: %d col: %d. Non existing entity in package: %s::%s",
          ctx.identifier.start.getLine(), ctx.identifier.start.getCharPositionInLine(), packagePath, fullSymbolName));
    }

    if (ctx.alias == null) {
      cdsFileScope.define(externalSymbol);
    } else {
      cdsFileScope.defineWithCustomName(ctx.alias.getText(), externalSymbol);
    }
  }

  /**
   * Enter assign type.
   *
   * @param ctx the ctx
   */
  @Override
  public void enterAssignType(AssignTypeContext ctx) {
    Typeable referencingSymbol = typeables.get(ctx.getParent());
    if (referencingSymbol.getType() != null) {
      return;
    }

    Set<Symbol> nonResolvedRefSymbols = new HashSet<>();
    nonResolvedRefSymbols.add((Symbol) referencingSymbol);
    Symbol resolvedTypeSymbol = resolveReferenceChain(referencingSymbol.getReference(), (Symbol) referencingSymbol, nonResolvedRefSymbols);
    setResolvedType(ctx.TYPE_OF() != null, referencingSymbol, resolvedTypeSymbol);
  }

  /**
   * Enter default value.
   *
   * @param ctx the ctx
   */
  @Override
  public void enterDefaultValue(DefaultValueContext ctx) {
    int valueType = ctx.value.getType();
    BuiltInTypeSymbol typeOfElement = (BuiltInTypeSymbol) this.entityElements.get(ctx.getParent().getParent()).getType();

    // If the default value is set to null, ignore it
    if (ctx.NULL() == null) {
      if (typeOfElement.getValueType().stream().filter(el -> el.equals(valueType)).count() < 1) {
        throw new CDSRuntimeException(String.format(
            "Error at line: %d col: %d. Incompatible types! Expected %s, Provided %s",
            ctx.value.getLine(), ctx.value.getCharPositionInLine(), typeOfElement.getName(), ctx.value.getText()));
      }

      EntityElementSymbol elementSymbol = this.entityElements.get(ctx.getParent().getParent());

      // Check if the default value is a hana datetime function
      if (valueType == CdsLexer.DATETIME_VALUE_FUNCTION) {
        elementSymbol.setDefaultValueDateTimeFunction(true);
      }

      elementSymbol.setDefaultValue(ctx.value.getText());
    }
  }

  /**
   * Enter association target.
   *
   * @param ctx the ctx
   */
  @Override
  public void enterAssociationTarget(AssociationTargetContext ctx) {
    AssociationSymbol associationSymbol = this.associations.get(ctx.getParent());

    Set<Symbol> nonResolvedRefSymbols = new HashSet<>();
    nonResolvedRefSymbols.add(associationSymbol);
    String reference = ctx.pathSubMembers.stream().map(IdentifierContext::getText).map(HdbddUtils::processEscapedSymbolName)
        .collect(Collectors.joining("."));
    associationSymbol.setReference(reference);

    Symbol resolvedSymbol = resolveReferenceChain(reference, associationSymbol, nonResolvedRefSymbols);

    if (!(resolvedSymbol instanceof EntitySymbol) && resolvedSymbol != null) {
      throw new CDSRuntimeException(String.format(
          "Error at line: %d col: %d. The provided reference must be an Entity!",
          resolvedSymbol.getIdToken().start.getLine(), resolvedSymbol.getIdToken().start.getCharPositionInLine()));
    }

    associationSymbol.setTarget((EntitySymbol) resolvedSymbol);
  }

  /**
   * Exit association.
   *
   * @param ctx the ctx
   */
  @Override
  public void exitAssociation(AssociationContext ctx) {
    AssociationSymbol associationSymbol = this.associations.get(ctx);

    if (ctx.getChild(ManagedForeignKeysContext.class, 0) == null
        && ctx.getChild(UnmanagedForeignKeyContext.class, 0) == null
        && associationSymbol.getTarget() != null) {
      List<EntityElementSymbol> associationKeys = associationSymbol.getTarget().getKeys();
      associationSymbol.setForeignKeys(associationKeys);
      associationSymbol.setManaged(true);
    }
  }

  /**
   * Enter foreign key.
   *
   * @param ctx the ctx
   */
  @Override
  public void enterForeignKey(ForeignKeyContext ctx) {
    AssociationSymbol associationSymbol = this.associations.get(ctx.getParent().getParent());
    associationSymbol.setManaged(true);
    String entityName = associationSymbol.getReference();
    String reference = ctx.pathSubMembers.stream().map(IdentifierContext::getText).map(HdbddUtils::processEscapedSymbolName)
        .collect(Collectors.joining("."));
    String refFullPath = entityName + "." + reference;
    Symbol resolvedSymbol = resolveReferenceChain(refFullPath, associationSymbol, new HashSet<>(Arrays.asList(associationSymbol)));

    if (resolvedSymbol == null) {
      throw new CDSRuntimeException(String.format(
          "Error at line: %s. No such element found in entity: %s.",
          associationSymbol.getIdToken().start.getLine(), refFullPath));
    }

    EntityElementSymbol entityElement = new EntityElementSymbol((EntityElementSymbol) resolvedSymbol);

    if (ctx.alias != null) {
      entityElement.setAlias(ctx.alias.getText());
    }

    associationSymbol.addForeignKey(entityElement);
    EntitySymbol entity = (EntitySymbol) associationSymbol.getScope();
    this.symbolTable.addChildToEntity(entity.getFullName(), ((Symbol) resolvedSymbol.getScope()).getFullName());
  }

  /**
   * Enter unmanaged foreign key.
   *
   * @param ctx the ctx
   */
  @Override
  public void enterUnmanagedForeignKey(UnmanagedForeignKeyContext ctx) {
    AssociationSymbol associationSymbol = this.associations.get(ctx.getParent());
    associationSymbol.setManaged(false);
    String targetFullName = associationSymbol.getTarget().getName();
    String reference = ctx.pathSubMembers.stream().skip(1).map(IdentifierContext::getText).map(HdbddUtils::processEscapedSymbolName)
        .collect(Collectors.joining("."));

    String refFullPath = targetFullName + "." + reference;
    Symbol resolvedTargetSymbol = resolveReferenceChain(refFullPath, associationSymbol, new HashSet<>(Arrays.asList(associationSymbol)));
    if (resolvedTargetSymbol == null) {
      throw new CDSRuntimeException(String.format(
          "Error at line: %s. No such element found in entity: %s.",
          associationSymbol.getIdToken().start.getLine(), refFullPath));
    }

    EntitySymbol associationHolder = (EntitySymbol) associationSymbol.getScope();
    EntityElementSymbol resolvedSourcePkElement = (EntityElementSymbol) associationHolder.resolve(ctx.source.getText());
    if (resolvedSourcePkElement == null || !resolvedSourcePkElement.isKey()) {
      throw new CDSRuntimeException(String.format(
          "Error at line: %s. No such element: %s found in: %s or the element is not a primary key.",
          ctx.source.start.getLine(), ctx.source.getText(), associationSymbol.getTarget().getName()));
    }

    AssociationSymbol targetAssociation = new AssociationSymbol(UNMANAGED_ASSOCIATION_MARKER + reference);
    targetAssociation.setManaged(false);
    targetAssociation.setTarget((EntitySymbol) associationSymbol.getScope());
    targetAssociation.setCardinality(CardinalityEnum.ONE_TO_ONE);
    targetAssociation.setScope(resolvedTargetSymbol.getScope());
    targetAssociation.setIdToken(resolvedTargetSymbol.getIdToken());
    targetAssociation.addForeignKey(resolvedSourcePkElement);

    EntitySymbol targetEntity = associationSymbol.getTarget();
    targetEntity.define(targetAssociation);

    this.symbolTable.addChildToEntity(targetEntity.getFullName(), associationHolder.getFullName());
  }

  /**
   * Resolve reference chain.
   *
   * @param reference the reference
   * @param referencingSymbol the referencing symbol
   * @param nonResolvedRefSymbols the non resolved ref symbols
   * @return the symbol
   */
  private Symbol resolveReferenceChain(String reference, Symbol referencingSymbol, Set<Symbol> nonResolvedRefSymbols) {
    String[] splitReference = reference.split("\\.");

    String firstMember = splitReference[0];
    Symbol resolvedSubMember = resolveReference(firstMember, referencingSymbol);

    for (int i = 1; i < splitReference.length; i++) {
      String member = splitReference[i];

      if (resolvedSubMember instanceof BuiltInTypeSymbol) {
        return null;
      } else if (resolvedSubMember instanceof FieldSymbol) {
        Scope scopeToExplore;
        FieldSymbol resolvedSubMemberField = (FieldSymbol) resolvedSubMember;
        Symbol resolvedSubMemberType = resolveSimpleReference(resolvedSubMember, nonResolvedRefSymbols);
        if (resolvedSubMemberField.getType() == null) {
          setResolvedType(true, resolvedSubMemberField, resolvedSubMemberType);
        }

        if (resolvedSubMemberType instanceof Scope) {
          scopeToExplore = (Scope) resolvedSubMemberType;
        } else {
          return null;
        }

        resolvedSubMember = scopeToExplore.resolve(member);

      } else if (resolvedSubMember instanceof Scope) {
        resolvedSubMember = ((Scope) resolvedSubMember).resolve(member);
      }
    }

    isCircularDependency(resolvedSubMember, nonResolvedRefSymbols);

    return resolvedSubMember;
  }

  /**
   * Checks if is circular dependency.
   *
   * @param resolvedSubMember the resolved sub member
   * @param nonResolvedRefSymbols the non resolved ref symbols
   */
  private void isCircularDependency(Symbol resolvedSubMember, Set<Symbol> nonResolvedRefSymbols) {
    if (resolvedSubMember instanceof FieldSymbol) {
      FieldSymbol resolvedSubMemberField = (FieldSymbol) resolvedSubMember;
      Symbol resolvedMemberType = (Symbol) resolvedSubMemberField.getType();

      if (resolvedMemberType == null) {
        if (!nonResolvedRefSymbols.contains(resolvedSubMember)) {
          nonResolvedRefSymbols.add(resolvedSubMemberField);
          resolvedMemberType = resolveReferenceChain(resolvedSubMemberField.getReference(), resolvedSubMemberField, nonResolvedRefSymbols);
          setResolvedType(true, resolvedSubMemberField, resolvedMemberType);
        } else {
          throw new CDSRuntimeException("Circular dependency");
        }
      }
    }
  }

  /**
   * Sets the resolved type.
   *
   * @param isTypeOfUsed the is type of used
   * @param typeable the typeable
   * @param resolvedSymbol the resolved symbol
   */
  private void setResolvedType(boolean isTypeOfUsed, Typeable typeable, Symbol resolvedSymbol) {
    Symbol typeableSymbol = (Symbol) typeable;
    if (resolvedSymbol == null) {
      throw new CDSRuntimeException(
          String.format("Error at line: %s. No such type existing.", typeableSymbol.getIdToken().start.getLine()));
    } else if (resolvedSymbol instanceof EntitySymbol) {
      throw new CDSRuntimeException(
          String.format("Error at line: %s. Entities could not be used as a type.", typeableSymbol.getIdToken().start.getLine()));
    } else if (resolvedSymbol instanceof Type) {
      typeable.setType((Type) resolvedSymbol);
    } else if (resolvedSymbol instanceof Typeable && !isTypeOfUsed) {
      throw new CDSRuntimeException(
          String.format("Error at line: %s. The reference provided is not a type, but a field. Use 'type of' keyword.",
              typeableSymbol.getIdToken().start.getLine()));
    } else if (resolvedSymbol instanceof Typeable) {
      typeable.setType(((Typeable) resolvedSymbol).getType());
    } else if (resolvedSymbol instanceof AssociationSymbol) {
      throw new CDSRuntimeException(String.format("Error at line: %s. Association field is not allowed as a reference, a type is required.",
          typeableSymbol.getIdToken().start.getLine()));
    }
  }

  /**
   * Resolve simple reference.
   *
   * @param resolvedMember the resolved member
   * @param nonResolvedRefSymbols the non resolved ref symbols
   * @return the symbol
   */
  private Symbol resolveSimpleReference(Symbol resolvedMember, Set<Symbol> nonResolvedRefSymbols) {
    FieldSymbol resolvedField = (FieldSymbol) resolvedMember;
    Symbol resolvedFieldType = (Symbol) resolvedField.getType();

    if (resolvedFieldType == null) {
      if (!nonResolvedRefSymbols.contains(resolvedField)) {
        nonResolvedRefSymbols.add(resolvedField);
        return resolveReferenceChain(resolvedField.getReference(), resolvedField, nonResolvedRefSymbols);
      } else {
        throw new CDSRuntimeException(String.format("Circular dependency for field: %s", resolvedField.getName()));
      }
    }

    return resolvedFieldType;
  }

  /**
   * Resolve reference.
   *
   * @param referencedId the referenced id
   * @param referencingSymbol the referencing symbol
   * @return the symbol
   */
  public Symbol resolveReference(String referencedId, Symbol referencingSymbol) {
    if (symbolTable.getGlobalBuiltInTypeScope().resolve(referencedId) != null) {
      return symbolTable.getGlobalBuiltInTypeScope().resolve(referencedId);
    }

    Symbol resolvedTypeSymbol = referencingSymbol.getScope().resolve(referencedId);

    if (resolvedTypeSymbol == null &&
        (referencingSymbol instanceof FieldSymbol || referencingSymbol instanceof AssociationSymbol)) {
      resolvedTypeSymbol = referencingSymbol.getScope().getEnclosingScope().resolve(referencedId);
      if (resolvedTypeSymbol == null) {
        throw new CDSRuntimeException(
            String.format("Error at line: %s. No such type found: %s", referencingSymbol.getIdToken().start.getLine(), referencedId));
      }

      return resolvedTypeSymbol;
    }

    return resolvedTypeSymbol;
  }

  /**
   * Sets the entity elements.
   *
   * @param entityElements the new entity elements
   */
  public void setEntityElements(ParseTreeProperty<EntityElementSymbol> entityElements) {
    this.entityElements = entityElements;
  }

  /**
   * Sets the typeables.
   *
   * @param typeables the new typeables
   */
  public void setTypeables(ParseTreeProperty<Typeable> typeables) {
    this.typeables = typeables;
  }

  /**
   * Sets the associations.
   *
   * @param associations the new associations
   */
  public void setAssociations(ParseTreeProperty<AssociationSymbol> associations) {
    this.associations = associations;
  }

  /**
   * Sets the symbol table.
   *
   * @param symbolTable the new symbol table
   */
  public void setSymbolTable(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  /**
   * Sets the cds file scope.
   *
   * @param cdsFileScope the new cds file scope
   */
  public void setCdsFileScope(CDSFileScope cdsFileScope) {
    this.cdsFileScope = cdsFileScope;
  }
}
