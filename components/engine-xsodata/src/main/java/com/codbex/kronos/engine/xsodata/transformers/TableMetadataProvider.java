/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.xsodata.transformers;

import static com.codbex.kronos.utils.CommonsDBUtils.getSynonymTargetObjectMetadata;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.components.base.spring.BeanProvider;
import org.eclipse.dirigible.components.data.sources.manager.DataSourcesManager;
import org.eclipse.dirigible.components.data.structures.domain.Table;
import org.eclipse.dirigible.components.odata.api.ODataEntity;
import org.eclipse.dirigible.components.odata.transformers.ODataDatabaseMetadataUtil;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TableMetadataProvider.
 */
public class TableMetadataProvider implements org.eclipse.dirigible.components.odata.api.TableMetadataProvider {

    /**
     * The data source.
     */
    private DataSource getDataSource() {
        DataSourcesManager dataSourcesManager = BeanProvider.getBean(DataSourcesManager.class);
        return dataSourcesManager.getDefaultDataSource();
    }

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(TableMetadataProvider.class);

    /**
     * The db metadata util.
     */
    private final ODataDatabaseMetadataUtil dbMetadataUtil = new ODataDatabaseMetadataUtil();

    /**
     * The Constant METADATA_ENTITY_TYPES.
     */
    private static final List<String> METADATA_ENTITY_TYPES = List.of(ISqlKeywords.METADATA_TABLE, ISqlKeywords.METADATA_CALC_VIEW,
            ISqlKeywords.METADATA_VIEW, ISqlKeywords.METADATA_BASE_TABLE);

    /**
     * The Constant PUBLIC_SCHEMA.
     */
    private static final String PUBLIC_SCHEMA = "PUBLIC";

    /**
     * Gets the persistence table model.
     *
     * @param oDataEntityDefinition the o data entity definition
     * @return the persistence table model
     * @throws SQLException the SQL exception
     */
    @Override
    public Table getTableMetadata(ODataEntity oDataEntityDefinition) throws SQLException {
        return getPersistenceTableModel(oDataEntityDefinition.getTable(), oDataEntityDefinition.getSchema());
    }

    /**
     * Gets the persistence table model.
     *
     * @param artifactName the artifact name
     * @return the persistence table model
     * @throws SQLException the SQL exception
     */
    public Table getPersistenceTableModel(String artifactName) throws SQLException {
        return this.getPersistenceTableModel(artifactName, null);
    }

    /**
     * Gets the persistence table model.
     *
     * @param artifactName the artifact name
     * @param schema artifact schema
     * @return the persistence table model
     * @throws SQLException the SQL exception
     */
    public Table getPersistenceTableModel(String artifactName, String schema) throws SQLException {
        if (null != schema) {
            Table tableMetadata = dbMetadataUtil.getTableMetadata(artifactName, schema);

            if (null != tableMetadata && METADATA_ENTITY_TYPES.contains(tableMetadata.getKind())) {
                return tableMetadata;
            }
        }

        String currentUserSchema = Configuration.get("HANA_USERNAME");
        Table tableMetadata = dbMetadataUtil.getTableMetadata(artifactName, currentUserSchema);

        if (null != tableMetadata && METADATA_ENTITY_TYPES.contains(tableMetadata.getKind())) {
            return tableMetadata;
        }

        PersistenceTableModel targetObjectMetadata = null;
        if (null == tableMetadata) {
            targetObjectMetadata = getSynonymTargetObjectMetadata(getDataSource(), artifactName, PUBLIC_SCHEMA);
        } else if (ISqlKeywords.METADATA_SYNONYM.equals(tableMetadata.getKind())) {
            targetObjectMetadata = getSynonymTargetObjectMetadata(getDataSource(), artifactName, tableMetadata.getSchema());
        }

        if (null == targetObjectMetadata || targetObjectMetadata.getTableName() == null) {
            logger.error("Cannot find view/table/synonym with name [{}] in schema [{}] and there is no public synonym with that name. "
                    + "Make sure that you are using view/table/synonym which is in your user default schema or is a public synonym.",
                    artifactName, currentUserSchema);
            return null;
        }

        return dbMetadataUtil.getTableMetadata(targetObjectMetadata.getTableName(), targetObjectMetadata.getSchemaName());
    }
}
