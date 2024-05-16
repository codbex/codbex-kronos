/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.processors;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.persistence.utils.DatabaseMetadataUtil;
import org.eclipse.dirigible.database.sql.DataType;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.TableStatements;
import org.eclipse.dirigible.database.sql.builders.table.AbstractTableBuilder;
import org.eclipse.dirigible.database.sql.builders.table.CreateTableBuilder;

import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableColumn;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintCheck;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintForeignKey;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintUnique;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraints;
import com.codbex.kronos.engine.hdb.domain.HDBTableIndex;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;

/**
 * The Class HDBTableBuilder.
 */
public class HDBTableBuilder {

    /** The case sensitive. */
    private boolean caseSensitive =
            Boolean.parseBoolean(Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true"));

    /**
     * Builds the.
     *
     * @param tableModel the model
     * @return the table
     */
    public TableStatements build(Connection connection, HDBTable tableModel) {
        String tableName = HDBUtils.escapeArtifactName(tableModel.getName(), tableModel.getSchema());

        CreateTableBuilder sqlTableBuilder = createTableBuilder(connection, tableName, tableModel.getTableType());

        addTableColumnToBuilder(sqlTableBuilder, tableModel);
        addTableConstraintsToBuilder(sqlTableBuilder, tableModel);
        addTableIndicesToBuilder(sqlTableBuilder, tableModel);

        return sqlTableBuilder.buildTable();
    }

    /**
     * Creates the table builder.
     *
     * @param tableName the table name
     * @param tableType the table type
     * @return the hana create table builder
     */
    private CreateTableBuilder createTableBuilder(Connection connection, String tableName, String tableType) {
        if (null != tableType) {
            return SqlFactory.deriveDialect(connection)
                             .create()
                             .table(tableName, tableType);
        }
        return SqlFactory.deriveDialect(connection)
                         .create()
                         .table(tableName);
    }

    /**
     * Adds the table indices to builder.
     *
     * @param sqlTableBuilder the sql table builder
     * @param tableModel the table model
     */
    private void addTableIndicesToBuilder(CreateTableBuilder sqlTableBuilder, HDBTable tableModel) {
        List<HDBTableIndex> indexes = tableModel.getIndexes();
        for (HDBTableIndex indexModel : indexes) {
            String name = caseSensitive ? HDBUtils.escapeArtifactName(indexModel.getName()) : indexModel.getName();

            sqlTableBuilder.index(name, indexModel.isUnique(), indexModel.getOrder(), indexModel.getType(),
                    new HashSet<String>(Arrays.asList(indexModel.getColumns())));
        }
    }

    /**
     * Adds the table column to builder.
     *
     * @param sqlTableBuilder the sql table builder
     * @param tableModel the table model
     */
    private void addTableColumnToBuilder(CreateTableBuilder sqlTableBuilder, HDBTable tableModel) {
        List<HDBTableColumn> columns = tableModel.getColumns();
        for (HDBTableColumn columnModel : columns) {
            String name = caseSensitive ? HDBUtils.escapeArtifactName(columnModel.getName()) : columnModel.getName();
            DataType type = DataType.valueOf(columnModel.getType());

            if (!columnModel.isFuzzySearchIndex()) {
                sqlTableBuilder.column(name, type, columnModel.isPrimaryKey(), columnModel.isNullable(), columnModel.isUnique(),
                        getColumnModelArgs(columnModel));
            } else {
                sqlTableBuilder.column(name, type, columnModel.isPrimaryKey(), columnModel.isNullable(), columnModel.isUnique(), true,
                        getColumnModelArgs(columnModel));
            }
        }
    }

    /**
     * Adds the table constraints to builder.
     *
     * @param sqlTableBuilder the sql table builder
     * @param tableModel the table model
     */
    private void addTableConstraintsToBuilder(CreateTableBuilder sqlTableBuilder, HDBTable tableModel) {
        HDBTableConstraints constraintsModel = tableModel.getConstraints();
        if (Objects.nonNull(constraintsModel)) {
            if (Objects.nonNull(constraintsModel.getPrimaryKey())) {
                sqlTableBuilder.primaryKey(getEscapedColumns(constraintsModel.getPrimaryKey()
                                                                             .getColumns()));
            }

            addTableForeignKeysToBuilder(sqlTableBuilder, tableModel);
            addUniqueIndicesToBuilder(sqlTableBuilder, tableModel);

            List<HDBTableConstraintCheck> checks = constraintsModel.getChecks();
            if (Objects.nonNull(checks)) {
                for (HDBTableConstraintCheck check : checks) {
                    String checkName = check.getName();
                    if (caseSensitive) {
                        checkName = caseSensitive ? HDBUtils.escapeArtifactName(checkName) : checkName;
                    }
                    sqlTableBuilder.check(checkName, check.getExpression());
                }
            }
        }
    }

    /**
     * Gets the column model args.
     *
     * @param columnModel the column model
     * @return the column model args
     */
    private String getColumnModelArgs(HDBTableColumn columnModel) {
        DataType type = DataType.valueOf(columnModel.getType());
        String args = "";
        if (columnModel.getLength() != null) {
            if (type.equals(DataType.VARCHAR) || type.equals(DataType.CHAR) || columnModel.getType()
                                                                                          .equalsIgnoreCase("NVARCHAR")
                    || columnModel.getType()
                                  .equalsIgnoreCase("ALPHANUM")
                    || columnModel.getType()
                                  .equalsIgnoreCase("SHORTTEXT")) {
                args = ISqlKeywords.OPEN + columnModel.getLength() + ISqlKeywords.CLOSE;
            }
        } else if ((columnModel.getPrecision() != null) && (columnModel.getScale() != null)) {
            if (type.equals(DataType.DECIMAL)) {
                args = ISqlKeywords.OPEN + columnModel.getPrecision() + "," + columnModel.getScale() + ISqlKeywords.CLOSE;
            }
        }
        if (columnModel.getDefaultValue() != null) {

            if ((type.equals(DataType.VARCHAR) || type.equals(DataType.CHAR) || type.equals(DataType.NVARCHAR) || columnModel.getType()
                                                                                                                             .equalsIgnoreCase(
                                                                                                                                     "ALPHANUM")
                    || columnModel.getType()
                                  .equalsIgnoreCase("SHORTTEXT")
                    || (columnModel.getType()
                                   .equalsIgnoreCase("TIMESTAMP")
                            && !columnModel.isDefaultValueDateTimeFunction()))) {
                args += " DEFAULT '" + columnModel.getDefaultValue() + "' ";
            } else {
                args += " DEFAULT " + columnModel.getDefaultValue() + " ";
            }

        }
        if (columnModel.isCalculatedColumn()) {
            args += " AS " + columnModel.getStatement();
        }
        return args;
    }

    /**
     * Gets the escaped columns.
     *
     * @param columns the columns
     * @return the escaped columns
     */
    private String[] getEscapedColumns(String[] columns) {
        String[] primaryKeyColumns = new String[columns.length];
        int i = 0;
        for (String column : columns) {
            if (caseSensitive) {
                primaryKeyColumns[i++] = HDBUtils.escapeArtifactName(column);
            } else {
                primaryKeyColumns[i++] = column;
            }
        }

        return primaryKeyColumns;
    }

    /**
     * Adds the table foreign keys to builder.
     *
     * @param sqlTableBuilder the sql table builder
     * @param tableModel the table model
     */
    private void addTableForeignKeysToBuilder(CreateTableBuilder sqlTableBuilder, HDBTable tableModel) {
        List<HDBTableConstraintForeignKey> foreignKeys = tableModel.getConstraints()
                                                                   .getForeignKeys();
        if (Objects.nonNull(foreignKeys)) {
            for (HDBTableConstraintForeignKey foreignKey : foreignKeys) {
                String foreignKeyName = foreignKey.getName();
                String foreignKeyReferencedTable = foreignKey.getReferencedTable();
                if (caseSensitive) {
                    foreignKeyName = HDBUtils.escapeArtifactName(foreignKeyName);
                    foreignKeyReferencedTable = HDBUtils.escapeArtifactName(foreignKeyReferencedTable);
                }
                String[] foreignKeyColumns = this.getEscapedColumns(foreignKey.getColumns());

                String[] foreignKeyReferencedColumns = this.getEscapedColumns(foreignKey.getReferencedColumns());

                sqlTableBuilder.foreignKey(foreignKeyName, foreignKeyColumns, foreignKeyReferencedTable,
                        HDBUtils.escapeArtifactName(foreignKey.getReferencedSchema()), foreignKeyReferencedColumns);
            }
        }
    }

    /**
     * Adds the unique indices to builder.
     *
     * @param builder the builder
     * @param tableModel the table model
     */
    protected void addUniqueIndicesToBuilder(AbstractTableBuilder builder, HDBTable tableModel) {
        List<HDBTableConstraintUnique> uniqueIndices = tableModel.getConstraints()
                                                                 .getUniqueIndexes();
        if (Objects.nonNull(uniqueIndices)) {
            for (HDBTableConstraintUnique uniqueIndex : uniqueIndices) {
                String uniqueIndexName = uniqueIndex.getName();
                if (this.caseSensitive) {
                    uniqueIndexName = HDBUtils.escapeArtifactName(uniqueIndexName);
                }
                String[] uniqueIndexColumns = this.getEscapedColumns(uniqueIndex.getColumns());
                String indexOrder = uniqueIndex.getOrder();
                String indexType = uniqueIndex.getIndexType();
                builder.unique(uniqueIndexName, uniqueIndexColumns, indexType, indexOrder);
            }
        }
    }
}
