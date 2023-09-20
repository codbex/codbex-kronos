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
package com.codbex.kronos.api.test;

import com.codbex.kronos.api.destination.CloudPlatformDestinationFacade;
import com.codbex.kronos.api.destination.CloudPlatformKymaFacade;
import com.codbex.kronos.api.destination.Destination;
import com.sap.cloud.sdk.cloudplatform.CloudPlatformAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.AuthenticationType;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;

import io.vavr.control.Option;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.net.URI;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * The Class CloudPlatformDestinationFacadeTest.
 */
@RunWith(Parameterized.class)
public class CloudPlatformDestinationFacadeTest {

  /** The http client. */
  private HttpClient httpClient;
  
  /** The destination name. */
  private String destinationName = "test-destination";
  
  /** The destination uri. */
  private String DESTINATION_URI = "http://test-destination.com:8080/destination";
  
  /** The destination accessor. */
  private MockedStatic<DestinationAccessor> destinationAccessor;
  
  /** The http client accessor. */
  private MockedStatic<HttpClientAccessor> httpClientAccessor;
  
  /** The cloud platform accessor. */
  private MockedStatic<CloudPlatformAccessor> cloudPlatformAccessor;
  
  /** The is kyma facade set. */
  private boolean isKymaFacadeSet = false;

  /**
	 * Data.
	 *
	 * @return the collection
	 */
  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {true}, {false}
    });
  }

  /**
	 * Instantiates a new cloud platform destination facade test.
	 *
	 * @param isKymaFacadeSet the is kyma facade set
	 */
  public CloudPlatformDestinationFacadeTest(boolean isKymaFacadeSet) {
    this.isKymaFacadeSet = isKymaFacadeSet;
  }

  /**
	 * Setup.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
  @Before
  public void setup() throws IOException {
    mockRequest();
    mockAccessors(isKymaFacadeSet);
  }

  /**
	 * Close.
	 */
  @After
  public void close() {
    destinationAccessor.close();
    httpClientAccessor.close();
    cloudPlatformAccessor.close();

  }


  /**
	 * Mock accessors.
	 *
	 * @param isKymaFacadeSet the is kyma facade set
	 */
  public void mockAccessors(Boolean isKymaFacadeSet) {
    destinationAccessor = Mockito.mockStatic(DestinationAccessor.class);
    httpClientAccessor = Mockito.mockStatic(HttpClientAccessor.class);
    cloudPlatformAccessor = Mockito.mockStatic(CloudPlatformAccessor.class);

    com.sap.cloud.sdk.cloudplatform.connectivity.Destination mockedDestination = Mockito.mock(
        com.sap.cloud.sdk.cloudplatform.connectivity.Destination.class);
    when(mockedDestination.get("URL")).thenReturn(Option.of(DESTINATION_URI));
    when(mockedDestination.isHttp()).thenReturn(true, false);

    HttpDestination mockedHttpDestination = Mockito.mock(HttpDestination.class);
    when(mockedHttpDestination.getAuthenticationType()).thenReturn(AuthenticationType.NO_AUTHENTICATION);
    when(mockedHttpDestination.getUri()).thenReturn(URI.create(DESTINATION_URI));

    when(mockedDestination.asHttp()).thenReturn(mockedHttpDestination);

    destinationAccessor.when(() -> DestinationAccessor.getDestination(destinationName))
        .thenReturn(mockedDestination, mockedDestination);

    httpClientAccessor.when(() -> HttpClientAccessor.getHttpClient(any()))
        .thenReturn(httpClient);

    cloudPlatformAccessor.when(() -> CloudPlatformAccessor.getCloudPlatformFacade())
        .thenReturn(isKymaFacadeSet ? new CloudPlatformKymaFacade() : null);
  }


  /**
	 * Mock request.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
  public void mockRequest() throws IOException {
    httpClient = Mockito.mock(HttpClient.class);
    HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
    StatusLine statusLine = Mockito.mock(StatusLine.class);

    when(statusLine.getStatusCode()).thenReturn(200);
    when(httpResponse.getStatusLine()).thenReturn(statusLine);
    when(httpResponse.getAllHeaders()).thenReturn(new Header[0]);
    when(httpClient.execute(any())).thenReturn(httpResponse);

    HttpEntity mockedEntity = Mockito.mock(HttpEntity.class);
    when(mockedEntity.getContent()).thenReturn(new ByteArrayInputStream("success".getBytes()));

    when(httpResponse.getEntity()).thenReturn(mockedEntity);
  }

  /**
	 * Gets the destination test.
	 *
	 * @return the destination test
	 * @throws Exception the exception
	 */
  @Test
  public void getDestinationTest() throws Exception {
    Destination dest = CloudPlatformDestinationFacade.getDestination(destinationName);
    assertEquals("test-destination.com", dest.getHost());
    assertEquals(8080, dest.getPort());
    assertEquals("/destination", dest.getPathPrefix());

    // when .isHttp() is false
    Destination dest2 = CloudPlatformDestinationFacade.getDestination(destinationName);
    assertNull(dest2.getHost());
    assertEquals(0, dest2.getPort());
    assertNull(dest2.getPathPrefix());
  }

  /**
	 * Execute destination request test.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void executeDestinationRequestTest() throws Exception {
    String request = "{\"method\": 1, \"queryPath\": \"test-destination.com\", \"headers\": []}";
    String options = "{}";

    String response = CloudPlatformDestinationFacade.executeRequest(request, destinationName, options);
    assertEquals("{\"headers\":[],\"statusCode\":200,\"text\":\"success\"}", response.replaceAll("\\s+",""));
  }

}
