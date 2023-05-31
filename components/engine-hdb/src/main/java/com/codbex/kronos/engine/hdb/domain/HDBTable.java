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
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

/**
 * The Class Table.
 */
@Entity
@Table(name = "KRONOS_TABLES")
public class HDBTable extends HDBDataStructure {
	
	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "hdbtable";
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HDBTABLE_ID", nullable = false)
	private Long id;
	
	/** The kind. */
	@Column(name = "HDBTABLE_TYPE", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	protected String tableType;
	
	/** The columns. */
	@OneToMany(mappedBy = "table", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Expose
	private List<HDBTableColumn> columns = new ArrayList<HDBTableColumn>();
	
	/** The indexes. */
	@OneToMany(mappedBy = "table", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Nullable
	@Expose
	private List<HDBTableIndex> indexes = new ArrayList<HDBTableIndex>();

	/** The constraints. */
	@OneToOne(mappedBy = "table", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	@Nullable
	@Expose
	private HDBTableConstraints constraints;
	
	/** The public prop. */
	@Column(name = "HDBTABLE_IS_PUBLIC", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private Boolean isPublic;

	  /** The logging type. */
	@Column(name = "HDBTABLE_LOGGING_TYPE", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String loggingType;

	  /** The temporary. */
	@Column(name = "HDBTABLE_IS_TEMPORARY", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private Boolean isTemporary;
	
	/** The schema reference. */
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "HDBDD_ID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HDBDD hdbdd;

	
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
	public HDBTable(String location, String name, String description, String dependencies, String schema, String content, boolean classic,
			String tableType, Boolean isPublic, String loggingType, Boolean isTemporary) {
		super(location, name, ARTEFACT_TYPE, null, null, schema, content, classic);
		this.constraints = new HDBTableConstraints(this);
		this.tableType = tableType;
		this.isPublic = isPublic;
		this.loggingType = loggingType;
		this.isTemporary = isTemporary;
	}
	
	/**
	 * Instantiates a new model.
	 */
	public HDBTable() {
		super();
		this.constraints = new HDBTableConstraints(this);
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
	 * Gets the table type.
	 *
	 * @return the table type
	 */
	public String getTableType() {
		return tableType;
	}

	/**
	 * Sets the table type.
	 *
	 * @param tableType the new table type
	 */
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public List<HDBTableColumn> getColumns() {
		return columns;
	}

	/**
	 * Sets the columns.
	 *
	 * @param columns the new columns
	 */
	public void setColumns(List<HDBTableColumn> columns) {
		this.columns = columns;
	}

	/**
	 * Gets the indexes.
	 *
	 * @return the indexes
	 */
	public List<HDBTableIndex> getIndexes() {
		return indexes;
	}

	/**
	 * Sets the indexes.
	 *
	 * @param indexes the new indexes
	 */
	public void setIndexes(List<HDBTableIndex> indexes) {
		this.indexes = indexes;
	}

	/**
	 * Gets the constraints.
	 *
	 * @return the constraints
	 */
	public HDBTableConstraints getConstraints() {
		return constraints;
	}

	/**
	 * Sets the constraints.
	 *
	 * @param constraints the new constraints
	 */
	public void setConstraints(HDBTableConstraints constraints) {
		this.constraints = constraints;
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
	 * Gets the logging type.
	 *
	 * @return the logging type
	 */
	public String getLoggingType() {
		return loggingType;
	}

	/**
	 * Sets the logging type.
	 *
	 * @param loggingType the new logging type
	 */
	public void setLoggingType(String loggingType) {
		this.loggingType = loggingType;
	}

	/**
	 * Gets the is temporary.
	 *
	 * @return the is temporary
	 */
	public Boolean getIsTemporary() {
		return isTemporary;
	}

	/**
	 * Sets the is temporary.
	 *
	 * @param temporary the new is temporary
	 */
	public void setIsTemporary(Boolean isTemporary) {
		this.isTemporary = isTemporary;
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
	 * Adds the column.
	 *
	 * @param name the name
	 * @param type the type
	 * @param length the length
	 * @param nullable the nullable
	 * @param primaryKey the primary key
	 * @param defaultValue the default value
	 * @param scale the scale
	 * @param unique the unique
	 * @return the table column
	 */
	public HDBTableColumn addColumn(String name, String type, String length, boolean nullable, boolean primaryKey,
			String defaultValue, String scale, boolean unique) {
		HDBTableColumn tableColumn = new HDBTableColumn(name, type, length, nullable, primaryKey, defaultValue, scale, unique, this);
		columns.add(tableColumn);
		return tableColumn;
	}
	
	/**
	 * Get the column by name.
	 *
	 * @param name the name
	 * @return the column
	 */
	public HDBTableColumn getColumn(String name) {
		for (HDBTableColumn c : columns) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Adds the index.
	 *
	 * @param name the name
	 * @param type the type
	 * @param unique the unique
	 * @param columns the columns
	 * @return the table index
	 */
	public HDBTableIndex addIndex(String name, String type, boolean unique, String order, String[] columns) {
		HDBTableIndex tableIndex = new HDBTableIndex(name, type, unique, order, columns, this);
		indexes.add(tableIndex);
		return tableIndex;
	}
	
	/**
	 * Get the index by name.
	 *
	 * @param name the name
	 * @return the index
	 */
	public HDBTableIndex getIndex(String name) {
		final List<HDBTableIndex> indexesList = indexes;
		if (indexesList != null) {
			for (HDBTableIndex i : indexesList) {
				if (i.getName().equals(name)) {
					return i;
				}
			}
		}
		return null;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "HDBTable [id=" + id + ", tableType=" + tableType + ", columns=" + columns + ", indexes=" + indexes
				+ ", constraints=" + constraints + ", isPublic=" + isPublic + ", loggingType=" + loggingType
				+ ", temporary=" + isTemporary + ", hdbdd=" + hdbdd + ", location=" + location + ", name=" + name
				+ ", type=" + type + ", description=" + description + ", key=" + key + ", dependencies=" + dependencies
				+ ", lifecycle=" + lifecycle + ", phase=" + phase + ", error=" + error + ", createdBy=" + createdBy
				+ ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt + "]";
	}
	
}
