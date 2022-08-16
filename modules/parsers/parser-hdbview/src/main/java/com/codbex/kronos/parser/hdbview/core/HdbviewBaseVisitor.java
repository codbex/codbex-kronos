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
// Generated from com/codbex/kronos/parser/hdbview/core/Hdbview.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.hdbview.core;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link HdbviewVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public class HdbviewBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements HdbviewVisitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitHdbviewDefinition(HdbviewParser.HdbviewDefinitionContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitProperty(HdbviewParser.PropertyContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitSchemaProp(HdbviewParser.SchemaPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitPublicProp(HdbviewParser.PublicPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitQueryProp(HdbviewParser.QueryPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitDependsOnProp(HdbviewParser.DependsOnPropContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitDependsOnTable(HdbviewParser.DependsOnTableContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 */
	@Override public T visitDependsOnView(HdbviewParser.DependsOnViewContext ctx) { return visitChildren(ctx); }
}