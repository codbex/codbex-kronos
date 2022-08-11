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
package com.codbex.kronos.hdb.ds.service.manager;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.processors.tabletype.HDBSynonymRemover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TableTypeManagerService.
 */
public class HDBStructureManagerService extends HDBTableTypeManagerService {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBScalarFunctionManagerService.class);

  /**
   * Instantiates a new structure manager service.
   *
   * @param synonymRemover the synonym remover
   */
  public HDBStructureManagerService(HDBSynonymRemover synonymRemover) {
    super(synonymRemover);
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDB_STRUCTURE;
  }
}
