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
// Generated from com/codbex/kronos/parser/hdbtable/core/HDBTable.g4 by ANTLR 4.13.1
package com.codbex.kronos.parser.hdbtable.core;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link HDBTableVisitor}, which can be extended to
 * create a visitor which only needs to handle a subset of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *        type.
 */
@SuppressWarnings("CheckReturnValue")
public class HDBTableBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements HDBTableVisitor<T> {
    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitHdbTableDefinition(HDBTableParser.HdbTableDefinitionContext ctx) {
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
    public T visitHdbTableProperties(HDBTableParser.HdbTablePropertiesContext ctx) {
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
    public T visitSchemaNameProp(HDBTableParser.SchemaNamePropContext ctx) {
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
    public T visitTemporaryProp(HDBTableParser.TemporaryPropContext ctx) {
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
    public T visitTableTypeProp(HDBTableParser.TableTypePropContext ctx) {
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
    public T visitPublicProp(HDBTableParser.PublicPropContext ctx) {
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
    public T visitLoggingTypeProp(HDBTableParser.LoggingTypePropContext ctx) {
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
    public T visitTableColumnsProp(HDBTableParser.TableColumnsPropContext ctx) {
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
    public T visitTableIndexesProp(HDBTableParser.TableIndexesPropContext ctx) {
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
    public T visitTablePrimaryKeyProp(HDBTableParser.TablePrimaryKeyPropContext ctx) {
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
    public T visitTablePrimaryKeyColumnsProp(HDBTableParser.TablePrimaryKeyColumnsPropContext ctx) {
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
    public T visitTablePrimaryKeyIndexTypeProp(HDBTableParser.TablePrimaryKeyIndexTypePropContext ctx) {
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
    public T visitDescriptionProp(HDBTableParser.DescriptionPropContext ctx) {
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
    public T visitColumnsObject(HDBTableParser.ColumnsObjectContext ctx) {
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
    public T visitColumnsProperties(HDBTableParser.ColumnsPropertiesContext ctx) {
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
    public T visitIndexesObject(HDBTableParser.IndexesObjectContext ctx) {
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
    public T visitIndexProperties(HDBTableParser.IndexPropertiesContext ctx) {
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
    public T visitColumnAssignName(HDBTableParser.ColumnAssignNameContext ctx) {
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
    public T visitColumnAssignSQLType(HDBTableParser.ColumnAssignSQLTypeContext ctx) {
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
    public T visitColumnAssignNullable(HDBTableParser.ColumnAssignNullableContext ctx) {
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
    public T visitColumnAssignUnique(HDBTableParser.ColumnAssignUniqueContext ctx) {
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
    public T visitColumnAssignLength(HDBTableParser.ColumnAssignLengthContext ctx) {
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
    public T visitColumnAssignComment(HDBTableParser.ColumnAssignCommentContext ctx) {
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
    public T visitColumnAssignDefaultValue(HDBTableParser.ColumnAssignDefaultValueContext ctx) {
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
    public T visitColumnAssignPrecision(HDBTableParser.ColumnAssignPrecisionContext ctx) {
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
    public T visitColumnAssignScale(HDBTableParser.ColumnAssignScaleContext ctx) {
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
    public T visitIndexAssignName(HDBTableParser.IndexAssignNameContext ctx) {
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
    public T visitIndexAssignUnique(HDBTableParser.IndexAssignUniqueContext ctx) {
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
    public T visitIndexAssignOrder(HDBTableParser.IndexAssignOrderContext ctx) {
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
    public T visitIndexAssignIndexColumns(HDBTableParser.IndexAssignIndexColumnsContext ctx) {
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
    public T visitIndexAssignIndexType(HDBTableParser.IndexAssignIndexTypeContext ctx) {
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
    public T visitIndexColumnsArray(HDBTableParser.IndexColumnsArrayContext ctx) {
        return visitChildren(ctx);
    }
}
