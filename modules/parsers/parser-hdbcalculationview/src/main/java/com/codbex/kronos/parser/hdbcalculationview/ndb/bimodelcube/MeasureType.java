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

package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcube;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for MeasureType.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="MeasureType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="amount"/&gt;
 *     &lt;enumeration value="quantity"/&gt;
 *     &lt;enumeration value="simple"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "MeasureType")
@XmlEnum
public enum MeasureType {

    /** The amount. */
    @XmlEnumValue("amount")
    AMOUNT("amount"),

    /** The quantity. */
    @XmlEnumValue("quantity")
    QUANTITY("quantity"),

    /** The simple. */
    @XmlEnumValue("simple")
    SIMPLE("simple");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new measure type.
     *
     * @param v the v
     */
    MeasureType(String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the measure type
     */
    public static MeasureType fromValue(String v) {
        for (MeasureType c : MeasureType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    /**
     * Value.
     *
     * @return the string
     */
    public String value() {
        return value;
    }

}
