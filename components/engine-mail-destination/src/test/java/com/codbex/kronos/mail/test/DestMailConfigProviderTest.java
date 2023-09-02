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
package com.codbex.kronos.mail.test;

import com.codbex.kronos.api.destination.CloudPlatformDestinationFacade;
import com.codbex.kronos.mail.DestMailConfigProvider;

import org.eclipse.dirigible.commons.config.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DestMailConfigProviderTest {

  private static final String MAIL_SERVER_DESTINATION_NAME = "MAIL_SERVER_DESTINATION_NAME";
  private static final String DESTINATION_NAME = "test-destination";

  private MockedStatic<CloudPlatformDestinationFacade> cloudPlatformDestinationFacadeMock;
  private MockedStatic<Configuration>configurationMock;

  @Before
  public void setup() {
    mock();
  }

  @After
  public void close() {
    configurationMock.close();
    cloudPlatformDestinationFacadeMock.close();
  }

  public void mock() {
    Properties props = new Properties();
    props.setProperty("mail.user", "user@codbex.com");

    configurationMock = Mockito.mockStatic(Configuration.class);
    configurationMock.when(() -> Configuration.get(MAIL_SERVER_DESTINATION_NAME))
        .thenReturn(DESTINATION_NAME);
    com.codbex.kronos.api.destination.Destination mockedDestination = Mockito.mock(com.codbex.kronos.api.destination.Destination.class);
    when(mockedDestination.getProperties()).thenReturn(props);

    cloudPlatformDestinationFacadeMock = Mockito.mockStatic(CloudPlatformDestinationFacade.class);
    cloudPlatformDestinationFacadeMock.when(() -> CloudPlatformDestinationFacade.getDestination(DESTINATION_NAME))
        .thenReturn(mockedDestination);
  }

  @Test
  public void getPropertiesTest() {
    DestMailConfigProvider provider = new DestMailConfigProvider();
    Properties properties = provider.getProperties();
    assertEquals(1, properties.size());
  }

}
