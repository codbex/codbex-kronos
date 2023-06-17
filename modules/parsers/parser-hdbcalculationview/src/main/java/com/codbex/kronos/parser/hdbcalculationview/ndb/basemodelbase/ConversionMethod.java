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


package com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for ConversionMethod.
 *
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;simpleType name="ConversionMethod"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ERP"/&gt;
 *     &lt;enumeration value="Banking"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "ConversionMethod")
@XmlEnum
public enum ConversionMethod {


  /**
   * Join type referential integrity means for each row in the left table there is a corresponding row in the right
   * table and vice versa.   If this join type is selected the engine can better optimize the queries, because the
   * join can be completely ignored if no fields are requested from the joined table.
   */
  ERP("ERP"),
  
  /** The banking. */
  @XmlEnumValue("Banking")
  BANKING("Banking");
  
  /** The value. */
  private final String value;

  /**
   * Instantiates a new conversion method.
   *
   * @param v the v
   */
  ConversionMethod(String v) {
    value = v;
  }

  /**
   * From value.
   *
   * @param v the v
   * @return the conversion method
   */
  public static ConversionMethod fromValue(String v) {
    for (ConversionMethod c : ConversionMethod.values()) {
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
