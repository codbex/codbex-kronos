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
package com.codbex.kronos.parser.xsodata.custom;

import com.codbex.kronos.parser.xsodata.core.HdbxsodataBaseListener;
import com.codbex.kronos.parser.xsodata.core.HdbxsodataParser;
import com.codbex.kronos.parser.xsodata.model.*;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.*;
import java.util.stream.Collectors;

public class HDBXSODataCoreListener extends HdbxsodataBaseListener {


    private final HDBXSODataService serviceModel = new HDBXSODataService();
    private ArrayList<HdbxsodataParser.NaventryContext> navEntries = new ArrayList<>();

    @Override
    public void exitXsodataDefinition(HdbxsodataParser.XsodataDefinitionContext ctx) {
        //set Annotations
        if (ctx.annotations() != null) {
            if (ctx.annotations().annotationsbody() != null) {
                ctx.annotations().annotationsbody().annotationconfig().forEach(el -> {
                    if (el.getChild(1).getText().equals("OData4SAP")) {
                        serviceModel.setEnableOData4SAPAnnotations(true);
                    }
                });
            }
        }

        //set Service
        if (ctx.service() != null) {
            if (ctx.service().namespace() != null) {
                serviceModel.setNamespace(handleStringLiteral(ctx.service().namespace().QUATED_STRING().getText()));
            }
        }

        //set Settings
        if (ctx.settings() != null) {
            if (ctx.settings().settingsbody() != null && ctx.settings().settingsbody().settingselement() != null) {
                HDBXSODataSetting setting = new HDBXSODataSetting();
                ctx.settings().settingsbody().settingselement().forEach(el -> {
                    if (el.supportnull() != null) {
                        setting.setEnableSupportNull(true);
                    }
                    if (el.contentcashecontrol() != null) {
                        setting.setContentCacheControl(Arrays.asList(handleStringLiteral(el.contentcashecontrol().QUATED_STRING().getText()).split(",")));
                    }
                    if (el.metadatacashecontrol() != null) {
                        setting.setMetadataCacheControl(Arrays.asList(handleStringLiteral(el.metadatacashecontrol().QUATED_STRING().getText()).split(",")));
                    }
                    if (el.hints() != null) {
                        if (el.hints().hintlist() != null) {
                            setting.setHints(el.hints().hintlist().hintvalue().stream().map(value -> handleStringLiteral(value.getText())).collect(Collectors.toList()));
                        }
                        if (el.hints().nullvalue() != null) {
                            setting.setHints(Collections.singletonList("null"));
                        }
                    }
                    if (el.limits() != null) {
                        el.limits().limitvalue().forEach(limit -> {
                            if (limit.maxrecords() != null) {
                                setting.setMaxRecords(limit.INT().getText());
                            }
                            if (limit.maxexpandedrecords() != null) {
                                setting.setMaxExpandedRecords(limit.INT().getText());
                            }
                        });
                    }
                    serviceModel.setSetting(setting);
                });
            }
        }
    }

