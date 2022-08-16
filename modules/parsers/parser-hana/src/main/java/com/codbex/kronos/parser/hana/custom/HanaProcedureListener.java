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
package com.codbex.kronos.parser.hana.custom;

import com.codbex.kronos.parser.hana.models.ProcedureDefinitionModel;
import com.codbex.kronos.parser.hana.core.HanaBaseListener;
import com.codbex.kronos.parser.hana.core.HanaParser.Create_procedure_bodyContext;

import org.apache.commons.lang3.StringUtils;

/**
 * The listener interface for receiving hanaProcedure events.
 * The class that is interested in processing a hanaProcedure
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addHanaProcedureListener</code> method. When
 * the hanaProcedure event occurs, that object's appropriate
 * method is invoked.
 *
 */
public class HanaProcedureListener extends HanaBaseListener {

    /** The model. */
    private ProcedureDefinitionModel model;

        /**
         * Exit create procedure body.
         *
         * @param ctx the ctx
         */
        @Override
        public void exitCreate_procedure_body(Create_procedure_bodyContext ctx) {

            String strippedSchema = null;
            String strippedName = null;

            if (ctx.proc_name() != null) {
                if (ctx.proc_name().id_expression() != null) {
                    String maybeQuotedName = ctx.proc_name().id_expression().getText();
                    strippedName = StringUtils.strip(maybeQuotedName, "\"");
                }

                if (ctx.proc_name().schema_name() != null) {
                    String maybeQuotedSchema = ctx.proc_name().schema_name().getText();
                    strippedSchema = StringUtils.strip(maybeQuotedSchema, "\"");
                }
            }

            model = new ProcedureDefinitionModel(strippedSchema, strippedName);
        }

        /**
         * Gets the model.
         *
         * @return the model
         */
        public ProcedureDefinitionModel getModel() {
            return model;
        }
}
