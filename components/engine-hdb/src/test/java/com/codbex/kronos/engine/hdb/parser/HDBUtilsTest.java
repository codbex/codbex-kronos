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
package com.codbex.kronos.engine.hdb.parser;

import static org.assertj.core.api.Assertions.assertThat;

import com.codbex.kronos.engine.hdb.domain.HDBTableColumn;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintPrimaryKey;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class HDBUtilsTest {

    private static final String MULTILINE_CREATE_TABLE_SQL = """
            CREATE COLUMN TABLE "MY_SCHEMA"."MY_TABLE" (
                "id" INTEGER NOT NULL,
                "column1" INTEGER NOT NULL,
                "Column2" INTEGER,
                column3 INTEGER,
                "column4" NVARCHAR(30),
                "column5" DECIMAL(20, 2),
                "column6" DECIMAL(20, 2),
                "column7" INTEGER,
                "column8" DECIMAL(20, 2),
                "column9" DECIMAL(20, 2),
                "column10" NVARCHAR(256),
                "cOlUmN11" NVARCHAR(256),
                "column12" NVARCHAR(256),
                PRIMARY KEY ("idx"),
                FOREIGN KEY ("Column2") REFERENCES "MY_SCHEMA_2"."com.test:app.Column2"("idx") ON DELETE RESTRICT,
                FOREIGN KEY ("column3") REFERENCES "MY_SCHEMA_2"."com.test:app.column3"("idx") ON DELETE RESTRICT
            )
            """;

    @Test
    void testExtractPrimayKeyColumns_singlePK() {
        testExtractPrimaryKeyColumns(MULTILINE_CREATE_TABLE_SQL, "idx");
    }

    @Test
    void testExtractPrimayKeyColumns_TwoPK() {
        String sql = """
                CREATE COLUMN TABLE "MY_SCHEMA"."MY_TABLE" (
                    "pk1" INTEGER NOT NULL,
                    "pk2" INTEGER NOT NULL,
                    "column1" INTEGER NOT NULL,
                    "Column2" INTEGER,
                    column3 INTEGER,
                    "column4" NVARCHAR(30),
                    "column5" DECIMAL(20, 2),
                    "column6" DECIMAL(20, 2),
                    "column7" INTEGER,
                    "column8" DECIMAL(20, 2),
                    "column9" DECIMAL(20, 2),
                    "column10" NVARCHAR(256),
                    "cOlUmN11" NVARCHAR(256),
                    "column12" NVARCHAR(256),
                    PRIMARY KEY ("pk1","pk2"),
                    FOREIGN KEY ("Column2") REFERENCES "MY_SCHEMA_2"."com.test:app.Column2"("idx") ON DELETE RESTRICT,
                    FOREIGN KEY ("column3") REFERENCES "MY_SCHEMA_2"."com.test:app.column3"("idx") ON DELETE RESTRICT
                )
                """;
        testExtractPrimaryKeyColumns(sql, "pk1", "pk2");
    }

    private void testExtractPrimaryKeyColumns(String sql, String... expectedPrimaryKeyColumns) {
        HDBTableConstraintPrimaryKey primaryKeyConstraint = HDBUtils.extractPrimayKeyColumns(sql);

        assertThat(primaryKeyConstraint).isNotNull();

        String[] columns = primaryKeyConstraint.getColumns();
        assertThat(columns).isNotNull();
        assertThat(columns).containsExactlyInAnyOrder(expectedPrimaryKeyColumns);
    }

    @Test
    void testExtractColumnsWithMultilineContent() {
        testExtractColumns(MULTILINE_CREATE_TABLE_SQL);
    }

    @Test
    void testExtractColumnsWithSingleLineContent() {
        String createSQL =
                "CREATE COLUMN TABLE \"MY_SCHEMA\".\"MY_TABLE\" ( \"id\" INTEGER NOT NULL, \"column1\" INTEGER NOT NULL, \"Column2\" INTEGER, column3 INTEGER, \"column4\" NVARCHAR(30), \"column5\" DECIMAL(20, 2), \"column6\" DECIMAL(20, 2), \"column7\" INTEGER, \"column8\" DECIMAL(20, 2), \"column9\" DECIMAL(20, 2), \"column10\" NVARCHAR(256), \"cOlUmN11\" NVARCHAR(256), \"column12\" NVARCHAR(256), PRIMARY KEY (\"idx\"), FOREIGN KEY (\"Column2\") REFERENCES \"MY_SCHEMA_2\".\"com.test:app.Column2\"(\"idx\") ON DELETE RESTRICT, FOREIGN KEY (\"column3\") REFERENCES \"MY_SCHEMA_2\".\"com.test:app.column3\"(\"idx\") ON DELETE RESTRICT )\n";
        testExtractColumns(createSQL);
    }

    private void testExtractColumns(String sql) {

        List<HDBTableColumn> expectedColumns = List.of(//
                createHDBTableColumn("id", "INTEGER"), createHDBTableColumn("column1", "INTEGER"),
                createHDBTableColumn("Column2", "INTEGER"), createHDBTableColumn("column3", "INTEGER"),
                createHDBTableColumn("column4", "NVARCHAR", 30), createHDBTableColumn("column5", "DECIMAL"),
                createHDBTableColumn("column6", "DECIMAL"), createHDBTableColumn("column7", "INTEGER"),
                createHDBTableColumn("column8", "DECIMAL"), createHDBTableColumn("column9", "DECIMAL"),
                createHDBTableColumn("column10", "NVARCHAR", 256), createHDBTableColumn("cOlUmN11", "NVARCHAR", 256),
                createHDBTableColumn("column12", "NVARCHAR", 256));

        List<HDBTableColumn> extractedColumns = HDBUtils.extractColumns(sql);

        assertThat(extractedColumns).hasSize(expectedColumns.size());
        expectedColumns.forEach(
                expectedColumn -> assertThat(containsElement(extractedColumns, expectedColumn))
                                                                                               .withFailMessage("Failed check for column "
                                                                                                       + expectedColumn.getName())
                                                                                               .isTrue());
    }

    private boolean containsElement(List<HDBTableColumn> actualColumns, HDBTableColumn expectedColumn) {
        return actualColumns.stream()
                            .filter(c -> Objects.equals(expectedColumn.getName(), c.getName())
                                    && Objects.equals(expectedColumn.getType(), c.getType())
                                    && Objects.equals(expectedColumn.getLength(), c.getLength()))
                            .findFirst()
                            .isPresent();
    }

    private HDBTableColumn createHDBTableColumn(String name, String type) {
        return createHDBTableColumn(name, type, null);
    }

    private HDBTableColumn createHDBTableColumn(String name, String type, Integer length) {
        HDBTableColumn column = new HDBTableColumn();
        column.setName(name);
        column.setType(type);
        column.setLength(null != length ? length.toString() : null);

        return column;
    }
}
