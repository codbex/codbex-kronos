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

/**
 * The Class Constants.
 */
public final class Constants {

  /** The Constant HDBPROCEDURE_CREATE. */
  public static final String HDBPROCEDURE_CREATE = "CREATE ";
  
  /** The Constant HDBPROCEDURE_DROP. */
  public static final String HDBPROCEDURE_DROP = "DROP PROCEDURE ";
  
  /** The Constant HDBTABLEFUNCTION_CREATE. */
  public static final String HDBTABLEFUNCTION_CREATE = "CREATE ";
  
  /** The Constant HDBTABLEFUNCTION_DROP. */
  public static final String HDBTABLEFUNCTION_DROP = "DROP FUNCTION ";
  
  /** The Constant HDBVIEW_SYNTAX. */
  public static final String HDBVIEW_SYNTAX = "VIEW ";
  
  /** The Constant HDBVIEW_CREATE. */
  public static final String HDBVIEW_CREATE = "CREATE ";
  
  /** The Constant HDBVIEW_DROP. */
  public static final String HDBVIEW_DROP = "DROP VIEW ";
  
  /** The Constant HDBSEQUENCE_SYNTAX. */
  public static final String HDBSEQUENCE_SYNTAX = "SEQUENCE ";
  
  /** The Constant HDBSEQUENCE_CREATE. */
  public static final String HDBSEQUENCE_CREATE = "CREATE ";
  
  /** The Constant HDBSEQUENCE_ALTER. */
  public static final String HDBSEQUENCE_ALTER = "ALTER ";
  
  /** The Constant HDBSEQUENCE_DROP. */
  public static final String HDBSEQUENCE_DROP = "DROP  ";
  
  /** The Constant HDBTABLE_CREATE. */
  public static final String HDBTABLE_CREATE = "CREATE ";
  
  /** The Constant HDBTABLE_DROP. */
  public static final String HDBTABLE_DROP = "DROP ";
  
  /** The Constant SYNONYM_PUBLIC_SCHEMA. */
  public static final String SYNONYM_PUBLIC_SCHEMA = "PUBLIC";
  
  /** The Constant HDBTABLETYPE_CREATE. */
  public static final String HDBTABLETYPE_CREATE = "CREATE ";
  
  /** The Constant HDBTABLE_INDEX_ORDER_HANA_V1_DSC. */
  public static final String HDBTABLE_INDEX_ORDER_HANA_V1_DSC = "DSC";
  
  /** The Constant HDBTABLE_INDEX_ORDER_HANA_DESC. */
  public static final String HDBTABLE_INDEX_ORDER_HANA_DESC = "DESC";

  /**
   * The Unix separator character.
   */
  public static final char UNIX_SEPARATOR = '/';
  /**
   * The Windows separator character.
   */
  public static final char WINDOWS_SEPARATOR = '\\';

  /**
   * Instantiates a new constants.
   */
  private Constants() {
  }

}
