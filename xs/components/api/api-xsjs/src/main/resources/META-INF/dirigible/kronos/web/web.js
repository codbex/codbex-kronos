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
/*
 * HANA XS Classic Bridge for Web API
 */

import { request as dRequest, response as dResponse, upload as dUpload} from 'sdk/http';
import * as session from 'kronos/session/session';
import { zip, bytes } from 'sdk/io';

import * as WEB_UTILS from 'kronos/web/webUtils';

var SET_COOKIE_HEADER = "Set-Cookie";

const TupelList = function (dArrayOfContents, isReadOnly) {
  var internalDArrayOfContents = dArrayOfContents ? dArrayOfContents : [];
  syncWithContentsArray.call(this);

  this.get = function (name) {
    var requestedElement = internalDArrayOfContents.find(function (element) {
      return element.name === name;
    });
    return requestedElement ? requestedElement.value : undefined;
  };

  this.remove = function (name) {
    if (isReadOnly) {
      notAllowed();
    }

    var length = internalDArrayOfContents.length;
    internalDArrayOfContents = internalDArrayOfContents.filter(function (element) {
      return element.name !== name;
    });

    if (this.length === length) {
      syncWithContentsArray.call(this);
      throw new Error("No tuple was removed");
    }
  };

  this.set = function (name, value, options) {
    if (isReadOnly) {
      notAllowed();
    }

    let element = internalDArrayOfContents.find(function (element) {
      return element.name.toUpperCase() === name.toUpperCase();
    });
    if (element) {
      element.value = value;
      if (options) {
        assignOptionsToCookie(element, options)
      }
    } else {
      element = {};
      element.name = name;
      element.value = value;
      if (options) {
        assignOptionsToCookie(element, options);
      }
      internalDArrayOfContents.push(element);
    }
    syncWithContentsArray.call(this);
  };

  function assignOptionsToCookie(cookie, options) {
    cookie.path = options.path;
    cookie.domain = options.domain;
    cookie.maxAge = options.expires;
    cookie.secure = options.secure;
    cookie.httpOnly = options.httpOnly;
  }

  function syncWithContentsArray() {
    for (var i = 0; i < internalDArrayOfContents.length; i++) {
      this[i] = internalDArrayOfContents[i];
    }

    Object.defineProperty(this, "length", {
      value: internalDArrayOfContents.length,
      writable: true,
      enumerable: false
    });
  }
};

const EntityList = function (webEntitiesArray, isReadOnly) {
  syncWithWebEntitiesArray.call(this);

  this.create = function () {
    if (isReadOnly) {
      notAllowed();
    }

    webEntitiesArray.push(new WebEntityResponse());
    syncWithWebEntitiesArray.call(this);
  };

  function syncWithWebEntitiesArray() {
    for (var i = 0; i < webEntitiesArray.length; i++) {
      this[i] = webEntitiesArray[i];
    }

    Object.defineProperty(this, "length", {
      value: webEntitiesArray.length,
      writable: true,
      enumerable: false
    });
  }
};

const Body = function (bodyValue) {
  this.asArrayBuffer = function () {
    if (bodyValue) {
      return bodyValue;
    } else {
      return dRequest.getBytes();
    }
  };

  this.asString = function () {
    if (bodyValue) {
      return String.fromCharCode.apply(null, bodyValue);
    } else {
      return dRequest.getText();
    }
  };

  this.asWebRequest = function () {
    if (bodyValue) {
      // ToDo
    } else {
      throw new Error("Cannot be used on root request.")
    }
  };
};

export function WebRequest(method, path) {
  if (typeof method === 'number') {
    if (typeof path !== 'string') {
      throw new Error('Expected string as a path (second argument)');
    }

    WebEntityRequest.call(this);
    this.method = method;
    this.cookies = new TupelList([], false);
    this.language = '';
    this.path = path || '';

    Object.defineProperty(this, "queryPath", {
      get() {
        throw new Error('Not supported for outbound requests');
      }
    });
  } else {
    const XSJS_FILE_EXTENSION_LENGTH = 5;
    const XSJS_FILE_EXTENSION = ".xsjs";

    this.body = new Body(null);
    this.contentType = dRequest.getContentType() || '';
    this.cookies = new TupelList(dRequest.getCookies(), true);

    this.entities = function () {
      var dEntitiesArray = [];
      if (dRequest.getMethod() === 'POST') {
        if (dUpload.isMultipartContent()) {
          var multipartItems = dUpload.parseRequest();
          for (let i = 0; i < multipartItems.size(); i++) {
            var multipartItem = multipartItems.get(i);
            var requestEntity = new WebEntityRequest();

            if (multipartItem.isFormField()) {
              var fieldName = multipartItem.getFieldName();
              var fieldValue = multipartItem.getText();

              requestEntity.parameters.set(fieldName, fieldValue);
            }

            var multipartItemHeaders = multipartItem.getHeaders();
            var headerNames = multipartItemHeaders.getHeaderNames();

            for (let j = 0; j < headerNames.size(); j++) {
              var headerName = headerNames.get(j);
              var headerValue = multipartItemHeaders.getHeader(headerName);
              requestEntity.headers.set(headerName, headerValue);
            }

            requestEntity.setBody(multipartItem);
            requestEntity.contentType = multipartItem.getContentType();
            dEntitiesArray.push(requestEntity);
          }
        }
      }

      return new EntityList(dEntitiesArray, true);
    }();

    this.language = session.language || '';
    this.method = WEB_UTILS.resolveMethod(dRequest.getMethod());

    this.path = function () {
      var dRequestURI = dRequest.getRequestURI();
      var filePlaceInURI = dRequestURI.indexOf(XSJS_FILE_EXTENSION);
      return dRequestURI.substring(0, filePlaceInURI + XSJS_FILE_EXTENSION_LENGTH + dRequestURI.length);
    }();

    this.queryPath = function () {
      var dRequestURI = dRequest.getRequestURI();
      var filePlaceInURI = dRequestURI.indexOf(XSJS_FILE_EXTENSION);
      return dRequestURI.substring(filePlaceInURI + XSJS_FILE_EXTENSION_LENGTH, dRequestURI.length);
    }();

    this.headers = function () {
      var dHeadersArray = [];
      var headerNamesArray = dRequest.getHeaderNames();

      headerNamesArray.forEach(headerName => {
        var headerValues = dRequest.getHeaders(headerName);
        headerValues.forEach(headerValue => {
          if (headerName === 'cookie' || headerName === 'authorization') {
            headerValue = '';
          }

          dHeadersArray.push({
            "name": headerName,
            "value": headerValue
          });
        })
      });

      return new TupelList(dHeadersArray, true);
    }.bind(this)();

    this.parameters = function () {
      var dArrayOfPairs = dRequest.getParameters();
      dArrayOfPairs = transformParametersObject(dArrayOfPairs);
      return new TupelList(dArrayOfPairs, true);
    }();

    this.setBody = cannotModifyBody;
  }

  function transformParametersObject(dParametersObject) {
    var arrayToReturn = [];
    Object.keys(dParametersObject).map(key => {
      for (var i = 0; i < dParametersObject[key].length; i++) {
        arrayToReturn.push({
          "name": key,
          "value": dParametersObject[key][i]
        });
      }
    });
    return arrayToReturn;
  };
};

