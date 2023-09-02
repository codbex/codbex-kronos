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
package com.codbex.kronos.mail;

import com.codbex.kronos.api.destination.CloudPlatformDestinationFacade;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;

import org.eclipse.dirigible.components.api.mail.MailConfigurationProvider;
import org.eclipse.dirigible.commons.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Class DestMailConfigProvider.
 */
public class DestMailConfigProvider implements IMailConfigurationProvider {

  /** The Constant MAIL_USER. */
  private static final String MAIL_USER = "mail.user";
  
  /** The Constant MAIL_PASSWORD. */
  private static final String MAIL_PASSWORD = "mail.password";
  
  /** The Constant MAIL_TRANSPORT_PROTOCOL. */
  private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
  
  /** The Constant PROXY_TYPE. */
  private static final String PROXY_TYPE = "ProxyType";
  
  /** The Constant HOST. */
  private static final String HOST = "host";
  
  /** The Constant PORT. */
  private static final String PORT = "port";
  
  /** The Constant AUTH. */
  private static final String AUTH = "auth";
  
  /** The Constant SOCKS_HOST. */
  private static final String SOCKS_HOST = "socks.host";
  
  /** The Constant SOCKS_PORT. */
  private static final String SOCKS_PORT = "socks.port";
  
  /** The Constant PROXY_USERNAME. */
  private static final String PROXY_USERNAME = "proxy.user";
  
  /** The Constant PROXY_PASSWORD. */
  private static final String PROXY_PASSWORD = "proxy.password";
  
  /** The Constant PROTOCOL_PROPERTIES. */
  private static final List<String> PROTOCOL_PROPERTIES = List.of(HOST, PORT, AUTH, SOCKS_HOST, SOCKS_HOST, SOCKS_PORT, PROXY_USERNAME,
      PROXY_PASSWORD);
  
  /** The Constant MAIL_PROPERTIES. */
  private static final List<String> MAIL_PROPERTIES = Stream.concat(
      PROTOCOL_PROPERTIES.stream().map(p -> Arrays.asList("mail.smtp." + p, "mail.smtps." + p))
          .flatMap(List::stream), Stream.of(MAIL_USER, MAIL_PASSWORD, MAIL_TRANSPORT_PROTOCOL, PROXY_TYPE)).collect(Collectors.toList());

  /** The Constant PROVIDER_NAME. */
  private static final String PROVIDER_NAME = "destination";
  
  /** The Constant DESTINATION_NAME. */
  private static final String DESTINATION_NAME = "MAIL_SERVER_DESTINATION_NAME";

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(DestMailConfigProvider.class);

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return PROVIDER_NAME;
  }

  /**
   * Gets the properties.
   *
   * @return the properties
   */
  @Override
  public Properties getProperties() {
    Properties properties = new Properties();
    try {
      String destinationName = Configuration.get(DESTINATION_NAME);
      Properties destinationProperties = CloudPlatformDestinationFacade.getDestination(destinationName).getProperties();
      for (String key : MAIL_PROPERTIES) {
        if (destinationProperties.containsKey(key)) {
          properties.put(key, destinationProperties.get(key));
        }
      }
    } catch (DestinationAccessException e) {
      logger.error(
          "Cannot find destination for mail configuration. Please check if " + DESTINATION_NAME + " is set and the destination exists.");
    }

    if (properties.isEmpty()) {
      logger.error("Destination object does not contain necessary mail settings!");
    }
    return properties;
  }
}
