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

@Table(name = "XSK_TABLE_IMPORT_TO_CSV")
public class XSKTableImportToCsvRelation {

  @Id
  @GeneratedValue
  @Column(name = "ID", columnDefinition = "BIGINT", length = 32, nullable = false)
  private Long id;

  @Column(name = "CSV_LOCATION", columnDefinition = "VARCHAR", nullable = false)
  private String csv;

  @Column(name = "HDBTI_LOCATION", columnDefinition = "VARCHAR", nullable = false)
  private String hdbti;

  @Column(name = "CSV_HASH", columnDefinition = "VARCHAR", nullable = false)
  private String csvHash;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCsv() {
    return csv;
  }

  public void setCsv(String csv) {
    this.csv = csv;
  }

  public String getHdbti() {
    return hdbti;
  }

  public void setHdbti(String hdbti) {
    this.hdbti = hdbti;
  }

  public String getCsvHash() {
    return csvHash;
  }

  public void setCsvHash(String csvHash) {
    this.csvHash = csvHash;
  }
}
