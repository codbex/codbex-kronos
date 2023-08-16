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
package com.codbex.kronos.engine.hdb.parser;

import static org.eclipse.dirigible.database.sql.ISqlKeywords.SPACE;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.dirigible.components.api.security.UserFacade;
import org.eclipse.dirigible.database.sql.ISqlKeywords;

import com.codbex.kronos.engine.hdb.domain.HDBColumn;
import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableColumn;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintForeignKey;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintPrimaryKey;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintUnique;
import com.codbex.kronos.engine.hdb.domain.HDBTableIndex;
import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.domain.HDBTableTypeColumn;
import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.parser.hdbdd.annotation.metadata.AbstractAnnotationValue;
import com.codbex.kronos.parser.hdbdd.annotation.metadata.AnnotationArray;
import com.codbex.kronos.parser.hdbdd.annotation.metadata.AnnotationObj;
import com.codbex.kronos.parser.hdbdd.exception.CDSRuntimeException;
import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.entity.AssociationSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.entity.EntityElementSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.entity.EntitySymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.BuiltInTypeSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.custom.DataTypeSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.custom.StructuredDataTypeSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.FieldSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.view.JoinSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.view.SelectSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.view.ViewSymbol;

/**
 * The Class HdbddTransformer.
 */
public class HDBDDTransformer {

	/** The Constant UNMANAGED_ASSOCIATION_MARKER. */
	private static final String UNMANAGED_ASSOCIATION_MARKER = "@";

	/** The Constant CATALOG_ANNOTATION. */
	private static final String CATALOG_ANNOTATION = "Catalog";

	/** The Constant CATALOG_OBJ_TABLE_TYPE. */
	private static final String CATALOG_OBJ_TABLE_TYPE = "tableType";

	/** The Constant SEARCH_INDEX_ANNOTATION. */
	private static final String SEARCH_INDEX_ANNOTATION = "SearchIndex";

	/** The Constant FUZZY_ANNOTATION. */
	private static final String FUZZY_ANNOTATION = "fuzzy";

	/** The Constant FUZZY_SEARCH_INDEX_ENABLED. */
	private static final String FUZZY_SEARCH_INDEX_ENABLED = "enabled";

	/** The Constant DUMMY_TABLE. */
	private static final String DUMMY_TABLE = "DUMMY";

	/** The Constant QUOTE. */
	private static final String QUOTE = "\"";

	/** The Constant DOT. */
	private static final String DOT = ".";

	/** The Constant PACKAGE_DELIMITER. */
	private static final String PACKAGE_DELIMITER = "::";

	/** The Constant INDEX. */
	private static final String INDEX = "index";

	/** The Constant UNIQUE. */
	private static final String UNIQUE = "unique";

	/** The Constant NAME. */
	private static final String NAME = "name";

	/** The Constant ORDER. */
	private static final String ORDER = "order";

	/** The Constant ELEMENT_NAMES. */
	private static final String ELEMENT_NAMES = "elementNames";

