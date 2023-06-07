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
package com.codbex.kronos.xsodata.ds.handler;

import static com.codbex.kronos.xsodata.ds.handler.AbstractKronosOData2EventHandler.AFTER_TABLE_NAME_CONTEXT_KEY;
import static com.codbex.kronos.xsodata.ds.handler.AbstractKronosOData2EventHandler.BEFORE_TABLE_NAME_CONTEXT_KEY;
import static com.codbex.kronos.xsodata.ds.handler.AbstractKronosOData2EventHandler.CONNECTION_CONTEXT_KEY;
import static com.codbex.kronos.xsodata.ds.handler.AbstractKronosOData2EventHandler.DATASOURCE_CONTEXT_KEY;
import static com.codbex.kronos.xsodata.ds.handler.AbstractKronosOData2EventHandler.ENTRY_JSON_CONTEXT_KEY;
import static com.codbex.kronos.xsodata.ds.handler.AbstractKronosOData2EventHandler.SQL_BUILDER_CONTEXT_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.core.ep.BasicEntityProvider;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.engine.odata2.definition.ODataHandlerDefinition;
import org.eclipse.dirigible.engine.odata2.service.ODataCoreService;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLQueryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.gson.internal.LinkedTreeMap;


@RunWith(MockitoJUnitRunner.class)
public class KronosOData2EventHandlerTest {

  @Mock
  private EdmType edmType;

  @Mock
  private UriInfo uriInfo;

  @Mock
  private ODataCoreService odataCoreService;

  @Mock
  private DataSource dataSource;

  @Mock
  private ODataEntry entry;

  @Mock
  private InputStream inputStream;

  @Mock
  private SQLQueryBuilder queryBuilder;

  private static MockedStatic<StaticObjects> staticObjects;

  private KronosScriptingOData2EventHandler spyScriptingHandler;
  private KronosProcedureOData2EventHandler spyProcedureHandler;
  private KronosOData2EventHandler kronosOData2EventHandler;
  private Map<Object, Object> scriptingHandlerContext;
  private Map<Object, Object> procedureHandlerContext;

  @Before
  public void setup() {
    staticObjects = Mockito.mockStatic(StaticObjects.class);
    staticObjects.when(() -> StaticObjects.get(StaticObjects.DATASOURCE)).thenReturn(dataSource, dataSource);
    spyScriptingHandler = Mockito.spy(new KronosScriptingOData2EventHandler());
    spyProcedureHandler = Mockito.spy(new KronosProcedureOData2EventHandler());
    kronosOData2EventHandler = new KronosOData2EventHandler(odataCoreService, spyProcedureHandler, spyScriptingHandler);
    scriptingHandlerContext = new HashMap();
    procedureHandlerContext = new HashMap();
    scriptingHandlerContext.put(SQL_BUILDER_CONTEXT_KEY,  queryBuilder);
    procedureHandlerContext.put(SQL_BUILDER_CONTEXT_KEY,  queryBuilder);
    scriptingHandlerContext.put(DATASOURCE_CONTEXT_KEY, dataSource);
    procedureHandlerContext.put(DATASOURCE_CONTEXT_KEY, dataSource);
  }

  @After
  public void teardown() {
    staticObjects.close();
  }

  @Test
  public void testBeforeCreateEntity() throws org.apache.olingo.odata2.api.exception.ODataException, ODataException, SQLException {
    // procedure
    mockGetHandlers();
    mockTemporaryTables(spyProcedureHandler);
    mockCallProcedure(spyProcedureHandler, false);
    ODataResponse response = kronosOData2EventHandler.beforeCreateEntity(uriInfo, "application/json", "application/json", entry,
        procedureHandlerContext);
    assertResponse(response);

    // .xsjslib
    mockTemporaryTables(spyScriptingHandler);
    Mockito.doNothing().when(spyScriptingHandler).callSuperBeforeCreateEntity(any(), any(), any(), any(), any());
    kronosOData2EventHandler.beforeCreateEntity(uriInfo, "application/json", "application/json", entry, scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey("connection"));
    assertTrue(scriptingHandlerContext.containsKey("afterTableName"));
  }

