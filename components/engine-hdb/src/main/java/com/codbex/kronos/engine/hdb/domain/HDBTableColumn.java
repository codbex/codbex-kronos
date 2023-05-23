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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

/**
 * The Class TableColumn.
 */
@Entity
@javax.persistence.Table(name = "DIRIGIBLE_DATA_TABLE_COLUMNS")
public class HDBTableColumn {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COLUMN_ID", nullable = false)
	@Expose
	private Long id;
	
	/** The name. */
	@Column(name = "COLUMN_NAME", columnDefinition = "VARCHAR", nullable = false, length = 255)
	@Expose
	private String name;
	
	/** The type. */
	@Column(name = "COLUMN_TYPE", columnDefinition = "VARCHAR", nullable = false, length = 255)
	@Expose
	private String type;
	
	/** The length. */
	@Column(name = "COLUMN_LENGTH", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String length;
	
	/** The nullable. */
	@Column(name = "COLUMN_NULLABLE", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private boolean nullable;
	
	/** The primary key. */
	@Column(name = "COLUMN_PRIMARY_KEY", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private boolean primaryKey;
	
	/** The default value. */
	@Column(name = "COLUMN_DEFAULT_VALUE", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String defaultValue;
	
	/** The scale. */
	@Column(name = "COLUMN_SCALE", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String scale;
	
	/** The unique. */
	@Column(name = "COLUMN_UNIQUE", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private boolean unique;
	
	/** The table. */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TABLE_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HDBTable table;

	/**
	 * Instantiates a new table column.
	 *
	 * @param name the name
	 * @param type the type
	 * @param length the length
	 * @param nullable the nullable
	 * @param primaryKey the primary key
	 * @param defaultValue the default value
	 * @param scale the scale
	 * @param unique the unique
	 * @param table the table
	 */
	public HDBTableColumn(String name, String type, String length, boolean nullable, boolean primaryKey,
			String defaultValue, String scale, boolean unique, HDBTable table) {
		super();
		this.name = name;
		this.type = type;
		this.length = length;
		this.nullable = nullable;
		this.primaryKey = primaryKey;
		this.defaultValue = defaultValue;
		this.scale = scale;
		this.unique = unique;
		this.table = table;
		this.table.getColumns().add(this);
	}
	
	/**
	 * Instantiates a new table column.
	 *
	 * @param name the name
	 * @param type the type
	 * @param length the length
	 * @param table the table
	 */
	public HDBTableColumn(String name, String type, String length, HDBTable table) {
		this(name, type, length, true, false, null, "0", false, table);
	}
	
	/**
	 * Instantiates a new table column.
	 *
	 * @param name the name
	 * @param type the type
	 * @param length the length
	 * @param nullable the nullable
	 * @param primaryKey the primary key
	 * @param table the table
	 */
	public HDBTableColumn(String name, String type, String length, boolean nullable, boolean primaryKey, HDBTable table) {
		this(name, type, length, nullable, primaryKey, null, "0", false, table);
	}
	
	/**
	 * Instantiates a new table column.
	 */
	public HDBTableColumn() {
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/**
	 * Checks if is nullable.
	 *
	 * @return the nullable
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * Sets the nullable.
	 *
	 * @param nullable the nullable to set
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * Checks if is primary key.
	 *
	 * @return the primaryKey
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * Sets the primary key.
	 *
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * Gets the default value.
	 *
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the default value.
	 *
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the scale.
	 *
	 * @return the scale
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * Sets the scale.
	 *
	 * @param scale the scale to set
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**
	 * Checks if is unique.
	 *
	 * @return the unique
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * Sets the unique.
	 *
	 * @param unique the unique to set
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public HDBTable getTable() {
		return table;
	}

	/**
	 * Sets the table.
	 *
	 * @param table the table to set
	 */
	public void setTable(HDBTable table) {
		this.table = table;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "TableColumn [id=" + id + ", name=" + name + ", type=" + type + ", length=" + length + ", nullable="
				+ nullable + ", primaryKey=" + primaryKey + ", defaultValue=" + defaultValue + ", scale=" + scale
				+ ", unique=" + unique  + ", table=" + table.getName() + "]";
	}
	
	

}
