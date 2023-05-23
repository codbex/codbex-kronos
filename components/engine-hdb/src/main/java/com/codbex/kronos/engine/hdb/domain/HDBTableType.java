/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.eclipse.dirigible.components.base.artefact.Artefact;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

/**
 * The Class View.
 */
@Entity
@javax.persistence.Table(name = "KRONOS_DATA_TABLETYPES")
public class HDBTableType extends Artefact {
	
	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "hdbtabletype";
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLETYPE_ID", nullable = false)
	private Long id;
	
	/** The kind. */
	@Column(name = "TABLETYPE_KIND", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	protected String kind;
	
	/** The schema name. */
	@Column(name = "TABLETYPE_SCHEMA", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	protected String schema;
	
	/** The schema reference. */
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "HDBDD_ID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HDBDD hdbdd;
	
	
	/**
	 * Instantiates a new view.
	 *
	 * @param location the location
	 * @param name the name
	 * @param description the description
	 * @param dependencies the dependencies
	 * @param kind the kind
	 * @param schema the schema name
	 * @param query the query
	 */
	public HDBTableType(String location, String name, String description, String dependencies, String kind, String schema, String query) {
		super(location, name, ARTEFACT_TYPE, description, dependencies);
		this.kind = kind;
		this.schema = schema;
		this.query = query;
	}
	
	/**
	 * Instantiates a new view.
	 *
	 * @param viewName the view name
	 */
	public HDBTableType(String viewName) {
		this(viewName, viewName, null, null, "VIEW", "", "");
	}
	
	/**
	 * Instantiates a new table.
	 */
	public HDBTableType() {
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Sets the kind.
	 *
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * Gets the schema name.
	 *
	 * @return the schema name
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * Sets the schema name.
	 *
	 * @param schema the schema name to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the query.
	 *
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
	/**
	 * Gets the schema reference.
	 *
	 * @return the schema reference
	 */
	public Schema getSchemaReference() {
		return schemaReference;
	}
	
	/**
	 * Sets the schema reference.
	 *
	 * @param schemaReference the new schema reference
	 */
	public void setSchemaReference(Schema schemaReference) {
		this.schemaReference = schemaReference;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "View [id=" + id + ", schemaName=" + schema
				+ ", query=" + query + ", location=" + location + ", name=" + name + ", type=" + type + ", description="
				+ description + ", key=" + key + ", dependencies=" + dependencies + ", createdBy=" + createdBy
				+ ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt + "]";
	}
	
}
