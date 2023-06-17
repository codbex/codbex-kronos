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


package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcalculation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.NameValuePair;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcube.MeasureGroup;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldatafoundation.InformationModel;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldatafoundation.Layout;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldimension.Hierarchy;
import com.codbex.kronos.parser.hdbcalculationview.ndb.datamodelentity.DataCategory;
import com.codbex.kronos.parser.hdbcalculationview.ndb.datamodelentity.DimensionType;


/**
 * A Calculation scenario scenario is a collection of calculation views. One of the calculation views is marked
 * as defaultView. Therefore the calculation scenario is identified with the default view to the SQL layer.
 * Sometimes a calculation scenario is simply	addressed as calculation view meaning the default view.
 * Constraints / validations: 1. Exactly one node (a DataSource or a Calculation view) is mapped to the
 * logical model via it's ID 2. A JoinView must have exactly two inputs 3. A UnionView must have at least one
 * input 4. A ProjectionView must have at exactly	one input
 *
 *
 * Java class for CalculationScenario complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CalculationScenario"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}InformationModel"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="executionHints" type="{http://www.sap.com/ndb/BaseModelBase.ecore}NameValuePair" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dataSources" type="{http://www.sap.com/ndb/BiModelCalculation.ecore}DataSources" minOccurs="0"/&gt;
 *         &lt;element name="calculationViews" type="{http://www.sap.com/ndb/BiModelCalculation.ecore}CalculationViews" minOccurs="0"/&gt;
 *         &lt;element name="inlineHierarchy" type="{http://www.sap.com/ndb/BiModelDimension.ecore}Hierarchy" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="logicalModel" type="{http://www.sap.com/ndb/BiModelCube.ecore}MeasureGroup" minOccurs="0"/&gt;
 *         &lt;element name="layout" type="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}Layout" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="runWithInvokerPrivileges" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="rowCounterName" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}AlphanumericName" /&gt;
 *       &lt;attribute name="enforceSqlExecution" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="alwaysAggregateResult" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="outputViewType" type="{http://www.sap.com/ndb/BiModelCalculation.ecore}CalculationViewType" default="Aggregation" /&gt;
 *       &lt;attribute name="dataCategory" type="{http://www.sap.com/ndb/DataModelEntity.ecore}DataCategory" /&gt;
 *       &lt;attribute name="dimensionType" type="{http://www.sap.com/ndb/DataModelEntity.ecore}DimensionType" default="STANDARD" /&gt;
 *       &lt;attribute name="defaultMember" type="{http://www.sap.com/ndb/BaseModelBase.ecore}DefaultMember" /&gt;
 *       &lt;attribute name="pruningTable" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}FQName" /&gt;
 *       &lt;attribute name="analyticViewCompatibilityMode" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="propagateInstantiation" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="determineTextFuzzySearchEnablement" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="xscCompatibilityMode" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CalculationScenario", propOrder = {
    "executionHints",
    "dataSources",
    "calculationViews",
    "inlineHierarchy",
    "logicalModel",
    "layout"
})
@XmlRootElement
public class CalculationScenario
    extends InformationModel {

  /** The execution hints. */
  protected List<NameValuePair> executionHints;
  
  /** The data sources. */
  protected DataSources dataSources;
  
  /** The calculation views. */
  protected CalculationViews calculationViews;
  
  /** The inline hierarchy. */
  protected List<Hierarchy> inlineHierarchy;
  
  /** The logical model. */
  protected MeasureGroup logicalModel;
  
  /** The layout. */
  protected Layout layout;
  
  /** The run with invoker privileges. */
  @XmlAttribute(name = "runWithInvokerPrivileges")
  protected Boolean runWithInvokerPrivileges;
  
  /** The row counter name. */
  @XmlAttribute(name = "rowCounterName")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String rowCounterName;
  
  /** The enforce sql execution. */
  @XmlAttribute(name = "enforceSqlExecution")
  protected Boolean enforceSqlExecution;
  
  /** The always aggregate result. */
  @XmlAttribute(name = "alwaysAggregateResult")
  protected Boolean alwaysAggregateResult;
  
  /** The output view type. */
  @XmlAttribute(name = "outputViewType")
  protected CalculationViewType outputViewType;
  
  /** The data category. */
  @XmlAttribute(name = "dataCategory")
  protected DataCategory dataCategory;
  
  /** The dimension type. */
  @XmlAttribute(name = "dimensionType")
  protected DimensionType dimensionType;
  
  /** The default member. */
  @XmlAttribute(name = "defaultMember")
  protected String defaultMember;
  
  /** The pruning table. */
  @XmlAttribute(name = "pruningTable")
  protected String pruningTable;
  
  /** The analytic view compatibility mode. */
  @XmlAttribute(name = "analyticViewCompatibilityMode")
  protected Boolean analyticViewCompatibilityMode;
  
  /** The propagate instantiation. */
  @XmlAttribute(name = "propagateInstantiation")
  protected Boolean propagateInstantiation;
  
  /** The determine text fuzzy search enablement. */
  @XmlAttribute(name = "determineTextFuzzySearchEnablement")
  protected Boolean determineTextFuzzySearchEnablement;
  
  /** The xsc compatibility mode. */
  @XmlAttribute(name = "xscCompatibilityMode")
  protected Boolean xscCompatibilityMode;

  /**
   * Gets the value of the executionHints property.
   * 
   * 
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the executionHints property.
   * 
   * 
   * For example, to add a new item, do as follows:
   * <pre>
   *    getExecutionHints().add(newItem);
   * </pre>
   * 
   * 
   * 
   * Objects of the following type(s) are allowed in the list
   * {@link NameValuePair }
   *
   * @return the execution hints
   */
  public List<NameValuePair> getExecutionHints() {
    if (executionHints == null) {
      executionHints = new ArrayList<NameValuePair>();
    }
    return this.executionHints;
  }

  /**
   * Gets the value of the dataSources property.
   *
   * @return possible object is
   * {@link DataSources }
   */
  public DataSources getDataSources() {
    return dataSources;
  }

  /**
   * Sets the value of the dataSources property.
   *
   * @param value allowed object is
   *              {@link DataSources }
   */
  public void setDataSources(DataSources value) {
    this.dataSources = value;
  }

  /**
   * Gets the value of the calculationViews property.
   *
   * @return possible object is
   * {@link CalculationViews }
   */
  public CalculationViews getCalculationViews() {
    return calculationViews;
  }

  /**
   * Sets the value of the calculationViews property.
   *
   * @param value allowed object is
   *              {@link CalculationViews }
   */
  public void setCalculationViews(CalculationViews value) {
    this.calculationViews = value;
  }

  /**
   * Gets the value of the inlineHierarchy property.
   * 
   * 
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the inlineHierarchy property.
   * 
   * 
   * For example, to add a new item, do as follows:
   * <pre>
   *    getInlineHierarchy().add(newItem);
   * </pre>
   * 
   * 
   * 
   * Objects of the following type(s) are allowed in the list
   * {@link Hierarchy }
   *
   * @return the inline hierarchy
   */
  public List<Hierarchy> getInlineHierarchy() {
    if (inlineHierarchy == null) {
      inlineHierarchy = new ArrayList<Hierarchy>();
    }
    return this.inlineHierarchy;
  }

  /**
   * Gets the value of the logicalModel property.
   *
   * @return possible object is
   * {@link MeasureGroup }
   */
  public MeasureGroup getLogicalModel() {
    return logicalModel;
  }

  /**
   * Sets the value of the logicalModel property.
   *
   * @param value allowed object is
   *              {@link MeasureGroup }
   */
  public void setLogicalModel(MeasureGroup value) {
    this.logicalModel = value;
  }

  /**
   * Gets the value of the layout property.
   *
   * @return possible object is
   * {@link Layout }
   */
  public Layout getLayout() {
    return layout;
  }

  /**
   * Sets the value of the layout property.
   *
   * @param value allowed object is
   *              {@link Layout }
   */
  public void setLayout(Layout value) {
    this.layout = value;
  }

  /**
   * Gets the value of the runWithInvokerPrivileges property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isRunWithInvokerPrivileges() {
    return runWithInvokerPrivileges;
  }

  /**
   * Sets the value of the runWithInvokerPrivileges property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setRunWithInvokerPrivileges(Boolean value) {
    this.runWithInvokerPrivileges = value;
  }

  /**
   * Gets the value of the rowCounterName property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getRowCounterName() {
    return rowCounterName;
  }

  /**
   * Sets the value of the rowCounterName property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setRowCounterName(String value) {
    this.rowCounterName = value;
  }

  /**
   * Gets the value of the enforceSqlExecution property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isEnforceSqlExecution() {
    return enforceSqlExecution;
  }

  /**
   * Sets the value of the enforceSqlExecution property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setEnforceSqlExecution(Boolean value) {
    this.enforceSqlExecution = value;
  }

  /**
   * Gets the value of the alwaysAggregateResult property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isAlwaysAggregateResult() {
    return alwaysAggregateResult;
  }

  /**
   * Sets the value of the alwaysAggregateResult property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setAlwaysAggregateResult(Boolean value) {
    this.alwaysAggregateResult = value;
  }

  /**
   * Gets the value of the outputViewType property.
   *
   * @return possible object is
   * {@link CalculationViewType }
   */
  public CalculationViewType getOutputViewType() {
    if (outputViewType == null) {
      return CalculationViewType.AGGREGATION;
    } else {
      return outputViewType;
    }
  }

  /**
   * Sets the value of the outputViewType property.
   *
   * @param value allowed object is
   *              {@link CalculationViewType }
   */
  public void setOutputViewType(CalculationViewType value) {
    this.outputViewType = value;
  }

  /**
   * Gets the value of the dataCategory property.
   *
   * @return possible object is
   * {@link DataCategory }
   */
  public DataCategory getDataCategory() {
    return dataCategory;
  }

  /**
   * Sets the value of the dataCategory property.
   *
   * @param value allowed object is
   *              {@link DataCategory }
   */
  public void setDataCategory(DataCategory value) {
    this.dataCategory = value;
  }

  /**
   * Gets the value of the dimensionType property.
   *
   * @return possible object is
   * {@link DimensionType }
   */
  public DimensionType getDimensionType() {
    if (dimensionType == null) {
      return DimensionType.STANDARD;
    } else {
      return dimensionType;
    }
  }

  /**
   * Sets the value of the dimensionType property.
   *
   * @param value allowed object is
   *              {@link DimensionType }
   */
  public void setDimensionType(DimensionType value) {
    this.dimensionType = value;
  }

  /**
   * Gets the value of the defaultMember property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getDefaultMember() {
    return defaultMember;
  }

  /**
   * Sets the value of the defaultMember property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setDefaultMember(String value) {
    this.defaultMember = value;
  }

  /**
   * Gets the value of the pruningTable property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getPruningTable() {
    return pruningTable;
  }

  /**
   * Sets the value of the pruningTable property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setPruningTable(String value) {
    this.pruningTable = value;
  }

  /**
   * Gets the value of the analyticViewCompatibilityMode property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isAnalyticViewCompatibilityMode() {
    return analyticViewCompatibilityMode;
  }

  /**
   * Sets the value of the analyticViewCompatibilityMode property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setAnalyticViewCompatibilityMode(Boolean value) {
    this.analyticViewCompatibilityMode = value;
  }

  /**
   * Gets the value of the propagateInstantiation property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isPropagateInstantiation() {
    return propagateInstantiation;
  }

  /**
   * Sets the value of the propagateInstantiation property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setPropagateInstantiation(Boolean value) {
    this.propagateInstantiation = value;
  }

  /**
   * Gets the value of the determineTextFuzzySearchEnablement property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isDetermineTextFuzzySearchEnablement() {
    return determineTextFuzzySearchEnablement;
  }

  /**
   * Sets the value of the determineTextFuzzySearchEnablement property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setDetermineTextFuzzySearchEnablement(Boolean value) {
    this.determineTextFuzzySearchEnablement = value;
  }

  /**
   * Gets the value of the xscCompatibilityMode property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isXscCompatibilityMode() {
    return xscCompatibilityMode;
  }

  /**
   * Sets the value of the xscCompatibilityMode property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setXscCompatibilityMode(Boolean value) {
    this.xscCompatibilityMode = value;
  }

}
