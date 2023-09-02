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
package com.codbex.kronos.hdi.ds.processors;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.dirigible.commons.config.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdi.domain.HDI;
import com.codbex.kronos.engine.hdi.processors.ConfigureLibrariesProcessor;
import com.codbex.kronos.engine.hdi.processors.CreateContainerGroupProcessor;
import com.codbex.kronos.engine.hdi.processors.CreateContainerProcessor;
import com.codbex.kronos.engine.hdi.processors.DeployContainerContentProcessor;
import com.codbex.kronos.engine.hdi.processors.GrantPrivilegesContainerAPIProcessor;
import com.codbex.kronos.engine.hdi.processors.GrantPrivilegesContainerGroupAPIProcessor;
import com.codbex.kronos.engine.hdi.processors.GrantPrivilegesContainerGroupProcessor;
import com.codbex.kronos.engine.hdi.processors.GrantPrivilegesContainerSchemaProcessor;
import com.codbex.kronos.engine.hdi.processors.GrantPrivilegesDefaultRoleProcessor;
import com.codbex.kronos.engine.hdi.processors.GrantPrivilegesExternalArtifactsSchemaProcessor;
import com.codbex.kronos.engine.hdi.processors.HDIContainerCreateProcessor;
import com.codbex.kronos.engine.hdi.processors.WriteContainerContentProcessor;

@ExtendWith(MockitoExtension.class)
public class HDIContainerCreateProcessorTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Connection mockConnection;

  @Test
  public void testExecute() throws SQLException, IOException, DataStructuresException {

    GrantPrivilegesContainerGroupAPIProcessor grantPrivilegesContainerGroupAPIProcessor = Mockito.mock(GrantPrivilegesContainerGroupAPIProcessor.class);
    CreateContainerGroupProcessor createContainerGroupProcessor = Mockito.mock(CreateContainerGroupProcessor.class);
    GrantPrivilegesContainerGroupProcessor grantPrivilegesContainerGroupProcessor = Mockito.mock(GrantPrivilegesContainerGroupProcessor.class);
    CreateContainerProcessor createContainerProcessor = Mockito.mock(CreateContainerProcessor.class);
    GrantPrivilegesContainerAPIProcessor grantPrivilegesContainerAPIProcessor = Mockito.mock(GrantPrivilegesContainerAPIProcessor.class);
    WriteContainerContentProcessor writeContainerContentProcessor = Mockito.mock(WriteContainerContentProcessor.class);
    ConfigureLibrariesProcessor configureLibrariesProcessor = Mockito.mock(ConfigureLibrariesProcessor.class);
    DeployContainerContentProcessor deployContainerContentProcessor = Mockito.mock(DeployContainerContentProcessor.class);
    GrantPrivilegesContainerSchemaProcessor grantPrivilegesContainerSchemaProcessor = Mockito.mock(GrantPrivilegesContainerSchemaProcessor.class);
    GrantPrivilegesExternalArtifactsSchemaProcessor grantPrivilegesExternalArtifactsSchemaProcessor = Mockito.mock(GrantPrivilegesExternalArtifactsSchemaProcessor.class);
    GrantPrivilegesDefaultRoleProcessor grantPrivilegesDefaultRoleProcessor = Mockito.mock(GrantPrivilegesDefaultRoleProcessor.class);

    HDIContainerCreateProcessor processor = new HDIContainerCreateProcessor(grantPrivilegesContainerGroupAPIProcessor,
        createContainerGroupProcessor,
        grantPrivilegesContainerGroupProcessor,
        createContainerProcessor,
        grantPrivilegesContainerAPIProcessor,
        writeContainerContentProcessor,
        configureLibrariesProcessor,
        deployContainerContentProcessor,
        grantPrivilegesContainerSchemaProcessor,
        grantPrivilegesExternalArtifactsSchemaProcessor,
        grantPrivilegesDefaultRoleProcessor
    );
    HDI hdiModel = new HDI();

    String container = "test-container";
    String[] files = new String[]{};
    String[] users = new String[]{};
    String group = "test-group";
    String[] deploy = new String[]{};
    String[] undeploy = new String[]{};
    String name = "test.hdi";
    String config = "test-config";

    hdiModel.setUsers(users);
    hdiModel.setContainer(container);
    hdiModel.setGroup(group);
    hdiModel.setDeploy(deploy);
    hdiModel.setUndeploy(undeploy);
    hdiModel.setConfiguration(config);
    hdiModel.setName(name);

    processor.execute(mockConnection, hdiModel);

    verify(grantPrivilegesContainerGroupAPIProcessor, times(1)).execute(mockConnection, users);
    verify(createContainerGroupProcessor, times(1)).execute(mockConnection, group);
    verify(grantPrivilegesContainerGroupProcessor, times(1)).execute(mockConnection, group, users);
    verify(createContainerProcessor, times(1)).execute(mockConnection, group, container);
    verify(grantPrivilegesContainerAPIProcessor, times(1)).execute(mockConnection, group, container, users);
    verify(writeContainerContentProcessor, times(1)).execute(mockConnection, container, files, config);
    verify(deployContainerContentProcessor, times(1)).execute(mockConnection, container, deploy, undeploy);
    verify(grantPrivilegesContainerSchemaProcessor, times(1)).execute(mockConnection, container, users);
    verify(grantPrivilegesExternalArtifactsSchemaProcessor, times(1)).execute(mockConnection, container, deploy);
    verify(grantPrivilegesDefaultRoleProcessor, times(1)).execute(mockConnection, hdiModel.getContainer(), Configuration.get("HANA_USERNAME"), hdiModel.getDeploy());
  }

}
