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
package com.codbex.kronos.engine.xssecurity.domain;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.eclipse.dirigible.components.base.artefact.Artefact;
import org.eclipse.dirigible.components.base.converters.ListOfStringsToCsvConverter;

import com.google.gson.annotations.Expose;

/**
 * The Class JobDefinition.
 */
@Entity
@Table(name = "KRONOS_ACCESS")
public class XSAccess extends Artefact {

	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "xsaccess";

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACCESS_ID", nullable = false)
	private Long id;

	/** The path. */
	@Column(name = "ACCESS_PATH", columnDefinition = "VARCHAR", nullable = false, length = 255)
	@Expose
	private String path;

	/** The authentication methods. */
	@Column(name = "ACCESS_AUTHENTICATION_METHODS", columnDefinition = "VARCHAR", nullable = true, length = 2000)
	@Nullable
	@Convert(converter = ListOfStringsToCsvConverter.class)
	@Expose
	private List<String> authenticationMethods;

	/** The authorization roles. */
	@Column(name = "ACCESS_AUTHORIZATION_ROLES", columnDefinition = "BLOB", nullable = false, length = 2000)
	@Nullable
	@Convert(converter = ListOfStringsToCsvConverter.class)
	@Expose
	private List<String> authorizationRoles;

	/** The exposed. */
	@Column(name = "ACCESS_EXPOSED", columnDefinition = "BOOLEAN", nullable = false, length = 1)
	private boolean exposed = true;

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
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Gets the authentication methods.
	 *
	 * @return the authentication methods
	 */
	public List<String> getAuthenticationMethods() {
		return authenticationMethods;
	}

	/**
	 * Sets the authentication methods.
	 *
	 * @param authenticationMethods the new authentication methods
	 */
	public void setAuthenticationMethods(List<String> authenticationMethods) {
		this.authenticationMethods = authenticationMethods;
	}

	/**
	 * Gets the authorization roles.
	 *
	 * @return the authorization roles
	 */
	public List<String> getAuthorizationRoles() {
		return authorizationRoles;
	}

	/**
	 * Sets the authorization roles.
	 *
	 * @param authorizationRoles the new authorization roles
	 */
	public void setAuthorizationRoles(List<String> authorizationRoles) {
		this.authorizationRoles = authorizationRoles;
	}

	/**
	 * Checks if is exposed.
	 *
	 * @return true, if is exposed
	 */
	public boolean isExposed() {
		return exposed;
	}

	/**
	 * Sets the exposed.
	 *
	 * @param exposed the new exposed
	 */
	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}

}
