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
package com.codbex.kronos.engine.xsodata.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.components.data.structures.domain.Table;
import org.eclipse.dirigible.components.data.structures.domain.TableColumn;
import org.eclipse.dirigible.components.odata.api.ODataAssociation;
import org.eclipse.dirigible.components.odata.api.ODataAssociationEnd;
import org.eclipse.dirigible.components.odata.api.ODataEntity;
import org.eclipse.dirigible.components.odata.api.ODataHandler;
import org.eclipse.dirigible.components.odata.api.ODataHandlerTypes;
import org.eclipse.dirigible.components.odata.api.ODataNavigation;
import org.eclipse.dirigible.components.odata.api.ODataParameter;
import org.eclipse.dirigible.components.odata.api.ODataProperty;
import org.eclipse.dirigible.components.odata.domain.OData;
import org.eclipse.dirigible.components.odata.transformers.ODataDatabaseMetadataUtil;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableColumnModel;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.xsodata.domain.XSOData;
import com.codbex.kronos.engine.xsodata.transformers.TableMetadataProvider;
import com.codbex.kronos.engine.xsodata.transformers.XSOData2TransformerException;
import com.codbex.kronos.parser.xsodata.model.XSODataAggregation;
import com.codbex.kronos.parser.xsodata.model.XSODataAggregationType;
import com.codbex.kronos.parser.xsodata.model.XSODataAssociation;
import com.codbex.kronos.parser.xsodata.model.XSODataEntity;
import com.codbex.kronos.parser.xsodata.model.XSODataEventType;
import com.codbex.kronos.parser.xsodata.model.XSODataModification;
import com.codbex.kronos.parser.xsodata.model.XSODataMultiplicityType;
import com.codbex.kronos.parser.xsodata.model.XSODataNavigation;

/**
 * The Class ODataUtils.
 */
public class ODataUtils {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(ODataUtils.class);
  
  /**
	 * Gets the data source.
	 *
	 * @return the data source
	 */
  private DataSource getDataSource() { 
	  return (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);
  }

  /** The metadata provider. */
  private TableMetadataProvider metadataProvider;
  
  /** The db metadata util. */
  private ODataDatabaseMetadataUtil dbMetadataUtil = new ODataDatabaseMetadataUtil();

  /**
   * Instantiates a new o data utils.
   *
   * @param metadataProvider the metadata provider
   */
  public ODataUtils(TableMetadataProvider metadataProvider) {
    this.metadataProvider = metadataProvider;
  }

