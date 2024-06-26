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

package com.codbex.kronos.parser.hdbcalculationview.ndb.searchmodelsearch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Search specific properties of an Element
 *
 *
 * Java class for SearchableElementProperties complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SearchableElementProperties"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="freestyleSearch" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="rankingWeight" type="{http://www.sap.com/ndb/SearchModelSearch.ecore}RankingWeight" minOccurs="0"/&gt;
 *         &lt;element name="fuzzySearch" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="fuzzinessThreshold" type="{http://www.sap.com/ndb/SearchModelSearch.ecore}FuzzinessThreshold" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchableElementProperties", propOrder = {"freestyleSearch", "rankingWeight", "fuzzySearch", "fuzzinessThreshold"})
public class SearchableElementProperties {

    /** The freestyle search. */
    protected Boolean freestyleSearch;

    /** The ranking weight. */
    @XmlElement(defaultValue = "0.5")
    protected Float rankingWeight;

    /** The fuzzy search. */
    protected Boolean fuzzySearch;

    /** The fuzziness threshold. */
    protected Float fuzzinessThreshold;

    /**
     * Gets the value of the freestyleSearch property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isFreestyleSearch() {
        return freestyleSearch;
    }

    /**
     * Sets the value of the freestyleSearch property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setFreestyleSearch(Boolean value) {
        this.freestyleSearch = value;
    }

    /**
     * Gets the value of the rankingWeight property.
     *
     * @return possible object is {@link Float }
     */
    public Float getRankingWeight() {
        return rankingWeight;
    }

    /**
     * Sets the value of the rankingWeight property.
     *
     * @param value allowed object is {@link Float }
     */
    public void setRankingWeight(Float value) {
        this.rankingWeight = value;
    }

    /**
     * Gets the value of the fuzzySearch property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isFuzzySearch() {
        return fuzzySearch;
    }

    /**
     * Sets the value of the fuzzySearch property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setFuzzySearch(Boolean value) {
        this.fuzzySearch = value;
    }

    /**
     * Gets the value of the fuzzinessThreshold property.
     *
     * @return possible object is {@link Float }
     */
    public Float getFuzzinessThreshold() {
        return fuzzinessThreshold;
    }

    /**
     * Sets the value of the fuzzinessThreshold property.
     *
     * @param value allowed object is {@link Float }
     */
    public void setFuzzinessThreshold(Float value) {
        this.fuzzinessThreshold = value;
    }

}
