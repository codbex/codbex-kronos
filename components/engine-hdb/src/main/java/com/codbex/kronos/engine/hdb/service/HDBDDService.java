/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.service;

import com.codbex.kronos.engine.hdb.domain.HDBDD;
import com.codbex.kronos.engine.hdb.repository.HDBDDRepository;
import org.eclipse.dirigible.components.base.artefact.BaseArtefactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class HDBDDService.
 */
@Service
@Transactional
public class HDBDDService extends BaseArtefactService<HDBDD, Long> {

    protected HDBDDService(HDBDDRepository repository) {
        super(repository);
    }

}
