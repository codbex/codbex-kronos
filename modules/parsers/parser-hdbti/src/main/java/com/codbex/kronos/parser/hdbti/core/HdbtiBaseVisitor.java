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
// Generated from com/codbex/kronos/parser/hdbti/core/Hdbti.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.hdbti.core;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link HdbtiVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public class HdbtiBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements HdbtiVisitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitImportArr(HdbtiParser.ImportArrContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitObjConfig(HdbtiParser.ObjConfigContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitAssignExpression(HdbtiParser.AssignExpressionContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitAssignTable(HdbtiParser.AssignTableContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitAssignSchema(HdbtiParser.AssignSchemaContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitAssignFile(HdbtiParser.AssignFileContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitAssignHeader(HdbtiParser.AssignHeaderContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitAssignUseHeaderNames(HdbtiParser.AssignUseHeaderNamesContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitAssignDelimField(HdbtiParser.AssignDelimFieldContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitAssignDelimEnclosing(HdbtiParser.AssignDelimEnclosingContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitAssignDistinguishEmptyFromNull(HdbtiParser.AssignDistinguishEmptyFromNullContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitAssignKeys(HdbtiParser.AssignKeysContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitKeyArr(HdbtiParser.KeyArrContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitPair(HdbtiParser.PairContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitPairKey(HdbtiParser.PairKeyContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitPairValue(HdbtiParser.PairValueContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitTableName(HdbtiParser.TableNameContext ctx) { return visitChildren(ctx); }
}