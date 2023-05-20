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
package com.codbex.kronos.hdbti.dao;

import com.codbex.kronos.hdbti.api.ICSVRecordDao;
import com.codbex.kronos.hdbti.utils.CSVRecordMetadata;
import com.codbex.kronos.utils.CommonsDBUtils;

import org.apache.commons.csv.CSVRecord;
import org.apache.cxf.common.util.StringUtils;
import org.eclipse.dirigible.api.v3.core.ConsoleFacade;
import org.eclipse.dirigible.commons.api.helpers.DateTimeUtils;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.ds.model.transfer.TableColumn;
import org.eclipse.dirigible.database.ds.model.transfer.TableMetadataHelper;
import org.eclipse.dirigible.database.persistence.PersistenceException;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.records.DeleteBuilder;
import org.eclipse.dirigible.database.sql.builders.records.InsertBuilder;
import org.eclipse.dirigible.database.sql.builders.records.UpdateBuilder;
import org.eclipse.dirigible.engine.odata2.transformers.DBMetadataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * The Class CSVRecordDao.
 */
public class CSVRecordDao implements ICSVRecordDao {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(CSVRecordDao.class);

    /** The data source. */
    @Override
    public DataSource getDataSource() { 
  	  	return (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);
    }

    /** The db metadata util. */
    private DBMetadataUtil dbMetadataUtil = new DBMetadataUtil();

    /**
     * Save.
     *
     * @param csvRecordMetadata the csv record metadata
     * @throws SQLException the SQL exception
     */
    @Override
    public void save(CSVRecordMetadata csvRecordMetadata) throws SQLException {
        String tableName = csvRecordMetadata.getTableMetadataModel().getTableName();
        try (Connection connection = getDataSource().getConnection()) {
            List<TableColumn> availableTableColumns = TableMetadataHelper.getColumns(connection, tableName, csvRecordMetadata.getTableMetadataModel().getSchemaName());
            for (TableColumn availableTableColumn : availableTableColumns) {
                logger.debug("    {}: {}", availableTableColumn.getName(), availableTableColumn.getType());
            }

            InsertBuilder insertBuilder = new InsertBuilder(SqlFactory.deriveDialect(connection));
            insertBuilder.into(tableName);
            CSVRecord csvRecord = csvRecordMetadata.getCsvRecord();
            for (int i = 0; i < csvRecord.size(); i++) {
                String columnName = availableTableColumns.get(i).getName();
                insertBuilder.column("\"" + columnName + "\"").value("?");
            }

            try (PreparedStatement statement = connection.prepareStatement(insertBuilder.generate())) {
                executeInsertPreparedStatement(csvRecordMetadata, availableTableColumns, statement);
                ConsoleFacade.log("Table row with id: " + csvRecord.get(0) + " was CREATED successfully in " + tableName);
            }
        }
    }

    /**
     * Update.
     *
     * @param csvRecordMetadata the csv record metadata
     * @throws SQLException the SQL exception
     */
    @Override
    public void update(CSVRecordMetadata csvRecordMetadata) throws SQLException {
        String tableName = csvRecordMetadata.getTableMetadataModel().getTableName();
        try (Connection connection = getDataSource().getConnection()) {
            List<TableColumn> availableTableColumns = TableMetadataHelper.getColumns(connection, tableName, csvRecordMetadata.getTableMetadataModel().getSchemaName());
            UpdateBuilder updateBuilder = new UpdateBuilder(SqlFactory.deriveDialect(connection));
            updateBuilder.table(tableName);
            for (TableColumn tableColumn : availableTableColumns) {
                logger.debug("    {}: {}", tableColumn.getName(), tableColumn.getType());
            }

            CSVRecord csvRecord = csvRecordMetadata.getCsvRecord();
            for (int i = 0; i < csvRecord.size(); i++) {
                String columnName = availableTableColumns.get(i).getName();
                if (columnName.equals(csvRecordMetadata.getPkColumnName())) {
                    continue;
                }

                updateBuilder.set("\"" + columnName + "\"", "?");
            }

            if (csvRecordMetadata.getHeaderNames().size() > 0) {
                updateBuilder.where(String.format("%s = ?", csvRecordMetadata.getPkColumnName()));
            } else {
                updateBuilder.where(String.format("%s = ?", availableTableColumns.get(0).getName()));
            }

            try (PreparedStatement statement = connection.prepareStatement(updateBuilder.generate())) {
                executeUpdatePreparedStatement(csvRecordMetadata, availableTableColumns, statement);
                logger.info(format("Table row with id: %s was UPDATED successfully in %s.", csvRecord.get(0), tableName));
            }
        }
    }

