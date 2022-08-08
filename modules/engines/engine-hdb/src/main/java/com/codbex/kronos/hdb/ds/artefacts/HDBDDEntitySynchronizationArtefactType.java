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
 * The Class HDBDDEntitySynchronizationArtefactType.
 */
public class HDBDDEntitySynchronizationArtefactType extends AbstractSynchronizationArtefactType {

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
        return "Processing for create hdbdd/entity has failed";
      case FAILED_CREATE_UPDATE:
        return "Processing for create or update hdbdd/entity has failed";
      case FAILED_UPDATE:
        return "Processing for update hdbdd/entity has failed";
      case SUCCESSFUL_CREATE:
        return "Processing for create hdbdd/entity was successful";
      case SUCCESSFUL_CREATE_UPDATE:
        return "Processing Create or update hdbdd/entity was successful";
      case SUCCESSFUL_UPDATE:
        return "Processing for update hdbdd/entity was successful";
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
    return "HDBDD Entity";
  }
}
