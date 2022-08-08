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
package com.codbex.kronos.hdb.ds.service.manager;

import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.OperationNotSupportedException;


/**
 * The Interface IDataStructureManager.
 *
 * @param <T> the generic type
 */
public interface IDataStructureManager<T extends DataStructureModel> {

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  Map<String, T> getDataStructureModels();

  /**
   * Synchronize runtime metadata.
   *
   * @param tableModel the table model
   * @throws DataStructuresException the data structures exception
   */
  void synchronizeRuntimeMetadata(T tableModel) throws DataStructuresException;

  /**
   * Creates the data structure.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  boolean createDataStructure(Connection connection, T tableModel) throws SQLException;

  /**
   * Drop data structure.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  boolean dropDataStructure(Connection connection, T tableModel) throws SQLException;

  /**
   * Update data structure.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   * @throws OperationNotSupportedException the operation not supported exception
   */
  boolean updateDataStructure(Connection connection, T tableModel)
      throws SQLException, OperationNotSupportedException;

  /**
   * Gets the data structure synchronized.
   *
   * @return the data structure synchronized
   */
  List<String> getDataStructureSynchronized();

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  String getDataStructureType();

  /**
   * Cleanup.
   *
   * @throws DataStructuresException the data structures exception
   */
  void cleanup() throws DataStructuresException;

  /**
   * Clear cache.
   */
  void clearCache();

  /**
   * Synchronize parsed by root metadata.
   *
   * @param tableModel the table model
   * @throws DataStructuresException the data structures exception
   */
  void synchronizeParsedByRootMetadata(T tableModel) throws DataStructuresException;

  /**
   * Exists artifact metadata.
   *
   * @param tableModel the table model
   * @return true, if successful
   * @throws DataStructuresException the data structures exception
   */
  boolean existsArtifactMetadata(T tableModel) throws DataStructuresException;
}
