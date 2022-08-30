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
// Generated on: 2020.11.27 at 08:01:21 PM EET 
//


package com.codbex.kronos.parser.calculationview.ndb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Java class for anonymous complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="calculationView" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="descriptions" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="viewAttributes"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="viewAttribute" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;simpleContent&gt;
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                   &lt;/extension&gt;
 *                                 &lt;/simpleContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="calculatedViewAttributes" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="input" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="mapping" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;simpleContent&gt;
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                                     &lt;attribute name="target" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                   &lt;/extension&gt;
 *                                 &lt;/simpleContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="node" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="filter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="joinAttribute" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="filterExpressionLanguage" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="joinType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="languageColumn" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "calculationView"
})
@XmlRootElement(name = "calculationViews")
public class CalculationViews {

  /** The calculation view. */
  protected List<CalculationViews.CalculationView> calculationView;

  /**
   * Gets the value of the calculationView property.
   * 
   * 
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the calculationView property.
   * 
   * 
   * For example, to add a new item, do as follows:
   * <pre>
   *    getCalculationView().add(newItem);
   * </pre>
   * 
   * 
   * 
   * Objects of the following type(s) are allowed in the list
   * {@link CalculationViews.CalculationView }
   *
   * @return the calculation view
   */
  public List<CalculationViews.CalculationView> getCalculationView() {
    if (calculationView == null) {
      calculationView = new ArrayList<CalculationViews.CalculationView>();
    }
    return this.calculationView;
  }


  /**
   * Java class for anonymous complex type.
   *
   * The following schema fragment specifies the expected content contained within this class.
   *
   * <pre>
   * &lt;complexType&gt;
   *   &lt;complexContent&gt;
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
   *       &lt;sequence&gt;
   *         &lt;element name="descriptions" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
   *         &lt;element name="viewAttributes"&gt;
   *           &lt;complexType&gt;
   *             &lt;complexContent&gt;
   *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
   *                 &lt;sequence&gt;
   *                   &lt;element name="viewAttribute" maxOccurs="unbounded" minOccurs="0"&gt;
   *                     &lt;complexType&gt;
   *                       &lt;simpleContent&gt;
   *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
   *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *                         &lt;/extension&gt;
   *                       &lt;/simpleContent&gt;
   *                     &lt;/complexType&gt;
   *                   &lt;/element&gt;
   *                 &lt;/sequence&gt;
   *               &lt;/restriction&gt;
   *             &lt;/complexContent&gt;
   *           &lt;/complexType&gt;
   *         &lt;/element&gt;
   *         &lt;element name="calculatedViewAttributes" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
   *         &lt;element name="input" maxOccurs="unbounded" minOccurs="0"&gt;
   *           &lt;complexType&gt;
   *             &lt;complexContent&gt;
   *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
   *                 &lt;sequence&gt;
   *                   &lt;element name="mapping" maxOccurs="unbounded" minOccurs="0"&gt;
   *                     &lt;complexType&gt;
   *                       &lt;simpleContent&gt;
   *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
   *                           &lt;attribute name="target" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *                           &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *                         &lt;/extension&gt;
   *                       &lt;/simpleContent&gt;
   *                     &lt;/complexType&gt;
   *                   &lt;/element&gt;
   *                 &lt;/sequence&gt;
   *                 &lt;attribute name="node" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *               &lt;/restriction&gt;
   *             &lt;/complexContent&gt;
   *           &lt;/complexType&gt;
   *         &lt;/element&gt;
   *         &lt;element name="filter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
   *         &lt;element name="joinAttribute" minOccurs="0"&gt;
   *           &lt;complexType&gt;
   *             &lt;simpleContent&gt;
   *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
   *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *               &lt;/extension&gt;
   *             &lt;/simpleContent&gt;
   *           &lt;/complexType&gt;
   *         &lt;/element&gt;
   *       &lt;/sequence&gt;
   *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *       &lt;attribute name="filterExpressionLanguage" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *       &lt;attribute name="joinType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *       &lt;attribute name="languageColumn" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *     &lt;/restriction&gt;
   *   &lt;/complexContent&gt;
   * &lt;/complexType&gt;
   * </pre>
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {
      "descriptions",
      "viewAttributes",
      "calculatedViewAttributes",
      "input",
      "filter",
      "joinAttribute"
  })
  public static class CalculationView {

    /** The descriptions. */
    @XmlElement(required = true)
    protected String descriptions;
    
    /** The view attributes. */
    @XmlElement(required = true)
    protected CalculationViews.CalculationView.ViewAttributes viewAttributes;
    
    /** The calculated view attributes. */
    @XmlElement(required = true)
    protected String calculatedViewAttributes;
    
