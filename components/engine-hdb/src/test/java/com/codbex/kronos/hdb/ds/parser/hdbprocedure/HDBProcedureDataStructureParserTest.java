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
package com.codbex.kronos.hdb.ds.parser.hdbprocedure;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.transaction.Transactional;

import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.eclipse.dirigible.repository.api.IResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBProcedure;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.repository.TestRepositoryMigrator;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBProcedureDataStructureParserTest {
	
  @Autowired
  private TestRepositoryMigrator migrator;
  
  @Autowired
  private IRepository repository;

  @Test
  public void checkModel() throws IOException, DataStructuresException, ArtifactParserException {
	final String location = "procedure/OrderProcedure.hdbprocedure";
	migrator.migrate(location);
	IResource loadedResource = repository.getResource(IRepositoryStructure.PATH_REGISTRY_PUBLIC + IRepository.SEPARATOR + location);
    String fileContent = new String(loadedResource.getContent());
	HDBProcedure parsedModel = HDBDataStructureModelFactory.parseProcedure("procedure/OrderProcedure.hdbprocedure", fileContent);
    assertEquals("MYSCHEMA", parsedModel.getSchema(), "Unexpected hdbprocedure schema.");
    assertEquals("hdb_view::OrderProcedure", parsedModel.getName(), "Unexpected hdbprocedure name.");
    assertEquals("procedure/OrderProcedure.hdbprocedure", parsedModel.getLocation(), "Unexpected hdbprocedure location.");
    assertEquals("HDBPROCEDURE", parsedModel.getType(), "Unexpected hdbprocedure type.");
//    assertEquals("Unexpected hdbprocedure dependencies.", 0, parsedModel.getDependencies().size());
    assertNull(parsedModel.getCreatedAt(), "Null value for hdbprocedure createdAt");
    assertNull(parsedModel.getCreatedBy(), "Null value for hdbprocedure createdBy");
  }

  /**
   * The Class TestConfiguration.
   */
  @EnableJpaRepositories(basePackages = "com.codbex.kronos")
  @SpringBootApplication(scanBasePackages = {"com.codbex.kronos", "org.eclipse.dirigible.components"})
  @EnableScheduling
  static class TestConfiguration {
  }

}
