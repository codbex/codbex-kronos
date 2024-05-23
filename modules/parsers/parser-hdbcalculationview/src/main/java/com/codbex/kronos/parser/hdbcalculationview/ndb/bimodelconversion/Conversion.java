/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.3.0
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2020.11.26 at 10:54:28 AM EET
//

package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelconversion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.CurrencyConversionTables;
import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.ErrorHandling;
import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.SchemaMappingBasedObject;
import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.UnitConversionTables;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldatafoundation.SimpleDataType;

/**
 * General base for conversions
 *
 *
 * Java class for Conversion complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Conversion"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="client" type="{http://www.sap.com/ndb/BiModelConversion.ecore}Parameterization" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="schema" type="{http://www.sap.com/ndb/BaseModelBase.ecore}SchemaMappingBasedObject" minOccurs="0"/&gt;
 *           &lt;element name="currencyConversionTables" type="{http://www.sap.com/ndb/BaseModelBase.ecore}CurrencyConversionTables" minOccurs="0"/&gt;
 *           &lt;element name="unitConversionTables" type="{http://www.sap.com/ndb/BaseModelBase.ecore}UnitConversionTables" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="outputDataType" type="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}SimpleDataType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="errorHandling" type="{http://www.sap.com/ndb/BaseModelBase.ecore}ErrorHandling" /&gt;
 *       &lt;attribute name="generateOutputUnitCurrencyAttribute" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="outputUnitCurrencyAttributeName" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}Identifier" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Conversion", propOrder = {"client", "schema", "currencyConversionTables", "unitConversionTables", "outputDataType"})
@XmlSeeAlso({CurrencyConversion.class, UnitConversion.class})
public abstract class Conversion {

    /** The client. */
    protected Parameterization client;

    /** The schema. */
    protected SchemaMappingBasedObject schema;

    /** The currency conversion tables. */
    protected CurrencyConversionTables currencyConversionTables;

    /** The unit conversion tables. */
    protected UnitConversionTables unitConversionTables;

    /** The output data type. */
    @XmlElement(required = true)
    protected SimpleDataType outputDataType;

    /** The error handling. */
    @XmlAttribute(name = "errorHandling")
    protected ErrorHandling errorHandling;

    /** The generate output unit currency attribute. */
    @XmlAttribute(name = "generateOutputUnitCurrencyAttribute")
    protected Boolean generateOutputUnitCurrencyAttribute;

    /** The output unit currency attribute name. */
    @XmlAttribute(name = "outputUnitCurrencyAttributeName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String outputUnitCurrencyAttributeName;

    /**
     * Gets the value of the client property.
     *
     * @return possible object is {@link Parameterization }
     */
    public Parameterization getClient() {
        return client;
    }

    /**
     * Sets the value of the client property.
     *
     * @param value allowed object is {@link Parameterization }
     */
    public void setClient(Parameterization value) {
        this.client = value;
    }

    /**
     * Gets the value of the schema property.
     *
     * @return possible object is {@link SchemaMappingBasedObject }
     */
    public SchemaMappingBasedObject getSchema() {
        return schema;
    }

    /**
     * Sets the value of the schema property.
     *
     * @param value allowed object is {@link SchemaMappingBasedObject }
     */
    public void setSchema(SchemaMappingBasedObject value) {
        this.schema = value;
    }

    /**
     * Gets the value of the currencyConversionTables property.
     *
     * @return possible object is {@link CurrencyConversionTables }
     */
    public CurrencyConversionTables getCurrencyConversionTables() {
        return currencyConversionTables;
    }

    /**
     * Sets the value of the currencyConversionTables property.
     *
     * @param value allowed object is {@link CurrencyConversionTables }
     */
    public void setCurrencyConversionTables(CurrencyConversionTables value) {
        this.currencyConversionTables = value;
    }

    /**
     * Gets the value of the unitConversionTables property.
     *
     * @return possible object is {@link UnitConversionTables }
     */
    public UnitConversionTables getUnitConversionTables() {
        return unitConversionTables;
    }

    /**
     * Sets the value of the unitConversionTables property.
     *
     * @param value allowed object is {@link UnitConversionTables }
     */
    public void setUnitConversionTables(UnitConversionTables value) {
        this.unitConversionTables = value;
    }

    /**
     * Gets the value of the outputDataType property.
     *
     * @return possible object is {@link SimpleDataType }
     */
    public SimpleDataType getOutputDataType() {
        return outputDataType;
    }

    /**
     * Sets the value of the outputDataType property.
     *
     * @param value allowed object is {@link SimpleDataType }
     */
    public void setOutputDataType(SimpleDataType value) {
        this.outputDataType = value;
    }

    /**
     * Gets the value of the errorHandling property.
     *
     * @return possible object is {@link ErrorHandling }
     */
    public ErrorHandling getErrorHandling() {
        return errorHandling;
    }

    /**
     * Sets the value of the errorHandling property.
     *
     * @param value allowed object is {@link ErrorHandling }
     */
    public void setErrorHandling(ErrorHandling value) {
        this.errorHandling = value;
    }

    /**
     * Gets the value of the generateOutputUnitCurrencyAttribute property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isGenerateOutputUnitCurrencyAttribute() {
        return generateOutputUnitCurrencyAttribute;
    }

    /**
     * Sets the value of the generateOutputUnitCurrencyAttribute property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setGenerateOutputUnitCurrencyAttribute(Boolean value) {
        this.generateOutputUnitCurrencyAttribute = value;
    }

    /**
     * Gets the value of the outputUnitCurrencyAttributeName property.
     *
     * @return possible object is {@link String }
     */
    public String getOutputUnitCurrencyAttributeName() {
        return outputUnitCurrencyAttributeName;
    }

    /**
     * Sets the value of the outputUnitCurrencyAttributeName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setOutputUnitCurrencyAttributeName(String value) {
        this.outputUnitCurrencyAttributeName = value;
    }

}