  @Test
  public void testAfterCreateEntity() throws ODataException, org.apache.olingo.odata2.api.exception.ODataException, SQLException {
    // procedure
    mockGetHandlers();
    mockTemporaryTables(spyProcedureHandler);
    mockCallProcedure(spyProcedureHandler, false);
    ODataResponse response = kronosOData2EventHandler.afterCreateEntity(uriInfo, "application/json", "application/json", entry,
        procedureHandlerContext);
    assertResponse(response);

    // .xsjslib
    mockTemporaryTables(spyScriptingHandler);
    Mockito.doNothing().when(spyScriptingHandler).callSuperAfterCreateEntity(any(), any(), any(), any(), any());
    kronosOData2EventHandler.afterCreateEntity(uriInfo, "application/json", "application/json", entry, scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(AFTER_TABLE_NAME_CONTEXT_KEY));
  }

  @Test
  public void testOnCreateEntity() throws ODataException, org.apache.olingo.odata2.api.exception.ODataException, SQLException {
    // procedure
    mockGetHandlers();
    mockTemporaryTables(spyProcedureHandler);
    mockCallProcedure(spyProcedureHandler, false);
    ODataResponse response = kronosOData2EventHandler.onCreateEntity(uriInfo, inputStream, "application/json", "application/json",
        procedureHandlerContext);
    assertResponse(response);

    // .xsjslib
    mockTemporaryTables(spyScriptingHandler);
    Mockito.doNothing().when(spyScriptingHandler).callSuperOnCreateEntity(any(), any(), any(), any(), any());
    kronosOData2EventHandler.onCreateEntity(uriInfo, inputStream, "application/json", "application/json", scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(AFTER_TABLE_NAME_CONTEXT_KEY));
  }

