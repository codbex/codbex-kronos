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

import java.util.Objects;

public class XSKHDBXSODATAEvent {

  private XSKHDBXSODATAEventType type;
  private String action;

  public XSKHDBXSODATAEvent() {
  }

  public XSKHDBXSODATAEvent(XSKHDBXSODATAEventType type, String action) {
    this.type = type;
    this.action = action;
  }

  public XSKHDBXSODATAEventType getType() {
    return type;
  }

  public void setType(XSKHDBXSODATAEventType type) {
    this.type = type;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    XSKHDBXSODATAEvent that = (XSKHDBXSODATAEvent) o;
    return type == that.type && Objects.equals(action, that.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, action);
  }
}
