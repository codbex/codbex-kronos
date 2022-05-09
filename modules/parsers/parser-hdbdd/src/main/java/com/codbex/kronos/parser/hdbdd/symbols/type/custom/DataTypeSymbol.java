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
package com.codbex.kronos.parser.hdbdd.symbols.type.custom;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import com.codbex.kronos.parser.hdbdd.symbols.type.Type;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.Typeable;

public class DataTypeSymbol extends Symbol implements Type, CustomDataType, Typeable {

  private Type type;
  private String reference;

  public DataTypeSymbol(String name, Scope scope) {
    super(name, scope);
  }

  public DataTypeSymbol(Symbol symbol) {
    super(symbol);
  }

  @Override
  public void setType(Type type) {
    this.type = type;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getReference() {
        return reference;
    }
}
