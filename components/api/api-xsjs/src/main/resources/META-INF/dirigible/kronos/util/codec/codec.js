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
import { base64 } from 'sdk/utils';
import { hex } from 'sdk/utils';

export function encodeHex(data) {
	if (typeof data === 'string') {
		return hex.encode(data);
	}
	return hex.encode(arrayBufferToString(data));
}

export function encodeBase64(data) {
	if (typeof data === 'string') {
		return base64.encode(data);
	}
	return base64.encode(arrayBufferToString(data));
}

export function decodeHex(hexData) {
	return hex.decode(hexData);
}

export function decodeBase64(base64Data) {
	return base64.decode(base64Data);
}

function arrayBufferToString(buf) {
	return String.fromCharCode.apply(null, new Uint16Array(buf));
}