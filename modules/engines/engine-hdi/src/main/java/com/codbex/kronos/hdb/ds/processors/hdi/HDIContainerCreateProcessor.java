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

import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.hdi.HDIDataStructureModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.dirigible.commons.api.scripting.ScriptingException;
import org.eclipse.dirigible.commons.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDIContainerCreateProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(HDIContainerCreateProcessor.class);

  private final GrantPrivilegesContainerGroupAPIProcessor grantPrivilegesContainerGroupAPIProcessor = new GrantPrivilegesContainerGroupAPIProcessor();
  private final CreateContainerGroupProcessor createContainerGroupProcessor = new CreateContainerGroupProcessor();
  private final GrantPrivilegesContainerGroupProcessor grantPrivilegesContainerGroupProcessor = new GrantPrivilegesContainerGroupProcessor();
  private final CreateContainerProcessor createContainerProcessor = new CreateContainerProcessor();
  private final GrantPrivilegesContainerAPIProcessor grantPrivilegesContainerAPIProcessor = new GrantPrivilegesContainerAPIProcessor();
  private final WriteContainerContentProcessor writeContainerContentProcessor = new WriteContainerContentProcessor();
  private final ConfigureLibrariesProcessor configureLibrariesProcessor = new ConfigureLibrariesProcessor();
  private final DeployContainerContentProcessor deployContainerContentProcessor = new DeployContainerContentProcessor();
  private final GrantPrivilegesContainerSchemaProcessor grantPrivilegesContainerSchemaProcessor = new GrantPrivilegesContainerSchemaProcessor();
  private final GrantPrivilegesExternalArtifactsSchemaProcessor grantPrivilegesExternalArtifactsSchemaProcessor = new GrantPrivilegesExternalArtifactsSchemaProcessor();

  public void execute(Connection connection, HDIDataStructureModel hdiModel) {
    LOGGER.info("Start processing HDI Containers...");
    try {
      LOGGER.info("Start processing HDI Container [{}] from [{}] ...", hdiModel.getContainer(), hdiModel.getLocation());

      // Grant Privileges to Container Group API
      this.grantPrivilegesContainerGroupAPIProcessor.execute(connection, hdiModel.getUsers());

      // Create a Container Group
      this.createContainerGroupProcessor.execute(connection, hdiModel.getGroup());

      List<String> users = new ArrayList<>(Arrays.asList(hdiModel.getUsers()));
      users.add(Configuration.get("HANA_USERNAME"));
      String[] usersAsArray = users.toArray(new String[0]);

      // Grant Privileges to the Container Group
      this.grantPrivilegesContainerGroupProcessor.execute(connection, hdiModel.getGroup(), usersAsArray);

      // Create a Container
      this.createContainerProcessor.execute(connection, hdiModel.getGroup(), hdiModel.getContainer());

      // Grant Privileges to Container API
      this.grantPrivilegesContainerAPIProcessor.execute(connection, hdiModel.getGroup(), hdiModel.getContainer(), usersAsArray);

      // Write the files content to the Container
      this.writeContainerContentProcessor.execute(connection, hdiModel.getContainer(), hdiModel.getDeploy(), hdiModel.getConfiguration());

      // Configure Libraries for the Container
      Set<String> pluginsToBEActivated = new HashSet<>(List.of("com.sap.hana.di.publicsynonym"));
      this.configureLibrariesProcessor.execute(connection, hdiModel.getContainer(), pluginsToBEActivated);

      //Grant Privileges on the external artifacts' schemas
      grantPrivilegesExternalArtifactsSchemaProcessor.execute(connection, hdiModel.getContainer(), hdiModel.getDeploy());

      // Deploy the Content
      this.deployContainerContentProcessor.execute(connection, hdiModel.getContainer(), hdiModel.getDeploy(), hdiModel.getUndeploy());

      // Grant Privileges to the Container Schema
      this.grantPrivilegesContainerSchemaProcessor.execute(connection, hdiModel.getContainer(), usersAsArray);

      LOGGER.info("HDI Container [{}] from [{}] finished successfully.", hdiModel.getContainer(), hdiModel.getLocation());
    } catch (SQLException | IOException | ScriptingException | DataStructuresException e) {
      String errorMessage = String.format("HDI Container %s from %s failed.", hdiModel.getContainer(), hdiModel.getLocation());
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, hdiModel.getLocation(),
          CommonsConstants.HDI_PROCESSOR);
      LOGGER.error(errorMessage, e);
    }

    LOGGER.info("Done processing HDI Containers.");
  }

}
