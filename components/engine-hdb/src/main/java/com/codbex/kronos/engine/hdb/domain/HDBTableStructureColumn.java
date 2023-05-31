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
@javax.persistence.Table(name = "KRONOS_TABLESTRUCTURE_COLUMNS")
public class HDBTableStructureColumn extends HDBColumn {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COLUMN_ID", nullable = false)
	@Expose
	private Long id;

	/** The tablestructure. */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "TABLESTRUCTURE_ID", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private HDBTableStructure tableStructure;

	/**
	 * Instantiates a new table structure column.
	 *
	 * @param name         the name
	 * @param type         the type
	 * @param length       the length
	 * @param nullable     the nullable
	 * @param primaryKey   the primary key
	 * @param defaultValue the default value
	 * @param scale        the scale
	 * @param unique       the unique
	 * @param tableType        the tableType
	 */
	public HDBTableStructureColumn(String name, String type, String length, boolean nullable, boolean primaryKey,
			String defaultValue, String scale, boolean unique, HDBTableStructure tableStructure) {
		super(name, type, length, nullable, primaryKey, defaultValue, scale, unique);
		this.tableStructure = tableStructure;
		this.tableStructure.getColumns().add(this);
	}

	/**
	 * Instantiates a new table structure column.
	 *
	 * @param name   the name
	 * @param type   the type
	 * @param length the length
	 * @param tableStructure  the table structure
	 */
	public HDBTableStructureColumn(String name, String type, String length, HDBTableStructure tableStrucutre) {
		this(name, type, length, true, false, null, "0", false, tableStrucutre);
	}

	/**
	 * Instantiates a new table type column.
	 *
	 * @param name       the name
	 * @param type       the type
	 * @param length     the length
	 * @param nullable   the nullable
	 * @param primaryKey the primary key
	 * @param tableStructure      the table structure
	 */
	public HDBTableStructureColumn(String name, String type, String length, boolean nullable, boolean primaryKey,
			HDBTableStructure tableStrucutre) {
		this(name, type, length, nullable, primaryKey, null, "0", false, tableStrucutre);
	}

	/**
	 * Instantiates a new table type column.
	 */
	public HDBTableStructureColumn() {
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
	 * Gets the table structure.
	 *
	 * @return the table structure
	 */
	public HDBTableStructure getTableStructure() {
		return tableStructure;
	}

	/**
	 * Sets the table structure.
	 *
	 * @param tableStructure the table structure to set
	 */
	public void setTableStructure(HDBTableStructure tableStrucutre) {
		this.tableStructure = tableStrucutre;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "HDBTableColumn [id=" + id + ", getName()=" + getName() + ", getType()=" + getType() + ", getLength()="
				+ getLength() + ", isNullable()=" + isNullable() + ", isPrimaryKey()=" + isPrimaryKey()
				+ ", getDefaultValue()=" + getDefaultValue() + ", getPrecision()=" + getPrecision() + ", getScale()="
				+ getScale() + ", isUnique()=" + isUnique() + ", isDefaultValueDateTimeFunction()="
				+ isDefaultValueDateTimeFunction() + ", getComment()=" + getComment() + ", getAlias()=" + getAlias()
				+ ", isFuzzySearchIndex()=" + isFuzzySearchIndex() + ", getStatement()=" + getStatement()
				+ ", isCalculatedColumn()=" + isCalculatedColumn() + "]";
	}

}
