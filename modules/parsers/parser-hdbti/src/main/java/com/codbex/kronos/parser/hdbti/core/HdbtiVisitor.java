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
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HdbtiParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HdbtiVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#importArr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportArr(HdbtiParser.ImportArrContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#objConfig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjConfig(HdbtiParser.ObjConfigContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#assignExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpression(HdbtiParser.AssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#assignTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignTable(HdbtiParser.AssignTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#assignSchema}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignSchema(HdbtiParser.AssignSchemaContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#assignFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignFile(HdbtiParser.AssignFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#assignHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignHeader(HdbtiParser.AssignHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#assignUseHeaderNames}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignUseHeaderNames(HdbtiParser.AssignUseHeaderNamesContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#assignDelimField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignDelimField(HdbtiParser.AssignDelimFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#assignDelimEnclosing}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignDelimEnclosing(HdbtiParser.AssignDelimEnclosingContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#assignDistinguishEmptyFromNull}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignDistinguishEmptyFromNull(HdbtiParser.AssignDistinguishEmptyFromNullContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#assignKeys}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignKeys(HdbtiParser.AssignKeysContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#keyArr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyArr(HdbtiParser.KeyArrContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair(HdbtiParser.PairContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#pairKey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairKey(HdbtiParser.PairKeyContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#pairValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairValue(HdbtiParser.PairValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link HdbtiParser#tableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableName(HdbtiParser.TableNameContext ctx);
}