	/**
	 * Transform entity symbol to table model.
	 *
	 * @param entitySymbol the entity symbol
	 * @param location     the location
	 * @return the data structure HDB table model
	 */
	public HDBTable transformEntitySymbolToTableModel(EntitySymbol entitySymbol, String location) {
		HDBTable tableModel = new HDBTable();
		tableModel.setClassic(true);
		tableModel.setName(entitySymbol.getFullName());
		tableModel.setSchema(entitySymbol.getSchema());

		List<EntityElementSymbol> entityPks = entitySymbol.getElements().stream().filter(EntityElementSymbol::isKey)
				.collect(Collectors.toList());
		HDBTableConstraintPrimaryKey primaryKey = new HDBTableConstraintPrimaryKey();
		primaryKey.setColumns(entityPks.stream().map(EntityElementSymbol::getName).toArray(String[]::new));
		primaryKey.setName("PK_" + tableModel.getName());
		tableModel.getConstraints().setPrimaryKey(primaryKey);

		List<HDBTableColumn> tableColumns = new ArrayList<>();
		entitySymbol.getElements().forEach(currentElement -> {
			if (currentElement.getType() instanceof StructuredDataTypeSymbol) {
				List<EntityElementSymbol> subElements = getStructuredTypeSubElements(currentElement);
				subElements.forEach(subE -> {
					tableColumns.add(transformFieldSymbolToColumnModel(subE, true));
				});
			} else {
				tableColumns.add(transformFieldSymbolToColumnModel(currentElement, true));
			}
		});

		entitySymbol.getAssociations().forEach(associationSymbol -> {
			List<HDBTableColumn> associationColumns = transformAssociationToColumnModels(associationSymbol);
			HDBTableConstraintForeignKey foreignKeyModel = new HDBTableConstraintForeignKey();
			String[] referencedColumns = associationColumns.stream().map(HDBTableColumn::getName)
					.toArray(String[]::new);
			String foreignKeyName = tableModel.getName() + "." + associationSymbol.getName();
			if (associationSymbol.isManaged()) {
				associationColumns.forEach(ac -> {
					if (ac.getAlias() == null) {
						ac.setName(associationSymbol.getName() + "." + ac.getName());
					} else {
						ac.setName(ac.getAlias());
					}
				});
			} else {
				String associationSymbolName = associationSymbol.getName();
				associationColumns.forEach(ac -> ac.setName(associationSymbolName
						.substring(associationSymbolName.indexOf(UNMANAGED_ASSOCIATION_MARKER) + 1)));
				foreignKeyName = foreignKeyName.replace(UNMANAGED_ASSOCIATION_MARKER, "");
			}

			String[] foreignKeyColumns = associationColumns.stream().map(HDBTableColumn::getName)
					.toArray(String[]::new);

			foreignKeyModel.setName(foreignKeyName);
			foreignKeyModel.setReferencedTable(associationSymbol.getTarget().getFullName());
			foreignKeyModel.setReferencedSchema(associationSymbol.getTarget().getSchema());
			foreignKeyModel.setReferencedColumns(referencedColumns);
			foreignKeyModel.setColumns(foreignKeyColumns);

			if (associationSymbol.isManaged()) {
				tableColumns.addAll(associationColumns);
			}

			if (!associationSymbol.getForeignKeys().isEmpty()) {
				tableModel.getConstraints().getForeignKeys().add(foreignKeyModel);
			}
		});

		tableModel.setColumns(tableColumns);
		tableModel.setLocation(location);
		if (entitySymbol.getAnnotation(CATALOG_ANNOTATION) != null) {
			String tableType = entitySymbol.getAnnotation(CATALOG_ANNOTATION).getKeyValuePairs()
					.get(CATALOG_OBJ_TABLE_TYPE).getValue();
			tableModel.setTableType(tableType);

			if (entitySymbol.getAnnotation(CATALOG_ANNOTATION).getKeyValuePairs().get(INDEX) != null) {
				List<HDBTableIndex> indexes = new ArrayList<>();
				List<HDBTableConstraintUnique> uniqueIndexes = new ArrayList<>();
				AnnotationArray catalogIndexAnnotationArray = (AnnotationArray) entitySymbol
						.getAnnotation(CATALOG_ANNOTATION).getKeyValuePairs().get(INDEX);

				for (AbstractAnnotationValue currentAnnotationValue : catalogIndexAnnotationArray.getValues()) {
					AnnotationObj annotationObject = (AnnotationObj) currentAnnotationValue;
					boolean isUnique = Boolean.parseBoolean(getCatalogAnnotationValue(annotationObject, UNIQUE));
					String name = getCatalogAnnotationValue(annotationObject, NAME);
					String order = getCatalogAnnotationValue(annotationObject, ORDER);
					Set<String> indexColumnSet = new HashSet<>();

					((AnnotationArray) annotationObject.getValue(ELEMENT_NAMES)).getValues()
							.forEach(currentElement -> indexColumnSet.add(currentElement.getValue()));

					if (!isUnique) {
						indexes.add(new HDBTableIndex(name, "index", false, order, indexColumnSet.toArray(String[]::new),
								tableModel));
					} else {
						uniqueIndexes.add(new HDBTableConstraintUnique(name, null,
								indexColumnSet.toArray(String[]::new), tableModel.getConstraints(), null, order));
					}
				}
				tableModel.setIndexes(indexes);
				tableModel.getConstraints().setUniqueIndexes(uniqueIndexes);
			}
		}

		handlePossibleSearchIndexAnnotations(entitySymbol, tableModel);

		return tableModel;
	}

