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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

/**
 * The Class TableConstraints.
 */
@Entity
@Table(name = "KRONOS_SYNONYM_TARGETS")
public class HDBSynonymTarget {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HDBSYNONYMTARGET_ID", nullable = false)
	private Long id;
	
	/** The object. */
	@Column(name = "HDBSYNONYMTARGET_OBJECT", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String object;
    
    /** The schema. */
	@Column(name = "HDBSYNONYMTARGET_SCHEMA", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String schema;
	
	/** The synonym. */
	@OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "HDBSYNONYM_ID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private HDBSynonym synonym;

	/**
	 * Instantiates a new HDB synonym target.
	 *
	 * @param synonym the synonym
	 * @param object  the object
	 * @param schema  the schema
	 */
	public HDBSynonymTarget(HDBSynonym synonym, String object, String schema) {
		this();
		this.synonym = synonym;
		this.object = object;
		this.schema = schema;
	}
	
	/**
	 * Instantiates a new HDB synonym target.
	 */
	public HDBSynonymTarget() {
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
	 * Gets the object.
	 *
	 * @return the object
	 */
	public String getObject() {
		return object;
	}

	/**
	 * Sets the object.
	 *
	 * @param object the new object
	 */
	public void setObject(String object) {
		this.object = object;
	}

	/**
	 * Gets the schema.
	 *
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * Sets the schema.
	 *
	 * @param schema the new schema
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * Gets the synonym.
	 *
	 * @return the synonym
	 */
	public HDBSynonym getSynonym() {
		return synonym;
	}

	/**
	 * Sets the synonym.
	 *
	 * @param synonym the new synonym
	 */
	public void setSynonym(HDBSynonym synonym) {
		this.synonym = synonym;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "HDBSynonymTarget [id=" + id + ", object=" + object + ", schema=" + schema + "]";
	}

}
