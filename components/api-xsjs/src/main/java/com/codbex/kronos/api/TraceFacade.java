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
package com.codbex.kronos.api;

import org.eclipse.dirigible.commons.config.Configuration;
import org.springframework.stereotype.Component;

/**
 * The Class TraceFacade.
 */
@Component
public class TraceFacade {
  
  /** The Constant KRONOS_LOG_DEBUG_ENABLED. */
  private static final String KRONOS_LOG_DEBUG_ENABLED = "KRONOS_LOG_DEBUG_ENABLED";
  
  /** The Constant KRONOS_LOG_ERROR_ENABLED. */
  private static final String KRONOS_LOG_ERROR_ENABLED = "KRONOS_LOG_ERROR_ENABLED";
  
  /** The Constant KRONOS_LOG_FATAL_ENABLED. */
  private static final String KRONOS_LOG_FATAL_ENABLED = "KRONOS_LOG_FATAL_ENABLED";
  
  /** The Constant KRONOS_LOG_INFO_ENABLED. */
  private static final String KRONOS_LOG_INFO_ENABLED = "KRONOS_LOG_INFO_ENABLED";
  
  /** The Constant KRONOS_LOG_WARNING_ENABLED. */
  private static final String KRONOS_LOG_WARNING_ENABLED = "KRONOS_LOG_WARNING_ENABLED";

  /**
   * Checks if is debug enabled.
   *
   * @return true, if is debug enabled
   */
  public static boolean isDebugEnabled() {
    return Boolean.parseBoolean(Configuration.get(KRONOS_LOG_DEBUG_ENABLED, "true"));
  }

  /**
   * Checks if is error enabled.
   *
   * @return true, if is error enabled
   */
  public static boolean isErrorEnabled() {
    return Boolean.parseBoolean(Configuration.get(KRONOS_LOG_ERROR_ENABLED, "true"));
  }

  /**
   * Checks if is fatal enabled.
   *
   * @return true, if is fatal enabled
   */
  public static boolean isFatalEnabled() {
    return Boolean.parseBoolean(Configuration.get(KRONOS_LOG_FATAL_ENABLED, "true"));
  }

  /**
   * Checks if is info enabled.
   *
   * @return true, if is info enabled
   */
  public static boolean isInfoEnabled() {
    return Boolean.parseBoolean(Configuration.get(KRONOS_LOG_INFO_ENABLED, "true"));
  }

  /**
   * Checks if is warning enabled.
   *
   * @return true, if is warning enabled
   */
  public static boolean isWarningEnabled() {
    return Boolean.parseBoolean(Configuration.get(KRONOS_LOG_WARNING_ENABLED, "true"));
  }
}