    /** The input. */
    protected List<CalculationViews.CalculationView.Input> input;
    
    /** The filter. */
    protected String filter;
    
    /** The join attribute. */
    protected CalculationViews.CalculationView.JoinAttribute joinAttribute;
    
    /** The id. */
    @XmlAttribute(name = "id")
    protected String id;
    
    /** The filter expression language. */
    @XmlAttribute(name = "filterExpressionLanguage")
    protected String filterExpressionLanguage;
    
    /** The join type. */
    @XmlAttribute(name = "joinType")
    protected String joinType;
    
    /** The language column. */
    @XmlAttribute(name = "languageColumn")
    protected String languageColumn;

    /**
     * Gets the value of the descriptions property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescriptions() {
      return descriptions;
    }

    /**
     * Sets the value of the descriptions property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescriptions(String value) {
      this.descriptions = value;
    }

    /**
     * Gets the value of the viewAttributes property.
     *
     * @return possible object is
     * {@link CalculationViews.CalculationView.ViewAttributes }
     */
    public CalculationViews.CalculationView.ViewAttributes getViewAttributes() {
      return viewAttributes;
    }

    /**
     * Sets the value of the viewAttributes property.
     *
     * @param value allowed object is
     *              {@link CalculationViews.CalculationView.ViewAttributes }
     */
    public void setViewAttributes(CalculationViews.CalculationView.ViewAttributes value) {
      this.viewAttributes = value;
    }

    /**
     * Gets the value of the calculatedViewAttributes property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCalculatedViewAttributes() {
      return calculatedViewAttributes;
    }

    /**
     * Sets the value of the calculatedViewAttributes property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCalculatedViewAttributes(String value) {
      this.calculatedViewAttributes = value;
    }

    /**
     * Gets the value of the input property.
     * 
     * 
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the input property.
     * 
     * 
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInput().add(newItem);
     * </pre>
     * 
     * 
     * 
     * Objects of the following type(s) are allowed in the list
     * {@link CalculationViews.CalculationView.Input }
     *
     * @return the input
     */
    public List<CalculationViews.CalculationView.Input> getInput() {
      if (input == null) {
        input = new ArrayList<CalculationViews.CalculationView.Input>();
      }
      return this.input;
    }

    /**
     * Gets the value of the filter property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFilter() {
      return filter;
    }

    /**
     * Sets the value of the filter property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFilter(String value) {
      this.filter = value;
    }

    /**
     * Gets the value of the joinAttribute property.
     *
     * @return possible object is
     * {@link CalculationViews.CalculationView.JoinAttribute }
     */
    public CalculationViews.CalculationView.JoinAttribute getJoinAttribute() {
      return joinAttribute;
    }

    /**
     * Sets the value of the joinAttribute property.
     *
     * @param value allowed object is
     *              {@link CalculationViews.CalculationView.JoinAttribute }
     */
    public void setJoinAttribute(CalculationViews.CalculationView.JoinAttribute value) {
      this.joinAttribute = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
      return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
      this.id = value;
    }

    /**
     * Gets the value of the filterExpressionLanguage property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFilterExpressionLanguage() {
      return filterExpressionLanguage;
    }

    /**
     * Sets the value of the filterExpressionLanguage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFilterExpressionLanguage(String value) {
      this.filterExpressionLanguage = value;
    }

    /**
     * Gets the value of the joinType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getJoinType() {
      return joinType;
    }

    /**
     * Sets the value of the joinType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setJoinType(String value) {
      this.joinType = value;
    }

    /**
     * Gets the value of the languageColumn property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLanguageColumn() {
      return languageColumn;
    }

    /**
     * Sets the value of the languageColumn property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLanguageColumn(String value) {
      this.languageColumn = value;
    }


    /**
     * Java class for anonymous complex type.
     *
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="mapping" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                 &lt;attribute name="target" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="node" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "mapping"
    })
    public static class Input {

      /** The mapping. */
      protected List<CalculationViews.CalculationView.Input.Mapping> mapping;
      
      /** The node. */
      @XmlAttribute(name = "node")
      protected String node;

      /**
       * Gets the value of the mapping property.
       * 
       * 
       * This accessor method returns a reference to the live list,
       * not a snapshot. Therefore any modification you make to the
       * returned list will be present inside the JAXB object.
       * This is why there is not a <CODE>set</CODE> method for the mapping property.
       * 
       * 
       * For example, to add a new item, do as follows:
       * <pre>
       *    getMapping().add(newItem);
       * </pre>
       * 
       * 
       * 
       * Objects of the following type(s) are allowed in the list
       * {@link CalculationViews.CalculationView.Input.Mapping }
       *
       * @return the mapping
       */
      public List<CalculationViews.CalculationView.Input.Mapping> getMapping() {
        if (mapping == null) {
          mapping = new ArrayList<CalculationViews.CalculationView.Input.Mapping>();
        }
        return this.mapping;
      }

