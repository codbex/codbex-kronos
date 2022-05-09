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
import org.eclipse.dirigible.engine.odata2.transformers.OData2ODataXTransformer;

import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;

public class XSKOData2ODataXTransformer {

    private OData2ODataXTransformer oData2ODataXTransformer = new OData2ODataXTransformer(new XSKTableMetadataProvider(), new XSODataPropertyNameEscaper());

    public String[] transform(ODataDefinition oDataDefinition) throws SQLException {
        try {
            return oData2ODataXTransformer.transform(oDataDefinition);
        } catch (Exception e) {
            XSKCommonsUtils.logProcessorErrors(e.getMessage(), XSKCommonsConstants.PROCESSOR_ERROR, oDataDefinition.getLocation(), XSKCommonsConstants.XSK_ODATA_PARSER);
            throw e;
        }
    }
}