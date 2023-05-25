// Generated from com/codbex/kronos/parser/hdbview/core/HDBView.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.hdbview.core;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HDBViewParser}.
 */
public interface HDBViewListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HDBViewParser#hdbViewDefinition}.
	 * @param ctx the parse tree
	 */
	void enterHdbViewDefinition(HDBViewParser.HdbViewDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBViewParser#hdbViewDefinition}.
	 * @param ctx the parse tree
	 */
	void exitHdbViewDefinition(HDBViewParser.HdbViewDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBViewParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(HDBViewParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBViewParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(HDBViewParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBViewParser#schemaProp}.
	 * @param ctx the parse tree
	 */
	void enterSchemaProp(HDBViewParser.SchemaPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBViewParser#schemaProp}.
	 * @param ctx the parse tree
	 */
	void exitSchemaProp(HDBViewParser.SchemaPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBViewParser#publicProp}.
	 * @param ctx the parse tree
	 */
	void enterPublicProp(HDBViewParser.PublicPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBViewParser#publicProp}.
	 * @param ctx the parse tree
	 */
	void exitPublicProp(HDBViewParser.PublicPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBViewParser#queryProp}.
	 * @param ctx the parse tree
	 */
	void enterQueryProp(HDBViewParser.QueryPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBViewParser#queryProp}.
	 * @param ctx the parse tree
	 */
	void exitQueryProp(HDBViewParser.QueryPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBViewParser#dependsOnProp}.
	 * @param ctx the parse tree
	 */
	void enterDependsOnProp(HDBViewParser.DependsOnPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBViewParser#dependsOnProp}.
	 * @param ctx the parse tree
	 */
	void exitDependsOnProp(HDBViewParser.DependsOnPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBViewParser#dependsOnTable}.
	 * @param ctx the parse tree
	 */
	void enterDependsOnTable(HDBViewParser.DependsOnTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBViewParser#dependsOnTable}.
	 * @param ctx the parse tree
	 */
	void exitDependsOnTable(HDBViewParser.DependsOnTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBViewParser#dependsOnView}.
	 * @param ctx the parse tree
	 */
	void enterDependsOnView(HDBViewParser.DependsOnViewContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBViewParser#dependsOnView}.
	 * @param ctx the parse tree
	 */
	void exitDependsOnView(HDBViewParser.DependsOnViewContext ctx);
}