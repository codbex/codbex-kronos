/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.xsodata.transformers;

import java.sql.SQLException;
import java.util.List;

import org.eclipse.dirigible.components.odata.domain.OData;
import org.eclipse.dirigible.components.odata.domain.ODataHandler;

import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class OData2ODataHTransformer.
 */
public class XSOData2ODataHTransformer {

    /** The odata 2 ODataH transformer. */
    private org.eclipse.dirigible.components.odata.transformers.OData2ODataHTransformer oData2ODataHTransformer =
            new org.eclipse.dirigible.components.odata.transformers.OData2ODataHTransformer();

    /**
     * Transform.
     *
     * @param oDataDefinition the o data definition
     * @return the list
     * @throws SQLException the SQL exception
     */
    public List<ODataHandler> transform(OData oDataDefinition) throws SQLException {
        try {
            return oData2ODataHTransformer.transform(oDataDefinition);
        } catch (Exception e) {
            CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, oDataDefinition.getLocation(),
                    CommonsConstants.ODATA_PARSER);
            throw e;
        }
    }
}
