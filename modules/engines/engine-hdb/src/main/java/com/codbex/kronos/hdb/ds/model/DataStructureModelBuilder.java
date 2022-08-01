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
package com.codbex.kronos.hdb.ds.model;

import java.sql.Timestamp;

public class DataStructureModelBuilder {

  private String location;
  private String name;
  private String type;
  private String hash;
  private String createdBy;
  private Timestamp createdAt;
  private String schema;
  private String rawContent;
  private DBContentType dbContentType;


  public DataStructureModelBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public DataStructureModelBuilder withLocation(String location) {
    this.location = location;
    return this;
  }

  public DataStructureModelBuilder withType(String type) {
    this.type = type;
    return this;
  }

  public DataStructureModelBuilder withHash(String hash) {
    this.hash = hash;
    return this;
  }

  public DataStructureModelBuilder createdAt(Timestamp timestamp) {
    this.createdAt = timestamp;
    return this;
  }

  public DataStructureModelBuilder createdBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  public DataStructureModelBuilder withSchema(String schema) {
    this.schema = schema;
    return this;
  }

  public DataStructureModelBuilder rawContent(String rawContent) {
    this.rawContent = rawContent;
    return this;
  }

  public String getLocation() {
    return location;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getHash() {
    return hash;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public String getSchema() {
    return schema;
  }

  public String getRawContent() {
    return rawContent;
  }

  public DBContentType getDbContentType() {
    return dbContentType;
  }

  public DataStructureModel build() {
    return new DataStructureModel(this);
  }
}
