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
// Generated from com/codbex/kronos/parser/hdbschema/core/Hdbschema.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.hdbschema.core;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * This class provides an empty implementation of {@link HdbschemaListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 *
 */
public class HdbschemaBaseListener implements HdbschemaListener {
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterHdbschemaDefinition(HdbschemaParser.HdbschemaDefinitionContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitHdbschemaDefinition(HdbschemaParser.HdbschemaDefinitionContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void enterSchemaNameProp(HdbschemaParser.SchemaNamePropContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * The default implementation does nothing.
	 */
	@Override public void exitSchemaNameProp(HdbschemaParser.SchemaNamePropContext ctx) { }

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