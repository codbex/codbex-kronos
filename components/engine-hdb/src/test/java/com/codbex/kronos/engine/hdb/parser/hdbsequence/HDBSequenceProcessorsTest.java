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
package com.codbex.kronos.engine.hdb.parser.hdbsequence;

import com.codbex.kronos.engine.hdb.domain.HDBSequence;
import com.codbex.kronos.engine.hdb.processors.HDBSequenceCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBSequenceDropProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBSequenceUpdateProcessor;
import jakarta.transaction.Transactional;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.components.api.platform.ProblemsFacade;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.AlterBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.CreateBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.DropBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.sequence.AlterSequenceBuilder;
import org.eclipse.dirigible.database.sql.builders.sequence.CreateSequenceBuilder;
import org.eclipse.dirigible.database.sql.builders.sequence.DropSequenceBuilder;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.eclipse.dirigible.database.sql.dialects.postgres.PostgresSqlDialect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.stubbing.Answer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * The Class HDBSequenceProcessorsTest.
 */
@SpringBootTest(classes = {HDBSequenceCreateProcessor.class, HDBSequenceDropProcessor.class, HDBSequenceUpdateProcessor.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
@Disabled
public class HDBSequenceProcessorsTest {

    /** The mock connection. */
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Connection mockConnection;

    /** The mock sql factory. */
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private SqlFactory mockSqlFactory;

    /** The create. */
    @Mock
    private CreateBranchingBuilder create;

    /** The alter. */
    @Mock
    private AlterBranchingBuilder alter;

    /** The drop. */
    @Mock
    private DropBranchingBuilder drop;

    /** The mock create sequence builder. */
    @Mock
    private CreateSequenceBuilder mockCreateSequenceBuilder;

    /** The mock alter sequence builder. */
    @Mock
    private AlterSequenceBuilder mockAlterSequenceBuilder;

    /** The mock drop sequence builder. */
    @Mock
    private DropSequenceBuilder mockDropSequenceBuilder;

    /**
     * Open mocks.
     */
    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Execute create successfully.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeCreateSuccessfully() throws Exception {
        HDBSequenceCreateProcessor spyProccessor = spy(HDBSequenceCreateProcessor.class);

        HDBSequence mockModel = mock(HDBSequence.class);
        String sql = "TestExecuteCreateSuccessfully";
        try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class)) {
            sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection))
                      .thenReturn(new HanaSqlDialect());
            sqlFactory.when(() -> mockModel.getName())
                      .thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
            sqlFactory.when(() -> mockModel.isClassic())
                      .thenReturn(true);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection))
                      .thenReturn(mockSqlFactory);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .create())
                      .thenReturn(create);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .create()
                                            .sequence(any()))
                      .thenReturn(mockCreateSequenceBuilder);
            when(mockModel.getResetBy()).thenReturn("LL");
            when(mockCreateSequenceBuilder.start(anyInt())).thenReturn(mockCreateSequenceBuilder);
            when(mockCreateSequenceBuilder.start(anyInt())
                                          .increment(anyInt())).thenReturn(mockCreateSequenceBuilder);
            when(mockCreateSequenceBuilder.start(anyInt())
                                          .increment(anyInt())
                                          .maxvalue(anyInt())).thenReturn(mockCreateSequenceBuilder);
            when(mockCreateSequenceBuilder.start(anyInt())
                                          .increment(anyInt())
                                          .maxvalue(anyInt())
                                          .nomaxvalue(anyBoolean())).thenReturn(mockCreateSequenceBuilder);
            when(mockCreateSequenceBuilder.start(anyInt())
                                          .increment(anyInt())
                                          .maxvalue(anyInt())
                                          .nomaxvalue(anyBoolean())
                                          .minvalue(anyInt())).thenReturn(mockCreateSequenceBuilder);
            when(mockCreateSequenceBuilder.start(anyInt())
                                          .increment(anyInt())
                                          .maxvalue(anyInt())
                                          .nomaxvalue(anyBoolean())
                                          .minvalue(anyInt())
                                          .nominvalue(anyBoolean())).thenReturn(mockCreateSequenceBuilder);
            when(mockCreateSequenceBuilder.start(anyInt())
                                          .increment(anyInt())
                                          .maxvalue(anyInt())
                                          .nomaxvalue(anyBoolean())
                                          .minvalue(anyInt())
                                          .nominvalue(anyBoolean())
                                          .cycles(anyBoolean())).thenReturn(mockCreateSequenceBuilder);
            when(mockCreateSequenceBuilder.start(anyInt())
                                          .increment(anyInt())
                                          .maxvalue(anyInt())
                                          .nomaxvalue(anyBoolean())
                                          .minvalue(anyInt())
                                          .nominvalue(anyBoolean())
                                          .cycles(anyBoolean())
                                          .resetBy(anyString())).thenReturn(mockCreateSequenceBuilder);
            when(mockCreateSequenceBuilder.start(anyInt())
                                          .increment(anyInt())
                                          .maxvalue(anyInt())
                                          .nomaxvalue(anyBoolean())
                                          .minvalue(anyInt())
                                          .nominvalue(anyBoolean())
                                          .cycles(anyBoolean())
                                          .resetBy(anyString())
                                          .build()).thenReturn(sql);
            spyProccessor.execute(mockConnection, mockModel);

            verify(spyProccessor, times(1)).executeSql(sql, mockConnection);
        }

    }

    /**
     * Execute update successfully.
     *
     * @throws SQLException the SQL exception
     */
    @Test
    public void executeUpdateSuccessfully() throws SQLException {
        HDBSequenceUpdateProcessor spyProccessor = spy(HDBSequenceUpdateProcessor.class);
        HDBSequence mockModel = mock(HDBSequence.class);
        String sql = "TestExecuteUpdateSuccessfully";

        try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class)) {
            sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection))
                      .thenReturn(new HanaSqlDialect());
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection))
                      .thenReturn(mockSqlFactory);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .alter())
                      .thenReturn(alter);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .alter()
                                            .sequence(any()))
                      .thenReturn(mockAlterSequenceBuilder);
            when(mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
            when(mockModel.isClassic()).thenReturn(true);
            when(mockModel.getResetBy()).thenReturn("LL");
            when(mockAlterSequenceBuilder.start(anyInt())).thenReturn(mockAlterSequenceBuilder);
            when(mockAlterSequenceBuilder.start(anyInt())
                                         .increment(anyInt())).thenReturn(mockAlterSequenceBuilder);
            when(mockAlterSequenceBuilder.start(anyInt())
                                         .increment(anyInt())
                                         .maxvalue(anyInt())).thenReturn(mockAlterSequenceBuilder);
            when(mockAlterSequenceBuilder.start(anyInt())
                                         .increment(anyInt())
                                         .maxvalue(anyInt())
                                         .nomaxvalue(anyBoolean())).thenReturn(mockAlterSequenceBuilder);
            when(mockAlterSequenceBuilder.start(anyInt())
                                         .increment(anyInt())
                                         .maxvalue(anyInt())
                                         .nomaxvalue(anyBoolean())
                                         .minvalue(anyInt())).thenReturn(mockAlterSequenceBuilder);
            when(mockAlterSequenceBuilder.start(anyInt())
                                         .increment(anyInt())
                                         .maxvalue(anyInt())
                                         .nomaxvalue(anyBoolean())
                                         .minvalue(anyInt())
                                         .nominvalue(anyBoolean())).thenReturn(mockAlterSequenceBuilder);
            when(mockAlterSequenceBuilder.start(anyInt())
                                         .increment(anyInt())
                                         .maxvalue(anyInt())
                                         .nomaxvalue(anyBoolean())
                                         .minvalue(anyInt())
                                         .nominvalue(anyBoolean())
                                         .cycles(anyBoolean())).thenReturn(mockAlterSequenceBuilder);
            when(mockAlterSequenceBuilder.start(anyInt())
                                         .increment(anyInt())
                                         .maxvalue(anyInt())
                                         .nomaxvalue(anyBoolean())
                                         .minvalue(anyInt())
                                         .nominvalue(anyBoolean())
                                         .cycles(anyBoolean())
                                         .resetBy(anyString())).thenReturn(mockAlterSequenceBuilder);
            when(mockAlterSequenceBuilder.start(anyInt())
                                         .increment(anyInt())
                                         .maxvalue(anyInt())
                                         .nomaxvalue(anyBoolean())
                                         .minvalue(anyInt())
                                         .nominvalue(anyBoolean())
                                         .cycles(anyBoolean())
                                         .resetBy(anyString())
                                         .build()).thenReturn(sql);
            spyProccessor.execute(mockConnection, mockModel);

            verify(spyProccessor, times(1)).executeSql(sql, mockConnection);
        }
    }

    /**
     * Execute drop successfully.
     *
     * @throws SQLException the SQL exception
     */
    @Test
    public void executeDropSuccessfully() throws SQLException {
        HDBSequenceDropProcessor spyProccessor = spy(HDBSequenceDropProcessor.class);
        try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
                MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
            HDBSequence mockModel = mock(HDBSequence.class);
            String sql = "TestExecuteDropSuccessfully";

            sqlFactory.when(() -> SqlFactory.getNative(mockConnection))
                      .thenReturn(mockSqlFactory);

            sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection))
                      .thenReturn(new HanaSqlDialect());
            when(mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .exists(mockConnection, mockModel.getName(), DatabaseArtifactTypes.SEQUENCE))
                      .thenReturn(true);
            when(mockModel.isClassic()).thenReturn(true);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection))
                      .thenReturn(mockSqlFactory);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .drop())
                      .thenReturn(drop);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .drop()
                                            .sequence(any()))
                      .thenReturn(mockDropSequenceBuilder);
            sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                            .drop()
                                            .sequence(any())
                                            .build())
                      .thenReturn(sql);

            spyProccessor.execute(mockConnection, mockModel);

            verify(spyProccessor, times(1)).executeSql(sql, mockConnection);
        }

    }

    /**
     * Execute create failed.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeCreateFailed() throws Exception {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            HDBSequenceCreateProcessor spyProccessor = spy(HDBSequenceCreateProcessor.class);

            HDBSequence mockModel = mock(HDBSequence.class);
            try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
                    MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
                when(mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
                when(mockModel.isClassic()).thenReturn(false);
                sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection))
                          .thenReturn(new PostgresSqlDialect());
                problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                              .thenAnswer((Answer<Void>) invocation -> null);
                spyProccessor.execute(mockConnection, mockModel);
            }
        });
    }

    /**
     * Execute update failed.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeUpdateFailed() throws Exception {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            HDBSequenceUpdateProcessor spyProccessor = spy(HDBSequenceUpdateProcessor.class);
            HDBSequence mockModel = mock(HDBSequence.class);
            try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
                    MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
                when(mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
                when(mockModel.isClassic()).thenReturn(false);
                sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection))
                          .thenReturn(new PostgresSqlDialect());
                problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                              .thenAnswer((Answer<Void>) invocation -> null);
                spyProccessor.execute(mockConnection, mockModel);
            }
        });
    }

    /**
     * Execute drop failed.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeDropFailed() throws Exception {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            HDBSequenceDropProcessor spyProccessor = spy(HDBSequenceDropProcessor.class);
            try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
                    MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class);
                    MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {

                HDBSequence mockModel = mock(HDBSequence.class);
                when(mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
                sqlFactory.when(() -> SqlFactory.getNative(mockConnection))
                          .thenReturn(mockSqlFactory);
                sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
                                                .exists(mockConnection, mockModel.getName(), DatabaseArtifactTypes.SEQUENCE))
                          .thenReturn(true);
                when(mockModel.isClassic()).thenReturn(false);
                sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection))
                          .thenReturn(new PostgresSqlDialect());
                problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                              .thenAnswer((Answer<Void>) invocation -> null);
                spyProccessor.execute(mockConnection, mockModel);
            }
        });
    }

}
