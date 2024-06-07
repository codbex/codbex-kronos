package com.codbex.kronos.engine.hdb.parser;

import static org.assertj.core.api.Assertions.assertThat;

import com.codbex.kronos.engine.hdb.domain.HDBTableColumn;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class HDBUtilsTest {

    @Test
    void testExtractColumnsWithMultilineContent() {
        String createSQL = """
                CREATE COLUMN TABLE "MY_SCHEMA"."MY_TABLE" (\s
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
                    FOREIGN KEY ("Column2") REFERENCES "AF477A06D"."com.apotest.jpk.backend.model::jpk.Column2"("idx") ON DELETE RESTRICT,
                    FOREIGN KEY ("column3") REFERENCES "AF477A06D"."com.apotest.jpk.backend.model::jpk.column3"("idx") ON DELETE RESTRICT
                )
                """;
        testExtractColumns(createSQL);
    }

    @Test
    void testExtractColumnsWithSingleLineContent() {
        String createSQL =
            "CREATE COLUMN TABLE \"MY_SCHEMA\".\"MY_TABLE\" ( \"id\" INTEGER NOT NULL, \"column1\" INTEGER NOT NULL, \"Column2\" INTEGER, column3 INTEGER, \"column4\" NVARCHAR(30), \"column5\" DECIMAL(20, 2), \"column6\" DECIMAL(20, 2), \"column7\" INTEGER, \"column8\" DECIMAL(20, 2), \"column9\" DECIMAL(20, 2), \"column10\" NVARCHAR(256), \"cOlUmN11\" NVARCHAR(256), \"column12\" NVARCHAR(256), PRIMARY KEY (\"idx\"), FOREIGN KEY (\"Column2\") REFERENCES \"AF477A06D\".\"com.apotest.jpk.backend.model::jpk.Column2\"(\"idx\") ON DELETE RESTRICT, FOREIGN KEY (\"column3\") REFERENCES \"AF477A06D\".\"com.apotest.jpk.backend.model::jpk.column3\"(\"idx\") ON DELETE RESTRICT )\n";
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
      expectedColumns.forEach(expectedColumn -> assertThat(containsElement(extractedColumns, expectedColumn)).withFailMessage(
          "Failed check for column " + expectedColumn.getName()).isTrue());
    }

    private boolean containsElement(List<HDBTableColumn> actualColumns, HDBTableColumn expectedColumn) {
        return actualColumns.stream()
                            .filter(c -> Objects.equals(expectedColumn.getName(), c.getName())
                                && Objects.equals(expectedColumn.getType(), c.getType()) && Objects.equals(expectedColumn.getLength(),
                                c.getLength()))
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
