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
// Generated from com\codbex\kronos\parser\xsodata\core\XSOData.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.xsodata.core;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XSODataParser}.
 */
public interface XSODataListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link XSODataParser#xsOdataDefinition}.
	 * @param ctx the parse tree
	 */
	void enterXsOdataDefinition(XSODataParser.XsOdataDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#xsOdataDefinition}.
	 * @param ctx the parse tree
	 */
	void exitXsOdataDefinition(XSODataParser.XsOdataDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#service}.
	 * @param ctx the parse tree
	 */
	void enterService(XSODataParser.ServiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#service}.
	 * @param ctx the parse tree
	 */
	void exitService(XSODataParser.ServiceContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#namespace}.
	 * @param ctx the parse tree
	 */
	void enterNamespace(XSODataParser.NamespaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#namespace}.
	 * @param ctx the parse tree
	 */
	void exitNamespace(XSODataParser.NamespaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(XSODataParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(XSODataParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#content}.
	 * @param ctx the parse tree
	 */
	void enterContent(XSODataParser.ContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#content}.
	 * @param ctx the parse tree
	 */
	void exitContent(XSODataParser.ContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#entry}.
	 * @param ctx the parse tree
	 */
	void enterEntry(XSODataParser.EntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#entry}.
	 * @param ctx the parse tree
	 */
	void exitEntry(XSODataParser.EntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#entity}.
	 * @param ctx the parse tree
	 */
	void enterEntity(XSODataParser.EntityContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#entity}.
	 * @param ctx the parse tree
	 */
	void exitEntity(XSODataParser.EntityContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(XSODataParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(XSODataParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#catalogobjectschema}.
	 * @param ctx the parse tree
	 */
	void enterCatalogobjectschema(XSODataParser.CatalogobjectschemaContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#catalogobjectschema}.
	 * @param ctx the parse tree
	 */
	void exitCatalogobjectschema(XSODataParser.CatalogobjectschemaContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#catalogobjectname}.
	 * @param ctx the parse tree
	 */
	void enterCatalogobjectname(XSODataParser.CatalogobjectnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#catalogobjectname}.
	 * @param ctx the parse tree
	 */
	void exitCatalogobjectname(XSODataParser.CatalogobjectnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#entityset}.
	 * @param ctx the parse tree
	 */
	void enterEntityset(XSODataParser.EntitysetContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#entityset}.
	 * @param ctx the parse tree
	 */
	void exitEntityset(XSODataParser.EntitysetContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#entitysetname}.
	 * @param ctx the parse tree
	 */
	void enterEntitysetname(XSODataParser.EntitysetnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#entitysetname}.
	 * @param ctx the parse tree
	 */
	void exitEntitysetname(XSODataParser.EntitysetnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#with}.
	 * @param ctx the parse tree
	 */
	void enterWith(XSODataParser.WithContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#with}.
	 * @param ctx the parse tree
	 */
	void exitWith(XSODataParser.WithContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#withProp}.
	 * @param ctx the parse tree
	 */
	void enterWithProp(XSODataParser.WithPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#withProp}.
	 * @param ctx the parse tree
	 */
	void exitWithProp(XSODataParser.WithPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#withoutProp}.
	 * @param ctx the parse tree
	 */
	void enterWithoutProp(XSODataParser.WithoutPropContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#withoutProp}.
	 * @param ctx the parse tree
	 */
	void exitWithoutProp(XSODataParser.WithoutPropContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#propertylist}.
	 * @param ctx the parse tree
	 */
	void enterPropertylist(XSODataParser.PropertylistContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#propertylist}.
	 * @param ctx the parse tree
	 */
	void exitPropertylist(XSODataParser.PropertylistContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#columnname}.
	 * @param ctx the parse tree
	 */
	void enterColumnname(XSODataParser.ColumnnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#columnname}.
	 * @param ctx the parse tree
	 */
	void exitColumnname(XSODataParser.ColumnnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#keys}.
	 * @param ctx the parse tree
	 */
	void enterKeys(XSODataParser.KeysContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#keys}.
	 * @param ctx the parse tree
	 */
	void exitKeys(XSODataParser.KeysContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#keylist}.
	 * @param ctx the parse tree
	 */
	void enterKeylist(XSODataParser.KeylistContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#keylist}.
	 * @param ctx the parse tree
	 */
	void exitKeylist(XSODataParser.KeylistContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#keygenerated}.
	 * @param ctx the parse tree
	 */
	void enterKeygenerated(XSODataParser.KeygeneratedContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#keygenerated}.
	 * @param ctx the parse tree
	 */
	void exitKeygenerated(XSODataParser.KeygeneratedContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#concurrencytoken}.
	 * @param ctx the parse tree
	 */
	void enterConcurrencytoken(XSODataParser.ConcurrencytokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#concurrencytoken}.
	 * @param ctx the parse tree
	 */
	void exitConcurrencytoken(XSODataParser.ConcurrencytokenContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#navigates}.
	 * @param ctx the parse tree
	 */
	void enterNavigates(XSODataParser.NavigatesContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#navigates}.
	 * @param ctx the parse tree
	 */
	void exitNavigates(XSODataParser.NavigatesContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#navlist}.
	 * @param ctx the parse tree
	 */
	void enterNavlist(XSODataParser.NavlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#navlist}.
	 * @param ctx the parse tree
	 */
	void exitNavlist(XSODataParser.NavlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#naventry}.
	 * @param ctx the parse tree
	 */
	void enterNaventry(XSODataParser.NaventryContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#naventry}.
	 * @param ctx the parse tree
	 */
	void exitNaventry(XSODataParser.NaventryContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#assocname}.
	 * @param ctx the parse tree
	 */
	void enterAssocname(XSODataParser.AssocnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#assocname}.
	 * @param ctx the parse tree
	 */
	void exitAssocname(XSODataParser.AssocnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#navpropname}.
	 * @param ctx the parse tree
	 */
	void enterNavpropname(XSODataParser.NavpropnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#navpropname}.
	 * @param ctx the parse tree
	 */
	void exitNavpropname(XSODataParser.NavpropnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#fromend}.
	 * @param ctx the parse tree
	 */
	void enterFromend(XSODataParser.FromendContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#fromend}.
	 * @param ctx the parse tree
	 */
	void exitFromend(XSODataParser.FromendContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#principal}.
	 * @param ctx the parse tree
	 */
	void enterPrincipal(XSODataParser.PrincipalContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#principal}.
	 * @param ctx the parse tree
	 */
	void exitPrincipal(XSODataParser.PrincipalContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#dependent}.
	 * @param ctx the parse tree
	 */
	void enterDependent(XSODataParser.DependentContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#dependent}.
	 * @param ctx the parse tree
	 */
	void exitDependent(XSODataParser.DependentContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#aggregates}.
	 * @param ctx the parse tree
	 */
	void enterAggregates(XSODataParser.AggregatesContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#aggregates}.
	 * @param ctx the parse tree
	 */
	void exitAggregates(XSODataParser.AggregatesContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#aggregatestuple}.
	 * @param ctx the parse tree
	 */
	void enterAggregatestuple(XSODataParser.AggregatestupleContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#aggregatestuple}.
	 * @param ctx the parse tree
	 */
	void exitAggregatestuple(XSODataParser.AggregatestupleContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#aggregate}.
	 * @param ctx the parse tree
	 */
	void enterAggregate(XSODataParser.AggregateContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#aggregate}.
	 * @param ctx the parse tree
	 */
	void exitAggregate(XSODataParser.AggregateContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#aggregatefunction}.
	 * @param ctx the parse tree
	 */
	void enterAggregatefunction(XSODataParser.AggregatefunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#aggregatefunction}.
	 * @param ctx the parse tree
	 */
	void exitAggregatefunction(XSODataParser.AggregatefunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(XSODataParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(XSODataParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#parameterskeyand}.
	 * @param ctx the parse tree
	 */
	void enterParameterskeyand(XSODataParser.ParameterskeyandContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#parameterskeyand}.
	 * @param ctx the parse tree
	 */
	void exitParameterskeyand(XSODataParser.ParameterskeyandContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#parameterentitysetname}.
	 * @param ctx the parse tree
	 */
	void enterParameterentitysetname(XSODataParser.ParameterentitysetnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#parameterentitysetname}.
	 * @param ctx the parse tree
	 */
	void exitParameterentitysetname(XSODataParser.ParameterentitysetnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#parametersresultsprop}.
	 * @param ctx the parse tree
	 */
	void enterParametersresultsprop(XSODataParser.ParametersresultspropContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#parametersresultsprop}.
	 * @param ctx the parse tree
	 */
	void exitParametersresultsprop(XSODataParser.ParametersresultspropContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#modificationBody}.
	 * @param ctx the parse tree
	 */
	void enterModificationBody(XSODataParser.ModificationBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#modificationBody}.
	 * @param ctx the parse tree
	 */
	void exitModificationBody(XSODataParser.ModificationBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#modification}.
	 * @param ctx the parse tree
	 */
	void enterModification(XSODataParser.ModificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#modification}.
	 * @param ctx the parse tree
	 */
	void exitModification(XSODataParser.ModificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#create}.
	 * @param ctx the parse tree
	 */
	void enterCreate(XSODataParser.CreateContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#create}.
	 * @param ctx the parse tree
	 */
	void exitCreate(XSODataParser.CreateContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#update}.
	 * @param ctx the parse tree
	 */
	void enterUpdate(XSODataParser.UpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#update}.
	 * @param ctx the parse tree
	 */
	void exitUpdate(XSODataParser.UpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#delete}.
	 * @param ctx the parse tree
	 */
	void enterDelete(XSODataParser.DeleteContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#delete}.
	 * @param ctx the parse tree
	 */
	void exitDelete(XSODataParser.DeleteContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#modificationspec}.
	 * @param ctx the parse tree
	 */
	void enterModificationspec(XSODataParser.ModificationspecContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#modificationspec}.
	 * @param ctx the parse tree
	 */
	void exitModificationspec(XSODataParser.ModificationspecContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#modificationaction}.
	 * @param ctx the parse tree
	 */
	void enterModificationaction(XSODataParser.ModificationactionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#modificationaction}.
	 * @param ctx the parse tree
	 */
	void exitModificationaction(XSODataParser.ModificationactionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#forbidden}.
	 * @param ctx the parse tree
	 */
	void enterForbidden(XSODataParser.ForbiddenContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#forbidden}.
	 * @param ctx the parse tree
	 */
	void exitForbidden(XSODataParser.ForbiddenContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#action}.
	 * @param ctx the parse tree
	 */
	void enterAction(XSODataParser.ActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#action}.
	 * @param ctx the parse tree
	 */
	void exitAction(XSODataParser.ActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#events}.
	 * @param ctx the parse tree
	 */
	void enterEvents(XSODataParser.EventsContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#events}.
	 * @param ctx the parse tree
	 */
	void exitEvents(XSODataParser.EventsContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#eventlist}.
	 * @param ctx the parse tree
	 */
	void enterEventlist(XSODataParser.EventlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#eventlist}.
	 * @param ctx the parse tree
	 */
	void exitEventlist(XSODataParser.EventlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#eventlistElement}.
	 * @param ctx the parse tree
	 */
	void enterEventlistElement(XSODataParser.EventlistElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#eventlistElement}.
	 * @param ctx the parse tree
	 */
	void exitEventlistElement(XSODataParser.EventlistElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#eventtype}.
	 * @param ctx the parse tree
	 */
	void enterEventtype(XSODataParser.EventtypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#eventtype}.
	 * @param ctx the parse tree
	 */
	void exitEventtype(XSODataParser.EventtypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#association}.
	 * @param ctx the parse tree
	 */
	void enterAssociation(XSODataParser.AssociationContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#association}.
	 * @param ctx the parse tree
	 */
	void exitAssociation(XSODataParser.AssociationContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#associationdef}.
	 * @param ctx the parse tree
	 */
	void enterAssociationdef(XSODataParser.AssociationdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#associationdef}.
	 * @param ctx the parse tree
	 */
	void exitAssociationdef(XSODataParser.AssociationdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#assocrefconstraint}.
	 * @param ctx the parse tree
	 */
	void enterAssocrefconstraint(XSODataParser.AssocrefconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#assocrefconstraint}.
	 * @param ctx the parse tree
	 */
	void exitAssocrefconstraint(XSODataParser.AssocrefconstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#principalend}.
	 * @param ctx the parse tree
	 */
	void enterPrincipalend(XSODataParser.PrincipalendContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#principalend}.
	 * @param ctx the parse tree
	 */
	void exitPrincipalend(XSODataParser.PrincipalendContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#dependentend}.
	 * @param ctx the parse tree
	 */
	void enterDependentend(XSODataParser.DependentendContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#dependentend}.
	 * @param ctx the parse tree
	 */
	void exitDependentend(XSODataParser.DependentendContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#end}.
	 * @param ctx the parse tree
	 */
	void enterEnd(XSODataParser.EndContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#end}.
	 * @param ctx the parse tree
	 */
	void exitEnd(XSODataParser.EndContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#endref}.
	 * @param ctx the parse tree
	 */
	void enterEndref(XSODataParser.EndrefContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#endref}.
	 * @param ctx the parse tree
	 */
	void exitEndref(XSODataParser.EndrefContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#endtype}.
	 * @param ctx the parse tree
	 */
	void enterEndtype(XSODataParser.EndtypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#endtype}.
	 * @param ctx the parse tree
	 */
	void exitEndtype(XSODataParser.EndtypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#joinpropertieslist}.
	 * @param ctx the parse tree
	 */
	void enterJoinpropertieslist(XSODataParser.JoinpropertieslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#joinpropertieslist}.
	 * @param ctx the parse tree
	 */
	void exitJoinpropertieslist(XSODataParser.JoinpropertieslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#multiplicity}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicity(XSODataParser.MultiplicityContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#multiplicity}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicity(XSODataParser.MultiplicityContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#multiplicityvalue}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicityvalue(XSODataParser.MultiplicityvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#multiplicityvalue}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicityvalue(XSODataParser.MultiplicityvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#assoctable}.
	 * @param ctx the parse tree
	 */
	void enterAssoctable(XSODataParser.AssoctableContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#assoctable}.
	 * @param ctx the parse tree
	 */
	void exitAssoctable(XSODataParser.AssoctableContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#repoobject}.
	 * @param ctx the parse tree
	 */
	void enterRepoobject(XSODataParser.RepoobjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#repoobject}.
	 * @param ctx the parse tree
	 */
	void exitRepoobject(XSODataParser.RepoobjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#overprincipalend}.
	 * @param ctx the parse tree
	 */
	void enterOverprincipalend(XSODataParser.OverprincipalendContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#overprincipalend}.
	 * @param ctx the parse tree
	 */
	void exitOverprincipalend(XSODataParser.OverprincipalendContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#overdependentend}.
	 * @param ctx the parse tree
	 */
	void enterOverdependentend(XSODataParser.OverdependentendContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#overdependentend}.
	 * @param ctx the parse tree
	 */
	void exitOverdependentend(XSODataParser.OverdependentendContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#overend}.
	 * @param ctx the parse tree
	 */
	void enterOverend(XSODataParser.OverendContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#overend}.
	 * @param ctx the parse tree
	 */
	void exitOverend(XSODataParser.OverendContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#storage}.
	 * @param ctx the parse tree
	 */
	void enterStorage(XSODataParser.StorageContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#storage}.
	 * @param ctx the parse tree
	 */
	void exitStorage(XSODataParser.StorageContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#nostorage}.
	 * @param ctx the parse tree
	 */
	void enterNostorage(XSODataParser.NostorageContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#nostorage}.
	 * @param ctx the parse tree
	 */
	void exitNostorage(XSODataParser.NostorageContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#storageend}.
	 * @param ctx the parse tree
	 */
	void enterStorageend(XSODataParser.StorageendContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#storageend}.
	 * @param ctx the parse tree
	 */
	void exitStorageend(XSODataParser.StorageendContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#annotations}.
	 * @param ctx the parse tree
	 */
	void enterAnnotations(XSODataParser.AnnotationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#annotations}.
	 * @param ctx the parse tree
	 */
	void exitAnnotations(XSODataParser.AnnotationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#annotationsbody}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationsbody(XSODataParser.AnnotationsbodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#annotationsbody}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationsbody(XSODataParser.AnnotationsbodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#annotationconfig}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationconfig(XSODataParser.AnnotationconfigContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#annotationconfig}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationconfig(XSODataParser.AnnotationconfigContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#settings}.
	 * @param ctx the parse tree
	 */
	void enterSettings(XSODataParser.SettingsContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#settings}.
	 * @param ctx the parse tree
	 */
	void exitSettings(XSODataParser.SettingsContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#settingsbody}.
	 * @param ctx the parse tree
	 */
	void enterSettingsbody(XSODataParser.SettingsbodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#settingsbody}.
	 * @param ctx the parse tree
	 */
	void exitSettingsbody(XSODataParser.SettingsbodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#settingselement}.
	 * @param ctx the parse tree
	 */
	void enterSettingselement(XSODataParser.SettingselementContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#settingselement}.
	 * @param ctx the parse tree
	 */
	void exitSettingselement(XSODataParser.SettingselementContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#supportnull}.
	 * @param ctx the parse tree
	 */
	void enterSupportnull(XSODataParser.SupportnullContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#supportnull}.
	 * @param ctx the parse tree
	 */
	void exitSupportnull(XSODataParser.SupportnullContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#contentcashecontrol}.
	 * @param ctx the parse tree
	 */
	void enterContentcashecontrol(XSODataParser.ContentcashecontrolContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#contentcashecontrol}.
	 * @param ctx the parse tree
	 */
	void exitContentcashecontrol(XSODataParser.ContentcashecontrolContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#metadatacashecontrol}.
	 * @param ctx the parse tree
	 */
	void enterMetadatacashecontrol(XSODataParser.MetadatacashecontrolContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#metadatacashecontrol}.
	 * @param ctx the parse tree
	 */
	void exitMetadatacashecontrol(XSODataParser.MetadatacashecontrolContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#hints}.
	 * @param ctx the parse tree
	 */
	void enterHints(XSODataParser.HintsContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#hints}.
	 * @param ctx the parse tree
	 */
	void exitHints(XSODataParser.HintsContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#hintlist}.
	 * @param ctx the parse tree
	 */
	void enterHintlist(XSODataParser.HintlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#hintlist}.
	 * @param ctx the parse tree
	 */
	void exitHintlist(XSODataParser.HintlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#hintvalue}.
	 * @param ctx the parse tree
	 */
	void enterHintvalue(XSODataParser.HintvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#hintvalue}.
	 * @param ctx the parse tree
	 */
	void exitHintvalue(XSODataParser.HintvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#nullvalue}.
	 * @param ctx the parse tree
	 */
	void enterNullvalue(XSODataParser.NullvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#nullvalue}.
	 * @param ctx the parse tree
	 */
	void exitNullvalue(XSODataParser.NullvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#limits}.
	 * @param ctx the parse tree
	 */
	void enterLimits(XSODataParser.LimitsContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#limits}.
	 * @param ctx the parse tree
	 */
	void exitLimits(XSODataParser.LimitsContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#limitvalue}.
	 * @param ctx the parse tree
	 */
	void enterLimitvalue(XSODataParser.LimitvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#limitvalue}.
	 * @param ctx the parse tree
	 */
	void exitLimitvalue(XSODataParser.LimitvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#maxrecords}.
	 * @param ctx the parse tree
	 */
	void enterMaxrecords(XSODataParser.MaxrecordsContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#maxrecords}.
	 * @param ctx the parse tree
	 */
	void exitMaxrecords(XSODataParser.MaxrecordsContext ctx);
	/**
	 * Enter a parse tree produced by {@link XSODataParser#maxexpandedrecords}.
	 * @param ctx the parse tree
	 */
	void enterMaxexpandedrecords(XSODataParser.MaxexpandedrecordsContext ctx);
	/**
	 * Exit a parse tree produced by {@link XSODataParser#maxexpandedrecords}.
	 * @param ctx the parse tree
	 */
	void exitMaxexpandedrecords(XSODataParser.MaxexpandedrecordsContext ctx);
}