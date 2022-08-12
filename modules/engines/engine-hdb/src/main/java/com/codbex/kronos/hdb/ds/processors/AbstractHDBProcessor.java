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
package com.codbex.kronos.hdb.ds.processors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizationArtefactType;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.IHDBProcessor;
import com.codbex.kronos.hdb.ds.facade.HDBCoreSynchronizationFacade;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;


/**
 * The Class AbstractHDBProcessor.
 *
 * @param <T> the generic type
 */
public abstract class AbstractHDBProcessor<T extends DataStructureModel> implements IHDBProcessor<T> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBCoreSynchronizationFacade.class);

  /** The Constant DATA_STRUCTURES_SYNCHRONIZER. */
  private static final DataStructuresSynchronizer DATA_STRUCTURES_SYNCHRONIZER = new DataStructuresSynchronizer();

  /**
   * Apply artefact state.
   *
   * @param artefactName the artefact name
   * @param artefactLocation the artefact location
   * @param type the type
   * @param state the state
   * @param message the message
   */
  public void applyArtefactState(String artefactName, String artefactLocation, AbstractSynchronizationArtefactType type, ISynchronizerArtefactType.ArtefactState state, String message) {
      DATA_STRUCTURES_SYNCHRONIZER.applyArtefactState(artefactName, artefactLocation, type, state, message);
  }

  /**
   * Execute sql.
   *
   * @param sql the sql
   * @param connection the connection
   * @throws SQLException the SQL exception
   */
  public void executeSql(String sql, Connection connection) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      logger.info(sql);
      statement.executeUpdate();
    } catch (SQLException e) {
      logger.error(sql);
      logger.error(e.getMessage(), e);
      throw e;
    }
  }
}
