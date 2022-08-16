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
package com.codbex.kronos.xsodata.ds.model;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataService;
import com.codbex.kronos.xsodata.ds.model.ODataModel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ODataModelTest {

    @Test
    public void testEqualsOfTwoObjects(){
        ODataModel model1 = new ODataModel();
        model1.setName("model");
        model1.setLocation("np/model.xsodata");
        model1.setHash("111");
        model1.setCreatedBy("user1");
        model1.setService(new HDBXSODataService());

        ODataModel model2 = new ODataModel();
        model2.setName("model");
        model2.setLocation("np/model.xsodata");
        model2.setHash("111");
        model2.setCreatedBy("user2");
        model2.setService(new HDBXSODataService());

        assertEquals(model1, model2);
    }
}
