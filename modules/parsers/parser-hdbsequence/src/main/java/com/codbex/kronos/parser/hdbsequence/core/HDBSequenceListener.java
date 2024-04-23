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
// Generated from com/codbex/kronos/parser/hdbsequence/core/HDBSequence.g4 by ANTLR 4.13.1
package com.codbex.kronos.parser.hdbsequence.core;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HDBSequenceParser}.
 */
public interface HDBSequenceListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#hdbSequenceDefinition}.
	 * @param ctx the parse tree
	 */
	void enterHdbSequenceDefinition(HDBSequenceParser.HdbSequenceDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#hdbSequenceDefinition}.
	 * @param ctx the parse tree
	 */
	void exitHdbSequenceDefinition(HDBSequenceParser.HdbSequenceDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(HDBSequenceParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(HDBSequenceParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#schema}.
	 * @param ctx the parse tree
	 */
	void enterSchema(HDBSequenceParser.SchemaContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#schema}.
	 * @param ctx the parse tree
	 */
	void exitSchema(HDBSequenceParser.SchemaContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#incrementBy}.
	 * @param ctx the parse tree
	 */
	void enterIncrementBy(HDBSequenceParser.IncrementByContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#incrementBy}.
	 * @param ctx the parse tree
	 */
	void exitIncrementBy(HDBSequenceParser.IncrementByContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#startWith}.
	 * @param ctx the parse tree
	 */
	void enterStartWith(HDBSequenceParser.StartWithContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#startWith}.
	 * @param ctx the parse tree
	 */
	void exitStartWith(HDBSequenceParser.StartWithContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#maxValue}.
	 * @param ctx the parse tree
	 */
	void enterMaxValue(HDBSequenceParser.MaxValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#maxValue}.
	 * @param ctx the parse tree
	 */
	void exitMaxValue(HDBSequenceParser.MaxValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#noMaxValue}.
	 * @param ctx the parse tree
	 */
	void enterNoMaxValue(HDBSequenceParser.NoMaxValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#noMaxValue}.
	 * @param ctx the parse tree
	 */
	void exitNoMaxValue(HDBSequenceParser.NoMaxValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#minValue}.
	 * @param ctx the parse tree
	 */
	void enterMinValue(HDBSequenceParser.MinValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#minValue}.
	 * @param ctx the parse tree
	 */
	void exitMinValue(HDBSequenceParser.MinValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#noMinValue}.
	 * @param ctx the parse tree
	 */
	void enterNoMinValue(HDBSequenceParser.NoMinValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#noMinValue}.
	 * @param ctx the parse tree
	 */
	void exitNoMinValue(HDBSequenceParser.NoMinValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#cycles}.
	 * @param ctx the parse tree
	 */
	void enterCycles(HDBSequenceParser.CyclesContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#cycles}.
	 * @param ctx the parse tree
	 */
	void exitCycles(HDBSequenceParser.CyclesContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#resetBy}.
	 * @param ctx the parse tree
	 */
	void enterResetBy(HDBSequenceParser.ResetByContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#resetBy}.
	 * @param ctx the parse tree
	 */
	void exitResetBy(HDBSequenceParser.ResetByContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#publicc}.
	 * @param ctx the parse tree
	 */
	void enterPublicc(HDBSequenceParser.PubliccContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#publicc}.
	 * @param ctx the parse tree
	 */
	void exitPublicc(HDBSequenceParser.PubliccContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#dependsOnTable}.
	 * @param ctx the parse tree
	 */
	void enterDependsOnTable(HDBSequenceParser.DependsOnTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#dependsOnTable}.
	 * @param ctx the parse tree
	 */
	void exitDependsOnTable(HDBSequenceParser.DependsOnTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBSequenceParser#dependsOnView}.
	 * @param ctx the parse tree
	 */
	void enterDependsOnView(HDBSequenceParser.DependsOnViewContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBSequenceParser#dependsOnView}.
	 * @param ctx the parse tree
	 */
	void exitDependsOnView(HDBSequenceParser.DependsOnViewContext ctx);
}