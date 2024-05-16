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
package com.codbex.kronos.api.destination;

import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import java.util.Properties;

/**
 * The Class Destination.
 */
public class Destination {

    /** The host. */
    private String host;

    /** The port. */
    private int port;

    /** The path prefix. */
    private String pathPrefix;

    /** The properties. */
    private Properties properties = new Properties();

    /**
     * Instantiates a new destination.
     *
     * @param properties the properties
     */
    public Destination(Properties properties) {
        this.properties = properties;
    }

    /**
     * Instantiates a new destination.
     *
     * @param host the host
     * @param port the port
     * @param pathPrefix the path prefix
     * @param properties the properties
     */
    public Destination(String host, int port, String pathPrefix, Properties properties) {
        this.host = host;
        this.port = port;
        this.pathPrefix = pathPrefix;
        this.properties = properties;
    }

    /**
     * Gets the host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the path prefix.
     *
     * @return the path prefix
     */
    public String getPathPrefix() {
        return pathPrefix;
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Gets the properties as JSON.
     *
     * @return the properties as JSON
     */
    public String getPropertiesAsJSON() {
        return GsonHelper.toJson(getProperties());
    }
}
