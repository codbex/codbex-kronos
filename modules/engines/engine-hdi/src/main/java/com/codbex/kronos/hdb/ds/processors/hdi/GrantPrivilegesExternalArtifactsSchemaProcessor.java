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
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDefinitionModel;


public class GrantPrivilegesExternalArtifactsSchemaProcessor extends HDIAbstractProcessor {

  private static final String HDBSYNONYM_FILE_EXTENSION = "hdbsynonym";

  public final void execute(Connection connection, String container, String[] deploys)
      throws SQLException, IOException, DataStructuresException {
    Set<String> externalArtifactsSchemas = collectExternalArtifactsSchemas(deploys);

    grantPrivileges(connection, container, externalArtifactsSchemas);
  }

  @NotNull
  private Set<String> collectExternalArtifactsSchemas(String[] deploys) throws IOException, DataStructuresException {
    Set<String> externalArtifactsSchemas = new HashSet<>();
    for (String deploy : deploys) {
      if (FilenameUtils.getExtension(deploy).equalsIgnoreCase(HDBSYNONYM_FILE_EXTENSION)) {
        String hdbSynonymContent = getSynonymContent(deploy);
        HDBSynonymDataStructureModel synonymModel = getSynonymModel(deploy, hdbSynonymContent);

        for (Entry<String, HDBSynonymDefinitionModel> synonymDefinition : synonymModel.getSynonymDefinitions().entrySet()) {
          String externalArtifactSchema = synonymDefinition.getValue().getTarget().getSchema();
          externalArtifactsSchemas.add(externalArtifactSchema);
        }
      }
    }
    return externalArtifactsSchemas;
  }

  protected String getSynonymContent(String deploy) throws IOException {
    return RegistryFacade.getText(deploy);
  }

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

  protected HDBSynonymDataStructureModel getSynonymModel(String location, String content) throws DataStructuresException, IOException {
    return DataStructureModelFactory.parseSynonym(location, content);
  }
}