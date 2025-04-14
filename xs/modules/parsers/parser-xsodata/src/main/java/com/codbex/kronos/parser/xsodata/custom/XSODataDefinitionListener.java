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
package com.codbex.kronos.parser.xsodata.custom;

import com.codbex.kronos.parser.xsodata.core.XSODataBaseListener;
import com.codbex.kronos.parser.xsodata.core.XSODataParser.*;
import org.antlr.v4.runtime.ParserRuleContext;

import com.codbex.kronos.parser.xsodata.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The listener interface for receiving HDBXSODataCore events. The class that is interested in
 * processing a HDBXSODataCore event implements this interface, and the object created with that
 * class is registered with a component using the component's <code>addHDBXSODataCoreListener</code>
 * method. When the HDBXSODataCore event occurs, that object's appropriate method is invoked.
 *
 */
public class XSODataDefinitionListener extends XSODataBaseListener {

    /** The service model. */
    private final XSODataService serviceModel = new XSODataService();

    /** The nav entries. */
    private ArrayList<NaventryContext> navEntries = new ArrayList<>();

    /**
     * Exit xsodata definition.
     *
     * @param ctx the ctx
     */
    @Override
    public void exitXsOdataDefinition(XsOdataDefinitionContext ctx) {
        // Set Annotations
        if (ctx.annotations() != null) {
            if (ctx.annotations()
                   .annotationsbody() != null) {
                ctx.annotations()
                   .annotationsbody()
                   .annotationconfig()
                   .forEach(el -> {
                       if (el.getChild(1)
                             .getText()
                             .equals("OData4SAP")) {
                           serviceModel.setEnableOData4SAPAnnotations(true);
                       }
                   });
            }
        }

        // Set Service
        if (ctx.service() != null) {
            if (ctx.service()
                   .namespace() != null) {
                serviceModel.setNamespace(handleStringLiteral(ctx.service()
                                                                 .namespace()
                                                                 .QUATED_STRING()
                                                                 .getText()));
            }
        }

        // Set Settings
        if (ctx.settings() != null) {
            if (ctx.settings()
                   .settingsbody() != null
                    && ctx.settings()
                          .settingsbody()
                          .settingselement() != null) {
                XSODataSetting setting = new XSODataSetting();
                ctx.settings()
                   .settingsbody()
                   .settingselement()
                   .forEach(el -> {
                       if (el.supportnull() != null) {
                           setting.setEnableSupportNull(true);
                       }
                       if (el.contentcashecontrol() != null) {
                           setting.setContentCacheControl(Arrays.asList(handleStringLiteral(el.contentcashecontrol()
                                                                                              .QUATED_STRING()
                                                                                              .getText()).split(",")));
                       }
                       if (el.metadatacashecontrol() != null) {
                           setting.setMetadataCacheControl(Arrays.asList(handleStringLiteral(el.metadatacashecontrol()
                                                                                               .QUATED_STRING()
                                                                                               .getText()).split(",")));
                       }
                       if (el.hints() != null) {
                           if (el.hints()
                                 .hintlist() != null) {
                               setting.setHints(el.hints()
                                                  .hintlist()
                                                  .hintvalue()
                                                  .stream()
                                                  .map(value -> handleStringLiteral(value.getText()))
                                                  .collect(Collectors.toList()));
                           }
                           if (el.hints()
                                 .nullvalue() != null) {
                               setting.setHints(Collections.singletonList("null"));
                           }
                       }
                       if (el.limits() != null) {
                           el.limits()
                             .limitvalue()
                             .forEach(limit -> {
                                 if (limit.maxrecords() != null) {
                                     setting.setMaxRecords(limit.INT()
                                                                .getText());
                                 }
                                 if (limit.maxexpandedrecords() != null) {
                                     setting.setMaxExpandedRecords(limit.INT()
                                                                        .getText());
                                 }
                             });
                       }
                       serviceModel.setSetting(setting);
                   });
            }
        }
    }

