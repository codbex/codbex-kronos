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
package com.codbex.kronos.xssecurestore.ds.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.codbex.kronos.xssecurestore.ds.api.ISecureStoreModel;

/**
 * The Class SecureStoreContent.
 */
@Table(name = ISecureStoreModel.SECURE_STORE_CONTENT_TABLE_NAME)
public class SecureStoreContent {

  /** The id. */
  @Id
  @GeneratedValue
  @Column(name = "KEY", columnDefinition = "BIGINT", length = 32, nullable = false)
  private long id;

  /** The store id. */
  @Column(name = "STORE_ID", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String storeId;

  /** The user id. */
  @Column(name = "USER_ID", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String userId;

  /** The data id. */
  @Column(name = "DATA_ID", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String dataId;

  /** The data value. */
  @Column(name = "DATA_VALUE", columnDefinition = "BLOB", nullable = false, length = 5000)
  private byte[] dataValue;

  /**
   * Instantiates a new secure store content.
   */
  public SecureStoreContent() {
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets the store id.
   *
   * @return the store id
   */
  public String getStoreId() {
    return storeId;
  }

  /**
   * Sets the store id.
   *
   * @param storeId the new store id
   */
  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  /**
   * Gets the user id.
   *
   * @return the user id
   */
  public String getUserId() {
    return userId;
  }

  /**
   * Sets the user id.
   *
   * @param userId the new user id
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * Gets the data id.
   *
   * @return the data id
   */
  public String getDataId() {
    return dataId;
  }

  /**
   * Sets the data id.
   *
   * @param dataId the new data id
   */
  public void setDataId(String dataId) {
    this.dataId = dataId;
  }

  /**
   * Gets the data value.
   *
   * @return the data value
   */
  public byte[] getDataValue() {
    return dataValue;
  }

  /**
   * Sets the data value.
   *
   * @param dataValue the new data value
   */
  public void setDataValue(byte[] dataValue) {
    this.dataValue = dataValue;
  }
}
