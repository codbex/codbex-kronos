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

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.sap.ndb.datamodeltype package.
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
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sap.ndb.datamodeltype
   */
  public ObjectFactory() {
  }

  /**
   * Create an instance of {@link SimpleType }.
   *
   * @return the simple type
   */
  public SimpleType createSimpleType() {
    return new SimpleType();
  }

  /**
   * Create an instance of {@link ValueRange }.
   *
   * @return the value range
   */
  public ValueRange createValueRange() {
    return new ValueRange();
  }

  /**
   * Create an instance of {@link StructureType }.
   *
   * @return the structure type
   */
  public StructureType createStructureType() {
    return new StructureType();
  }

  /**
   * Create an instance of {@link DisplayFolder }.
   *
   * @return the display folder
   */
  public DisplayFolder createDisplayFolder() {
    return new DisplayFolder();
  }

  /**
   * Create an instance of {@link InternalExternalConversion }.
   *
   * @return the internal external conversion
   */
  public InternalExternalConversion createInternalExternalConversion() {
    return new InternalExternalConversion();
  }

  /**
   * Create an instance of {@link Element }.
   *
   * @return the element
   */
  public Element createElement() {
    return new Element();
  }

  /**
   * Create an instance of {@link ElementRuntime }.
   *
   * @return the element runtime
   */
  public ElementRuntime createElementRuntime() {
    return new ElementRuntime();
  }

  /**
   * Create an instance of {@link Expression }.
   *
   * @return the expression
   */
  public Expression createExpression() {
    return new Expression();
  }

  /**
   * Create an instance of {@link ElementRelationship }.
   *
   * @return the element relationship
   */
  public ElementRelationship createElementRelationship() {
    return new ElementRelationship();
  }

  /**
   * Create an instance of {@link ElementFilter }.
   *
   * @return the element filter
   */
  public ElementFilter createElementFilter() {
    return new ElementFilter();
  }

  /**
   * Create an instance of {@link ElementRefFilter }.
   *
   * @return the element ref filter
   */
  public ElementRefFilter createElementRefFilter() {
    return new ElementRefFilter();
  }

  /**
   * Create an instance of {@link ExceptionAggregationStep }.
   *
   * @return the exception aggregation step
   */
  public ExceptionAggregationStep createExceptionAggregationStep() {
    return new ExceptionAggregationStep();
  }

  /**
   * Create an instance of {@link UDFParameter }.
   *
   * @return the UDF parameter
   */
  public UDFParameter createUDFParameter() {
    return new UDFParameter();
  }

  /**
   * Create an instance of {@link CurrencyConversion }.
   *
   * @return the currency conversion
   */
  public CurrencyConversion createCurrencyConversion() {
    return new CurrencyConversion();
  }

  /**
   * Create an instance of {@link UnitConversion }.
   *
   * @return the unit conversion
   */
  public UnitConversion createUnitConversion() {
    return new UnitConversion();
  }

  /**
   * Create an instance of {@link ElementMapping }.
   *
   * @return the element mapping
   */
  public ElementMapping createElementMapping() {
    return new ElementMapping();
  }

  /**
   * Create an instance of {@link ConstantElementMapping }.
   *
   * @return the constant element mapping
   */
  public ConstantElementMapping createConstantElementMapping() {
    return new ConstantElementMapping();
  }

  /**
   * Create an instance of {@link Order }.
   *
   * @return the order
   */
  public Order createOrder() {
    return new Order();
  }

}
