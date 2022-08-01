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

import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableColumnDataStructureModel;
import java.util.ArrayList;
import java.util.List;

public class HDBTableTypeDataStructureModel extends DataStructureModel {


  private List<HDBTableColumnDataStructureModel> columns = new ArrayList<>();
  private HDBTableTypePrimaryKeyDataStructureModel primaryKey = new HDBTableTypePrimaryKeyDataStructureModel();
  private Boolean publicProp;

  public List<HDBTableColumnDataStructureModel> getColumns() {
    return columns;
  }

  public void setColumns(List<HDBTableColumnDataStructureModel> columns) {
    this.columns = columns;
  }

  public HDBTableTypePrimaryKeyDataStructureModel getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(HDBTableTypePrimaryKeyDataStructureModel primaryKey) {
    this.primaryKey = primaryKey;
  }

  public Boolean isPublicProp() {
    return publicProp;
  }

  public void setPublicProp(Boolean publicProp) {
    this.publicProp = publicProp;
  }
}
