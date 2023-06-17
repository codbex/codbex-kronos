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
 * Java class for ErrorHandling.
 *
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;simpleType name="ErrorHandling"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="failOnError"/&gt;
 *     &lt;enumeration value="setToNull"/&gt;
 *     &lt;enumeration value="keepUnconverted"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "ErrorHandling")
@XmlEnum
public enum ErrorHandling {


  /** this produces an SQL engine error. */
  @XmlEnumValue("failOnError")
  FAIL_ON_ERROR("failOnError"),

  /** the target measure and the currency are set to NULL. */
  @XmlEnumValue("setToNull")
  SET_TO_NULL("setToNull"),

  /** the measure is not converted. */
  @XmlEnumValue("keepUnconverted")
  KEEP_UNCONVERTED("keepUnconverted");
  
  /** The value. */
  private final String value;

  /**
   * Instantiates a new error handling.
   *
   * @param v the v
   */
  ErrorHandling(String v) {
    value = v;
  }

  /**
   * From value.
   *
   * @param v the v
   * @return the error handling
   */
  public static ErrorHandling fromValue(String v) {
    for (ErrorHandling c : ErrorHandling.values()) {
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