	/**
	 * Handle possible search index annotations.
	 *
	 * @param entitySymbol the entity symbol
	 * @param tableModel   the table model
	 */
	private void handlePossibleSearchIndexAnnotations(EntitySymbol entitySymbol, HDBTable tableModel) {
		for (int i = 0; i < entitySymbol.getElements().size(); i++) {
			EntityElementSymbol currentElement = entitySymbol.getElements().get(i);

			if (currentElement.getAnnotation(SEARCH_INDEX_ANNOTATION) != null) {

				boolean hasFuzzySearchIndex = false;
				Map<String, AbstractAnnotationValue> searchIndexAnnotationValueMap = currentElement
						.getAnnotation(SEARCH_INDEX_ANNOTATION).getKeyValuePairs();
				AnnotationObj fuzzyIndexAnnotationObject = (AnnotationObj) searchIndexAnnotationValueMap
						.get(FUZZY_ANNOTATION);
				AbstractAnnotationValue fuzzyIndexAnnotationValue = searchIndexAnnotationValueMap
						.get(FUZZY_SEARCH_INDEX_ENABLED);
				AbstractAnnotationValue fuzzyIndexAnnotationObjectValue = fuzzyIndexAnnotationObject != null
						? fuzzyIndexAnnotationObject.getKeyValuePairs().get(FUZZY_SEARCH_INDEX_ENABLED)
						: null;

				if (fuzzyIndexAnnotationObjectValue != null) {
					hasFuzzySearchIndex = Boolean.parseBoolean(fuzzyIndexAnnotationObjectValue.getValue());
				} else if (fuzzyIndexAnnotationValue != null) {
					hasFuzzySearchIndex = Boolean.parseBoolean(fuzzyIndexAnnotationValue.getValue());
				}
				tableModel.getColumns().get(i).setFuzzySearchIndex(hasFuzzySearchIndex);
			}
		}
	}

	/**
	 * Transform view symbol to hdb view model.
	 *
	 * @param viewSymbol the view symbol
	 * @param location   the location
	 * @return the data structure HDB view model
	 */
	public HDBView transformViewSymbolToHdbViewModel(ViewSymbol viewSymbol, String location) {
		HDBView viewModel = new HDBView();

		StringBuilder viewStatementSql = new StringBuilder();
		List<String> aliasesForReplacement = new ArrayList<>();

		viewStatementSql.append(ISqlKeywords.KEYWORD_VIEW).append(SPACE).append(QUOTE).append(viewSymbol.getSchema())
				.append(QUOTE).append(DOT).append(QUOTE).append(viewSymbol.getFullName()).append(QUOTE).append(SPACE)
				.append(ISqlKeywords.KEYWORD_AS).append(SPACE);

		String selectStatementSql = traverseSelectStatements(viewSymbol, aliasesForReplacement, viewModel);
		viewStatementSql.append(selectStatementSql);

		String finalViewSql = viewStatementSql.toString();

		for (String alias : aliasesForReplacement) {
			finalViewSql = replaceWithQuotes(finalViewSql, alias, alias);
		}

		viewModel.setClassic(false);
		viewModel.setName(viewSymbol.getFullName());
		viewModel.setSchema(viewSymbol.getSchema());
		viewModel.setQuery(finalViewSql);
		viewModel.setLocation(location);
		return viewModel;
	}

