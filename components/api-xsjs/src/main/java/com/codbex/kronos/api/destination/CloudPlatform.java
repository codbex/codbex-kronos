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
package com.codbex.kronos.api.destination;

import com.google.gson.JsonObject;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;
import com.sap.cloud.sdk.cloudplatform.exception.CloudPlatformException;
import org.eclipse.dirigible.commons.config.Configuration;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * The Class CloudPlatform.
 */
public class CloudPlatform extends ScpCfCloudPlatform {

	/** The Constant CLIENT_ID. */
	private static final String CLIENT_ID = "clientid";
	
	/** The Constant CLIENT_SECRET. */
	private static final String CLIENT_SECRET = "clientsecret";
	
	/** The Constant URL. */
	private static final String URL = "url";
	
	/** The Constant URI. */
	private static final String URI = "uri";

	/** The Constant ONPREMISE_PROXY_HOST. */
	private static final String ONPREMISE_PROXY_HOST = "onpremise_proxy_host";
	
	/** The Constant ONPREMISE_PROXY_HTTP_PORT. */
	private static final String ONPREMISE_PROXY_HTTP_PORT = "onpremise_proxy_http_port";
	
	/** The Constant ONPREMISE_PROXY_LDAP_PORT. */
	private static final String ONPREMISE_PROXY_LDAP_PORT = "onpremise_proxy_ldap_port";
	
	/** The Constant ONPREMISE_PROXY_PORT. */
	private static final String ONPREMISE_PROXY_PORT = "onpremise_proxy_port";
	
	/** The Constant ONPREMISE_PROXY_RFC_PORT. */
	private static final String ONPREMISE_PROXY_RFC_PORT = "onpremise_proxy_rfc_port";
	
	/** The Constant ONPREMISE_SOCKS5_PROXY_PORT. */
	private static final String ONPREMISE_SOCKS5_PROXY_PORT = "onpremise_socks5_proxy_port";

	/** The Constant DIRIGIBLE_DESTINATION_CLIENT_ID. */
	private static final String DIRIGIBLE_DESTINATION_CLIENT_ID = "DIRIGIBLE_DESTINATION_CLIENT_ID";
	
	/** The Constant DIRIGIBLE_DESTINATION_CLIENT_SECRET. */
	private static final String DIRIGIBLE_DESTINATION_CLIENT_SECRET = "DIRIGIBLE_DESTINATION_CLIENT_SECRET";
	
	/** The Constant DIRIGIBLE_DESTINATION_URL. */
	private static final String DIRIGIBLE_DESTINATION_URL = "DIRIGIBLE_DESTINATION_URL";
	
	/** The Constant DIRIGIBLE_DESTINATION_URI. */
	private static final String DIRIGIBLE_DESTINATION_URI = "DIRIGIBLE_DESTINATION_URI";

	/** The Constant DIRIGIBLE_CONNECTIVITY_CLIENT_ID. */
	private static final String DIRIGIBLE_CONNECTIVITY_CLIENT_ID = "DIRIGIBLE_CONNECTIVITY_CLIENT_ID";
	
	/** The Constant DIRIGIBLE_CONNECTIVITY_CLIENT_SECRET. */
	private static final String DIRIGIBLE_CONNECTIVITY_CLIENT_SECRET = "DIRIGIBLE_CONNECTIVITY_CLIENT_SECRET";
	
	/** The Constant DIRIGIBLE_CONNECTIVITY_URL. */
	private static final String DIRIGIBLE_CONNECTIVITY_URL = "DIRIGIBLE_CONNECTIVITY_URL";
	
	/** The Constant DIRIGIBLE_CONNECTIVITY_URI. */
	private static final String DIRIGIBLE_CONNECTIVITY_URI = "DIRIGIBLE_CONNECTIVITY_URI";
	
	/** The Constant DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_HOST. */
	private static final String DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_HOST = "DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_HOST";
	
	/** The Constant DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_HTTP_PORT. */
	private static final String DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_HTTP_PORT = "DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_HTTP_PORT";
	
	/** The Constant DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_LDAP_PORT. */
	private static final String DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_LDAP_PORT = "DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_LDAP_PORT";
	
	/** The Constant DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_PORT. */
	private static final String DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_PORT = "DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_PORT";
	
	/** The Constant DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_RFC_PORT. */
	private static final String DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_RFC_PORT = "DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_RFC_PORT";
	
	/** The Constant DIRIGIBLE_CONNECTIVITY_ONPREMISE_SOCKS5_PROXY_PORT. */
	private static final String DIRIGIBLE_CONNECTIVITY_ONPREMISE_SOCKS5_PROXY_PORT = "DIRIGIBLE_CONNECTIVITY_ONPREMISE_SOCKS5_PROXY_PORT";

	/**
	 * Gets the service credentials.
	 *
	 * @param serviceName the service name
	 * @param servicePlan the service plan
	 * @return the service credentials
	 * @throws CloudPlatformException the cloud platform exception
	 */
	@Nonnull
	@Override
	public JsonObject getServiceCredentials(@Nonnull String serviceName, @Nullable String servicePlan) throws CloudPlatformException {
		JsonObject credentialsObject = new JsonObject();
		switch (serviceName) {
		case "destination":
			credentialsObject.addProperty(CLIENT_ID, Configuration.get(DIRIGIBLE_DESTINATION_CLIENT_ID));
			credentialsObject.addProperty(CLIENT_SECRET, Configuration.get(DIRIGIBLE_DESTINATION_CLIENT_SECRET));
			credentialsObject.addProperty(URL, Configuration.get(DIRIGIBLE_DESTINATION_URL));
			credentialsObject.addProperty(URI, Configuration.get(DIRIGIBLE_DESTINATION_URI));
			break;
		case "connectivity":
			credentialsObject.addProperty(CLIENT_ID, Configuration.get(DIRIGIBLE_CONNECTIVITY_CLIENT_ID));
			credentialsObject.addProperty(CLIENT_SECRET, Configuration.get(DIRIGIBLE_CONNECTIVITY_CLIENT_SECRET));
			credentialsObject.addProperty(URL, Configuration.get(DIRIGIBLE_CONNECTIVITY_URL));
			credentialsObject.addProperty(URI, Configuration.get(DIRIGIBLE_CONNECTIVITY_URI));
			credentialsObject.addProperty(ONPREMISE_PROXY_HOST, Configuration.get(DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_HOST));
			credentialsObject.addProperty(ONPREMISE_PROXY_HTTP_PORT, Configuration.get(DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_HTTP_PORT));
			credentialsObject.addProperty(ONPREMISE_PROXY_LDAP_PORT, Configuration.get(DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_LDAP_PORT));
			credentialsObject.addProperty(ONPREMISE_PROXY_PORT, Configuration.get(DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_PORT));
			credentialsObject.addProperty(ONPREMISE_PROXY_RFC_PORT, Configuration.get(DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_RFC_PORT));
			credentialsObject.addProperty(ONPREMISE_SOCKS5_PROXY_PORT, Configuration.get(DIRIGIBLE_CONNECTIVITY_ONPREMISE_SOCKS5_PROXY_PORT));
			break;
		default:
			throw new IllegalArgumentException(
					"Retrieving credentials for service [" + serviceName + "] not supported");
		}

		return credentialsObject;
	}

}