    @Override
    public void exitEntity(HdbxsodataParser.EntityContext ctx) {
        HDBXSODataEntity entity = new HDBXSODataEntity();

        //set Object
        if (ctx.object() != null) {
            HDBXSODataRepositoryObject repObject = new HDBXSODataRepositoryObject();
            if (ctx.object().catalogobjectschema() != null) {
                repObject.setCatalogObjectSchema(handleStringLiteral(ctx.object().catalogobjectschema().getText()));
            }
            if (ctx.object().catalogobjectname() != null) {
                repObject.setCatalogObjectName(handleStringLiteral(ctx.object().catalogobjectname().getText()));
            }
            entity.setRepositoryObject(repObject);
        }

        //set EntitySet
        if (ctx.entityset() != null) {
            entity.setAlias(handleStringLiteral(ctx.entityset().entitysetname().getText()));
        }

        //set Navigations
        this.navEntries.forEach(el -> {
            HDBXSODataNavigation navProp = new HDBXSODataNavigation();
            navProp.setAssociation(handleStringLiteral(el.assocname().getText()));
            navProp.setAliasNavigation(handleStringLiteral(el.navpropname().getText()));
            if(el.fromend()!=null){
                if(el.fromend().principal()!=null){
                    navProp.setFromBindingType(HDBXSODataBindingType.fromValue(el.fromend().principal().getText()).get());
                }
                if(el.fromend().dependent()!=null){
                    navProp.setFromBindingType(HDBXSODataBindingType.fromValue(el.fromend().dependent().getText()).get());
                }
            }
            entity.getNavigates().add(navProp);
        });

        //set Property Projection
        if (ctx.with() != null) {
            if (ctx.with().withoutProp() != null && ctx.with().propertylist() != null) {
                entity.setWithoutPropertyProjections(ctx.with().propertylist().columnname().stream().map(el -> handleStringLiteral(el.getText())).collect(Collectors.toList()));
            }
            if (ctx.with().withProp() != null && ctx.with().propertylist() != null) {
                entity.setWithPropertyProjections(ctx.with().propertylist().columnname().stream().map(el -> handleStringLiteral(el.getText())).collect(Collectors.toList()));
            }
        }

        //set Keys
        if (ctx.keys() != null) {
            if (ctx.keys().keygenerated() != null) {
                entity.setKeyGenerated(handleStringLiteral(ctx.keys().keygenerated().columnname().getText()));
            }
            if (ctx.keys().keylist() != null) {
                entity.setKeyList(ctx.keys().keylist().propertylist().columnname().stream().map(el -> handleStringLiteral(el.getText())).collect(Collectors.toList()));
            }
        }

        //set Etag
        if (ctx.concurrencytoken() != null) {
            entity.setConcurrencyToken(true);
            if (ctx.concurrencytoken().keylist() != null) {
                entity.setETags(ctx.concurrencytoken().keylist().propertylist().columnname().stream().map(el -> handleStringLiteral(el.getText())).collect(Collectors.toList()));
            }
        }

        //set Aggregations
        if (ctx.aggregates() != null) {
            if (ctx.aggregates().aggregatestuple() != null) {
                ctx.aggregates().aggregatestuple().aggregate().forEach(el -> {
                    HDBXSODataAggregation newAggregation = new HDBXSODataAggregation();
                    newAggregation.setAggregateFunction(el.aggregatefunction().getText());
                    newAggregation.setAggregateColumnName(handleStringLiteral(el.columnname().getText()));
                    entity.getAggregations().add(newAggregation);
                });
                entity.setAggregationType(HDBXSODataAggregationType.EXPLICIT);
            } else {
                entity.setAggregationType(HDBXSODataAggregationType.IMPLICIT);
            }
        }

        //set Parameters
        if (ctx.parameters() != null) {
            if (ctx.parameters().parameterskeyand() != null) {
                entity.setParameterType(HDBXSODataParameterType.KEY_AND_ENTITY);
            } else {
                entity.setParameterType(HDBXSODataParameterType.ENTITY);
            }
            HDBXSODataParameter parameterEntitySets = new HDBXSODataParameter();
            if (ctx.parameters().parameterentitysetname() != null) {
                parameterEntitySets.setParameterEntitySetName(handleStringLiteral(ctx.parameters().parameterentitysetname().getText()));
            }
            if (ctx.parameters().parametersresultsprop() != null) {
                parameterEntitySets.setParameterResultsProperty(handleStringLiteral(ctx.parameters().parametersresultsprop().QUATED_STRING().getText()));
            }
            entity.setParameterEntitySet(parameterEntitySets);
        }

        //set Events
        if (ctx.modificationBody() != null) {
            entity.setModifications(assembleModificationsFromContext(ctx.modificationBody()));
        }

        serviceModel.getEntities().add(entity);
        this.navEntries = new ArrayList<>();
    }

    @Override
    public void exitAssociation(HdbxsodataParser.AssociationContext ctx) {
        HDBXSODataAssociation association = new HDBXSODataAssociation();

        //set AssociationName
        if (ctx.associationdef() != null) {
            association.setName(handleStringLiteral(ctx.associationdef().assocname().getText()));
        }

        //set WithReferentialConstraint
        if (ctx.assocrefconstraint() != null) {
            association.setWithReferentialConstraint(true);
        }

        //set Principal
        if (ctx.principalend() != null) {
            HDBXSODataBinding principal = assembleBindingFromAssociationContext(ctx.principalend(), HDBXSODataBindingType.PRINCIPAL);
            association.setPrincipal(principal);
        }

        //set Dependent
        if (ctx.dependentend() != null) {
            HDBXSODataBinding dependent = assembleBindingFromAssociationContext(ctx.dependentend(), HDBXSODataBindingType.DEPENDENT);
            association.setDependent(dependent);
        }

        //set AssociationTable
        if (ctx.assoctable() != null) {
            HDBXSODataAssociationTable associationTable = new HDBXSODataAssociationTable();
            associationTable.setRepositoryObject(handleStringLiteral(ctx.assoctable().repoobject().QUATED_STRING().getText()));
            //set Principal
            if (ctx.assoctable().overprincipalend() != null) {
                associationTable.setPrincipal(assembleBindingRoleFromAssociationTableContext(ctx.assoctable().overprincipalend(), HDBXSODataBindingType.PRINCIPAL));
            }
            //set Dependent
            if (ctx.assoctable().overdependentend() != null) {
                associationTable.setDependent(assembleBindingRoleFromAssociationTableContext(ctx.assoctable().overdependentend(), HDBXSODataBindingType.DEPENDENT));
            }
            //set Events
            if (ctx.assoctable().modificationBody() != null) {
                associationTable.setModifications(assembleModificationsFromContext(ctx.assoctable().modificationBody()));
            }
            association.setAssociationTable(associationTable);
        }

        //set Storage
        if (ctx.storage() != null) {
            HDBXSODataStorage storage = new HDBXSODataStorage();
            if (ctx.storage().nostorage() != null) {
                storage.setStorageType(HDBXSODataStorageType.NO_STORAGE);
            }
            if (ctx.storage().storageend() != null && HDBXSODataStorageType.fromValue(ctx.storage().storageend().getText()).isPresent()) {
                storage.setStorageType(HDBXSODataStorageType.fromValue(ctx.storage().storageend().getText()).get());
            }
            if (ctx.storage().modificationBody() != null) {
                storage.setModifications(assembleModificationsFromContext(ctx.storage().modificationBody()));
            }
            association.setStorage(storage);
        }

        //set Events
        if (ctx.modificationBody() != null) {
            association.setModifications(assembleModificationsFromContext(ctx.modificationBody()));
        }

        serviceModel.getAssociations().add(association);
    }