	/**
	 * Traverse select statements.
	 *
	 * @param viewSymbol            the view symbol
	 * @param aliasesForReplacement the aliases for replacement
	 * @param viewModel             the view model
	 * @return the string
	 */
	public String traverseSelectStatements(ViewSymbol viewSymbol, List<String> aliasesForReplacement,
			HDBView viewModel) {
		List<String> dependsOnTableList = new ArrayList<>();
		StringBuilder selectSql = new StringBuilder();

		for (Symbol symbol : viewSymbol.getSelectStatements()) {
			SelectSymbol selectSymbol = (SelectSymbol) symbol;
			String dependsOnTable = selectSymbol.getDependsOnTable();
			String dependingTableAlias = selectSymbol.getDependingTableAlias();
			String selectColumns = selectSymbol.getColumnsSql();
			String where = selectSymbol.getWhereSql();
			boolean unionBol = selectSymbol.getUnion();
			boolean distinctBol = selectSymbol.getDistinct();

			// Check if the dependant table has :: to know whether short or full name is
			// used in the hdbdd view definition. In case it is not we should build the full
			// name
			if (!dependsOnTable.contains(PACKAGE_DELIMITER)) {
				dependsOnTable = getFullTableName(viewSymbol, dependsOnTable);
				selectColumns = replaceWithQuotes(selectColumns, dependsOnTable, dependsOnTable);
			}
			dependsOnTableList.add(dependsOnTable);

			if (unionBol) {
				selectSql.append(ISqlKeywords.KEYWORD_UNION).append(SPACE);
			}

			selectSql.append(ISqlKeywords.KEYWORD_SELECT).append(SPACE);

			if (distinctBol) {
				selectSql.append(ISqlKeywords.KEYWORD_DISTINCT).append(SPACE);
			}

			selectSql.append(selectColumns).append(SPACE).append(ISqlKeywords.KEYWORD_FROM).append(SPACE).append(QUOTE)
					.append(dependsOnTable).append(QUOTE).append(SPACE);

			if (dependingTableAlias != null) {
				selectSql.append(ISqlKeywords.KEYWORD_AS).append(SPACE).append(QUOTE).append(dependingTableAlias)
						.append(QUOTE).append(SPACE);
				aliasesForReplacement.add(dependingTableAlias);
			}

			String joinStatements = traverseJoinStatements(selectSymbol, viewSymbol, dependsOnTable,
					aliasesForReplacement);

			selectSql.append(joinStatements).append(SPACE);

			if (where != null) {
				selectSql.append(ISqlKeywords.KEYWORD_WHERE).append(SPACE).append(where).append(SPACE);
			}
		}
		viewModel.setDependsOnTable(dependsOnTableList);

		return selectSql.toString();
	}

	/**
	 * Traverse join statements.
	 *
	 * @param selectSymbol          the select symbol
	 * @param viewSymbol            the view symbol
	 * @param dependsOnTable        the depends on table
	 * @param aliasesForReplacement the aliases for replacement
	 * @return the string
	 */
	public String traverseJoinStatements(SelectSymbol selectSymbol, ViewSymbol viewSymbol, String dependsOnTable,
			List<String> aliasesForReplacement) {
		StringBuilder joinStatements = new StringBuilder();

		for (Symbol symbol : selectSymbol.getJoinStatements()) {
			JoinSymbol joinSymbol = (JoinSymbol) symbol;
			String joinType = joinSymbol.getJoinType();
			String joinArtifactName = joinSymbol.getJoinArtifactName();
			String joinTableAlias = joinSymbol.getJoinTableAlias();
			String joinFieldsSql = joinSymbol.getJoinFields();

			// Check if the join artifact name contains :: to determine if full artifact
			// name is used and build the full name if not
			if (!joinArtifactName.contains(PACKAGE_DELIMITER)) {
				joinArtifactName = getFullTableName(viewSymbol, joinArtifactName);
			}

			// Replace the select from dependant table if anywhere in the join with its full
			// name
			String dependsOnTableShortName = shortTableNameExtractorFromViewSymbol(dependsOnTable, viewSymbol);
			joinFieldsSql = replaceWithQuotes(joinFieldsSql, dependsOnTableShortName, dependsOnTable);

			joinStatements.append(joinType).append(SPACE).append(QUOTE).append(joinArtifactName).append(QUOTE)
					.append(SPACE);

			if (joinTableAlias != null) {
				joinStatements.append(ISqlKeywords.KEYWORD_AS).append(SPACE).append(QUOTE).append(joinTableAlias)
						.append(QUOTE).append(SPACE);
				aliasesForReplacement.add(joinTableAlias);
			}

			joinStatements.append(joinFieldsSql).append(SPACE);
		}

		return joinStatements.toString();
	}

