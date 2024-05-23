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
package com.codbex.kronos.engine.xsodata.service;

import com.codbex.kronos.engine.xsodata.domain.XSOData;
import com.codbex.kronos.engine.xsodata.repository.XSODataRepository;
import org.eclipse.dirigible.components.base.artefact.BaseArtefactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class XSODataService.
 */
@Service
@Transactional
public class XSODataService extends BaseArtefactService<XSOData, Long> {

    protected XSODataService(XSODataRepository repository) {
        super(repository);
    }

}
