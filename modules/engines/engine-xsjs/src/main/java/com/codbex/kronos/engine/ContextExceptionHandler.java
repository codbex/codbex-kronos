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
package com.codbex.kronos.engine;

import org.eclipse.dirigible.commons.api.context.ContextException;
import org.eclipse.dirigible.commons.api.service.AbstractExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.Response.Status;

/**
 * The Class ContextExceptionHandler.
 */
public class ContextExceptionHandler extends AbstractExceptionHandler<ContextException> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(ContextExceptionHandler.class);

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public Class<? extends AbstractExceptionHandler<ContextException>> getType() {
    return ContextExceptionHandler.class;
  }

  /**
   * Gets the logger.
   *
   * @return the logger
   */
  @Override
  protected Logger getLogger() {
    return logger;
  }

  /**
   * Gets the response status.
   *
   * @param e the e
   * @return the response status
   */
  @Override
  protected Status getResponseStatus(ContextException e) {
    return Status.INTERNAL_SERVER_ERROR;
  }

  /**
   * Gets the response message.
   *
   * @param e the e
   * @return the response message
   */
  @Override
  protected String getResponseMessage(ContextException e) {
    return e.getMessage();
  }
}
