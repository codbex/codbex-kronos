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

import java.util.Objects;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.codbex.kronos.engine.hdb.parser.HDBSynonymMissingPropertyException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

/**
 * The Class Table.
 */
@Entity
@Table(name = "KRONOS_SYNONYMS")
public class HDBSynonym extends HDBDataStructure {
	
	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "hdbsynonym";
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HDBSYNONYM_ID", nullable = false)
	private Long id;
	
	/** The target. */
	@OneToOne(mappedBy = "synonym", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	@Nullable
	@Expose
	private HDBSynonymTarget target;
	
	/** The group. */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "HDBSYNONYMGROUP_ID", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private HDBSynonymGroup group;
	
	/**
	 * Instantiates a new model.
	 *
	 * @param location the location
	 * @param name the name
	 * @param description the description
	 * @param dependencies the dependencies
	 * @param object the object
	 * @param schema the schema
	 */
	public HDBSynonym(String location, String name, String description, String dependencies, String object, String schema, String content, boolean classic, HDBSynonymGroup group) {
		super(location, name, ARTEFACT_TYPE, description, dependencies, schema, content, classic);
		this.target = new HDBSynonymTarget();
		this.target.setObject(object);
		this.target.setSchema(schema);
		this.target.setSynonym(this);
		this.group = group;
	}
	
	/**
	 * Instantiates a new synonym.
	 */
	public HDBSynonym() {
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
	 * Gets the target.
	 *
	 * @return the target
	 */
	public HDBSynonymTarget getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 *
	 * @param target the new target
	 */
	public void setTarget(HDBSynonymTarget target) {
		this.target = target;
	}
	
	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public HDBSynonymGroup getGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(HDBSynonymGroup group) {
		this.group = group;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "HDBSynonym [id=" + id + ", target=" + target + ", location=" + location + ", name=" + name + ", type="
				+ type + ", description=" + description + ", key=" + key + ", dependencies=" + dependencies
				+ ", lifecycle=" + lifecycle + ", phase=" + phase + ", error=" + error + ", createdBy=" + createdBy
				+ ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt + "]";
	}
	
	/**
	   * Check for all mandatory fields presence.
	   */
	  public void checkForAllMandatoryFieldsPresence() {
	    checkPresence(target, "target");
	    checkPresence(this.getTarget().getObject(), "object");
	    checkPresence(this.getTarget().getSchema(), "schema");
	  }

	  /**
	   * Check presence.
	   *
	   * @param <T> the generic type
	   * @param field the field
	   * @param fieldName the field name
	   */
	  private <T> void checkPresence(T field, String fieldName) {
	    if (Objects.isNull(field)) {
	      throw new HDBSynonymMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
	    }
	  }
	
}
