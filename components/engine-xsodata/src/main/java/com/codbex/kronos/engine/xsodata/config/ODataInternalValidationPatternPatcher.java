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
package com.codbex.kronos.engine.xsodata.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.olingo.odata2.core.edm.provider.EdmNamedImplProv;

class ODataNamesValidationPatternPatcher {

  private static final String NAME_OF_THE_PROPERTY_NAME_PATTERN_FIELD = "PATTERN_VALID_NAME";

  // OData property name pattern, Olingo default pattern do not allow dot in the property name.
  // Which is the case when you have a data types in HDBDD file, to support XS Classic use cases we need to allow dots in OData property names.
  private static final Pattern ODATA_PROPERTY_NAME_PATTERN = Pattern
      .compile("\\A[_\\p{L}\\p{Nl}][_.\\p{L}\\p{Nl}\\p{Nd}\\p{Mn}\\p{Mc}\\p{Pc}\\p{Cf}]{0,}\\Z");

  void patch() throws ReflectiveOperationException {
    setFinalStatic(EdmNamedImplProv.class.getDeclaredField(NAME_OF_THE_PROPERTY_NAME_PATTERN_FIELD), ODATA_PROPERTY_NAME_PATTERN);
  }

  private static void setFinalStatic(Field field, Object newValue)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    field.setAccessible(true); // NOSONAR

    Field modifiersField = getModifiersField();
    modifiersField.setAccessible(true); // NOSONAR
    modifiersField.setInt(field, (field.getModifiers() & ~Modifier.FINAL)); // NOSONAR

    field.set(null, newValue); // NOSONAR
  }

  private static Field getModifiersField() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
    getDeclaredFields0.setAccessible(true); // NOSONAR
    Field[] allDeclaredFields = (Field[]) getDeclaredFields0.invoke(Field.class, false);
    return Arrays.stream(allDeclaredFields)
        .filter(f -> "modifiers".equals(f.getName()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Could not get modifiers field"));
  }
}
