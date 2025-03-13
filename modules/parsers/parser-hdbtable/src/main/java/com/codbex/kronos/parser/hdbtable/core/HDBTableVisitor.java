/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
// Generated from com/codbex/kronos/parser/hdbtable/core/HDBTable.g4 by ANTLR 4.13.2
package com.codbex.kronos.parser.hdbtable.core;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by
 * {@link HDBTableParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *        type.
 */
public interface HDBTableVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link HDBTableParser#hdbTableDefinition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHdbTableDefinition(HDBTableParser.HdbTableDefinitionContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#hdbTableProperties}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHdbTableProperties(HDBTableParser.HdbTablePropertiesContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#schemaNameProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSchemaNameProp(HDBTableParser.SchemaNamePropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#temporaryProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTemporaryProp(HDBTableParser.TemporaryPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#tableTypeProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTableTypeProp(HDBTableParser.TableTypePropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#publicProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPublicProp(HDBTableParser.PublicPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#loggingTypeProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLoggingTypeProp(HDBTableParser.LoggingTypePropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#tableColumnsProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTableColumnsProp(HDBTableParser.TableColumnsPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#tableIndexesProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTableIndexesProp(HDBTableParser.TableIndexesPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#tablePrimaryKeyProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTablePrimaryKeyProp(HDBTableParser.TablePrimaryKeyPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#tablePrimaryKeyColumnsProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTablePrimaryKeyColumnsProp(HDBTableParser.TablePrimaryKeyColumnsPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#tablePrimaryKeyIndexTypeProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTablePrimaryKeyIndexTypeProp(HDBTableParser.TablePrimaryKeyIndexTypePropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#descriptionProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDescriptionProp(HDBTableParser.DescriptionPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnsObject}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnsObject(HDBTableParser.ColumnsObjectContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnsProperties}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnsProperties(HDBTableParser.ColumnsPropertiesContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#indexesObject}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexesObject(HDBTableParser.IndexesObjectContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#indexProperties}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexProperties(HDBTableParser.IndexPropertiesContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnAssignName}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnAssignName(HDBTableParser.ColumnAssignNameContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnAssignSQLType}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnAssignSQLType(HDBTableParser.ColumnAssignSQLTypeContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnAssignNullable}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnAssignNullable(HDBTableParser.ColumnAssignNullableContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnAssignUnique}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnAssignUnique(HDBTableParser.ColumnAssignUniqueContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnAssignLength}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnAssignLength(HDBTableParser.ColumnAssignLengthContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnAssignComment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnAssignComment(HDBTableParser.ColumnAssignCommentContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnAssignDefaultValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnAssignDefaultValue(HDBTableParser.ColumnAssignDefaultValueContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnAssignPrecision}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnAssignPrecision(HDBTableParser.ColumnAssignPrecisionContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#columnAssignScale}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnAssignScale(HDBTableParser.ColumnAssignScaleContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#indexAssignName}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexAssignName(HDBTableParser.IndexAssignNameContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#indexAssignUnique}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexAssignUnique(HDBTableParser.IndexAssignUniqueContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#indexAssignOrder}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexAssignOrder(HDBTableParser.IndexAssignOrderContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#indexAssignIndexColumns}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexAssignIndexColumns(HDBTableParser.IndexAssignIndexColumnsContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#indexAssignIndexType}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexAssignIndexType(HDBTableParser.IndexAssignIndexTypeContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBTableParser#indexColumnsArray}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexColumnsArray(HDBTableParser.IndexColumnsArrayContext ctx);
}
