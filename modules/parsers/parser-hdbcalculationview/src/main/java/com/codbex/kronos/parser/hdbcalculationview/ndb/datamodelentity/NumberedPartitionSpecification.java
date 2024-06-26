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

package com.codbex.kronos.parser.hdbcalculationview.ndb.datamodelentity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * Base class for partitioning specification that specify the numbers of the partitions
 *
 *
 * Java class for NumberedPartitionSpecification complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="NumberedPartitionSpecification"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/DataModelEntity.ecore}PartitionSpecification"&gt;
 *       &lt;attribute name="numberOfPartitions" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="useNumberOfServers" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumberedPartitionSpecification")
@XmlSeeAlso({RoundRobinPartitionSpecification.class, HashPartitionSpecification.class})
public abstract class NumberedPartitionSpecification extends PartitionSpecification {

    /** The number of partitions. */
    @XmlAttribute(name = "numberOfPartitions")
    protected Integer numberOfPartitions;

    /** The use number of servers. */
    @XmlAttribute(name = "useNumberOfServers")
    protected Boolean useNumberOfServers;

    /**
     * Gets the value of the numberOfPartitions property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getNumberOfPartitions() {
        return numberOfPartitions;
    }

    /**
     * Sets the value of the numberOfPartitions property.
     *
     * @param value allowed object is {@link Integer }
     */
    public void setNumberOfPartitions(Integer value) {
        this.numberOfPartitions = value;
    }

    /**
     * Gets the value of the useNumberOfServers property.
     *
     * @return possible object is {@link Boolean }
     */
    public Boolean isUseNumberOfServers() {
        return useNumberOfServers;
    }

    /**
     * Sets the value of the useNumberOfServers property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setUseNumberOfServers(Boolean value) {
        this.useNumberOfServers = value;
    }

}
