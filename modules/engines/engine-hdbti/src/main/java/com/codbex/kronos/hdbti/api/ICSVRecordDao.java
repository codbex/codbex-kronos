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
package com.codbex.kronos.hdbti.api;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.eclipse.dirigible.engine.odata2.transformers.DBMetadataUtil;

import com.codbex.kronos.hdbti.utils.CSVRecordMetadata;

/**
 * The Interface ICSVRecordDao.
 */
public interface ICSVRecordDao {

  /**
   * Save.
   *
   * @param csvRecordMetadata the csv record metadata
   * @throws SQLException the SQL exception
   */
  void save(CSVRecordMetadata csvRecordMetadata) throws SQLException;

  /**
   * Update.
   *
   * @param csvRecordMetadata the csv record metadata
   * @throws SQLException the SQL exception
   */
  void update(CSVRecordMetadata csvRecordMetadata) throws SQLException;

  /**
   * Delete all.
   *
   * @param ids the ids
   * @param tableName the table name
   * @throws SQLException the SQL exception
   */
  void deleteAll(List<String> ids, String tableName) throws SQLException;

  /**
   * Delete.
   *
   * @param id the id
   * @param tableName the table name
   * @throws SQLException the SQL exception
   */
  void delete(String id, String tableName) throws SQLException;

  /**
   * Gets the data source.
   *
   * @return the data source
   */
  DataSource getDataSource();

  /**
   * Gets the db metadata util.
   *
   * @return the db metadata util
   */
  DBMetadataUtil getDbMetadataUtil();
}
