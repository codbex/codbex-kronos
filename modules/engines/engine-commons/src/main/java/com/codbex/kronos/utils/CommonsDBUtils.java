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
package com.codbex.kronos.utils;

import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.database.sql.ISqlKeywords;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Class CommonsDBUtils.
 */
public class CommonsDBUtils {

	/** The Constant SQL_GET_SYNONYM. */
	private static final String SQL_GET_SYNONYM = "SELECT * FROM \"SYS\".\"SYNONYMS\" WHERE \"SYNONYM_NAME\" = ? AND \"SCHEMA_NAME\" = ?";

    /** The Constant RESULT_TABLE_SCHEM. */
    private static final String RESULT_TABLE_SCHEM = "TABLE_SCHEM";
    
    /** The Constant RESULT_OBJECT_NAME. */
    private static final String RESULT_OBJECT_NAME = "OBJECT_NAME";
	
	/** The Constant RESULT_OBJECT_SCHEMA. */
	private static final String RESULT_OBJECT_SCHEMA = "OBJECT_SCHEMA";
	
	/** The Constant RESULT_OBJECT_TYPE. */
	private static final String RESULT_OBJECT_TYPE = "OBJECT_TYPE";

	/**
	 * Gets the table schema.
	 *
	 * @param dataSource the data source
	 * @param tableName the table name
	 * @return the table schema
	 * @throws SQLException the SQL exception
	 */
	public static String getTableSchema(DataSource dataSource, String tableName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rs = databaseMetaData.getTables(connection.getCatalog(), null, tableName, new String[]{ISqlKeywords.KEYWORD_TABLE});
            if (rs.next()) {
                return rs.getString(RESULT_TABLE_SCHEM);
            }
            return null;
        }
    }

  /**
   * Gets the synonym target object metadata.
   *
   * @param dataSource the data source
   * @param synonymName the synonym name
   * @param schemaName the schema name
   * @return the synonym target object metadata
   * @throws SQLException the SQL exception
   */
  public static PersistenceTableModel getSynonymTargetObjectMetadata(DataSource dataSource, String synonymName, String schemaName) throws SQLException {
    PersistenceTableModel tableMetadata = new PersistenceTableModel();

    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement prepareStatement = connection.prepareStatement(SQL_GET_SYNONYM)) {
    	  prepareStatement.setString(1, synonymName);
    	  prepareStatement.setString(2, schemaName);
    	  
    	  try (ResultSet resultSet = prepareStatement.executeQuery()) {
    		  if (resultSet.next()) {
    			  tableMetadata.setTableName(resultSet.getString(RESULT_OBJECT_NAME));
    			  tableMetadata.setSchemaName(resultSet.getString(RESULT_OBJECT_SCHEMA));
    			  tableMetadata.setTableType(resultSet.getString(RESULT_OBJECT_TYPE));
    		  }
    	  }
      }
    }

    return tableMetadata;
  }
}

