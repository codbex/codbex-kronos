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

public class HDBXSODataEvent {

  private HDBXSODataEventType type;
  private String action;

  public HDBXSODataEvent() {
  }

  public HDBXSODataEvent(HDBXSODataEventType type, String action) {
    this.type = type;
    this.action = action;
  }

  public HDBXSODataEventType getType() {
    return type;
  }

  public void setType(HDBXSODataEventType type) {
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
    HDBXSODataEvent that = (HDBXSODataEvent) o;
    return type == that.type && Objects.equals(action, that.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, action);
  }
}
