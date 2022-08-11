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
package com.codbex.kronos.hdb.ds.artefacts;

import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizationArtefactType;

/**
 * The Class HDBTableSynchronizationArtefactType.
 */
public class HDBTableSynchronizationArtefactType extends AbstractSynchronizationArtefactType {

  /**
   * Gets the artefact state message.
   *
   * @param state the state
   * @return the artefact state message
   */
  @Override
  protected String getArtefactStateMessage(ArtefactState state) {
    switch (state) {
      case FAILED_CREATE:
        return "Processing for create hdbtable has failed";
      case FAILED_CREATE_UPDATE:
        return "Processing for create or update hdbtable has failed";
      case FAILED_UPDATE:
        return "Processing for update hdbtable has failed";
      case SUCCESSFUL_CREATE:
        return "Processing for create hdbtable was successful";
      case SUCCESSFUL_CREATE_UPDATE:
        return "Processing for create or update hdbtable was successful";
      case SUCCESSFUL_UPDATE:
        return "Processing for update hdbtable was successful";
      default:
        return state.getValue();
    }
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  @Override
  public String getId() {
    return "HDBTable";
  }
}