  /**
   * Convert O data model to O data definition.
   *
   * @param oDataModel the o data model
   * @return the o data definition
   */
  public OData convertODataModelToODataDefinition(XSOData oDataModel) {
    OData oDataDefinitionModel = new OData();

    oDataDefinitionModel.setLocation(oDataModel.getLocation());

    String namespace = oDataModel.getService().getNamespace() != null ? oDataModel.getService().getNamespace() : "Default";
    oDataDefinitionModel.setNamespace(namespace);
    oDataDefinitionModel.setName(oDataModel.getName());

    for (XSODataEntity entity : oDataModel.getService().getEntities()) {
      List<PersistenceTableColumnModel> allEntityParameters = new ArrayList<>();

      String tableName = entity.getRepositoryObject().getCatalogObjectName();

      ODataEntity oDataEntityDefinition = new ODataEntity();
      oDataEntityDefinition.setName(entity.getAlias());
      oDataEntityDefinition.setAlias(entity.getAlias());
      oDataEntityDefinition.setTable(tableName);

      entity.getNavigates().forEach(processNavigation(oDataModel, oDataDefinitionModel, oDataEntityDefinition));

      // Set properties
      try {
        Table tableMetadata = metadataProvider.getPersistenceTableModel(tableName);

        if (tableMetadata == null) {
          logger.error("DB artifact {} not available for entity {}, so it will be skipped.", tableName, entity.getAlias());
          continue;
        }

        List<TableColumn> allEntityDbColumns = tableMetadata.getColumns();

        if (ISqlKeywords.METADATA_CALC_VIEW.equals(tableMetadata.getKind()) && entity.getWithPropertyProjections().isEmpty() && entity
            .getWithoutPropertyProjections().isEmpty()) {

          getParametersForCalcView(allEntityParameters, tableName);

          allEntityDbColumns.forEach(el -> {
            ODataProperty oDataProperty = new ODataProperty();
            oDataProperty.setName(el.getName());
            oDataProperty.setColumn(el.getName());
            oDataProperty.setNullable(el.isNullable());
            oDataProperty.setType(el.getType());
            oDataEntityDefinition.getProperties().add(oDataProperty);
          });
        }

        entity.getWithPropertyProjections().forEach(prop -> {
          ODataProperty oDataProperty = new ODataProperty();
          oDataProperty.setName(prop);
          oDataProperty.setColumn(prop);
          List<TableColumn> dbProp = allEntityDbColumns.stream().filter(x -> x.getName().equals(prop))
              .collect(Collectors.toList());
          if (!dbProp.isEmpty()) {
            oDataProperty.setNullable(dbProp.get(0).isNullable());
            oDataProperty.setType(dbProp.get(0).getType());
          }
          oDataEntityDefinition.getProperties().add(oDataProperty);
        });

        if (!entity.getWithoutPropertyProjections().isEmpty()) {
          allEntityDbColumns.forEach(el -> {
            if (entity.getWithoutPropertyProjections().stream().noneMatch(x -> x.equals(el.getName()))) {
              ODataProperty oDataProperty = new ODataProperty();
              oDataProperty.setName(el.getName());
              oDataProperty.setColumn(el.getName());
              oDataProperty.setNullable(el.isNullable());
              oDataProperty.setType(el.getType());
              oDataEntityDefinition.getProperties().add(oDataProperty);
            }
          });
        }
      } catch (SQLException e) {
        throw new XSOData2TransformerException(e);
      }

      List<ODataHandler> handlers = new ArrayList<>();
      entity.getModifications().forEach(processModification(oDataEntityDefinition, handlers));
      handlers.forEach(el -> oDataEntityDefinition.getHandlers().add(el));

      if (!entity.getKeyList().isEmpty()) {
        oDataEntityDefinition.setKeys(entity.getKeyList());
      } else if (entity.getKeyGenerated() != null) {
        oDataEntityDefinition.setKeyGenerated(entity.getKeyGenerated());
      }

      // Process Aggregations
      if (XSODataAggregationType.EXPLICIT.equals(entity.getAggregationType())) {
        oDataEntityDefinition.getAnnotationsEntityType().put("sap:semantics", "aggregate");
        for (XSODataAggregation aggregation : entity.getAggregations()) {
          oDataEntityDefinition.getAggregationsTypeAndColumn()
              .put(aggregation.getAggregateColumnName(), aggregation.getAggregateFunction());
        }
      } else if (XSODataAggregationType.IMPLICIT.equals(entity.getAggregationType())) {
        oDataEntityDefinition.getAnnotationsEntityType().put("sap:semantics", "aggregate");
      }

      oDataDefinitionModel.getEntities().add(oDataEntityDefinition);

      if (entity.getParameterType() != null) {
        processParameters(oDataDefinitionModel, oDataEntityDefinition, entity, allEntityParameters, tableName);
      }
    }
    return oDataDefinitionModel;
  }

  /**
   * Process modification.
   *
   * @param oDataEntityDefinition the o data entity definition
   * @param handlers the handlers
   * @return the consumer
   */
  private Consumer<XSODataModification> processModification(ODataEntity oDataEntityDefinition,
      List<ODataHandler> handlers) {
    return modification -> {
      modification.getSpecification().getEvents().forEach(event -> {
        if (validateHandlerType(event.getType())) {
          ODataHandler oDataHandler = new ODataHandler();
          oDataHandler.setHandler(event.getAction());
          oDataHandler.setType(event.getType().getOdataHandlerType());
          oDataHandler.setMethod(modification.getMethod().getOdataHandlerType());
          handlers.add(oDataHandler);
        }
      });
      if (modification.getSpecification().isForbidden()) {
        ODataHandler oDataHandler = new ODataHandler();
        oDataHandler.setType(ODataHandlerTypes.forbid.name());
        oDataHandler.setMethod(modification.getMethod().getOdataHandlerType());
        handlers.add(oDataHandler);
        oDataEntityDefinition.getAnnotationsEntitySet().put(modification.getMethod().getOdataSAPAnnotation(), "false");
      }
      if (modification.getSpecification().getModificationAction() != null) {
        ODataHandler oDataHandler = new ODataHandler();
        oDataHandler.setHandler(modification.getSpecification().getModificationAction());
        oDataHandler.setType(ODataHandlerTypes.on.name());
        oDataHandler.setMethod(modification.getMethod().getOdataHandlerType());
        handlers.add(oDataHandler);
      }
    };
  }

