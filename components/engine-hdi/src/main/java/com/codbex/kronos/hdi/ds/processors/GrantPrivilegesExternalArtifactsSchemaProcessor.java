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
package com.codbex.kronos.hdi.ds.processors;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.dirigible.api.v3.platform.RegistryFacade;
import org.jetbrains.annotations.NotNull;

import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureModelFactory;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.DataStructureHDBSynonymModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDefinitionModel;


/**
 * The Class GrantPrivilegesExternalArtifactsSchemaProcessor.
 */
public class GrantPrivilegesExternalArtifactsSchemaProcessor extends HDIAbstractProcessor {

  /** The Constant HDBSYNONYM_FILE_EXTENSION. */
  private static final String HDBSYNONYM_FILE_EXTENSION = "hdbsynonym";

  /**
   * Execute.
   *
   * @param connection the connection
   * @param container the container
   * @param deploys the deploys
   * @throws SQLException the SQL exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws DataStructuresException the data structures exception
   */
  public void execute(Connection connection, String container, String[] deploys)
      throws SQLException, IOException, DataStructuresException {
    Set<String> externalArtifactsSchemas = collectExternalArtifactsSchemas(deploys);

    grantPrivileges(connection, container, externalArtifactsSchemas);
  }

  /**
   * Collect external artifacts schemas.
   *
   * @param deploys the deploys
   * @return the sets the
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws DataStructuresException the data structures exception
   */
  @NotNull
  private Set<String> collectExternalArtifactsSchemas(String[] deploys) throws IOException, DataStructuresException {
    Set<String> externalArtifactsSchemas = new HashSet<>();
    for (String deploy : deploys) {
      if (FilenameUtils.getExtension(deploy).equalsIgnoreCase(HDBSYNONYM_FILE_EXTENSION)) {
        String hdbSynonymContent = getSynonymContent(deploy);
        DataStructureHDBSynonymModel synonymModel = getSynonymModel(deploy, hdbSynonymContent);

        for (Entry<String, HDBSynonymDefinitionModel> synonymDefinition : synonymModel.getSynonymDefinitions().entrySet()) {
          String externalArtifactSchema = synonymDefinition.getValue().getTarget().getSchema();
          externalArtifactsSchemas.add(externalArtifactSchema);
        }
      }
    }
    return externalArtifactsSchemas;
  }

  /**
   * Gets the synonym content.
   *
   * @param deploy the deploy
   * @return the synonym content
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected String getSynonymContent(String deploy) throws IOException {
    return RegistryFacade.getText(deploy);
  }

  /**
   * Grant privileges.
   *
   * @param connection the connection
   * @param container the container
   * @param externalArtifactsSchemas the external artifacts schemas
   * @throws SQLException the SQL exception
   */
  private void grantPrivileges(Connection connection, String container, Set<String> externalArtifactsSchemas) throws SQLException {
    String containerOwner = container + "#OO";
    for (String externalSchema: externalArtifactsSchemas) {
      executeUpdate(connection, format("GRANT SELECT ON SCHEMA \"{0}\" TO \"{1}\" WITH GRANT OPTION;", externalSchema, containerOwner));
      executeUpdate(connection, format("GRANT EXECUTE ON SCHEMA \"{0}\" TO \"{1}\" WITH GRANT OPTION;", externalSchema, containerOwner));
      executeUpdate(connection, format("GRANT INSERT ON SCHEMA \"{0}\" TO \"{1}\" WITH GRANT OPTION;", externalSchema, containerOwner));
      executeUpdate(connection, format("GRANT UPDATE ON SCHEMA \"{0}\" TO \"{1}\" WITH GRANT OPTION;", externalSchema, containerOwner));
      executeUpdate(connection, format("GRANT DELETE ON SCHEMA \"{0}\" TO \"{1}\" WITH GRANT OPTION;", externalSchema, containerOwner));
    }
  }

  /**
   * Gets the synonym model.
   *
   * @param location the location
   * @param content the content
   * @return the synonym model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected DataStructureHDBSynonymModel getSynonymModel(String location, String content) throws DataStructuresException, IOException {
    return DataStructureModelFactory.parseSynonym(location, content);
  }
}