	/**
	 * Short table name extractor from view symbol.
	 *
	 * @param fullTableName the full table name
	 * @param viewSymbol    the view symbol
	 * @return the string
	 */
	private String shortTableNameExtractorFromViewSymbol(String fullTableName, ViewSymbol viewSymbol) {
		return fullTableName.replace(viewSymbol.getPackageId() + PACKAGE_DELIMITER + viewSymbol.getContext() + DOT, "");
	}

	/**
	 * Replace with quotes.
	 *
	 * @param inContent    the in content
	 * @param toBeReplaced the to be replaced
	 * @param replacement  the replacement
	 * @return the string
	 */
	private String replaceWithQuotes(String inContent, String toBeReplaced, String replacement) {
		return inContent.replaceAll(toBeReplaced + "[.]|\"" + toBeReplaced + "\"[.]", "\"" + replacement + "\".");
	}

	/**
	 * Transform structured data type to hdb table type.
	 *
	 * @param structuredDataTypeSymbol the structured data type symbol
	 * @return the data structure HDB table type model
	 */
	public HDBTableType transformStructuredDataTypeToHdbTableType(StructuredDataTypeSymbol structuredDataTypeSymbol) {
		HDBTableType hdbTableTypeModel = new HDBTableType();
		List<HDBTableTypeColumn> tableColumns = new ArrayList<>();
		structuredDataTypeSymbol.getFields().forEach(field -> {
			if (field.getType() instanceof StructuredDataTypeSymbol) {
				List<EntityElementSymbol> subElements = getStructuredTypeSubElements(field);
				subElements.forEach(subE -> {
					tableColumns.add(transformFieldSymbolToColumnTypeModel(subE, true));
				});
			} else {
				tableColumns.add(transformFieldSymbolToColumnTypeModel(field, true));
			}
		});

		hdbTableTypeModel.setColumns(tableColumns);
		hdbTableTypeModel.setClassic(true);
		hdbTableTypeModel.setName(structuredDataTypeSymbol.getFullName());
		hdbTableTypeModel.setSchema(structuredDataTypeSymbol.getSchema());

		return hdbTableTypeModel;
	}

	/**
	 * Transform field symbol to column model.
	 *
	 * @param fieldSymbol the field symbol
	 * @param bAssignPK   the b assign PK
	 * @return the data structure HDB table column model
	 */
	private HDBTableColumn transformFieldSymbolToColumnModel(FieldSymbol fieldSymbol, boolean bAssignPK) {
		HDBTableColumn columnModel = new HDBTableColumn();

		setTransformFieldSymbolToColumnModel(fieldSymbol, bAssignPK, columnModel);

		return columnModel;
	}
	
	/**
	 * Transform field symbol to column model.
	 *
	 * @param fieldSymbol the field symbol
	 * @param bAssignPK   the b assign PK
	 * @return the data structure HDB table column model
	 */
	private HDBTableTypeColumn transformFieldSymbolToColumnTypeModel(FieldSymbol fieldSymbol, boolean bAssignPK) {
		HDBTableTypeColumn columnModel = new HDBTableTypeColumn();

		setTransformFieldSymbolToColumnModel(fieldSymbol, bAssignPK, columnModel);

		return columnModel;
	}

