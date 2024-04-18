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
package com.codbex.kronos.engine.xsjob.service;

import com.codbex.kronos.engine.xsjob.domain.XSJob;
import com.codbex.kronos.engine.xsjob.repository.XSJobRepository;
import org.eclipse.dirigible.components.base.artefact.BaseArtefactService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The Class XSJobService.
 */
@Service
@Transactional
public class XSJobService extends BaseArtefactService<XSJob, Long> implements InitializingBean {

  /**
   * The instance.
   */
  private static XSJobService INSTANCE;

  protected XSJobService(XSJobRepository repository) {
    super(repository);
  }

  /**
   * After properties set.
   *
   * @throws Exception the exception
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    INSTANCE = this;
  }

  /**
   * Gets the.
   *
   * @return the XSJob service
   */
  public static XSJobService get() {
    return INSTANCE;
  }

}
