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
 * API Update
 *
 */

exports.execute = function(sql, parameters, datasourceName) {
	let result = {};
	if (parameters) {
		const params = JSON.stringify(parameters);
		result = org.eclipse.dirigible.components.api.db.DatabaseFacade.update(sql,params,datasourceName)
	} else {
		result = org.eclipse.dirigible.components.api.db.DatabaseFacade.update(sql,null,datasourceName);
	}
	return result;
};
