/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.parser.hdbschema;

import com.codbex.kronos.engine.hdb.domain.HDBSchema;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBSchemaCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBSchemaDropProcessor;
import jakarta.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.CreateBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.DropBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.schema.CreateSchemaBuilder;
import org.eclipse.dirigible.database.sql.builders.schema.DropSchemaBuilder;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * The Class HDBSchemaProcessorTest.
 */
// @RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = {HDBDataStructureModelFactory.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
@ExtendWith(MockitoExtension.class)
public class HDBSchemaProcessorTest {

    /** The mock connection. */
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Connection mockConnection;

    /** The mock sql factory. */
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private SqlFactory mockSqlFactory;

    /** The create. */
    @Mock
    private CreateBranchingBuilder create;

    /** The mock create schema builder. */
    @Mock
    private CreateSchemaBuilder mockCreateSchemaBuilder;

    /** The mock drop schema builder. */
    @Mock
    private DropSchemaBuilder mockDropSchemaBuilder;

    /** The drop. */
    @Mock
    private DropBranchingBuilder drop;

    /**
     * Open mocks.
     */
    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Execute create schema successfully.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeCreateSchemaSuccessfully() throws Exception {
        HDBSchemaCreateProcessor processorSpy = spy(HDBSchemaCreateProcessor.class);
        String hdbschemaSample =
                IOUtils.toString(HDBSchemaProcessorTest.class.getResourceAsStream("/Myschema.hdbschema"), StandardCharsets.UTF_8);

        HDBSchema model = HDBDataStructureModelFactory.parseSchema("hdb_schema/Myschema.hdbschema", hdbschemaSample);

        try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class)) {
            String mockSQL = "testSQLScript";
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection))
                      .thenReturn(mockSqlFactory);
            sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection))
                      .thenReturn(new HanaSqlDialect());
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .exists(mockConnection, "MySchema", DatabaseArtifactTypes.SCHEMA))
                      .thenReturn(false);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .create())
                      .thenReturn(create);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .create()
                                            .schema(anyString()))
                      .thenReturn(mockCreateSchemaBuilder);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .create()
                                            .schema(anyString())
                                            .build())
                      .thenReturn(mockSQL);

            // Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());

            processorSpy.execute(mockConnection, model);
            verify(processorSpy, times(1)).executeSql(mockSQL, mockConnection);
            // verify(processorSpy, times(1)).applyArtefactState(any(), any(), any(),
            // eq(ArtefactState.SUCCESSFUL_CREATE), any());
        }
    }

    // @Test
    // public void executeCreateSchemaFail() throws Exception {
    // IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
    // HDBSchemaCreateProcessor processorSpy = spy(HDBSchemaCreateProcessor.class);
    // String hdbschemaSample =
    // IOUtils.toString(HDBSchemaProcessorTest.class.getResourceAsStream("/Myschema.hdbschema"),
    // StandardCharsets.UTF_8);
    //
    // HDBSchema model = HDBDataStructureModelFactory.parseSchema("hdb_schema/Myschema.hdbschema",
    // hdbschemaSample);
    //
    // try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
    //// MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)
    // ) {
    // sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
    // sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new
    // PostgresSqlDialect());
    //// problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(),
    // any(), any(), any()))
    //// .thenAnswer((Answer<Void>) invocation -> null);
    //// Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());
    //
    // processorSpy.execute(mockConnection, model);
    // }
    // });
    // }

    /**
     * Execute drop schema successfully.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeDropSchemaSuccessfully() throws Exception {
        HDBSchemaDropProcessor processorSpy = spy(HDBSchemaDropProcessor.class);
        String hdbschemaSample =
                IOUtils.toString(HDBSchemaProcessorTest.class.getResourceAsStream("/Myschema.hdbschema"), StandardCharsets.UTF_8);

        HDBSchema model = HDBDataStructureModelFactory.parseSchema("hdb_schema/Myschema.hdbschema", hdbschemaSample);
        String mockSQL = "testSQLScript";

        try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class)) {
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection))
                      .thenReturn(mockSqlFactory);
            sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection))
                      .thenReturn(new HanaSqlDialect());
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .exists(mockConnection, "MySchema", DatabaseArtifactTypes.SCHEMA))
                      .thenReturn(true);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .drop())
                      .thenReturn(drop);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .drop()
                                            .schema(anyString()))
                      .thenReturn(mockDropSchemaBuilder);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .drop()
                                            .schema(anyString())
                                            .build())
                      .thenReturn(mockSQL);

            // Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());

            processorSpy.execute(mockConnection, model);
            verify(processorSpy, times(1)).executeSql(mockSQL, mockConnection);
            // verify(processorSpy, times(1)).applyArtefactState(any(), any(), any(),
            // eq(ArtefactState.SUCCESSFUL_DELETE), any());
        }
    }

    // @Test
    // public void executeDropSchemaFail() throws Exception {
    // IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
    // try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
    //// MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)
    // ) {
    // sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
    // sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new
    // PostgresSqlDialect());
    //
    // HDBSchemaDropProcessor processorSpy = spy(HDBSchemaDropProcessor.class);
    // String hdbschemaSample =
    // IOUtils.toString(HDBSchemaProcessorTest.class.getResourceAsStream("/Myschema.hdbschema"),
    // StandardCharsets.UTF_8);
    //
    // HDBSchema model = HDBDataStructureModelFactory.parseSchema("hdb_schema/Myschema.hdbschema",
    // hdbschemaSample);
    //// problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(),
    // any(), any(), any()))
    //// .thenAnswer((Answer<Void>) invocation -> null);
    //// Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());
    //
    // processorSpy.execute(mockConnection, model);
    // }
    // });
    // }

}
