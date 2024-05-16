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

package com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelprivilege;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.codbex.kronos.parser.hdbcalculationview.ndb.basemodelbase.PrivilegeType;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodeldatafoundation.BIResource;
import com.codbex.kronos.parser.hdbcalculationview.ndb.repositorymodelresource.RepositoryObject;
import com.codbex.kronos.parser.hdbcalculationview.ndb.sqlcoremodelaccesscontrol.ValueFilter;

/**
 * Analytic privileges define multi-dimensional authorizations on information models If an
 * analytical privilege applies to more than one Information model the restrictions for dimensions
 * and attributes which are not contained in a cube are ignored at runtime. An analytic privilege
 * having zero restrictions in interpreted as "everything is allowed" Constraints 3. For privileges
 * no excluding sign is allowed as of now - i.e. Prililege.Restriction.Filter.including = true for
 * all restrictions and all filters
 *
 *
 * Java class for AnalyticPrivilege complex type.
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AnalyticPrivilege"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.sap.com/ndb/BiModelDataFoundation.ecore}BIResource"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="securedModels" type="{http://www.sap.com/ndb/BiModelPrivilege.ecore}SecuredModels" minOccurs="0"/&gt;
 *         &lt;element name="validity" type="{http://www.sap.com/ndb/SQLCoreModelAccessControl.ecore}ValueFilter" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="restriction" type="{http://www.sap.com/ndb/BiModelPrivilege.ecore}Restriction" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="conditionProcedureName" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}FQName"/&gt;
 *           &lt;element name="whereSql" type="{http://www.sap.com/ndb/BaseModelBase.ecore}SqlFragment"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="requiredEntity" type="{http://www.sap.com/ndb/RepositoryModelResource.ecore}RepositoryObject" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="privilegeType" type="{http://www.sap.com/ndb/BaseModelBase.ecore}PrivilegeType" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnalyticPrivilege",
        propOrder = {"securedModels", "validity", "restriction", "conditionProcedureName", "whereSql", "requiredEntity"})
public class AnalyticPrivilege extends BIResource {

    /** The secured models. */
    protected SecuredModels securedModels;

    /** The validity. */
    protected List<ValueFilter> validity;

    /** The restriction. */
    protected List<Restriction> restriction;

    /** The condition procedure name. */
    protected String conditionProcedureName;

    /** The where sql. */
    protected String whereSql;

    /** The required entity. */
    protected List<RepositoryObject> requiredEntity;

    /** The privilege type. */
    @XmlAttribute(name = "privilegeType")
    protected PrivilegeType privilegeType;

    /**
     * Gets the value of the securedModels property.
     *
     * @return possible object is {@link SecuredModels }
     */
    public SecuredModels getSecuredModels() {
        return securedModels;
    }

    /**
     * Sets the value of the securedModels property.
     *
     * @param value allowed object is {@link SecuredModels }
     */
    public void setSecuredModels(SecuredModels value) {
        this.securedModels = value;
    }

    /**
     * Gets the value of the validity property.
     *
     *
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is why
     * there is not a <CODE>set</CODE> method for the validity property.
     *
     *
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getValidity().add(newItem);
     * </pre>
     *
     *
     *
     * Objects of the following type(s) are allowed in the list {@link ValueFilter }
     *
     * @return the validity
     */
    public List<ValueFilter> getValidity() {
        if (validity == null) {
            validity = new ArrayList<ValueFilter>();
        }
        return this.validity;
    }

    /**
     * Gets the value of the restriction property.
     *
     *
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is why
     * there is not a <CODE>set</CODE> method for the restriction property.
     *
     *
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getRestriction().add(newItem);
     * </pre>
     *
     *
     *
     * Objects of the following type(s) are allowed in the list {@link Restriction }
     *
     * @return the restriction
     */
    public List<Restriction> getRestriction() {
        if (restriction == null) {
            restriction = new ArrayList<Restriction>();
        }
        return this.restriction;
    }

    /**
     * Gets the value of the conditionProcedureName property.
     *
     * @return possible object is {@link String }
     */
    public String getConditionProcedureName() {
        return conditionProcedureName;
    }

    /**
     * Sets the value of the conditionProcedureName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setConditionProcedureName(String value) {
        this.conditionProcedureName = value;
    }

    /**
     * Gets the value of the whereSql property.
     *
     * @return possible object is {@link String }
     */
    public String getWhereSql() {
        return whereSql;
    }

    /**
     * Sets the value of the whereSql property.
     *
     * @param value allowed object is {@link String }
     */
    public void setWhereSql(String value) {
        this.whereSql = value;
    }

    /**
     * Gets the value of the requiredEntity property.
     *
     *
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is why
     * there is not a <CODE>set</CODE> method for the requiredEntity property.
     *
     *
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getRequiredEntity().add(newItem);
     * </pre>
     *
     *
     *
     * Objects of the following type(s) are allowed in the list {@link RepositoryObject }
     *
     * @return the required entity
     */
    public List<RepositoryObject> getRequiredEntity() {
        if (requiredEntity == null) {
            requiredEntity = new ArrayList<RepositoryObject>();
        }
        return this.requiredEntity;
    }

    /**
     * Gets the value of the privilegeType property.
     *
     * @return possible object is {@link PrivilegeType }
     */
    public PrivilegeType getPrivilegeType() {
        return privilegeType;
    }

    /**
     * Sets the value of the privilegeType property.
     *
     * @param value allowed object is {@link PrivilegeType }
     */
    public void setPrivilegeType(PrivilegeType value) {
        this.privilegeType = value;
    }

}
