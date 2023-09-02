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


package com.codbex.kronos.parser.hdbcalculationview.ndb.datamodeltype;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for ObjectType.
 *
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;simpleType name="ObjectType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="attributeview"/&gt;
 *     &lt;enumeration value="analyticview"/&gt;
 *     &lt;enumeration value="calculationview"/&gt;
 *     &lt;enumeration value="hdbprocedure"/&gt;
 *     &lt;enumeration value="hdbtablefunction"/&gt;
 *     &lt;enumeration value="hdbscalarfunction"/&gt;
 *     &lt;enumeration value="hdbruldec"/&gt;
 *     &lt;enumeration value="hdbdd"/&gt;
 *     &lt;enumeration value="procedure"/&gt;
 *     &lt;enumeration value="table"/&gt;
 *     &lt;enumeration value="view"/&gt;
 *     &lt;enumeration value="columnview"/&gt;
 *     &lt;enumeration value="tableFunction"/&gt;
 *     &lt;enumeration value="scalarFunction"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "ObjectType")
@XmlEnum
public enum ObjectType {

  /** The attributeview. */
  @XmlEnumValue("attributeview")
  ATTRIBUTEVIEW("attributeview"),
  
  /** The analyticview. */
  @XmlEnumValue("analyticview")
  ANALYTICVIEW("analyticview"),
  
  /** The calculationview. */
  @XmlEnumValue("calculationview")
  CALCULATIONVIEW("calculationview"),
  
  /** The hdbprocedure. */
  @XmlEnumValue("hdbprocedure")
  HDBPROCEDURE("hdbprocedure"),
  
  /** The hdbtablefunction. */
  @XmlEnumValue("hdbtablefunction")
  HDBTABLEFUNCTION("hdbtablefunction"),
  
  /** The hdbscalarfunction. */
  @XmlEnumValue("hdbscalarfunction")
  HDBSCALARFUNCTION("hdbscalarfunction"),
  
  /** The hdbruldec. */
  @XmlEnumValue("hdbruldec")
  HDBRULDEC("hdbruldec"),
  
  /** The hdbdd. */
  @XmlEnumValue("hdbdd")
  HDBDD("hdbdd"),
  
  /** The procedure. */
  @XmlEnumValue("procedure")
  PROCEDURE("procedure"),
  
  /** The table. */
  @XmlEnumValue("table")
  TABLE("table"),
  
  /** The view. */
  @XmlEnumValue("view")
  VIEW("view"),
  
  /** The columnview. */
  @XmlEnumValue("columnview")
  COLUMNVIEW("columnview"),
  
  /** The table function. */
  @XmlEnumValue("tableFunction")
  TABLE_FUNCTION("tableFunction"),
  
  /** The scalar function. */
  @XmlEnumValue("scalarFunction")
  SCALAR_FUNCTION("scalarFunction");
  
  /** The value. */
  private final String value;

  /**
   * Instantiates a new object type.
   *
   * @param v the v
   */
  ObjectType(String v) {
    value = v;
  }

  /**
   * From value.
   *
   * @param v the v
   * @return the object type
   */
  public static ObjectType fromValue(String v) {
    for (ObjectType c : ObjectType.values()) {
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
