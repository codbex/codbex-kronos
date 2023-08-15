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
package com.codbex.kronos.engine.xsodata.transformers;

import static com.codbex.kronos.utils.CommonsDBUtils.getSynonymTargetObjectMetadata;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.xsodata.domain.XSOData;
import com.codbex.kronos.engine.xsodata.model.DBArtifactModel;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.xsodata.core.XSODataLexer;
import com.codbex.kronos.parser.xsodata.core.XSODataParser;
import com.codbex.kronos.parser.xsodata.custom.XSODataDefinitionListener;
import com.codbex.kronos.parser.xsodata.custom.XSODataSyntaxErrorListener;
import com.codbex.kronos.parser.xsodata.model.XSODataBindingType;
import com.codbex.kronos.parser.xsodata.model.XSODataEntity;
import com.codbex.kronos.parser.xsodata.model.XSODataParameter;
import com.codbex.kronos.parser.xsodata.model.XSODataService;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The factory for creation of the data structure models from source content.
 */
@Component
public class XSODataArtefactParser implements InitializingBean {

	/** The Constant METADATA_VIEW_TYPES. */
	private static final List<String> METADATA_VIEW_TYPES = List.of(ISqlKeywords.METADATA_CALC_VIEW,
			ISqlKeywords.METADATA_VIEW);

	/** The Constant METADATA_SYNONYM_TYPES. */
	private static final List<String> METADATA_SYNONYM_TYPES = List.of(ISqlKeywords.METADATA_SYNONYM);

	/** The Constant METADATA_ENTITY_TYPES. */
	private static final List<String> METADATA_ENTITY_TYPES = List.of(ISqlKeywords.METADATA_TABLE,
			ISqlKeywords.METADATA_CALC_VIEW, ISqlKeywords.METADATA_VIEW);

	/** The Constant PUBLIC_SCHEMA. */
	private static final String PUBLIC_SCHEMA = "PUBLIC";

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(XSODataArtefactParser.class);

	/** The instance. */
	private static XSODataArtefactParser INSTANCE;

	/**
	 * After properties set.
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		INSTANCE = this;
	}

	/**
	 * Gets the.
	 *
	 * @return the XSOData service
	 */
	public static XSODataArtefactParser get() {
		return INSTANCE;
	}

	/** The SystemDB datasource. */
	@Autowired
	@Qualifier("SystemDB")
	private DataSource systemDatasource;

	/** The DefaultDB datasource. */
	@Autowired
	private DataSource defaultDatasource;

	/**
	 * Gets the datasource.
	 *
	 * @return the datasource
	 */
	public DataSource getSystemDatasource() {
		return systemDatasource;
	}

	public DataSource getDefaultDatasource() {
		return defaultDatasource;
	}
	
	/**
	 * Creates a odata model from the raw content.
	 *
	 * @param location the location
	 * @param content  the odata definition
	 * @return the odata model instance
	 * @throws IOException             exception during parsing
	 * @throws SQLException            the SQL exception
	 * @throws ArtifactParserException the artifact parser exception
	 */
	public XSOData parseXSOData(String location, String content)
			throws IOException, SQLException, ArtifactParserException {
		XSOData odataModel = new XSOData();
		return parseXSOData(location, content, odataModel);
	}

