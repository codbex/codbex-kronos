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
package com.codbex.kronos.hdbti.api;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdbti.model.TableImportArtifact;
import com.codbex.kronos.parser.hdbti.custom.IHDBTIParser;
import com.codbex.kronos.parser.hdbti.exception.HDBTISyntaxErrorException;

import java.io.IOException;
import org.eclipse.dirigible.repository.api.IRepository;

/**
 * A factory for creating ITableImportArtifact objects.
 */
public interface ITableImportArtifactFactory {

  /**
   * Parses the table import.
   *
   * @param content the content
   * @param location the location
   * @return the table import artifact
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws HDBTISyntaxErrorException the HDBTI syntax error exception
   * @throws ArtifactParserException the artifact parser exception
   */
  TableImportArtifact parseTableImport(String content, String location)
      throws IOException, HDBTISyntaxErrorException, ArtifactParserException;

  /**
   * Gets the repository.
   *
   * @return the repository
   */
  IRepository getRepository();

  /**
   * Gets the HDBTI core service.
   *
   * @return the HDBTI core service
   */
  IHDBTICoreService getHDBTICoreService();

  /**
   * Gets the HDBTI parser.
   *
   * @return the HDBTI parser
   */
  IHDBTIParser getHDBTIParser();
}
