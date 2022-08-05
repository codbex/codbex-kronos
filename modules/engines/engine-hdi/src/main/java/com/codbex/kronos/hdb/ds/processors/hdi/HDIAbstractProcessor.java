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
package com.codbex.kronos.hdb.ds.processors.hdi;

import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.hdb.ds.util.Message;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizationArtefactType;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HDIAbstractProcessor {

	private static final String ERROR_LOCATION = "-";
	private static final String MESSAGE_SEVERITY_ERROR = "ERROR";
  private static final String MESSAGE_SEVERITY_WARNING = "WARNING";
	private static final Logger LOGGER = LoggerFactory.getLogger(HDIAbstractProcessor.class);
  private static final DataStructuresSynchronizer DATA_STRUCTURES_SYNCHRONIZER = new DataStructuresSynchronizer();

	/**
	 * Execute non-select SQL statement with String parameters
	 * 
	 * @param connection - DB connection
	 * @param sql        - SQL to be executed
	 * @param parameters - SQL parameters
	 * @throws SQLException - in case of failure
	 */
  public void executeUpdate(Connection connection, String sql, String... parameters) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			setStatementParams(statement, parameters);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Failed to execute SQL statement - " + sql, e);
			CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, ERROR_LOCATION,
					CommonsConstants.HDI_PROCESSOR);
		}
	}

	/**
	 * Execute SQL statement with String parameters that might return a result object
	 * 
	 * @param connection - DB connection
	 * @param sql        - SQL to be executed
	 * @param parameters - SQL parameters
	 * @throws SQLException - in case of failure
	 */
	protected void executeQuery(Connection connection, String sql, String... parameters) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			setStatementParams(statement, parameters);
			try (ResultSet resultSet = statement.executeQuery()) {
        parseResultSet(resultSet);
			}
		} catch (SQLException e) {
			LOGGER.error("Failed to execute SQL statement - " + sql, e);
			CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, ERROR_LOCATION,
					CommonsConstants.HDI_PROCESSOR);
		}
	}

	protected void parseResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<Message> messages = new ArrayList<>();
    while (resultSet.next())
    {
      messages.add(new Message(resultSet));
    }
    for(Message message : messages) {
      if(message.severity.equals(MESSAGE_SEVERITY_ERROR)) {
        LOGGER.error(message.message);
        CommonsUtils.logProcessorErrors(message.message, CommonsConstants.PROCESSOR_ERROR, message.path,
            CommonsConstants.HDI_PROCESSOR);
      }else if(message.severity.equals(MESSAGE_SEVERITY_WARNING)){
        LOGGER.warn(message.message);
      }else {
        LOGGER.info(message.message);
      }
    }
	}

	protected void setStatementParams(PreparedStatement statement, String... parameters) throws SQLException {
		int paramIndex = 0;
		for (String param : parameters) {
			statement.setString(++paramIndex, param);
		}
	}


  public void applyArtefactState(String artefactName, String artefactLocation, AbstractSynchronizationArtefactType type, ISynchronizerArtefactType.ArtefactState state, String message) {
    DATA_STRUCTURES_SYNCHRONIZER.applyArtefactState(artefactName, artefactLocation, type, state, message);
  }

}
