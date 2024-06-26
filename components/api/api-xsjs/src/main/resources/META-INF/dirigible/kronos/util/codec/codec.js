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
var base64 = require('sdk/utils/base64');
var hex = require('sdk/utils/hex');

exports.encodeHex = function(data) {
	if (typeof data === 'string') {
		return hex.encode(data);
	}
	return hex.encode(arrayBufferToString(data));
}

exports.encodeBase64 = function(data) {
	if (typeof data === 'string') {
		return base64.encode(data);
	}
	return base64.encode(arrayBufferToString(data));
}

exports.decodeHex = function(hexData) {
	return hex.decode(hexData);
}

exports.decodeBase64 = function(base64Data) {
	return base64.decode(base64Data);
}

function arrayBufferToString(buf) {
	return String.fromCharCode.apply(null, new Uint16Array(buf));
}