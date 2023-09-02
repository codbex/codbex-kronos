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
package com.codbex.kronos.engine.hdi.processors;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.dirigible.commons.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.hdi.domain.HDI;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class HDIContainerCreateProcessor.
 */
public class HDIContainerCreateProcessor {

  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(HDIContainerCreateProcessor.class);

  /** The grant privileges container group API processor. */
  private final GrantPrivilegesContainerGroupAPIProcessor grantPrivilegesContainerGroupAPIProcessor;
  
  /** The create container group processor. */
  private final CreateContainerGroupProcessor createContainerGroupProcessor;
  
  /** The grant privileges container group processor. */
  private final GrantPrivilegesContainerGroupProcessor grantPrivilegesContainerGroupProcessor;
  
  /** The create container processor. */
  private final CreateContainerProcessor createContainerProcessor;
  
  /** The grant privileges container API processor. */
  private final GrantPrivilegesContainerAPIProcessor grantPrivilegesContainerAPIProcessor;
  
  /** The write container content processor. */
  private final WriteContainerContentProcessor writeContainerContentProcessor;
  
  /** The configure libraries processor. */
  private final ConfigureLibrariesProcessor configureLibrariesProcessor;
  
  /** The deploy container content processor. */
  private final DeployContainerContentProcessor deployContainerContentProcessor;
  
  /** The grant privileges container schema processor. */
  private final GrantPrivilegesContainerSchemaProcessor grantPrivilegesContainerSchemaProcessor;
  
  /** The grant privileges external artifacts schema processor. */
  private final GrantPrivilegesExternalArtifactsSchemaProcessor grantPrivilegesExternalArtifactsSchemaProcessor;
  
  /** The grant privileges default role processor. */
  private final GrantPrivilegesDefaultRoleProcessor grantPrivilegesDefaultRoleProcessor;

  /**
   * Instantiates a new HDI container create processor.
   */
  public HDIContainerCreateProcessor() {
    this(new GrantPrivilegesContainerGroupAPIProcessor(), new CreateContainerGroupProcessor(), new GrantPrivilegesContainerGroupProcessor(), new CreateContainerProcessor(), new GrantPrivilegesContainerAPIProcessor(), new WriteContainerContentProcessor(), new ConfigureLibrariesProcessor(), new DeployContainerContentProcessor(), new GrantPrivilegesContainerSchemaProcessor(), new GrantPrivilegesExternalArtifactsSchemaProcessor(), new GrantPrivilegesDefaultRoleProcessor());
  }
  
  /**
   * Instantiates a new HDI container create processor.
   *
   * @param grantPrivilegesContainerGroupAPIProcessor the grant privileges container group API processor
   * @param createContainerGroupProcessor the create container group processor
   * @param grantPrivilegesContainerGroupProcessor the grant privileges container group processor
   * @param createContainerProcessor the create container processor
   * @param grantPrivilegesContainerAPIProcessor the grant privileges container API processor
   * @param writeContainerContentProcessor the write container content processor
   * @param configureLibrariesProcessor the configure libraries processor
   * @param deployContainerContentProcessor the deploy container content processor
   * @param grantPrivilegesContainerSchemaProcesso the grant privileges container schema processo
   * @param grantPrivilegesExternalArtifactsSchemaProcessor the grant privileges external artifacts schema processor
   * @param grantPrivilegesDefaultRoleProcessor the grant privileges default role processor
   */
  public HDIContainerCreateProcessor(
      GrantPrivilegesContainerGroupAPIProcessor grantPrivilegesContainerGroupAPIProcessor,
      CreateContainerGroupProcessor createContainerGroupProcessor,
      GrantPrivilegesContainerGroupProcessor grantPrivilegesContainerGroupProcessor,
      CreateContainerProcessor createContainerProcessor,
      GrantPrivilegesContainerAPIProcessor grantPrivilegesContainerAPIProcessor,
      WriteContainerContentProcessor writeContainerContentProcessor,
      ConfigureLibrariesProcessor configureLibrariesProcessor,
      DeployContainerContentProcessor deployContainerContentProcessor,
      GrantPrivilegesContainerSchemaProcessor grantPrivilegesContainerSchemaProcesso,
      GrantPrivilegesExternalArtifactsSchemaProcessor grantPrivilegesExternalArtifactsSchemaProcessor,
      GrantPrivilegesDefaultRoleProcessor grantPrivilegesDefaultRoleProcessor
  ) { //NOSONAR
    this.grantPrivilegesContainerGroupAPIProcessor = grantPrivilegesContainerGroupAPIProcessor;
    this.createContainerGroupProcessor =createContainerGroupProcessor;
    this.grantPrivilegesContainerGroupProcessor = grantPrivilegesContainerGroupProcessor;
    this.createContainerProcessor = createContainerProcessor;
    this.configureLibrariesProcessor = configureLibrariesProcessor;
    this.deployContainerContentProcessor = deployContainerContentProcessor;
    this.grantPrivilegesContainerSchemaProcessor = grantPrivilegesContainerSchemaProcesso;
    this.grantPrivilegesExternalArtifactsSchemaProcessor = grantPrivilegesExternalArtifactsSchemaProcessor;
    this.grantPrivilegesContainerAPIProcessor = grantPrivilegesContainerAPIProcessor;
    this.grantPrivilegesDefaultRoleProcessor = grantPrivilegesDefaultRoleProcessor;
    this.writeContainerContentProcessor = writeContainerContentProcessor;
  }

  /**
   * Execute.
   *
   * @param connection the connection
   * @param hdiModel the hdi model
   * @throws com.codbex.kronos.engine.hdb.api.DataStructuresException 
   */
  public void execute(Connection connection, HDI hdiModel) throws com.codbex.kronos.engine.hdb.api.DataStructuresException {
    LOGGER.info("Start processing HDI Containers...");
    try {
      LOGGER.info("Start processing HDI Container [{}] from [{}] ...", hdiModel.getContainer(), hdiModel.getLocation());

      // Grant Privileges to Container Group API
      this.grantPrivilegesContainerGroupAPIProcessor.execute(connection, hdiModel.getUsers());

      // Create a Container Group
      this.createContainerGroupProcessor.execute(connection, hdiModel.getGroup());

      List<String> users = new ArrayList<>(Arrays.asList(hdiModel.getUsers()));
      String hanaUsername = Configuration.get("HANA_USERNAME");
      users.add(hanaUsername);
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

      // Grant Privileges to the default role
      this.grantPrivilegesDefaultRoleProcessor.execute(connection, hdiModel.getContainer(), hanaUsername, hdiModel.getDeploy());

      // Grant Privileges to the Container Schema
      this.grantPrivilegesContainerSchemaProcessor.execute(connection, hdiModel.getContainer(), usersAsArray);

      LOGGER.info("HDI Container [{}] from [{}] finished successfully.", hdiModel.getContainer(), hdiModel.getLocation());
    } catch (SQLException | IOException e) {
      String errorMessage = String.format("HDI Container %s from %s failed.", hdiModel.getContainer(), hdiModel.getLocation());
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, hdiModel.getLocation(),
          CommonsConstants.HDI_PROCESSOR);
      LOGGER.error(errorMessage, e);
    }

    LOGGER.info("Done processing HDI Containers.");
  }

}
