// Generated from com/codbex/kronos/parser/hdbdd/core/Cds.g4 by ANTLR 4.13.1
package com.codbex.kronos.parser.hdbdd.core;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by {@link CdsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *        type.
 */
public interface CdsVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link CdsParser#cdsDefinition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCdsDefinition(CdsParser.CdsDefinitionContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#namespaceRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNamespaceRule(CdsParser.NamespaceRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#usingRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUsingRule(CdsParser.UsingRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#topLevelSymbol}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTopLevelSymbol(CdsParser.TopLevelSymbolContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#dataTypeRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDataTypeRule(CdsParser.DataTypeRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#contextRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitContextRule(CdsParser.ContextRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#structuredTypeRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStructuredTypeRule(CdsParser.StructuredTypeRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#entityRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEntityRule(CdsParser.EntityRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#viewRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitViewRule(CdsParser.ViewRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#fieldDeclRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFieldDeclRule(CdsParser.FieldDeclRuleContext ctx);

    /**
     * Visit a parse tree produced by the {@code AssignType} labeled alternative in
     * {@link CdsParser#typeAssignRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssignType(CdsParser.AssignTypeContext ctx);

    /**
     * Visit a parse tree produced by the {@code AssignTypeWithArgs} labeled alternative in
     * {@link CdsParser#typeAssignRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssignTypeWithArgs(CdsParser.AssignTypeWithArgsContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#elementDeclRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitElementDeclRule(CdsParser.ElementDeclRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#elementDetails}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitElementDetails(CdsParser.ElementDetailsContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#elementConstraints}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitElementConstraints(CdsParser.ElementConstraintsContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#associationConstraints}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssociationConstraints(CdsParser.AssociationConstraintsContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#constraints}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitConstraints(CdsParser.ConstraintsContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#association}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssociation(CdsParser.AssociationContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#calculatedAssociation}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCalculatedAssociation(CdsParser.CalculatedAssociationContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStatement(CdsParser.StatementContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#associationTarget}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssociationTarget(CdsParser.AssociationTargetContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#unmanagedForeignKey}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUnmanagedForeignKey(CdsParser.UnmanagedForeignKeyContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#managedForeignKeys}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitManagedForeignKeys(CdsParser.ManagedForeignKeysContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#foreignKey}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitForeignKey(CdsParser.ForeignKeyContext ctx);

    /**
     * Visit a parse tree produced by the {@code MinMaxCardinality} labeled alternative in
     * {@link CdsParser#cardinality}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMinMaxCardinality(CdsParser.MinMaxCardinalityContext ctx);

    /**
     * Visit a parse tree produced by the {@code MaxCardinality} labeled alternative in
     * {@link CdsParser#cardinality}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMaxCardinality(CdsParser.MaxCardinalityContext ctx);

    /**
     * Visit a parse tree produced by the {@code NoCardinality} labeled alternative in
     * {@link CdsParser#cardinality}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNoCardinality(CdsParser.NoCardinalityContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#defaultValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDefaultValue(CdsParser.DefaultValueContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnnObjectRule} labeled alternative in
     * {@link CdsParser#annotationRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnnObjectRule(CdsParser.AnnObjectRuleContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnnPropertyRule} labeled alternative in
     * {@link CdsParser#annotationRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnnPropertyRule(CdsParser.AnnPropertyRuleContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnnMarkerRule} labeled alternative in
     * {@link CdsParser#annotationRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnnMarkerRule(CdsParser.AnnMarkerRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#annValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnnValue(CdsParser.AnnValueContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#enumRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEnumRule(CdsParser.EnumRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#arrRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArrRule(CdsParser.ArrRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#obj}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitObj(CdsParser.ObjContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#keyValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitKeyValue(CdsParser.KeyValueContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#selectRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSelectRule(CdsParser.SelectRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#joinRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitJoinRule(CdsParser.JoinRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#joinFields}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitJoinFields(CdsParser.JoinFieldsContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#selectedColumnsRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSelectedColumnsRule(CdsParser.SelectedColumnsRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#whereRule}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWhereRule(CdsParser.WhereRuleContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#characters}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCharacters(CdsParser.CharactersContext ctx);

    /**
     * Visit a parse tree produced by {@link CdsParser#identifier}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIdentifier(CdsParser.IdentifierContext ctx);
}
