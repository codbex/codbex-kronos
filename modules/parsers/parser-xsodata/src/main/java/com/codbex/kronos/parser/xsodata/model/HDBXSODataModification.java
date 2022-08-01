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

public class HDBXSODataModification {

  private HDBXSODataHandlerMethod method;
  private HDBXSODataModificationSpec specification;

  public HDBXSODataHandlerMethod getMethod() {
    return method;
  }

  public HDBXSODataModification setMethod(HDBXSODataHandlerMethod method) {
    this.method = method;
    return this;
  }

  public HDBXSODataModificationSpec getSpecification() {
    return specification;
  }

  public HDBXSODataModification setSpecification(HDBXSODataModificationSpec specification) {
    this.specification = specification;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HDBXSODataModification that = (HDBXSODataModification) o;
    return method == that.method && Objects.equals(specification, that.specification);
  }

  @Override
  public int hashCode() {
    return Objects.hash(method, specification);
  }
}