  /**
   * Process navigation.
   *
   * @param oDataModel the o data model
   * @param oDataDefinitionModel the o data definition model
   * @param oDataEntityDefinition the o data entity definition
   * @return the consumer
   */
  Consumer<XSODataNavigation> processNavigation(XSOData oDataModel,
      OData oDataDefinitionModel, ODataEntity oDataEntityDefinition) {
    return navigate -> {
      ODataNavigation oDataNavigation = new ODataNavigation();
      oDataNavigation.setName(navigate.getAliasNavigation());
      oDataNavigation.setAssociation(navigate.getAssociation());
      oDataEntityDefinition.getNavigations().add(oDataNavigation);

      //set navigations
      ODataAssociation oDataAssociationDefinition = new ODataAssociation();
      oDataAssociationDefinition.setName(navigate.getAssociation());
      XSODataAssociation xsOdataAssoc = 
          getAssociation(oDataModel, navigate.getAssociation(), navigate.getAliasNavigation());

      ODataAssociationEnd fromDef = new ODataAssociationEnd();
      fromDef.setEntity(xsOdataAssoc.getPrincipal().getEntitySetName());

      //The Multiplicity of the Principal role must be 1 or 0..1
      validateEdmMultiplicity(xsOdataAssoc.getPrincipal().getMultiplicityType().getText(), navigate.getAssociation());
      fromDef.setMultiplicity(xsOdataAssoc.getPrincipal().getMultiplicityType().getText());
      fromDef.setProperties(xsOdataAssoc.getPrincipal().getBindingRole().getKeys());
      ODataAssociationEnd toDef = new ODataAssociationEnd();
      toDef.setEntity(xsOdataAssoc.getDependent().getEntitySetName());

      //The Multiplicity of the Principal role must be 1, 0..1, 1..*, *
      //convert 1..* to *, because odata do not support it
      if (xsOdataAssoc.getDependent().getMultiplicityType().getText().equals(XSODataMultiplicityType.ONE_TO_MANY.getText())) {
        toDef.setMultiplicity(EdmMultiplicity.MANY.toString());
      } else {
        validateEdmMultiplicity(xsOdataAssoc.getDependent().getMultiplicityType().getText(), navigate.getAssociation());
        toDef.setMultiplicity(xsOdataAssoc.getDependent().getMultiplicityType().getText());
      }

      toDef.setProperties(xsOdataAssoc.getDependent().getBindingRole().getKeys());
      oDataAssociationDefinition.setFrom(fromDef);
      oDataAssociationDefinition.setTo(toDef);

      if (xsOdataAssoc.getDependent().getMultiplicityType().getText().equals(EdmMultiplicity.MANY.toString())
          && xsOdataAssoc.getPrincipal().getMultiplicityType().getText().equals(EdmMultiplicity.MANY.toString())) {

        fromDef.getMappingTable().setMappingTableName(xsOdataAssoc.getAssociationTable().getRepositoryObject());
        fromDef.getMappingTable().setMappingTableJoinColumn(xsOdataAssoc.getAssociationTable().getPrincipal().getKeys().get(0));

        toDef.getMappingTable().setMappingTableName(xsOdataAssoc.getAssociationTable().getRepositoryObject());
        toDef.getMappingTable().setMappingTableJoinColumn(xsOdataAssoc.getAssociationTable().getDependent().getKeys().get(0));
      }

      oDataDefinitionModel.getAssociations().add(oDataAssociationDefinition);
    };
  }

  /**
   * Validate if provided multiplicity from xsodata can be mapped to olingo ones.
   *
   * @param actualValue the actual value
   * @param assName the ass name
   */
  void validateEdmMultiplicity(String actualValue, String assName) {
    try {
      EdmMultiplicity.fromLiteral(actualValue);
    } catch (IllegalArgumentException ex) {
      throw new XSOData2TransformerException(String.format("Unsupported multiplicity %s for association %s", actualValue, assName));
    }
  }

  /**
   * Validate if provided handler type is one of the org.eclipse.dirigible.engine.odata2.definition.ODataHandlerTypes
   *
   * @param eventType the event type
   * @return true, if successful
   */
  boolean validateHandlerType(XSODataEventType eventType) {
    try {
      ODataHandlerTypes.fromValue(eventType.getOdataHandlerType());
    } catch (IllegalArgumentException ex) {
      logger.error(String.format("%s type is not supported", eventType.name()));
      return false;
    }
    return true;
  }

