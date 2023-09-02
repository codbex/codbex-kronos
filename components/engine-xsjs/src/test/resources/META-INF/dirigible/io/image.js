/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
/**
 * API Image
 */
const streams = require("io/streams");

exports.resize = function(original, type, width, height) {
	const native = org.eclipse.dirigible.components.api.io.ImageFacade(original, type, width, height);
	const inputStream = new streams.InputStream();
	inputStream.native = native;
	return inputStream;
};
