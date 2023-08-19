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
package com.codbex.kronos.engine.hdb.processors;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.hdb.domain.HDBSynonym;
import com.codbex.kronos.engine.hdb.domain.HDBSynonymGroup;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;


/**
 * The Class HDBSynonymCreateProcessor.
 */
public class HDBSynonymCreateProcessor extends AbstractHDBProcessor<HDBSynonymGroup> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBSynonymCreateProcessor.class);
  
  /**
   * Execute :
   * <code>CREATE [PUBLIC] SYNONYM {synonym_name} FOR {synonym_source_object_name}
   * {synonym_name} ::= [{schema_name}.]{identifier}
   * {synonym_source_object_name}:==[{object_schema_name}.]{object_name}</code>
   *
   * @param connection the connection
   * @param synonymModel the synonym model
   * @return true, if successful
   * @throws SQLException the SQL exception
   * @see <a href="https://help.sap.com/viewer/4fe29514fd584807ac9f2a04f6754767/1.0.12/en-US/20d5412b75191014bc7ec7e133ce5bf5.html">CREATE SYNONYM Statement (Data Definition)</a>
   */
  @Override
  public void execute(Connection connection, HDBSynonymGroup synonymModel) throws SQLException {
	  for (Map.Entry<String, HDBSynonym> entry : synonymModel.getSynonymDefinitions().entrySet()) {
	      logger.info("Processing Create Synonym: " + entry.getKey());
	
	      String synonymName = (entry.getValue().getSchema() != null) ? (HDBUtils.escapeArtifactName(entry.getKey(), entry.getValue().getSchema())) : (HDBUtils.escapeArtifactName(entry.getKey()));
	      String targetObjectName = HDBUtils.escapeArtifactName(entry.getValue().getTarget().getObject(), entry.getValue().getTarget().getSchema());
	      try {
	        String synonymSchema = null != entry.getValue().getSchema() ? entry.getValue().getSchema() : connection.getMetaData().getUserName();
	        if (!SqlFactory.getNative(connection).exists(connection, synonymSchema, entry.getKey(), DatabaseArtifactTypes.SYNONYM)) {
				// TODO: Add Support for public synonym creation
				// "CREATE SYNONYM "PUBLIC"."com.codbex.test.data::Test.Table1234" FOR "SCHEMA_NAME"."com.codbex.test.data::Test.Table1234""
				// ->
				// "CREATE PUBLIC SYNONYM "com.codbex.test.data::Test.Table1234" FOR "SCHEMA_NAME"."com.codbex.test.data::Test.Table1234""
	          String sql = SqlFactory.getNative(connection).create().synonym(synonymName).forSource(targetObjectName).build();
	          executeSql(sql, connection);
	          String message = String.format("Create synonym [%s] successfully", synonymName);
	          logger.info(message);
	        } else {
	          String warningMessage = String.format("Synonym [%s] already exists during the create process", synonymName);
	          logger.warn(warningMessage);
	        }
	      } catch (SQLException ex) {
	        String errorMessage = String.format("Create synonym [%s] skipped due to an error: %s", synonymName, ex.getMessage());
			CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, synonymModel.getLocation(), CommonsConstants.HDB_SYNONYM_PARSER);
			throw ex;
	      }
	    };
    }
}
