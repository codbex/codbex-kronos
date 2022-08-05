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
package com.codbex.kronos.parser.hdbsequence.custom;


import com.codbex.kronos.parser.hdbsequence.exceptions.HDBSequenceMissingPropertyException;
import com.codbex.kronos.parser.hdbsequence.models.HDBSequenceModel;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Objects;

public class HDBSequenceModelAdapter implements JsonDeserializer<HDBSequenceModel> {

    protected static String handleStringLiteral(String value) {
        if (value != null && value.length() > 1) {
            String subStr = value.substring(1, value.length() - 1);
            String escapedQuote = subStr.replace("\\\"", "\"");
            return escapedQuote.replace("\\\\", "\\");
        }

        return value;
    }

    @Override
    public HDBSequenceModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        HDBSequenceModel model = new Gson().fromJson(jsonElement, type);
        if (model.hasMandatoryFieldsMissing()) {
            String properties = String.join(",", model.getMissingProps());
            throw new HDBSequenceMissingPropertyException(String.format("Missing mandatory fields among  %s!", properties));
        }
        model.setSchema(handleStringLiteral(model.getSchema()));
        if (Objects.nonNull(model.getResetBy())) {
            model.setResetBy(handleStringLiteral(model.getResetBy()));
        }
        if (Objects.nonNull(model.getDependsOnTable())) {
            model.setDependsOnTable(handleStringLiteral(model.getDependsOnTable()));
        }
        if (Objects.nonNull(model.getDependsOnView())) {
            model.setDependsOnView(handleStringLiteral(model.getDependsOnView()));
        }
        return model;
    }
}
