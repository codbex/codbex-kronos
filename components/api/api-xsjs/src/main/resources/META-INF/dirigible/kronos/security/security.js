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
import { repository as repositoryManager } from "sdk/platform";

export function Store(filePath) {
    var REGISTRY_DIR_PREFIX = "/registry/public/";
    filePath = REGISTRY_DIR_PREFIX + filePath;

    repositoryManager.getResource(filePath);
    var existsStore = com.codbex.kronos.xssecurestore.ds.facade.SecureStoreFacade.existsStore(filePath);
    if (!existsStore) {
        throw new Error("Not such secure store found.")
    }

    this.store = function(writeObject) {
        com.codbex.kronos.xssecurestore.ds.facade.SecureStoreFacade.store(filePath, writeObject.name, writeObject.value);
    }

    this.storeForUser = function(writeObject) {
        com.codbex.kronos.xssecurestore.ds.facade.SecureStoreFacade.storeForUser(filePath, writeObject.name, writeObject.value);
    }

    this.readForUser = function(readObject) {
        return com.codbex.kronos.xssecurestore.ds.facade.SecureStoreFacade.readForUser(filePath, readObject.name);
    }

    this.read = function(readObject) {
        return com.codbex.kronos.xssecurestore.ds.facade.SecureStoreFacade.read(filePath, readObject.name);
    }

    this.remove = function(removeObject) {
        com.codbex.kronos.xssecurestore.ds.facade.SecureStoreFacade.remove(filePath, removeObject.name);
    }

    this.removeForUser = function(removeObject) {
        com.codbex.kronos.xssecurestore.ds.facade.SecureStoreFacade.removeForUser(filePath, removeObject.name);
    }
}

class XSCrypto {

	md5(data, key) {
		if (data instanceof ArrayBuffer) {
			data = fromBufferToArray(data);
		}
		const javaBytes = Java.type("com.codbex.kronos.xssecurestore.ds.facade.SecureCryptoFacade").md5(data, key);
		return fromArrayToBuffer(javaBytes);
	}

	sha1(data, key) {
		if (data instanceof ArrayBuffer) {
			data = fromBufferToArray(data);
		}
		const javaBytes = Java.type("com.codbex.kronos.xssecurestore.ds.facade.SecureCryptoFacade").sha1(data, key);
		return fromArrayToBuffer(javaBytes);
	}

	sha256(data, key) {
		if (data instanceof ArrayBuffer) {
			data = fromBufferToArray(data);
		}
		const javaBytes = Java.type("com.codbex.kronos.xssecurestore.ds.facade.SecureCryptoFacade").sha256(data, key);
		return fromArrayToBuffer(javaBytes);
	}
}

export const crypto = new XSCrypto();

// From ArrayBuffer to byte[]
function fromBufferToArray(buffer) {
	return Array.from(new Uint8Array(buffer))
}

// from Java byte[]  to JS ArrayBuffer
function fromArrayToBuffer(javaBytes) {
	var uint8Array = new Uint8Array(javaBytes.length);
	uint8Array.set(Java.from(javaBytes));
	return uint8Array.buffer
}
