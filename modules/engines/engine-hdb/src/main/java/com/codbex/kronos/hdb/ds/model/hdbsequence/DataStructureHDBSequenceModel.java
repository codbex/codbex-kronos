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

/**
 * The Class DataStructureHDBSequenceModel.
 */
public class DataStructureHDBSequenceModel extends DataStructureModel {

  /** The increment by. */
  private Integer incrementBy = HDBSequenceConstants.INCREMENT_BY_DEFAULT_VALUE;
  
  /** The start with. */
  private Integer startWith = HDBSequenceConstants.START_WITH_DEFAULT_VALUE;
  
  /** The max value. */
  private Integer maxValue;
  
  /** The no max value. */
  private Boolean noMaxValue;
  
  /** The min value. */
  private Integer minValue = HDBSequenceConstants.MIN_DEFAULT_VALUE;
  
  /** The no min value. */
  private Boolean noMinValue;
  
  /** The cycles. */
  private Boolean cycles;
  
  /** The reset by. */
  private String resetBy;
  
  /** The depends on table. */
  private String dependsOnTable;
  
  /** The depends on view. */
  private String dependsOnView;
  
  /** The public prop. */
  private Boolean publicProp = HDBSequenceConstants.PUBLIC_DEFAULT_VALUE;

  /**
   * Instantiates a new data structure HDB sequence model.
   */
  public DataStructureHDBSequenceModel(){}

  /**
   * Instantiates a new data structure HDB sequence model.
   *
   * @param model the model
   */
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

  /**
   * Gets the increment by.
   *
   * @return the increment by
   */
  public Integer getIncrementBy() {
    return incrementBy;
  }

  /**
   * Sets the increment by.
   *
   * @param incrementBy the new increment by
   */
  public void setIncrementBy(Integer incrementBy) {
    this.incrementBy = incrementBy;
  }

  /**
   * Gets the start with.
   *
   * @return the start with
   */
  public Integer getStartWith() {
    return startWith;
  }

  /**
   * Sets the start with.
   *
   * @param startWith the new start with
   */
  public void setStartWith(Integer startWith) {
    this.startWith = startWith;
  }

  /**
   * Gets the max value.
   *
   * @return the max value
   */
  public Integer getMaxValue() {
    return maxValue;
  }

  /**
   * Sets the max value.
   *
   * @param maxValue the new max value
   */
  public void setMaxValue(Integer maxValue) {
    this.maxValue = maxValue;
  }

  /**
   * Gets the no max value.
   *
   * @return the no max value
   */
  public Boolean getNoMaxValue() {
    return noMaxValue;
  }

  /**
   * Sets the no max value.
   *
   * @param noMaxValue the new no max value
   */
  public void setNoMaxValue(Boolean noMaxValue) {
    this.noMaxValue = noMaxValue;
  }

  /**
   * Gets the min value.
   *
   * @return the min value
   */
  public Integer getMinValue() {
    return minValue;
  }

  /**
   * Sets the min value.
   *
   * @param minValue the new min value
   */
  public void setMinValue(Integer minValue) {
    this.minValue = minValue;
  }

  /**
   * Gets the no min value.
   *
   * @return the no min value
   */
  public Boolean getNoMinValue() {
    return noMinValue;
  }

  /**
   * Sets the no min value.
   *
   * @param noMinValue the new no min value
   */
  public void setNoMinValue(Boolean noMinValue) {
    this.noMinValue = noMinValue;
  }

  /**
   * Gets the cycles.
   *
   * @return the cycles
   */
  public Boolean getCycles() {
    return cycles;
  }

  /**
   * Sets the cycles.
   *
   * @param cycles the new cycles
   */
  public void setCycles(Boolean cycles) {
    this.cycles = cycles;
  }

  /**
   * Gets the reset by.
   *
   * @return the reset by
   */
  public String getResetBy() {
    return resetBy;
  }

  /**
   * Sets the reset by.
   *
   * @param resetBy the new reset by
   */
  public void setResetBy(String resetBy) {
    this.resetBy = resetBy;
  }

  /**
   * Gets the depends on table.
   *
   * @return the depends on table
   */
  public String getDependsOnTable() {
    return dependsOnTable;
  }

  /**
   * Sets the depends on table.
   *
   * @param dependsOnTable the new depends on table
   */
  public void setDependsOnTable(String dependsOnTable) {
    this.dependsOnTable = dependsOnTable;
  }

  /**
   * Gets the depends on view.
   *
   * @return the depends on view
   */
  public String getDependsOnView() {
    return dependsOnView;
  }

  /**
   * Sets the depends on view.
   *
   * @param dependsOnView the new depends on view
   */
  public void setDependsOnView(String dependsOnView) {
    this.dependsOnView = dependsOnView;
  }

  /**
   * Checks if is public.
   *
   * @return true, if is public
   */
  public boolean isPublic() {
    return publicProp;
  }

  /**
   * Sets the public.
   *
   * @param publicProp the new public
   */
  public void setPublic(boolean publicProp) {
    this.publicProp = publicProp;
  }
}
