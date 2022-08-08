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
package com.codbex.kronos.hdb.ds.model.hdbsynonym;

import java.util.HashMap;
import java.util.Map;

import com.codbex.kronos.hdb.ds.model.DataStructureModel;

/**
 * The synonym model representation.
 */
public class DataStructureHDBSynonymModel extends DataStructureModel {
  
  /** The synonym definitions. */
  Map<String, HDBSynonymDefinitionModel> synonymDefinitions = new HashMap<>();

  /**
   * Gets the synonym definitions.
   *
   * @return the synonym definitions
   */
  public Map<String, HDBSynonymDefinitionModel> getSynonymDefinitions() {
    return synonymDefinitions;
  }

  /**
   * Sets the synonym definitions.
   *
   * @param synonymDefinitions the synonym definitions
   */
  public void setSynonymDefinitions(Map<String, HDBSynonymDefinitionModel> synonymDefinitions) {
    this.synonymDefinitions = synonymDefinitions;
  }
}
