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

/**
 * The Class JobSchedule.
 */
public class JobSchedule {

  /** The description. */
  private String description;

  /** The signature version. */
  private String signature_version;

  /** The xscron. */
  private String xscron;

  /** The parameter. */
  private Map<String, String> parameter;

  /**
   * Instantiates a new job schedule.
   */
  public JobSchedule() {

  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the signature version.
   *
   * @return the signature version
   */
  public String getSignature_version() {
    return signature_version;
  }

  /**
   * Sets the signature version.
   *
   * @param signature_version the new signature version
   */
  public void setSignature_version(String signature_version) {
    this.signature_version = signature_version;
  }

  /**
   * Gets the xscron.
   *
   * @return the xscron
   */
  public String getXscron() {
    return xscron;
  }

  /**
   * Sets the xscron.
   *
   * @param xscron the new xscron
   */
  public void setXscron(String xscron) {
    this.xscron = xscron;
  }

  /**
   * Gets the parameter.
   *
   * @return the parameter
   */
  public Map<String, String> getParameter() {
    return parameter;
  }

  /**
   * Sets the parameter.
   *
   * @param parameter the parameter
   */
  public void setParameter(Map<String, String> parameter) {
    this.parameter = parameter;
  }
}
