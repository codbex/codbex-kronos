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
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HdbschemaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HdbschemaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HdbschemaParser#hdbschemaDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHdbschemaDefinition(HdbschemaParser.HdbschemaDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbschemaParser#schemaNameProp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaNameProp(HdbschemaParser.SchemaNamePropContext ctx);
}