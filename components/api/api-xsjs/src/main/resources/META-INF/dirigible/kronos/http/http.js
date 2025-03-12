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
import { client as dClient } from "sdk/http";
import * as web from "kronos/web/web";
import { Configurations as config } from "sdk/core";

export const OPTIONS = 0;
export const GET = 1;
export const HEAD = 2;
export const POST = 3;
export const PUT = 4;
export const DEL = 5;
export const TRACE = 6;
export const CONNECT = 7;
export const PATCH = 8;
export const DELETE = 9;

export const CONTINUE = 100;
export const SWITCH_PROTOCOL = 101;
export const OK = 200;
export const CREATED = 201;
export const ACCEPTED = 202;
export const NON_AUTHORITATIVE = 203;
export const NO_CONTENT = 204;
export const RESET_CONTENT = 205;
export const PARTIAL_CONTENT = 206;
export const MULTIPLE_CHOICES = 300;
export const MOVED_PERMANENTLY = 301;
export const FOUND = 302;
export const SEE_OTHER = 303;
export const NOT_MODIFIED = 304;
export const USE_PROXY = 305;
export const TEMPORARY_REDIRECT = 307;
export const BAD_REQUEST = 400;
export const UNAUTHORIZED = 401;
export const PAYMENT_REQUIRED = 402;
export const FORBIDDEN = 403;
export const NOT_FOUND = 404;
export const METHOD_NOT_ALLOWED = 405;
export const NOT_ACCEPTABLE = 406;
export const PROXY_AUTH_REQUIRED = 407;
export const REQUEST_TIMEOUT = 408;
export const CONFLICT = 409;
export const GONE = 410;
export const LENGTH_REQUIRED = 411;
export const PRECONDITION_FAILED = 412;
export const REQUEST_ENTITY_TOO_LARGE = 413;
export const REQUEST_URI_TOO_LONG = 414;
export const UNSUPPORTED_MEDIA_TYPE = 415;
export const REQUESTED_RANGE_NOT_SATISFIABLE = 416;
export const EXPECTATION_FAILED = 417;
export const INTERNAL_SERVER_ERROR = 500;
export const NOT_YET_IMPLEMENTED = 501;
export const BAD_GATEWAY = 502;
export const SERVICE_UNAVAILABLE = 503;
export const GATEWAY_TIMEOUT = 504;
export const HTTP_VERSION_NOT_SUPPORTED = 505;

export function readDestination(destinationPackage, destinationName) {
  const readDestination = com.codbex.kronos.api.destination.CloudPlatformDestinationFacade.getDestination(destinationName);
  let destination = new Destination();

  destination.host = readDestination.getHost();
  destination.port = readDestination.getPort();
  destination.pathPrefix = readDestination.getPathPrefix();
  destination.name = destinationName;

  const properties = JSON.parse(readDestination.getPropertiesAsJSON());

  return Object.assign(destination, properties);
};

