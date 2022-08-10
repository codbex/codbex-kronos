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

/**
 * The Class HDBXSODataModificationSpec.
 */
public class HDBXSODataModificationSpec {

  /** The events. */
  private List<HDBXSODataEvent> events = new ArrayList<>();
  
  /** The modification action. */
  private String modificationAction;
  
  /** The forbidden. */
  private boolean forbidden = false;

  /**
   * Gets the events.
   *
   * @return the events
   */
  public List<HDBXSODataEvent> getEvents() {
    return events;
  }

  /**
   * Sets the events.
   *
   * @param events the events
   * @return the HDBXSO data modification spec
   */
  public HDBXSODataModificationSpec setEvents(List<HDBXSODataEvent> events) {
    this.events = events;
    return this;
  }

  /**
   * Gets the modification action.
   *
   * @return the modification action
   */
  public String getModificationAction() {
    return modificationAction;
  }

  /**
   * Sets the modification action.
   *
   * @param modificationAction the modification action
   * @return the HDBXSO data modification spec
   */
  public HDBXSODataModificationSpec setModificationAction(String modificationAction) {
    this.modificationAction = modificationAction;
    return this;
  }

  /**
   * Checks if is forbidden.
   *
   * @return true, if is forbidden
   */
  public boolean isForbidden() {
    return forbidden;
  }

  /**
   * Sets the forbidden.
   *
   * @param forbidden the forbidden
   * @return the HDBXSO data modification spec
   */
  public HDBXSODataModificationSpec setForbidden(boolean forbidden) {
    this.forbidden = forbidden;
    return this;
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
    HDBXSODataModificationSpec that = (HDBXSODataModificationSpec) o;
    return forbidden == that.forbidden && Objects.equals(events, that.events) && Objects.equals(modificationAction,
        that.modificationAction);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(events, modificationAction, forbidden);
  }
}
