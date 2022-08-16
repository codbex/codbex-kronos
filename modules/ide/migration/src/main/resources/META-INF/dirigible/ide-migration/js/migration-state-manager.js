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
migrationLaunchView.factory("$messageHub", [
    function () {
        const messageHub = new FramesMessageHub();
        const announceAlert = function (title, message, type) {
            messageHub.post(
                {
                    data: {
                        title: title,
                        message: message,
                        type: type,
                    },
                },
                "ide.alert"
            );
        };
        const announceAlertError = function (title, message) {
            announceAlert(title, message, "error");
        };
        const message = function (evtName, data) {
            messageHub.post({ data: data }, evtName);
        };
        const on = function (topic, callback) {
            messageHub.subscribe(callback, topic);
        };
        return {
            openNext: null,
            openState: null
        };



    },
]);