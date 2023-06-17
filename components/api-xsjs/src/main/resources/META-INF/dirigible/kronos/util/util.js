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
var uuid = require("utils/uuid");
var bytes = require("io/bytes");

exports.codec = require("kronos/util/codec/codec");

exports.createUuid = function () {
  return uuid.random();
}

exports.stringify = function (arrayBuffer) {
  return bytes.byteArrayToText(arrayBuffer);
}

exports.Zip = function (zipParams) {
  zipParams = zipParams || {};
  var source = zipParams.source || null;
  var index = zipParams.index || null;
  var settings = zipParams.settings || {};

  var password = settings.password || null;
  var maxUncompressedSizeInBytes = settings.maxUncompressedSizeInBytes || null;

  // TODO: Check source type. Currently implemented only for byte array.
  // TODO: Index must be provided when source is a ResultSet.
  // TODO: Apply settings if provided.

  if (source) {
    var zipContent = JSON.parse(bytes.byteArrayToText(source));
    for (var file in zipContent) {
      this[file] = zipContent[file];
    }
  }

  this._password_; // TODO: Should always return undefined.
  this._metadata_; // TODO: Should return data about the zip.

  this.asArrayBuffer = function () {
    var {_password_, _metadata_, asArrayBuffer, ...zipContent} = this;
    var content = JSON.stringify(zipContent);

    return bytes.textToByteArray(content);
    ;
  };
}
