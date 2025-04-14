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
package com.codbex.kronos.integration.tests.applications.status;

public class ProjectHttpCheck {

  private final String endpointToCall;
  private final Integer expectedStatusCode;
  private String expectedBodyMessage;

  public ProjectHttpCheck(String endpointToCall, Integer expectedStatusCode, String expectedBodyMessage) {
    this(endpointToCall, expectedStatusCode);
    this.expectedBodyMessage = expectedBodyMessage;
  }

  public ProjectHttpCheck(String endpointToCall, Integer expectedStatusCode) {
    this.endpointToCall = endpointToCall;
    this.expectedStatusCode = expectedStatusCode;
  }

  public String getEndpointToCall() {
    return endpointToCall;
  }

  public Integer getExpectedStatusCode() {
    return expectedStatusCode;
  }

  public String getExpectedBodyMessage() {
    return expectedBodyMessage;
  }
}
