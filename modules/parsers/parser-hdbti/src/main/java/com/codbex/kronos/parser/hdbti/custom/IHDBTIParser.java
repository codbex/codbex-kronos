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
package com.codbex.kronos.parser.hdbti.custom;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hdbti.exception.HDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportModel;

import java.io.IOException;

/**
 * The Interface IHDBTIParser.
 */
public interface IHDBTIParser {

  /**
   * Parses the.
   *
   * @param location the location
   * @param content the content
   * @return the HDBTI import model
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws HDBTISyntaxErrorException the HDBTI syntax error exception
   * @throws ArtifactParserException the artifact parser exception
   */
  HDBTIImportModel parse(String location, String content) throws IOException, HDBTISyntaxErrorException, ArtifactParserException;
}