    /**
     * Delete all.
     *
     * @param ids the ids
     * @param tableName the table name
     * @throws SQLException the SQL exception
     */
    @Override
    public void deleteAll(List<String> ids, String tableName) throws SQLException {
        if (ids.isEmpty()) {
            return;
        }

        try (Connection connection = getDataSource().getConnection()) {
            PersistenceTableModel tableMetadata = dbMetadataUtil.getTableMetadata(tableName,
                    CommonsDBUtils.getTableSchema(getDataSource(), tableName));
            if (null == tableMetadata) {
                logger.debug("Table with name [{}] was not found.", tableName);
                return;
            }
            String pkColumnName = tableMetadata.getColumns().get(0).getName();
            DeleteBuilder deleteBuilder = new DeleteBuilder(SqlFactory.deriveDialect(connection));
            deleteBuilder.from(tableName).where(String.format("%s IN (%s)", pkColumnName, String.join(",", ids)));
            try (PreparedStatement statement = connection.prepareStatement(deleteBuilder.build())) {
                statement.executeUpdate();
                logger.info(String.format("Entities with Row Ids: %s from table: %s", String.join(", ", ids), tableName));
            }
        }
    }

    /**
     * Delete.
     *
     * @param id the id
     * @param tableName the table name
     * @throws SQLException the SQL exception
     */
    @Override
    public void delete(String id, String tableName) throws SQLException {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(tableName)) {
            return;
        }

