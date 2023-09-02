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


package com.codbex.kronos.parser.hdbcalculationview.ndb.repositorymodelresource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Information about the origin of the resource, if exported from external system
 *
 * Java class for Origin complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Origin"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="entityName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="entityType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="system" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Origin")
public class Origin {

  /** The entity name. */
  @XmlAttribute(name = "entityName")
  protected String entityName;
  
  /** The entity type. */
  @XmlAttribute(name = "entityType")
  protected String entityType;
  
  /** The system. */
  @XmlAttribute(name = "system")
  protected String system;

  /**
   * Gets the value of the entityName property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getEntityName() {
    return entityName;
  }

  /**
   * Sets the value of the entityName property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setEntityName(String value) {
    this.entityName = value;
  }

  /**
   * Gets the value of the entityType property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getEntityType() {
    return entityType;
  }

  /**
   * Sets the value of the entityType property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setEntityType(String value) {
    this.entityType = value;
  }

  /**
   * Gets the value of the system property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getSystem() {
    return system;
  }

  /**
   * Sets the value of the system property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setSystem(String value) {
    this.system = value;
  }

}
