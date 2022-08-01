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

import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.HDBTableTypeDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbview.HDBViewDataStructureModel;
import java.util.List;

public class CdsDataStructureModel extends DataStructureModel {

  private boolean forceUpdate;

  private List<HDBTableDataStructureModel> tableModels;

  private List<HDBTableTypeDataStructureModel> tableTypeModels;

  private List<HDBViewDataStructureModel> viewModels;

  public List<HDBTableDataStructureModel> getTableModels() {
    return tableModels;
  }

  public void setTableModels(List<HDBTableDataStructureModel> tableModels) {
    this.tableModels = tableModels;
  }

  public List<HDBTableTypeDataStructureModel> getTableTypeModels() {
    return tableTypeModels;
  }

  public void setTableTypeModels(List<HDBTableTypeDataStructureModel> tableTypeModels) {
    this.tableTypeModels = tableTypeModels;
  }

  public void setViewModels(List<HDBViewDataStructureModel> viewModels) {
    this.viewModels = viewModels;
  }

  public List<HDBViewDataStructureModel> getViewModels() {
    return viewModels;
  }

  public boolean isForceUpdate() {
    return forceUpdate;
  }

  public void setForceUpdate(boolean forceUpdate) {
    this.forceUpdate = forceUpdate;
  }
}
