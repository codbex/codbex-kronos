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
package com.codbex.kronos.parser.xsodata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import com.codbex.kronos.parser.xsodata.core.HdbxsodataLexer;
import com.codbex.kronos.parser.xsodata.core.HdbxsodataParser;
import com.codbex.kronos.parser.xsodata.custom.HDBXSODataCoreListener;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataAggregation;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataAggregationType;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataAssociation;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataAssociationTable;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataBinding;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataBindingRole;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataBindingType;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataEntity;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataEvent;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataEventType;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataHandlerMethod;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataModification;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataModificationSpec;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataMultiplicityType;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataNavigation;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataRepositoryObject;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataService;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataSetting;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataStorage;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataStorageType;

public class HDBXSODataCoreListenerTest {
    private final HDBXSODataCoreListener listener = new HDBXSODataCoreListener();

    @Test
    public void parseEmptyServiceSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("empty_service.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertNull(model.getNamespace());
        assertEquals(model.getEntities().size(), 0);
        assertFalse(model.isEnableOData4SAPAnnotations());
    }

    @Test
    public void parseEmptyServiceWithNamespaceDefinitionSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("empty_service_with_namespace.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getNamespace(), "my.demo.namespace");
        assertEquals(model.getEntities().size(), 0);
        assertFalse(model.isEnableOData4SAPAnnotations());
    }

    @Test
    public void parseEmptyServiceWithAnnotationSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("empty_service_with_annotations.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertNull(model.getNamespace());
        assertEquals(model.getEntities().size(), 0);
        assertTrue(model.isEnableOData4SAPAnnotations());
    }

    @Test
    public void parseEmptyServiceWithSettingsSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("empty_service_with_settings.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertNull(model.getNamespace());
        assertEquals(model.getEntities().size(), 0);
        assertEquals(model.getAssociations().size(), 0);
        assertNotNull(model.getSetting());
        HDBXSODataSetting actualSetting = new HDBXSODataSetting();
        actualSetting.setEnableSupportNull(true);
        actualSetting.setMaxExpandedRecords("30");
        actualSetting.setMaxRecords("10");
        actualSetting.setContentCacheControl(Collections.singletonList("no-store"));
        actualSetting.setMetadataCacheControl(Arrays.asList("no-cache", "no-store", "max-age=86401", "must-revalidate"));
        actualSetting.setHints(Arrays.asList("NO_CALC_VIEW_UNFOLDING", "ABC"));
        assertEquals(model.getSetting(), actualSetting);
    }

    @Test
    public void parseEmptyServiceWithSettingsWithHintsAsNullSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("empty_service_without_hints.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertNull(model.getNamespace());
        assertEquals(model.getEntities().size(), 0);
        assertEquals(model.getAssociations().size(), 0);
        assertNotNull(model.getSetting());
        HDBXSODataSetting actualSetting = new HDBXSODataSetting();
        actualSetting.setHints(Collections.singletonList("null"));
        assertEquals(model.getSetting(), actualSetting);
    }

    @Test
    public void parseServiceWithObjectsSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("entity_with_object_exposure.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities().size(), 2);

        HDBXSODataEntity actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::table").setCatalogObjectSchema("XSODATA_TEST"));
        actualEntity.setAlias("MyTable");
        assertEquals(model.getEntities().get(0), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("test/AN_AGENCY_REV.analyticView"));
        actualEntity.setAlias("Revenue");
        assertEquals(model.getEntities().get(1), actualEntity);
    }

