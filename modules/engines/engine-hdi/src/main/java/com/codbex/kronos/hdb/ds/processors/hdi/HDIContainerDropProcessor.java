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
package com.codbex.kronos.hdb.ds.processors.hdi;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.model.hdi.HDIDataStructureModel;

public class HDIContainerDropProcessor {

  private static final Logger logger = LoggerFactory.getLogger(HDIContainerDropProcessor.class);

  private HDIContainerDropProcessor() {
  }

  public static void execute(Connection connection, List<HDIDataStructureModel> hdiModels) {
  }

}