	/**
	 * Sets the transform field symbol to column model.
	 *
	 * @param fieldSymbol the field symbol
	 * @param bAssignPK   the b assign PK
	 * @param columnModel the column model
	 */
	private void setTransformFieldSymbolToColumnModel(FieldSymbol fieldSymbol, boolean bAssignPK,
			HDBColumn columnModel) {
		columnModel.setAlias(fieldSymbol.getAlias());
		columnModel.setName(fieldSymbol.getName());

		columnModel.setNullable(true);

		if (fieldSymbol instanceof EntityElementSymbol) {
			EntityElementSymbol elementSymbol = (EntityElementSymbol) fieldSymbol;
			if (bAssignPK) {
				columnModel.setPrimaryKey(elementSymbol.isKey());
			}

			columnModel.setCalculatedColumn(elementSymbol.isCalculatedColumn());
			columnModel.setStatement(elementSymbol.getStatement());
			columnModel.setNullable(!elementSymbol.isNotNull());
			columnModel.setDefaultValue(elementSymbol.getDefaultValue());
			columnModel.setDefaultValueDateTimeFunction(elementSymbol.isDefaultValueDateTimeFunction());
		}

		if (fieldSymbol.getType() instanceof BuiltInTypeSymbol) {
			BuiltInTypeSymbol builtInTypeSymbol = (BuiltInTypeSymbol) fieldSymbol.getType();
			if (builtInTypeSymbol.isHanaType()) {
				setHanaType(columnModel, builtInTypeSymbol);
			} else {
				setSqlType(columnModel, builtInTypeSymbol);
			}

		} else if (fieldSymbol.getType() instanceof DataTypeSymbol) {
			DataTypeSymbol dataType = (DataTypeSymbol) fieldSymbol.getType();
			if (!(dataType.getType() instanceof StructuredDataTypeSymbol)) {
				BuiltInTypeSymbol builtInType = (BuiltInTypeSymbol) dataType.getType();
				setSqlType(columnModel, builtInType);
			} else {
				StructuredDataTypeSymbol structuredDataTypeSymbol = (StructuredDataTypeSymbol) dataType.getType();
				transformStructuredDataTypeToHdbTableType(structuredDataTypeSymbol);
			}
		} else {
			throw new IllegalArgumentException("Unknown table type: " + fieldSymbol.getType());
		}
	}

	/**
	 * Transform association to column models.
	 *
	 * @param associationSymbol the association symbol
	 * @return the list
	 */
	private List<HDBTableColumn> transformAssociationToColumnModels(AssociationSymbol associationSymbol) {
		List<HDBTableColumn> tableColumns = new ArrayList<>();
		associationSymbol.getForeignKeys().forEach(fk -> {
			if (fk.getType() instanceof StructuredDataTypeSymbol) {
				List<EntityElementSymbol> subElements = getStructuredTypeSubElements(fk);
				subElements.forEach(subE -> tableColumns.add(getAssociationForeignKeyColumn(associationSymbol, subE)));
			} else {
				tableColumns.add(getAssociationForeignKeyColumn(associationSymbol, fk));
			}
		});

		return tableColumns;
	}

	/**
	 * Sets the sql type.
	 *
	 * @param columnModel       the column model
	 * @param builtInTypeSymbol the built in type symbol
	 */
	private void setSqlType(HDBColumn columnModel, BuiltInTypeSymbol builtInTypeSymbol) {
		String typeName = builtInTypeSymbol.getName();
		CdsTypeEnum cdsTypeEnum = CdsTypeEnum.valueOf(typeName);

		if (cdsTypeEnum.equals(CdsTypeEnum.String)) {
			columnModel.setLength(builtInTypeSymbol.getArgsValues().get(0).toString());
		} else if (cdsTypeEnum.equals(CdsTypeEnum.Decimal)) {
			columnModel.setPrecision(builtInTypeSymbol.getArgsValues().get(0).toString());
			columnModel.setScale(builtInTypeSymbol.getArgsValues().get(1).toString());
		}

		columnModel.setType(cdsTypeEnum.getSqlType());
	}

