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
package com.codbex.kronos.hdb.ds.transformer;

import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableColumnModel;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;
import com.codbex.kronos.parser.hdbtable.model.HDBTableColumnsModel;
import com.codbex.kronos.parser.hdbtable.model.HDBTableDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class HDBTableDefinitionModelToHDBTableColumnModelTransformer.
 */
public class HDBTableDefinitionModelToHDBTableColumnModelTransformer {


  /**
   * Transform.
   *
   * @param hdbtableDefinitionModel the hdbtable definition model
   * @param location the location
   * @return the list
   */
  public List<DataStructureHDBTableColumnModel> transform(HDBTableDefinitionModel hdbtableDefinitionModel, String location) {

    List<DataStructureHDBTableColumnModel> columns = new ArrayList<>();

    for (HDBTableColumnsModel column : hdbtableDefinitionModel.getColumns()) {
      try {
        column.checkForAllMandatoryColumnFieldsPresence();
      } catch (Exception e) {
        CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
            CommonsConstants.EXPECTED_FIELDS, CommonsConstants.HDB_TABLE_PARSER,CommonsConstants.MODULE_PARSERS,
            CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
        throw new HDBTableMissingPropertyException(String.format("Wrong format of table definition: [%s]. [%s]", location, e.getMessage()));
      }
      DataStructureHDBTableColumnModel dataStructureHDBTableColumnModel = new DataStructureHDBTableColumnModel();
      dataStructureHDBTableColumnModel.setLength(column.getLength());
      dataStructureHDBTableColumnModel.setName(column.getName());
      dataStructureHDBTableColumnModel.setType(column.getSqlType());
      dataStructureHDBTableColumnModel.setComment(column.getComment());
      dataStructureHDBTableColumnModel.setNullable(column.isNullable());
      dataStructureHDBTableColumnModel.setDefaultValue(column.getDefaultValue());
      dataStructureHDBTableColumnModel.setPrecision(column.getPrecision());
      dataStructureHDBTableColumnModel.setScale(column.getScale());
      dataStructureHDBTableColumnModel.setUnique(column.isUnique());
      List<String> foundMatchKey = hdbtableDefinitionModel.getPkColumns().stream().filter(key -> key.equals(column.getName())).collect(
          Collectors.toList());
      if (foundMatchKey.size() == 1) {
        dataStructureHDBTableColumnModel.setPrimaryKey(true);
      }
      columns.add(dataStructureHDBTableColumnModel);
    }

    return columns;
  }
}