export function Client() {
  var clientResponse;
  var connectionTimeout;

  this.request = function (param1, param2, param3) {
    if (typeof param1 === "string") {
      sendRequestWithHttpMethod(param1, param2, param3);
    } else if (typeof param2 === "string") {
      sendRequestObjToUrl(param1, param2, param3);
    } else {
      sendRequestObjToDestination(param1, param2);
    }
    return this; 
  };

  this.close = function () {
    // Empty. Called automatically inside HttpClientFacade
  };

  this.getResponse = function () {
    return getWebResponseByDirigibleResponse(clientResponse);
  };

  this.setTrustStore = function (trustStore) {
    // TODO: No dirigible functionality
  };

  this.setTimeout = function (timeout) {
    connectionTimeout = timeout;
  };

  function sendRequestObjToDestination(requestObj, destination) {
    let options = {};

    if (requestObj.contentType) {
      requestObj.headers.set("Content-Type", requestObj.contentType);
    }

    if (destination.ProxyType === "OnPremise") {
      options.proxyHost = config.get("DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_HOST");
      options.proxyPort = config.get("DIRIGIBLE_CONNECTIVITY_ONPREMISE_PROXY_PORT");
    }

    const requestHeaders = tupelObjectToArray(requestObj.headers);
    addCookieToHeadersFromTupel(requestObj.cookies, requestHeaders);
    requestObj.headers = requestHeaders;

    if (requestObj.parameters !== undefined) {
      requestObj.path = addQueryParametersToUrl(requestObj.path, requestObj.parameters);
    }

    if (requestObj.body) {
      options.text = requestObj.body.asString();
    }

    clientResponse = com.codbex.kronos.api.destination.CloudPlatformDestinationFacade.executeRequest(JSON.stringify(requestObj), destination.name, JSON.stringify(options));
  }

  function sendRequestObjToUrl(requestObj, url, proxy) {
    const fullUrl = url + requestObj.path;

    let options = {};
    if (proxy) {
      options = proxyUrlToOptionsObject(proxy);
    }

    let requestHeaders = tupelObjectToArray(requestObj.headers);
    addCookieToHeadersFromTupel(requestObj.cookies, requestHeaders);
    options.headers = requestHeaders;

    addTimeoutToOptions(options);

    if (requestObj.contentType) {
      options.contentType = requestObj.contentType;
    }

    options.params = tupelObjectToArray(requestObj.parameters);
    clientResponse = executeRequest(fullUrl, requestObj.method, options, requestObj.body);
  }

  function sendRequestWithHttpMethod(requestHttpMethod, url, proxy) {
    let options = {};

    if (proxy) {
      options = proxyUrlToOptionsObject(proxy);
    }

    addTimeoutToOptions(options);
    clientResponse = executeRequest(url, requestHttpMethod, options);
  }

  function proxyUrlToOptionsObject(proxyUrl) {
    let options = {};

    const proxyHostPortPair = proxyUrl.split(":");
    options.proxyHost = proxyHostPortPair[0].trim();
    options.proxyPort = Number.parseInt(proxyHostPortPair[1]).trim();

    return options;
  }

  function addTimeoutToOptions(options) {
    options.connectionRequestTimeout = connectionTimeout;
  }

  function executeRequest(url, requestMethod, options, requestBody) {
    if (requestBody) {
      options.text = requestBody.asString();
    }

    switch (requestMethod) {
      case GET:
        return dClient.get(url, options);
      case POST:
        return dClient.post(url, options);
      case PUT:
        return dClient.put(url, options);
      case DEL:
        return dClient.delete(url, options);
      default:
        throw new Error("Not supported request method.");
    }
  }

  function getWebResponseByDirigibleResponse(dResponse) {
    if (typeof dResponse === 'string' || dResponse instanceof String) {
      dResponse = JSON.parse(dResponse);
    }

    const webResponse = new web.WebResponse(dResponse);
    return webResponse;
  }

  function tupelObjectToArray(tupelObject) {
    if (!tupelObject) {
      return;
    }

    let arr = [];
    for (let i = 0; i < tupelObject.length; i++) {
      arr.push(tupelObject[i]);
    }

    return arr;
  }

  function getUrlParametersFromTupel(tupelParameters) {
    let queryParameterPairs = [];
    for (let i = 0; i < tupelParameters.length; i++) {
      const name = tupelParameters[i].name;
      const value = tupelParameters[i].value;

      const queryPair = name + "=" + (value instanceof Array ? value.join(",") : value);

      queryParameterPairs.push(queryPair);
    }

    return queryParameterPairs.join("&");
  }

  function addQueryParametersToUrl(url, parametersTupel) {
    if (parametersTupel.length === 0) {
      return url;
    }

    const urlQueryParameters = getUrlParametersFromTupel(parametersTupel);
    return url + "?" + urlQueryParameters;
  }

  function addCookieToHeadersFromTupel(tupelCookie, headersArray) {
    if (tupelCookie === undefined && tupelCookie.length === 0) {
      return;
    }

    var cookiesArray = [];
    for (let i = 0; i < tupelCookie.length; i++) {
      const cookieName = tupelCookie[i].name;
      const cookieValue = tupelCookie[i].value;

      cookiesArray.push(cookieName + "=" + cookieValue + ";");
    }

    headersArray.push({ name: "Cookie", value: cookiesArray.join(" ") });
  }
};

export const Request = web.WebRequest;

function processDestValue(destValueArg) {
  if (!destValueArg) {
    return;
  }

  const splitDestValueArray = destValueArg.split('"');
  const destValue = splitDestValueArray.length > 1 ? splitDestValueArray[1].trim() : splitDestValueArray[0].trim();

  return parseDestValue(destValue);
}

function parseDestValue(destValue) {
  if (Number.isInteger(destValue)) {
    return Number.parseInt(destValue);
  } else if (destValue === "true" || destValue === "false") {
    return new Boolean(destValue);
  }

  return destValue;
}

export function Destination() { }
