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
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HdbschemaParser}.
 *
 */
public interface HdbschemaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HdbschemaParser#hdbschemaDefinition}.
	 * @param ctx the parse tree
	 */
	void enterHdbschemaDefinition(HdbschemaParser.HdbschemaDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HdbschemaParser#hdbschemaDefinition}.
	 * @param ctx the parse tree
	 */
	void exitHdbschemaDefinition(HdbschemaParser.HdbschemaDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HdbschemaParser#schemaNameProp}.
	 * @param ctx the parse tree
	 */
	void enterSchemaNameProp(HdbschemaParser.SchemaNamePropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HdbschemaParser#schemaNameProp}.
	 * @param ctx the parse tree
	 */
	void exitSchemaNameProp(HdbschemaParser.SchemaNamePropContext ctx);
}