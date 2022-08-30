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


package com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Comment properties like the mime type and the comment text
 *
 *
 * Java class for CommentProperties complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CommentProperties"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="text" use="required" type="{http://www.sap.com/ndb/BaseModelBase.ecore}Comment" /&gt;
 *       &lt;attribute name="mimetype" type="{http://www.sap.com/ndb/BaseModelBase.ecore}Mimetype" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommentProperties")
public class CommentProperties {

  /** The text. */
  @XmlAttribute(name = "text", required = true)
  protected String text;
  
  /** The mimetype. */
  @XmlAttribute(name = "mimetype")
  protected Mimetype mimetype;

  /**
   * Gets the value of the text property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getText() {
    return text;
  }

  /**
   * Sets the value of the text property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setText(String value) {
    this.text = value;
  }

  /**
   * Gets the value of the mimetype property.
   *
   * @return possible object is
   * {@link Mimetype }
   */
  public Mimetype getMimetype() {
    return mimetype;
  }

  /**
   * Sets the value of the mimetype property.
   *
   * @param value allowed object is
   *              {@link Mimetype }
   */
  public void setMimetype(Mimetype value) {
    this.mimetype = value;
  }

}