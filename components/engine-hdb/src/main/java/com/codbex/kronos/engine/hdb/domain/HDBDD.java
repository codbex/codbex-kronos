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

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;

/**
 * The Class DataStructureCdsModel.
 */
@Entity
@Table(name = "KRONOS_HDBDD")
public class HDBDD extends HDBDataStructure {

	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "hdbdd";

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HDBDD_ID", nullable = false)
	private Long id;

	/** The tables. */
	@OneToMany(mappedBy = "hdbdd", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Expose
	private List<HDBTable> tables = new ArrayList<HDBTable>();

	/** The tables. */
	@OneToMany(mappedBy = "hdbdd", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Expose
	private List<HDBTableType> tableTypes = new ArrayList<HDBTableType>();

	/** The views. */
	@OneToMany(mappedBy = "hdbdd", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Expose
	private List<HDBView> views = new ArrayList<HDBView>();

	/** The force update. */
	@Transient
	private boolean forceUpdate;

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
	 * Gets the tables.
	 *
	 * @return the tables
	 */
	public List<HDBTable> getTables() {
		return tables;
	}

	/**
	 * Sets the tables.
	 *
	 * @param tables the tables to set
	 */
	public void setTables(List<HDBTable> tables) {
		this.tables = tables;
	}

	/**
	 * Gets the table types.
	 *
	 * @return the table types
	 */
	public List<HDBTableType> getTableTypes() {
		return tableTypes;
	}

	/**
	 * Sets the table types.
	 *
	 * @param tabletypes the table types to set
	 */
	public void setTableTypes(List<HDBTableType> tabletypes) {
		this.tableTypes = tabletypes;
	}

	/**
	 * Gets the views.
	 *
	 * @return the views
	 */
	public List<HDBView> getViews() {
		return views;
	}

	/**
	 * Sets the views.
	 *
	 * @param views the views to set
	 */
	public void setViews(List<HDBView> views) {
		this.views = views;
	}

	/**
	 * Find table.
	 *
	 * @param name the name
	 * @return the table
	 */
	public HDBTable findTable(String name) {
		for (HDBTable t : getTables()) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Find table type.
	 *
	 * @param name the name
	 * @return the table type
	 */
	public HDBTableType findTableType(String name) {
		for (HDBTableType t : getTableTypes()) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Find view.
	 *
	 * @param name the name
	 * @return the view
	 */
	public HDBView findView(String name) {
		for (HDBView v : getViews()) {
			if (v.getName().equals(name)) {
				return v;
			}
		}
		return null;
	}

	/**
	 * Checks if is force update.
	 *
	 * @return true, if is force update
	 */
	public boolean isForceUpdate() {
		return forceUpdate;
	}

	/**
	 * Sets the force update.
	 *
	 * @param forceUpdate the new force update
	 */
	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}
}
