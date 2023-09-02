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

import java.util.ArrayList;
import java.util.List;

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
@javax.persistence.Table(name = "KRONOS_TABLESTRUCTURES")
public class HDBTableStructure extends HDBDataStructure {
	
	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "hdbstructure";
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLESTRUCTURE_ID", nullable = false)
	private Long id;
	
	/** The columns. */
	@OneToMany(mappedBy = "tableStructure", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Expose
	private List<HDBTableStructureColumn> columns = new ArrayList<HDBTableStructureColumn>();
	
	/** The primary key. */
	@OneToOne(mappedBy = "tableStructure", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
	@Nullable
	@Expose
	private HDBTableStructurePrimaryKey primaryKey;
	
	/** The schema reference. */
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "HDBDD_ID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HDBDD hdbdd;
	
	/** The public prop. */
	@Column(name = "HDBTABLESTRUCTURE_IS_PUBLIC", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private Boolean isPublic;
	
	
	/**
	 * Instantiates a new model.
	 *
	 * @param location     the location
	 * @param name         the name
	 * @param description  the description
	 * @param dependencies the dependencies
	 * @param schema       the schema
	 * @param content      the content
	 * @param classic      the classic
	 * @param tableType    the table type
	 * @param isPublic     the is public
	 * @param loggingType  the logging type
	 * @param temporary    the temporary
	 */
	public HDBTableStructure(String location, String name, String description, String dependencies, String schema, String content, boolean classic, String tableType, Boolean isPublic, String loggingType, Boolean isTemporary) {
		super(location, name, ARTEFACT_TYPE, null, null, schema, content, classic);
		this.isPublic = isPublic;
	}
	
	/**
	 * Instantiates a new model.
	 */
	public HDBTableStructure() {
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
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public List<HDBTableStructureColumn> getColumns() {
		return columns;
	}

	/**
	 * Sets the columns.
	 *
	 * @param columns the new columns
	 */
	public void setColumns(List<HDBTableStructureColumn> columns) {
		this.columns = columns;
	}
	
	/**
	 * Get the column by name.
	 *
	 * @param name the name
	 * @return the column
	 */
	public HDBTableStructureColumn getColumn(String name) {
		for (HDBTableStructureColumn c : columns) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Gets the primary key.
	 *
	 * @return the primary key
	 */
	public HDBTableStructurePrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * Sets the primary key.
	 *
	 * @param primaryKey the new primary key
	 */
	public void setPrimaryKey(HDBTableStructurePrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
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
	 * Gets the checks if is public.
	 *
	 * @return the checks if is public
	 */
	public Boolean getIsPublic() {
		return isPublic;
	}

	/**
	 * Sets the checks if is public.
	 *
	 * @param isPublic the new checks if is public
	 */
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "HDBTableStructure [id=" + id + ", columns=" + columns + ", primaryKey=" + primaryKey + ", location="
				+ location + ", name=" + name + ", type=" + type + ", description=" + description + ", key=" + key
				+ ", dependencies=" + dependencies + ", lifecycle=" + lifecycle + ", phase=" + phase + ", error="
				+ error + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy
				+ ", updatedAt=" + updatedAt + "]";
	}
	
}