  /**
   * Get input parameters if entity data source is a calculation view.
   *
   * @param allEntityParameters the all entity parameters
   * @param tableName the table name
   * @return the parameters for calc view
   * @throws SQLException the SQL exception
   */
  private void getParametersForCalcView(List<PersistenceTableColumnModel> allEntityParameters, String tableName) throws SQLException {
    try (Connection connection = getDataSource().getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(
          "SELECT DISTINCT VARIABLE_NAME, COLUMN_SQL_TYPE, MANDATORY, \"ORDER\" "
              + "FROM _SYS_BI.BIMC_VARIABLE_VIEW_HDI "
              + "WHERE \"QUALIFIED_NAME\" = ? "
              + "ORDER BY \"ORDER\"")) {
        statement.setString(1, tableName);

        try (ResultSet calcViewParameters = statement.executeQuery()) {

          while (calcViewParameters.next()) {
            PersistenceTableColumnModel calcViewParam = new PersistenceTableColumnModel();

            String calcViewParamName = calcViewParameters.getString("VARIABLE_NAME");
            String calcViewParamType = calcViewParameters.getString("COLUMN_SQL_TYPE");
            String calcViewParamMandatory = calcViewParameters.getString("MANDATORY");

            boolean isNullable = false;

            if (calcViewParamMandatory.equals("0")) {
              isNullable = true;
            }

            Integer index = calcViewParamType.indexOf("(");

            if (index != -1) {
              calcViewParamType = calcViewParamType.substring(0, index);
            }

            calcViewParam.setName(calcViewParamName);
            calcViewParam.setType(dbMetadataUtil.convertSqlTypeToOdataEdmType(calcViewParamType));
            calcViewParam.setNullable(isNullable);

            allEntityParameters.add(calcViewParam);
          }
        }
      }
    }
  }

  /**
   * Create a second parameter entity.
   *
   * @param oDataDefinitionModel the o data definition model
   * @param oDataEntityDefinition the o data entity definition
   * @param entity the entity
   * @param allEntityParameters the all entity parameters
   * @param tableName the table name
   */
  private void processParameters(OData oDataDefinitionModel, ODataEntity oDataEntityDefinition,
      XSODataEntity entity, List<PersistenceTableColumnModel> allEntityParameters, String tableName) {
    ODataEntity oDataEntityParametersDefinition = new ODataEntity();

    String parameterEntitySetName = entity.getParameterEntitySet().getParameterEntitySetName();

    oDataEntityParametersDefinition.setName(parameterEntitySetName);
    oDataEntityParametersDefinition.setAlias(parameterEntitySetName);
    oDataEntityParametersDefinition.setTable(tableName);

    allEntityParameters.forEach(el -> {
      ODataParameter oDataParameter = new ODataParameter();
      oDataParameter.setName(el.getName());
      oDataParameter.setNullable(el.isNullable());
      oDataParameter.setType(el.getType());

      oDataEntityDefinition.getParameters().add(oDataParameter);
      oDataEntityParametersDefinition.getParameters().add(oDataParameter);
    });

    ODataAssociation oDataParametersAssociation = new ODataAssociation();
    oDataParametersAssociation.setName(oDataEntityParametersDefinition.getName() + "_" + oDataEntityDefinition.getName() + "Type");

    ODataAssociationEnd oDataParametersFrom = new ODataAssociationEnd();
    ODataAssociationEnd oDataParametersTo = new ODataAssociationEnd();

    oDataParametersFrom.setEntity(oDataEntityParametersDefinition.getName());
    oDataParametersFrom.setMultiplicity("*");
    oDataParametersTo.setEntity(oDataEntityDefinition.getName());
    oDataParametersTo.setMultiplicity("*");

    oDataParametersAssociation.setFrom(oDataParametersFrom);
    oDataParametersAssociation.setTo(oDataParametersTo);

    oDataDefinitionModel.getAssociations().add(oDataParametersAssociation);

    ODataNavigation oDataResultsNavigation = new ODataNavigation();
    oDataResultsNavigation.setName(entity.getParameterEntitySet().getParameterResultsProperty());
    oDataResultsNavigation.setAssociation(oDataParametersAssociation.getName());
    oDataEntityParametersDefinition.getNavigations().add(oDataResultsNavigation);

    oDataDefinitionModel.getEntities().add(oDataEntityParametersDefinition);
  }
  
  /**
   * Gets the association.
   *
   * @param model the model
   * @param name the name
   * @param navigation the navigation
   * @return the association
   */
  public static XSODataAssociation getAssociation(XSOData model, String name, String navigation) {
      if (model != null && model.getService() != null) {
          for (XSODataAssociation association : model.getService().getAssociations()) {
              if (name != null && name.equals(association.getName())) {
                  return association;
              }
          }
      }
      throw new IllegalArgumentException(
              String.format("There is no association with name: %s, referenced by the navigation: %s", name, navigation));
  }
}
