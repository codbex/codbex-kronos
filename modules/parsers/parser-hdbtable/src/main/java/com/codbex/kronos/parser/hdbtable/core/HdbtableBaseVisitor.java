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
// Generated from com/codbex/kronos/parser/hdbtable/core/Hdbtable.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.hdbtable.core;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link HdbtableVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public class HdbtableBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements HdbtableVisitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitHdbtableDefinition(HdbtableParser.HdbtableDefinitionContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitHdbtableProperties(HdbtableParser.HdbtablePropertiesContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitSchemaNameProp(HdbtableParser.SchemaNamePropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitTemporaryProp(HdbtableParser.TemporaryPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitTableTypeProp(HdbtableParser.TableTypePropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitPublicProp(HdbtableParser.PublicPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitLoggingTypeProp(HdbtableParser.LoggingTypePropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitTableColumnsProp(HdbtableParser.TableColumnsPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitTableIndexesProp(HdbtableParser.TableIndexesPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitTablePrimaryKeyProp(HdbtableParser.TablePrimaryKeyPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitTablePrimaryKeyColumnsProp(HdbtableParser.TablePrimaryKeyColumnsPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitTablePrimaryKeyIndexTypeProp(HdbtableParser.TablePrimaryKeyIndexTypePropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitDescriptionProp(HdbtableParser.DescriptionPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnsObject(HdbtableParser.ColumnsObjectContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnsProperties(HdbtableParser.ColumnsPropertiesContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitIndexesObject(HdbtableParser.IndexesObjectContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitIndexProperties(HdbtableParser.IndexPropertiesContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnAssignName(HdbtableParser.ColumnAssignNameContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnAssignSQLType(HdbtableParser.ColumnAssignSQLTypeContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnAssignNullable(HdbtableParser.ColumnAssignNullableContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnAssignUnique(HdbtableParser.ColumnAssignUniqueContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnAssignLength(HdbtableParser.ColumnAssignLengthContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnAssignComment(HdbtableParser.ColumnAssignCommentContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnAssignDefaultValue(HdbtableParser.ColumnAssignDefaultValueContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnAssignPrecision(HdbtableParser.ColumnAssignPrecisionContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitColumnAssignScale(HdbtableParser.ColumnAssignScaleContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitIndexAssignName(HdbtableParser.IndexAssignNameContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitIndexAssignUnique(HdbtableParser.IndexAssignUniqueContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitIndexAssignOrder(HdbtableParser.IndexAssignOrderContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitIndexAssignIndexColumns(HdbtableParser.IndexAssignIndexColumnsContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitIndexAssignIndexType(HdbtableParser.IndexAssignIndexTypeContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitIndexColumnsArray(HdbtableParser.IndexColumnsArrayContext ctx) { return visitChildren(ctx); }
}