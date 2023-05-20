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

/**
 * The Class OData2ODataMTransformer.
 */
public class OData2ODataMTransformer {

    /** The o data 2 O data M transformer. */
    private org.eclipse.dirigible.engine.odata2.transformers.OData2ODataMTransformer oData2ODataMTransformer = new org.eclipse.dirigible.engine.odata2.transformers.OData2ODataMTransformer(new TableMetadataProvider(), new XSODataPropertyNameEscaper());

    /**
     * Transform.
     *
     * @param oDataDefinition the o data definition
     * @return the string[]
     * @throws SQLException the SQL exception
     */
    public String[] transform(ODataDefinition oDataDefinition) throws SQLException {
        try {
            return oData2ODataMTransformer.transform(oDataDefinition);
        } catch (Exception e) {
            CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, oDataDefinition.getLocation(), CommonsConstants.ODATA_PARSER);
            throw e;
        }
    }
}
