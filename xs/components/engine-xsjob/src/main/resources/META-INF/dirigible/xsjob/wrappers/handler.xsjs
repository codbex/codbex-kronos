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
var module = __context.get("kronos-job-module");
var jobFunction = __context.get("kronos-job-function");
var jobParameters = __context.get("kronos-job-parameters");

$.import(module)[jobFunction](JSON.parse(jobParameters));