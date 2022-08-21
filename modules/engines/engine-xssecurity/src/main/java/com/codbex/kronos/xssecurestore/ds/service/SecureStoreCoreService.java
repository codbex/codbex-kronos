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
package com.codbex.kronos.xssecurestore.ds.service;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.PersistenceManager;

import com.codbex.kronos.xssecurestore.ds.api.ISecureStoreCoreService;
import com.codbex.kronos.xssecurestore.ds.api.ISecureStoreModel;
import com.codbex.kronos.xssecurestore.ds.api.SecureStoreException;
import com.codbex.kronos.xssecurestore.ds.model.SecureStore;
import com.codbex.kronos.xssecurestore.ds.model.SecureStoreContent;
import com.google.gson.JsonSyntaxException;

/**
 * The Class SecureStoreCoreService.
 */
public class SecureStoreCoreService implements ISecureStoreCoreService {

  /** The data source. */
  public DataSource getDataSource() {
  	return (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
  }

  /** The secure store persistence manager. */
  private PersistenceManager<SecureStore> secureStorePersistenceManager = new PersistenceManager<SecureStore>();

  /** The secure store content persistence manager. */
  private PersistenceManager<SecureStoreContent> secureStoreContentPersistenceManager = new PersistenceManager<SecureStoreContent>();

  /** The secure store encryptor. */
  private SecureStoreEncryptor secureStoreEncryptor = new SecureStoreEncryptor();

  /**
   * Creates the secure store.
   *
   * @param location the location
   * @param content the content
   * @return the secure store
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public SecureStore createSecureStore(String location, String content) throws SecureStoreException {
    if (!isJSONValid(content) || content.isEmpty()) {
      throw new SecureStoreException("Invalid json at " + location);
    }

    SecureStore secureStore = new SecureStore();
    secureStore.setLocation(location);
    secureStore.setActive(true);

    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        secureStorePersistenceManager.insert(connection, secureStore);
        return secureStore;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new SecureStoreException(e.getMessage());
    }
  }

  /**
   * Gets the secure stores.
   *
   * @return the secure stores
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public List<SecureStore> getSecureStores() throws SecureStoreException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        return secureStorePersistenceManager.findAll(connection, SecureStore.class);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new SecureStoreException(e);
    }
  }

  /**
   * Removes the secure store.
   *
   * @param location the location
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public void removeSecureStore(String location) throws SecureStoreException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        secureStorePersistenceManager.delete(connection, SecureStore.class, location);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new SecureStoreException(e);
    }
  }

  /**
   * Exists secure store.
   *
   * @param location the location
   * @return true, if successful
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public boolean existsSecureStore(String location) throws SecureStoreException {
    return getSecureStore(location) != null;
  }

  /**
   * Gets the secure store.
   *
   * @param location the location
   * @return the secure store
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public SecureStore getSecureStore(String location) throws SecureStoreException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        return secureStorePersistenceManager.find(connection, SecureStore.class, location);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new SecureStoreException(e.getMessage());
    }
  }

  /**
   * Creates the secure store value.
   *
   * @param storeId the store id
   * @param userId the user id
   * @param dataId the data id
   * @param value the value
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public void createSecureStoreValue(String storeId, String userId, String dataId, String value) throws SecureStoreException {
    if (!existsSecureStore(storeId)) {
      throw new SecureStoreException("Non existing active secure store with id: " + storeId);
    }

    byte[] dataValueAsBytes = value.getBytes(StandardCharsets.UTF_8);

    SecureStoreContent existingXscSecureStoreContent = findSecureStoreContent(storeId, userId, dataId);
    if (existingXscSecureStoreContent != null) {
      existingXscSecureStoreContent.setDataValue(secureStoreEncryptor.encode(dataValueAsBytes));
      updateSecureStoreValue(existingXscSecureStoreContent);
      return;
    }

    SecureStoreContent secureStoreContent = new SecureStoreContent();
    secureStoreContent.setStoreId(storeId);
    secureStoreContent.setUserId(userId);
    secureStoreContent.setDataId(dataId);

    secureStoreContent.setDataValue(secureStoreEncryptor.encode(dataValueAsBytes));

    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        secureStoreContentPersistenceManager.insert(connection, secureStoreContent);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new SecureStoreException(e.getMessage());
    }
  }

  /**
   * Update secure store value.
   *
   * @param secureStoreContent the secure store content
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public void updateSecureStoreValue(SecureStoreContent secureStoreContent) throws SecureStoreException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        secureStoreContentPersistenceManager.update(connection, secureStoreContent);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new SecureStoreException(e.getMessage());
    }
  }

  /**
   * Find secure store content.
   *
   * @param storeId the store id
   * @param userId the user id
   * @param dataId the data id
   * @return the secure store content
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public SecureStoreContent findSecureStoreContent(String storeId, String userId, String dataId) throws SecureStoreException {
    List<Object> queryArguments = Arrays.asList(storeId, userId, dataId);

    try {
      Connection connection = null;

      try {
        connection = getDataSource().getConnection();
        List<SecureStoreContent> foundContent = secureStoreContentPersistenceManager
            .query(connection, SecureStoreContent.class, ISecureStoreModel.SECURE_STORE_VALUE_FIND_STATEMENT, queryArguments);

        if (foundContent.size() == 0) {
          return null;
        } else if (foundContent.size() == 1) {
          SecureStoreContent secureStoreContent = foundContent.get(0);
          byte[] encodedData = secureStoreContent.getDataValue();
          secureStoreContent.setDataValue(secureStoreEncryptor.decode(encodedData));
          return foundContent.get(0);
        } else {
          throw new SQLException("Duplicate security createSecureStoreValue content identifier");
        }

      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new SecureStoreException(e.getMessage());
    }
  }

  /**
   * Delete secure store value.
   *
   * @param storeId the store id
   * @param userId the user id
   * @param dataId the data id
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public void deleteSecureStoreValue(String storeId, String userId, String dataId) throws SecureStoreException {
    if (!existsSecureStoreContent(storeId, userId, dataId)) {
      throw new SecureStoreException("No such secure createSecureStoreValue value to be removed.");
    }

    List<Object> queryArguments = Arrays.asList(storeId, userId, dataId);

    try {
      Connection connection = null;

      try {
        connection = getDataSource().getConnection();
        secureStoreContentPersistenceManager.execute(connection, ISecureStoreModel.SECURE_STORE_VALUE_DELETE_STATEMENT, queryArguments);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new SecureStoreException(e.getMessage());
    }
  }

  /**
   * Delete secure store values by store id.
   *
   * @param userId the user id
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public void deleteSecureStoreValuesByStoreId(String userId) throws SecureStoreException {

    try {
      Connection connection = null;

      try {
        connection = getDataSource().getConnection();
        secureStoreContentPersistenceManager
            .execute(connection, ISecureStoreModel.SECURE_STORE_DELETE_BY_STORE_ID, Arrays.asList(userId));
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new SecureStoreException(e.getMessage());
    }
  }

  /**
   * Exists secure store content.
   *
   * @param storeId the store id
   * @param userId the user id
   * @param dataId the data id
   * @return true, if successful
   * @throws SecureStoreException the secure store exception
   */
  @Override
  public boolean existsSecureStoreContent(String storeId, String userId, String dataId) throws SecureStoreException {
    return findSecureStoreContent(storeId, userId, dataId) != null;
  }

  /**
   * Checks if is JSON valid.
   *
   * @param content the content
   * @return true, if is JSON valid
   */
  private boolean isJSONValid(String content) {
    try {
      GsonHelper.PARSER.parse(content);
      return true;
    } catch (JsonSyntaxException ex) {
      return false;
    }

  }
}
