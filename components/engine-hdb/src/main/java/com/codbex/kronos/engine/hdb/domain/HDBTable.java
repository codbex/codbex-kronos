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
 * The Class Table.
 */
@Entity
@javax.persistence.Table(name = "DIRIGIBLE_DATA_TABLES")
public class HDBTable extends Artefact {
	
	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "table";
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLE_ID", nullable = false)
	private Long id;
	
	/** The kind. */
	@Column(name = "TABLE_KIND", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	protected String kind;
	
	/** The schema name. */
	@Column(name = "TABLE_SCHEMA", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	protected String schema;
	
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
	
	/** The schema reference. */
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "SCHEMA_ID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Schema schemaReference;

	/**
	 * Instantiates a new table.
	 *
	 * @param location the location
	 * @param name the name
	 * @param description the description
	 * @param dependencies the dependencies
	 * @param kind the kind
	 * @param schema the schema name
	 */
	public HDBTable(String location, String name, String description, String dependencies, String kind, String schema) {
		super(location, name, ARTEFACT_TYPE, description, dependencies);
		this.constraints = new HDBTableConstraints(this);
		this.kind = kind;
		this.schema = schema;
	}
	
	/**
	 * Instantiates a new table.
	 *
	 * @param tableName the table name
	 */
	public HDBTable(String tableName) {
		this(tableName, tableName, null, null, "TABLE", "");
	}
	
	/**
	 * Instantiates a new table.
	 */
	public HDBTable() {
		super();
		this.constraints = new HDBTableConstraints();
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
	 * @param columns the columns to set
	 */
	public void setColumns(List<HDBTableColumn> columns) {
		this.columns = columns;
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
	 * @param indexes the indexes to set
	 */
	public void setIndexes(List<HDBTableIndex> indexes) {
		this.indexes = indexes;
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
	 * @param constraints the constraints to set
	 */
	public void setConstraints(HDBTableConstraints constraints) {
		this.constraints = constraints;
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
		return "Table [id=" + id + ", schemaName=" + schema + ", columns=" + columns + ", indexes=" + indexes
				+ ", constraints=" + constraints + ", location=" + location + ", name=" + name + ", type=" + type
				+ ", description=" + description + ", key=" + key + ", dependencies=" + dependencies + ", createdBy="
				+ createdBy + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt
				+ "]";
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
	 * Adds the index.
	 *
	 * @param name the name
	 * @param type the type
	 * @param unique the unique
	 * @param columns the columns
	 * @return the table index
	 */
	public HDBTableIndex addIndex(String name, String type, boolean unique, String[] columns) {
		HDBTableIndex tableIndex = new HDBTableIndex(name, type, unique, columns, this);
		indexes.add(tableIndex);
		return tableIndex;
	}
	
	
	
}
