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
package com.codbex.kronos.hdi.ds.processors;

import java.sql.Connection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdi.ds.model.DataStructureHDIModel;

/**
 * The Class HDIContainerDropProcessor.
 */
public class HDIContainerDropProcessor {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDIContainerDropProcessor.class);

  /**
   * Instantiates a new HDI container drop processor.
   */
  private HDIContainerDropProcessor() {
  }

  /**
   * Execute.
   *
   * @param connection the connection
   * @param hdiModels the hdi models
   */
  public static void execute(Connection connection, List<DataStructureHDIModel> hdiModels) {
  }

}
