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
package com.codbex.kronos.xsjob.ds.model;

import java.util.List;

public class XSKJobArtifact {

  private String description;

  private String action;

  private List<XSKJobSchedule> schedules;

  public XSKJobArtifact() {
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public List<XSKJobSchedule> getSchedules() {
    return schedules;
  }

  public void setSchedules(List<XSKJobSchedule> schedules) {
    this.schedules = schedules;
  }
}
