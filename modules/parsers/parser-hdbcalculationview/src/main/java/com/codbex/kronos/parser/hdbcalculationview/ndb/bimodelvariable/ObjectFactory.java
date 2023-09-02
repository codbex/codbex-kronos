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


package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelvariable;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.sap.ndb.bimodelvariable package.
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
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sap.ndb.bimodelvariable
   */
  public ObjectFactory() {
  }

  /**
   * Create an instance of {@link LocalVariable }.
   *
   * @return the local variable
   */
  public LocalVariable createLocalVariable() {
    return new LocalVariable();
  }

  /**
   * Create an instance of {@link VariableProperties }.
   *
   * @return the variable properties
   */
  public VariableProperties createVariableProperties() {
    return new VariableProperties();
  }

  /**
   * Create an instance of {@link ValueDomain }.
   *
   * @return the value domain
   */
  public ValueDomain createValueDomain() {
    return new ValueDomain();
  }

  /**
   * Create an instance of {@link Selection }.
   *
   * @return the selection
   */
  public Selection createSelection() {
    return new Selection();
  }

  /**
   * Create an instance of {@link DerivationRule }.
   *
   * @return the derivation rule
   */
  public DerivationRule createDerivationRule() {
    return new DerivationRule();
  }

  /**
   * Create an instance of {@link ValueListEntry }.
   *
   * @return the value list entry
   */
  public ValueListEntry createValueListEntry() {
    return new ValueListEntry();
  }

  /**
   * Create an instance of {@link LocalVariables }.
   *
   * @return the local variables
   */
  public LocalVariables createLocalVariables() {
    return new LocalVariables();
  }

  /**
   * Create an instance of {@link TargetVariable }.
   *
   * @return the target variable
   */
  public TargetVariable createTargetVariable() {
    return new TargetVariable();
  }

  /**
   * Create an instance of {@link VariableMapping }.
   *
   * @return the variable mapping
   */
  public VariableMapping createVariableMapping() {
    return new VariableMapping();
  }

  /**
   * Create an instance of {@link ConstantVariableMapping }.
   *
   * @return the constant variable mapping
   */
  public ConstantVariableMapping createConstantVariableMapping() {
    return new ConstantVariableMapping();
  }

  /**
   * Create an instance of {@link VariableMappings }.
   *
   * @return the variable mappings
   */
  public VariableMappings createVariableMappings() {
    return new VariableMappings();
  }

}
