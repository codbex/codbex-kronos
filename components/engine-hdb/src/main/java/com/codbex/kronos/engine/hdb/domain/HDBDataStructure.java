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
import javax.persistence.MappedSuperclass;

import org.eclipse.dirigible.components.base.artefact.Artefact;

import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelBuilder;
import com.google.gson.annotations.Expose;

/**
 * The Class HDBDataStructure.
 */
@MappedSuperclass
public class HDBDataStructure extends Artefact {

	/** The schema. */
	@Column(name = "HDB_SCHEMA", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String schema;

	/** The raw content. */
	@Column(name = "HDB_CONTENT", columnDefinition = "CLOB")
	private String content;

	/** The db content type. */
	/** The primary key. */
	@Column(name = "HDB_CLASSIC", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private boolean classic;

	
	/**
	 * Instantiates a new model.
	 *
	 * @param location     the location
	 * @param name         the name
	 * @param type         the type
	 * @param description  the description
	 * @param dependencies the dependencies
	 * @param schema       the schema
	 * @param content      the content
	 * @param classic      the classic
	 */
	protected HDBDataStructure(String location, String name, String type, String description, String dependencies, String schema, String content, boolean classic) {
		super(location, name, type, null, null);
		this.schema = schema;
		this.content = content;
		this.classic = classic;
	}
	
	/**
	   * Instantiates a new data structure model.
	   *
	   * @param builder the builder
	   */
	  protected HDBDataStructure(HDBDataStructureModelBuilder builder) {
	    this.location = builder.getLocation();
	    this.name = builder.getName();
	    this.type = builder.getType();
	    this.schema = builder.getSchema();
	    this.content = builder.getRawContent();
	    this.classic = builder.isClassic();
	  }
	
	/**
	 * Instantiates a new model.
	 */
	public HDBDataStructure() {
		super();
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
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Checks if is classic.
	 *
	 * @return true, if is classic
	 */
	public boolean isClassic() {
		return classic;
	}

	/**
	 * Sets the classic.
	 *
	 * @param classic the new classic
	 */
	public void setClassic(boolean classic) {
		this.classic = classic;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "HDBDataStructure [schema=" + schema + ", content=" + content + ", classic=" + classic + ", location="
				+ location + ", name=" + name + ", type=" + type + ", description=" + description + ", key=" + key
				+ ", dependencies=" + dependencies + ", lifecycle=" + lifecycle + ", phase=" + phase + ", error="
				+ error + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy
				+ ", updatedAt=" + updatedAt + "]";
	}

}
