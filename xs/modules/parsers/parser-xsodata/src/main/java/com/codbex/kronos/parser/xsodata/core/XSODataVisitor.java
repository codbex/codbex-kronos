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
// Generated from com/codbex/kronos/parser/xsodata/core/XSOData.g4 by ANTLR 4.13.2
package com.codbex.kronos.parser.xsodata.core;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by
 * {@link XSODataParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *        type.
 */
public interface XSODataVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link XSODataParser#xsOdataDefinition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitXsOdataDefinition(XSODataParser.XsOdataDefinitionContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#service}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitService(XSODataParser.ServiceContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#namespace}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNamespace(XSODataParser.NamespaceContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#body}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBody(XSODataParser.BodyContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#content}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitContent(XSODataParser.ContentContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#entry}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEntry(XSODataParser.EntryContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#entity}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEntity(XSODataParser.EntityContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#object}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitObject(XSODataParser.ObjectContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#catalogobjectschema}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCatalogobjectschema(XSODataParser.CatalogobjectschemaContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#catalogobjectname}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCatalogobjectname(XSODataParser.CatalogobjectnameContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#entityset}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEntityset(XSODataParser.EntitysetContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#entitysetname}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEntitysetname(XSODataParser.EntitysetnameContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#with}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWith(XSODataParser.WithContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#withProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWithProp(XSODataParser.WithPropContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#withoutProp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWithoutProp(XSODataParser.WithoutPropContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#propertylist}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPropertylist(XSODataParser.PropertylistContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#columnname}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnname(XSODataParser.ColumnnameContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#keys}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitKeys(XSODataParser.KeysContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#keylist}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitKeylist(XSODataParser.KeylistContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#keygenerated}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitKeygenerated(XSODataParser.KeygeneratedContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#concurrencytoken}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitConcurrencytoken(XSODataParser.ConcurrencytokenContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#navigates}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNavigates(XSODataParser.NavigatesContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#navlist}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNavlist(XSODataParser.NavlistContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#naventry}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNaventry(XSODataParser.NaventryContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#assocname}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssocname(XSODataParser.AssocnameContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#navpropname}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNavpropname(XSODataParser.NavpropnameContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#fromend}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFromend(XSODataParser.FromendContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#principal}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrincipal(XSODataParser.PrincipalContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#dependent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDependent(XSODataParser.DependentContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#aggregates}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAggregates(XSODataParser.AggregatesContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#aggregatestuple}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAggregatestuple(XSODataParser.AggregatestupleContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#aggregate}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAggregate(XSODataParser.AggregateContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#aggregatefunction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAggregatefunction(XSODataParser.AggregatefunctionContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#parameters}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParameters(XSODataParser.ParametersContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#parameterskeyand}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParameterskeyand(XSODataParser.ParameterskeyandContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#parameterentitysetname}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParameterentitysetname(XSODataParser.ParameterentitysetnameContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#parametersresultsprop}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParametersresultsprop(XSODataParser.ParametersresultspropContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#modificationBody}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitModificationBody(XSODataParser.ModificationBodyContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#modification}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitModification(XSODataParser.ModificationContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#create}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCreate(XSODataParser.CreateContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#update}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUpdate(XSODataParser.UpdateContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#delete}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDelete(XSODataParser.DeleteContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#modificationspec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitModificationspec(XSODataParser.ModificationspecContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#modificationaction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitModificationaction(XSODataParser.ModificationactionContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#forbidden}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitForbidden(XSODataParser.ForbiddenContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#action}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAction(XSODataParser.ActionContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#events}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEvents(XSODataParser.EventsContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#eventlist}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEventlist(XSODataParser.EventlistContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#eventlistElement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEventlistElement(XSODataParser.EventlistElementContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#eventtype}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEventtype(XSODataParser.EventtypeContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#association}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssociation(XSODataParser.AssociationContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#associationdef}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssociationdef(XSODataParser.AssociationdefContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#assocrefconstraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssocrefconstraint(XSODataParser.AssocrefconstraintContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#principalend}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrincipalend(XSODataParser.PrincipalendContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#dependentend}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDependentend(XSODataParser.DependentendContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#end}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEnd(XSODataParser.EndContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#endref}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEndref(XSODataParser.EndrefContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#endtype}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEndtype(XSODataParser.EndtypeContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#joinpropertieslist}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitJoinpropertieslist(XSODataParser.JoinpropertieslistContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#multiplicity}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMultiplicity(XSODataParser.MultiplicityContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#multiplicityvalue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMultiplicityvalue(XSODataParser.MultiplicityvalueContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#assoctable}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssoctable(XSODataParser.AssoctableContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#repoobject}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRepoobject(XSODataParser.RepoobjectContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#overprincipalend}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOverprincipalend(XSODataParser.OverprincipalendContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#overdependentend}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOverdependentend(XSODataParser.OverdependentendContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#overend}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOverend(XSODataParser.OverendContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#storage}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStorage(XSODataParser.StorageContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#nostorage}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNostorage(XSODataParser.NostorageContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#storageend}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStorageend(XSODataParser.StorageendContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#annotations}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnnotations(XSODataParser.AnnotationsContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#annotationsbody}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnnotationsbody(XSODataParser.AnnotationsbodyContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#annotationconfig}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnnotationconfig(XSODataParser.AnnotationconfigContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#settings}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSettings(XSODataParser.SettingsContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#settingsbody}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSettingsbody(XSODataParser.SettingsbodyContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#settingselement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSettingselement(XSODataParser.SettingselementContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#supportnull}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSupportnull(XSODataParser.SupportnullContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#contentcashecontrol}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitContentcashecontrol(XSODataParser.ContentcashecontrolContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#metadatacashecontrol}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMetadatacashecontrol(XSODataParser.MetadatacashecontrolContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#hints}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHints(XSODataParser.HintsContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#hintlist}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHintlist(XSODataParser.HintlistContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#hintvalue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHintvalue(XSODataParser.HintvalueContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#nullvalue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNullvalue(XSODataParser.NullvalueContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#limits}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLimits(XSODataParser.LimitsContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#limitvalue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLimitvalue(XSODataParser.LimitvalueContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#maxrecords}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMaxrecords(XSODataParser.MaxrecordsContext ctx);

    /**
     * Visit a parse tree produced by {@link XSODataParser#maxexpandedrecords}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMaxexpandedrecords(XSODataParser.MaxexpandedrecordsContext ctx);
}