  @Test
  public void testBeforeUpdateEntity() throws ODataException, org.apache.olingo.odata2.api.exception.ODataException, SQLException {
    // procedure
    mockGetHandlers();
    mockTemporaryTables(spyProcedureHandler);
    mockCallProcedure(spyProcedureHandler, true);
    ODataResponse response = kronosOData2EventHandler.beforeUpdateEntity(uriInfo, "application/json", true, "application/json", entry,
        procedureHandlerContext);
    assertTrue(procedureHandlerContext.containsKey(ENTRY_JSON_CONTEXT_KEY));
    assertResponse(response);

    // .xsjslib
    mockTemporaryTables(spyScriptingHandler);
    Mockito.doNothing().when(spyScriptingHandler).callSuperBeforeUpdateEntity(any(), any(), anyBoolean(), any(), any(), any());
    kronosOData2EventHandler.beforeUpdateEntity(uriInfo, "application/json", true, "application/json", entry, scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(AFTER_TABLE_NAME_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(ENTRY_JSON_CONTEXT_KEY));
  }

  @Test
  public void testAfterUpdateEntity() throws SQLException, ODataException, org.apache.olingo.odata2.api.exception.ODataException {
    // procedure
    mockGetHandlers();
    mockTemporaryTables(spyProcedureHandler);
    mockCallProcedure(spyProcedureHandler, true);
    ODataResponse response = kronosOData2EventHandler.afterUpdateEntity(uriInfo, "application/json", true, "application/json", entry,
        procedureHandlerContext);
    assertResponse(response);

    // .xsjslib
    mockTemporaryTables(spyScriptingHandler);
    Mockito.doNothing().when(spyScriptingHandler).callSuperAfterUpdateEntity(any(), any(), anyBoolean(), any(), any(), any());
    kronosOData2EventHandler.afterUpdateEntity(uriInfo, "application/json", true, "application/json", entry, scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(AFTER_TABLE_NAME_CONTEXT_KEY));
  }

  @Test
  public void testOnUpdateEntity() throws SQLException, ODataException, org.apache.olingo.odata2.api.exception.ODataException {
    // procedure
    mockGetHandlers();
    mockTemporaryTables(spyProcedureHandler);
    mockCallProcedure(spyProcedureHandler, true);
    ODataResponse response = kronosOData2EventHandler.onUpdateEntity(uriInfo, inputStream, "application/json", true, "application/json",
        procedureHandlerContext);
    assertResponse(response);

    // .xsjslib
    mockTemporaryTables(spyScriptingHandler);
    Mockito.doNothing().when(spyScriptingHandler).callSuperOnUpdateEntity(any(), any(), any(), anyBoolean(), any(), any());
    kronosOData2EventHandler.onUpdateEntity(uriInfo, inputStream, "application/json", true, "application/json", scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(AFTER_TABLE_NAME_CONTEXT_KEY));
  }

  @Test
  public void testBeforeDeleteEntity() throws SQLException, ODataException, org.apache.olingo.odata2.api.exception.ODataException {
    // procedure
    mockGetHandlers();
    mockTemporaryTables(spyProcedureHandler);
    mockCallProcedure(spyProcedureHandler, false);
    ODataResponse response = kronosOData2EventHandler.beforeDeleteEntity(uriInfo, "application/json", procedureHandlerContext);
    assertTrue(procedureHandlerContext.containsKey(ENTRY_JSON_CONTEXT_KEY));
    assertResponse(response);

    // .xsjslib
    mockTemporaryTables(spyScriptingHandler);
    Mockito.doNothing().when(spyScriptingHandler).callSuperBeforeDeleteEntity(any(), any(), any());
    kronosOData2EventHandler.beforeDeleteEntity(uriInfo, "application/json", scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(ENTRY_JSON_CONTEXT_KEY));
  }

  @Test
  public void testAfterDeleteEntity() throws SQLException, ODataException, org.apache.olingo.odata2.api.exception.ODataException {
    // procedure
    mockGetHandlers();
    mockTemporaryTables(spyProcedureHandler);
    mockCallProcedure(spyProcedureHandler, false);
    ODataResponse response = kronosOData2EventHandler.afterDeleteEntity(uriInfo, "application/json", procedureHandlerContext);
    assertResponse(response);

    // .xsjslib
    mockTemporaryTables(spyScriptingHandler);
    Mockito.doNothing().when(spyScriptingHandler).callSuperAfterDeleteEntity(any(), any(), any());
    kronosOData2EventHandler.afterDeleteEntity(uriInfo, "application/json", scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME_CONTEXT_KEY));
  }

