/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.domain;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;

/**
 * The Class Table.
 */
@Entity
@Table(name = "KRONOS_SYNONYMGROUPS")
public class HDBSynonymGroup extends HDBDataStructure {

    /** The Constant ARTEFACT_TYPE. */
    public static final String ARTEFACT_TYPE = "hdbsynonymgroup";

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HDBSYNONYMGROUP_ID", nullable = false)
    private Long id;

    /** The target. */
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Expose
    /** The synonym definitions. */
    Map<String, HDBSynonym> synonymDefinitions = new HashMap<>();

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
    public HDBSynonymGroup(String location, String name, String description, String dependencies, String object, String schema,
            String content, boolean classic) {
        super(location, name, ARTEFACT_TYPE, description, dependencies, schema, content, classic);
    }

    /**
     * Instantiates a new table.
     */
    public HDBSynonymGroup() {
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
     * Gets the synonym definitions.
     *
     * @return the synonym definitions
     */
    public Map<String, HDBSynonym> getSynonymDefinitions() {
        return synonymDefinitions;
    }

    /**
     * Sets the synonym definitions.
     *
     * @param synonymDefinitions the synonym definitions
     */
    public void setSynonymDefinitions(Map<String, HDBSynonym> synonymDefinitions) {
        this.synonymDefinitions = synonymDefinitions;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "HDBSynonymGroup [id=" + id + ", synonymDefinitions=" + synonymDefinitions + ", location=" + location + ", name=" + name
                + ", type=" + type + ", description=" + description + ", key=" + key + ", dependencies=" + dependencies + ", lifecycle="
                + lifecycle + ", phase=" + phase + ", error=" + error + ", createdBy=" + createdBy + ", createdAt=" + createdAt
                + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt + "]";
    }

}
