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

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class TableImportToCsvRelation.
 */
@Table(name = "KRONOS_TABLE_IMPORT_TO_CSV")
public class TableImportToCsvRelation {

  /** The id. */
  @Id
  @GeneratedValue
  @Column(name = "ID", columnDefinition = "BIGINT", length = 32, nullable = false)
  private Long id;

  /** The csv. */
  @Column(name = "CSV_LOCATION", columnDefinition = "VARCHAR", nullable = false)
  private String csv;

  /** The hdbti. */
  @Column(name = "HDBTI_LOCATION", columnDefinition = "VARCHAR", nullable = false)
  private String hdbti;

  /** The csv hash. */
  @Column(name = "CSV_HASH", columnDefinition = "VARCHAR", nullable = false)
  private String csvHash;

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
   * Gets the csv.
   *
   * @return the csv
   */
  public String getCsv() {
    return csv;
  }

  /**
   * Sets the csv.
   *
   * @param csv the new csv
   */
  public void setCsv(String csv) {
    this.csv = csv;
  }

  /**
   * Gets the hdbti.
   *
   * @return the hdbti
   */
  public String getHdbti() {
    return hdbti;
  }

  /**
   * Sets the hdbti.
   *
   * @param hdbti the new hdbti
   */
  public void setHdbti(String hdbti) {
    this.hdbti = hdbti;
  }

  /**
   * Gets the csv hash.
   *
   * @return the csv hash
   */
  public String getCsvHash() {
    return csvHash;
  }

  /**
   * Sets the csv hash.
   *
   * @param csvHash the new csv hash
   */
  public void setCsvHash(String csvHash) {
    this.csvHash = csvHash;
  }
}
