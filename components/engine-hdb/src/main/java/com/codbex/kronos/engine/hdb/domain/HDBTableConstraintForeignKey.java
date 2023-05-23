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

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderColumn;

import com.google.gson.annotations.Expose;

/**
 * The Class TableConstraintForeignKey.
 */
@Entity
@javax.persistence.Table(name = "DIRIGIBLE_DATA_TABLE_FOREIGNKEYS")
public class HDBTableConstraintForeignKey extends HDBTableConstraint {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FOREIGNKEY_ID", nullable = false)
	private Long id;
	
	/** The referenced table. */
	@Column(name = "FOREIGNKEY_REF_TABLE", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Nullable
	@Expose
	private String referencedTable;
	
	/** The referenced columns. */
	@Column(name = "FOREIGNKEY_REF_COLUMNS", columnDefinition = "VARCHAR", nullable = true, length = 2000)
	@Nullable
	@ElementCollection
	@OrderColumn
	@Expose
	private String[] referencedColumns;
	
	/**
	 * Instantiates a new table constraint foreign key.
	 *
	 * @param name the name
	 * @param modifiers the modifiers
	 * @param columns the columns
	 * @param referencedTable the referenced table
	 * @param referencedColumns the referenced columns
	 * @param constraints the constraints
	 */
	public HDBTableConstraintForeignKey(String name, String[] modifiers, String[] columns,
			String referencedTable, String[] referencedColumns, HDBTableConstraints constraints) {
		super(name, modifiers, columns, constraints);
		this.referencedTable = referencedTable;
		this.referencedColumns = referencedColumns;
		this.constraints.getForeignKeys().add(this);
	}
	
	/**
	 * Instantiates a new table constraint foreign key.
	 *
	 * @param referencedTable the referenced table
	 * @param columnName the column name
	 * @param referencedColumnName the referenced column name
	 * @param constraints the constraints
	 */
	public HDBTableConstraintForeignKey(String referencedTable, String columnName, String referencedColumnName, HDBTableConstraints constraints) {
		this(constraints.getTable().getName() + "_" + referencedTable, null,
				new String[] {columnName}, referencedTable, new String[] {referencedColumnName}, constraints);
	}
	
	

	/**
	 * Instantiates a new table constraint foreign key.
	 */
	public HDBTableConstraintForeignKey() {
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
	 * Gets the referenced table.
	 *
	 * @return the referencedTable
	 */
	public String getReferencedTable() {
		return referencedTable;
	}

	/**
	 * Sets the referenced table.
	 *
	 * @param referencedTable the referencedTable to set
	 */
	public void setReferencedTable(String referencedTable) {
		this.referencedTable = referencedTable;
	}

	/**
	 * Gets the referenced columns.
	 *
	 * @return the referencedColumns
	 */
	public String[] getReferencedColumns() {
		return referencedColumns;
	}

	/**
	 * Sets the referenced columns.
	 *
	 * @param referencedColumns the referencedColumns to set
	 */
	public void setReferencedColumns(String[] referencedColumns) {
		this.referencedColumns = referencedColumns;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "TableConstraintForeignKey [id=" + id + ", referencedTable=" + referencedTable + ", referencedColumns="
				+ referencedColumns + ", name=" + name + ", modifiers=" + modifiers + ", columns=" + columns
				+ ", constraints.table=" + constraints.getTable().getName() + "]";
	}

}
