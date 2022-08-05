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
package com.codbex.kronos.xsodata.ds.service;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.xsodata.core.HdbxsodataLexer;
import com.codbex.kronos.parser.xsodata.core.HdbxsodataParser;
import com.codbex.kronos.parser.xsodata.custom.HDBXSODataCoreListener;
import com.codbex.kronos.parser.xsodata.custom.HDBXSODataSyntaxErrorListener;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataBindingType;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataEntity;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataParameter;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataService;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.xsodata.ds.api.IODataParser;
import com.codbex.kronos.xsodata.ds.model.DBArtifactModel;
import com.codbex.kronos.xsodata.ds.model.ODataModel;

import static com.codbex.kronos.utils.CommonsDBUtils.getSynonymTargetObjectMetadata;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.engine.odata2.transformers.DBMetadataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The factory for creation of the data structure models from source content.
 */
public class ODataParser implements IODataParser {

  private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);

  private static final List<String> METADATA_VIEW_TYPES = List.of(ISqlKeywords.METADATA_CALC_VIEW, ISqlKeywords.METADATA_VIEW);
  private static final List<String> METADATA_SYNONYM_TYPES = List.of(ISqlKeywords.METADATA_SYNONYM);
  private static final List<String> METADATA_ENTITY_TYPES = List.of(ISqlKeywords.METADATA_TABLE, ISqlKeywords.METADATA_CALC_VIEW,
      ISqlKeywords.METADATA_VIEW);
  private static final String PUBLIC_SCHEMA = "PUBLIC";

  private static final Logger logger = LoggerFactory.getLogger(ODataParser.class);

  private DBMetadataUtil dbMetadataUtil = new DBMetadataUtil();

  /**
   * Creates a odata model from the raw content.
   *
   * @param content the odata definition
   * @return the odata model instance
   * @throws IOException exception during parsing
   */
  public ODataModel parseODataArtifact(String location, String content)
      throws IOException, SQLException, ArtifactParserException {
    logger.debug("Parsing xsodata.");
    ODataModel odataModel = new ODataModel();
    odataModel.setName(new File(location).getName());
    odataModel.setLocation(location);
    odataModel.setHash(DigestUtils.md5Hex(content));
    odataModel.setCreatedBy(UserFacade.getName());
    odataModel.setCreatedAt(new Timestamp(new java.util.Date().getTime()));

    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HdbxsodataLexer lexer = new HdbxsodataLexer(inputStream);
    HDBXSODataSyntaxErrorListener lexerErrorListener = new HDBXSODataSyntaxErrorListener();
    lexer.addErrorListener(lexerErrorListener);
    lexer.removeErrorListeners();

    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    HdbxsodataParser parser = new HdbxsodataParser(tokenStream);
    parser.setBuildParseTree(true);
    parser.removeErrorListeners();
    HDBXSODataSyntaxErrorListener parserErrorListener = new HDBXSODataSyntaxErrorListener();
    parser.addErrorListener(parserErrorListener);

    ParseTree parseTree = parser.xsodataDefinition();
    CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location, CommonsConstants.ODATA_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location, CommonsConstants.ODATA_PARSER);

    HDBXSODataCoreListener coreListener = new HDBXSODataCoreListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(coreListener, parseTree);

    HDBXSODataService antlr4Model = coreListener.getServiceModel();
    odataModel.setService(antlr4Model);

    applyConditions(location, odataModel);

    return odataModel;
  }

  private void applyConditions(String location, ODataModel odataModel) throws SQLException {
    try {
      //the order of invocation matter, so do not change it
      applyEmptyExistCondition(location, odataModel);
      applyEntitySetCondition(odataModel);

      applyEmptyNamespaceCondition(location, odataModel);
      applyKeysCondition(odataModel);
      applyNavEntryFromEndCondition(odataModel);
      applyNumberOfJoinPropertiesCondition(odataModel);
      applyOrderOfJoinPropertiesCondition(odataModel);
      applyParametersToViewsCondition(odataModel);
      applyOmittedParamResultCondition(odataModel);
    } catch (Exception ex) {
      CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PARSER_ERROR, location, CommonsConstants.ODATA_PARSER);
      throw ex;
    }
  }

  private void applyEmptyExistCondition(String location, ODataModel odataModel) throws SQLException {
    for (HDBXSODataEntity entity : odataModel.getService().getEntities()) {
      if (entity.getKeyList().size() > 0) {
        if (!checkIfEntityExist(entity.getRepositoryObject().getCatalogObjectName())) {
          throw new OData2TransformerException(String
              .format("Entity: %s from %s don't exist. make sure the artifacts exist before processing the xsodata file",
                  entity.getRepositoryObject().getCatalogObjectName(), location));
        }
      }
    }
  }

  /**
   * If the namespace is not specified, the schema namespace in the EDMX metadata document will be the repository package of
   * the service definition file concatenated with the repository object name.
   * E.g. if the location name of the .xsodata file is /hdb-xsodata/test.xsodata the namespace will implicitly be hdb-xsodata.test
   */
  private void applyEmptyNamespaceCondition(String location, ODataModel odataModel) {
    if (odataModel.getService().getNamespace() == null) {
      String namespace = CommonsUtils.getRepositoryNamespace(location) + "." + FilenameUtils.getBaseName(location);
      odataModel.getService().setNamespace(namespace);
    }
  }

  /**
   * keyslist must not be specified for objects of type 'table'.
   * They must only be applied to objects referring a view type.
   * keygenerated in turn, can be applied to table objects
   */
  private void applyKeysCondition(ODataModel odataModel) throws SQLException {
    for (HDBXSODataEntity entity : odataModel.getService().getEntities()) {
      if (entity.getKeyList().size() > 0) {
        String catalogObjectName = getCorrectCatalogObjectName(entity);

        if (!checkIfEntityIsOfViewType(catalogObjectName)) {
          throw new OData2TransformerException("Artefact of type View with name " + entity.getRepositoryObject().getCatalogObjectName() +
                  " is not found. Synonym with name " + entity.getRepositoryObject().getCatalogObjectName() + " is not found.");
        }
      }
    }
  }

  /**
   * If the Alias is not specified in an entity, the Alias for this object is named after the repository object name.
   * For example, if object is "sap.hana.xs.doc/odata_docu", then the Alias is implicitly set to odata_docu,
   * which then can also be referenced in associations.
   */
  private void applyEntitySetCondition(ODataModel odataModel) {
    odataModel.getService().getEntities().forEach(entity -> {
      if (StringUtils.isEmpty(entity.getAlias()) || entity.getAlias() == null) {
        String baseObjName = CommonsUtils.extractBaseObjectNameFromCatalogName(entity.getRepositoryObject().getCatalogObjectName());
        if (!StringUtils.isEmpty(baseObjName) || baseObjName == null) {
          entity.setAlias(baseObjName);
        } else {
          entity.setAlias(entity.getRepositoryObject().getCatalogObjectName());
        }
      }
    });
  }

  /**
   * The fromend in a navigation entry must be specified if the endtype is the same for both the principalend and the dependentend of an association.
   * Here is a working example:
   * <pre>
   *  "tables::A" navigates ("A_Self" as "ARef" from principal);
   *  association "A_Self"
   * 	principal "A"("SelfID") multiplicity "1"
   * 	dependent "A"("ID") multiplicity "0..1";
   * 	</pre>
   */
  private void applyNavEntryFromEndCondition(ODataModel odataModel) {
    odataModel.getService().getAssociations().forEach(ass -> {
      if (ass.getDependent().getEntitySetName().equals(ass.getPrincipal().getEntitySetName())) {
        List<HDBXSODataEntity> entity = odataModel.getService().getEntities().stream()
            .filter(el -> el.getAlias().equals(ass.getDependent().getEntitySetName())).collect(Collectors.toList());
        if (entity.size() == 1) {
          entity.get(0).getNavigates().forEach(nav -> {
            if (nav.getAssociation().equals(ass.getName())) {
              if (nav.getFromBindingType() == null) {
                throw new OData2TransformerException(String
                    .format("Both ends of association %s are of type %s. Specify whether to navigate from principal or dependent.",
                        ass.getName(), ass.getDependent().getEntitySetName()));
              }
            }
          });
        } else {
          throw new OData2TransformerException(String.format("Missing entity with alias: %s", ass.getDependent().getEntitySetName()));
        }
      }
    });
  }

  /**
   * The number of joinproperties in the principalend must be the same as in the dependentend.
   * Here is a working example:
   * <pre>
   *  association "someName"
   * 	principal "A"("ID1","ID2") multiplicity "1"
   * 	dependent "B"("ID3","ID4") multiplicity "0..1";
   * 	</pre>
   */
  private void applyNumberOfJoinPropertiesCondition(ODataModel odataModel) {
    odataModel.getService().getAssociations().forEach(ass -> {
      if (ass.getDependent().getBindingRole().getKeys().size() != ass.getPrincipal().getBindingRole().getKeys().size()) {
        throw new OData2TransformerException(String.format("Different number of properties in ends of association %s.", ass.getName()));
      }
    });
  }

  /**
   * The overprincipalend corresponds to the principalend.
   * The number of properties in the joinproperties and the overproperties must be the same and their ordering is relevant.
   * The same statement is true for the dependent end.
   * Here is a working example:
   * <pre>
   *
   * "tables::A" navigates ("B_A_linktab" as "linkedBs");
   * "tables::B" navigates ("B_A_linktab" as "linkedAs");
   *
   * association "B_A_linktab"
   *  principal "A"("ID") multiplicity "*"
   *  dependent "B"("ID1") multiplicity "*"
   *  over "tables::linktab"
   *  principal ("AID")
   *  dependent ("BID1","BID2");
   * 	</pre>
   */
  private void applyOrderOfJoinPropertiesCondition(ODataModel odataModel) {
    odataModel.getService().getAssociations().forEach(ass -> {
      if (ass.getAssociationTable() != null) {
        if (ass.getAssociationTable().getDependent().getKeys().size() != ass.getDependent().getBindingRole().getKeys().size()) {
          throw new OData2TransformerException(String
              .format("Different number of %s properties in association %s", HDBXSODataBindingType.DEPENDENT.getText(), ass.getName()));
        }
        if (ass.getAssociationTable().getPrincipal().getKeys().size() != ass.getPrincipal().getBindingRole().getKeys().size()) {
          throw new OData2TransformerException(String
              .format("Different number of %s properties in association %s", HDBXSODataBindingType.PRINCIPAL.getText(), ass.getName()));
        }
      }
    });
  }

  /**
   * Specifying parameters is only possible for calculation views and analytic views
   */
  private void applyParametersToViewsCondition(ODataModel odataModel) throws SQLException {
    for (HDBXSODataEntity entity : odataModel.getService().getEntities()) {
      if (entity.getParameterEntitySet() != null) {
        String catalogObjectName = getCorrectCatalogObjectName(entity);

        if (!checkIfEntityIsOfViewType(catalogObjectName)) {
          throw new OData2TransformerException(String
              .format("Parameters are not allowed for entity %s as it is not a calculation or analytical view.",
                  entity.getRepositoryObject().getCatalogObjectName()));
        }
      }
    }
  }

  /**
   * If the parametersresultsprop is omitted, the navigation property from the parameter entity set to the entity is called "Results".
   * The default parameterentitysetname is the entitysetname of the entity concatenated with the suffix "Parameters".
   * Here is a working example:
   * <pre>
   * "sap.test.odata.db.views::COUNT_VIEW" as "Count"
   * 		keys generate local "ID"
   * 		parameters via entity "CountParameters" results property "Results";
   * 	</pre>
   */
  private void applyOmittedParamResultCondition(ODataModel odataModel) {
    for (HDBXSODataEntity entity : odataModel.getService().getEntities()) {
      if (entity.getParameterType() != null) {
        HDBXSODataParameter defaultParam = new HDBXSODataParameter();
        if (entity.getParameterEntitySet() != null && entity.getParameterEntitySet().getParameterEntitySetName() == null) {
          entity.setParameterEntitySet(defaultParam.setParameterEntitySetName(entity.getAlias() + "Parameters"));
        }
        if (entity.getParameterEntitySet() != null && entity.getParameterEntitySet().getParameterResultsProperty() == null) {
          entity.setParameterEntitySet(defaultParam.setParameterResultsProperty("Results"));
        }
      }
    }
  }

  private boolean checkIfEntityIsOfSynonymType(String artifactName) throws SQLException {
    return checkIfEntityIsFromAGivenDBType(artifactName, METADATA_SYNONYM_TYPES);
  }

  private boolean checkIfEntityIsOfViewType(String artifactName) throws SQLException {
    return checkIfEntityIsFromAGivenDBType(artifactName, METADATA_VIEW_TYPES);
  }

  private boolean checkIfEntityIsFromAGivenDBType(String artifactName, List<String> dbTypes) throws SQLException {
    List<DBArtifactModel> artifacts = getDBArtifactsByName(artifactName);
    Optional<DBArtifactModel> filteredArtifact = artifacts.stream()
        .parallel()
        .filter(artifact -> dbTypes.contains(artifact.getType()))
        .findAny();

    DBArtifactModel dbArtifact = filteredArtifact.isPresent()? filteredArtifact.get() : filterSynonymObjects(artifacts, dbTypes, artifactName);

    return null != dbArtifact;
  }

  private boolean checkIfEntityExist(String artifactName) throws SQLException {
    return checkIfEntityIsFromAGivenDBType(artifactName, METADATA_ENTITY_TYPES);
  }

  private String getCorrectCatalogObjectName(HDBXSODataEntity entity) throws SQLException {
    PersistenceTableModel targetObjectMetadata = new PersistenceTableModel();
    String catalogObjectName;

    if (checkIfEntityIsOfSynonymType(entity.getRepositoryObject().getCatalogObjectName())) {
      targetObjectMetadata = getSynonymTargetObjectMetadata(dataSource, entity.getRepositoryObject().getCatalogObjectName(),
          entity.getRepositoryObject().getCatalogObjectSchema());
    }

    if (targetObjectMetadata.getTableName() == null) {
      catalogObjectName = entity.getRepositoryObject().getCatalogObjectName();
    } else {
      catalogObjectName = targetObjectMetadata.getTableName();
    }

    return catalogObjectName;
  }

  private DBArtifactModel filterSynonymObjects(List<DBArtifactModel> artifacts, List<String> dbTypes, String artifactName)
      throws SQLException {
    Optional<DBArtifactModel> synonym = artifacts.stream()
        .parallel()
        .filter(artifact -> ISqlKeywords.METADATA_SYNONYM.equalsIgnoreCase(artifact.getType())).findAny();

    String targetSchema = synonym.isPresent()? synonym.get().getSchema() : PUBLIC_SCHEMA;
    return getTargetObjectOfSynonymIfAny(targetSchema, artifactName, dbTypes);
  }

  private DBArtifactModel getTargetObjectOfSynonymIfAny(String schemaName, String artifactName, List<String> dbTypes)
      throws SQLException {
    PersistenceTableModel targetObjectMetadata = getSynonymTargetObjectMetadata(dataSource, artifactName, schemaName);

    String type = targetObjectMetadata.getTableType();
    if (type != null && dbTypes.contains(type)) {
      String name = targetObjectMetadata.getTableName();
      String schema = targetObjectMetadata.getSchemaName();
      return new DBArtifactModel(name, type, schema);
    }

    return null;
  }

  public List<DBArtifactModel> getDBArtifactsByName(String artifactName) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      DatabaseMetaData databaseMetaData = connection.getMetaData();
      ResultSet rs = databaseMetaData.getTables(connection.getCatalog(), Configuration.get("HANA_USERNAME"), artifactName, null);
      List<DBArtifactModel> artifacts = new ArrayList<>();
      while (rs.next()) {
        artifacts.add(new DBArtifactModel(rs.getString("TABLE_NAME"), rs.getString("TABLE_TYPE"),
            rs.getString("TABLE_SCHEM")));
      }
      return artifacts;
    }
  }
}
