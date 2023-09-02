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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.Descriptions;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcalculation.Node;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldatafoundation.LogicalColumn;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldimension.Hierarchy;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldimension.LocalDimension;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelvariable.LocalVariable;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelvariable.ValueListEntry;


/**
 * Base object for ID based model classes that allow multiple language descriptions
 *
 * Java class for IDObjectWithDescription complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="IDObjectWithDescription"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/RepositoryModelResource.ecore}IDObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="descriptions" type="{http://www.sap.com/ndb/BaseModelBase.ecore}Descriptions" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IDObjectWithDescription", propOrder = {
    "descriptions"
})
@XmlSeeAlso({
    Node.class,
    LocalVariable.class,
    ValueListEntry.class,
    LogicalColumn.class,
    LocalDimension.class,
    Hierarchy.class
})
public abstract class IDObjectWithDescription
    extends IDObject {

  /** The descriptions. */
  protected Descriptions descriptions;

  /**
   * Gets the value of the descriptions property.
   *
   * @return possible object is
   * {@link Descriptions }
   */
  public Descriptions getDescriptions() {
    return descriptions;
  }

  /**
   * Sets the value of the descriptions property.
   *
   * @param value allowed object is
   *              {@link Descriptions }
   */
  public void setDescriptions(Descriptions value) {
    this.descriptions = value;
  }

}