      /**
       * Gets the value of the node property.
       *
       * @return possible object is
       * {@link String }
       */
      public String getNode() {
        return node;
      }

      /**
       * Sets the value of the node property.
       *
       * @param value allowed object is
       *              {@link String }
       */
      public void setNode(String value) {
        this.node = value;
      }


      /**
       * Java class for anonymous complex type.
       *
       * The following schema fragment specifies the expected content contained within this class.
       *
       * <pre>
       * &lt;complexType&gt;
       *   &lt;simpleContent&gt;
       *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
       *       &lt;attribute name="target" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
       *       &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
       *     &lt;/extension&gt;
       *   &lt;/simpleContent&gt;
       * &lt;/complexType&gt;
       * </pre>
       */
      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {
          "value"
      })
      public static class Mapping {

        /** The value. */
        @XmlValue
        protected String value;
        
        /** The target. */
        @XmlAttribute(name = "target")
        protected String target;
        
        /** The source. */
        @XmlAttribute(name = "source")
        protected String source;

        /**
         * Gets the value of the value property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getValue() {
          return value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setValue(String value) {
          this.value = value;
        }

        /**
         * Gets the value of the target property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getTarget() {
          return target;
        }

        /**
         * Sets the value of the target property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setTarget(String value) {
          this.target = value;
        }

        /**
         * Gets the value of the source property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getSource() {
          return source;
        }

        /**
         * Sets the value of the source property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setSource(String value) {
          this.source = value;
        }

      }

    }


    /**
     * Java class for anonymous complex type.
     *
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class JoinAttribute {

      /** The value. */
      @XmlValue
      protected String value;
      
      /** The name. */
      @XmlAttribute(name = "name")
      protected String name;

      /**
       * Gets the value of the value property.
       *
       * @return possible object is
       * {@link String }
       */
      public String getValue() {
        return value;
      }

      /**
       * Sets the value of the value property.
       *
       * @param value allowed object is
       *              {@link String }
       */
      public void setValue(String value) {
        this.value = value;
      }

      /**
       * Gets the value of the name property.
       *
       * @return possible object is
       * {@link String }
       */
      public String getName() {
        return name;
      }

      /**
       * Sets the value of the name property.
       *
       * @param value allowed object is
       *              {@link String }
       */
      public void setName(String value) {
        this.name = value;
      }

    }


    /**
     * Java class for anonymous complex type.
     *
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="viewAttribute" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "viewAttribute"
    })
    public static class ViewAttributes {

      /** The view attribute. */
      protected List<CalculationViews.CalculationView.ViewAttributes.ViewAttribute> viewAttribute;

      /**
       * Gets the value of the viewAttribute property.
       * 
       * 
       * This accessor method returns a reference to the live list,
       * not a snapshot. Therefore any modification you make to the
       * returned list will be present inside the JAXB object.
       * This is why there is not a <CODE>set</CODE> method for the viewAttribute property.
       * 
       * 
       * For example, to add a new item, do as follows:
       * <pre>
       *    getViewAttribute().add(newItem);
       * </pre>
       * 
       * 
       * 
       * Objects of the following type(s) are allowed in the list
       * {@link CalculationViews.CalculationView.ViewAttributes.ViewAttribute }
       *
       * @return the view attribute
       */
      public List<CalculationViews.CalculationView.ViewAttributes.ViewAttribute> getViewAttribute() {
        if (viewAttribute == null) {
          viewAttribute = new ArrayList<CalculationViews.CalculationView.ViewAttributes.ViewAttribute>();
        }
        return this.viewAttribute;
      }


      /**
       * Java class for anonymous complex type.
       *
       * The following schema fragment specifies the expected content contained within this class.
       *
       * <pre>
       * &lt;complexType&gt;
       *   &lt;simpleContent&gt;
       *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
       *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
       *     &lt;/extension&gt;
       *   &lt;/simpleContent&gt;
       * &lt;/complexType&gt;
       * </pre>
       */
      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {
          "value"
      })
      public static class ViewAttribute {

        /** The value. */
        @XmlValue
        protected String value;
        
        /** The id. */
        @XmlAttribute(name = "id")
        protected String id;

        /**
         * Gets the value of the value property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getValue() {
          return value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setValue(String value) {
          this.value = value;
        }

        /**
         * Gets the value of the id property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getId() {
          return id;
        }

        /**
         * Sets the value of the id property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setId(String value) {
          this.id = value;
        }

      }

    }

  }

}