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
package com.codbex.kronos.engine.hdb.parser.hdbtablefunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import javax.transaction.Transactional;

import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.eclipse.dirigible.repository.api.IResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBTableFunction;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.repository.TestRepositoryMigrator;
import com.codbex.kronos.exceptions.ArtifactParserException;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBTableFunctionDataStructureParserTest {
	
	@Autowired
	private TestRepositoryMigrator migrator;
	  
	@Autowired
	private IRepository repository;

    @AfterEach
    public void cleanUp() {
        migrator.cleanUp();
    }

    @Test
    public void parseTableFunction() throws IOException, DataStructuresException, ArtifactParserException {
        String location = "tablefunction/OrderTableFunction.hdbtablefunction";
        migrator.migrate(location);
    	IResource loadedResource = repository.getResource(IRepositoryStructure.PATH_REGISTRY_PUBLIC + IRepository.SEPARATOR + location);
        String content = new String(loadedResource.getContent());

        HDBTableFunction model = HDBDataStructureModelFactory.parseTableFunction(location, content);
        assertEquals("MYSCHEMA", model.getSchema(), "Unexpected tablefunction schema.");
        assertEquals("hdb_view::OrderTableFunction", model.getName(), "Unexpected tablefunction name.");
        assertEquals(content, model.getContent(), "Unexpected tablefunction content.");
        assertEquals(content, model.getContent(), "Unexpected tablefunction raw content.");
        assertEquals(location, model.getLocation(), "Unexpected tablefunction location.");
        assertEquals("HDBTABLEFUNCTION", model.getType(), "Unexpected tablefunction type : " + model.getType());
//        assertEquals(0, model.getDependencies().size(), "Unexpected tablefunction dependencies.");
//        assertNotNull(model.getCreatedAt(), "Null value for tablefunction createdAt");
//        assertNotNull(model.getCreatedBy(), "Null value for tablefunction createdBy");
//        assertEquals("class com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel", parser.getDataStructureClass().toString(), "Cant access data structure model class");
    }

    @Test
    public void parseNoSchemaTableFunction() throws IOException, DataStructuresException, ArtifactParserException {
        String location = "tablefunction/OrderTableFunctionNoSchema.hdbtablefunction";
        migrator.migrate(location);
    	IResource loadedResource = repository.getResource(IRepositoryStructure.PATH_REGISTRY_PUBLIC + IRepository.SEPARATOR + location);
        String content = new String(loadedResource.getContent());

        HDBTableFunction model = HDBDataStructureModelFactory.parseTableFunction(location, content);
        assertNull(model.getSchema(), "Unexpected tablefunction schema.");
        assertEquals("hdb_view::OrderTableFunction", model.getName(), "Unexpected tablefunction name.");
        assertEquals(content, model.getContent(), "Unexpected tablefunction content.");
        assertEquals(location, model.getLocation(), "Unexpected tablefunction location.");
        assertEquals("HDBTABLEFUNCTION", model.getType(), "Unexpected tablefunction type: " + model.getType());
//        assertEquals("Unexpected tablefunction dependencies.", 0, model.getDependencies().size());
//        assertNotNull("Null value for tablefunction createdAt", model.getCreatedAt());
//        assertNotNull("Null value for tablefunction createdBy", model.getCreatedBy());
    }

    @Test
    public void parseNoSchemaNoNameTableFunction() throws IOException {
        String location = "tablefunction/OrderTableFunctionNoSchemaNoName.hdbtablefunction";
        migrator.migrate(location);
    	IResource loadedResource = repository.getResource(IRepositoryStructure.PATH_REGISTRY_PUBLIC + IRepository.SEPARATOR + location);
    	final String content = new String(loadedResource.getContent());

    	DataStructuresException caughtException = Assertions.assertThrows(
    			DataStructuresException.class,
                () -> HDBDataStructureModelFactory.parseTableFunction(location, content));
        Throwable caughtExceptionCause = caughtException.getCause();

        assertEquals("Wrong format of HDB Table Function: " + location + " during parsing. ",
                caughtException.getMessage(), "Unexpected exception message");
//        verify(tableFunctionLoggerMock).logError(location, CommonsConstants.EXPECTED_FIELDS, caughtExceptionCause.getMessage());
//        verify(dataStructuresSynchronizerMock).applyArtefactState(
//                CommonsUtils.getRepositoryBaseObjectName(location),
//                location,
//                tableFunctionSynchronizationArtefactType,
//                ArtefactState.FAILED_CREATE,
//                caughtExceptionCause.getMessage()
//        );
    }

}
