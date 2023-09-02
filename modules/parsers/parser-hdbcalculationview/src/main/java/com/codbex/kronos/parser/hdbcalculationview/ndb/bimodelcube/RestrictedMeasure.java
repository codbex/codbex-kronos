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


package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcube;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelprivilege.Restriction;
import com.codbex.kronos.parser.hdbcalculationview.ndb.datamodeltype.ExpressionLanguage;


/**
 * Java class for RestrictedMeasure complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RestrictedMeasure"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/BiModelCube.ecore}Measure"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="restriction" type="{http://www.sap.com/ndb/BiModelPrivilege.ecore}Restriction" maxOccurs="unbounded"/&gt;
 *         &lt;element name="restrictionExpression" type="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}Expression"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="restrictionExpressionLanguage" type="{http://www.sap.com/ndb/DataModelType.ecore}ExpressionLanguage" /&gt;
 *       &lt;attribute name="baseMeasure" use="required" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}AlphanumericName" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictedMeasure", propOrder = {
    "restriction",
    "restrictionExpression"
})
public class RestrictedMeasure
    extends Measure {

  /** The restriction. */
  protected List<Restriction> restriction;
  
  /** The restriction expression. */
  protected String restrictionExpression;
  
  /** The restriction expression language. */
  @XmlAttribute(name = "restrictionExpressionLanguage")
  protected ExpressionLanguage restrictionExpressionLanguage;
  
  /** The base measure. */
  @XmlAttribute(name = "baseMeasure", required = true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String baseMeasure;

  /**
   * Gets the value of the restriction property.
   * 
   * 
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the restriction property.
   * 
   * 
   * For example, to add a new item, do as follows:
   * <pre>
   *    getRestriction().add(newItem);
   * </pre>
   * 
   * 
   * 
   * Objects of the following type(s) are allowed in the list
   * {@link Restriction }
   *
   * @return the restriction
   */
  public List<Restriction> getRestriction() {
    if (restriction == null) {
      restriction = new ArrayList<Restriction>();
    }
    return this.restriction;
  }

  /**
   * Gets the value of the restrictionExpression property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getRestrictionExpression() {
    return restrictionExpression;
  }

  /**
   * Sets the value of the restrictionExpression property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setRestrictionExpression(String value) {
    this.restrictionExpression = value;
  }

  /**
   * Gets the value of the restrictionExpressionLanguage property.
   *
   * @return possible object is
   * {@link ExpressionLanguage }
   */
  public ExpressionLanguage getRestrictionExpressionLanguage() {
    return restrictionExpressionLanguage;
  }

  /**
   * Sets the value of the restrictionExpressionLanguage property.
   *
   * @param value allowed object is
   *              {@link ExpressionLanguage }
   */
  public void setRestrictionExpressionLanguage(ExpressionLanguage value) {
    this.restrictionExpressionLanguage = value;
  }

  /**
   * Gets the value of the baseMeasure property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getBaseMeasure() {
    return baseMeasure;
  }

  /**
   * Sets the value of the baseMeasure property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setBaseMeasure(String value) {
    this.baseMeasure = value;
  }

}
