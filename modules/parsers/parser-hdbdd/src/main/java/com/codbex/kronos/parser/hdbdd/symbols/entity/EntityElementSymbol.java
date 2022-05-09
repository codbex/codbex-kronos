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
package com.codbex.kronos.parser.hdbdd.symbols.entity;

import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.FieldSymbol;

public class EntityElementSymbol extends FieldSymbol {

  private String defaultValue;
  private boolean isDefaultValueDateTimeFunction;
  private boolean isKey;
  private boolean isNotNull;

  public EntityElementSymbol(String name, Scope scope) {
    super(name, scope);
  }

  public EntityElementSymbol(EntityElementSymbol entityElementSymbol) {
    super(entityElementSymbol.getType(), entityElementSymbol.getReference(), entityElementSymbol.getName(), entityElementSymbol.getScope(),
        entityElementSymbol.getIdToken(), entityElementSymbol.getFullName(), entityElementSymbol.getAnnotations(),
        entityElementSymbol.getSchema());
    this.defaultValue = entityElementSymbol.getDefaultValue();
    this.isDefaultValueDateTimeFunction = entityElementSymbol.isDefaultValueDateTimeFunction();
    this.isKey = entityElementSymbol.isKey();
    this.isNotNull = entityElementSymbol.isNotNull();
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String value) {
    this.defaultValue = value;
  }

  public boolean isDefaultValueDateTimeFunction() {
    return isDefaultValueDateTimeFunction;
  }

  public void setDefaultValueDateTimeFunction(boolean defaultValueDateTimeFunction) {
    isDefaultValueDateTimeFunction = defaultValueDateTimeFunction;
  }

  public boolean isNotNull() {
    return isNotNull;
  }

  public void setNotNull(boolean notNull) {
    isNotNull = notNull;
  }

  public boolean isKey() {
    return isKey;
  }

  public void setKey(boolean key) {
    isKey = key;
  }
}