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
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for TemporalCondition.
 *
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;simpleType name="TemporalCondition"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="INCLUDE_TO_EXCLUDE_FROM"/&gt;
 *     &lt;enumeration value="EXCLUDE_TO_INCLUDE_FROM"/&gt;
 *     &lt;enumeration value="EXCLUDE_BOTH"/&gt;
 *     &lt;enumeration value="INCLUDE_BOTH"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "TemporalCondition")
@XmlEnum
public enum TemporalCondition {

  /** The include to exclude from. */
  INCLUDE_TO_EXCLUDE_FROM,
  
  /** The exclude to include from. */
  EXCLUDE_TO_INCLUDE_FROM,
  
  /** The exclude both. */
  EXCLUDE_BOTH,
  
  /** The include both. */
  INCLUDE_BOTH;

  /**
   * From value.
   *
   * @param v the v
   * @return the temporal condition
   */
  public static TemporalCondition fromValue(String v) {
    return valueOf(v);
  }

  /**
   * Value.
   *
   * @return the string
   */
  public String value() {
    return name();
  }

}
