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

/**
 * The Class DataStructureCdsModel.
 */
public class DataStructureCdsModel extends DataStructureModel {

  /** The force update. */
  private boolean forceUpdate;

  /** The table models. */
  private List<DataStructureHDBTableModel> tableModels;

  /** The table type models. */
  private List<DataStructureHDBTableTypeModel> tableTypeModels;

  /** The view models. */
  private List<DataStructureHDBViewModel> viewModels;

  /**
   * Gets the table models.
   *
   * @return the table models
   */
  public List<DataStructureHDBTableModel> getTableModels() {
    return tableModels;
  }

  /**
   * Sets the table models.
   *
   * @param tableModels the new table models
   */
  public void setTableModels(List<DataStructureHDBTableModel> tableModels) {
    this.tableModels = tableModels;
  }

  /**
   * Gets the table type models.
   *
   * @return the table type models
   */
  public List<DataStructureHDBTableTypeModel> getTableTypeModels() {
    return tableTypeModels;
  }

  /**
   * Sets the table type models.
   *
   * @param tableTypeModels the new table type models
   */
  public void setTableTypeModels(List<DataStructureHDBTableTypeModel> tableTypeModels) {
    this.tableTypeModels = tableTypeModels;
  }

  /**
   * Sets the view models.
   *
   * @param viewModels the new view models
   */
  public void setViewModels(List<DataStructureHDBViewModel> viewModels) {
    this.viewModels = viewModels;
  }

  /**
   * Gets the view models.
   *
   * @return the view models
   */
  public List<DataStructureHDBViewModel> getViewModels() {
    return viewModels;
  }

  /**
   * Checks if is force update.
   *
   * @return true, if is force update
   */
  public boolean isForceUpdate() {
    return forceUpdate;
  }

  /**
   * Sets the force update.
   *
   * @param forceUpdate the new force update
   */
  public void setForceUpdate(boolean forceUpdate) {
    this.forceUpdate = forceUpdate;
  }
}
