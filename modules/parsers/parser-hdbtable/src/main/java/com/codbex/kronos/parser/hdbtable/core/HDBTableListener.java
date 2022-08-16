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
// Generated from com\codbex\kronos\parser\hdbtable\core\HDBTable.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.hdbtable.core;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HDBTableParser}.
 */
public interface HDBTableListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#hdbTableDefinition}.
	 * @param ctx the parse tree
	 */
	void enterHdbTableDefinition(HDBTableParser.HdbTableDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#hdbTableDefinition}.
	 * @param ctx the parse tree
	 */
	void exitHdbTableDefinition(HDBTableParser.HdbTableDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#hdbTableProperties}.
	 * @param ctx the parse tree
	 */
	void enterHdbTableProperties(HDBTableParser.HdbTablePropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#hdbTableProperties}.
	 * @param ctx the parse tree
	 */
	void exitHdbTableProperties(HDBTableParser.HdbTablePropertiesContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#schemaNameProp}.
	 * @param ctx the parse tree
	 */
	void enterSchemaNameProp(HDBTableParser.SchemaNamePropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#schemaNameProp}.
	 * @param ctx the parse tree
	 */
	void exitSchemaNameProp(HDBTableParser.SchemaNamePropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#temporaryProp}.
	 * @param ctx the parse tree
	 */
	void enterTemporaryProp(HDBTableParser.TemporaryPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#temporaryProp}.
	 * @param ctx the parse tree
	 */
	void exitTemporaryProp(HDBTableParser.TemporaryPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#tableTypeProp}.
	 * @param ctx the parse tree
	 */
	void enterTableTypeProp(HDBTableParser.TableTypePropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#tableTypeProp}.
	 * @param ctx the parse tree
	 */
	void exitTableTypeProp(HDBTableParser.TableTypePropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#publicProp}.
	 * @param ctx the parse tree
	 */
	void enterPublicProp(HDBTableParser.PublicPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#publicProp}.
	 * @param ctx the parse tree
	 */
	void exitPublicProp(HDBTableParser.PublicPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#loggingTypeProp}.
	 * @param ctx the parse tree
	 */
	void enterLoggingTypeProp(HDBTableParser.LoggingTypePropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#loggingTypeProp}.
	 * @param ctx the parse tree
	 */
	void exitLoggingTypeProp(HDBTableParser.LoggingTypePropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#tableColumnsProp}.
	 * @param ctx the parse tree
	 */
	void enterTableColumnsProp(HDBTableParser.TableColumnsPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#tableColumnsProp}.
	 * @param ctx the parse tree
	 */
	void exitTableColumnsProp(HDBTableParser.TableColumnsPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#tableIndexesProp}.
	 * @param ctx the parse tree
	 */
	void enterTableIndexesProp(HDBTableParser.TableIndexesPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#tableIndexesProp}.
	 * @param ctx the parse tree
	 */
	void exitTableIndexesProp(HDBTableParser.TableIndexesPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#tablePrimaryKeyProp}.
	 * @param ctx the parse tree
	 */
	void enterTablePrimaryKeyProp(HDBTableParser.TablePrimaryKeyPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#tablePrimaryKeyProp}.
	 * @param ctx the parse tree
	 */
	void exitTablePrimaryKeyProp(HDBTableParser.TablePrimaryKeyPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#tablePrimaryKeyColumnsProp}.
	 * @param ctx the parse tree
	 */
	void enterTablePrimaryKeyColumnsProp(HDBTableParser.TablePrimaryKeyColumnsPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#tablePrimaryKeyColumnsProp}.
	 * @param ctx the parse tree
	 */
	void exitTablePrimaryKeyColumnsProp(HDBTableParser.TablePrimaryKeyColumnsPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#tablePrimaryKeyIndexTypeProp}.
	 * @param ctx the parse tree
	 */
	void enterTablePrimaryKeyIndexTypeProp(HDBTableParser.TablePrimaryKeyIndexTypePropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#tablePrimaryKeyIndexTypeProp}.
	 * @param ctx the parse tree
	 */
	void exitTablePrimaryKeyIndexTypeProp(HDBTableParser.TablePrimaryKeyIndexTypePropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#descriptionProp}.
	 * @param ctx the parse tree
	 */
	void enterDescriptionProp(HDBTableParser.DescriptionPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#descriptionProp}.
	 * @param ctx the parse tree
	 */
	void exitDescriptionProp(HDBTableParser.DescriptionPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnsObject}.
	 * @param ctx the parse tree
	 */
	void enterColumnsObject(HDBTableParser.ColumnsObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnsObject}.
	 * @param ctx the parse tree
	 */
	void exitColumnsObject(HDBTableParser.ColumnsObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnsProperties}.
	 * @param ctx the parse tree
	 */
	void enterColumnsProperties(HDBTableParser.ColumnsPropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnsProperties}.
	 * @param ctx the parse tree
	 */
	void exitColumnsProperties(HDBTableParser.ColumnsPropertiesContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#indexesObject}.
	 * @param ctx the parse tree
	 */
	void enterIndexesObject(HDBTableParser.IndexesObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#indexesObject}.
	 * @param ctx the parse tree
	 */
	void exitIndexesObject(HDBTableParser.IndexesObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#indexProperties}.
	 * @param ctx the parse tree
	 */
	void enterIndexProperties(HDBTableParser.IndexPropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#indexProperties}.
	 * @param ctx the parse tree
	 */
	void exitIndexProperties(HDBTableParser.IndexPropertiesContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnAssignName}.
	 * @param ctx the parse tree
	 */
	void enterColumnAssignName(HDBTableParser.ColumnAssignNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnAssignName}.
	 * @param ctx the parse tree
	 */
	void exitColumnAssignName(HDBTableParser.ColumnAssignNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnAssignSQLType}.
	 * @param ctx the parse tree
	 */
	void enterColumnAssignSQLType(HDBTableParser.ColumnAssignSQLTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnAssignSQLType}.
	 * @param ctx the parse tree
	 */
	void exitColumnAssignSQLType(HDBTableParser.ColumnAssignSQLTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnAssignNullable}.
	 * @param ctx the parse tree
	 */
	void enterColumnAssignNullable(HDBTableParser.ColumnAssignNullableContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnAssignNullable}.
	 * @param ctx the parse tree
	 */
	void exitColumnAssignNullable(HDBTableParser.ColumnAssignNullableContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnAssignUnique}.
	 * @param ctx the parse tree
	 */
	void enterColumnAssignUnique(HDBTableParser.ColumnAssignUniqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnAssignUnique}.
	 * @param ctx the parse tree
	 */
	void exitColumnAssignUnique(HDBTableParser.ColumnAssignUniqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnAssignLength}.
	 * @param ctx the parse tree
	 */
	void enterColumnAssignLength(HDBTableParser.ColumnAssignLengthContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnAssignLength}.
	 * @param ctx the parse tree
	 */
	void exitColumnAssignLength(HDBTableParser.ColumnAssignLengthContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnAssignComment}.
	 * @param ctx the parse tree
	 */
	void enterColumnAssignComment(HDBTableParser.ColumnAssignCommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnAssignComment}.
	 * @param ctx the parse tree
	 */
	void exitColumnAssignComment(HDBTableParser.ColumnAssignCommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnAssignDefaultValue}.
	 * @param ctx the parse tree
	 */
	void enterColumnAssignDefaultValue(HDBTableParser.ColumnAssignDefaultValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnAssignDefaultValue}.
	 * @param ctx the parse tree
	 */
	void exitColumnAssignDefaultValue(HDBTableParser.ColumnAssignDefaultValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnAssignPrecision}.
	 * @param ctx the parse tree
	 */
	void enterColumnAssignPrecision(HDBTableParser.ColumnAssignPrecisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnAssignPrecision}.
	 * @param ctx the parse tree
	 */
	void exitColumnAssignPrecision(HDBTableParser.ColumnAssignPrecisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#columnAssignScale}.
	 * @param ctx the parse tree
	 */
	void enterColumnAssignScale(HDBTableParser.ColumnAssignScaleContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#columnAssignScale}.
	 * @param ctx the parse tree
	 */
	void exitColumnAssignScale(HDBTableParser.ColumnAssignScaleContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#indexAssignName}.
	 * @param ctx the parse tree
	 */
	void enterIndexAssignName(HDBTableParser.IndexAssignNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#indexAssignName}.
	 * @param ctx the parse tree
	 */
	void exitIndexAssignName(HDBTableParser.IndexAssignNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#indexAssignUnique}.
	 * @param ctx the parse tree
	 */
	void enterIndexAssignUnique(HDBTableParser.IndexAssignUniqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#indexAssignUnique}.
	 * @param ctx the parse tree
	 */
	void exitIndexAssignUnique(HDBTableParser.IndexAssignUniqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#indexAssignOrder}.
	 * @param ctx the parse tree
	 */
	void enterIndexAssignOrder(HDBTableParser.IndexAssignOrderContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#indexAssignOrder}.
	 * @param ctx the parse tree
	 */
	void exitIndexAssignOrder(HDBTableParser.IndexAssignOrderContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#indexAssignIndexColumns}.
	 * @param ctx the parse tree
	 */
	void enterIndexAssignIndexColumns(HDBTableParser.IndexAssignIndexColumnsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#indexAssignIndexColumns}.
	 * @param ctx the parse tree
	 */
	void exitIndexAssignIndexColumns(HDBTableParser.IndexAssignIndexColumnsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#indexAssignIndexType}.
	 * @param ctx the parse tree
	 */
	void enterIndexAssignIndexType(HDBTableParser.IndexAssignIndexTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#indexAssignIndexType}.
	 * @param ctx the parse tree
	 */
	void exitIndexAssignIndexType(HDBTableParser.IndexAssignIndexTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link HDBTableParser#indexColumnsArray}.
	 * @param ctx the parse tree
	 */
	void enterIndexColumnsArray(HDBTableParser.IndexColumnsArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link HDBTableParser#indexColumnsArray}.
	 * @param ctx the parse tree
	 */
	void exitIndexColumnsArray(HDBTableParser.IndexColumnsArrayContext ctx);
}