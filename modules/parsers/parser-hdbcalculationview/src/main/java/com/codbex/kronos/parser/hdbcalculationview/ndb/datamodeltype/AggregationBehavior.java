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

package com.codbex.kronos.parser.hdbcalculationview.ndb.datamodeltype;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for AggregationBehavior.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="AggregationBehavior"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="NONE"/&gt;
 *     &lt;enumeration value="SUM"/&gt;
 *     &lt;enumeration value="MIN"/&gt;
 *     &lt;enumeration value="MAX"/&gt;
 *     &lt;enumeration value="COUNT"/&gt;
 *     &lt;enumeration value="AVG"/&gt;
 *     &lt;enumeration value="VAR"/&gt;
 *     &lt;enumeration value="STDDEV"/&gt;
 *     &lt;enumeration value="FORMULA"/&gt;
 *     &lt;enumeration value="NOP"/&gt;
 *     &lt;enumeration value="NO1"/&gt;
 *     &lt;enumeration value="NO2"/&gt;
 *     &lt;enumeration value="MOV"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "AggregationBehavior")
@XmlEnum
public enum AggregationBehavior {

    /** The none. */
    NONE("NONE"),

    /** The sum. */
    SUM("SUM"),

    /** The min. */
    MIN("MIN"),

    /** The max. */
    MAX("MAX"),

    /** The count. */
    COUNT("COUNT"),

    /** Average defined as SUM( column ) / COUNT( column ). */
    AVG("AVG"),

    /**
     * Standard deviation: Sum over the squares of the differences to the average divided by (COUNT-1).
     */
    VAR("VAR"),

    /** Standard deviation: Square root of the variance. */
    STDDEV("STDDEV"),

    /** The formula. */
    FORMULA("FORMULA"),

    /**
     * No Aggregation (If More Than One Value unequal to 0 Occurs). Only needed by BW
     */
    NOP("NOP"),

    /**
     * No Aggregation (If More Than One Record Occurs). Only needed by BW
     */
    @XmlEnumValue("NO1")
    NO_1("NO1"),

    /**
     * No Aggregation (If More Than One Value Occurs). Only needed by BW
     */
    @XmlEnumValue("NO2")
    NO_2("NO2"),

    /** Move - Only needed by BW transformations. */
    MOV("MOV");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new aggregation behavior.
     *
     * @param v the v
     */
    AggregationBehavior(String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the aggregation behavior
     */
    public static AggregationBehavior fromValue(String v) {
        for (AggregationBehavior c : AggregationBehavior.values()) {
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
