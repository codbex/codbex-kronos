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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldatafoundation.ColumnMapping;

/**
 * Type for referencing a column based SQL object. This type is really meant for column based types,
 * whereas Qualified SQL object name might also be used for non column based objects like functions
 * or procedures. In addition this type contains the alias in case the same table is used several
 * times in the same data foundation.
 *
 *
 * Java class for QualifiedColumnObjectName complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="QualifiedColumnObjectName"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/BaseModelBase.ecore}QualifiedSQLObjectName"&gt;
 *       &lt;attribute name="alias" type="{http://www.sap.com/ndb/BaseModelBase.ecore}DbName" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualifiedColumnObjectName")
@XmlSeeAlso({ColumnMapping.class})
public class QualifiedColumnObjectName extends QualifiedSQLObjectName {

    /** The alias. */
    @XmlAttribute(name = "alias")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String alias;

    /**
     * Gets the value of the alias property.
     *
     * @return possible object is {@link String }
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     *
     * @param value allowed object is {@link String }
     */
    public void setAlias(String value) {
        this.alias = value;
    }

}