	/**
	 * Creates a odata model from the raw content.
	 *
	 * @param location the location
	 * @param content  the odata definition
	 * @return the odata model instance
	 * @throws IOException             exception during parsing
	 * @throws SQLException            the SQL exception
	 * @throws ArtifactParserException the artifact parser exception
	 */
	public XSOData parseXSOData(String location, String content, XSOData odataModel)
			throws IOException, SQLException, ArtifactParserException {
		logger.debug("Parsing xsodata.");
		
		odataModel.setName(new File(location).getName());
		odataModel.setLocation(location);

		ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
		ANTLRInputStream inputStream = new ANTLRInputStream(is);
		XSODataLexer lexer = new XSODataLexer(inputStream);
		XSODataSyntaxErrorListener lexerErrorListener = new XSODataSyntaxErrorListener();
		lexer.addErrorListener(lexerErrorListener);
		lexer.removeErrorListeners();

		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		XSODataParser parser = new XSODataParser(tokenStream);
		parser.setBuildParseTree(true);
		parser.removeErrorListeners();
		XSODataSyntaxErrorListener parserErrorListener = new XSODataSyntaxErrorListener();
		parser.addErrorListener(parserErrorListener);

		ParseTree parseTree = parser.xsOdataDefinition();
		CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location,
				CommonsConstants.ODATA_PARSER);
		CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location,
				CommonsConstants.ODATA_PARSER);

		XSODataDefinitionListener coreListener = new XSODataDefinitionListener();
		ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
		parseTreeWalker.walk(coreListener, parseTree);

		XSODataService antlr4Model = coreListener.getServiceModel();
		odataModel.setService(antlr4Model);

		applyConditions(location, odataModel);

		return odataModel;
	}

	/**
	 * Apply conditions.
	 *
	 * @param location   the location
	 * @param odataModel the odata model
	 * @throws SQLException the SQL exception
	 */
	private void applyConditions(String location, XSOData odataModel) throws SQLException {
		try {
			// the order of invocation matter, so do not change it
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
			CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PARSER_ERROR, location,
					CommonsConstants.ODATA_PARSER);
			throw ex;
		}
	}

	/**
	 * Apply empty exist condition.
	 *
	 * @param location   the location
	 * @param odataModel the odata model
	 * @throws SQLException the SQL exception
	 */
	private void applyEmptyExistCondition(String location, XSOData odataModel) throws SQLException {
		for (XSODataEntity entity : odataModel.getService().getEntities()) {
			if (entity.getKeyList().size() > 0) {
				if (!checkIfEntityExist(entity.getRepositoryObject().getCatalogObjectName())) {
					throw new XSOData2TransformerException(String.format(
							"Entity: %s from %s don't exist. make sure the artifacts exist before processing the xsodata file",
							entity.getRepositoryObject().getCatalogObjectName(), location));
				}
			}
		}
	}

	/**
	 * If the namespace is not specified, the schema namespace in the EDMX metadata
	 * document will be the repository package of the service definition file
	 * concatenated with the repository object name. E.g. if the location name of
	 * the .xsodata file is /hdb-xsodata/test.xsodata the namespace will implicitly
	 * be hdb-xsodata.test
	 *
	 * @param location   the location
	 * @param odataModel the odata model
	 */
	private void applyEmptyNamespaceCondition(String location, XSOData odataModel) {
		if (odataModel.getService().getNamespace() == null) {
			String namespace = CommonsUtils.getRepositoryNamespace(location) + "."
					+ FilenameUtils.getBaseName(location);
			odataModel.getService().setNamespace(namespace);
		}
	}

	/**
	 * keyslist must not be specified for objects of type 'table'. They must only be
	 * applied to objects referring a view type. keygenerated in turn, can be
	 * applied to table objects
	 *
	 * @param odataModel the odata model
	 * @throws SQLException the SQL exception
	 */
	private void applyKeysCondition(XSOData odataModel) throws SQLException {
		for (XSODataEntity entity : odataModel.getService().getEntities()) {
			if (entity.getKeyList().size() > 0) {
				String catalogObjectName = getCorrectCatalogObjectName(entity);

				if (!checkIfEntityIsOfViewType(catalogObjectName)) {
					throw new XSOData2TransformerException("Artefact of type View with name "
							+ entity.getRepositoryObject().getCatalogObjectName() + " is not found. Synonym with name "
							+ entity.getRepositoryObject().getCatalogObjectName() + " is not found.");
				}
			}
		}
	}

	/**
	 * If the Alias is not specified in an entity, the Alias for this object is
	 * named after the repository object name. For example, if object is
	 * "sap.hana.xs.doc/odata_docu", then the Alias is implicitly set to odata_docu,
	 * which then can also be referenced in associations.
	 *
	 * @param odataModel the odata model
	 */
	private void applyEntitySetCondition(XSOData odataModel) {
		odataModel.getService().getEntities().forEach(entity -> {
			if (StringUtils.isEmpty(entity.getAlias()) || entity.getAlias() == null) {
				String baseObjName = CommonsUtils
						.extractBaseObjectNameFromCatalogName(entity.getRepositoryObject().getCatalogObjectName());
				if (!StringUtils.isEmpty(baseObjName) || baseObjName == null) {
					entity.setAlias(baseObjName);
				} else {
					entity.setAlias(entity.getRepositoryObject().getCatalogObjectName());
				}
			}
		});
	}

	/**
	 * The fromend in a navigation entry must be specified if the endtype is the
	 * same for both the principalend and the dependentend of an association. Here
	 * is a working example:
	 * 
	 * <pre>
	 *  "tables::A" navigates ("A_Self" as "ARef" from principal);
	 *  association "A_Self"
	 * 	principal "A"("SelfID") multiplicity "1"
	 * 	dependent "A"("ID") multiplicity "0..1";
	 * </pre>
	 *
	 * @param odataModel the odata model
	 */
	private void applyNavEntryFromEndCondition(XSOData odataModel) {
		odataModel.getService().getAssociations().forEach(ass -> {
			if (ass.getDependent().getEntitySetName().equals(ass.getPrincipal().getEntitySetName())) {
				List<XSODataEntity> entity = odataModel.getService().getEntities().stream()
						.filter(el -> el.getAlias().equals(ass.getDependent().getEntitySetName()))
						.collect(Collectors.toList());
				if (entity.size() == 1) {
					entity.get(0).getNavigates().forEach(nav -> {
						if (nav.getAssociation().equals(ass.getName())) {
							if (nav.getFromBindingType() == null) {
								throw new XSOData2TransformerException(String.format(
										"Both ends of association %s are of type %s. Specify whether to navigate from principal or dependent.",
										ass.getName(), ass.getDependent().getEntitySetName()));
							}
						}
					});
				} else {
					throw new XSOData2TransformerException(
							String.format("Missing entity with alias: %s", ass.getDependent().getEntitySetName()));
				}
			}
		});
	}

	/**
	 * The number of joinproperties in the principalend must be the same as in the
	 * dependentend. Here is a working example:
	 * 
	 * <pre>
	 *  association "someName"
	 * 	principal "A"("ID1","ID2") multiplicity "1"
	 * 	dependent "B"("ID3","ID4") multiplicity "0..1";
	 * </pre>
	 *
	 * @param odataModel the odata model
	 */
	private void applyNumberOfJoinPropertiesCondition(XSOData odataModel) {
		odataModel.getService().getAssociations().forEach(ass -> {
			if (ass.getDependent().getBindingRole().getKeys().size() != ass.getPrincipal().getBindingRole().getKeys()
					.size()) {
				throw new XSOData2TransformerException(
						String.format("Different number of properties in ends of association %s.", ass.getName()));
			}
		});
	}

	/**
	 * The overprincipalend corresponds to the principalend. The number of
	 * properties in the joinproperties and the overproperties must be the same and
	 * their ordering is relevant. The same statement is true for the dependent end.
	 * Here is a working example:
	 * 
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
	 * </pre>
	 *
	 * @param odataModel the odata model
	 */
	private void applyOrderOfJoinPropertiesCondition(XSOData odataModel) {
		odataModel.getService().getAssociations().forEach(ass -> {
			if (ass.getAssociationTable() != null) {
				if (ass.getAssociationTable().getDependent().getKeys().size() != ass.getDependent().getBindingRole()
						.getKeys().size()) {
					throw new XSOData2TransformerException(
							String.format("Different number of %s properties in association %s",
									XSODataBindingType.DEPENDENT.getText(), ass.getName()));
				}
				if (ass.getAssociationTable().getPrincipal().getKeys().size() != ass.getPrincipal().getBindingRole()
						.getKeys().size()) {
					throw new XSOData2TransformerException(
							String.format("Different number of %s properties in association %s",
									XSODataBindingType.PRINCIPAL.getText(), ass.getName()));
				}
			}
		});
	}

	/**
	 * Specifying parameters is only possible for calculation views and analytic
	 * views.
	 *
	 * @param odataModel the odata model
	 * @throws SQLException the SQL exception
	 */
	private void applyParametersToViewsCondition(XSOData odataModel) throws SQLException {
		for (XSODataEntity entity : odataModel.getService().getEntities()) {
			if (entity.getParameterEntitySet() != null) {
				String catalogObjectName = getCorrectCatalogObjectName(entity);

				if (!checkIfEntityIsOfViewType(catalogObjectName)) {
					throw new XSOData2TransformerException(String.format(
							"Parameters are not allowed for entity %s as it is not a calculation or analytical view.",
							entity.getRepositoryObject().getCatalogObjectName()));
				}
			}
		}
	}

	/**
	 * If the parametersresultsprop is omitted, the navigation property from the
	 * parameter entity set to the entity is called "Results". The default
	 * parameterentitysetname is the entitysetname of the entity concatenated with
	 * the suffix "Parameters". Here is a working example:
	 * 
	 * <pre>
	 * "sap.test.odata.db.views::COUNT_VIEW" as "Count"
	 * 		keys generate local "ID"
	 * 		parameters via entity "CountParameters" results property "Results";
	 * </pre>
	 *
	 * @param odataModel the odata model
	 */
	private void applyOmittedParamResultCondition(XSOData odataModel) {
		for (XSODataEntity entity : odataModel.getService().getEntities()) {
			if (entity.getParameterType() != null) {
				XSODataParameter defaultParam = new XSODataParameter();
				if (entity.getParameterEntitySet() != null
						&& entity.getParameterEntitySet().getParameterEntitySetName() == null) {
					entity.setParameterEntitySet(
							defaultParam.setParameterEntitySetName(entity.getAlias() + "Parameters"));
				}
				if (entity.getParameterEntitySet() != null
						&& entity.getParameterEntitySet().getParameterResultsProperty() == null) {
					entity.setParameterEntitySet(defaultParam.setParameterResultsProperty("Results"));
				}
			}
		}
	}

	/**
	 * Check if entity is of synonym type.
	 *
	 * @param artifactName the artifact name
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	private boolean checkIfEntityIsOfSynonymType(String artifactName) throws SQLException {
		return checkIfEntityIsFromAGivenDBType(artifactName, METADATA_SYNONYM_TYPES);
	}

	/**
	 * Check if entity is of view type.
	 *
	 * @param artifactName the artifact name
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	private boolean checkIfEntityIsOfViewType(String artifactName) throws SQLException {
		return checkIfEntityIsFromAGivenDBType(artifactName, METADATA_VIEW_TYPES);
	}

	/**
	 * Check if entity is from A given DB type.
	 *
	 * @param artifactName the artifact name
	 * @param dbTypes      the db types
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	private boolean checkIfEntityIsFromAGivenDBType(String artifactName, List<String> dbTypes) throws SQLException {
		List<DBArtifactModel> artifacts = getDBArtifactsByName(artifactName);
		Optional<DBArtifactModel> filteredArtifact = artifacts.stream().parallel()
				.filter(artifact -> dbTypes.contains(artifact.getType())).findAny();

		DBArtifactModel dbArtifact = filteredArtifact.isPresent() ? filteredArtifact.get()
				: filterSynonymObjects(artifacts, dbTypes, artifactName);

		return null != dbArtifact;
	}

	/**
	 * Check if entity exist.
	 *
	 * @param artifactName the artifact name
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	private boolean checkIfEntityExist(String artifactName) throws SQLException {
		return checkIfEntityIsFromAGivenDBType(artifactName, METADATA_ENTITY_TYPES);
	}

	/**
	 * Gets the correct catalog object name.
	 *
	 * @param entity the entity
	 * @return the correct catalog object name
	 * @throws SQLException the SQL exception
	 */
	private String getCorrectCatalogObjectName(XSODataEntity entity) throws SQLException {
		PersistenceTableModel targetObjectMetadata = new PersistenceTableModel();
		String catalogObjectName;

		if (checkIfEntityIsOfSynonymType(entity.getRepositoryObject().getCatalogObjectName())) {
			targetObjectMetadata = getSynonymTargetObjectMetadata(getSystemDatasource(),
					entity.getRepositoryObject().getCatalogObjectName(),
					entity.getRepositoryObject().getCatalogObjectSchema());
		}

		if (targetObjectMetadata.getTableName() == null) {
			catalogObjectName = entity.getRepositoryObject().getCatalogObjectName();
		} else {
			catalogObjectName = targetObjectMetadata.getTableName();
		}

		return catalogObjectName;
	}

	/**
	 * Filter synonym objects.
	 *
	 * @param artifacts    the artifacts
	 * @param dbTypes      the db types
	 * @param artifactName the artifact name
	 * @return the DB artifact model
	 * @throws SQLException the SQL exception
	 */
	private DBArtifactModel filterSynonymObjects(List<DBArtifactModel> artifacts, List<String> dbTypes,
			String artifactName) throws SQLException {
		Optional<DBArtifactModel> synonym = artifacts.stream().parallel()
				.filter(artifact -> ISqlKeywords.METADATA_SYNONYM.equalsIgnoreCase(artifact.getType())).findAny();

		String targetSchema = synonym.isPresent() ? synonym.get().getSchema() : PUBLIC_SCHEMA;
		return getTargetObjectOfSynonymIfAny(targetSchema, artifactName, dbTypes);
	}

	/**
	 * Gets the target object of synonym if any.
	 *
	 * @param schemaName   the schema name
	 * @param artifactName the artifact name
	 * @param dbTypes      the db types
	 * @return the target object of synonym if any
	 * @throws SQLException the SQL exception
	 */
	private DBArtifactModel getTargetObjectOfSynonymIfAny(String schemaName, String artifactName, List<String> dbTypes)
			throws SQLException {
		PersistenceTableModel targetObjectMetadata = getSynonymTargetObjectMetadata(getDefaultDatasource(), artifactName,
				schemaName);

		String type = targetObjectMetadata.getTableType();
		if (type != null && dbTypes.contains(type)) {
			String name = targetObjectMetadata.getTableName();
			String schema = targetObjectMetadata.getSchemaName();
			return new DBArtifactModel(name, type, schema);
		}

		return null;
	}

	/**
	 * Gets the DB artifacts by name.
	 *
	 * @param artifactName the artifact name
	 * @return the DB artifacts by name
	 * @throws SQLException the SQL exception
	 */
	public List<DBArtifactModel> getDBArtifactsByName(String artifactName) throws SQLException {
		try (Connection connection = getSystemDatasource().getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet rs = databaseMetaData.getTables(connection.getCatalog(), Configuration.get("HANA_USERNAME"),
					artifactName, null);
			List<DBArtifactModel> artifacts = new ArrayList<>();
			while (rs.next()) {
				artifacts.add(new DBArtifactModel(rs.getString("TABLE_NAME"), rs.getString("TABLE_TYPE"),
						rs.getString("TABLE_SCHEM")));
			}
			return artifacts;
		}
	}
}
