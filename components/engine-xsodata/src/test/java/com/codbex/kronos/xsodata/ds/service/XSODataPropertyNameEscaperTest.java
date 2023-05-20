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
package com.codbex.kronos.xsodata.ds.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.codbex.kronos.xsodata.ds.service.XSODataPropertyNameEscaper;

@RunWith(MockitoJUnitRunner.class)
public class XSODataPropertyNameEscaperTest {

    private XSODataPropertyNameEscaper escaper;

    @Before
    public void setUp() {
        this.escaper = new XSODataPropertyNameEscaper();
    }

    @Test
    public void testEscapeWithDots() {
        String propertyName = "Property.Name";
        assertEquals("Unexpected property name", propertyName, escaper.escape(propertyName));
    }

    @Test
    public void testEscapeWithUnderscore() {
        String propertyName = "Property_Name";
        assertEquals("Unexpected property name", propertyName, escaper.escape(propertyName));
    }

}