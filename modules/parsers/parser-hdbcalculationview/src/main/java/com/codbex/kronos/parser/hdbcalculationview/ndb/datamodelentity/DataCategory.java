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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for DataCategory.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="DataCategory"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="DEFAULT"/&gt;
 *     &lt;enumeration value="DIMENSION"/&gt;
 *     &lt;enumeration value="CUBE"/&gt;
 *     &lt;enumeration value="FACT"/&gt;
 *     &lt;enumeration value="TEXT"/&gt;
 *     &lt;enumeration value="HIERARCHY"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "DataCategory")
@XmlEnum
public enum DataCategory {

    /** The default. */
    DEFAULT,

    /** The dimension. */
    DIMENSION,

    /** The cube. */
    CUBE,

    /** The fact. */
    FACT,

    /** The text. */
    TEXT,

    /** The hierarchy. */
    HIERARCHY;

    /**
     * From value.
     *
     * @param v the v
     * @return the data category
     */
    public static DataCategory fromValue(String v) {
        return valueOf(v);
    }

    /**
     * Value.
     *
     * @return the string
     */
    public String value() {
        return name();
    }

}
