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

package com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Specification of unassigned member properties in hierarchies
 *
 *
 * Java class for UnassignedMemberProperties complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UnassignedMemberProperties"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="caption" type="{http://www.sap.com/ndb/BaseModelBase.ecore}EndUserTexts" minOccurs="0"/&gt;
 *         &lt;element name="nullCaption" type="{http://www.sap.com/ndb/BaseModelBase.ecore}EndUserTexts" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="mode" type="{http://www.sap.com/ndb/BaseModelBase.ecore}UnassignedMemberMode" /&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="drillable" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="includeNulls" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="nullConvertValue" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnassignedMemberProperties", propOrder = {"caption", "nullCaption"})
public class UnassignedMemberProperties {

    /** The caption. */
    protected EndUserTexts caption;

    /** The null caption. */
    protected EndUserTexts nullCaption;

    /** The mode. */
    @XmlAttribute(name = "mode")
    protected UnassignedMemberMode mode;

    /** The name. */
    @XmlAttribute(name = "name")
    protected String name;

    /** The drillable. */
    @XmlAttribute(name = "drillable")
    protected Boolean drillable;

    /** The include nulls. */
    @XmlAttribute(name = "includeNulls")
    protected Boolean includeNulls;

    /** The null convert value. */
    @XmlAttribute(name = "nullConvertValue")
    protected String nullConvertValue;

    /**
     * Gets the value of the caption property.
     *
     * @return possible object is {@link EndUserTexts }
     */
    public EndUserTexts getCaption() {
        return caption;
    }

    /**
     * Sets the value of the caption property.
     *
     * @param value allowed object is {@link EndUserTexts }
     */
    public void setCaption(EndUserTexts value) {
        this.caption = value;
    }

    /**
     * Gets the value of the nullCaption property.
     *
     * @return possible object is {@link EndUserTexts }
     */
    public EndUserTexts getNullCaption() {
        return nullCaption;
    }

    /**
     * Sets the value of the nullCaption property.
     *
     * @param value allowed object is {@link EndUserTexts }
     */
    public void setNullCaption(EndUserTexts value) {
        this.nullCaption = value;
    }

    /**
     * Gets the value of the mode property.
     *
     * @return possible object is {@link UnassignedMemberMode }
     */
    public UnassignedMemberMode getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     *
     * @param value allowed object is {@link UnassignedMemberMode }
     */
    public void setMode(UnassignedMemberMode value) {
        this.mode = value;
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
     * Gets the value of the drillable property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isDrillable() {
        return drillable;
    }

    /**
     * Sets the value of the drillable property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setDrillable(Boolean value) {
        this.drillable = value;
    }

    /**
     * Gets the value of the includeNulls property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isIncludeNulls() {
        return includeNulls;
    }

    /**
     * Sets the value of the includeNulls property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setIncludeNulls(Boolean value) {
        this.includeNulls = value;
    }

    /**
     * Gets the value of the nullConvertValue property.
     *
     * @return possible object is {@link String }
     */
    public String getNullConvertValue() {
        return nullConvertValue;
    }

    /**
     * Sets the value of the nullConvertValue property.
     *
     * @param value allowed object is {@link String }
     */
    public void setNullConvertValue(String value) {
        this.nullConvertValue = value;
    }

}
