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
package com.codbex.kronos.hdb.ds.processors.synonym;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.artefacts.HDBSynonymSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.DataStructureHDBSynonymModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDefinitionModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;


/**
 * The Class HDBSynonymCreateProcessor.
 */
public class SynonymCreateProcessor extends AbstractHDBProcessor<DataStructureHDBSynonymModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(SynonymCreateProcessor.class);
  
  /** The Constant SYNONYM_ARTEFACT. */
  private static final HDBSynonymSynchronizationArtefactType SYNONYM_ARTEFACT = new HDBSynonymSynchronizationArtefactType();

  /**
   * Execute :
   * <code>CREATE [PUBLIC] SYNONYM {synonym_name} FOR {synonym_source_object_name}
   * {synonym_name} ::= [{schema_name}.]{identifier}
   * {synonym_source_object_name}:==[{object_schema_name}.]{object_name}</code>
   *
   * @param connection the connection
   * @param synonymModel the synonym model
   * @return true, if successful
   * @throws SQLException the SQL exception
   * @see <a href="https://help.sap.com/viewer/4fe29514fd584807ac9f2a04f6754767/1.0.12/en-US/20d5412b75191014bc7ec7e133ce5bf5.html">CREATE SYNONYM Statement (Data Definition)</a>
   */
  @Override
  public boolean execute(Connection connection, DataStructureHDBSynonymModel synonymModel) throws SQLException {
	  for (Map.Entry<String, HDBSynonymDefinitionModel> entry : synonymModel.getSynonymDefinitions().entrySet()) {
	      logger.info("Processing Create Synonym: " + entry.getKey());
	
	      String synonymName = (entry.getValue().getSynonymSchema() != null) ? (HDBUtils.escapeArtifactName(entry.getKey(), entry.getValue().getSynonymSchema()))
	          : (HDBUtils.escapeArtifactName(entry.getKey()));
	      String targetObjectName = HDBUtils
	          .escapeArtifactName(entry.getValue().getTarget().getObject(),
	        		  entry.getValue().getTarget().getSchema());
	      try {
	        String synonymSchema = null != entry.getValue().getSynonymSchema() ? entry.getValue().getSynonymSchema() : connection.getMetaData().getUserName();
	        if (!SqlFactory.getNative(connection).exists(connection, synonymSchema, entry.getKey(), DatabaseArtifactTypes.SYNONYM)) {
	          ISqlDialect dialect = SqlFactory.deriveDialect(connection);
	          if (!(dialect.getClass().equals(HanaSqlDialect.class))) {
	            String errorMessage = String.format("Synonyms are not supported for %s", dialect.getDatabaseName(connection));
	            CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, synonymModel.getLocation(),
	                CommonsConstants.HDB_SYNONYM_PARSER);
	            applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
	            throw new IllegalStateException(errorMessage);
	          } else {
	            String sql = SqlFactory.getNative(connection).create().synonym(synonymName).forSource(targetObjectName).build();
	            try {
	              executeSql(sql, connection);
	              String message = String.format("Create synonym [%s] successfully", synonymName);
	              applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE, message);
	            } catch (SQLException ex) {
	              String errorMessage = String.format("Create synonym [%s] skipped due to an error: %s", synonymName, ex.getMessage());
	              CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, synonymModel.getLocation(),
	                  CommonsConstants.HDB_SYNONYM_PARSER);
	              applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
	              return false;
	            }
	          }
	        } else {
	          String warningMessage = String.format("Synonym [%s] already exists during the create process", synonymName);
	          logger.warn(warningMessage);
	          applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.FAILED_CREATE, warningMessage);
	          return false;
	        }
	      } catch (SQLException exception) {
	        String errorMessage = String.format("Create synonym [%s] skipped due to an error: %s", synonymName, exception.getMessage());
	        logger.error(exception.getMessage(), exception);
	        applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
	        return false;
	      }
	    };
	    return true;
    }
}
