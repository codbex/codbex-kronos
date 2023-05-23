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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The Class XSODataPropertyNameEscaperTest.
 */
@ExtendWith(MockitoExtension.class)
public class XSODataPropertyNameEscaperTest {

    /** The escaper. */
    private XSODataPropertyNameEscaper escaper;

    /**
	 * Sets the up.
	 */
    @BeforeEach
    public void setUp() {
        this.escaper = new XSODataPropertyNameEscaper();
    }

    /**
	 * Test escape with dots.
	 */
    @Test
    public void testEscapeWithDots() {
        String propertyName = "Property.Name";
        assertEquals(propertyName, escaper.escape(propertyName), "Unexpected property name");
    }

    /**
	 * Test escape with underscore.
	 */
    @Test
    public void testEscapeWithUnderscore() {
        String propertyName = "Property_Name";
        assertEquals(propertyName, escaper.escape(propertyName), "Unexpected property name");
    }

}