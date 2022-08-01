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
package com.codbex.kronos.xssecurestore.ds.facade;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureCryptoFacade {
    private static final String HMAC_MD_5 = "HmacMD5";
    private static final String HMAC_SHA_1 = "HmacSHA1";
    private static final String HMAC_SHA_256 = "HmacSHA256";
    private static final String SHA256 = "SHA-256";
    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA1";

    private SecureCryptoFacade() {
    }

    public static byte[] md5(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encryptHash(data, key, MD5, HMAC_MD_5);
    }

    public static byte[] md5(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encryptHash(data, key, MD5, HMAC_MD_5);
    }

    public static byte[] sha1(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encryptHash(data, key, SHA1, HMAC_SHA_1);
    }

    public static byte[] sha1(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encryptHash(data, key, SHA1, HMAC_SHA_1);
    }

    public static byte[] sha256(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encryptHash(data, key, SHA256, HMAC_SHA_256);
    }

    public static byte[] sha256(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encryptHash(data, key, SHA256, HMAC_SHA_256);
    }

    public static byte[] encryptHash(String data, String key, String encryptType, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        if (key != null) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } else {
            MessageDigest digest = MessageDigest.getInstance(encryptType);
            return digest.digest(data.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static byte[] encryptHash(byte[] data, String key, String encryptType, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        if (key != null) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);
            return mac.doFinal(data);
        } else {
            MessageDigest digest = MessageDigest.getInstance(encryptType);
            return digest.digest(data);
        }
    }
}
