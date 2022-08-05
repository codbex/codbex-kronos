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

import java.sql.SQLException;
import org.eclipse.dirigible.engine.odata2.definition.ODataDefinition;

import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

public class OData2ODataXTransformer {

    private org.eclipse.dirigible.engine.odata2.transformers.OData2ODataXTransformer oData2ODataXTransformer = new org.eclipse.dirigible.engine.odata2.transformers.OData2ODataXTransformer(new TableMetadataProvider(), new XSODataPropertyNameEscaper());

    public String[] transform(ODataDefinition oDataDefinition) throws SQLException {
        try {
            return oData2ODataXTransformer.transform(oDataDefinition);
        } catch (Exception e) {
            CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, oDataDefinition.getLocation(), CommonsConstants.ODATA_PARSER);
            throw e;
        }
    }
}