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

public class CalculationViewSynchronizationArtefactType extends AbstractSynchronizationArtefactType {

  @Override
  protected String getArtefactStateMessage(ArtefactState artefactState) {
    switch (artefactState) {
      case FAILED_CREATE:
        return "Processing for create calculation view has failed";
      case FAILED_CREATE_UPDATE:
        return "Processing for create or update calculation view has failed";
      case FAILED_UPDATE:
        return "Processing for update calculation view has failed";
      case SUCCESSFUL_CREATE:
        return "Processing for create calculation view was successful";
      case SUCCESSFUL_CREATE_UPDATE:
        return "Processing Create or update calculation view was successful";
      case SUCCESSFUL_UPDATE:
        return "Processing for update calculation view was successful";
      default:
        return artefactState.getValue();
    }
  }

  @Override
  public String getId() {
    return "CalculationView";
  }
}
