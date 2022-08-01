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
package com.codbex.kronos.hdb.ds.synchronizer;

import org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler;
import org.eclipse.dirigible.commons.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.IEnvironmentVariables;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;

/**
 * The Data Structures Classpath Content Handler.
 */
public class DataStructuresClasspathContentHandler extends AbstractClasspathContentHandler {

  private static final Logger logger = LoggerFactory.getLogger(DataStructuresClasspathContentHandler.class);

  private DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler#isValid(java.lang.String)
   */
  @Override
  protected boolean isValid(String path) {

    try {

      boolean hdiOnly = Boolean.parseBoolean(Configuration.get(IEnvironmentVariables.KRONOS_HDI_ONLY, "false"));
      if (!hdiOnly) {
        try {
          if (path.endsWith(HDBDataStructureModel.FILE_EXTENSION_ENTITIES)) {
            dataStructuresSynchronizer.registerPredeliveredEntities(path);
            return true;
          }
        } catch (DataStructuresException e) {
          logger.error("Predelivered hdbdd artifact is not valid");
          logger.error(e.getMessage());
        }

        try {
          if (path.endsWith(HDBDataStructureModel.FILE_EXTENSION_TABLE)) {
            dataStructuresSynchronizer.registerPredeliveredTable(path);
            return true;
          }
        } catch (DataStructuresException e) {
          logger.error("Predelivered hdbtable artifact is not valid or is in the new format");
          logger.error(e.getMessage());
        }

        try {
          if (path.endsWith(HDBDataStructureModel.FILE_EXTENSION_VIEW)) {
            dataStructuresSynchronizer.registerPredeliveredView(path);
            return true;
          }
        } catch (DataStructuresException e) {
          logger.error("Predelivered hdbview artifact is not valid or is in the new format");
          logger.error(e.getMessage());
        }

        //            if (path.endsWith(IDataStructureModel.FILE_EXTENSION_CALCULATION_VIEW)) {
        //                dataStructuresSynchronizer.registerPredeliveredCalculationView(path);
        //                return true;
        //            }
        //            if (path.endsWith(IDataStructureModel.FILE_EXTENSION_HDBCALCULATION_VIEW)) {
        //                dataStructuresSynchronizer.registerPredeliveredHDBCalculationView(path);
        //                return true;
        //            }

        try {
          if (path.endsWith(HDBDataStructureModel.FILE_EXTENSION_HDBPROCEDURE)) {
            dataStructuresSynchronizer.registerPredeliveredHDBProcedure(path);
            return true;
          }
        } catch (DataStructuresException e) {
          logger.error("Predelivered hdbprocedure artifact is not valid");
          logger.error(e.getMessage());
        }

        try {
          if (path.endsWith(HDBDataStructureModel.FILE_EXTENSION_HDBTABLEFUNCTION)) {
            dataStructuresSynchronizer.registerPredeliveredHDBTableFunction(path);
            return true;
          }
        } catch (DataStructuresException e) {
          logger.error("Predelivered hdbtablefunction artifact is not valid");
          logger.error(e.getMessage());
        }

        try {
          if (path.endsWith(HDBDataStructureModel.FILE_EXTENSION_HDBSCHEMA)) {
            dataStructuresSynchronizer.registerPredeliveredHDBSchema(path);
            return true;
          }
        } catch (DataStructuresException e) {
          logger.error("Predelivered hdbschema artifact is not valid");
          logger.error(e.getMessage());
        }

        try {
          if (path.endsWith(HDBDataStructureModel.FILE_EXTENSION_SYNONYM)) {
            dataStructuresSynchronizer.registerPredeliveredSynonym(path);
            return true;
          }
        } catch (DataStructuresException e) {
          logger.error("Predelivered hdbsynonym artifact is not valid");
          logger.error(e.getMessage());
        }

        try {
          if (path.endsWith(HDBDataStructureModel.FILE_EXTENSION_STRUCTURE)) {
            dataStructuresSynchronizer.registerPredeliveredHDBStructure(path);
            return true;
          }
        } catch (DataStructuresException e) {
          logger.error("Predelivered hdbstructure artifact is not valid");
          logger.error(e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.error("Predelivered Artifact is not valid", e);
    }

    return false;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.commons.api.content.AbstractClasspathContentHandler#getLogger()
   */
  @Override
  protected Logger getLogger() {
    return logger;
  }

}
