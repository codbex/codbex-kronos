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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Class TableConstraintPrimaryKey.
 */
@Entity
@Table(name = "KRONOS_TABLE_PRIMARYKEYS")
public class HDBTableConstraintPrimaryKey extends HDBTableConstraint {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRIMARYKEY_ID", nullable = false)
	private Long id;

	/**
	 * Instantiates a new table constraint primary key.
	 *
	 * @param name the name
	 * @param modifiers the modifiers
	 * @param columns the columns
	 * @param constraints the constraints
	 */
	public HDBTableConstraintPrimaryKey(String name, String[] modifiers, String[] columns, HDBTableConstraints constraints) {
		super(name, modifiers, columns, constraints);
		this.getConstraints().setPrimaryKey(this);
	}

	/**
	 * Instantiates a new table constraint primary key.
	 */
	public HDBTableConstraintPrimaryKey() {
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
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "TableConstraintPrimaryKey [id=" + id + ", name=" + name + ", modifiers=" + modifiers + ", columns="
				+ columns + ", constraints.table=" + constraints.getTable().getName() + "]";
	}

}
