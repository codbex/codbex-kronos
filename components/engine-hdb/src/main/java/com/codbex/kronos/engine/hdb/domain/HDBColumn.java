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
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

/**
 * The Class TableColumn.
 */
@MappedSuperclass
public class HDBColumn {

	/** The name. */
	@Column(name = "COLUMN_NAME", columnDefinition = "VARCHAR", nullable = false, length = 255)
	@Expose
	private String name;

	/** The type. */
	@Column(name = "COLUMN_TYPE", columnDefinition = "VARCHAR", nullable = false, length = 255)
	@Expose
	private String type;

	/** The length. */
	@Column(name = "COLUMN_LENGTH", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String length;

	/** The nullable. */
	@Column(name = "COLUMN_NULLABLE", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private boolean nullable;

	/** The primary key. */
	@Column(name = "COLUMN_PRIMARY_KEY", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private boolean primaryKey;

	/** The default value. */
	@Column(name = "COLUMN_DEFAULT_VALUE", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String defaultValue;

	/** The precision. */
	@Column(name = "COLUMN_PRECISION", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String precision;

	/** The scale. */
	@Column(name = "COLUMN_SCALE", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String scale;

	/** The unique. */
	@Column(name = "COLUMN_UNIQUE", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private boolean unique;

	/** The is default value date time function. */
	@Column(name = "COLUMN_DEFAULT_IS_DATETIME", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private boolean isDefaultValueDateTimeFunction;

	/** The comment. */
	@Column(name = "COLUMN_COMMENT", columnDefinition = "VARCHAR", nullable = true, length = 2000)
	@Expose
	private String comment;

	/** The alias. */
	@Column(name = "COLUMN_ALIAS", columnDefinition = "VARCHAR", nullable = true, length = 255)
	@Expose
	private String alias;

	/** The fuzzy search index. */
	@Column(name = "COLUMN_FUZZY_SEARCH", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private boolean fuzzySearchIndex;

	/** The statement. */
	@Column(name = "COLUMN_STATEMENT", columnDefinition = "VARCHAR", nullable = true, length = 2000)
	@Expose
	private String statement;

	/** The is calculated column. */
	@Column(name = "COLUMN_CALCULATED", columnDefinition = "BOOLEAN", nullable = true)
	@Expose
	private boolean isCalculatedColumn;

	/**
	 * Instantiates a new table column.
	 *
	 * @param name         the name
	 * @param type         the type
	 * @param length       the length
	 * @param nullable     the nullable
	 * @param primaryKey   the primary key
	 * @param defaultValue the default value
	 * @param scale        the scale
	 * @param unique       the unique
	 */
	public HDBColumn(String name, String type, String length, boolean nullable, boolean primaryKey,
			String defaultValue, String scale, boolean unique) {
		super();
		this.name = name;
		this.type = type;
		this.length = length;
		this.nullable = nullable;
		this.primaryKey = primaryKey;
		this.defaultValue = defaultValue;
		this.scale = scale;
		this.unique = unique;
	}

	/**
	 * Instantiates a new table column.
	 *
	 * @param name   the name
	 * @param type   the type
	 * @param length the length
	 */
	public HDBColumn(String name, String type, String length) {
		this(name, type, length, true, false, null, "0", false);
	}

	/**
	 * Instantiates a new table column.
	 *
	 * @param name       the name
	 * @param type       the type
	 * @param length     the length
	 * @param nullable   the nullable
	 * @param primaryKey the primary key
	 */
	public HDBColumn(String name, String type, String length, boolean nullable, boolean primaryKey) {
		this(name, type, length, nullable, primaryKey, null, "0", false);
	}

	/**
	 * Instantiates a new table column.
	 */
	public HDBColumn() {
		super();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/**
	 * Checks if is nullable.
	 *
	 * @return the nullable
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * Sets the nullable.
	 *
	 * @param nullable the nullable to set
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * Checks if is primary key.
	 *
	 * @return the primaryKey
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * Sets the primary key.
	 *
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * Gets the default value.
	 *
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the default value.
	 *
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the precision.
	 *
	 * @return the precision
	 */
	public String getPrecision() {
		return precision;
	}

	/**
	 * Sets the precision.
	 *
	 * @param precision the new precision
	 */
	public void setPrecision(String precision) {
		this.precision = precision;
	}

	/**
	 * Gets the scale.
	 *
	 * @return the scale
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * Sets the scale.
	 *
	 * @param scale the scale to set
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**
	 * Checks if is unique.
	 *
	 * @return the unique
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * Sets the unique.
	 *
	 * @param unique the unique to set
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/**
	 * Checks if is default value date time function.
	 *
	 * @return true, if is default value date time function
	 */
	public boolean isDefaultValueDateTimeFunction() {
		return isDefaultValueDateTimeFunction;
	}

	/**
	 * Sets the default value date time function.
	 *
	 * @param isDefaultValueDateTimeFunction the new default value date time
	 *                                       function
	 */
	public void setDefaultValueDateTimeFunction(boolean isDefaultValueDateTimeFunction) {
		this.isDefaultValueDateTimeFunction = isDefaultValueDateTimeFunction;
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the alias.
	 *
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the alias.
	 *
	 * @param alias the new alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Checks if is fuzzy search index.
	 *
	 * @return true, if is fuzzy search index
	 */
	public boolean isFuzzySearchIndex() {
		return fuzzySearchIndex;
	}

	/**
	 * Sets the fuzzy search index.
	 *
	 * @param fuzzySearchIndex the new fuzzy search index
	 */
	public void setFuzzySearchIndex(boolean fuzzySearchIndex) {
		this.fuzzySearchIndex = fuzzySearchIndex;
	}

	/**
	 * Gets the statement.
	 *
	 * @return the statement
	 */
	public String getStatement() {
		return statement;
	}

	/**
	 * Sets the statement.
	 *
	 * @param statement the new statement
	 */
	public void setStatement(String statement) {
		this.statement = statement;
	}

	/**
	 * Checks if is calculated column.
	 *
	 * @return true, if is calculated column
	 */
	public boolean isCalculatedColumn() {
		return isCalculatedColumn;
	}

	/**
	 * Sets the calculated column.
	 *
	 * @param isCalculatedColumn the new calculated column
	 */
	public void setCalculatedColumn(boolean isCalculatedColumn) {
		this.isCalculatedColumn = isCalculatedColumn;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "HDBColumn [name=" + name + ", type=" + type + ", length=" + length + ", nullable="
				+ nullable + ", primaryKey=" + primaryKey + ", defaultValue=" + defaultValue + ", scale=" + scale
				+ ", unique=" + unique + "]";
	}

}
