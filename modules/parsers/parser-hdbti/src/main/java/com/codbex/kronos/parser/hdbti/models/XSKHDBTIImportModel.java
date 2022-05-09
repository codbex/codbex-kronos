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
package com.codbex.kronos.parser.hdbti.models;

import java.util.ArrayList;
import java.util.List;

public class XSKHDBTIImportModel {

  private List<XSKHDBTIImportConfigModel> configModels = new ArrayList<>();

  public List<XSKHDBTIImportConfigModel> getConfigModels() {
    return configModels;
  }

  public void setConfigModels(List<XSKHDBTIImportConfigModel> configModels) {
    this.configModels = configModels;
  }

  @Override
  public String toString() {
    return "import = " + configModels.toString() + ";";
  }

  public void checkMandatoryFieldsInAllConfigModels() {
    configModels.forEach(XSKHDBTIImportConfigModel::checkForAllMandatoryFieldsPresence);
  }
}
