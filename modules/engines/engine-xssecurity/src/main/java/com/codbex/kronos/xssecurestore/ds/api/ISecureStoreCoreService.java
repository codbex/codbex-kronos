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
package com.codbex.kronos.xssecurestore.ds.api;

import java.util.List;

import com.codbex.kronos.xssecurestore.ds.model.SecureStore;
import com.codbex.kronos.xssecurestore.ds.model.SecureStoreContent;

/**
 * The Interface ISecureStoreCoreService.
 */
public interface ISecureStoreCoreService {

  /**
   * Exists secure store.
   *
   * @param location the location
   * @return true, if successful
   * @throws SecureStoreException the secure store exception
   */
  boolean existsSecureStore(String location) throws SecureStoreException;

  /**
   * Creates the secure store.
   *
   * @param location the location
   * @param content the content
   * @return the secure store
   * @throws SecureStoreException the secure store exception
   */
  SecureStore createSecureStore(String location, String content) throws SecureStoreException;

  /**
   * Gets the secure store.
   *
   * @param location the location
   * @return the secure store
   * @throws SecureStoreException the secure store exception
   */
  SecureStore getSecureStore(String location) throws SecureStoreException;

  /**
   * Gets the secure stores.
   *
   * @return the secure stores
   * @throws SecureStoreException the secure store exception
   */
  List<SecureStore> getSecureStores() throws SecureStoreException;

  /**
   * Removes the secure store.
   *
   * @param location the location
   * @throws SecureStoreException the secure store exception
   */
  void removeSecureStore(String location) throws SecureStoreException;

  /**
   * Creates the secure store value.
   *
   * @param storeId the store id
   * @param userId the user id
   * @param dataId the data id
   * @param value the value
   * @throws SecureStoreException the secure store exception
   */
  void createSecureStoreValue(String storeId, String userId, String dataId, String value) throws SecureStoreException;

  /**
   * Update secure store value.
   *
   * @param secureStoreContent the secure store content
   * @throws SecureStoreException the secure store exception
   */
  void updateSecureStoreValue(SecureStoreContent secureStoreContent) throws SecureStoreException;

  /**
   * Find secure store content.
   *
   * @param storeId the store id
   * @param userId the user id
   * @param dataId the data id
   * @return the secure store content
   * @throws SecureStoreException the secure store exception
   */
  SecureStoreContent findSecureStoreContent(String storeId, String userId, String dataId) throws SecureStoreException;

  /**
   * Delete secure store value.
   *
   * @param storeId the store id
   * @param userId the user id
   * @param dataId the data id
   * @throws SecureStoreException the secure store exception
   */
  void deleteSecureStoreValue(String storeId, String userId, String dataId) throws SecureStoreException;

  /**
   * Delete secure store values by store id.
   *
   * @param storeId the store id
   * @throws SecureStoreException the secure store exception
   */
  void deleteSecureStoreValuesByStoreId(String storeId) throws SecureStoreException;

  /**
   * Exists secure store content.
   *
   * @param storeId the store id
   * @param userId the user id
   * @param dataId the data id
   * @return true, if successful
   * @throws SecureStoreException the secure store exception
   */
  boolean existsSecureStoreContent(String storeId, String userId, String dataId) throws SecureStoreException;
}
