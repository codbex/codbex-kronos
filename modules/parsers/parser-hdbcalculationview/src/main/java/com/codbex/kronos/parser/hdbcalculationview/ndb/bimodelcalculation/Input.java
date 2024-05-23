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

package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcalculation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.EmptyUnionBehavior;

/**
 * Java class for Input complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Input"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mapping" type="{http://www.sap.com/ndb/BiModelCalculation.ecore}AbstractAttributeMapping" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="node" use="required" type="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}BIName" /&gt;
 *       &lt;attribute name="mapAll" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="emptyUnionBehavior" type="{http://www.sap.com/ndb/BaseModelBase.ecore}EmptyUnionBehavior" /&gt;
 *       &lt;attribute name="outputName" type="{http://www.sap.com/ndb/BaseModelBase.ecore}DbName" /&gt;
 *       &lt;attribute name="tableParameterName" type="{http://www.sap.com/ndb/BaseModelBase.ecore}DbName" /&gt;
 *       &lt;attribute name="type" type="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}BIName" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Input", propOrder = {"mapping"})
public class Input {

    /** The mapping. */
    protected List<AbstractAttributeMapping> mapping;

    /** The node. */
    @XmlAttribute(name = "node", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String node;

    /** The map all. */
    @XmlAttribute(name = "mapAll")
    protected Boolean mapAll;

    /** The empty union behavior. */
    @XmlAttribute(name = "emptyUnionBehavior")
    protected EmptyUnionBehavior emptyUnionBehavior;

    /** The output name. */
    @XmlAttribute(name = "outputName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String outputName;

    /** The table parameter name. */
    @XmlAttribute(name = "tableParameterName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String tableParameterName;

    /** The type. */
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;

    /**
     * Gets the value of the mapping property.
     *
     *
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is why
     * there is not a <CODE>set</CODE> method for the mapping property.
     *
     *
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getMapping().add(newItem);
     * </pre>
     *
     *
     *
     * Objects of the following type(s) are allowed in the list {@link AbstractAttributeMapping }
     *
     * @return the mapping
     */
    public List<AbstractAttributeMapping> getMapping() {
        if (mapping == null) {
            mapping = new ArrayList<AbstractAttributeMapping>();
        }
        return this.mapping;
    }

    /**
     * Gets the value of the node property.
     *
     * @return possible object is {@link String }
     */
    public String getNode() {
        return node;
    }

    /**
     * Sets the value of the node property.
     *
     * @param value allowed object is {@link String }
     */
    public void setNode(String value) {
        this.node = value;
    }

    /**
     * Gets the value of the mapAll property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isMapAll() {
        return mapAll;
    }

    /**
     * Sets the value of the mapAll property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setMapAll(Boolean value) {
        this.mapAll = value;
    }

    /**
     * Gets the value of the emptyUnionBehavior property.
     *
     * @return possible object is {@link EmptyUnionBehavior }
     */
    public EmptyUnionBehavior getEmptyUnionBehavior() {
        return emptyUnionBehavior;
    }

    /**
     * Sets the value of the emptyUnionBehavior property.
     *
     * @param value allowed object is {@link EmptyUnionBehavior }
     */
    public void setEmptyUnionBehavior(EmptyUnionBehavior value) {
        this.emptyUnionBehavior = value;
    }

    /**
     * Gets the value of the outputName property.
     *
     * @return possible object is {@link String }
     */
    public String getOutputName() {
        return outputName;
    }

    /**
     * Sets the value of the outputName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setOutputName(String value) {
        this.outputName = value;
    }

    /**
     * Gets the value of the tableParameterName property.
     *
     * @return possible object is {@link String }
     */
    public String getTableParameterName() {
        return tableParameterName;
    }

    /**
     * Sets the value of the tableParameterName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setTableParameterName(String value) {
        this.tableParameterName = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link String }
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is {@link String }
     */
    public void setType(String value) {
        this.type = value;
    }

}
