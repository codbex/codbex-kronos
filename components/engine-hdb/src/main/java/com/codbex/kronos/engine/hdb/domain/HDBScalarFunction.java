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
 * The Class HDBTableFunction.
 */
@Entity
@Table(name = "KRONOS_SCALARFUNCTIONS")
public class HDBScalarFunction extends HDBDataStructure {
	
	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "hdbscalarfunction";
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HDBSCALARFUNCTION_ID", nullable = false)
	private Long id;
	
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
	 */
	protected HDBScalarFunction(String location, String name, String description, String dependencies, String schema, String content, boolean classic) {
		super(location, name, ARTEFACT_TYPE, null, null, schema, content, classic);
	}
	
	/**
	 * Instantiates a new model.
	 */
	public HDBScalarFunction() {
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
		return "HDBScalarFunction [id=" + id + ", location=" + location + ", name=" + name + ", type=" + type
				+ ", description=" + description + ", key=" + key + ", dependencies=" + dependencies + ", lifecycle="
				+ lifecycle + ", phase=" + phase + ", error=" + error + ", createdBy=" + createdBy + ", createdAt="
				+ createdAt + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt + "]";
	}
	
}