        try (Connection connection = getDataSource().getConnection()) {
            PersistenceTableModel tableMetadata = dbMetadataUtil.getTableMetadata(tableName,
                    CommonsDBUtils.getTableSchema(getDataSource(), tableName));
            if (null == tableMetadata) {
                logger.debug("Table with name [{}] was not found.", tableName);
                return;
            }
            String pkColumnName = tableMetadata.getColumns().get(0).getName();
            DeleteBuilder deleteBuilder = new DeleteBuilder(SqlFactory.deriveDialect(connection));
            deleteBuilder.from(tableName).where(String.format("%s='%s'", pkColumnName, id));
            try (PreparedStatement statement = connection.prepareStatement(deleteBuilder.build())) {
                statement.executeUpdate();
                logger.info(String.format("Entity with Row Id: %s from table: %s", id, tableName));
            }
        }
    }

    /**
     * Gets the db metadata util.
     *
     * @return the db metadata util
     */
    @Override
    public DBMetadataUtil getDbMetadataUtil() {
        return dbMetadataUtil;
    }

    /**
     * Execute insert prepared statement.
     *
     * @param csvRecordMetadata the csv record metadata
     * @param tableColumns the table columns
     * @param statement the statement
     * @throws SQLException the SQL exception
     */
    private void executeInsertPreparedStatement(CSVRecordMetadata csvRecordMetadata, List<TableColumn> tableColumns, PreparedStatement statement) throws SQLException {
        if (csvRecordMetadata.getHeaderNames().size() > 0) {
            insertCsvWithHeader(csvRecordMetadata, tableColumns, statement);
        } else {
            insertCsvWithoutHeader(csvRecordMetadata, tableColumns, statement);
        }

        statement.execute();
    }

    /**
     * Insert csv with header.
     *
     * @param csvRecordMetadata the csv record metadata
     * @param tableColumns the table columns
     * @param statement the statement
     * @throws SQLException the SQL exception
     */
    private void insertCsvWithHeader(CSVRecordMetadata csvRecordMetadata, List<TableColumn> tableColumns, PreparedStatement statement) throws SQLException {

        for (int i = 0; i < tableColumns.size(); i++) {
            String columnName = tableColumns.get(i).getName();
            int columnType = tableColumns.get(i).getType();
            String value = csvRecordMetadata.getCsvValueForColumn(columnName);
            setPreparedStatementValue(csvRecordMetadata.isDistinguishEmptyFromNull(), statement, i + 1, value, columnType);
        }
    }

    /**
     * Insert csv without header.
     *
     * @param csvRecordMetadata the csv record metadata
     * @param tableColumns the table columns
     * @param statement the statement
     * @throws SQLException the SQL exception
     */
    private void insertCsvWithoutHeader(CSVRecordMetadata csvRecordMetadata, List<TableColumn> tableColumns, PreparedStatement statement) throws SQLException {
        for (int i = 0; i < csvRecordMetadata.getCsvRecord().size(); i++) {
            String value = csvRecordMetadata.getCsvRecord().get(i);
            int columnType = tableColumns.get(i).getType();

            setPreparedStatementValue(csvRecordMetadata.isDistinguishEmptyFromNull(), statement, i + 1, value, columnType);
        }
    }

    /**
     * Execute update prepared statement.
     *
     * @param csvRecordMetadata the csv record metadata
     * @param tableColumns the table columns
     * @param statement the statement
     * @throws SQLException the SQL exception
     */
    private void executeUpdatePreparedStatement(CSVRecordMetadata csvRecordMetadata, List<TableColumn> tableColumns, PreparedStatement statement) throws SQLException {
        if (csvRecordMetadata.getHeaderNames().size() > 0) {
            updateCsvWithHeader(csvRecordMetadata, tableColumns, statement);
        } else {
            updateCsvWithoutHeader(csvRecordMetadata, tableColumns, statement);
        }

        statement.execute();
    }

    /**
     * Update csv with header.
     *
     * @param csvRecordMetadata the csv record metadata
     * @param tableColumns the table columns
     * @param statement the statement
     * @throws SQLException the SQL exception
     */
    private void updateCsvWithHeader(CSVRecordMetadata csvRecordMetadata, List<TableColumn> tableColumns, PreparedStatement statement) throws SQLException {
        CSVRecord csvRecord = csvRecordMetadata.getCsvRecord();

        for (int i = 1; i < tableColumns.size(); i++) {
            String columnName = tableColumns.get(i).getName();
            String value = csvRecordMetadata.getCsvValueForColumn(columnName);

            int columnType = tableColumns.get(i).getType();

            setPreparedStatementValue(csvRecordMetadata.isDistinguishEmptyFromNull(), statement, i, value,
                    columnType);
        }

        int pkColumnType = tableColumns.get(0).getType();
        int lastStatementPlaceholderIndex = csvRecord.size();

        setValue(statement, lastStatementPlaceholderIndex, pkColumnType, csvRecordMetadata.getCsvRecordPkValue());
    }

    /**
     * Update csv without header.
     *
     * @param csvRecordMetadata the csv record metadata
     * @param tableColumns the table columns
     * @param statement the statement
     * @throws SQLException the SQL exception
     */
    private void updateCsvWithoutHeader(CSVRecordMetadata csvRecordMetadata, List<TableColumn> tableColumns, PreparedStatement statement) throws SQLException {
        CSVRecord csvRecord = csvRecordMetadata.getCsvRecord();
        for (int i = 1; i < csvRecord.size(); i++) {
            String value = csvRecord.get(i);
            int columnType = tableColumns.get(i).getType();
            setPreparedStatementValue(csvRecordMetadata.isDistinguishEmptyFromNull(), statement, i, value, columnType);
        }

        int pkColumnType = tableColumns.get(0).getType();
        int lastStatementPlaceholderIndex = csvRecord.size();
        setValue(statement, lastStatementPlaceholderIndex, pkColumnType, csvRecord.get(0));
    }

    /**
     * Sets the prepared statement value.
     *
     * @param distinguishEmptyFromNull the distinguish empty from null
     * @param statement the statement
     * @param i the i
     * @param value the value
     * @param columnType the column type
     * @throws SQLException the SQL exception
     */
    private void setPreparedStatementValue(Boolean distinguishEmptyFromNull, PreparedStatement statement, int i, String value, int columnType)
            throws SQLException {
        if (!StringUtils.isEmpty(value)) {
            setValue(statement, i, columnType, value);
        } else {
            if (distinguishEmptyFromNull) {
                setValue(statement, i, columnType, "");
            } else {
                setValue(statement, i, columnType, value);
            }
        }
    }

    /**
     * Sets the value.
     *
     * @param preparedStatement the prepared statement
     * @param i                 the i
     * @param dataType          the data type
     * @param value             the value
     * @throws SQLException the SQL exception
     */
    protected void setValue(PreparedStatement preparedStatement, int i, int dataType, String value)
            throws SQLException {
        logger.trace("setValue -> i: " + i + ", dataType: " + dataType + ", value: " + value);

        if (value == null || (value.isEmpty() && (dataType == Types.DATE || dataType == Types.TIME || dataType == Types.TIMESTAMP))) {
            preparedStatement.setNull(i, dataType);
        } else if (Types.VARCHAR == dataType) {
            preparedStatement.setString(i, sanitize(value));
        } else if (Types.NVARCHAR == dataType) {
            preparedStatement.setString(i, sanitize(value));
        } else if (Types.CHAR == dataType) {
            preparedStatement.setString(i, sanitize(value));
        } else if (Types.DATE == dataType) {
            preparedStatement.setDate(i, DateTimeUtils.parseDate(value));
        } else if (Types.TIME == dataType) {
            preparedStatement.setTime(i, DateTimeUtils.parseTime(value));
        } else if (Types.TIMESTAMP == dataType) {
            preparedStatement.setTimestamp(i, DateTimeUtils.parseDateTime(value));
        } else if (Types.INTEGER == dataType) {
            value = numberize(value);
            preparedStatement.setInt(i, Integer.parseInt(value));
        } else if (Types.TINYINT == dataType) {
            value = numberize(value);
            preparedStatement.setByte(i, Byte.parseByte(value));
        } else if (Types.SMALLINT == dataType) {
            value = numberize(value);
            preparedStatement.setShort(i, Short.parseShort(value));
        } else if (Types.BIGINT == dataType) {
            value = numberize(value);
            preparedStatement.setLong(i, new BigInteger(value).longValueExact());
        } else if (Types.REAL == dataType) {
            value = numberize(value);
            preparedStatement.setFloat(i, Float.parseFloat(value));
        } else if (Types.DOUBLE == dataType) {
            value = numberize(value);
            preparedStatement.setDouble(i, Double.parseDouble(value));
        } else if (Types.BOOLEAN == dataType) {
            preparedStatement.setBoolean(i, Boolean.parseBoolean(value));
        } else if (Types.DECIMAL == dataType) {
            value = numberize(value);
            preparedStatement.setBigDecimal(i, new BigDecimal(value));
        } else if (Types.NCLOB == dataType) {
            preparedStatement.setString(i, sanitize(value));
        } else {
            throw new PersistenceException(format("Database type [{0}] not supported", JDBCType.valueOf(dataType).getName()));
        }
    }

    /**
     * Sanitize.
     *
     * @param value the value
     * @return the string
     */
    private String sanitize(String value) {
        if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }
        if (value != null && value.startsWith("'") && value.endsWith("'")) {
            value = value.substring(1, value.length() - 1);
        }
        return value != null ? value.trim() : null;
    }

    /**
     * Numberize.
     *
     * @param value the value
     * @return the string
     */
    private String numberize(String value) {
        if (StringUtils.isEmpty(value)) {
            value = "0";
        }
        return value;
    }
}
