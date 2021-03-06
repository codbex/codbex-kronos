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
var repositoryManager = require("platform/v4/repository");

exports.Store = function (filePath) {
    var REGISTRY_DIR_PREFIX = "/registry/public/";
    filePath = REGISTRY_DIR_PREFIX + filePath;

    repositoryManager.getResource(filePath);
    var existsStore = com.codbex.kronos.xssecurestore.ds.facade.XSKSecureStoreFacade.existsStore(filePath);
    if (!existsStore) {
        throw new Error("Not such secure store found.")
    }

    this.store = function(writeObject) {
        com.codbex.kronos.xssecurestore.ds.facade.XSKSecureStoreFacade.store(filePath, writeObject.name, writeObject.value);
    }

    this.storeForUser = function(writeObject) {
        com.codbex.kronos.xssecurestore.ds.facade.XSKSecureStoreFacade.storeForUser(filePath, writeObject.name, writeObject.value);
    }

    this.readForUser = function(readObject) {
        return com.codbex.kronos.xssecurestore.ds.facade.XSKSecureStoreFacade.readForUser(filePath, readObject.name);
    }

    this.read = function(readObject) {
        return com.codbex.kronos.xssecurestore.ds.facade.XSKSecureStoreFacade.read(filePath, readObject.name);
    }

    this.remove = function(removeObject) {
        com.codbex.kronos.xssecurestore.ds.facade.XSKSecureStoreFacade.remove(filePath, removeObject.name);
    }

    this.removeForUser = function(removeObject) {
        com.codbex.kronos.xssecurestore.ds.facade.XSKSecureStoreFacade.removeForUser(filePath, removeObject.name);
    }
}