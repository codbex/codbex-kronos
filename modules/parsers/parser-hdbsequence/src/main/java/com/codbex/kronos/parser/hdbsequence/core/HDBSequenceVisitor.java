// Generated from com/codbex/kronos/parser/hdbsequence/core/HDBSequence.g4 by ANTLR 4.13.1
package com.codbex.kronos.parser.hdbsequence.core;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by
 * {@link HDBSequenceParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *        type.
 */
public interface HDBSequenceVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#hdbSequenceDefinition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHdbSequenceDefinition(HDBSequenceParser.HdbSequenceDefinitionContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#property}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProperty(HDBSequenceParser.PropertyContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#schema}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSchema(HDBSequenceParser.SchemaContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#incrementBy}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIncrementBy(HDBSequenceParser.IncrementByContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#startWith}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStartWith(HDBSequenceParser.StartWithContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#maxValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMaxValue(HDBSequenceParser.MaxValueContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#noMaxValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNoMaxValue(HDBSequenceParser.NoMaxValueContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#minValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMinValue(HDBSequenceParser.MinValueContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#noMinValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNoMinValue(HDBSequenceParser.NoMinValueContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#cycles}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCycles(HDBSequenceParser.CyclesContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#resetBy}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitResetBy(HDBSequenceParser.ResetByContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#publicc}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPublicc(HDBSequenceParser.PubliccContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#dependsOnTable}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDependsOnTable(HDBSequenceParser.DependsOnTableContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSequenceParser#dependsOnView}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDependsOnView(HDBSequenceParser.DependsOnViewContext ctx);
}
