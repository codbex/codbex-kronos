/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
// Generated from com/codbex/kronos/parser/hdbdd/core/Cds.g4 by ANTLR 4.13.1
package com.codbex.kronos.parser.hdbdd.core;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link CdsVisitor}, which can be extended to
 * create a visitor which only needs to handle a subset of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *        type.
 */
@SuppressWarnings("CheckReturnValue")
public class CdsBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements CdsVisitor<T> {
    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitCdsDefinition(CdsParser.CdsDefinitionContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitNamespaceRule(CdsParser.NamespaceRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitUsingRule(CdsParser.UsingRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitTopLevelSymbol(CdsParser.TopLevelSymbolContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitDataTypeRule(CdsParser.DataTypeRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitContextRule(CdsParser.ContextRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitStructuredTypeRule(CdsParser.StructuredTypeRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitEntityRule(CdsParser.EntityRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitViewRule(CdsParser.ViewRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitFieldDeclRule(CdsParser.FieldDeclRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitAssignType(CdsParser.AssignTypeContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitAssignTypeWithArgs(CdsParser.AssignTypeWithArgsContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitElementDeclRule(CdsParser.ElementDeclRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitElementDetails(CdsParser.ElementDetailsContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitElementConstraints(CdsParser.ElementConstraintsContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitAssociationConstraints(CdsParser.AssociationConstraintsContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitConstraints(CdsParser.ConstraintsContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitAssociation(CdsParser.AssociationContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitCalculatedAssociation(CdsParser.CalculatedAssociationContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitStatement(CdsParser.StatementContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitAssociationTarget(CdsParser.AssociationTargetContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitUnmanagedForeignKey(CdsParser.UnmanagedForeignKeyContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitManagedForeignKeys(CdsParser.ManagedForeignKeysContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitForeignKey(CdsParser.ForeignKeyContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitMinMaxCardinality(CdsParser.MinMaxCardinalityContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitMaxCardinality(CdsParser.MaxCardinalityContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitNoCardinality(CdsParser.NoCardinalityContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitDefaultValue(CdsParser.DefaultValueContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitAnnObjectRule(CdsParser.AnnObjectRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitAnnPropertyRule(CdsParser.AnnPropertyRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitAnnMarkerRule(CdsParser.AnnMarkerRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitAnnValue(CdsParser.AnnValueContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitEnumRule(CdsParser.EnumRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitArrRule(CdsParser.ArrRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitObj(CdsParser.ObjContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitKeyValue(CdsParser.KeyValueContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitSelectRule(CdsParser.SelectRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitJoinRule(CdsParser.JoinRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitJoinFields(CdsParser.JoinFieldsContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitSelectedColumnsRule(CdsParser.SelectedColumnsRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitWhereRule(CdsParser.WhereRuleContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitCharacters(CdsParser.CharactersContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitIdentifier(CdsParser.IdentifierContext ctx) {
        return visitChildren(ctx);
    }
}
