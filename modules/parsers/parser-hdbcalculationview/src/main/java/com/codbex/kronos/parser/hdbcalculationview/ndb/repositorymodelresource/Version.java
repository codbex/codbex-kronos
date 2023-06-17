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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for Version.
 *
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;simpleType name="Version"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="active"/&gt;
 *     &lt;enumeration value="inactive"/&gt;
 *     &lt;enumeration value="historical"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "Version")
@XmlEnum
public enum Version {

  /** The active. */
  @XmlEnumValue("active")
  ACTIVE("active"),
  
  /** The inactive. */
  @XmlEnumValue("inactive")
  INACTIVE("inactive"),
  
  /** The historical. */
  @XmlEnumValue("historical")
  HISTORICAL("historical");
  
  /** The value. */
  private final String value;

  /**
   * Instantiates a new version.
   *
   * @param v the v
   */
  Version(String v) {
    value = v;
  }

  /**
   * From value.
   *
   * @param v the v
   * @return the version
   */
  public static Version fromValue(String v) {
    for (Version c : Version.values()) {
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
