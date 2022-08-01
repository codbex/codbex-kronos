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

public class HDBXSODataRepositoryObject {

  /**
   * Catalog object name
   * The name can represent repoobject or catalogobject.
   * Repoobject is represented as "repopackage '/' reponame '.' repoextension"
   * * <pre>{@code
   *      service {
   *          "teamdemo/MY_REPO_NAME.REPO_NAME" as "MyTable";
   *      }
   * }</pre>
   * Catalogobject is represented as "catalogobjectschema '.' catalogobjectname"
   * <pre>{@code
   *      service {
   *           "MY_SCHEMA"."sample.odata::table" as "MyTable";
   *      }
   * }</pre>
   */
  private String catalogObjectName;
  private String catalogObjectSchema;

  public String getCatalogObjectName() {
    return catalogObjectName;
  }

  public HDBXSODataRepositoryObject setCatalogObjectName(String catalogObjectName) {
    this.catalogObjectName = catalogObjectName;
    return this;
  }

  public String getCatalogObjectSchema() {
    return catalogObjectSchema;
  }

  public HDBXSODataRepositoryObject setCatalogObjectSchema(String catalogObjectSchema) {
    this.catalogObjectSchema = catalogObjectSchema;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HDBXSODataRepositoryObject that = (HDBXSODataRepositoryObject) o;
    return Objects.equals(catalogObjectName, that.catalogObjectName) && Objects.equals(catalogObjectSchema, that.catalogObjectSchema);
  }

  @Override
  public int hashCode() {
    return Objects.hash(catalogObjectName, catalogObjectSchema);
  }
}