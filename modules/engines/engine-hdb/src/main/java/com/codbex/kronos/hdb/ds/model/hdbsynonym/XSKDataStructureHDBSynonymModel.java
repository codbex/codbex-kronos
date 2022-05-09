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

import com.codbex.kronos.hdb.ds.model.XSKDataStructureModel;

/**
 * The synonym model representation.
 */
public class XSKDataStructureHDBSynonymModel extends XSKDataStructureModel {
  Map<String, XSKHDBSYNONYMDefinitionModel> synonymDefinitions = new HashMap<>();

  public Map<String, XSKHDBSYNONYMDefinitionModel> getSynonymDefinitions() {
    return synonymDefinitions;
  }

  public void setSynonymDefinitions(Map<String, XSKHDBSYNONYMDefinitionModel> synonymDefinitions) {
    this.synonymDefinitions = synonymDefinitions;
  }
}