    /**
     * Exit entity.
     *
     * @param ctx the ctx
     */
    @Override
    public void exitEntity(EntityContext ctx) {
        XSODataEntity entity = new XSODataEntity();

        // Set Object
        if (ctx.object() != null) {
            XSODataRepositoryObject repObject = new XSODataRepositoryObject();
            if (ctx.object()
                   .catalogobjectschema() != null) {
                repObject.setCatalogObjectSchema(handleStringLiteral(ctx.object()
                                                                        .catalogobjectschema()
                                                                        .getText()));
            }
            if (ctx.object()
                   .catalogobjectname() != null) {
                repObject.setCatalogObjectName(handleStringLiteral(ctx.object()
                                                                      .catalogobjectname()
                                                                      .getText()));
            }
            entity.setRepositoryObject(repObject);
        }

        // Set EntitySet
        if (ctx.entityset() != null) {
            entity.setAlias(handleStringLiteral(ctx.entityset()
                                                   .entitysetname()
                                                   .getText()));
        }

        // Set Navigations
        this.navEntries.forEach(el -> {
            XSODataNavigation navProp = new XSODataNavigation();
            navProp.setAssociation(handleStringLiteral(el.assocname()
                                                         .getText()));
            navProp.setAliasNavigation(handleStringLiteral(el.navpropname()
                                                             .getText()));
            if (el.fromend() != null) {
                if (el.fromend()
                      .principal() != null) {
                    navProp.setFromBindingType(XSODataBindingType.fromValue(el.fromend()
                                                                              .principal()
                                                                              .getText())
                                                                 .get());
                }
                if (el.fromend()
                      .dependent() != null) {
                    navProp.setFromBindingType(XSODataBindingType.fromValue(el.fromend()
                                                                              .dependent()
                                                                              .getText())
                                                                 .get());
                }
            }
            entity.getNavigates()
                  .add(navProp);
        });

        // Set Property Projection
        if (ctx.with() != null) {
            if (ctx.with()
                   .withoutProp() != null
                    && ctx.with()
                          .propertylist() != null) {
                entity.setWithoutPropertyProjections(ctx.with()
                                                        .propertylist()
                                                        .columnname()
                                                        .stream()
                                                        .map(el -> handleStringLiteral(el.getText()))
                                                        .collect(Collectors.toList()));
            }
            if (ctx.with()
                   .withProp() != null
                    && ctx.with()
                          .propertylist() != null) {
                entity.setWithPropertyProjections(ctx.with()
                                                     .propertylist()
                                                     .columnname()
                                                     .stream()
                                                     .map(el -> handleStringLiteral(el.getText()))
                                                     .collect(Collectors.toList()));
            }
        }

        // Set Keys
        if (ctx.keys() != null) {
            if (ctx.keys()
                   .keygenerated() != null) {
                entity.setKeyGenerated(handleStringLiteral(ctx.keys()
                                                              .keygenerated()
                                                              .columnname()
                                                              .getText()));
            }
            if (ctx.keys()
                   .keylist() != null) {
                entity.setKeyList(ctx.keys()
                                     .keylist()
                                     .propertylist()
                                     .columnname()
                                     .stream()
                                     .map(el -> handleStringLiteral(el.getText()))
                                     .collect(Collectors.toList()));
            }
        }

        // Set Etag
        if (ctx.concurrencytoken() != null) {
            entity.setConcurrencyToken(true);
            if (ctx.concurrencytoken()
                   .keylist() != null) {
                entity.setETags(ctx.concurrencytoken()
                                   .keylist()
                                   .propertylist()
                                   .columnname()
                                   .stream()
                                   .map(el -> handleStringLiteral(el.getText()))
                                   .collect(Collectors.toList()));
            }
        }

        // Set Aggregations
        if (ctx.aggregates() != null) {
            if (ctx.aggregates()
                   .aggregatestuple() != null) {
                ctx.aggregates()
                   .aggregatestuple()
                   .aggregate()
                   .forEach(el -> {
                       XSODataAggregation newAggregation = new XSODataAggregation();
                       newAggregation.setAggregateFunction(el.aggregatefunction()
                                                             .getText());
                       newAggregation.setAggregateColumnName(handleStringLiteral(el.columnname()
                                                                                   .getText()));
                       entity.getAggregations()
                             .add(newAggregation);
                   });
                entity.setAggregationType(XSODataAggregationType.EXPLICIT);
            } else {
                entity.setAggregationType(XSODataAggregationType.IMPLICIT);
            }
        }

        // Set Parameters
        if (ctx.parameters() != null) {
            if (ctx.parameters()
                   .parameterskeyand() != null) {
                entity.setParameterType(XSODataParameterType.KEY_AND_ENTITY);
            } else {
                entity.setParameterType(XSODataParameterType.ENTITY);
            }
            XSODataParameter parameterEntitySets = new XSODataParameter();
            if (ctx.parameters()
                   .parameterentitysetname() != null) {
                parameterEntitySets.setParameterEntitySetName(handleStringLiteral(ctx.parameters()
                                                                                     .parameterentitysetname()
                                                                                     .getText()));
            }
            if (ctx.parameters()
                   .parametersresultsprop() != null) {
                parameterEntitySets.setParameterResultsProperty(handleStringLiteral(ctx.parameters()
                                                                                       .parametersresultsprop()
                                                                                       .QUATED_STRING()
                                                                                       .getText()));
            }
            entity.setParameterEntitySet(parameterEntitySets);
        }

        // Set Events
        if (ctx.modificationBody() != null) {
            entity.setModifications(assembleModificationsFromContext(ctx.modificationBody()));
        }

        serviceModel.getEntities()
                    .add(entity);
        this.navEntries = new ArrayList<>();
    }

