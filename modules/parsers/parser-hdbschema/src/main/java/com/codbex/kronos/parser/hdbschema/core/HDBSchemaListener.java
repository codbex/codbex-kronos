// Generated from com/codbex/kronos/parser/hdbschema/core/HDBSchema.g4 by ANTLR 4.13.1
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