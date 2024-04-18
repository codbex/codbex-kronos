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
// Generated from com/codbex/kronos/parser/hdbschema/core/HDBSchema.g4 by ANTLR 4.13.1
package com.codbex.kronos.parser.hdbschema.core;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HDBSchemaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HDBSchemaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HDBSchemaParser#hdbSchemaDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHdbSchemaDefinition(HDBSchemaParser.HdbSchemaDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBSchemaParser#schemaNameProp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaNameProp(HDBSchemaParser.SchemaNamePropContext ctx);
}