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
package com.codbex.kronos.engine.hdi.parser;

import com.codbex.kronos.engine.hdi.domain.HDI;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * The Class HDIModelAdapter.
 */
public class HDIModelAdapter implements JsonDeserializer<HDI> {

  /**
   * Deserialize.
   *
   * @param jsonElement the json element
   * @param type the type
   * @param context the context
   * @return the data structure HDI model
   * @throws JsonParseException the json parse exception
   */
  @Override
  public HDI deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    HDI hdiModel = new Gson().fromJson(jsonElement, type);
    if (hdiModel.isMandatoryFieldMissing()) {
      throw new JsonParseException("Mandatory field among configuration/users/group/container is missing!");
    }
    if (!hdiModel.hasDeploymentFile()) {
      throw new JsonParseException("Both deploy and undeploy cannot be empty arrays!");
    }

    if (hdiModel.hasMisusedDeploymentFile()) {
      throw new JsonParseException("Both deploy and undeploy cannot have to same file!");
    }
    return hdiModel;
  }
}