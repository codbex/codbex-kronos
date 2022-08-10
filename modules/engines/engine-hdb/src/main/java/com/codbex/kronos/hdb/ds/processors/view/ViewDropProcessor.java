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
package com.codbex.kronos.hdb.ds.processors.view;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.artefacts.HDBViewSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbview.DataStructureHDBViewModel;
import com.codbex.kronos.hdb.ds.module.HDBModule;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.hdb.ds.service.manager.IDataStructureManager;
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

/**
 * The View Drop Processor.
 */
public class ViewDropProcessor extends AbstractHDBProcessor<DataStructureHDBViewModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(ViewDropProcessor.class);
  
  /** The Constant VIEW_ARTEFACT. */
  private static final HDBViewSynchronizationArtefactType VIEW_ARTEFACT = new HDBViewSynchronizationArtefactType();

  /** The manager services. */
  private Map<String, IDataStructureManager> managerServices = HDBModule.getManagerServices();

  /**
   * Execute the corresponding statement.
   *
   * @param connection the connection
   * @param viewModel  the view model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, DataStructureHDBViewModel viewModel)
      throws SQLException {
    logger.info("Processing Drop View: " + viewModel.getName());
    String viewNameWithSchema = HDBUtils.escapeArtifactName(viewModel.getName(), viewModel.getSchema());

    //Drop public synonym
    if (managerServices != null) {
      HDBUtils.dropPublicSynonymForArtifact(managerServices
          .get(IDataStructureModel.TYPE_HDB_SYNONYM), viewModel.getName(), viewModel.getSchema(), connection);
    }

    //Drop view
    if (SqlFactory.getNative(connection).exists(connection, viewNameWithSchema, DatabaseArtifactTypes.VIEW)) {
      String sql = SqlFactory.getNative(connection).drop().view(viewNameWithSchema).build();
      try {
        executeSql(sql, connection);
        String message = String.format("Drop view %s successfully", viewModel.getName());
        applyArtefactState(viewModel.getName(), viewModel.getLocation(), VIEW_ARTEFACT, ArtefactState.SUCCESSFUL_DELETE, message);
        return true;
      } catch (SQLException ex) {
        String errorMessage = String.format("Drop view [%s] skipped due to an error: %s", viewModel.getName(), ex.getMessage());
        CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, viewModel.getLocation(),
            CommonsConstants.HDB_VIEW_PARSER);
        applyArtefactState(viewModel.getName(), viewModel.getLocation(), VIEW_ARTEFACT, ArtefactState.FAILED_DELETE, errorMessage);
        return false;
      }
    } else {
      String warningMessage = String.format("View [%s] does not exists during the drop process", viewModel.getName());
      logger.warn(warningMessage);
      applyArtefactState(viewModel.getName(), viewModel.getLocation(), VIEW_ARTEFACT, ArtefactState.FAILED_DELETE, warningMessage);
      return true;
    }
  }

}
