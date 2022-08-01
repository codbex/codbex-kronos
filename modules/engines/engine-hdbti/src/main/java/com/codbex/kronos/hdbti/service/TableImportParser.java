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
package com.codbex.kronos.hdbti.service;

import static com.codbex.kronos.hdbti.api.ITableImportModel.TYPE_HDBTI;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdbti.api.ITableImportArtifactFactory;
import com.codbex.kronos.hdbti.api.ITableImportParser;
import com.codbex.kronos.hdbti.model.TableImportArtifact;
import com.codbex.kronos.hdbti.model.TableImportConfigurationDefinition;
import com.codbex.kronos.hdbti.transformer.TableImportArtifactFactory;
import com.codbex.kronos.parser.hdbti.exception.HDBTISyntaxErrorException;
import com.codbex.kronos.utils.CommonsDBUtils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.sql.DataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.config.StaticObjects;

public class TableImportParser implements ITableImportParser {

  private ITableImportArtifactFactory tableImportArtifactFactory = new TableImportArtifactFactory();

  private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);

  @Override
  public TableImportArtifact parseTableImportArtifact(String location, String content)
      throws IOException, HDBTISyntaxErrorException, ArtifactParserException, SQLException {
    TableImportArtifact parsedArtifact = tableImportArtifactFactory.parseTableImport(content, location);
    parsedArtifact.setName(new File(location).getName());
    parsedArtifact.setLocation(location);
    parsedArtifact.setType(TYPE_HDBTI);
    parsedArtifact.setHash(DigestUtils.md5Hex(content));
    parsedArtifact.setCreatedBy(UserFacade.getName());
    parsedArtifact.setCreatedAt(new Timestamp(new java.util.Date().getTime()));

    for (TableImportConfigurationDefinition configurationDefinition : parsedArtifact.getImportConfigurationDefinition()) {
      configurationDefinition.setHdbtiFileName(location);
      if (configurationDefinition.getSchema() == null) {
        configurationDefinition.setSchema(CommonsDBUtils.getTableSchema(dataSource, configurationDefinition.getTable()));
      }
    }

    return parsedArtifact;
  }
}
