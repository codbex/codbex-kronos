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
package com.codbex.kronos.engine.hdb.api;

/**
 * The Interface IDataStructureModel.
 */
public interface IDataStructureModel {

  /**
   * File extension for *.hdbdd files
   */
  public static final String FILE_EXTENSION_HDBDD = ".hdbdd";

  /**
   * File extension for *.hdbtable files
   */
  public static final String FILE_EXTENSION_HDB_TABLE = ".hdbtable";

  /**
   * File extension for *.hdbview files
   */
  public static final String FILE_EXTENSION_HDB_VIEW = ".hdbview";


  /**
   * File extension for *.calculationview files
   */
  public static final String FILE_EXTENSION_CALCULATION_VIEW = ".calculationview";

  /**
   * File extension for *.hdbcalculationview files
   */
  public static final String FILE_EXTENSION_HDB_CALCULATION_VIEW = ".hdbcalculationview";

  /**
   * File extension for *.hdbprocedure files
   */
  public static final String FILE_EXTENSION_HDB_PROCEDURE = ".hdbprocedure";

  /**
   * File extension for *.hdbtablefunction files
   */
  public static final String FILE_EXTENSION_HDB_TABLE_FUNCTION = ".hdbtablefunction";

  /**
   * File extension for *.hdbschema files
   */
  public static final String FILE_EXTENSION_HDB_SCHEMA = ".hdbschema";

  /**
   * File extension for *.hdbsequence files
   */
  public static final String FILE_EXTENSION_HDB_SEQUENCE = ".hdbsequence";

  /**
   * File extension for *.hdbscalarfunction files
   */
  public static final String FILE_EXTENSION_HDB_SCALAR_FUNCTION = ".hdbscalarfunction";

  /**
   * File extension for *.hdbsynonym files
   */
  public static final String FILE_EXTENSION_HDB_SYNONYM = ".hdbsynonym";

  /**
   * File extension for *.hdbstructure files
   */
  public static final String FILE_EXTENSION_HDB_STRUCTURE = ".hdbstructure";

  /**
   * File extension for *.hdbtabletype files
   */
  public static final String FILE_EXTENSION_HDB_TABLE_TYPE = ".hdbtabletype";

  /**
   * File extension for *.hdi files
   */
  public static final String FILE_EXTENSION_HDI = ".hdi";

  /** Type hdbdd. */
  public static final String TYPE_HDBDD = "HDBDD";

  /** Type hdbsynonym. */
  public static final String TYPE_HDB_SYNONYM = "HDBSYNONYM";

  /** Type hdbtable. */
  public static final String TYPE_HDB_TABLE = "HDBTABLE";
  
  /** Type hdbview. */
  public static final String TYPE_HDB_VIEW = "HDBVIEW";
  
  /** Type calculation view. */
  public static final String TYPE_CALCULATION_VIEW = "CALCULATIONVIEW";
  
  /** Type hdbcalculation view. */
  public static final String TYPE_HDB_CALCULATION_VIEW = "HDBCALCULATIONVIEW";
  
  /** Type hdbprocedure. */
  public static final String TYPE_HDB_PROCEDURE = "HDBPROCEDURE";
  
  /** Type hdbdschema. */
  public static final String TYPE_HDB_SCHEMA = "HDBSCHEMA";

  /** Type hdbtablefunction. */
  public static final String TYPE_HDB_TABLE_FUNCTION = "HDBTABLEFUNCTION";

  /** Type hdbsequence. */
  public static final String TYPE_HDB_SEQUENCE = "HDBSEQUENCE";

  /** Type hdbscalarfunction. */
  public static final String TYPE_HDB_SCALAR_FUNCTION = "HDBSCALARFUNCTION";

  /** Type hdbtabletype. */
  public static final String TYPE_HDB_TABLE_TYPE = "HDBTABLETYPE";

  /** Type hdbstructure. */
  public static final String TYPE_HDB_STRUCTURE = "HDBSTRUCTURE";

  /** Type hdi. */
  public static final String TYPE_HDI = "HDI";
}
