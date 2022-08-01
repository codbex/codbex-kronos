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
package com.codbex.kronos.parser.xsodata.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HDBXSODataSetting {

  private boolean enableSupportNull;
  private List<String> contentCacheControl = new ArrayList<>();
  private List<String> metadataCacheControl = new ArrayList<>();
  private List<String> hints = new ArrayList<>();
  private String maxRecords;
  private String maxExpandedRecords;

  public boolean isEnableSupportNull() {
    return enableSupportNull;
  }

  public HDBXSODataSetting setEnableSupportNull(boolean enableSupportNull) {
    this.enableSupportNull = enableSupportNull;
    return this;
  }

  public List<String> getContentCacheControl() {
    return contentCacheControl;
  }

  public HDBXSODataSetting setContentCacheControl(List<String> contentCacheControl) {
    this.contentCacheControl = contentCacheControl;
    return this;
  }

  public List<String> getMetadataCacheControl() {
    return metadataCacheControl;
  }

  public HDBXSODataSetting setMetadataCacheControl(List<String> metadataCacheControl) {
    this.metadataCacheControl = metadataCacheControl;
    return this;
  }

  public List<String> getHints() {
    return hints;
  }

  public HDBXSODataSetting setHints(List<String> hints) {
    this.hints = hints;
    return this;
  }

  public String getMaxRecords() {
    return maxRecords;
  }

  public HDBXSODataSetting setMaxRecords(String maxRecords) {
    this.maxRecords = maxRecords;
    return this;
  }

  public String getMaxExpandedRecords() {
    return maxExpandedRecords;
  }

  public HDBXSODataSetting setMaxExpandedRecords(String maxExpandedRecords) {
    this.maxExpandedRecords = maxExpandedRecords;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HDBXSODataSetting that = (HDBXSODataSetting) o;
    return enableSupportNull == that.enableSupportNull && Objects.equals(contentCacheControl, that.contentCacheControl) && Objects.equals(
        metadataCacheControl, that.metadataCacheControl) && Objects.equals(hints, that.hints) && Objects.equals(maxRecords, that.maxRecords)
        && Objects.equals(maxExpandedRecords, that.maxExpandedRecords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enableSupportNull, contentCacheControl, metadataCacheControl, hints, maxRecords, maxExpandedRecords);
  }
}
