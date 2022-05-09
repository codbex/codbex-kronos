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
package com.codbex.kronos.parser.hdbdd.symbols.type.field;

import com.codbex.kronos.parser.hdbdd.annotation.metadata.AnnotationObj;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.IdentifierContext;
import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import com.codbex.kronos.parser.hdbdd.symbols.type.Type;
import com.codbex.kronos.parser.hdbdd.symbols.type.custom.DataTypeSymbol;

import java.util.Map;

public class FieldSymbol extends Symbol implements Typeable {

  private Type type;
  private String reference;

  public FieldSymbol(String name) {
    super(name);
  }

  public FieldSymbol(String name, Scope scope) {
    super(name, scope);
  }

  public FieldSymbol(Type type, String reference, String name, Scope scope, IdentifierContext idToken, String fullName,
      Map<String, AnnotationObj> annotations, String schema) {
    super(name, scope, idToken, fullName, annotations, schema);
    this.type = type;
    this.reference = reference;
  }

  @Override
  public Type getType() {
    if (type instanceof DataTypeSymbol){
      return ((DataTypeSymbol) type).getType();
    }else {
      return type;
    }
  }

  @Override
  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public String getReference() {
    return reference;
  }

  @Override
  public void setReference(String token) {
    this.reference = token;
  }
}