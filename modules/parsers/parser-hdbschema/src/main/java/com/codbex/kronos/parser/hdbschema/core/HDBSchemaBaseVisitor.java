// Generated from com/codbex/kronos/parser/hdbschema/core/HDBSchema.g4 by ANTLR 4.13.0
package com.codbex.kronos.parser.hdbschema.core;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link HDBSchemaVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
@SuppressWarnings("CheckReturnValue")
public class HDBSchemaBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements HDBSchemaVisitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitHdbSchemaDefinition(HDBSchemaParser.HdbSchemaDefinitionContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitSchemaNameProp(HDBSchemaParser.SchemaNamePropContext ctx) { return visitChildren(ctx); }
}