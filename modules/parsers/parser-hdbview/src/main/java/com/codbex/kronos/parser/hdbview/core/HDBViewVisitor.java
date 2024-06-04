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
// Generated from com/codbex/kronos/parser/hdbview/core/HDBView.g4 by ANTLR 4.13.1
package com.codbex.kronos.parser.hdbview.core;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by
 * {@link HDBViewParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *        type.
 */
public interface HDBViewVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link HDBViewParser#hdbViewDefinition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHdbViewDefinition(HDBViewParser.HdbViewDefinitionContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBViewParser#property}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProperty(HDBViewParser.PropertyContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBViewParser#schemaProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSchemaProp(HDBViewParser.SchemaPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBViewParser#publicProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPublicProp(HDBViewParser.PublicPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBViewParser#queryProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQueryProp(HDBViewParser.QueryPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBViewParser#dependsOnProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDependsOnProp(HDBViewParser.DependsOnPropContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBViewParser#dependsOnTable}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDependsOnTable(HDBViewParser.DependsOnTableContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBViewParser#dependsOnView}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDependsOnView(HDBViewParser.DependsOnViewContext ctx);
}
