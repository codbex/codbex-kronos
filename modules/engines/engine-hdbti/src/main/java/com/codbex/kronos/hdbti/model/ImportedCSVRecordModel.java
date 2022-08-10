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
package com.codbex.kronos.hdbti.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class ImportedCSVRecordModel.
 */
@Table(name = "KRONOS_IMPORTED_CSV_RECORDS")
public class ImportedCSVRecordModel {

  /** The id. */
  @Id
  @GeneratedValue
  @Column(name = "ID", columnDefinition = "BIGINT", length = 32, nullable = false)
  private Long id;

  /** The row id. */
  @Column(name = "CSV_RECORD_ID", columnDefinition = "VARCHAR", nullable = false)
  private String rowId;

  /** The table name. */
  @Column(name = "TABLE_NAME", columnDefinition = "VARCHAR", nullable = false)
  private String tableName;

  /** The csv location. */
  @Column(name = "CSV_LOCATION", columnDefinition = "VARCHAR", nullable = false)
  private String csvLocation;

  /** The hdbti location. */
  @Column(name = "HDBTI_LOCATION", columnDefinition = "VARCHAR", nullable = false)
  private String hdbtiLocation;

  /** The hash. */
  @Column(name = "HASH", columnDefinition = "VARCHAR", nullable = false)
  private String hash;

  /** The created at. */
  @Column(name = "DS_CREATED_AT", columnDefinition = "TIMESTAMP", nullable = false)
  private Timestamp createdAt;

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
   * Gets the row id.
   *
   * @return the row id
   */
  public String getRowId() {
    return rowId;
  }

  /**
   * Sets the row id.
   *
   * @param rowId the new row id
   */
  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  /**
   * Gets the table name.
   *
   * @return the table name
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * Sets the table name.
   *
   * @param tableName the new table name
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   * Gets the csv location.
   *
   * @return the csv location
   */
  public String getCsvLocation() {
    return csvLocation;
  }

  /**
   * Sets the csv location.
   *
   * @param csvLocation the new csv location
   */
  public void setCsvLocation(String csvLocation) {
    this.csvLocation = csvLocation;
  }

  /**
   * Gets the hdbti location.
   *
   * @return the hdbti location
   */
  public String getHdbtiLocation() {
    return hdbtiLocation;
  }

  /**
   * Sets the hdbti location.
   *
   * @param hdbtiLocation the new hdbti location
   */
  public void setHdbtiLocation(String hdbtiLocation) {
    this.hdbtiLocation = hdbtiLocation;
  }

  /**
   * Gets the hash.
   *
   * @return the hash
   */
  public String getHash() {
    return hash;
  }

  /**
   * Sets the hash.
   *
   * @param hash the new hash
   */
  public void setHash(String hash) {
    this.hash = hash;
  }

  /**
   * Gets the created at.
   *
   * @return the created at
   */
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the created at.
   *
   * @param createdAt the new created at
   */
  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }
}
