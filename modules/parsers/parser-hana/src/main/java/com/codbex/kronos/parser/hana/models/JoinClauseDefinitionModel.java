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
package com.codbex.kronos.parser.hana.models;

/**
 * The Class JoinClauseDefinitionModel.
 */
public class JoinClauseDefinitionModel extends TableReferenceModel {

  /** The on part. */
  private String onPart;
  
  /** The raw content. */
  private String rawContent;

  /**
   * Gets the on part.
   *
   * @return the on part
   */
  public String getOnPart() {
    return onPart;
  }

  /**
   * Sets the on part.
   *
   * @param onPart the new on part
   */
  public void setOnPart(String onPart) {
    this.onPart = onPart;
  }

  /**
   * Gets the raw content.
   *
   * @return the raw content
   */
  public String getRawContent() {
    return rawContent;
  }

  /**
   * Sets the raw content.
   *
   * @param rawContent the new raw content
   */
  public void setRawContent(String rawContent) {
    this.rawContent = rawContent;
  }
}
