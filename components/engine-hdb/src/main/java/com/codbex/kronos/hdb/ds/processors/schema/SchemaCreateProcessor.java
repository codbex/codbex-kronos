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
package com.codbex.kronos.hdb.ds.processors.schema;

import com.codbex.kronos.hdb.ds.artefacts.HDBSchemaSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbschema.DataStructureHDBSchemaModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HDBSchemaCreateProcessor.
 */
public class SchemaCreateProcessor extends AbstractHDBProcessor<DataStructureHDBSchemaModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(SchemaCreateProcessor.class);
  
  /** The Constant SCHEMA_ARTEFACT. */
  private static final HDBSchemaSynchronizationArtefactType SCHEMA_ARTEFACT = new HDBSchemaSynchronizationArtefactType();

  /**
   * Execute.
   *
   * @param connection the connection
   * @param schemaModel the hdb schema
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, DataStructureHDBSchemaModel schemaModel) throws SQLException {
    logger.info("Processing Create Schema: " + schemaModel.getSchema());

    ISqlDialect dialect = SqlFactory.deriveDialect(connection);
    if (!(dialect.getClass().equals(HanaSqlDialect.class))) {
      String errorMessage = String.format("Schemas are not supported for %s", dialect.getDatabaseName(connection));
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, schemaModel.getLocation(),
          CommonsConstants.HDB_SCHEMA_PARSER);
      applyArtefactState(schemaModel.getName(), schemaModel.getLocation(), SCHEMA_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
      throw new IllegalStateException(errorMessage);
    } else {
      if (!SqlFactory.getNative(connection).exists(connection, schemaModel.getSchema(), DatabaseArtifactTypes.SCHEMA)) {
        String schemaName = HDBUtils.escapeArtifactName(schemaModel.getSchema());
        String sql = SqlFactory.getNative(connection).create().schema(schemaName).build();
        try {
          executeSql(sql, connection);
          String message = String.format("Create schema [%s] successfully", schemaModel.getName());
          applyArtefactState(schemaModel.getName(), schemaModel.getLocation(), SCHEMA_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE, message);
          return true;
        } catch (SQLException ex) {
          String errorMessage = String.format("Create schema [%s] skipped due to an error: %s", schemaModel, ex.getMessage());
          CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, schemaModel.getLocation(),
              CommonsConstants.HDB_SCHEMA_PARSER);
          applyArtefactState(schemaModel.getName(), schemaModel.getLocation(), SCHEMA_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
          return false;
        }
      } else {
        String warningMessage = String.format("Schema [%s] already exists during the create process", schemaModel.getSchema());
        logger.warn(warningMessage);
        applyArtefactState(schemaModel.getName(), schemaModel.getLocation(), SCHEMA_ARTEFACT, ArtefactState.FAILED_CREATE, warningMessage);
        return false;
      }
    }
  }
}
