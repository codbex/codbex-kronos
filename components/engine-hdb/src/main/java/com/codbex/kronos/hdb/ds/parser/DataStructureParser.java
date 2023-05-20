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
package com.codbex.kronos.hdb.ds.parser;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;

import java.io.IOException;

/**
 * The Interface DataStructureParser.
 *
 * @param <T> the generic type
 */
public interface DataStructureParser<T extends DataStructureModel> {

  /**
   * Parses the.
   *
   * @param parametersModel the parameters model
   * @return the t
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  T parse(DataStructureParametersModel parametersModel) throws DataStructuresException, IOException, ArtifactParserException;

  /**
   * Gets the type.
   *
   * @return the type
   */
  String getType();

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  Class<T> getDataStructureClass();
}
