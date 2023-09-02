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
package com.codbex.kronos.engine.hdi.processors;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;
import org.eclipse.dirigible.components.api.platform.RegistryFacade;
import org.eclipse.dirigible.repository.api.RepositoryPath;

/**
 * The Class WriteContainerContentProcessor.
 */
public class WriteContainerContentProcessor extends HDIAbstractProcessor {

  /**
   * Enumerate folders.
   *
   * @param files the files
   * @return the string[]
   */
  protected static String[] enumerateFolders(String[] files) {
    Set<String> folders = new TreeSet<>();
    for (String file : files) {
      RepositoryPath path = new RepositoryPath(file);
      String current = "";
      String[] segments = path.getSegments();
      for (int i = 0; i < segments.length - 1; i++) {
        current += segments[i] + "/";
        if (!folders.contains(current)) {
          folders.add(current);
        }
      }
    }
    return folders.toArray(new String[]{});
  }

  /**
   * Execute.
   *
   * @param connection the connection
   * @param container the container
   * @param files the files
   * @param configuration the configuration
   * @throws SQLException the SQL exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ScriptingException the scripting exception
   */
  public void execute(Connection connection, String container, String[] files, String configuration)
      throws SQLException, IOException {

    String[] folders = enumerateFolders(files);

    executeUpdate(connection, "CREATE LOCAL TEMPORARY COLUMN TABLE #PATHS LIKE _SYS_DI.TT_FILESFOLDERS_CONTENT;");

    String content = RegistryFacade.getText(configuration);

    executeUpdate(connection, "INSERT INTO #PATHS (PATH, CONTENT) VALUES ('.hdiconfig', ?)", content);
    for (String folder : folders) {
      executeUpdate(connection, "INSERT INTO #PATHS (PATH, CONTENT) VALUES (?, NULL);", folder);
    }
    for (String file : files) {
      content = RegistryFacade.getText(file);
      executeUpdate(connection, "INSERT INTO #PATHS (PATH, CONTENT) VALUES (?, ?);", file.substring(1), content);
    }
    executeQuery(connection, "CALL " + container + "#DI.WRITE(#PATHS, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);");
    executeUpdate(connection, "DROP TABLE #PATHS;");
  }

}
