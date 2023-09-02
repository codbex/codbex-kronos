/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
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


package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldatafoundation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for CalculatedAttributes complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CalculatedAttributes"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="calculatedAttribute" type="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}CalculatedAttribute" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CalculatedAttributes", propOrder = {
    "calculatedAttribute"
})
public class CalculatedAttributes {

  /** The calculated attribute. */
  protected List<CalculatedAttribute> calculatedAttribute;

  /**
   * Gets the value of the calculatedAttribute property.
   * 
   * 
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the calculatedAttribute property.
   * 
   * 
   * For example, to add a new item, do as follows:
   * <pre>
   *    getCalculatedAttribute().add(newItem);
   * </pre>
   * 
   * 
   * 
   * Objects of the following type(s) are allowed in the list
   * {@link CalculatedAttribute }
   *
   * @return the calculated attribute
   */
  public List<CalculatedAttribute> getCalculatedAttribute() {
    if (calculatedAttribute == null) {
      calculatedAttribute = new ArrayList<CalculatedAttribute>();
    }
    return this.calculatedAttribute;
  }

}
