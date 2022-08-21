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
package com.codbex.kronos.xsodata.ds.service;

import static com.codbex.kronos.utils.CommonsDBUtils.getSynonymTargetObjectMetadata;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.engine.odata2.api.ITableMetadataProvider;
import org.eclipse.dirigible.engine.odata2.definition.ODataEntityDefinition;
import org.eclipse.dirigible.engine.odata2.transformers.DBMetadataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TableMetadataProvider.
 */
public class TableMetadataProvider implements ITableMetadataProvider {

  /** The data source. */
  private DataSource getDataSource() { 
	  return (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);
  }

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(TableMetadataProvider.class);

  /** The db metadata util. */
  private final DBMetadataUtil dbMetadataUtil = new DBMetadataUtil();

  /** The Constant METADATA_ENTITY_TYPES. */
  private static final List<String> METADATA_ENTITY_TYPES = List.of(ISqlKeywords.METADATA_TABLE, ISqlKeywords.METADATA_CALC_VIEW,
      ISqlKeywords.METADATA_VIEW);
  
  /** The Constant PUBLIC_SCHEMA. */
  private static final String PUBLIC_SCHEMA = "PUBLIC";

  /**
   * Gets the persistence table model.
   *
   * @param oDataEntityDefinition the o data entity definition
   * @return the persistence table model
   * @throws SQLException the SQL exception
   */
  @Override
  public PersistenceTableModel getPersistenceTableModel(ODataEntityDefinition oDataEntityDefinition) throws SQLException {
    return getPersistenceTableModel(oDataEntityDefinition.getTable());
  }

  /**
   * Gets the persistence table model.
   *
   * @param artifactName the artifact name
   * @return the persistence table model
   * @throws SQLException the SQL exception
   */
  public PersistenceTableModel getPersistenceTableModel(String artifactName) throws SQLException {
    String currentUserSchema = Configuration.get("HANA_USERNAME");
    PersistenceTableModel tableMetadata = dbMetadataUtil.getTableMetadata(artifactName, currentUserSchema);

    if (null != tableMetadata && METADATA_ENTITY_TYPES.contains(tableMetadata.getTableType())) {
      return tableMetadata;
    }

    PersistenceTableModel targetObjectMetadata = null;
    if (null == tableMetadata) {
      targetObjectMetadata = getSynonymTargetObjectMetadata(getDataSource(), artifactName, PUBLIC_SCHEMA);
    } else if (ISqlKeywords.METADATA_SYNONYM.equals(tableMetadata.getTableType())) {
      targetObjectMetadata = getSynonymTargetObjectMetadata(getDataSource(), artifactName, tableMetadata.getSchemaName());
    }

    if (null == targetObjectMetadata || targetObjectMetadata.getTableName() == null) {
      logger.error("We cannot find view/table/synonym with name " + artifactName + " in schema " + currentUserSchema +
          " and there is no public synonym with that name. Make sure that you are using view/table/synonym which is in your user default "
          + "schema or is a public synonym.");
      return null;
    }

    return dbMetadataUtil.getTableMetadata(targetObjectMetadata.getTableName(), targetObjectMetadata.getSchemaName());
  }
}
