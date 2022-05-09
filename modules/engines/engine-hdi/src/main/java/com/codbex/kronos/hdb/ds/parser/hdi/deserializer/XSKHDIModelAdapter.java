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
package com.codbex.kronos.hdb.ds.parser.hdi.deserializer;

import com.codbex.kronos.hdb.ds.model.hdi.XSKDataStructureHDIModel;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;


public class XSKHDIModelAdapter implements JsonDeserializer<XSKDataStructureHDIModel> {


  @Override
  public XSKDataStructureHDIModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    XSKDataStructureHDIModel hdiModel = new Gson().fromJson(jsonElement, type);
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