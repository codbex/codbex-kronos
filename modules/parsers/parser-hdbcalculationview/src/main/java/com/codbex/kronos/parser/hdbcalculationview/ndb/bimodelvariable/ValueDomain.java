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

package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelvariable;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.codbex.kronos.parser.hdbcalculationview.ndb.repositorymodelresource.QualifiedNameReference;

/**
 * Describes how the allowed values for the variable can be retrieved
 *
 *
 * Java class for ValueDomain complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ValueDomain"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice minOccurs="0"&gt;
 *           &lt;element name="attribute" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}QualifiedNameReference"/&gt;
 *           &lt;element name="listEntry" type="{http://www.sap.com/ndb/BiModelVariable.ecore}ValueListEntry" maxOccurs="unbounded"/&gt;
 *           &lt;sequence&gt;
 *             &lt;element name="externalLikeStructureName" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}FQName"/&gt;
 *             &lt;element name="externalLikeElementName" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}AlphanumericName"/&gt;
 *             &lt;element name="variableMapping" type="{http://www.sap.com/ndb/BiModelVariable.ecore}AbstractVariableMapping" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;/sequence&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="hierarchyName" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}AlphanumericName" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="type" use="required" type="{http://www.sap.com/ndb/BiModelVariable.ecore}ValueType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueDomain",
        propOrder = {"attribute", "listEntry", "externalLikeStructureName", "externalLikeElementName", "variableMapping", "hierarchyName"})
public class ValueDomain {

    /** The attribute. */
    protected QualifiedNameReference attribute;

    /** The list entry. */
    protected List<ValueListEntry> listEntry;

    /** The external like structure name. */
    protected String externalLikeStructureName;

    /** The external like element name. */
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String externalLikeElementName;

    /** The variable mapping. */
    protected List<AbstractVariableMapping> variableMapping;

    /** The hierarchy name. */
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String hierarchyName;

    /** The type. */
    @XmlAttribute(name = "type", required = true)
    protected ValueType type;

    /**
     * Gets the value of the attribute property.
     *
     * @return possible object is {@link QualifiedNameReference }
     */
    public QualifiedNameReference getAttribute() {
        return attribute;
    }

    /**
     * Sets the value of the attribute property.
     *
     * @param value allowed object is {@link QualifiedNameReference }
     */
    public void setAttribute(QualifiedNameReference value) {
        this.attribute = value;
    }

    /**
     * Gets the value of the listEntry property.
     *
     *
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is why
     * there is not a <CODE>set</CODE> method for the listEntry property.
     *
     *
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getListEntry().add(newItem);
     * </pre>
     *
     *
     *
     * Objects of the following type(s) are allowed in the list {@link ValueListEntry }
     *
     * @return the list entry
     */
    public List<ValueListEntry> getListEntry() {
        if (listEntry == null) {
            listEntry = new ArrayList<ValueListEntry>();
        }
        return this.listEntry;
    }

    /**
     * Gets the value of the externalLikeStructureName property.
     *
     * @return possible object is {@link String }
     */
    public String getExternalLikeStructureName() {
        return externalLikeStructureName;
    }

    /**
     * Sets the value of the externalLikeStructureName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setExternalLikeStructureName(String value) {
        this.externalLikeStructureName = value;
    }

    /**
     * Gets the value of the externalLikeElementName property.
     *
     * @return possible object is {@link String }
     */
    public String getExternalLikeElementName() {
        return externalLikeElementName;
    }

    /**
     * Sets the value of the externalLikeElementName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setExternalLikeElementName(String value) {
        this.externalLikeElementName = value;
    }

    /**
     * Gets the value of the variableMapping property.
     *
     *
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is why
     * there is not a <CODE>set</CODE> method for the variableMapping property.
     *
     *
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getVariableMapping().add(newItem);
     * </pre>
     *
     *
     *
     * Objects of the following type(s) are allowed in the list {@link AbstractVariableMapping }
     *
     * @return the variable mapping
     */
    public List<AbstractVariableMapping> getVariableMapping() {
        if (variableMapping == null) {
            variableMapping = new ArrayList<AbstractVariableMapping>();
        }
        return this.variableMapping;
    }

    /**
     * Gets the value of the hierarchyName property.
     *
     * @return possible object is {@link String }
     */
    public String getHierarchyName() {
        return hierarchyName;
    }

    /**
     * Sets the value of the hierarchyName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setHierarchyName(String value) {
        this.hierarchyName = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link ValueType }
     */
    public ValueType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is {@link ValueType }
     */
    public void setType(ValueType value) {
        this.type = value;
    }

}