	/**
	 * Sets the hana type.
	 *
	 * @param columnModel       the column model
	 * @param builtInTypeSymbol the built in type symbol
	 */
	private void setHanaType(HDBColumn columnModel, BuiltInTypeSymbol builtInTypeSymbol) {
		String typeName = builtInTypeSymbol.getName();
		CdsHanaTypeEnum cdsHanaTypeEnum = CdsHanaTypeEnum.getSQLName(typeName);

		if (cdsHanaTypeEnum.equals(CdsHanaTypeEnum.VARCHAR)) {
			columnModel.setLength(builtInTypeSymbol.getArgsValues().get(0).toString());
		}

		columnModel.setType(cdsHanaTypeEnum.toString());
	}

	/**
	 * Gets the structured type sub elements.
	 *
	 * @param entityElementSymbol the entity element symbol
	 * @return the structured type sub elements
	 */
	private List<EntityElementSymbol> getStructuredTypeSubElements(FieldSymbol entityElementSymbol) {
		StructuredDataTypeSymbol structuredDataType = (StructuredDataTypeSymbol) entityElementSymbol.getType();
		String elementName = entityElementSymbol.getName();
		List<EntityElementSymbol> subElements = new ArrayList<>();
		structuredDataType.getFields().forEach(field -> {
			EntityElementSymbol subElement = new EntityElementSymbol(elementName + "." + field.getName(),
					entityElementSymbol.getScope());
			if (field.getType() instanceof BuiltInTypeSymbol) {
				subElement.setType(field.getType());
				subElements.add(subElement);
			} else if (field.getType() instanceof StructuredDataTypeSymbol) {
				subElement.setType(field.getType());
				subElements.addAll(getStructuredTypeSubElements(subElement));
			}
		});

		return subElements;
	}

	/**
	 * Gets the association foreign key column.
	 *
	 * @param associationSymbol the association symbol
	 * @param foreignKey        the foreign key
	 * @return the association foreign key column
	 */
	private HDBTableColumn getAssociationForeignKeyColumn(AssociationSymbol associationSymbol,
			EntityElementSymbol foreignKey) {
		HDBTableColumn columnModel = transformFieldSymbolToColumnModel(foreignKey, false);
		columnModel.setPrimaryKey(associationSymbol.isKey());
		columnModel.setNullable(!associationSymbol.isNotNull());

		return columnModel;
	}

	/**
	 * Gets the full table name.
	 *
	 * @param dependingView the depending view
	 * @param tableName     the table name
	 * @return the full table name
	 */
	private String getFullTableName(ViewSymbol dependingView, String tableName) {
		// Check if the dependant table name is DUMMY. This is a reserved table name for
		// hana dummy tables. We make sure to make it in uppercase
		if (tableName.equalsIgnoreCase(DUMMY_TABLE)) {
			return tableName.toUpperCase();
		} else {
			Symbol resolvedDependsOnTable = dependingView.getEnclosingScope().resolve(tableName);
			if (resolvedDependsOnTable == null) {
				throw new CDSRuntimeException("Could not resolve referenced entity: " + tableName);
			}
			return resolvedDependsOnTable.getFullName();
		}
	}

	/**
	 * Gets the catalog annotation value.
	 *
	 * @param annotationObject the annotation object
	 * @param value            the value
	 * @return the catalog annotation value
	 */
	private String getCatalogAnnotationValue(AnnotationObj annotationObject, String value) {
		return annotationObject.getValue(value) != null ? annotationObject.getValue(value).getValue() : null;
	}
}