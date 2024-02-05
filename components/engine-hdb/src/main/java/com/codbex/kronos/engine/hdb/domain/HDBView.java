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
package com.codbex.kronos.engine.hdb.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.eclipse.dirigible.components.base.converters.ListOfStringsToCsvConverter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

/**
 * The Class HDBView.
 */
@Entity
@Table(name = "KRONOS_VIEWS")
public class HDBView extends HDBDataStructure {
	
	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "hdbview";
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HDBVIEW_ID", nullable = false)
	private Long id;
	
	/** The name. */
	@Column(name = "HDBVIEW_QUERY", columnDefinition = "CLOB", nullable = false)
	@Expose
	protected String query;
	
	/** The public prop. */
	@Column(name = "HDBVIEW_IS_PUBLIC", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
    private boolean isPublic;
    
    /** The depends on. */
	@Column(name = "HDBVIEW_DEPENDS_ON", columnDefinition = "VARCHAR", nullable = true, length = 2000)
//	@OrderColumn
//	@ElementCollection(targetClass=String.class)
	@Convert(converter = ListOfStringsToCsvConverter.class)
	@Expose
    private List<String> dependsOn;
    
    /** The depends on table. */
	@Column(name = "HDBVIEW_DEPENDS_ON_TABLE", columnDefinition = "VARCHAR", nullable = true, length = 2000)
//	@OrderColumn
//	@ElementCollection(targetClass=String.class)
	@Convert(converter = ListOfStringsToCsvConverter.class)
	@Expose
    private List<String> dependsOnTable;
    
    /** The depends on view. */
	@Column(name = "HDBVIEW_DEPENDS_ON_VIEW", columnDefinition = "VARCHAR", nullable = true, length = 2000)
//	@OrderColumn
//	@ElementCollection(targetClass=String.class)
	@Convert(converter = ListOfStringsToCsvConverter.class)
	@Expose
    private List<String> dependsOnView;
	
	/** The schema reference. */
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "HDBDD_ID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HDBDD hdbdd;
	
	
	/**
	 * Instantiates a new HDB view.
	 *
	 * @param location       the location
	 * @param name           the name
	 * @param description    the description
	 * @param dependencies   the dependencies
	 * @param schema         the schema
	 * @param content        the content
	 * @param classic        the classic
	 * @param query          the query
	 * @param isPublic       the is public
	 * @param dependsOn      the depends on
	 * @param dependsOnTable the depends on table
	 * @param dependsOnView  the depends on view
	 */
	public HDBView(String location, String name, String description, String dependencies, String schema, String content, boolean classic,
			String query, Boolean isPublic, List<String> dependsOn, List<String> dependsOnTable, List<String> dependsOnView) {
		super(location, name, ARTEFACT_TYPE, null, null, schema, content, classic);
		this.query = query;
		this.isPublic = isPublic;
		this.dependsOn = dependsOn;
		this.dependsOnTable = dependsOnTable;
		this.dependsOnView = dependsOnView;
	}
	
	/**
	 * Instantiates a new HDB view.
	 */
	public HDBView() {
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
	 * @param query the new query
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Checks if is public.
	 *
	 * @return true, if is public
	 */
	public boolean isPublic() {
		return isPublic;
	}

	/**
	 * Sets the public.
	 *
	 * @param isPublic the new public
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * Gets the depends on.
	 *
	 * @return the depends on
	 */
	public List<String> getDependsOn() {
		return dependsOn;
	}

	/**
	 * Sets the depends on.
	 *
	 * @param dependsOn the new depends on
	 */
	public void setDependsOn(List<String> dependsOn) {
		this.dependsOn = dependsOn;
	}

	/**
	 * Gets the depends on table.
	 *
	 * @return the depends on table
	 */
	public List<String> getDependsOnTable() {
		return dependsOnTable;
	}

	/**
	 * Sets the depends on table.
	 *
	 * @param dependsOnTable the new depends on table
	 */
	public void setDependsOnTable(List<String> dependsOnTable) {
		this.dependsOnTable = dependsOnTable;
	}

	/**
	 * Gets the depends on view.
	 *
	 * @return the depends on view
	 */
	public List<String> getDependsOnView() {
		return dependsOnView;
	}

	/**
	 * Sets the depends on view.
	 *
	 * @param dependsOnView the new depends on view
	 */
	public void setDependsOnView(List<String> dependsOnView) {
		this.dependsOnView = dependsOnView;
	}

	/**
	 * Gets the hdbdd.
	 *
	 * @return the hdbdd
	 */
	public HDBDD getHdbdd() {
		return hdbdd;
	}

	/**
	 * Sets the hdbdd.
	 *
	 * @param hdbdd the new hdbdd
	 */
	public void setHdbdd(HDBDD hdbdd) {
		this.hdbdd = hdbdd;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "HDBView [id=" + id + ", query=" + query + ", isPublic=" + isPublic + ", dependsOn=" + dependsOn
				+ ", dependsOnTable=" + dependsOnTable + ", dependsOnView=" + dependsOnView + ", location=" + location
				+ ", name=" + name + ", type=" + type + ", description=" + description + ", key=" + key
				+ ", dependencies=" + dependencies + ", lifecycle=" + lifecycle + ", phase=" + phase + ", error="
				+ error + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy
				+ ", updatedAt=" + updatedAt + "]";
	}
	
}
