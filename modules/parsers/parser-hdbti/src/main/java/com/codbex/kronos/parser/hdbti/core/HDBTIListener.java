// Generated from com/codbex/kronos/parser/hdbti/core/HDBTI.g4 by ANTLR 4.13.0
package com.codbex.kronos.parser.hdbti.core;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HDBTIParser}.
 */
public interface HDBTIListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#importArr}.
	 * @param ctx the parse tree
	 */
	void enterImportArr(HDBTIParser.ImportArrContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#importArr}.
	 * @param ctx the parse tree
	 */
	void exitImportArr(HDBTIParser.ImportArrContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#objConfig}.
	 * @param ctx the parse tree
	 */
	void enterObjConfig(HDBTIParser.ObjConfigContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#objConfig}.
	 * @param ctx the parse tree
	 */
	void exitObjConfig(HDBTIParser.ObjConfigContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#assignExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpression(HDBTIParser.AssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#assignExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpression(HDBTIParser.AssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#assignTable}.
	 * @param ctx the parse tree
	 */
	void enterAssignTable(HDBTIParser.AssignTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#assignTable}.
	 * @param ctx the parse tree
	 */
	void exitAssignTable(HDBTIParser.AssignTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#assignSchema}.
	 * @param ctx the parse tree
	 */
	void enterAssignSchema(HDBTIParser.AssignSchemaContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#assignSchema}.
	 * @param ctx the parse tree
	 */
	void exitAssignSchema(HDBTIParser.AssignSchemaContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#assignFile}.
	 * @param ctx the parse tree
	 */
	void enterAssignFile(HDBTIParser.AssignFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#assignFile}.
	 * @param ctx the parse tree
	 */
	void exitAssignFile(HDBTIParser.AssignFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#assignHeader}.
	 * @param ctx the parse tree
	 */
	void enterAssignHeader(HDBTIParser.AssignHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#assignHeader}.
	 * @param ctx the parse tree
	 */
	void exitAssignHeader(HDBTIParser.AssignHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#assignUseHeaderNames}.
	 * @param ctx the parse tree
	 */
	void enterAssignUseHeaderNames(HDBTIParser.AssignUseHeaderNamesContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#assignUseHeaderNames}.
	 * @param ctx the parse tree
	 */
	void exitAssignUseHeaderNames(HDBTIParser.AssignUseHeaderNamesContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#assignDelimField}.
	 * @param ctx the parse tree
	 */
	void enterAssignDelimField(HDBTIParser.AssignDelimFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#assignDelimField}.
	 * @param ctx the parse tree
	 */
	void exitAssignDelimField(HDBTIParser.AssignDelimFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#assignDelimEnclosing}.
	 * @param ctx the parse tree
	 */
	void enterAssignDelimEnclosing(HDBTIParser.AssignDelimEnclosingContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#assignDelimEnclosing}.
	 * @param ctx the parse tree
	 */
	void exitAssignDelimEnclosing(HDBTIParser.AssignDelimEnclosingContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#assignDistinguishEmptyFromNull}.
	 * @param ctx the parse tree
	 */
	void enterAssignDistinguishEmptyFromNull(HDBTIParser.AssignDistinguishEmptyFromNullContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#assignDistinguishEmptyFromNull}.
	 * @param ctx the parse tree
	 */
	void exitAssignDistinguishEmptyFromNull(HDBTIParser.AssignDistinguishEmptyFromNullContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#assignKeys}.
	 * @param ctx the parse tree
	 */
	void enterAssignKeys(HDBTIParser.AssignKeysContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#assignKeys}.
	 * @param ctx the parse tree
	 */
	void exitAssignKeys(HDBTIParser.AssignKeysContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#keyArr}.
	 * @param ctx the parse tree
	 */
	void enterKeyArr(HDBTIParser.KeyArrContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#keyArr}.
	 * @param ctx the parse tree
	 */
	void exitKeyArr(HDBTIParser.KeyArrContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterPair(HDBTIParser.PairContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitPair(HDBTIParser.PairContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#pairKey}.
	 * @param ctx the parse tree
	 */
	void enterPairKey(HDBTIParser.PairKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#pairKey}.
	 * @param ctx the parse tree
	 */
	void exitPairKey(HDBTIParser.PairKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#pairValue}.
	 * @param ctx the parse tree
	 */
	void enterPairValue(HDBTIParser.PairValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#pairValue}.
	 * @param ctx the parse tree
	 */
	void exitPairValue(HDBTIParser.PairValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTIParser#tableName}.
	 * @param ctx the parse tree
	 */
	void enterTableName(HDBTIParser.TableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTIParser#tableName}.
	 * @param ctx the parse tree
	 */
	void exitTableName(HDBTIParser.TableNameContext ctx);
}