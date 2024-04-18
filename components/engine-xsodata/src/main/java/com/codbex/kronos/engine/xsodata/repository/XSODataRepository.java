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
package com.codbex.kronos.engine.xsodata.repository;

import com.codbex.kronos.engine.xsodata.domain.XSOData;
import org.eclipse.dirigible.components.base.artefact.ArtefactRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface XSODataRepository.
 */
@Repository("xsodataRepository")
public interface XSODataRepository extends ArtefactRepository<XSOData, Long> {

}
