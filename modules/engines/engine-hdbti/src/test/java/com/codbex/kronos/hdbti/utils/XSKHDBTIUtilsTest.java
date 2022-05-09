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
package com.codbex.kronos.hdbti.utils;

import org.junit.Test;

import com.codbex.kronos.hdbti.utils.XSKHDBTIUtils;

import static org.junit.Assert.assertEquals;

public class XSKHDBTIUtilsTest {
    @Test
    public void testConvertHDBTIFilePropertyToPathSuccessfully() {
        String fileNamePath = "acme.ti2.demo:myData.csv";
        assertEquals(XSKHDBTIUtils.convertHDBTIFilePropertyToPath(fileNamePath), "/acme/ti2/demo/myData.csv");
    }

    @Test
    public void testConvertPathToHDBTIFilePropertySuccessfully() {
        String fileNamePath = "/acme/ti2/demo/myData.csv";
        assertEquals(XSKHDBTIUtils.convertPathToHDBTIFileProperty(fileNamePath), "acme.ti2.demo:myData.csv");
    }

    @Test
    public void testConvertPathToHDBTIFilePropertySuccessfully2() {
        String fileNamePath = "acme/ti2/demo/myData.csv";
        assertEquals(XSKHDBTIUtils.convertPathToHDBTIFileProperty(fileNamePath), "acme.ti2.demo:myData.csv");
    }
}
