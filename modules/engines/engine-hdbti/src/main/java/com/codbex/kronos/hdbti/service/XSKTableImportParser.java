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

import com.codbex.kronos.exceptions.XSKArtifactParserException;
import com.codbex.kronos.hdbti.api.IXSKTableImportArtifactFactory;
import com.codbex.kronos.hdbti.api.IXSKTableImportParser;
import com.codbex.kronos.hdbti.model.XSKTableImportArtifact;
import com.codbex.kronos.hdbti.model.XSKTableImportConfigurationDefinition;
import com.codbex.kronos.hdbti.transformer.XSKTableImportArtifactFactory;
import com.codbex.kronos.parser.hdbti.exception.XSKHDBTISyntaxErrorException;
import com.codbex.kronos.utils.XSKCommonsDBUtils;

import static com.codbex.kronos.hdbti.api.IXSKTableImportModel.TYPE_HDBTI;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.sql.DataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.config.StaticObjects;

public class XSKTableImportParser implements IXSKTableImportParser {

  private IXSKTableImportArtifactFactory xskTableImportArtifactFactory = new XSKTableImportArtifactFactory();

  private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);

  @Override
  public XSKTableImportArtifact parseTableImportArtifact(String location, String content)
      throws IOException, XSKHDBTISyntaxErrorException, XSKArtifactParserException, SQLException {
    XSKTableImportArtifact parsedArtifact = xskTableImportArtifactFactory.parseTableImport(content, location);
    parsedArtifact.setName(new File(location).getName());
    parsedArtifact.setLocation(location);
    parsedArtifact.setType(TYPE_HDBTI);
    parsedArtifact.setHash(DigestUtils.md5Hex(content));
    parsedArtifact.setCreatedBy(UserFacade.getName());
    parsedArtifact.setCreatedAt(new Timestamp(new java.util.Date().getTime()));

    for (XSKTableImportConfigurationDefinition configurationDefinition : parsedArtifact.getImportConfigurationDefinition()) {
      configurationDefinition.setHdbtiFileName(location);
      if (configurationDefinition.getSchema() == null) {
        configurationDefinition.setSchema(XSKCommonsDBUtils.getTableSchema(dataSource, configurationDefinition.getTable()));
      }
    }

    return parsedArtifact;
  }
}
