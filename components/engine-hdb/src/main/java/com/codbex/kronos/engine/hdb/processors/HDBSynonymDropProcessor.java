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
package com.codbex.kronos.engine.hdb.processors;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.hdb.domain.HDBSynonym;
import com.codbex.kronos.engine.hdb.domain.HDBSynonymGroup;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class HDBSynonymDropProcessor.
 */
public class HDBSynonymDropProcessor extends AbstractHDBProcessor<HDBSynonymGroup> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBSynonymDropProcessor.class);

  /**
   * Execute :
   * <code>DROP SYNONYM {synonym_name} [{drop_option}]</code>
   * If {drop_option} is not specified, then a non-cascaded drop is performed which only drops the specified synonym.
   * Dependent objects of the synonym are invalidated but not dropped.
   *
   * @param connection   the connection
   * @param synonymModel the synonym model
   * @return true, if successful
   * @see <a href="https://help.sap.com/viewer/4fe29514fd584807ac9f2a04f6754767/1.0.12/en-US/20d7e172751910148bccb49de92d9859.html">DROP SYNONYM Statement (Data Definition)</a>
   */
  @Override
  public boolean execute(Connection connection, HDBSynonymGroup synonymModel) {
    for (Map.Entry<String, HDBSynonym> entry : synonymModel.getSynonymDefinitions().entrySet()) {

      String synonymName =
          (entry.getValue().getSchema() != null) ? HDBUtils.escapeArtifactName(entry.getKey(), entry.getValue().getSchema())
              : HDBUtils.escapeArtifactName(entry.getKey());
      try {
        if (SqlFactory.getNative(connection)
            .exists(connection, entry.getValue().getSchema(), entry.getKey(), DatabaseArtifactTypes.SYNONYM)) {
          String sql = SqlFactory.getNative(connection).drop().synonym(synonymName).build();
          try {
            executeSql(sql, connection);
            String message = String.format("Drop synonym [%s] successfully", synonymName);
//            applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.SUCCESSFUL_DELETE, message);
          } catch (SQLException ex) {
            String message = String.format("Drop synonym [%s] skipped due to an error: %s", synonymModel.getName(), ex.getMessage());
            CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, synonymModel.getLocation(),
                CommonsConstants.HDB_SYNONYM_PARSER);
//            applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.FAILED_DELETE, message);
            return false;
          }
        } else {
          String message = String.format("Synonym [%s] does not exists during the drop process", synonymModel.getName());
          logger.warn(message);
//          applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.SUCCESSFUL_DELETE, message);
        }
      } catch (SQLException exception) {
        String message = String.format("Drop synonym [%s] skipped due to an error: %s", synonymModel.getName(), exception.getMessage());
        logger.error(message, exception);
//        applyArtefactState(synonymName, synonymModel.getLocation(), SYNONYM_ARTEFACT, ArtefactState.FAILED_DELETE, message);
        return false;
      }
    }
    return true;
  }
}
