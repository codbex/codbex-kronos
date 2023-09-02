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

import com.sap.cloud.sdk.cloudplatform.CloudPlatformAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import io.vavr.control.Option;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.eclipse.dirigible.components.api.http.client.HttpClientRequestOptions;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.springframework.stereotype.Component;
import org.eclipse.dirigible.components.api.http.HttpClientFacade;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Properties;

/**
 * The Class CloudPlatformDestinationFacade.
 */
@Component
public class CloudPlatformDestinationFacade {

  /**
   * Gets the destination.
   *
   * @param destinationName the destination name
   * @return the destination
   */
  public static Destination getDestination(String destinationName) {
    setKymaCloudPlatformFacade();

    Properties destinationProperties = new Properties();
    com.sap.cloud.sdk.cloudplatform.connectivity.Destination fetchedDestination = DestinationAccessor.getDestination(destinationName);
    fetchedDestination.getPropertyNames()
        .forEach(propName -> {
          Option<Object> property = fetchedDestination.get(propName);
          if (!property.isEmpty()) {
            destinationProperties.put(propName, property.get());
          }
        });

    if (fetchedDestination.isHttp()) {
      URI uri = URI.create((String) fetchedDestination.get("URL").get());

      return new Destination(uri.getHost(), uri.getPort(), uri.getPath(), destinationProperties);
    } else {
      return new Destination(destinationProperties);
    }
  }

  /**
   * Execute request.
   *
   * @param requestObject the request object
   * @param destinationName the destination name
   * @param options the options
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws MethodNotSupportedException the method not supported exception
   */
  public static String executeRequest(String requestObject, String destinationName, String options)
      throws IOException, MethodNotSupportedException {
    setKymaCloudPlatformFacade();

    DestinationRequest destinationRequest = GsonHelper.fromJson(requestObject, DestinationRequest.class);
    HttpClientRequestOptions parsedOptions = HttpClientFacade.parseOptions(options);
    HttpDestination httpDestination = DestinationAccessor.getDestination(destinationName).asHttp();
    HttpClient client = HttpClientAccessor.getHttpClient(httpDestination);
    String uri = URI.create(httpDestination.getUri() + destinationRequest.getPath()).toString();
    HttpRequestBase request = getRequest(uri, destinationRequest, parsedOptions);
    setRequestHeaders(destinationRequest, request);

    HttpResponse response = client.execute(request);

    Header[] headers = Arrays.stream(response.getAllHeaders()).map(CloudPlatformDestinationFacade::createHeader).toArray(Header[]::new);

    DestinationResponse destinationResponse = new DestinationResponse();
    destinationResponse.setHeaders(headers);
    destinationResponse.setStatusCode(response.getStatusLine().getStatusCode());
    destinationResponse.setText(EntityUtils.toString(response.getEntity()));

    return GsonHelper.toJson(destinationResponse);
  }

  /**
   * Sets the kyma cloud platform facade.
   */
  private static void setKymaCloudPlatformFacade() {
    if (CloudPlatformAccessor.getCloudPlatformFacade() instanceof CloudPlatformKymaFacade) {
      return;
    }

    CloudPlatformAccessor.setCloudPlatformFacade(new CloudPlatformKymaFacade());
  }

  /**
   * Gets the request.
   *
   * @param uri the uri
   * @param destinationRequest the destination request
   * @param options the options
   * @return the request
   * @throws MethodNotSupportedException the method not supported exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static HttpRequestBase getRequest(String uri, DestinationRequest destinationRequest, HttpClientRequestOptions options)
      throws MethodNotSupportedException, IOException {
    switch (destinationRequest.getMethod()) {
      case 0:
        return new HttpOptions();
      case 1:
        return HttpClientFacade.createGetRequest(uri, options);
      case 2:
        return HttpClientFacade.createHeadRequest(uri, options);
      case 3:
        return HttpClientFacade.createPostRequest(uri, options);
      case 4:
        return HttpClientFacade.createPutRequest(uri, options);
      case 5:
        return HttpClientFacade.createDeleteRequest(uri, options);
      case 6:
        return HttpClientFacade.createTraceRequest(uri, options);
      case 7:
        throw new MethodNotSupportedException("Method CONNECT not supported");
      case 8:
        return HttpClientFacade.createPatchRequest(uri, options);
      default:
        throw new MethodNotSupportedException("XSJS method integer not supported");
    }
  }

  /**
   * Sets the request headers.
   *
   * @param destinationRequest the destination request
   * @param request the request
   */
  private static void setRequestHeaders(DestinationRequest destinationRequest, HttpRequestBase request) {
    destinationRequest.getHeaders()
        .forEach(e -> request.setHeader(e.getName(), e.getValue()));
  }

  /**
   * Creates the header.
   *
   * @param header the header
   * @return the header
   */
  private static Header createHeader(org.apache.http.Header header) {
    return new Header(header.getName(), header.getValue());
  }
}
