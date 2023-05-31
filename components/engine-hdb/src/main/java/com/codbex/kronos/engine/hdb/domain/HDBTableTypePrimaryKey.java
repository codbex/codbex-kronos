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

import java.util.Arrays;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

/**
 * The Class HDBTableTypePrimaryKey.
 */
@Entity
@Table(name = "KRONOS_TABLETYPE_PRIMARYKEYS")
public class HDBTableTypePrimaryKey {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRIMARYKEY_ID", nullable = false)
	private Long id;
	
	/** The name. */
	@Column(name = "PRIMARYKEY_NAME", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Nullable
	@Expose
	protected String name;
	
	/** The modifiers. */
	@Column(name = "PRIMARYKEY_MODIFIERS", columnDefinition = "VARCHAR", nullable = true, length = 2000)
	@Nullable
	@ElementCollection
	@OrderColumn
	@Expose
	protected String[] modifiers;
	
	/** The columns. */
	@Column(name = "PRIMARYKEY_COLUMNS", columnDefinition = "VARCHAR", nullable = true, length = 2000)
	@Nullable
	@ElementCollection
	@OrderColumn
	@Expose
	protected String[] columns;
	
	/** The constraints. */
	@OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TABLETYPE_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    protected HDBTableType tableType;

	/**
	 * Instantiates a new table constraint primary key.
	 *
	 * @param name the name
	 * @param modifiers the modifiers
	 * @param columns the columns
	 * @param constraints the constraints
	 */
	public HDBTableTypePrimaryKey(String name, String[] modifiers, String[] columns, HDBTableType tableType) {
		this.tableType = tableType;
		this.tableType.setPrimaryKey(this);
	}

	/**
	 * Instantiates a new table constraint primary key.
	 */
	public HDBTableTypePrimaryKey() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getModifiers() {
		return modifiers;
	}

	public void setModifiers(String[] modifiers) {
		this.modifiers = modifiers;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public HDBTableType getTableType() {
		return tableType;
	}

	public void setTableType(HDBTableType tableType) {
		this.tableType = tableType;
	}

	@Override
	public String toString() {
		return "HDBTableTypePrimaryKey [id=" + id + ", name=" + name + ", modifiers=" + Arrays.toString(modifiers)
				+ ", columns=" + Arrays.toString(columns) + "]";
	}
	
}
