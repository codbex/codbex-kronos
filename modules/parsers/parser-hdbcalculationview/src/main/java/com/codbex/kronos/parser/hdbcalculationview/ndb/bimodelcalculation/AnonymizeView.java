/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.26 at 10:54:28 AM EET 
//


package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcalculation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A View node for Anonymization
 *
 *
 * Java class for AnonymizeView complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AnonymizeView"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/BiModelCalculation.ecore}CalculationView"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="anonymizeParameterization" type="{http://www.sap.com/ndb/BiModelCalculation.ecore}AnonymizeParameterization"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="method" type="{http://www.sap.com/ndb/BiModelCalculation.ecore}AnonymizeMethod" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnonymizeView", propOrder = {
    "anonymizeParameterization"
})
public class AnonymizeView
    extends CalculationView {

  /** The anonymize parameterization. */
  @XmlElement(required = true)
  protected AnonymizeParameterization anonymizeParameterization;
  
  /** The method. */
  @XmlAttribute(name = "method")
  protected AnonymizeMethod method;

  /**
   * Gets the value of the anonymizeParameterization property.
   *
   * @return possible object is
   * {@link AnonymizeParameterization }
   */
  public AnonymizeParameterization getAnonymizeParameterization() {
    return anonymizeParameterization;
  }

  /**
   * Sets the value of the anonymizeParameterization property.
   *
   * @param value allowed object is
   *              {@link AnonymizeParameterization }
   */
  public void setAnonymizeParameterization(AnonymizeParameterization value) {
    this.anonymizeParameterization = value;
  }

  /**
   * Gets the value of the method property.
   *
   * @return possible object is
   * {@link AnonymizeMethod }
   */
  public AnonymizeMethod getMethod() {
    return method;
  }

  /**
   * Sets the value of the method property.
   *
   * @param value allowed object is
   *              {@link AnonymizeMethod }
   */
  public void setMethod(AnonymizeMethod value) {
    this.method = value;
  }

}