    /**
     * Exit association.
     *
     * @param ctx the ctx
     */
    @Override
    public void exitAssociation(AssociationContext ctx) {
        XSODataAssociation association = new XSODataAssociation();

        // Set AssociationName
        if (ctx.associationdef() != null) {
            association.setName(handleStringLiteral(ctx.associationdef()
                                                       .assocname()
                                                       .getText()));
        }

        // Set WithReferentialConstraint
        if (ctx.assocrefconstraint() != null) {
            association.setWithReferentialConstraint(true);
        }

        // Set Principal
        if (ctx.principalend() != null) {
            XSODataBinding principal = assembleBindingFromAssociationContext(ctx.principalend(), XSODataBindingType.PRINCIPAL);
            association.setPrincipal(principal);
        }

        // Set Dependent
        if (ctx.dependentend() != null) {
            XSODataBinding dependent = assembleBindingFromAssociationContext(ctx.dependentend(), XSODataBindingType.DEPENDENT);
            association.setDependent(dependent);
        }

        // Set AssociationTable
        if (ctx.assoctable() != null) {
            XSODataAssociationTable associationTable = new XSODataAssociationTable();
            associationTable.setRepositoryObject(handleStringLiteral(ctx.assoctable()
                                                                        .repoobject()
                                                                        .QUATED_STRING()
                                                                        .getText()));
            // Set Principal
            if (ctx.assoctable()
                   .overprincipalend() != null) {
                associationTable.setPrincipal(assembleBindingRoleFromAssociationTableContext(ctx.assoctable()
                                                                                                .overprincipalend(),
                        XSODataBindingType.PRINCIPAL));
            }
            // Set Dependent
            if (ctx.assoctable()
                   .overdependentend() != null) {
                associationTable.setDependent(assembleBindingRoleFromAssociationTableContext(ctx.assoctable()
                                                                                                .overdependentend(),
                        XSODataBindingType.DEPENDENT));
            }
            // Set Events
            if (ctx.assoctable()
                   .modificationBody() != null) {
                associationTable.setModifications(assembleModificationsFromContext(ctx.assoctable()
                                                                                      .modificationBody()));
            }
            association.setAssociationTable(associationTable);
        }

        // Set Storage
        if (ctx.storage() != null) {
            XSODataStorage storage = new XSODataStorage();
            if (ctx.storage()
                   .nostorage() != null) {
                storage.setStorageType(XSODataStorageType.NO_STORAGE);
            }
            if (ctx.storage()
                   .storageend() != null && XSODataStorageType
                                                              .fromValue(ctx.storage()
                                                                            .storageend()
                                                                            .getText())
                                                              .isPresent()) {
                storage.setStorageType(XSODataStorageType.fromValue(ctx.storage()
                                                                       .storageend()
                                                                       .getText())
                                                         .get());
            }
            if (ctx.storage()
                   .modificationBody() != null) {
                storage.setModifications(assembleModificationsFromContext(ctx.storage()
                                                                             .modificationBody()));
            }
            association.setStorage(storage);
        }

        // Set Events
        if (ctx.modificationBody() != null) {
            association.setModifications(assembleModificationsFromContext(ctx.modificationBody()));
        }

        serviceModel.getAssociations()
                    .add(association);
    }

    /**
     * Exit naventry.
     *
     * @param ctx the ctx
     */
    @Override
    public void exitNaventry(NaventryContext ctx) {
        this.navEntries.add(ctx);
    }

    /**
     * Gets the service model.
     *
     * @return the service model
     */
    public XSODataService getServiceModel() {
        return serviceModel;
    }

