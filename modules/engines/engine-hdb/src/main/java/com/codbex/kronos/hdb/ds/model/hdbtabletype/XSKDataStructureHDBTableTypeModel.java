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
package com.codbex.kronos.hdb.ds.model.hdbtabletype;

import com.codbex.kronos.hdb.ds.model.XSKDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.XSKDataStructureHDBTableColumnModel;

import java.util.ArrayList;
import java.util.List;

public class XSKDataStructureHDBTableTypeModel extends XSKDataStructureModel {


  private List<XSKDataStructureHDBTableColumnModel> columns = new ArrayList<>();
  private XSKDataStructureHDBTableTypePrimaryKeyModel primaryKey = new XSKDataStructureHDBTableTypePrimaryKeyModel();
  private Boolean publicProp;

  public List<XSKDataStructureHDBTableColumnModel> getColumns() {
    return columns;
  }

  public void setColumns(List<XSKDataStructureHDBTableColumnModel> columns) {
    this.columns = columns;
  }

  public XSKDataStructureHDBTableTypePrimaryKeyModel getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(XSKDataStructureHDBTableTypePrimaryKeyModel primaryKey) {
    this.primaryKey = primaryKey;
  }

  public Boolean isPublicProp() {
    return publicProp;
  }

  public void setPublicProp(Boolean publicProp) {
    this.publicProp = publicProp;
  }
}
