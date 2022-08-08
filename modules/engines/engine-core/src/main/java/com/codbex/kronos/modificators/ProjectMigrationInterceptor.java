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
package com.codbex.kronos.modificators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;
import org.eclipse.dirigible.api.v3.platform.WorkspaceFacade;
import org.eclipse.dirigible.core.workspace.api.IFile;
import org.eclipse.dirigible.core.workspace.api.IFolder;
import org.eclipse.dirigible.core.workspace.api.IProject;
import org.eclipse.dirigible.core.workspace.api.IWorkspace;

/**
 * The Class ProjectMigrationInterceptor.
 */
public class ProjectMigrationInterceptor {

  /** The project files modificator. */
  private final ProjectFilesModificator projectFilesModificator = new ProjectFilesModificator();

  /**
   * Modify.
   *
   * @param fileContent the file content
   * @return the byte[]
   * @throws TransformerException the transformer exception
   */
  @ToolingHook
  public byte[] modify(byte[] fileContent) throws TransformerException {
    return new CalculationViewTransformation().removeTypeArtifact(fileContent);
  }

  /**
   * Intercept project.
   *
   * @param workspaceName the workspace name
   * @param projectName the project name
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @ToolingHook
  public void interceptProject(String workspaceName, String projectName) throws IOException {
    IWorkspace workspace = WorkspaceFacade.getWorkspace(workspaceName);
    IProject project = workspace.getProject(projectName);

    List<IFile> allProjectFiles = collectAllProjectFiles(project);

    projectFilesModificator.modifyProjectFiles(allProjectFiles);
  }

  /**
   * Collect all project files.
   *
   * @param project the project
   * @return the list
   */
  private static List<IFile> collectAllProjectFiles(IProject project) {
    List<IFile> allProjectFiles = new ArrayList<>();

    List<IFolder> rootFolders = project.getFolders();
    List<IFile> rootFiles = project.getFiles();

    if (isNotEmptyOrNull(rootFiles)) {
      allProjectFiles.addAll(rootFiles);
    }

    for (IFolder nestedFolder : rootFolders) {
      traverseFiles(nestedFolder, allProjectFiles);
    }

    return allProjectFiles;
  }


  /**
   * Traverse files.
   *
   * @param folder the folder
   * @param allProjectFiles the all project files
   */
  private static void traverseFiles(IFolder folder, List<IFile> allProjectFiles) {
    allProjectFiles.addAll(folder.getFiles());

    List<IFolder> nestedFolders = folder.getFolders();
    if (isNotEmptyOrNull(nestedFolders)) {
      for (IFolder nestedFolder : nestedFolders) {
        traverseFiles(nestedFolder, allProjectFiles);
      }
    }
  }

  /**
   * Checks if is not empty or null.
   *
   * @param <T> the generic type
   * @param list the list
   * @return true, if is not empty or null
   */
  private static <T> boolean isNotEmptyOrNull(List<T> list) {
    return list != null && !list.isEmpty();
  }
}
