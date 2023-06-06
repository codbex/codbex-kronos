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
package com.codbex.kronos.engine.hdb.parser.hdbtable;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Connection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.processors.HDBTableAlterHandler;
import com.codbex.kronos.engine.hdb.processors.HDBTableAlterProcessor;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
@ExtendWith(MockitoExtension.class)
public class HDBTableProcessorsTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Connection mockConnection;

  @Mock
  private HDBTable mockModel;

  @Mock
  private HDBTableAlterHandler mockHandler;

  private HDBTableAlterProcessor tableAlterProcessor = spy(new HDBTableAlterProcessor());

  @BeforeEach
  public void openMocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void executeAlterSuccessfully() throws Exception {
    Mockito.doReturn(mockHandler).when(tableAlterProcessor).createTableAlterHandler(mockConnection, mockModel);

    doNothing().when(mockHandler).addColumns(mockConnection);
    doNothing().when(mockHandler).removeColumns(mockConnection);
    doNothing().when(mockHandler).updateColumns(mockConnection);
    doNothing().when(mockHandler).rebuildIndeces(mockConnection);
    doNothing().when(mockHandler).ensurePrimaryKeyIsUnchanged(mockConnection);

    tableAlterProcessor.execute(mockConnection, mockModel);

    verify(mockHandler, times(1)).addColumns(mockConnection);
    verify(mockHandler, times(1)).removeColumns(mockConnection);
    verify(mockHandler, times(1)).updateColumns(mockConnection);
    verify(mockHandler, times(1)).rebuildIndeces(mockConnection);
    verify(mockHandler, times(1)).ensurePrimaryKeyIsUnchanged(mockConnection);
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
