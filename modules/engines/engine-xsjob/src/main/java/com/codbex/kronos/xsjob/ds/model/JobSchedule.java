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

import java.util.Map;

public class JobSchedule {

  private String description;

  private String signature_version;

  private String xscron;

  private Map<String, String> parameter;

  public JobSchedule() {

  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSignature_version() {
    return signature_version;
  }

  public void setSignature_version(String signature_version) {
    this.signature_version = signature_version;
  }

  public String getXscron() {
    return xscron;
  }

  public void setXscron(String xscron) {
    this.xscron = xscron;
  }

  public Map<String, String> getParameter() {
    return parameter;
  }

  public void setParameter(Map<String, String> parameter) {
    this.parameter = parameter;
  }
}