  @Test
  public void testOnDeleteEntity() throws SQLException, ODataException, org.apache.olingo.odata2.api.exception.ODataException {
    // procedure
    mockGetHandlers();
    mockTemporaryTables(spyProcedureHandler);
    mockCallProcedure(spyProcedureHandler, false);
    ODataResponse response = kronosOData2EventHandler.onDeleteEntity(uriInfo, "application/json", procedureHandlerContext);
    assertResponse(response);

    // .xsjslib
    mockTemporaryTables(spyScriptingHandler);
    Mockito.doNothing().when(spyScriptingHandler).callSuperOnDeleteEntity(any(), any(), any());
    kronosOData2EventHandler.onDeleteEntity(uriInfo, "application/json", scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION_CONTEXT_KEY));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME_CONTEXT_KEY));
  }

  private void mockGetHandlers() throws EdmException, ODataException {
    Mockito.when(uriInfo.getTargetType()).thenReturn(edmType, edmType);
    Mockito.when(edmType.getNamespace()).thenReturn("test-namespace", "test-namespace");
    Mockito.when(edmType.getName()).thenReturn("test-name", "test-name");

    ODataHandlerDefinition procedureHandler = new ODataHandlerDefinition();
    procedureHandler.setHandler("test-namespace::procedure");
    List<ODataHandlerDefinition> procedureHandlers = List.of(procedureHandler);
    ODataHandlerDefinition scriptingHandler = new ODataHandlerDefinition();
    scriptingHandler.setHandler("test-namespace::handler.xsjslib::handlerFunction");
    List<ODataHandlerDefinition> scriptingHandlers = List.of(scriptingHandler);
    Mockito.when(odataCoreService.getHandlers(any(), any(), any(), any()))
        .thenReturn(procedureHandlers, scriptingHandlers);
  }

  private void mockTemporaryTables(AbstractKronosOData2EventHandler handler)
      throws org.apache.olingo.odata2.api.exception.ODataException, SQLException {
    Mockito.doReturn("test-table").when(handler).getSQLInsertBuilderTargetTable(any(), any());

    Mockito.doReturn("test-table").when(handler).getSQLUpdateBuilderTargetTable(any(), any());

    Mockito.doReturn("test-table").when(handler).getSQLDeleteBuilderTargetTable(any(), any());

    Mockito.doNothing().when(handler).createTemporaryTableLikeTable(any(), any(), any());

    Mockito.doNothing().when(handler).createTemporaryTableAsSelect(any(), any(), any(), any());

    Mockito.doNothing().when(handler).insertIntoTemporaryTable(any(), any(), any(), any());

    Mockito.doNothing().when(handler).updateTemporaryTable(any(), any(), any(), any());

    Mockito.doReturn(new HashMap<>()).when(handler).readEntryMap(any(), any());

    if (handler instanceof KronosProcedureOData2EventHandler) {
      Mockito.doReturn("TEST_SCHEMA").when((KronosProcedureOData2EventHandler) handler).getODataArtifactTypeSchema("test-table");
    }
  }

  private void mockCallProcedure(KronosProcedureOData2EventHandler handler, boolean isUpdate)
      throws SQLException {
    ResultSet resultSetMock = Mockito.mock(ResultSet.class);
    Mockito.when(resultSetMock.next()).thenReturn(true);
    Mockito.when(resultSetMock.getString(anyString())).thenReturn("400", "INVALID ID");
    Mockito.when(resultSetMock.getString(anyInt())).thenReturn("detail message 1", "detail message 2");

    ResultSetMetaData metaData = Mockito.mock(ResultSetMetaData.class);

    Mockito.when(metaData.getColumnCount()).thenReturn(2);
    Mockito.when(metaData.getColumnName(anyInt())).thenReturn("errorDetail1", "errorDetail2");

    Mockito.when(resultSetMock.getMetaData()).thenReturn(metaData);

    if (isUpdate) {
      Mockito.doReturn(resultSetMock).when(handler).callProcedure(any(), any(), any(), any(), any());
    } else {
      Mockito.doReturn(resultSetMock).when(handler).callProcedure(any(), any(), any(), any());
    }
  }

  private void assertResponse(ODataResponse response) throws EntityProviderException {
    assertEquals(HttpStatusCodes.BAD_REQUEST, response.getStatus());
    assertEquals("application/json", response.getHeader("Content-Type"));

    Map<String, Object> responseBody = GsonHelper.fromJson(new BasicEntityProvider().readText(((InputStream) response.getEntity())),
        HashMap.class);

    assertEquals("INVALID ID", ((LinkedTreeMap) ((LinkedTreeMap) responseBody.get("error")).get("message")).get("value"));
    assertEquals("{\n"
    		+ "  \"errordetail\": {\n"
    		+ "    \"errorDetail2\": \"detail message 2\",\n"
    		+ "    \"errorDetail1\": \"detail message 1\"\n"
    		+ "  }\n"
    		+ "}",
        (((LinkedTreeMap) responseBody.get("error")).get("innererror")));
  }
}
