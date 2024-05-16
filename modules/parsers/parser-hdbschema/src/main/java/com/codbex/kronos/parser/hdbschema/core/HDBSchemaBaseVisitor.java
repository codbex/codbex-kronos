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
// Generated from com/codbex/kronos/parser/hdbschema/core/HDBSchema.g4 by ANTLR 4.13.1
package com.codbex.kronos.parser.hdbschema.core;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link HDBSchemaVisitor}, which can be extended to
 * create a visitor which only needs to handle a subset of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *        type.
 */
@SuppressWarnings("CheckReturnValue")
public class HDBSchemaBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements HDBSchemaVisitor<T> {
    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitHdbSchemaDefinition(HDBSchemaParser.HdbSchemaDefinitionContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitSchemaNameProp(HDBSchemaParser.SchemaNamePropContext ctx) {
        return visitChildren(ctx);
    }
}
