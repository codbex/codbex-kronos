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
package com.codbex.kronos.engine.xsjs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.eclipse.dirigible.components.base.artefact.Artefact;

import com.google.gson.annotations.Expose;

/**
 * The Class Xsjslib.
 */
@Entity
@Table(name = "KRONOS_XSJSLIB")
public class Xsjslib extends Artefact {
	
	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "xsjslib";
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "XSJSLIB_ID", nullable = false)
	private Long id;
	
	/** The content. */
	@Column(name = "XSJSLIB_CONTENT", columnDefinition = "CLOB", nullable = true)
	@Expose
	private byte[] content;
	
	
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
	 * Gets the content.
	 *
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Xsjslib [id=" + id + ", location=" + location + ", name=" + name + ", type=" + type + ", description="
				+ description + ", key=" + key + ", dependencies=" + dependencies + ", lifecycle=" + lifecycle
				+ ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", updatedAt="
				+ updatedAt + "]";
	}

}
