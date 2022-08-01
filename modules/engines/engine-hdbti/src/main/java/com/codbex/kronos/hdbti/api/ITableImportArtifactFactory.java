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

public interface ITableImportArtifactFactory {

  TableImportArtifact parseTableImport(String content, String location)
      throws IOException, HDBTISyntaxErrorException, ArtifactParserException;

  IRepository getRepository();

  IHDBTICoreService getHDBTICoreService();

  IHDBTIParser getHDBTIParser();
}
