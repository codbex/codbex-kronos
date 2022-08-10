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
package com.codbex.kronos.hdb.ds.service.parser;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;

import java.io.IOException;

/**
 * The Interface ICoreParserService.
 */
public interface ICoreParserService {

  /**
   * Parses the data structure.
   *
   * @param parametersModel the parameters model
   * @return the data structure model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  DataStructureModel parseDataStructure(DataStructureParametersModel parametersModel)
      throws DataStructuresException, IOException, ArtifactParserException;

  /**
   * Gets the data structure class.
   *
   * @param type the type
   * @return the data structure class
   */
  Class<DataStructureModel> getDataStructureClass(String type);
}
