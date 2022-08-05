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
package com.codbex.kronos.hdb.ds.model.hdbsequence;

import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.parser.hdbsequence.models.HDBSequenceModel;
import com.codbex.kronos.parser.hdbsequence.utils.HDBSequenceConstants;

public class DataStructureHDBSequenceModel extends DataStructureModel {

  private Integer incrementBy = HDBSequenceConstants.INCREMENT_BY_DEFAULT_VALUE;
  private Integer startWith = HDBSequenceConstants.START_WITH_DEFAULT_VALUE;
  private Integer maxValue;
  private Boolean noMaxValue;
  private Integer minValue = HDBSequenceConstants.MIN_DEFAULT_VALUE;
  private Boolean noMinValue;
  private Boolean cycles;
  private String resetBy;
  private String dependsOnTable;
  private String dependsOnView;
  private Boolean publicProp = HDBSequenceConstants.PUBLIC_DEFAULT_VALUE;

  public DataStructureHDBSequenceModel(){}

  public DataStructureHDBSequenceModel(HDBSequenceModel model){
    this.incrementBy = model.getIncrementBy();
    this.startWith = model.getStartWith();
    this.maxValue = model.getMaxValue();
    this.noMaxValue = model.getNoMaxValue();
    this.noMinValue = model.getNoMinValue();
    this.minValue = model.getMinValue();
    this.cycles = model.getCycles();
    this.resetBy = model.getResetBy();
    this.dependsOnTable = model.getDependsOnTable();
    this.dependsOnView = model.getDependsOnView();
    this.publicProp = model.getPublic();
    this.setSchema(model.getSchema());
  }

  public Integer getIncrementBy() {
    return incrementBy;
  }

  public void setIncrementBy(Integer incrementBy) {
    this.incrementBy = incrementBy;
  }

  public Integer getStartWith() {
    return startWith;
  }

  public void setStartWith(Integer startWith) {
    this.startWith = startWith;
  }

  public Integer getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(Integer maxValue) {
    this.maxValue = maxValue;
  }

  public Boolean getNoMaxValue() {
    return noMaxValue;
  }

  public void setNoMaxValue(Boolean noMaxValue) {
    this.noMaxValue = noMaxValue;
  }

  public Integer getMinValue() {
    return minValue;
  }

  public void setMinValue(Integer minValue) {
    this.minValue = minValue;
  }

  public Boolean getNoMinValue() {
    return noMinValue;
  }

  public void setNoMinValue(Boolean noMinValue) {
    this.noMinValue = noMinValue;
  }

  public Boolean getCycles() {
    return cycles;
  }

  public void setCycles(Boolean cycles) {
    this.cycles = cycles;
  }

  public String getResetBy() {
    return resetBy;
  }

  public void setResetBy(String resetBy) {
    this.resetBy = resetBy;
  }

  public String getDependsOnTable() {
    return dependsOnTable;
  }

  public void setDependsOnTable(String dependsOnTable) {
    this.dependsOnTable = dependsOnTable;
  }

  public String getDependsOnView() {
    return dependsOnView;
  }

  public void setDependsOnView(String dependsOnView) {
    this.dependsOnView = dependsOnView;
  }

  public boolean isPublic() {
    return publicProp;
  }

  public void setPublic(boolean publicProp) {
    this.publicProp = publicProp;
  }
}