    /**
     * Handle string literal.
     *
     * @param value the value
     * @return the string
     */
    private String handleStringLiteral(String value) {
        if (value != null && value.length() > 1) {
            String subStr = value.substring(1, value.length() - 1);
            String escapedQuote = subStr.replace("\\\"", "\"");
            return escapedQuote.replace("\\\\", "\\");
        }
        return null;
    }

    /**
     * Assemble binding from association context.
     *
     * @param context the context
     * @param type the type
     * @return the HDBXSO data binding
     */
    private XSODataBinding assembleBindingFromAssociationContext(ParserRuleContext context, XSODataBindingType type) {
        XSODataBindingRole role = new XSODataBindingRole();
        XSODataBinding binding = new XSODataBinding();
        role.setBindingType(type);
        EndContext endContext = (EndContext) context.children.stream()
                                                             .filter(x -> x instanceof EndContext)
                                                             .findAny()
                                                             .orElse(null);
        if (endContext != null) {
            role.setKeys(endContext.endref()
                                   .joinpropertieslist()
                                   .propertylist()
                                   .columnname()
                                   .stream()
                                   .map(el -> handleStringLiteral(el.getText()))
                                   .collect(Collectors.toList()));
            binding.setEntitySetName(handleStringLiteral(endContext.endref()
                                                                   .endtype()
                                                                   .entitysetname()
                                                                   .getText()));
            binding.setBindingRole(role);
            Optional<XSODataMultiplicityType> multiType = XSODataMultiplicityType.fromValue(handleStringLiteral(endContext.multiplicity()
                                                                                                                          .multiplicityvalue()
                                                                                                                          .getText()));
            multiType.ifPresent(binding::setMultiplicityType);
        }
        return binding;
    }

    /**
     * Assemble binding role from association table context.
     *
     * @param context the context
     * @param type the type
     * @return the HDBXSO data binding role
     */
    private XSODataBindingRole assembleBindingRoleFromAssociationTableContext(ParserRuleContext context, XSODataBindingType type) {
        XSODataBindingRole bindingRole = new XSODataBindingRole();
        bindingRole.setBindingType(type);
        OverendContext endContext = (OverendContext) context.children.stream()
                                                                     .filter(x -> x instanceof OverendContext)
                                                                     .findAny()
                                                                     .orElse(null);
        if (endContext != null) {
            bindingRole.setKeys(endContext.propertylist()
                                          .columnname()
                                          .stream()
                                          .map(el -> handleStringLiteral(el.getText()))
                                          .collect(Collectors.toList()));
        }
        return bindingRole;
    }

    /**
     * Assemble modifications from context.
     *
     * @param modificationBody the modification body
     * @return the list
     */
    private List<XSODataModification> assembleModificationsFromContext(ModificationBodyContext modificationBody) {
        List<XSODataModification> modifications = new ArrayList<>();

        modificationBody.modification()
                        .forEach(el -> {
                            XSODataModification modification = new XSODataModification();
                            XSODataModificationSpec modificationSpec = new XSODataModificationSpec();
                            ModificationspecContext spec = null;
                            if (el.update() != null) {
                                modification.setMethod(XSODataHandlerMethod.UPDATE);
                                spec = el.update()
                                         .modificationspec();
                            } else if (el.create() != null) {
                                modification.setMethod(XSODataHandlerMethod.CREATE);
                                spec = el.create()
                                         .modificationspec();
                            } else if (el.delete() != null) {
                                modification.setMethod(XSODataHandlerMethod.DELETE);
                                spec = el.delete()
                                         .modificationspec();
                            }

                            if (spec != null && spec.events() != null) {
                                spec.events()
                                    .eventlist()
                                    .eventlistElement()
                                    .forEach(eventListEl -> {
                                        XSODataEvent event = new XSODataEvent();
                                        Optional<XSODataEventType> eventType = XSODataEventType.fromValue(eventListEl.eventtype()
                                                                                                                     .getText());
                                        eventType.ifPresent(event::setType);
                                        event.setAction(handleStringLiteral(eventListEl.action()
                                                                                       .getText()));
                                        modificationSpec.getEvents()
                                                        .add(event);
                                    });
                            }
                            if (spec != null && spec.modificationaction() != null) {
                                modificationSpec.setModificationAction(handleStringLiteral(spec.modificationaction()
                                                                                               .action()
                                                                                               .getText()));
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
