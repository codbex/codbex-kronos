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
package com.codbex.kronos.hdb.ds.processors.hdbschema;

import com.codbex.kronos.hdb.ds.artefacts.HDBSchemaSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbschema.HDBSchemaDataStructureModel;
import com.codbex.kronos.hdb.ds.processors.AbstractProcessor;
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

public class HDBSchemaCreateProcessor extends AbstractProcessor<HDBSchemaDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(HDBSchemaCreateProcessor.class);
  private static final HDBSchemaSynchronizationArtefactType SCHEMA_ARTEFACT = new HDBSchemaSynchronizationArtefactType();

  public boolean execute(Connection connection, HDBSchemaDataStructureModel hdbSchema) throws SQLException {
    logger.info("Processing Create Schema: " + hdbSchema.getSchema());

    ISqlDialect dialect = SqlFactory.deriveDialect(connection);
    if (!(dialect.getClass().equals(HanaSqlDialect.class))) {
      String errorMessage = String.format("%s does not support Schema", dialect.getDatabaseName(connection));
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, hdbSchema.getLocation(),
          CommonsConstants.HDB_SCHEMA_PARSER);
      applyArtefactState(hdbSchema.getName(), hdbSchema.getLocation(), SCHEMA_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
      throw new IllegalStateException(errorMessage);
    } else {
      if (!SqlFactory.getNative(connection).exists(connection, hdbSchema.getSchema(), DatabaseArtifactTypes.SCHEMA)) {
        String schemaName = HDBUtils.escapeArtifactName(hdbSchema.getSchema());
        String sql = SqlFactory.getNative(connection).create().schema(schemaName).build();
        try {
          executeSql(sql, connection);
          String message = String.format("Create schema %s successfully", hdbSchema.getName());
          applyArtefactState(hdbSchema.getName(), hdbSchema.getLocation(), SCHEMA_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE, message);
          return true;
        } catch (SQLException ex) {
          String errorMessage = String.format("Create schema [%s] skipped due to an error: %s", hdbSchema, ex.getMessage());
          CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, hdbSchema.getLocation(),
              CommonsConstants.HDB_SCHEMA_PARSER);
          applyArtefactState(hdbSchema.getName(), hdbSchema.getLocation(), SCHEMA_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
          return false;
        }
      } else {
        String warningMessage = String.format("Schema [%s] already exists during the create process", hdbSchema.getSchema());
        logger.warn(warningMessage);
        applyArtefactState(hdbSchema.getName(), hdbSchema.getLocation(), SCHEMA_ARTEFACT, ArtefactState.FAILED_CREATE, warningMessage);
        return false;
      }
    }
  }
}
