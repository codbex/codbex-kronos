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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for JoinOperator.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="JoinOperator"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="Equal"/&gt;
 *     &lt;enumeration value="NotEqual"/&gt;
 *     &lt;enumeration value="Greater"/&gt;
 *     &lt;enumeration value="GreaterOrEqual"/&gt;
 *     &lt;enumeration value="Less"/&gt;
 *     &lt;enumeration value="LessOrEqual"/&gt;
 *     &lt;enumeration value="Complex"/&gt;
 *     &lt;enumeration value="Unknown"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "JoinOperator")
@XmlEnum
public enum JoinOperator {

    /** The equal. */
    @XmlEnumValue("Equal")
    EQUAL("Equal"),

    /** The not equal. */
    @XmlEnumValue("NotEqual")
    NOT_EQUAL("NotEqual"),

    /** The greater. */
    @XmlEnumValue("Greater")
    GREATER("Greater"),

    /** The greater or equal. */
    @XmlEnumValue("GreaterOrEqual")
    GREATER_OR_EQUAL("GreaterOrEqual"),

    /** The less. */
    @XmlEnumValue("Less")
    LESS("Less"),

    /** The less or equal. */
    @XmlEnumValue("LessOrEqual")
    LESS_OR_EQUAL("LessOrEqual"),

    /** The complex. */
    @XmlEnumValue("Complex")
    COMPLEX("Complex"),

    /** The unknown. */
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new join operator.
     *
     * @param v the v
     */
    JoinOperator(String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the join operator
     */
    public static JoinOperator fromValue(String v) {
        for (JoinOperator c : JoinOperator.values()) {
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
