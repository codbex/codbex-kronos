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
package com.codbex.kronos.engine.xsodata.transformers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.components.api.platform.ProblemsFacade;
import org.eclipse.dirigible.components.odata.transformers.ODataDatabaseMetadataUtil;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.codbex.kronos.engine.xsodata.domain.XSOData;
import com.codbex.kronos.engine.xsodata.model.DBArtifactModel;
import com.codbex.kronos.engine.xsodata.transformers.XSODataArtefactParser;
import com.codbex.kronos.parser.xsodata.model.XSODataAggregationType;

/**
 * The Class ODataParserTest.
 */
@ExtendWith(MockitoExtension.class)
public class ODataParserTest {

    /** The mock connection. */
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Connection mockConnection;

    /** The db metadata util. */
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ODataDatabaseMetadataUtil dbMetadataUtil;

    /** The mock synonym target object metadata. */
    @Mock
    private PersistenceTableModel mockSynonymTargetObjectMetadata;

    /** The mock result set. */
    @Mock
    private ResultSet mockResultSet;

    /** The mock result set when synonym. */
    @Mock
    private ResultSet mockResultSetWhenSynonym;

    /** The mock result set entity exist. */
    @Mock
    private ResultSet mockResultSetEntityExist;

    /** The mock data source. */
    @Mock
    private DataSource mockDataSource;

    /** The artifact return type. */
    @Mock
    private DBArtifactModel artifactReturnType;

    /** The mock prepared statement. */
    @Mock
    private PreparedStatement mockPreparedStatement;

    /** The parser. */
    @Spy
    @InjectMocks
    private final XSODataArtefactParser parser = new XSODataArtefactParser();