    @Override
    public void exitNaventry(HdbxsodataParser.NaventryContext ctx) {
        this.navEntries.add(ctx);
    }

    public HDBXSODataService getServiceModel() {
        return serviceModel;
    }

    private String handleStringLiteral(String value) {
        if (value != null && value.length() > 1) {
            String subStr = value.substring(1, value.length() - 1);
            String escapedQuote = subStr.replace("\\\"", "\"");
            return escapedQuote.replace("\\\\", "\\");
        }
        return null;
    }

    private HDBXSODataBinding assembleBindingFromAssociationContext(ParserRuleContext context, HDBXSODataBindingType type) {
        HDBXSODataBindingRole role = new HDBXSODataBindingRole();
        HDBXSODataBinding binding = new HDBXSODataBinding();
        role.setBindingType(type);
        HdbxsodataParser.EndContext endContext = (HdbxsodataParser.EndContext) context.children.stream().filter(x -> x instanceof HdbxsodataParser.EndContext).findAny().orElse(null);
        if (endContext != null) {
            role.setKeys(endContext.endref().joinpropertieslist().propertylist().columnname().stream().map(el -> handleStringLiteral(el.getText())).collect(Collectors.toList()));
            binding.setEntitySetName(handleStringLiteral(endContext.endref().endtype().entitysetname().getText()));
            binding.setBindingRole(role);
            Optional<HDBXSODataMultiplicityType> multiType = HDBXSODataMultiplicityType.fromValue(handleStringLiteral(endContext.multiplicity().multiplicityvalue().getText()));
            multiType.ifPresent(binding::setMultiplicityType);
        }
        return binding;
    }

    private HDBXSODataBindingRole assembleBindingRoleFromAssociationTableContext(ParserRuleContext context, HDBXSODataBindingType type) {
        HDBXSODataBindingRole bindingRole = new HDBXSODataBindingRole();
        bindingRole.setBindingType(type);
        HdbxsodataParser.OverendContext endContext = (HdbxsodataParser.OverendContext) context.children.stream().filter(x -> x instanceof HdbxsodataParser.OverendContext).findAny().orElse(null);
        if (endContext != null) {
            bindingRole.setKeys(endContext.propertylist().columnname().stream().map(el -> handleStringLiteral(el.getText())).collect(Collectors.toList()));
        }
        return bindingRole;
    }

    private List<HDBXSODataModification> assembleModificationsFromContext(HdbxsodataParser.ModificationBodyContext modificationBody) {
        List<HDBXSODataModification> modifications = new ArrayList<>();

        modificationBody.modification().forEach(el -> {
            HDBXSODataModification modification = new HDBXSODataModification();
            HDBXSODataModificationSpec modificationSpec = new HDBXSODataModificationSpec();
            HdbxsodataParser.ModificationspecContext spec = null;
            if (el.update() != null) {
                modification.setMethod(HDBXSODataHandlerMethod.UPDATE);
                spec = el.update().modificationspec();
            } else if (el.create() != null) {
                modification.setMethod(HDBXSODataHandlerMethod.CREATE);
                spec = el.create().modificationspec();
            } else if (el.delete() != null) {
                modification.setMethod(HDBXSODataHandlerMethod.DELETE);
                spec = el.delete().modificationspec();
            }

            if (spec != null && spec.events() != null) {
                spec.events().eventlist().eventlistElement().forEach(eventListEl -> {
                    HDBXSODataEvent event = new HDBXSODataEvent();
                    Optional<HDBXSODataEventType> eventType = HDBXSODataEventType.fromValue(eventListEl.eventtype().getText());
                    eventType.ifPresent(event::setType);
                    event.setAction(handleStringLiteral(eventListEl.action().getText()));
                    modificationSpec.getEvents().add(event);
                });
            }
            if (spec != null && spec.modificationaction() != null) {
                modificationSpec.setModificationAction(handleStringLiteral(spec.modificationaction().action().getText()));
            }
            if (spec != null && spec.forbidden() != null) {
                modificationSpec.setForbidden(true);
            }
            modification.setSpecification(modificationSpec);
            modifications.add(modification);
        });
        return modifications;
    }
}