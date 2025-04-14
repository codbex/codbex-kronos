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
package com.codbex.kronos.engine.hdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codbex.kronos.engine.hdb.domain.HDBSynonymTarget;

/**
 * The Interface HDBSynonymTargetRepository.
 */
@Repository("hdbsynonymtargetRepository")
public interface HDBSynonymTargetRepository extends JpaRepository<HDBSynonymTarget, Long> {

}
