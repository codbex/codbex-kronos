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
package com.codbex.kronos.hdi.ds.processors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBSynonym;
import com.codbex.kronos.engine.hdb.domain.HDBSynonymGroup;
import com.codbex.kronos.engine.hdb.domain.HDBSynonymTarget;
import com.codbex.kronos.engine.hdi.processors.GrantPrivilegesExternalArtifactsSchemaProcessor;

@RunWith(MockitoJUnitRunner.class)
public class GrantPrivilegesExternalArtifactsSchemaProcessorTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Connection mockConnection;

  @Test
  public void testOneSynonymFile() throws DataStructuresException, SQLException, IOException {
    SynonymDefinition definition = new SynonymDefinition("externalArtefact1", "schema");
    HDISynonym synonym = new HDISynonym("/package/test.hdbsynonym", definition);

    testGrantPrivilege(synonym);
  }

  @Test
  public void testOneSynonymFileWithTwoDefinitions() throws DataStructuresException, SQLException, IOException {
    SynonymDefinition definition1 = new SynonymDefinition("externalArtefact1", "schema");
    SynonymDefinition definition2 = new SynonymDefinition("externalArtefact2", "schema2");
    HDISynonym synonym = new HDISynonym("/package/test.hdbsynonym", definition1, definition2);

    testGrantPrivilege(synonym);
  }

  @Test
  public void testTwoSynonymFileWithTwoDefinitions() throws DataStructuresException, SQLException, IOException {
    SynonymDefinition definition1 = new SynonymDefinition("externalArtefact1", "schema");
    SynonymDefinition definition2 = new SynonymDefinition("externalArtefact2", "schema2");
    HDISynonym synonym = new HDISynonym("/package/test.hdbsynonym", definition1, definition2);

    SynonymDefinition definition3 = new SynonymDefinition("externalArtefact3", "schema3");
    SynonymDefinition definition4 = new SynonymDefinition("externalArtefact4", "schema4");
    HDISynonym synonym2 = new HDISynonym("/package/.hdbsynonym", definition3, definition4);

    testGrantPrivilege(synonym, synonym2);
  }

  public void testGrantPrivilege(HDISynonym... synonyms) throws SQLException, IOException, DataStructuresException {
    List<String> deploys = new ArrayList<>();
    List<String> synonymDeploys = Arrays.stream(synonyms).map(HDISynonym::getLocation).collect(Collectors.toList());
    deploys.addAll(synonymDeploys);
    deploys.add("/some-package/calc-view.hdbcalculationview");
    deploys.add("/some-package-2/tablefunction.hdbtablefunction");

    GrantPrivilegesExternalArtifactsSchemaProcessor processorSpy = stubProcessor(synonyms);

    String container = "testContainer";
    String containerOwner = container + "#OO";
    processorSpy.execute(mockConnection, container, deploys.toArray(new String[0]));

    Arrays.stream(synonyms).forEach(bombBox(synonym -> {
      synonym.getModel().getSynonymDefinitions().forEach( bombBox((key, definition) -> {
        String schema = definition.getTarget().getSchema();
        String mockSQLSelect = "GRANT SELECT ON SCHEMA \"" + schema + "\" TO \"" + containerOwner + "\" WITH GRANT OPTION;";
        String mockSQLExecute = "GRANT EXECUTE ON SCHEMA \"" + schema + "\" TO \"" + containerOwner + "\" WITH GRANT OPTION;";
        String mockSQLInsert = "GRANT INSERT ON SCHEMA \"" + schema + "\" TO \"" + containerOwner + "\" WITH GRANT OPTION;";
        String mockSQLUpdate = "GRANT UPDATE ON SCHEMA \"" + schema + "\" TO \"" + containerOwner + "\" WITH GRANT OPTION;";
        String mockSQLDelete = "GRANT DELETE ON SCHEMA \"" + schema + "\" TO \"" + containerOwner + "\" WITH GRANT OPTION;";
        verify(processorSpy, times(1)).executeUpdate(mockConnection, mockSQLExecute, new String[]{});
        verify(processorSpy, times(1)).executeUpdate(mockConnection, mockSQLSelect, new String[]{});
        verify(processorSpy, times(1)).executeUpdate(mockConnection, mockSQLInsert, new String[]{});
        verify(processorSpy, times(1)).executeUpdate(mockConnection, mockSQLUpdate, new String[]{});
        verify(processorSpy, times(1)).executeUpdate(mockConnection, mockSQLDelete, new String[]{});
      }));

      verify(processorSpy, times(1)).getSynonymModel(eq(synonym.getLocation()), anyString());
    }));

  }

  private GrantPrivilegesExternalArtifactsSchemaProcessor stubProcessor(HDISynonym[] synonyms) {
    GrantPrivilegesExternalArtifactsSchemaProcessor processorSpy = spy(GrantPrivilegesExternalArtifactsSchemaProcessor.class);
    Arrays.stream(synonyms).forEach(bombBox(synonym -> {
      doReturn(synonym.getModel()).when(processorSpy).getSynonymModel(eq(synonym.getLocation()), anyString());
      doReturn("blablabla").when(processorSpy).getSynonymContent(synonym.getLocation());
    }));
    return processorSpy;
  }

  static <T> Consumer<T> bombBox(ReThrowingConsumer<T, Exception> throwingConsumer) {
    return i -> {
      try {
        throwingConsumer.accept(i);
      } catch (Exception ex) {
        Assert.fail("Unexpected exception occur! - " + ex.getMessage());
      }
    };
  }

  static <T, X> BiConsumer<T, X> bombBox(ReThrowingBiConsumer<T, X, Exception> throwingConsumer) {
    return (i, y) -> {
      try {
        throwingConsumer.accept(i, y);
      } catch (Exception ex) {
        Assert.fail("Unexpected exception occur! - " + ex.getMessage());
      }
    };
  }
}

@FunctionalInterface
interface ReThrowingConsumer<T, E extends Exception> {
  void accept(T t) throws E;
}

@FunctionalInterface
interface ReThrowingBiConsumer<T, X, E extends Exception> {
  void accept(T t, X x) throws E;
}

class HDISynonym {
  final String location;
  final HDBSynonymGroup model;

  public HDISynonym(String location, SynonymDefinition... definitions){
    this.location = location;
    this.model = createSynonymModel(definitions);
  }

  public String getLocation() {
    return location;
  }

  public HDBSynonymGroup getModel() {
    return model;
  }

  private HDBSynonymGroup createSynonymModel(SynonymDefinition... definitions){
	  HDBSynonymGroup synonymModel = new HDBSynonymGroup();

    Map<String, HDBSynonym> models = Arrays.stream(definitions)
        .collect(Collectors.toMap(SynonymDefinition::getArtifactName, SynonymDefinition::getModel));
    synonymModel.setSynonymDefinitions(models);

    return synonymModel;
  }
}

class SynonymDefinition {
  final String artifactName;
  final HDBSynonym model;

  public SynonymDefinition(String artifactName, String schemaName){
    this.artifactName = artifactName;
    this.model = createSynonymDefinition(artifactName, schemaName);
  }

  private HDBSynonym createSynonymDefinition(String artifactName, String schemaName){
    HDBSynonym synonymDefinitionModel = new HDBSynonym();
    synonymDefinitionModel.setTarget(new HDBSynonymTarget(synonymDefinitionModel, artifactName, schemaName));
    return synonymDefinitionModel;
  }

  public String getArtifactName(){
    return artifactName;
  }

  public HDBSynonym getModel() {
    return model;
  }
}