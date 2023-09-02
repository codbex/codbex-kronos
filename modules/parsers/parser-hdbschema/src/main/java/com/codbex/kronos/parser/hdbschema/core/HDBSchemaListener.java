/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
// Generated from java-escape by ANTLR 4.11.1
package com.codbex.kronos.parser.hdbschema.core;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HDBSchemaParser}.
 */
public interface HDBSchemaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HDBSchemaParser#hdbSchemaDefinition}.
	 * @param ctx the parse tree
	 */
	void enterHdbSchemaDefinition(HDBSchemaParser.HdbSchemaDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSchemaParser#hdbSchemaDefinition}.
	 * @param ctx the parse tree
	 */
	void exitHdbSchemaDefinition(HDBSchemaParser.HdbSchemaDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSchemaParser#schemaNameProp}.
	 * @param ctx the parse tree
	 */
	void enterSchemaNameProp(HDBSchemaParser.SchemaNamePropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSchemaParser#schemaNameProp}.
	 * @param ctx the parse tree
	 */
	void exitSchemaNameProp(HDBSchemaParser.SchemaNamePropContext ctx);
}