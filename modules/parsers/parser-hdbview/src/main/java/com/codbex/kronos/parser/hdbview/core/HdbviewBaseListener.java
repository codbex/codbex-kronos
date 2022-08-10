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

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * This class provides an empty implementation of {@link HdbviewListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 *
 */
public class HdbviewBaseListener implements HdbviewListener {
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterHdbviewDefinition(HdbviewParser.HdbviewDefinitionContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitHdbviewDefinition(HdbviewParser.HdbviewDefinitionContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterProperty(HdbviewParser.PropertyContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitProperty(HdbviewParser.PropertyContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterSchemaProp(HdbviewParser.SchemaPropContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitSchemaProp(HdbviewParser.SchemaPropContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterPublicProp(HdbviewParser.PublicPropContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitPublicProp(HdbviewParser.PublicPropContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterQueryProp(HdbviewParser.QueryPropContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitQueryProp(HdbviewParser.QueryPropContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterDependsOnProp(HdbviewParser.DependsOnPropContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitDependsOnProp(HdbviewParser.DependsOnPropContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterDependsOnTable(HdbviewParser.DependsOnTableContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitDependsOnTable(HdbviewParser.DependsOnTableContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterDependsOnView(HdbviewParser.DependsOnViewContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitDependsOnView(HdbviewParser.DependsOnViewContext ctx) { }

	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterEveryRule(ParserRuleContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void visitTerminal(TerminalNode node) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void visitErrorNode(ErrorNode node) { }
}