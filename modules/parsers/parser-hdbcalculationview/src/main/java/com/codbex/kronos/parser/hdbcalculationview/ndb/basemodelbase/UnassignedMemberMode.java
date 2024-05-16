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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for UnassignedMemberMode.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="UnassignedMemberMode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="FALSE"/&gt;
 *     &lt;enumeration value="TRUE"/&gt;
 *     &lt;enumeration value="AUTO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "UnassignedMemberMode")
@XmlEnum
public enum UnassignedMemberMode {

    /** No NotAssigned member (the default). */
    FALSE,

    /** Add a visible NotAssigned member. */
    TRUE,

    /**
     * Automatically add a visible NotAssigned member only if necessary. NB use carefully. Clearly, any
     * access to such a hierarchy (even for example in a request to BIMC_LEVELS) must instantiate the
     * hierarchy to determine if the NotAssigned member needs adding to the hierarchy so will increase
     * processing and cache usage. Normally for BIMC_LEVELS etc queries, if the results can be obtained
     * from deploy info alone, we do not need to instantiate the hierarchy
     */
    AUTO;

    /**
     * From value.
     *
     * @param v the v
     * @return the unassigned member mode
     */
    public static UnassignedMemberMode fromValue(String v) {
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
