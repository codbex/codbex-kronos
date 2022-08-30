/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
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


package com.codbex.kronos.parser.hdbcalculationview.ndb.datamodelfilter;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.sap.ndb.datamodelfilter package.
 * An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {


  /**
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sap.ndb.datamodelfilter
   */
  public ObjectFactory() {
  }

  /**
   * Create an instance of {@link ValueFilterOperand }.
   *
   * @return the value filter operand
   */
  public ValueFilterOperand createValueFilterOperand() {
    return new ValueFilterOperand();
  }

  /**
   * Create an instance of {@link SingleValueFilter }.
   *
   * @return the single value filter
   */
  public SingleValueFilter createSingleValueFilter() {
    return new SingleValueFilter();
  }

  /**
   * Create an instance of {@link RangeValueFilter }.
   *
   * @return the range value filter
   */
  public RangeValueFilter createRangeValueFilter() {
    return new RangeValueFilter();
  }

  /**
   * Create an instance of {@link ListValueFilter }.
   *
   * @return the list value filter
   */
  public ListValueFilter createListValueFilter() {
    return new ListValueFilter();
  }

  /**
   * Create an instance of {@link DefaultRange }.
   *
   * @return the default range
   */
  public DefaultRange createDefaultRange() {
    return new DefaultRange();
  }

}