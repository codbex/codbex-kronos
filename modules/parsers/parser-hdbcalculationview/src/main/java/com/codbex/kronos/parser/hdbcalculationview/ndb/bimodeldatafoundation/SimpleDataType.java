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

package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldatafoundation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.codbex.kronos.parser.hdbcalculationview.ndb.sqlcoremodeldatatypes.PrimitiveTypeSQL;

/**
 * This class just comprises the attributes used for simple data type
 *
 *
 * Java class for SimpleDataType complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SimpleDataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="datatype" type="{http://www.sap.com/ndb/SQLCoreModelDataTypes.ecore}PrimitiveTypeSQL" /&gt;
 *       &lt;attribute name="length" type="{http://www.w3.org/2001/XMLSchema}short" /&gt;
 *       &lt;attribute name="scale" type="{http://www.w3.org/2001/XMLSchema}short" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SimpleDataType")
public class SimpleDataType {

    /** The datatype. */
    @XmlAttribute(name = "datatype")
    protected PrimitiveTypeSQL datatype;

    /** The length. */
    @XmlAttribute(name = "length")
    protected Short length;

    /** The scale. */
    @XmlAttribute(name = "scale")
    protected Short scale;

    /**
     * Gets the value of the datatype property.
     *
     * @return possible object is {@link PrimitiveTypeSQL }
     */
    public PrimitiveTypeSQL getDatatype() {
        return datatype;
    }

    /**
     * Sets the value of the datatype property.
     *
     * @param value allowed object is {@link PrimitiveTypeSQL }
     */
    public void setDatatype(PrimitiveTypeSQL value) {
        this.datatype = value;
    }

    /**
     * Gets the value of the length property.
     *
     * @return possible object is {@link Short }
     */
    public Short getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     *
     * @param value allowed object is {@link Short }
     */
    public void setLength(Short value) {
        this.length = value;
    }

    /**
     * Gets the value of the scale property.
     *
     * @return possible object is {@link Short }
     */
    public Short getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     *
     * @param value allowed object is {@link Short }
     */
    public void setScale(Short value) {
        this.scale = value;
    }

}
