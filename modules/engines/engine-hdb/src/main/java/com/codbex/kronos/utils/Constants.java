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
package com.codbex.kronos.utils;

public final class Constants {

  public static final String HDBPROCEDURE_CREATE = "CREATE ";
  public static final String HDBPROCEDURE_DROP = "DROP PROCEDURE ";
  public static final String HDBTABLEFUNCTION_CREATE = "CREATE ";
  public static final String HDBTABLEFUNCTION_DROP = "DROP FUNCTION ";
  public static final String HDBVIEW_SYNTAX = "VIEW ";
  public static final String HDBVIEW_CREATE = "CREATE ";
  public static final String HDBVIEW_DROP = "DROP VIEW ";
  public static final String HDBSEQUENCE_SYNTAX = "SEQUENCE ";
  public static final String HDBSEQUENCE_CREATE = "CREATE ";
  public static final String HDBSEQUENCE_ALTER = "ALTER ";
  public static final String HDBSEQUENCE_DROP = "DROP  ";
  public static final String HDBTABLE_CREATE = "CREATE ";
  public static final String HDBTABLE_DROP = "DROP ";
  public static final String SYNONYM_PUBLIC_SCHEMA = "PUBLIC";
  public static final String HDBTABLETYPE_CREATE = "CREATE ";
  public static final String HDBTABLE_INDEX_ORDER_HANA_V1_DSC = "DSC";
  public static final String HDBTABLE_INDEX_ORDER_HANA_DESC = "DESC";

  /**
   * The Unix separator character.
   */
  public static final char UNIX_SEPARATOR = '/';
  /**
   * The Windows separator character.
   */
  public static final char WINDOWS_SEPARATOR = '\\';

  private Constants() {
  }

}
