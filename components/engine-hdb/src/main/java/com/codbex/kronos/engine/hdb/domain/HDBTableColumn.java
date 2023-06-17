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
@javax.persistence.Table(name = "KRONOS_TABLE_COLUMNS")
public class HDBTableColumn extends HDBColumn {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COLUMN_ID", nullable = false)
	@Expose
	private Long id;

	/** The table. */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "TABLE_ID", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private HDBTable table;

	/**
	 * Instantiates a new table column.
	 *
	 * @param name         the name
	 * @param type         the type
	 * @param length       the length
	 * @param nullable     the nullable
	 * @param primaryKey   the primary key
	 * @param defaultValue the default value
	 * @param scale        the scale
	 * @param unique       the unique
	 * @param table        the table
	 */
	public HDBTableColumn(String name, String type, String length, boolean nullable, boolean primaryKey,
			String defaultValue, String scale, boolean unique, HDBTable table) {
		super(name, type, length, nullable, primaryKey, defaultValue, scale, unique);
		this.table = table;
		this.table.getColumns().add(this);
	}

	/**
	 * Instantiates a new table column.
	 *
	 * @param name   the name
	 * @param type   the type
	 * @param length the length
	 * @param table  the table
	 */
	public HDBTableColumn(String name, String type, String length, HDBTable table) {
		this(name, type, length, true, false, null, "0", false, table);
	}

	/**
	 * Instantiates a new table column.
	 *
	 * @param name       the name
	 * @param type       the type
	 * @param length     the length
	 * @param nullable   the nullable
	 * @param primaryKey the primary key
	 * @param table      the table
	 */
	public HDBTableColumn(String name, String type, String length, boolean nullable, boolean primaryKey,
			HDBTable table) {
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
		return "HDBTableColumn [id=" + id + ", getName()=" + getName() + ", getType()=" + getType() + ", getLength()="
				+ getLength() + ", isNullable()=" + isNullable() + ", isPrimaryKey()=" + isPrimaryKey()
				+ ", getDefaultValue()=" + getDefaultValue() + ", getPrecision()=" + getPrecision() + ", getScale()="
				+ getScale() + ", isUnique()=" + isUnique() + ", isDefaultValueDateTimeFunction()="
				+ isDefaultValueDateTimeFunction() + ", getComment()=" + getComment() + ", getAlias()=" + getAlias()
				+ ", isFuzzySearchIndex()=" + isFuzzySearchIndex() + ", getStatement()=" + getStatement()
				+ ", isCalculatedColumn()=" + isCalculatedColumn() + "]";
	}

}
