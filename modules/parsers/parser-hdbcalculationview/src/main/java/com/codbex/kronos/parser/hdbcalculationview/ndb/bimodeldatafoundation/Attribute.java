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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Attribute (aka physhical attriubute) is an attribute containing a mapping to a physical column
 * only these Attributes can be defined as key and	principal key and only these attributes can be
 * used in a logical join as of now
 *
 *
 * Java class for Attribute complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Attribute"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}AbstractAttribute"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="keyMapping" type="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}ColumnMapping"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="principalKey" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="keepFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="transparentFilter" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Attribute", propOrder = {
    "keyMapping"
})
public class Attribute
    extends AbstractAttribute {

  /** The key mapping. */
  @XmlElement(required = true)
  protected ColumnMapping keyMapping;
  
  /** The principal key. */
  @XmlAttribute(name = "principalKey")
  protected Boolean principalKey;
  
  /** The keep flag. */
  @XmlAttribute(name = "keepFlag")
  protected Boolean keepFlag;
  
  /** The transparent filter. */
  @XmlAttribute(name = "transparentFilter")
  protected Boolean transparentFilter;

  /**
   * Gets the value of the keyMapping property.
   *
   * @return possible object is
   * {@link ColumnMapping }
   */
  public ColumnMapping getKeyMapping() {
    return keyMapping;
  }

  /**
   * Sets the value of the keyMapping property.
   *
   * @param value allowed object is
   *              {@link ColumnMapping }
   */
  public void setKeyMapping(ColumnMapping value) {
    this.keyMapping = value;
  }

  /**
   * Gets the value of the principalKey property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isPrincipalKey() {
    return principalKey;
  }

  /**
   * Sets the value of the principalKey property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setPrincipalKey(Boolean value) {
    this.principalKey = value;
  }

  /**
   * Gets the value of the keepFlag property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isKeepFlag() {
    return keepFlag;
  }

  /**
   * Sets the value of the keepFlag property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setKeepFlag(Boolean value) {
    this.keepFlag = value;
  }

  /**
   * Gets the value of the transparentFilter property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isTransparentFilter() {
    return transparentFilter;
  }

  /**
   * Sets the value of the transparentFilter property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setTransparentFilter(Boolean value) {
    this.transparentFilter = value;
  }

}