    /**
     * Open mocks.
     */
    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Parses the xsodata file successfully.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseXsodataFileSuccessfully() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_all_set_of_navigations.xsodata"),
                StandardCharsets.UTF_8);
        XSOData oDataModel = parser.parseXSOData("np/entity_with_all_set_of_navigations.xsodata", content);
        assertEquals("entity_with_all_set_of_navigations.xsodata", oDataModel.getName());
        assertEquals("np/entity_with_all_set_of_navigations.xsodata", oDataModel.getLocation());
        assertNotNull(oDataModel.getService());

        // no need to check the service content the parser module unit tests cover it
    }

    /**
     * Test validate edm multiplicity.
     *
     * @throws Exception the exception
     */
    // @Test(expected = ArtifactParserException.class)
    public void testValidateEdmMultiplicity() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_wrong_syntax.xsodata"),
                StandardCharsets.UTF_8);
        parser.parseXSOData("np/entity_wrong_syntax.xsodata", content);
    }

    /**
     * Test apply empty namespace condition.
     *
     * @throws Exception the exception
     */
    @Test
    public void testApplyEmptyNamespaceCondition() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_no_namespace.xsodata"),
                StandardCharsets.UTF_8);
        XSOData oDataModel = parser.parseXSOData("/a_1/b-2/c/entity_with_no_namespace.xsodata", content);
        assertEquals("a_1.b-2.c.entity_with_no_namespace", oDataModel.getService()
                                                                     .getNamespace());
    }

    /**
     * Test apply keys condition successfully.
     *
     * @throws Exception the exception
     */
    // @Test
    public void testApplyKeysConditionSuccessfully() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_keys.xsodata"),
                StandardCharsets.UTF_8);
        XSOData model = parser.parseXSOData("/a_1/b-2/c/entity_with_no_namespace.xsodata", content);
        assertNotNull(model, "ODataModel should not be null after parsing");
    }

    /**
     * Test apply keys condition successfully when synonym.
     *
     * @throws Exception the exception
     */
    // @Test
    public void testApplyKeysConditionSuccessfullyWhenSynonym() throws Exception {
        mockGetTablesSuccessfullyWhenSynonym();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_keys.xsodata"),
                StandardCharsets.UTF_8);
        XSOData model = parser.parseXSOData("/a_1/b-2/c/entity_with_no_namespace.xsodata", content);
        assertNotNull(model, "ODataModel should not be null after parsing");
    }

    /**
     * Test apply keys condition fail.
     *
     * @throws Exception the exception
     */
    // @Test(expected = OData2TransformerException.class)
    public void testApplyKeysConditionFail() throws Exception {
        mockGetTablesFail();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_keys.xsodata"),
                StandardCharsets.UTF_8);
        parser.parseXSOData("/a_1/b-2/c/entity_with_no_namespace.xsodata", content);
    }

    /**
     * Test apply keys condition fail when synonym.
     *
     * @throws Exception the exception
     */
    // @Test(expected = OData2TransformerException.class)
    public void testApplyKeysConditionFailWhenSynonym() throws Exception {
        mockGetTablesFailWhenSynonym();
        mockGetTable();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_keys.xsodata"),
                StandardCharsets.UTF_8);
        parser.parseXSOData("/a_1/b-2/c/entity_with_no_namespace.xsodata", content);
    }

    /**
     * Test apply entity set condition.
     *
     * @throws Exception the exception
     */
    @Test
    public void testApplyEntitySetCondition() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_no_namespace.xsodata"),
                StandardCharsets.UTF_8);
        XSOData oDataModel = parser.parseXSOData("/entity_with_no_namespace.xsodata", content);
        assertEquals(oDataModel.getService()
                               .getEntities()
                               .get(0)
                               .getAlias(),
                "MyView");
        assertEquals(oDataModel.getService()
                               .getEntities()
                               .get(1)
                               .getAlias(),
                "view");
        assertEquals(oDataModel.getService()
                               .getEntities()
                               .get(2)
                               .getAlias(),
                "view");
        assertEquals(oDataModel.getService()
                               .getEntities()
                               .get(3)
                               .getAlias(),
                "view");
    }

    /**
     * Test apply nav entry from end condition successfully.
     *
     * @throws Exception the exception
     */
    @Test
    public void testApplyNavEntryFromEndConditionSuccessfully() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_navigation.xsodata"),
                StandardCharsets.UTF_8);
        XSOData oDataModel = parser.parseXSOData("/entity_with_navigation.xsodata", content);
        assertEquals(2, oDataModel.getService()
                                  .getEntities()
                                  .size());
        assertEquals(1, oDataModel.getService()
                                  .getAssociations()
                                  .size());
    }

    /**
     * Test apply nav entry from end condition fail.
     *
     * @throws Exception the exception
     */
    // @Test(expected = OData2TransformerException.class)
    public void testApplyNavEntryFromEndConditionFail() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_navigation_error.xsodata"),
                StandardCharsets.UTF_8);
        parser.parseXSOData("/entity_with_navigation_error.xsodata", content);
    }

    /**
     * Test apply number of join properties condition fail.
     *
     * @throws Exception the exception
     */
    // @Test(expected = OData2TransformerException.class)
    public void testApplyNumberOfJoinPropertiesConditionFail() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_wrong_join_prop.xsodata"),
                StandardCharsets.UTF_8);
        parser.parseXSOData("/entity_with_wrong_join_prop.xsodata", content);
    }

    /**
     * Test apply order of join properties condition.
     *
     * @throws Exception the exception
     */
    // @Test(expected = OData2TransformerException.class)
    public void testApplyOrderOfJoinPropertiesCondition() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_wrong_over_join_prop.xsodata"),
                StandardCharsets.UTF_8);
        parser.parseXSOData("/entity_with_wrong_over_join_prop.xsodata", content);
    }

    /**
     * Test implicit aggregation.
     *
     * @throws Exception the exception
     */
    @Test
    public void testImplicitAggregation() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_implicit_aggregation.xsodata"),
                StandardCharsets.UTF_8);
        XSOData oDataModel = parser.parseXSOData("/entity_with_implicit_aggregation.xsodata", content);
        assertEquals(0, oDataModel.getService()
                                  .getEntities()
                                  .get(0)
                                  .getAggregations()
                                  .size());
        assertEquals(XSODataAggregationType.IMPLICIT, oDataModel.getService()
                                                                .getEntities()
                                                                .get(0)
                                                                .getAggregationType());
    }

    /**
     * Test explicit aggregation.
     *
     * @throws Exception the exception
     */
    @Test
    public void testExplicitAggregation() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_explicit_aggregation.xsodata"),
                StandardCharsets.UTF_8);
        XSOData oDataModel = parser.parseXSOData("/entity_with_explicit_aggregation.xsodata", content);
        assertEquals(3, oDataModel.getService()
                                  .getEntities()
                                  .get(0)
                                  .getAggregations()
                                  .size());
        assertEquals(XSODataAggregationType.EXPLICIT, oDataModel.getService()
                                                                .getEntities()
                                                                .get(0)
                                                                .getAggregationType());
    }

    /**
     * Test apply parameters to views condition.
     *
     * @throws Exception the exception
     */
    // @Test(expected = OData2TransformerException.class)
    public void testApplyParametersToViewsCondition() throws Exception {
        mockGetTablesFail();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_params.xsodata"),
                StandardCharsets.UTF_8);
        parser.parseXSOData("/entity_with_params.xsodata", content);
    }

    /**
     * Test apply parameters to views condition when synonym.
     *
     * @throws Exception the exception
     */
    // @Test(expected = OData2TransformerException.class)
    public void testApplyParametersToViewsConditionWhenSynonym() throws Exception {
        mockGetTablesFailWhenSynonym();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_params.xsodata"),
                StandardCharsets.UTF_8);
        parser.parseXSOData("/entity_with_params.xsodata", content);
    }

    /**
     * Test apply omitted param result condition.
     *
     * @throws Exception the exception
     */
    // @Test
    public void testApplyOmittedParamResultCondition() throws Exception {
        mockGetTablesSuccessfully();
        String content = IOUtils.toString(this.getClass()
                                              .getResourceAsStream("/entity_with_params.xsodata"),
                StandardCharsets.UTF_8);
        XSOData oDataModel = parser.parseXSOData("/entity_with_params.xsodata", content);
        assertEquals("CalcViewParameters", oDataModel.getService()
                                                     .getEntities()
                                                     .get(0)
                                                     .getParameterEntitySet()
                                                     .getParameterEntitySetName());
        assertEquals("Results", oDataModel.getService()
                                          .getEntities()
                                          .get(0)
                                          .getParameterEntitySet()
                                          .getParameterResultsProperty());
    }

    /**
     * Mock get tables successfully.
     *
     * @throws Exception the exception
     */
    private void mockGetTablesSuccessfully() throws Exception {
        mockGetTable();
        mockGetDBArtifactsByName();
        mockProblemsFacade();
        // when(mockResultSet.next()).thenReturn(true);
    }

    /**
     * Mock get tables successfully when synonym.
     *
     * @throws Exception the exception
     */
    private void mockGetTablesSuccessfullyWhenSynonym() throws Exception {
        mockGetTable();
        mockGetDBArtifactsByName();
        mockProblemsFacade();
    }

    /**
     * Mock get tables fail.
     *
     * @throws Exception the exception
     */
    private void mockGetTablesFail() throws Exception {
        mockGetTable();
        mockProblemsFacade();
        mockDBArtifactsByNameMissing();
    }

    /**
     * Mock get tables fail when synonym.
     *
     * @throws Exception the exception
     */
    private void mockGetTablesFailWhenSynonym() throws Exception {
        mockGetTable();
        mockProblemsFacade();
        mockDBArtifactsByNameMissing();
    }

    /**
	 * Mock get table.
	 *
	 * @throws Exception the exception
	 */
	private void mockGetTable() throws Exception {
		//when(mockDataSource.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
		//when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
	}

    /**
     * Mock DB artifacts by name missing.
     *
     * @throws SQLException the SQL exception
     */
    private void mockDBArtifactsByNameMissing() throws SQLException {
        DBArtifactModel dbArtifactModelPlaceholder = new DBArtifactModel("PLACEHOLDER", "PLACEHOLDER", "PLACEHOLDER");
        doReturn(List.of(dbArtifactModelPlaceholder)).when(parser)
                                                     .getDBArtifactsByName(anyString());
    }

    /**
     * Mock get DB artifacts by name.
     *
     * @throws SQLException the SQL exception
     */
    private void mockGetDBArtifactsByName() throws SQLException {
        DBArtifactModel dbArtifactModelView =
                new DBArtifactModel(ISqlKeywords.METADATA_VIEW, ISqlKeywords.METADATA_VIEW, ISqlKeywords.METADATA_VIEW);
        DBArtifactModel dbArtifactModelCalcView =
                new DBArtifactModel(ISqlKeywords.METADATA_CALC_VIEW, ISqlKeywords.METADATA_CALC_VIEW, ISqlKeywords.METADATA_CALC_VIEW);
        DBArtifactModel dbArtifactModelSynonym =
                new DBArtifactModel(ISqlKeywords.METADATA_SYNONYM, ISqlKeywords.METADATA_SYNONYM, ISqlKeywords.METADATA_SYNONYM);
        // doReturn(List.of(dbArtifactModelView, dbArtifactModelCalcView,
        // dbArtifactModelSynonym)).when(parser).getDBArtifactsByName(anyString());
    }

    /**
     * Mock problems facade.
     *
     * @throws Exception the exception
     */
    private void mockProblemsFacade() throws Exception {
        try (MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
            problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                          .thenAnswer((Answer<Void>) invocation -> null);
        }
    }
}