export function WebResponse(clientResponse) {
  if (clientResponse) {
    WebEntityResponse.call(this);
    this.body = new Body(clientResponse.text.getBytes());
    this.cookies = new TupelList(getCookieFromClientResponse(clientResponse), true);
    this.headers = new TupelList(clientResponse.headers, true);
    this.status = clientResponse.statusCode;
  } else {
    this.body = notSupported;
    this.cacheControl;
    this.contentType;
    this.cookies = new TupelList([], false);
    this.entities = new EntityList([], false);
    this.headers = new TupelList([], false);
    this.status; // from $.net.http

    this.followUp = function (followUpObject) {
      var {
        uri: uri,
        functionName: functionName,
        parameter: parameters
      } = followUpObject

      if (uri && functionName) {
        try {
          var params = new Array();

          if (parameters && typeof parameters === 'object') {
            for (var param in parameters) {
              params.push(parameters[param]);
            }
          }

          var splitUri = uri.split(":");
          var pathToFile = splitUri[0].replace(".", "/");
          var fileName = splitUri[1];

          var parsedUri = pathToFile + "/" + fileName;

          var module = require(parsedUri);
          var func = module[functionName];
          func.apply(this, params);
        } catch (error) {
          if (error instanceof Error) {
            throw new Error(error.message);
          }
        }
      }
    };

    this.setBody = function (content) {
      if (this.contentType) {
        dResponse.setContentType(this.contentType);
      }

      if (this.status) {
        dResponse.setStatus(this.status);
      }

      syncHeaders.bind(this)();
      syncCookies.bind(this)();

      if (content instanceof Array && this.contentType === 'application/zip') {
        var parsedContent = JSON.parse(bytes.byteArrayToText(content));
        var outputStream = dResponse.getOutputStream();
        if (outputStream.isValid()) {
          try {
            var zipOutputStream = zip.createZipOutputStream(outputStream);
            for (let file in parsedContent) {
              zipOutputStream.createZipEntry(file);
              zipOutputStream.writeText(parsedContent[file]);
            }
          } finally {
            zipOutputStream.close();
          }
        }
      } else {
        dResponse.print(content);
        dResponse.flush();
        dResponse.close();
      }
    };
  }

  function syncHeaders() {
    var responseHeaders = this.headers;
    for (var headerId = 0; headerId < responseHeaders.length; headerId++) {
      var header = responseHeaders[headerId];
      dResponse.setHeader(header.name, header.value);
    }
  };

  function syncCookies() {
    var responseCookies = this.cookies;
    for (var cookieId = 0; cookieId < responseCookies.length; cookieId++) {
      dResponse.addCookie(responseCookies[cookieId]);
    }
  };

  function getCookieFromClientResponse(clientResponse) {
    var headers = clientResponse.headers;
    var cookieObjArray = [];

    headers.forEach(header => {
      if (header.name === SET_COOKIE_HEADER) {
        var cookieKeyValue = header.value.split(";")[0].trim();
        var cookieKeyValueArray = cookieKeyValue.split("=");
        var cookieObj = {
          name: cookieKeyValueArray[0],
          value: cookieKeyValueArray[1]
        };
        cookieObjArray.push(cookieObj);
      }
    });

    return cookieObjArray;
  }
};

const WebEntityRequest = function () {
  this.body = new Body([]);
  this.contentType = '';
  this.entities = new EntityList([], false);
  this.headers = new TupelList([], false);
  this.parameters = new TupelList([], false);

  this.setBody = function (body) {
    this.body = new Body(body.getBytes());
  };
};

const WebEntityResponse = function () {
  this.body = new Body(null);
  this.contentType = '';
  this.entities = new EntityList([], true);
  this.headers = new TupelList([], true);
  this.setBody = cannotModifyBody;
};

function notAllowed() {
  throw new Error('This is only allowed for currently populated response and outbound request objects');
}

function cannotModifyBody() {
  throw new Error('Cannot modify body on this object');
}

function notSupported() {
  throw new Error('Not supported');
}
