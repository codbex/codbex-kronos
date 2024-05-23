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

package com.codbex.kronos.parser.hdbcalculationview.ndb.datamodelentity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.SortDirection;

/**
 * Data base index for entities
 *
 *
 * Java class for Index complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Index"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="element" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}Identifier" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.sap.com/ndb/BaseModelBase.ecore}DbName" /&gt;
 *       &lt;attribute name="type" type="{http://www.sap.com/ndb/DataModelEntity.ecore}IndexType" /&gt;
 *       &lt;attribute name="unique" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="order" type="{http://www.sap.com/ndb/BaseModelBase.ecore}SortDirection" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Index", propOrder = {"element"})
public class Index {

    /** The element. */
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected List<String> element;

    /** The name. */
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String name;

    /** The type. */
    @XmlAttribute(name = "type")
    protected IndexType type;

    /** The unique. */
    @XmlAttribute(name = "unique")
    protected Boolean unique;

    /** The order. */
    @XmlAttribute(name = "order")
    protected SortDirection order;

    /**
     * Gets the value of the element property.
     *
     *
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is why
     * there is not a <CODE>set</CODE> method for the element property.
     *
     *
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getElement().add(newItem);
     * </pre>
     *
     *
     *
     * Objects of the following type(s) are allowed in the list {@link String }
     *
     * @return the element
     */
    public List<String> getElement() {
        if (element == null) {
            element = new ArrayList<String>();
        }
        return this.element;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link IndexType }
     */
    public IndexType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is {@link IndexType }
     */
    public void setType(IndexType value) {
        this.type = value;
    }

    /**
     * Gets the value of the unique property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isUnique() {
        return unique;
    }

    /**
     * Sets the value of the unique property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setUnique(Boolean value) {
        this.unique = value;
    }

    /**
     * Gets the value of the order property.
     *
     * @return possible object is {@link SortDirection }
     */
    public SortDirection getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     *
     * @param value allowed object is {@link SortDirection }
     */
    public void setOrder(SortDirection value) {
        this.order = value;
    }

}
