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
package com.codbex.kronos.engine.xsodata.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eclipse.dirigible.components.base.artefact.Artefact;
import org.eclipse.dirigible.components.odata.api.ODataAssociation;
import org.eclipse.dirigible.components.odata.api.ODataEntity;

import com.codbex.kronos.parser.xsodata.model.XSODataService;
import com.google.gson.annotations.Expose;

/**
 * The Class XSOData.
 */
@Entity
@Table(name = "KRONOS_XSODATA")
public class XSOData extends Artefact {
	
	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "xsodata";
	
	/** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "XSODATA_ID", nullable = false)
    private Long id;
	
	/** The namespace. */
	@Column(name = "XSODATA_NAMESPACE", columnDefinition = "VARCHAR", nullable = false, length = 255)
	@Expose
	private String namespace;
	
	/** The entities. */
	@Transient
	@Expose
	private List<ODataEntity> entities = new ArrayList<ODataEntity>();
	
	/** The associations. */
	@Transient
	@Expose
	private List<ODataAssociation> associations = new ArrayList<ODataAssociation>();
	
	/** The service. */
	  @Transient
	  private XSODataService service;

	/**
	 * Instantiates a new XSOData.
	 *
	 * @param location the location
	 * @param name the name
	 * @param description the description
	 * @param dependencies the dependencies
	 * @param namespace the namespace
	 * @param entities the entities
	 * @param associations the associations
	 */
	public XSOData(String location, String name, String description, String dependencies,
			String namespace, List<ODataEntity> entities, List<ODataAssociation> associations) {
		super(location, name, ARTEFACT_TYPE, description, dependencies);
		this.namespace = namespace;
		this.entities = entities;
		this.associations = associations;
	}
	
	/**
	 * Instantiates a new XSOData.
	 */
	public XSOData() {
		super();
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the namesapce.
	 *
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * Sets the namespace.
	 *
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * Gets the entities.
	 *
	 * @return the entities
	 */
	public List<ODataEntity> getEntities() {
		return entities;
	}
	
	/**
	 * Gets the associations.
	 *
	 * @return the associations
	 */
	public List<ODataAssociation> getAssociations() {
		return associations;
	}
	
	/**
	   * Gets the service.
	   *
	   * @return the service
	   */
	  public XSODataService getService() {
	    return service;
	  }

	  /**
	   * Sets the service.
	   *
	   * @param service the new service
	   */
	  public void setService(XSODataService service) {
	    this.service = service;
	  }

}
