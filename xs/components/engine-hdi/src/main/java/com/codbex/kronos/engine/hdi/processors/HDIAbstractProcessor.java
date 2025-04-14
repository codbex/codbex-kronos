/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdi.processors;

import com.codbex.kronos.engine.hdi.ds.util.Message;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HDIAbstractProcessor.
 */
public abstract class HDIAbstractProcessor {

    /**
     * The Constant ERROR_LOCATION.
     */
    private static final String ERROR_LOCATION = "-";

    /**
     * The Constant MESSAGE_SEVERITY_ERROR.
     */
    private static final String MESSAGE_SEVERITY_ERROR = "ERROR";

    /**
     * The Constant MESSAGE_SEVERITY_WARNING.
     */
    private static final String MESSAGE_SEVERITY_WARNING = "WARNING";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HDIAbstractProcessor.class);

    // /** The Constant DATA_STRUCTURES_SYNCHRONIZER. */
    // private static final DataStructuresSynchronizer DATA_STRUCTURES_SYNCHRONIZER = new
    // DataStructuresSynchronizer();

    /**
     * Execute non-select SQL statement with String parameters.
     *
     * @param connection - DB connection
     * @param sql - SQL to be executed
     * @param parameters - SQL parameters
     * @throws SQLException - in case of failure
     */
    public void executeUpdate(Connection connection, String sql, String... parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParams(statement, parameters);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to execute SQL statement [{}]", sql, e);
            CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, ERROR_LOCATION,
                    CommonsConstants.HDI_PROCESSOR);
        }
    }

    /**
     * Execute SQL statement with String parameters that might return a result object.
     *
     * @param connection - DB connection
     * @param sql - SQL to be executed
     * @param parameters - SQL parameters
     * @throws SQLException - in case of failure
     */
    public void executeQuery(Connection connection, String sql, String... parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParams(statement, parameters);
            try (ResultSet resultSet = statement.executeQuery()) {
                parseResultSet(resultSet, sql);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to execute SQL statement - [{}]", sql, e);
            CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, ERROR_LOCATION,
                    CommonsConstants.HDI_PROCESSOR);
        }
    }

    public void parseResultSet(ResultSet resultSet, String usedSql) throws SQLException {
        ArrayList<Message> messages = new ArrayList<>();
        while (resultSet.next()) {
            messages.add(new Message(resultSet));
        }

        boolean containsError = messages.stream()
                                        .filter(m -> MESSAGE_SEVERITY_ERROR.equals(m.severity))
                                        .findFirst()
                                        .isPresent();
        boolean containsWarning = messages.stream()
                                          .filter(m -> MESSAGE_SEVERITY_WARNING.equals(m.severity))
                                          .findFirst()
                                          .isPresent();

        if (containsError) {
            String concatenatedMessage = concatenatedMessages(messages);
            LOGGER.error("Result of [{}] - ERROR:\n[{}]", usedSql, concatenatedMessage);
            CommonsUtils.logProcessorErrors(concatenatedMessage, CommonsConstants.PROCESSOR_ERROR, "", CommonsConstants.HDI_PROCESSOR);
        }
        if (!containsError && containsWarning) {
            String concatenatedMessage = concatenatedMessages(messages);
            LOGGER.warn("Result of [{}] - WARNING:\n[{}]", usedSql, concatenatedMessage);
        } else {
            String concatenatedMessage = concatenatedMessages(messages);
            LOGGER.warn("Result of [{}] - INFO:\n[{}]", usedSql, concatenatedMessage);
        }
    }

    private static String concatenatedMessages(ArrayList<Message> messages) {
        StringBuilder sb = new StringBuilder();
        messages.forEach(m -> sb.append("[")
                                .append(m.severity)
                                .append("]: ")
                                .append(m.message)
                                .append("\n"));
        return sb.toString();
    }

    /**
     * Sets the statement params.
     *
     * @param statement the statement
     * @param parameters the parameters
     * @throws SQLException the SQL exception
     */
    protected void setStatementParams(PreparedStatement statement, String... parameters) throws SQLException {
        int paramIndex = 0;
        for (String param : parameters) {
            statement.setString(++paramIndex, param);
        }
    }

    // /**
    // * Apply artefact state.
    // *
    // * @param artefactName the artefact name
    // * @param artefactLocation the artefact location
    // * @param type the type
    // * @param state the state
    // * @param message the message
    // */
    // public void applyArtefactState(String artefactName, String artefactLocation,
    // AbstractSynchronizationArtefactType type, ISynchronizerArtefactType.ArtefactState state, String
    // message) {
    // DATA_STRUCTURES_SYNCHRONIZER.applyArtefactState(artefactName, artefactLocation, type, state,
    // message);
    // }

}
