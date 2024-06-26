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
const dClient = require("sdk/http/client");
const web = require("kronos/web/web");
const config = require("sdk/core/configurations");

exports.OPTIONS = 0;
exports.GET = 1;
exports.HEAD = 2;
exports.POST = 3;
exports.PUT = 4;
exports.DEL = 5;
exports.TRACE = 6;
exports.CONNECT = 7;
exports.PATCH = 8;
exports.DELETE = 9;
exports.DEL = exports.DELETE;

exports.CONTINUE = 100;
exports.SWITCH_PROTOCOL = 101;
exports.OK = 200;
exports.CREATED = 201;
exports.ACCEPTED = 202;
exports.NON_AUTHORITATIVE = 203;
exports.NO_CONTENT = 204;
exports.RESET_CONTENT = 205;
exports.PARTIAL_CONTENT = 206;
exports.MULTIPLE_CHOICES = 300;
exports.MOVED_PERMANENTLY = 301;
exports.FOUND = 302;
exports.SEE_OTHER = 303;
exports.NOT_MODIFIED = 304;
exports.USE_PROXY = 305;
exports.TEMPORARY_REDIRECT = 307;
exports.BAD_REQUEST = 400;
exports.UNAUTHORIZED = 401;
exports.PAYMENT_REQUIRED = 402;
exports.FORBIDDEN = 403;
exports.NOT_FOUND = 404;
exports.METHOD_NOT_ALLOWED = 405;
exports.NOT_ACCEPTABLE = 406;
exports.PROXY_AUTH_REQUIRED = 407;
exports.REQUEST_TIMEOUT = 408;
exports.CONFLICT = 409;
exports.GONE = 410;
exports.LENGTH_REQUIRED = 411;
exports.PRECONDITION_FAILED = 412;
exports.REQUEST_ENTITY_TOO_LARGE = 413;
exports.REQUEST_URI_TOO_LONG = 414;
exports.UNSUPPORTED_MEDIA_TYPE = 415;
exports.REQUESTED_RANGE_NOT_SATISFIABLE = 416;
exports.EXPECTATION_FAILED = 417;
exports.INTERNAL_SERVER_ERROR = 500;
exports.NOT_YET_IMPLEMENTED = 501;
exports.BAD_GATEWAY = 502;
exports.SERVICE_UNAVAILABLE = 503;
exports.GATEWAY_TIMEOUT = 504;
exports.HTTP_VERSION_NOT_SUPPORTED = 505;

exports.readDestination = function (destinationPackage, destinationName) {
  const readDestination = com.codbex.kronos.api.destination.CloudPlatformDestinationFacade.getDestination(destinationName);
  let destination = new exports.Destination();

  destination.host = readDestination.getHost();
  destination.port = readDestination.getPort();
  destination.pathPrefix = readDestination.getPathPrefix();
  destination.name = destinationName;

  const properties = JSON.parse(readDestination.getPropertiesAsJSON());

  return Object.assign(destination, properties);
};

exports.Client = function () {
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
      case exports.GET:
        return dClient.get(url, options);
      case exports.POST:
        return dClient.post(url, options);
      case exports.PUT:
        return dClient.put(url, options);
      case exports.DEL:
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

exports.Request = web.WebRequest;

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

exports.Destination = function () { }
