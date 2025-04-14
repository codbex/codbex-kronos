// Generated from com/codbex/kronos/parser/hdbschema/core/HDBSchema.g4 by ANTLR 4.13.2
package com.codbex.kronos.parser.hdbschema.core;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by
 * {@link HDBSchemaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *        type.
 */
public interface HDBSchemaVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link HDBSchemaParser#hdbSchemaDefinition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHdbSchemaDefinition(HDBSchemaParser.HdbSchemaDefinitionContext ctx);

    /**
     * Visit a parse tree produced by {@link HDBSchemaParser#schemaNameProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSchemaNameProp(HDBSchemaParser.SchemaNamePropContext ctx);
}
