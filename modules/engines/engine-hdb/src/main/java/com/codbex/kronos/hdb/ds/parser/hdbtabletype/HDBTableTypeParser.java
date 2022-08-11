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
package com.codbex.kronos.hdb.ds.parser.hdbtabletype;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.transformer.HDBTableDefinitionModelToHDBTableColumnModelTransformer;
import com.codbex.kronos.parser.hdbtable.core.HdbtableLexer;
import com.codbex.kronos.parser.hdbtable.core.HdbtableParser;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableCoreVisitor;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableSyntaxErrorListener;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;
import com.codbex.kronos.parser.hdbtable.model.HDBTableColumnsModel;
import com.codbex.kronos.parser.hdbtable.model.HDBTableDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * The Class HDBTableTypeParser.
 */
public class HDBTableTypeParser implements DataStructureParser<DataStructureHDBTableTypeModel> {

  // TYPE (?:["'](.*)["'].)?["'](.*)["']
  // uses non-capturing group in order to handle the
  // possible case of a table type with only a name and no schema
  /** The Constant TABLE_TYPE_SCHEMA_AND_NAME_PATTERN. */
  private static final Pattern TABLE_TYPE_SCHEMA_AND_NAME_PATTERN = Pattern.compile("TYPE\\s+(?:[\"'](.*)[\"'].)?[\"'](.*)[\"']");

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBTableTypeParser.class);

  /**
   * Parses the hdbtabletype file.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB table type model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public DataStructureHDBTableTypeModel parse(DataStructureParametersModel parametersModel)
      throws DataStructuresException, IOException, ArtifactParserException {
    logger.debug("Parsing hdbstructure as Hana XS Advanced format");
    DataStructureHDBTableTypeModel dataStructureHDBTableTypeModel = new DataStructureHDBTableTypeModel();

    HDBUtils.populateDataStructureModel(parametersModel.getLocation(), parametersModel.getContent(), dataStructureHDBTableTypeModel, IDataStructureModel.TYPE_HDB_TABLE_TYPE,
        DBContentType.OTHERS);
    Pair<String, String> schemaAndNamePair = extractTableTypeSchemaAndName(parametersModel.getContent());

    dataStructureHDBTableTypeModel.setSchema(schemaAndNamePair.getLeft());
    dataStructureHDBTableTypeModel.setName(schemaAndNamePair.getRight());
    dataStructureHDBTableTypeModel.setRawContent(parametersModel.getContent());

    return dataStructureHDBTableTypeModel;
  }

  /**
   * Extract table type schema and name.
   *
   * @param content the content
   * @return the pair
   */
  private Pair<String, String> extractTableTypeSchemaAndName(String content) {
    String contentWithoutPossibleComments = HDBUtils.removeSqlCommentsFromContent(content);
    Matcher matcher = TABLE_TYPE_SCHEMA_AND_NAME_PATTERN.matcher(contentWithoutPossibleComments);

    if (!matcher.find()) {
      throw new IllegalStateException("Couldn't extract table type schema and name from content: " + content);
    }

    return Pair.of(matcher.group(1), matcher.group(2));
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDB_TABLE_TYPE;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class<DataStructureHDBTableTypeModel> getDataStructureClass() {
    return DataStructureHDBTableTypeModel.class;
  }
}
