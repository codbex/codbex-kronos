/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.parser.hdbtable.custom;

import com.codbex.kronos.parser.hdbtable.core.HDBTableBaseVisitor;
import com.codbex.kronos.parser.hdbtable.core.HDBTableParser.*;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableDuplicatePropertyException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.antlr.v4.runtime.tree.ParseTree;

import static com.codbex.kronos.parser.hdbtable.constants.HDBTablePropertiesConstants.*;

import java.util.HashSet;
import java.util.List;

/**
 * The Class HDBTableCoreVisitor.
 */
public class HDBTableDefinitionVisitor extends HDBTableBaseVisitor<JsonElement> {

  /**
   * Visit hdbtable definition.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitHdbTableDefinition(HdbTableDefinitionContext ctx) {
    JsonObject hdbtableObject = new JsonObject();
    HashSet<String> uniqueTableProperties = new HashSet<>();
    List<ParseTree> ctxList = ctx.children;

    for (ParseTree tree : ctxList) {
      if (tree.getChild(0) instanceof SchemaNamePropContext) {
        checkPropertyDeclarationUniqueness(HDBTABLE_PROPERTY_SCHEMA_NAME, uniqueTableProperties);
        hdbtableObject.add(HDBTABLE_PROPERTY_SCHEMA_NAME, visitSchemaNameProp((SchemaNamePropContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof TableTypePropContext) {
        checkPropertyDeclarationUniqueness(HDBTABLE_PROPERTY_TABLE_TYPE, uniqueTableProperties);
        hdbtableObject.add(HDBTABLE_PROPERTY_TABLE_TYPE, visitTableTypeProp((TableTypePropContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof TableColumnsPropContext) {
        checkPropertyDeclarationUniqueness(HDBTABLE_PROPERTY_COLUMNS, uniqueTableProperties);
        hdbtableObject.add(HDBTABLE_PROPERTY_COLUMNS, visitTableColumnsProp((TableColumnsPropContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof TableIndexesPropContext) {
        checkPropertyDeclarationUniqueness(HDBTABLE_PROPERTY_INDEXES, uniqueTableProperties);
        hdbtableObject.add(HDBTABLE_PROPERTY_INDEXES, visitTableIndexesProp((TableIndexesPropContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof TablePrimaryKeyPropContext) {
        checkPropertyDeclarationUniqueness(HDBTABLE_PROPERTY_PKCOLUMNS, uniqueTableProperties);
        hdbtableObject.add(HDBTABLE_PROPERTY_PKCOLUMNS,
            visitTablePrimaryKeyProp((TablePrimaryKeyPropContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof DescriptionPropContext) {
        checkPropertyDeclarationUniqueness(HDBTABLE_PROPERTY_DESCRIPTION, uniqueTableProperties);
        hdbtableObject.add(HDBTABLE_PROPERTY_DESCRIPTION, visitDescriptionProp((DescriptionPropContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof TemporaryPropContext) {
        checkPropertyDeclarationUniqueness(HDBTABLE_PROPERTY_TEMPORARY, uniqueTableProperties);
        hdbtableObject.add(HDBTABLE_PROPERTY_TEMPORARY, visitTemporaryProp((TemporaryPropContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof PublicPropContext) {
        checkPropertyDeclarationUniqueness(HDBTABLE_PROPERTY_PUBLIC, uniqueTableProperties);
        hdbtableObject.add(HDBTABLE_PROPERTY_PUBLIC, visitPublicProp((PublicPropContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof LoggingTypePropContext) {
        checkPropertyDeclarationUniqueness(HDBTABLE_PROPERTY_LOGGING_TYPE, uniqueTableProperties);
        hdbtableObject.add(HDBTABLE_PROPERTY_LOGGING_TYPE, visitLoggingTypeProp((LoggingTypePropContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof TablePrimaryKeyIndexTypePropContext) {
        checkPropertyDeclarationUniqueness(HDBTABLE_PROPERTY_INDEX_TYPE, uniqueTableProperties);
        hdbtableObject.add(HDBTABLE_PROPERTY_INDEX_TYPE,
            visitTablePrimaryKeyIndexTypeProp((TablePrimaryKeyIndexTypePropContext) tree.getChild(0)));
      }
    }

    return hdbtableObject;
  }

  /**
   * Visit table columns prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitTableColumnsProp(TableColumnsPropContext ctx) {
    JsonArray tableColumns = new JsonArray();
    if (ctx != null && ctx.columnsObject() != null) {
      ctx.columnsObject().forEach(column -> {
        tableColumns.add(visitColumnsObject(column));
      });
    }
    return tableColumns;
  }

  /**
   * Visit indexes object.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitIndexesObject(IndexesObjectContext ctx) {
    JsonObject indexesObject = new JsonObject();
    List<ParseTree> ctxList = ctx.children;
    HashSet<String> uniqueIndexProperties = new HashSet<>();
    for (ParseTree tree : ctxList) {
      if (tree.getChild(0) instanceof IndexAssignNameContext) {
        checkPropertyDeclarationUniqueness(INDEX_PROPERTY_NAME, uniqueIndexProperties);
        indexesObject.add(INDEX_PROPERTY_NAME, visitIndexAssignName((IndexAssignNameContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof IndexAssignUniqueContext) {
        checkPropertyDeclarationUniqueness(INDEX_PROPERTY_UNIQUE, uniqueIndexProperties);
        indexesObject.add(INDEX_PROPERTY_UNIQUE, visitIndexAssignUnique((IndexAssignUniqueContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof IndexAssignOrderContext) {
        checkPropertyDeclarationUniqueness(INDEX_PROPERTY_ORDER, uniqueIndexProperties);
        indexesObject.add(INDEX_PROPERTY_ORDER, visitIndexAssignOrder((IndexAssignOrderContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof IndexAssignIndexColumnsContext) {
        checkPropertyDeclarationUniqueness(INDEX_PROPERTY_INDEX_COLUMNS, uniqueIndexProperties);
        indexesObject.add(INDEX_PROPERTY_INDEX_COLUMNS,
            visitIndexAssignIndexColumns((IndexAssignIndexColumnsContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof IndexAssignIndexTypeContext) {
        checkPropertyDeclarationUniqueness(INDEX_PROPERTY_INDEX_TYPE, uniqueIndexProperties);
        indexesObject.add(INDEX_PROPERTY_INDEX_TYPE,
            visitIndexAssignIndexType((IndexAssignIndexTypeContext) tree.getChild(0)));
      }
    }

    return indexesObject;
  }

  /**
   * Visit column assign unique.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitColumnAssignUnique(ColumnAssignUniqueContext ctx) {
    return (ctx != null && ctx.BOOLEAN() != null) ? new JsonPrimitive(Boolean.parseBoolean(ctx.BOOLEAN().getText())) : null;
  }

  /**
   * Visit table indexes prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitTableIndexesProp(TableIndexesPropContext ctx) {
    JsonArray tableIndexes = new JsonArray();
    if (ctx != null && ctx.indexesObject() != null) {
      ctx.indexesObject().forEach(index -> {
        tableIndexes.add(visitIndexesObject(index));
      });
    }
    return tableIndexes;
  }

  /**
   * Visit column assign default value.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitColumnAssignDefaultValue(ColumnAssignDefaultValueContext ctx) {
    if (ctx != null && ctx.STRING() != null) {
      return new JsonPrimitive(handleStringLiteral(ctx.STRING().getText()));
    } else if (ctx != null && ctx.INT() != null) {
      return new JsonPrimitive(Integer.parseInt(ctx.INT().getText()));
    } else {
      return null;
    }
  }

  /**
   * Visit column assign name.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitColumnAssignName(ColumnAssignNameContext ctx) {
    return (ctx != null && ctx.STRING() != null) ? new JsonPrimitive(handleStringLiteral(ctx.STRING().getText())) : null;
  }

  /**
   * Visit index assign index columns.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitIndexAssignIndexColumns(IndexAssignIndexColumnsContext ctx) {
    return (ctx != null && ctx.indexColumnsArray() != null) ? visitIndexColumnsArray(ctx.indexColumnsArray()) : new JsonArray();
  }

  /**
   * Visit columns object.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitColumnsObject(ColumnsObjectContext ctx) {
    JsonObject columnsObject = new JsonObject();
    List<ParseTree> ctxList = ctx.children;
    HashSet<String> uniqueColumnProperties = new HashSet<>();
    for (ParseTree tree : ctxList) {
      if (tree.getChild(0) instanceof ColumnAssignNameContext) {
        checkPropertyDeclarationUniqueness(COLUMN_PROPERTY_NAME, uniqueColumnProperties);
        columnsObject.add(COLUMN_PROPERTY_NAME, visitColumnAssignName((ColumnAssignNameContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof ColumnAssignSQLTypeContext) {
        checkPropertyDeclarationUniqueness(COLUMN_PROPERTY_SQL_TYPE, uniqueColumnProperties);
        columnsObject.add(COLUMN_PROPERTY_SQL_TYPE, visitColumnAssignSQLType((ColumnAssignSQLTypeContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof ColumnAssignNullableContext) {
        checkPropertyDeclarationUniqueness(COLUMN_PROPERTY_NULLABLE, uniqueColumnProperties);
        columnsObject.add(COLUMN_PROPERTY_NULLABLE,
            visitColumnAssignNullable((ColumnAssignNullableContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof ColumnAssignUniqueContext) {
        checkPropertyDeclarationUniqueness(COLUMN_PROPERTY_UNIQUE, uniqueColumnProperties);
        columnsObject.add(COLUMN_PROPERTY_UNIQUE, visitColumnAssignUnique((ColumnAssignUniqueContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof ColumnAssignLengthContext) {
        checkPropertyDeclarationUniqueness(COLUMN_PROPERTY_LENGTH, uniqueColumnProperties);
        columnsObject.add(COLUMN_PROPERTY_LENGTH, visitColumnAssignLength((ColumnAssignLengthContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof ColumnAssignPrecisionContext) {
        checkPropertyDeclarationUniqueness(COLUMN_PROPERTY_PRECISION, uniqueColumnProperties);
        columnsObject.add(COLUMN_PROPERTY_PRECISION,
            visitColumnAssignPrecision((ColumnAssignPrecisionContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof ColumnAssignCommentContext) {
        checkPropertyDeclarationUniqueness(COLUMN_PROPERTY_COMMENT, uniqueColumnProperties);
        columnsObject.add(COLUMN_PROPERTY_COMMENT, visitColumnAssignComment((ColumnAssignCommentContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof ColumnAssignScaleContext) {
        checkPropertyDeclarationUniqueness(COLUMN_PROPERTY_SCALE, uniqueColumnProperties);
        columnsObject.add(COLUMN_PROPERTY_SCALE, visitColumnAssignScale((ColumnAssignScaleContext) tree.getChild(0)));
      } else if (tree.getChild(0) instanceof ColumnAssignDefaultValueContext) {
        checkPropertyDeclarationUniqueness(COLUMN_PROPERTY_DEFAULT_VALUE, uniqueColumnProperties);
        columnsObject.add(COLUMN_PROPERTY_DEFAULT_VALUE,
            visitColumnAssignDefaultValue((ColumnAssignDefaultValueContext) tree.getChild(0)));
      }
    }
    return columnsObject;
  }

  /**
   * Visit table type prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitTableTypeProp(TableTypePropContext ctx) {
    return (ctx != null && ctx.TABLETYPE() != null) ? new JsonPrimitive(ctx.TABLETYPE().getText()) : null;
  }

  /**
   * Visit column assign length.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitColumnAssignLength(ColumnAssignLengthContext ctx) {
    return (ctx != null && ctx.INT() != null) ? new JsonPrimitive(Integer.parseInt(ctx.INT().getText())) : null;
  }

  /**
   * Visit index columns array.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitIndexColumnsArray(IndexColumnsArrayContext ctx) {
    JsonArray indexColumnsArray = new JsonArray();
    if (ctx != null && ctx.STRING() != null) {
      ctx.STRING().forEach(column -> {
        indexColumnsArray.add(new JsonPrimitive(handleStringLiteral(column.getText())));
      });
    }
    return indexColumnsArray;
  }

  /**
   * Visit column assign scale.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitColumnAssignScale(ColumnAssignScaleContext ctx) {
    return (ctx != null && ctx.INT() != null) ? new JsonPrimitive(Integer.parseInt(ctx.INT().getText())) : null;
  }

  /**
   * Visit table primary key index type prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitTablePrimaryKeyIndexTypeProp(TablePrimaryKeyIndexTypePropContext ctx) {
    return (ctx != null && ctx.INDEXTYPE() != null) ? new JsonPrimitive(ctx.INDEXTYPE().getText()) : null;
  }

  /**
   * Visit column assign SQL type.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitColumnAssignSQLType(ColumnAssignSQLTypeContext ctx) {
    return (ctx != null && ctx.SQLTYPES() != null) ? new JsonPrimitive(ctx.SQLTYPES().getText()) : null;
  }

  /**
   * Visit index assign index type.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitIndexAssignIndexType(IndexAssignIndexTypeContext ctx) {
    return (ctx != null && ctx.INDEXTYPE() != null) ? new JsonPrimitive(ctx.INDEXTYPE().getText()) : null;
  }

  /**
   * Visit index assign unique.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitIndexAssignUnique(IndexAssignUniqueContext ctx) {
    return (ctx != null && ctx.BOOLEAN() != null) ? new JsonPrimitive(Boolean.parseBoolean(ctx.BOOLEAN().getText())) : null;
  }

  /**
   * Visit logging type prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitLoggingTypeProp(LoggingTypePropContext ctx) {
    return (ctx != null && ctx.TABLELOGGINGTYPE() != null) ? new JsonPrimitive(ctx.TABLELOGGINGTYPE().getText()) : null;
  }

  /**
   * Visit column assign nullable.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitColumnAssignNullable(ColumnAssignNullableContext ctx) {
    return (ctx != null && ctx.BOOLEAN() != null) ? new JsonPrimitive(Boolean.parseBoolean(ctx.BOOLEAN().getText())) : null;
  }

  /**
   * Visit temporary prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitTemporaryProp(TemporaryPropContext ctx) {
    return (ctx != null && ctx.BOOLEAN() != null) ? new JsonPrimitive(Boolean.parseBoolean(ctx.BOOLEAN().getText())) : null;
  }

  /**
   * Visit public prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitPublicProp(PublicPropContext ctx) {
    return (ctx != null && ctx.BOOLEAN() != null) ? new JsonPrimitive(Boolean.parseBoolean(ctx.BOOLEAN().getText())) : null;
  }

  /**
   * Visit index assign order.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitIndexAssignOrder(IndexAssignOrderContext ctx) {
    return (ctx != null && ctx.ORDER() != null) ? new JsonPrimitive(ctx.ORDER().getText()) : null;
  }

  /**
   * Visit table primary key prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitTablePrimaryKeyProp(TablePrimaryKeyPropContext ctx) {
    return (ctx != null && ctx.tablePrimaryKeyColumnsProp() != null) ? visitTablePrimaryKeyColumnsProp(ctx.tablePrimaryKeyColumnsProp())
        : new JsonArray();
  }

  /**
   * Visit column assign precision.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitColumnAssignPrecision(ColumnAssignPrecisionContext ctx) {
    return (ctx != null && ctx.INT() != null) ? new JsonPrimitive(Integer.parseInt(ctx.INT().getText())) : null;
  }

  /**
   * Visit schema name prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitSchemaNameProp(SchemaNamePropContext ctx) {
    return (ctx != null && ctx.STRING() != null) ? new JsonPrimitive(handleStringLiteral(ctx.STRING().getText())) : null;
  }

  /**
   * Visit table primary key columns prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitTablePrimaryKeyColumnsProp(TablePrimaryKeyColumnsPropContext ctx) {
    JsonArray primaryKeyArray = new JsonArray();
    if (ctx != null && ctx.STRING() != null) {
      ctx.STRING().forEach(primaryKey -> {
        primaryKeyArray.add(new JsonPrimitive(handleStringLiteral(primaryKey.getText())));
      });
    }
    return primaryKeyArray;
  }

  /**
   * Visit column assign comment.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitColumnAssignComment(ColumnAssignCommentContext ctx) {
    return (ctx != null && ctx.STRING() != null) ? new JsonPrimitive(handleStringLiteral(ctx.STRING().getText())) : null;
  }

  /**
   * Visit description prop.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitDescriptionProp(DescriptionPropContext ctx) {
    return (ctx != null && ctx.STRING() != null) ? new JsonPrimitive(handleStringLiteral(ctx.STRING().getText())) : null;
  }

  /**
   * Visit index assign name.
   *
   * @param ctx the ctx
   * @return the json element
   */
  @Override
  public JsonElement visitIndexAssignName(IndexAssignNameContext ctx) {
    return (ctx != null && ctx.STRING() != null) ? new JsonPrimitive(handleStringLiteral(ctx.STRING().getText())) : null;
  }

  /**
   * Check property declaration uniqueness.
   *
   * @param property         the property
   * @param uniqueProperties the unique properties
   */
  private void checkPropertyDeclarationUniqueness(String property, HashSet<String> uniqueProperties) {
    if (!uniqueProperties.contains(property)) {
      uniqueProperties.add(property);
    } else {
      throw new HDBTableDuplicatePropertyException(String.format("Property %s is already declared!", property));
    }
  }

  /**
   * Handle string literal.
   *
   * @param value the value
   * @return the string
   */
  private String handleStringLiteral(String value) {
    if (value != null && value.length() > 1) {
      String subStr = value.substring(1, value.length() - 1);
      String escapedQuote = subStr.replace("\\\"", "\"");
      return escapedQuote.replace("\\\\", "\\");
    }

    return null;
  }
}
