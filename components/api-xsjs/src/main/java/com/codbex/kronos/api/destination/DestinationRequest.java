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
package com.codbex.kronos.api.destination;

import java.util.List;

/**
 * The Class DestinationRequest.
 */
public class DestinationRequest {

  /** The method. */
  private int method;
  
  /** The path. */
  private String path;
  
  /** The headers. */
  private List<Header> headers;

  /**
   * Gets the method.
   *
   * @return the method
   */
  public int getMethod() {
    return method;
  }

  /**
   * Sets the method.
   *
   * @param method the new method
   */
  public void setMethod(int method) {
    this.method = method;
  }

  /**
   * Gets the path.
   *
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets the path.
   *
   * @param path the new path
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Gets the headers.
   *
   * @return the headers
   */
  public List<Header> getHeaders() {
    return headers;
  }

  /**
   * Sets the headers.
   *
   * @param headers the new headers
   */
  public void setHeaders(List<Header> headers) {
    this.headers = headers;
  }

}
