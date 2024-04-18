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
package com.codbex.kronos.engine.xssecurity.service;

import com.codbex.kronos.engine.xssecurity.domain.XSAccess;
import com.codbex.kronos.engine.xssecurity.repository.XSAccessRepository;
import org.eclipse.dirigible.components.base.artefact.BaseArtefactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The Class XSAccessService.
 */
@Service
@Transactional
public class XSAccessService extends BaseArtefactService<XSAccess, Long> {


  protected XSAccessService(XSAccessRepository repository) {
    super(repository);
  }

}
