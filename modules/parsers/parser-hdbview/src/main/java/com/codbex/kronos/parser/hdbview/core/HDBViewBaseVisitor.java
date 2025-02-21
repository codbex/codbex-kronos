// Generated from com/codbex/kronos/parser/hdbview/core/HDBView.g4 by ANTLR 4.13.2
package com.codbex.kronos.parser.hdbview.core;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link HDBViewVisitor}, which can be extended to
 * create a visitor which only needs to handle a subset of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *        type.
 */
@SuppressWarnings("CheckReturnValue")
public class HDBViewBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements HDBViewVisitor<T> {
    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation returns the result of calling {@link #visitChildren} on {@code ctx}.
     * </p>
     */
    @Override
    public T visitHdbViewDefinition(HDBViewParser.HdbViewDefinitionContext ctx) {
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
    public T visitProperty(HDBViewParser.PropertyContext ctx) {
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
    public T visitSchemaProp(HDBViewParser.SchemaPropContext ctx) {
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
    public T visitPublicProp(HDBViewParser.PublicPropContext ctx) {
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
    public T visitQueryProp(HDBViewParser.QueryPropContext ctx) {
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
    public T visitDependsOnProp(HDBViewParser.DependsOnPropContext ctx) {
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
    public T visitDependsOnTable(HDBViewParser.DependsOnTableContext ctx) {
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
    public T visitDependsOnView(HDBViewParser.DependsOnViewContext ctx) {
        return visitChildren(ctx);
    }
}
