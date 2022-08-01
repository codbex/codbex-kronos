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
package com.codbex.kronos.xsaccess.ds.model.privilege;

import com.codbex.kronos.xsaccess.ds.api.IPrivilegeCoreService;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = IPrivilegeCoreService.PRIVILEGES_TABLE_NAME)
public class PrivilegeDefinition {

  @Id
  @Column(name = "NAME", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String name;

  @Column(name = "DESCRIPTION", columnDefinition = "VARCHAR", nullable = true, length = 255)
  private String description = "";

  @Column(name = "ACCESS_CREATED_BY", columnDefinition = "VARCHAR", nullable = false, length = 32)
  private String createdBy;

  @Column(name = "ACCESS_CREATED_AT", columnDefinition = "TIMESTAMP", nullable = false)
  private Timestamp createdAt;

  public PrivilegeDefinition() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrivilegeDefinition that = (PrivilegeDefinition) o;
    return name.equals(that.name) &&
        description.equals(that.description) &&
        createdBy.equals(that.createdBy) &&
        createdAt.equals(that.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, createdBy, createdAt);
  }
}