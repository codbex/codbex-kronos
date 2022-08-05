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
package com.codbex.kronos.hdb.ds.model.hdbdd;

import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;

/**
 * The table model representation.
 */
public class DataStructureEntitiesModel extends DataStructureHDBTableModel {

  private String namespace;

  private String schema;

  private DataStructureContextModel context;

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public DataStructureContextModel getContext() {
    return context;
  }

  public void setContext(DataStructureContextModel context) {
    this.context = context;
  }

}