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
package com.codbex.kronos.engine.hdb.parser;

/**
 * These types should respond to globalBuiltInTypeScope in SymbolTable class
 *
 * @see <a href="https://help.sap.com/viewer/52715f71adba4aaeb480d946c742d1f6/2.0.03/en-US/cf394efd3fb4400f9c09d10315028515.html">CDS Primitive Data Types</a>
 */
public enum CdsTypeEnum {
  
  /** The String. */
  String("NVARCHAR"),
  
  /** The Large string. */
  LargeString("NCLOB"),
  
  /** The Binary. */
  Binary("VARBINARY"),
  
  /** The Large binary. */
  LargeBinary("BLOB"),
  
  /** The Integer. */
  Integer("INTEGER"),
  
  /** The Integer 64. */
  Integer64("BIGINT"),
  
  /** The Decimal. */
  Decimal("DECIMAL"),
  
  /** The Binary float. */
  BinaryFloat("DOUBLE"),
  
  /** The Decimal float. */
  DecimalFloat("DECIMAL"),
  
  /** The Local date. */
  LocalDate("DATE"),
  
  /** The Local time. */
  LocalTime("TIME"),
  
  /** The UTC date time. */
  UTCDateTime("SECONDDATE"),
  
  /** The UTC timestamp. */
  UTCTimestamp("TIMESTAMP"),
  
  /** The Boolean. */
  Boolean("BOOLEAN");

  /**
   * Instantiates a new cds type enum.
   *
   * @param sqlType the sql type
   */
  CdsTypeEnum(java.lang.String sqlType) {
    this.sqlType = sqlType;
  }

  /** The sql type. */
  private String sqlType;

  /**
   * Gets the sql type.
   *
   * @return the sql type
   */
  public java.lang.String getSqlType() {
    return sqlType;
  }
}
