/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
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

/**
 * The Class HDBTIImportModel.
 */
public class HDBTIImportModel {

  /** The config models. */
  private List<HDBTIImportConfigModel> configModels = new ArrayList<>();

  /**
   * Gets the config models.
   *
   * @return the config models
   */
  public List<HDBTIImportConfigModel> getConfigModels() {
    return configModels;
  }

  /**
   * Sets the config models.
   *
   * @param configModels the new config models
   */
  public void setConfigModels(List<HDBTIImportConfigModel> configModels) {
    this.configModels = configModels;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "import = " + configModels.toString() + ";";
  }

  /**
   * Check mandatory fields in all config models.
   */
  public void checkMandatoryFieldsInAllConfigModels() {
    configModels.forEach(HDBTIImportConfigModel::checkForAllMandatoryFieldsPresence);
  }
}
