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

/**
 * The Class DataStructureModelBuilder.
 */
public class DataStructureModelBuilder {

  /** The location. */
  private String location;
  
  /** The name. */
  private String name;
  
  /** The type. */
  private String type;
  
  /** The hash. */
  private String hash;
  
  /** The created by. */
  private String createdBy;
  
  /** The created at. */
  private Timestamp createdAt;
  
  /** The schema. */
  private String schema;
  
  /** The raw content. */
  private String rawContent;
  
  /** The db content type. */
  private DBContentType dbContentType;


  /**
   * With name.
   *
   * @param name the name
   * @return the data structure model builder
   */
  public DataStructureModelBuilder withName(String name) {
    this.name = name;
    return this;
  }

  /**
   * With location.
   *
   * @param location the location
   * @return the data structure model builder
   */
  public DataStructureModelBuilder withLocation(String location) {
    this.location = location;
    return this;
  }

  /**
   * With type.
   *
   * @param type the type
   * @return the data structure model builder
   */
  public DataStructureModelBuilder withType(String type) {
    this.type = type;
    return this;
  }

  /**
   * With hash.
   *
   * @param hash the hash
   * @return the data structure model builder
   */
  public DataStructureModelBuilder withHash(String hash) {
    this.hash = hash;
    return this;
  }

  /**
   * Created at.
   *
   * @param timestamp the timestamp
   * @return the data structure model builder
   */
  public DataStructureModelBuilder createdAt(Timestamp timestamp) {
    this.createdAt = timestamp;
    return this;
  }

  /**
   * Created by.
   *
   * @param createdBy the created by
   * @return the data structure model builder
   */
  public DataStructureModelBuilder createdBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  /**
   * With schema.
   *
   * @param schema the schema
   * @return the data structure model builder
   */
  public DataStructureModelBuilder withSchema(String schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Raw content.
   *
   * @param rawContent the raw content
   * @return the data structure model builder
   */
  public DataStructureModelBuilder rawContent(String rawContent) {
    this.rawContent = rawContent;
    return this;
  }

  /**
   * Gets the location.
   *
   * @return the location
   */
  public String getLocation() {
    return location;
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
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return type;
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
   * Gets the created by.
   *
   * @return the created by
   */
  public String getCreatedBy() {
    return createdBy;
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
   * Gets the schema.
   *
   * @return the schema
   */
  public String getSchema() {
    return schema;
  }

  /**
   * Gets the raw content.
   *
   * @return the raw content
   */
  public String getRawContent() {
    return rawContent;
  }

  /**
   * Gets the db content type.
   *
   * @return the db content type
   */
  public DBContentType getDbContentType() {
    return dbContentType;
  }

  /**
   * Builds the.
   *
   * @return the data structure model
   */
  public DataStructureModel build() {
    return new DataStructureModel(this);
  }
}
