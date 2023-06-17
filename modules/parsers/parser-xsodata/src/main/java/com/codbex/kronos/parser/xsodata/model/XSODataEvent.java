/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
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

/**
 * The Class HDBXSODataEvent.
 */
public class XSODataEvent {

  /** The type. */
  private XSODataEventType type;
  
  /** The action. */
  private String action;

  /**
   * Instantiates a new HDBXSO data event.
   */
  public XSODataEvent() {
  }

  /**
   * Instantiates a new HDBXSO data event.
   *
   * @param type the type
   * @param action the action
   */
  public XSODataEvent(XSODataEventType type, String action) {
    this.type = type;
    this.action = action;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public XSODataEventType getType() {
    return type;
  }

  /**
   * Sets the type.
   *
   * @param type the new type
   */
  public void setType(XSODataEventType type) {
    this.type = type;
  }

  /**
   * Gets the action.
   *
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /**
   * Sets the action.
   *
   * @param action the new action
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * Equals.
   *
   * @param o the o
   * @return true, if successful
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    XSODataEvent that = (XSODataEvent) o;
    return type == that.type && Objects.equals(action, that.action);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(type, action);
  }
}
