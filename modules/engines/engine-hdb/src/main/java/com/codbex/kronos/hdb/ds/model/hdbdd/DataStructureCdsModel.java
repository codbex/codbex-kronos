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

import java.util.List;

import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.model.hdbview.DataStructureHDBViewModel;

public class DataStructureCdsModel extends DataStructureModel {

  private boolean forceUpdate;

  private List<DataStructureHDBTableModel> tableModels;

  private List<DataStructureHDBTableTypeModel> tableTypeModels;

  private List<DataStructureHDBViewModel> viewModels;

  public List<DataStructureHDBTableModel> getTableModels() {
    return tableModels;
  }

  public void setTableModels(List<DataStructureHDBTableModel> tableModels) {
    this.tableModels = tableModels;
  }

  public List<DataStructureHDBTableTypeModel> getTableTypeModels() {
    return tableTypeModels;
  }

  public void setTableTypeModels(List<DataStructureHDBTableTypeModel> tableTypeModels) {
    this.tableTypeModels = tableTypeModels;
  }

  public void setViewModels(List<DataStructureHDBViewModel> viewModels) {
    this.viewModels = viewModels;
  }

  public List<DataStructureHDBViewModel> getViewModels() {
    return viewModels;
  }

  public boolean isForceUpdate() {
    return forceUpdate;
  }

  public void setForceUpdate(boolean forceUpdate) {
    this.forceUpdate = forceUpdate;
  }
}
