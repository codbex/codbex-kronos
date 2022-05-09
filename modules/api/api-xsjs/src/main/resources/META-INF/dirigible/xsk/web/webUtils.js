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
var http = require('xsk/http/http');

exports.resolveMethod = function (method) {
    switch (method) {
        case 'GET': return http.GET;
        case 'POST': return http.POST;
        case 'PUT': return http.PUT;
        case 'PATCH': return http.PATCH;
        case 'DELETE': return http.DELETE;
        case 'OPTIONS': return http.OPTIONS;
        case 'HEAD': return http.HEAD;
        case 'TRACE': return http.TRACE;
        case 'CONNECT': return http.CONNECT;
    }
}

exports.getMethodName = function (method) {
    switch (method) {
        case http.GET: return 'GET';
        case http.POST: return 'POST';
        case http.PUT: return 'PUT';
        case http.PATCH: return 'PATCH';
        case http.DELETE: return 'DELETE';
        case http.OPTIONS: return 'OPTIONS';
        case http.HEAD: return 'HEAD';
        case http.TRACE: return 'TRACE';
        case http.CONNECT: return 'CONNECT';
    }
}