    @Test
    public void parseServiceWithPropertyProjectionSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("entity_with_prop.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities().size(), 2);

        HDBXSODataEntity actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        actualEntity.setAlias("MyTable");
        actualEntity.setWithPropertyProjections(Arrays.asList("ID", "Text"));
        assertEquals(model.getEntities().get(0), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        actualEntity.setAlias("MyTable");
        actualEntity.setWithoutPropertyProjections(Arrays.asList("Text", "Time"));
        assertEquals(model.getEntities().get(1), actualEntity);
    }

    @Test
    public void parseServiceWithKeysSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("entity_with_keys.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities().size(), 4);

        HDBXSODataEntity actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::view"));
        actualEntity.setAlias("MyView");
        actualEntity.setKeyList(Arrays.asList("ID", "Text"));
        assertEquals(model.getEntities().get(0), actualEntity);
        assertEquals(model.getEntities().get(1), actualEntity);


        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::view"));
        actualEntity.setAlias("MyView");
        actualEntity.setKeyGenerated("GenID");
        assertEquals(model.getEntities().get(2), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("test/AN_AGENCY.analyticView"));
        actualEntity.setAlias("Revenue");
        actualEntity.setKeyGenerated("GENERATED_ID");
        assertEquals(model.getEntities().get(3), actualEntity);
    }

    @Test
    public void parseServiceWithNavigationsSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("entity_with_navigation.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertEquals(5, model.getEntities().size());

        HDBXSODataEntity actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::customer"));
        actualEntity.setAlias("Customers");
        HDBXSODataNavigation nav1 = new HDBXSODataNavigation().setAssociation("Customer_Orders").setAliasNavigation("HisOrders");
        HDBXSODataNavigation nav2 = new HDBXSODataNavigation().setAssociation("Customer_Recruit").setAliasNavigation("Recruit").setFromBindingType(HDBXSODataBindingType.DEPENDENT);
        actualEntity.setNavigates(Arrays.asList(nav1, nav2));
        assertEquals(model.getEntities().get(0), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::student"));
        actualEntity.setAlias("Students");
        actualEntity.setNavigates(Collections.singletonList(new HDBXSODataNavigation().setAssociation("Students_Grades").setAliasNavigation("HisGrades").setFromBindingType(HDBXSODataBindingType.PRINCIPAL)));
        assertEquals(model.getEntities().get(1), actualEntity);
    }

    @Test
    public void parseServiceWithAggregationsSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("entity_with_aggregations.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities().size(), 3);

        HDBXSODataEntity actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("package::revenues"));
        actualEntity.setAlias("Revenues");
        HDBXSODataAggregation agg1 = new HDBXSODataAggregation().setAggregateColumnName("Amount").setAggregateFunction("SUM");
        HDBXSODataAggregation agg2 = new HDBXSODataAggregation().setAggregateColumnName("Amount").setAggregateFunction("AVG");
        HDBXSODataAggregation agg3 = new HDBXSODataAggregation().setAggregateColumnName("Amount").setAggregateFunction("MIN");
        actualEntity.setAggregations(Arrays.asList(agg1, agg2, agg3));
        actualEntity.setKeyGenerated("ID");
        actualEntity.setAggregationType(HDBXSODataAggregationType.EXPLICIT);
        assertEquals(model.getEntities().get(0), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("package::revenues"));
        actualEntity.setAlias("Revenues");
        actualEntity.setKeyGenerated("ID");
        actualEntity.setAggregations(Collections.singletonList((new HDBXSODataAggregation()).setAggregateColumnName("Amount").setAggregateFunction("MAX")));
        actualEntity.setAggregationType(HDBXSODataAggregationType.EXPLICIT);
        assertEquals(model.getEntities().get(1), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("package::PLANNED_ACTUAL_SALES"));
        actualEntity.setAlias("PlannedCalcView");
        actualEntity.setKeyGenerated("ID");
        actualEntity.setAggregationType(HDBXSODataAggregationType.IMPLICIT);
        assertEquals(model.getEntities().get(2), actualEntity);
    }

    @Test
    public void parseServiceWithEventsSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("entity_with_events.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities().size(), 4);

        HDBXSODataEntity actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        HDBXSODataModification updateModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.UPDATE);
        updateModification.setSpecification(new HDBXSODataModificationSpec().setEvents(Arrays.asList(new HDBXSODataEvent(HDBXSODataEventType.BEFORE, "sample.odata::beforeMethod"), new HDBXSODataEvent(HDBXSODataEventType.PRECOMMIT, "sample.odata::beforeMethod"))));
        HDBXSODataModification deleteModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.DELETE);
        deleteModification.setSpecification(new HDBXSODataModificationSpec().setEvents(Collections.singletonList(new HDBXSODataEvent(HDBXSODataEventType.AFTER, "sample.odata::afterMethod"))));
        HDBXSODataModification createModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.CREATE);
        createModification.setSpecification(new HDBXSODataModificationSpec().setForbidden(true));
        actualEntity.setModifications(Arrays.asList(updateModification, deleteModification, createModification));
        assertEquals(model.getEntities().get(0), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        createModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.CREATE);
        createModification.setSpecification(new HDBXSODataModificationSpec().setModificationAction("sample.odata::createMethod").setEvents(Collections.singletonList(new HDBXSODataEvent(HDBXSODataEventType.AFTER, "sample.odata::afterMethod"))));
        updateModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.UPDATE);
        updateModification.setSpecification(new HDBXSODataModificationSpec().setModificationAction("sample.odata::updateMethod"));
        deleteModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.DELETE);
        deleteModification.setSpecification(new HDBXSODataModificationSpec().setModificationAction("sample.odata::deleteMethod"));
        actualEntity.setModifications(Arrays.asList(createModification, updateModification, deleteModification));
        assertEquals(model.getEntities().get(1), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        createModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.CREATE);
        createModification.setSpecification(new HDBXSODataModificationSpec().setModificationAction("sample.odata::createMethod"));
        updateModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.UPDATE);
        updateModification.setSpecification(new HDBXSODataModificationSpec().setEvents(Collections.singletonList(new HDBXSODataEvent(HDBXSODataEventType.PRECOMMIT, "sample.odata::precommitMethod"))));
        deleteModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.DELETE).setSpecification(new HDBXSODataModificationSpec().setForbidden(true));
        actualEntity.setModifications(Arrays.asList(createModification, updateModification, deleteModification));
        assertEquals(model.getEntities().get(2), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        createModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.CREATE).setSpecification(new HDBXSODataModificationSpec().setForbidden(true));
        updateModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.UPDATE).setSpecification(new HDBXSODataModificationSpec().setForbidden(true));
        deleteModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.DELETE).setSpecification(new HDBXSODataModificationSpec().setForbidden(true));
        actualEntity.setModifications(Arrays.asList(createModification, updateModification, deleteModification));
        assertEquals(model.getEntities().get(3), actualEntity);
    }

    @Test
    public void parseServiceWithAssociationsSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("entity_with_associations.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getAssociations().size(), 6);

        HDBXSODataAssociation actualAssociation = new HDBXSODataAssociation();
        actualAssociation.setName("complex_atob");
        actualAssociation.setWithReferentialConstraint(false);
        HDBXSODataBinding principal = new HDBXSODataBinding();
        principal.setEntitySetName("complex_a");
        principal.setBindingRole(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.PRINCIPAL).setKeys(Collections.singletonList("ID")));
        principal.setMultiplicityType(HDBXSODataMultiplicityType.MANY);
        actualAssociation.setPrincipal(principal);
        HDBXSODataBinding dependent = new HDBXSODataBinding();
        dependent.setEntitySetName("complex_b");
        dependent.setBindingRole(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.DEPENDENT).setKeys(Collections.singletonList("ID_A")));
        dependent.setMultiplicityType(HDBXSODataMultiplicityType.MANY);
        actualAssociation.setDependent(dependent);
        HDBXSODataAssociationTable table = new HDBXSODataAssociationTable();
        table.setRepositoryObject("xsodata.test.tables::complex_assoc_atob");
        table.setPrincipal(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.PRINCIPAL).setKeys(Collections.singletonList("ID_A")));
        table.setDependent(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.DEPENDENT).setKeys(Collections.singletonList("ID_B")));
        actualAssociation.setAssociationTable(table);
        assertEquals(model.getAssociations().get(0), actualAssociation);


        HDBXSODataModification updateModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.UPDATE);
        updateModification.setSpecification(new HDBXSODataModificationSpec().setEvents(Collections.singletonList(new HDBXSODataEvent(HDBXSODataEventType.BEFORE, "sample.odata::updateMethod"))));
        HDBXSODataModification deleteModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.DELETE);
        deleteModification.setSpecification(new HDBXSODataModificationSpec().setEvents(Collections.singletonList(new HDBXSODataEvent(HDBXSODataEventType.BEFORE, "sample.odata::deleteMethod"))));
        HDBXSODataModification createModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.CREATE);
        createModification.setSpecification(new HDBXSODataModificationSpec().setEvents(Collections.singletonList(new HDBXSODataEvent(HDBXSODataEventType.BEFORE, "sample.odata::createMethod"))));
        table.setModifications(Arrays.asList(deleteModification, createModification, updateModification));
        actualAssociation.setAssociationTable(table);
        assertEquals(model.getAssociations().get(1), actualAssociation);

        actualAssociation = new HDBXSODataAssociation();
        actualAssociation.setName("complex_b_to_c");
        actualAssociation.setWithReferentialConstraint(false);
        principal = new HDBXSODataBinding();
        principal.setEntitySetName("complex_b");
        principal.setBindingRole(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.PRINCIPAL).setKeys(Collections.singletonList("ID")));
        principal.setMultiplicityType(HDBXSODataMultiplicityType.ONE);
        actualAssociation.setPrincipal(principal);
        dependent = new HDBXSODataBinding();
        dependent.setEntitySetName("complex_c");
        dependent.setBindingRole(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.DEPENDENT).setKeys(Collections.singletonList("ID_A")));
        dependent.setMultiplicityType(HDBXSODataMultiplicityType.MANY);
        actualAssociation.setDependent(dependent);
        updateModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.UPDATE).setSpecification(new HDBXSODataModificationSpec().setModificationAction("sap.test:oDataExtendedRules.xsjslib::associationToConditions"));
        table.setModifications(Arrays.asList(deleteModification, createModification, updateModification));
        actualAssociation.setStorage(new HDBXSODataStorage().setStorageType(HDBXSODataStorageType.STORAGE_ON_DEPENDENT).setModifications(Collections.singletonList(updateModification)));
        assertEquals(model.getAssociations().get(2), actualAssociation);

        actualAssociation = new HDBXSODataAssociation();
        actualAssociation.setName("complex_b_to_c");
        actualAssociation.setWithReferentialConstraint(false);
        principal = new HDBXSODataBinding();
        principal.setEntitySetName("complex_b");
        principal.setBindingRole(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.PRINCIPAL).setKeys(Collections.singletonList("ID")));
        principal.setMultiplicityType(HDBXSODataMultiplicityType.ZERO_TO_ONE);
        actualAssociation.setPrincipal(principal);
        dependent = new HDBXSODataBinding();
        dependent.setEntitySetName("complex_c");
        dependent.setBindingRole(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.DEPENDENT).setKeys(Collections.singletonList("ID_B")));
        dependent.setMultiplicityType(HDBXSODataMultiplicityType.ONE);
        actualAssociation.setDependent(dependent);
        actualAssociation.setStorage(new HDBXSODataStorage().setStorageType(HDBXSODataStorageType.STORAGE_ON_PRINCIPAL));
        assertEquals(model.getAssociations().get(3), actualAssociation);

        actualAssociation = new HDBXSODataAssociation();
        actualAssociation.setName("complex_b_to_c");
        actualAssociation.setWithReferentialConstraint(false);
        principal = new HDBXSODataBinding();
        principal.setEntitySetName("complex_b");
        principal.setBindingRole(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.PRINCIPAL).setKeys(Collections.singletonList("ID")));
        principal.setMultiplicityType(HDBXSODataMultiplicityType.ONE_TO_MANY);
        actualAssociation.setPrincipal(principal);
        dependent = new HDBXSODataBinding();
        dependent.setEntitySetName("complex_c");
        dependent.setBindingRole(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.DEPENDENT).setKeys(Collections.singletonList("ID_B")));
        dependent.setMultiplicityType(HDBXSODataMultiplicityType.MANY);
        actualAssociation.setDependent(dependent);
        actualAssociation.setStorage(new HDBXSODataStorage().setStorageType(HDBXSODataStorageType.NO_STORAGE));
        assertEquals(model.getAssociations().get(4), actualAssociation);

        actualAssociation = new HDBXSODataAssociation();
        actualAssociation.setName("complex_b_to_c");
        actualAssociation.setWithReferentialConstraint(true);
        principal = new HDBXSODataBinding();
        principal.setEntitySetName("complex_b");
        principal.setBindingRole(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.PRINCIPAL).setKeys(Collections.singletonList("ID")));
        principal.setMultiplicityType(HDBXSODataMultiplicityType.ONE);
        actualAssociation.setPrincipal(principal);
        dependent = new HDBXSODataBinding();
        dependent.setEntitySetName("complex_c");
        dependent.setBindingRole(new HDBXSODataBindingRole().setBindingType(HDBXSODataBindingType.DEPENDENT).setKeys(Collections.singletonList("ID_B")));
        dependent.setMultiplicityType(HDBXSODataMultiplicityType.MANY);
        actualAssociation.setDependent(dependent);
        updateModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.UPDATE);
        updateModification.setSpecification(new HDBXSODataModificationSpec().setEvents(Collections.singletonList(new HDBXSODataEvent(HDBXSODataEventType.BEFORE, "sample.odata::updateMethod"))));
        deleteModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.DELETE);
        deleteModification.setSpecification(new HDBXSODataModificationSpec().setEvents(Collections.singletonList(new HDBXSODataEvent(HDBXSODataEventType.BEFORE, "sample.odata::deleteMethod"))));
        createModification = new HDBXSODataModification().setMethod(HDBXSODataHandlerMethod.CREATE);
        createModification.setSpecification(new HDBXSODataModificationSpec().setEvents(Collections.singletonList(new HDBXSODataEvent(HDBXSODataEventType.BEFORE, "sample.odata::createMethod"))));
        actualAssociation.setModifications(Arrays.asList(deleteModification, createModification, updateModification));
        assertEquals(model.getAssociations().get(5), actualAssociation);
    }

    @Test
    public void parseServiceWithETagsSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("empty_service_with_etag.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities().size(), 4);

        HDBXSODataEntity actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("xsodata.test.tables::all_types"));
        actualEntity.setAlias("all_types_etag");
        actualEntity.setConcurrencyToken(true);
        assertEquals(model.getEntities().get(0), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("xsodata.test.tables::BusinessPartner.BusinessPartner"));
        actualEntity.setAlias("BusinessPartner");
        actualEntity.setConcurrencyToken(true);
        actualEntity.setWithoutPropertyProjections(Collections.singletonList("isContactPerson"));
        HDBXSODataNavigation nav1 = new HDBXSODataNavigation().setAssociation("BusinessPartner_To_N_BPRole").setAliasNavigation("Roles");
        actualEntity.setNavigates(Collections.singletonList(nav1));
        assertEquals(model.getEntities().get(1), actualEntity);

        actualEntity = new HDBXSODataEntity();
        actualEntity.setRepositoryObject(new HDBXSODataRepositoryObject().setCatalogObjectName("sap.test.odata.db.views::Etag"));
        actualEntity.setAlias("EtagAll");
        actualEntity.setConcurrencyToken(true);
        actualEntity.setKeyList(Arrays.asList("KEY_00", "KEY_01"));
        assertEquals(model.getEntities().get(2), actualEntity);

        actualEntity.setETags(Arrays.asList("NVARCHAR_01", "INTEGER_02"));
        assertEquals(model.getEntities().get(3), actualEntity);
    }

    @Test
    public void parseServiceSuccessfully() throws Exception {
        HdbxsodataParser parser = parseSampleFile("entity_with_no_associations.xsodata");

        HDBXSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(parser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities().size(), 20);
    }


    private HdbxsodataParser parseSampleFile(String sampleFileName) throws Exception {
        String sample =
                org.apache.commons.io.IOUtils.toString(
                		HDBXSODataCoreListenerTest.class.getResourceAsStream(sampleFileName),
                        StandardCharsets.UTF_8);

        ANTLRInputStream inputStream = new ANTLRInputStream(sample);
        HdbxsodataLexer lexer = new HdbxsodataLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        HdbxsodataParser parser = new HdbxsodataParser(tokenStream);
        parser.setBuildParseTree(true);
        ParseTree parseTree = parser.xsodataDefinition();

        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        parseTreeWalker.walk(listener, parseTree);

        return parser;
    }

}