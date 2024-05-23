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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.3.0
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2020.11.26 at 10:54:28 AM EET
//

package com.codbex.kronos.parser.hdbcalculationview.ndb.datamodelhierarchy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.CycleHandling;
import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.HierarchyJoinProperties;
import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.OrphanedNodesHandling;
import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.RootNodeVisibility;
import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.UnassignedMemberProperties;
import com.codbex.kronos.parser.hdbcalculationview.ndb.repositorymodelresource.NamedObjectWithEndUserTexts;

/**
 * A Hierarchy that is defined on top of a entity by simply specifying which columns make up the
 * level or parent child columns. The data provided by the view must fulfill some constraints to
 * make the hierarchy consistent. The following additional behavior applies: 1. Each hierarchy of a
 * view having the data category DIMENSION becomes a hierarchy of that dimension if the dimension is
 * used in a view of dataCatefory CUBE. 2. Each hierarchy of a view that is joined via a
 * dimensionJoin becomes a hierarchy of that dimension 3. Each hierarchy that is defined on a view
 * with dataCategory CUBE (aka local hierarchy) implicitly defines a dimension with just that
 * hierarchy. All columns that are used in the hierarchy definition (including level attributes
 * defined via element relationships) become attributes of that dimension. The concept of local
 * dimensions with several hierarchies is not supported.
 *
 *
 * Java class for InlineHierarchy complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="InlineHierarchy"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/RepositoryModelResource.ecore}NamedObjectWithEndUserTexts"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="joinProperties" type="{http://www.sap.com/ndb/BaseModelBase.ecore}HierarchyJoinProperties" minOccurs="0"/&gt;
 *         &lt;element name="unassignedMemberProperties" type="{http://www.sap.com/ndb/BaseModelBase.ecore}UnassignedMemberProperties" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="rootNodeVisibility" type="{http://www.sap.com/ndb/BaseModelBase.ecore}RootNodeVisibility" /&gt;
 *       &lt;attribute name="aggregateAllNodes" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="defaultMember" type="{http://www.sap.com/ndb/BaseModelBase.ecore}DefaultMember" /&gt;
 *       &lt;attribute name="multipleParents" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="orphanedNodesHandling" type="{http://www.sap.com/ndb/BaseModelBase.ecore}OrphanedNodesHandling" /&gt;
 *       &lt;attribute name="timeDependent" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="withHierarchyJoin" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="cycleHandling" type="{http://www.sap.com/ndb/BaseModelBase.ecore}CycleHandling" /&gt;
 *       &lt;attribute name="cacheEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InlineHierarchy", propOrder = {"joinProperties", "unassignedMemberProperties"})
@XmlSeeAlso({LeveledHierarchy.class, ParentChildHierarchy.class})
public abstract class InlineHierarchy extends NamedObjectWithEndUserTexts {

    /** The join properties. */
    protected HierarchyJoinProperties joinProperties;

    /** The unassigned member properties. */
    protected UnassignedMemberProperties unassignedMemberProperties;

    /** The root node visibility. */
    @XmlAttribute(name = "rootNodeVisibility")
    protected RootNodeVisibility rootNodeVisibility;

    /** The aggregate all nodes. */
    @XmlAttribute(name = "aggregateAllNodes")
    protected Boolean aggregateAllNodes;

    /** The default member. */
    @XmlAttribute(name = "defaultMember")
    protected String defaultMember;

    /** The multiple parents. */
    @XmlAttribute(name = "multipleParents")
    protected Boolean multipleParents;

    /** The orphaned nodes handling. */
    @XmlAttribute(name = "orphanedNodesHandling")
    protected OrphanedNodesHandling orphanedNodesHandling;

    /** The time dependent. */
    @XmlAttribute(name = "timeDependent")
    protected Boolean timeDependent;

    /** The with hierarchy join. */
    @XmlAttribute(name = "withHierarchyJoin")
    protected Boolean withHierarchyJoin;

    /** The cycle handling. */
    @XmlAttribute(name = "cycleHandling")
    protected CycleHandling cycleHandling;

    /** The cache enabled. */
    @XmlAttribute(name = "cacheEnabled")
    protected Boolean cacheEnabled;

    /**
     * Gets the value of the joinProperties property.
     *
     * @return possible object is {@link HierarchyJoinProperties }
     */
    public HierarchyJoinProperties getJoinProperties() {
        return joinProperties;
    }

    /**
     * Sets the value of the joinProperties property.
     *
     * @param value allowed object is {@link HierarchyJoinProperties }
     */
    public void setJoinProperties(HierarchyJoinProperties value) {
        this.joinProperties = value;
    }

    /**
     * Gets the value of the unassignedMemberProperties property.
     *
     * @return possible object is {@link UnassignedMemberProperties }
     */
    public UnassignedMemberProperties getUnassignedMemberProperties() {
        return unassignedMemberProperties;
    }

    /**
     * Sets the value of the unassignedMemberProperties property.
     *
     * @param value allowed object is {@link UnassignedMemberProperties }
     */
    public void setUnassignedMemberProperties(UnassignedMemberProperties value) {
        this.unassignedMemberProperties = value;
    }

    /**
     * Gets the value of the rootNodeVisibility property.
     *
     * @return possible object is {@link RootNodeVisibility }
     */
    public RootNodeVisibility getRootNodeVisibility() {
        return rootNodeVisibility;
    }

    /**
     * Sets the value of the rootNodeVisibility property.
     *
     * @param value allowed object is {@link RootNodeVisibility }
     */
    public void setRootNodeVisibility(RootNodeVisibility value) {
        this.rootNodeVisibility = value;
    }

    /**
     * Gets the value of the aggregateAllNodes property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isAggregateAllNodes() {
        return aggregateAllNodes;
    }

    /**
     * Sets the value of the aggregateAllNodes property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setAggregateAllNodes(Boolean value) {
        this.aggregateAllNodes = value;
    }

    /**
     * Gets the value of the defaultMember property.
     *
     * @return possible object is {@link String }
     */
    public String getDefaultMember() {
        return defaultMember;
    }

    /**
     * Sets the value of the defaultMember property.
     *
     * @param value allowed object is {@link String }
     */
    public void setDefaultMember(String value) {
        this.defaultMember = value;
    }

    /**
     * Gets the value of the multipleParents property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isMultipleParents() {
        return multipleParents;
    }

    /**
     * Sets the value of the multipleParents property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setMultipleParents(Boolean value) {
        this.multipleParents = value;
    }

    /**
     * Gets the value of the orphanedNodesHandling property.
     *
     * @return possible object is {@link OrphanedNodesHandling }
     */
    public OrphanedNodesHandling getOrphanedNodesHandling() {
        return orphanedNodesHandling;
    }

    /**
     * Sets the value of the orphanedNodesHandling property.
     *
     * @param value allowed object is {@link OrphanedNodesHandling }
     */
    public void setOrphanedNodesHandling(OrphanedNodesHandling value) {
        this.orphanedNodesHandling = value;
    }

    /**
     * Gets the value of the timeDependent property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isTimeDependent() {
        return timeDependent;
    }

    /**
     * Sets the value of the timeDependent property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setTimeDependent(Boolean value) {
        this.timeDependent = value;
    }

    /**
     * Gets the value of the withHierarchyJoin property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isWithHierarchyJoin() {
        return withHierarchyJoin;
    }

    /**
     * Sets the value of the withHierarchyJoin property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setWithHierarchyJoin(Boolean value) {
        this.withHierarchyJoin = value;
    }

    /**
     * Gets the value of the cycleHandling property.
     *
     * @return possible object is {@link CycleHandling }
     */
    public CycleHandling getCycleHandling() {
        return cycleHandling;
    }

    /**
     * Sets the value of the cycleHandling property.
     *
     * @param value allowed object is {@link CycleHandling }
     */
    public void setCycleHandling(CycleHandling value) {
        this.cycleHandling = value;
    }

    /**
     * Gets the value of the cacheEnabled property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isCacheEnabled() {
        return cacheEnabled;
    }

    /**
     * Sets the value of the cacheEnabled property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setCacheEnabled(Boolean value) {
        this.cacheEnabled = value;
    }

}
