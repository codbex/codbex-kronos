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

import com.codbex.kronos.hdb.ds.artefacts.HDBSynonymSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDefinitionModel;
import com.codbex.kronos.hdb.ds.processors.AbstractProcessor;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDBSynonymDropProcessor extends AbstractProcessor<HDBSynonymDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(HDBSynonymDropProcessor.class);
  private static final HDBSynonymSynchronizationArtefactType SYNONYM_ARTEFACT = new HDBSynonymSynchronizationArtefactType();

  /**
   * Execute :
   * DROP SYNONYM <synonym_name> [<drop_option>]
   * If <drop_option> is not specified, then a non-cascaded drop is performed which only drops the specified synonym.
   * Dependent objects of the synonym are invalidated but not dropped.
   *
   * @see <a href="https://help.sap.com/viewer/4fe29514fd584807ac9f2a04f6754767/1.0.12/en-US/20d7e172751910148bccb49de92d9859.html">DROP SYNONYM Statement (Data Definition)</a>
   */
  @Override
  public boolean execute(Connection connection, HDBSynonymDataStructureModel synonymModel) {
	  for (Map.Entry<String, HDBSynonymDefinitionModel> entry : synonymModel.getSynonymDefinitions().entrySet()) {

	      String synonymName = (entry.getValue().getSynonymSchema() != null) ? HDBUtils.escapeArtifactName(entry.getKey(), entry.getValue().getSynonymSchema())
	          : HDBUtils.escapeArtifactName(entry.getKey());
	      try {
	        if (SqlFactory.getNative(connection).exists(connection, entry.getValue().getSynonymSchema(), entry.getKey(), DatabaseArtifactTypes.SYNONYM)) {
	          String sql = SqlFactory.getNative(connection).drop().synonym(synonymName).build();
	          try {
	            executeSql(sql, connection);
	            applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.SUCCESSFUL_DELETE,
	                "Drop synonym [" + synonymName + "] successfully");
	          } catch (SQLException ex) {
	            String errorMessage = "Drop synonym [" + synonymName + "] skipped due to an error: " + ex.getMessage();
	            CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, synonymModel.getLocation(),
	                CommonsConstants.HDB_SYNONYM_PARSER);
	            applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.FAILED_DELETE, errorMessage);
	            return false;
	          }
	        } else {
	          String message = "Synonym [" + synonymName + "] does not exists during the drop process";
	          applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.SUCCESSFUL_DELETE, message);
	        }
	      } catch (SQLException exception) {
	        String errorMessage = "Drop synonym [" + synonymName + "] skipped due to an error: " + exception.getMessage();
	        logger.error(errorMessage, exception);
	        applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.FAILED_DELETE, errorMessage);
	        return false;
	      }
    }
    return true;
  }
}
