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
package com.codbex.kronos.engine.hdb.api;

import java.sql.Connection;
import java.sql.SQLException;

import com.codbex.kronos.engine.hdb.domain.HDBDataStructure;

/**
 * The Interface IHDBProcessor.
 *
 * @param <T> the generic type
 */
public interface IHDBProcessor<T extends HDBDataStructure> {

  /**
   * Execute.
   *
   * @param connection the connection
   * @param entityModel the entity model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  boolean execute(Connection connection, T entityModel) throws SQLException;
}
