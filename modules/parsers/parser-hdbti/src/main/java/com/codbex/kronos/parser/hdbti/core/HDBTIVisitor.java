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
package com.codbex.kronos.parser.hdbti.core;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HDBTIParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HDBTIVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#importArr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportArr(HDBTIParser.ImportArrContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#objConfig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjConfig(HDBTIParser.ObjConfigContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#assignExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpression(HDBTIParser.AssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#assignTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignTable(HDBTIParser.AssignTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#assignSchema}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignSchema(HDBTIParser.AssignSchemaContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#assignFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignFile(HDBTIParser.AssignFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#assignHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignHeader(HDBTIParser.AssignHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#assignUseHeaderNames}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignUseHeaderNames(HDBTIParser.AssignUseHeaderNamesContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#assignDelimField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignDelimField(HDBTIParser.AssignDelimFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#assignDelimEnclosing}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignDelimEnclosing(HDBTIParser.AssignDelimEnclosingContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#assignDistinguishEmptyFromNull}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignDistinguishEmptyFromNull(HDBTIParser.AssignDistinguishEmptyFromNullContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#assignKeys}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignKeys(HDBTIParser.AssignKeysContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#keyArr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyArr(HDBTIParser.KeyArrContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair(HDBTIParser.PairContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#pairKey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairKey(HDBTIParser.PairKeyContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#pairValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairValue(HDBTIParser.PairValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link HDBTIParser#tableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableName(HDBTIParser.TableNameContext ctx);
}