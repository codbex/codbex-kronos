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
package com.codbex.kronos.api.destination;

/**
 * The Class DestinationResponse.
 */
public class DestinationResponse {

  /** The headers. */
  private Header[] headers;
  
  /** The status code. */
  private int statusCode;
  
  /** The text. */
  private String text;

  /**
   * Gets the headers.
   *
   * @return the headers
   */
  public Header[] getHeaders() {
    return headers;
  }

  /**
   * Sets the headers.
   *
   * @param headers the new headers
   */
  public void setHeaders(Header[] headers) {
    this.headers = headers;
  }

  /**
   * Gets the status code.
   *
   * @return the status code
   */
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * Sets the status code.
   *
   * @param statusCode the new status code
   */
  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Sets the text.
   *
   * @param text the new text
   */
  public void setText(String text) {
    this.text = text;
  }

}
