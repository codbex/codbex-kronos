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
package com.codbex.kronos.engine.xsjob.domain;

import java.sql.Timestamp;
import java.util.Map;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eclipse.dirigible.components.base.artefact.Artefact;

import com.google.gson.annotations.Expose;

/**
 * The Class JobDefinition.
 */
@Entity
@Table(name = "KRONOS_JOBS")
public class XSJob extends Artefact {

	/** The Constant ARTEFACT_TYPE. */
	public static final String ARTEFACT_TYPE = "xsjob";

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "JOB_ID", nullable = false)
	private Long id;

	/** The group. */
	@Column(name = "JOB_GROUP", columnDefinition = "VARCHAR", nullable = false, length = 255)
	@Expose
	private String group;

	/** The module. */
	@Column(name = "JOB_MODULE", columnDefinition = "VARCHAR", nullable = false, length = 255)
	@Expose
	private String module;

	/** The function. */
	@Column(name = "JOB_FUNCTION", columnDefinition = "VARCHAR", nullable = false, length = 255)
	@Expose
	private String function;

	/** The cron expression. */
	@Column(name = "JOB_CRON_EXPRESSION", columnDefinition = "VARCHAR", nullable = false, length = 255)
	@Expose
	private String cronExpression;

	/** The start at. */
	@Column(name = "JOB_START_AT", columnDefinition = "TIMESTAMP", nullable = true)
	@Nullable
	@Expose
	private Timestamp startAt;

	/** The end at. */
	@Column(name = "JOB_END_AT", columnDefinition = "TIMESTAMP", nullable = true)
	@Nullable
	@Expose
	private Timestamp endAt;

	/** The parameters. */
	@Column(name = "JOB_PARAMETERS")
	@Nullable
	@Lob
	private byte[] parameters;

	/** The parameters as map. */
	@Transient
	private Map<String, String> parametersAsMap;
	
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
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Gets the module.
	 *
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * Sets the module.
	 *
	 * @param module the new module
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * Gets the function.
	 *
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * Sets the function.
	 *
	 * @param function the new function
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * Gets the start at.
	 *
	 * @return the start at
	 */
	public Timestamp getStartAt() {
		return startAt;
	}

	/**
	 * Sets the start at.
	 *
	 * @param startAt the new start at
	 */
	public void setStartAt(Timestamp startAt) {
		this.startAt = startAt;
	}

	/**
	 * Gets the end at.
	 *
	 * @return the end at
	 */
	public Timestamp getEndAt() {
		return endAt;
	}

	/**
	 * Sets the end at.
	 *
	 * @param endAt the new end at
	 */
	public void setEndAt(Timestamp endAt) {
		this.endAt = endAt;
	}

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public byte[] getParameters() {
		return parameters;
	}

	/**
	 * Sets the parameters.
	 *
	 * @param parameters the new parameters
	 */
	public void setParameters(byte[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * Gets the parameters as map.
	 *
	 * @return the parameters as map
	 */
	public Map<String, String> getParametersAsMap() {
		return parametersAsMap;
	}

	/**
	 * Sets the parameters as map.
	 *
	 * @param parametersAsMap the parameters as map
	 */
	public void setParametersAsMap(Map<String, String> parametersAsMap) {
		this.parametersAsMap = parametersAsMap;
	}

	/**
	 * Gets the cron expression.
	 *
	 * @return the cron expression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * Sets the cron expression.
	 *
	 * @param cronExpression the new cron expression
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

}
