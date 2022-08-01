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

import com.codbex.kronos.hdbti.utils.CSVRecordMetadata;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.eclipse.dirigible.engine.odata2.transformers.DBMetadataUtil;

public interface ICSVRecordDao {

  void save(CSVRecordMetadata csvRecordMetadata) throws SQLException;

  void update(CSVRecordMetadata csvRecordMetadata) throws SQLException;

  void deleteAll(List<String> ids, String tableName) throws SQLException;

  void delete(String id, String tableName) throws SQLException;

  DataSource getDataSource();

  DBMetadataUtil getDbMetadataUtil();
}
