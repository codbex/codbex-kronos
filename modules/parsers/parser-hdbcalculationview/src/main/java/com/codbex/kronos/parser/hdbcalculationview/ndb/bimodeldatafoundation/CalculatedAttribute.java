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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Attribute defined via a formula in the expression language
 *
 *
 * Java class for CalculatedAttribute complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CalculatedAttribute"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}AbstractAttribute"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="keyCalculation" type="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}Calculation"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CalculatedAttribute", propOrder = {"keyCalculation"})
public class CalculatedAttribute extends AbstractAttribute {

    /** The key calculation. */
    @XmlElement(required = true)
    protected Calculation keyCalculation;

    /**
     * Gets the value of the keyCalculation property.
     *
     * @return possible object is {@link Calculation }
     */
    public Calculation getKeyCalculation() {
        return keyCalculation;
    }

    /**
     * Sets the value of the keyCalculation property.
     *
     * @param value allowed object is {@link Calculation }
     */
    public void setKeyCalculation(Calculation value) {
        this.keyCalculation = value;
    }

}
