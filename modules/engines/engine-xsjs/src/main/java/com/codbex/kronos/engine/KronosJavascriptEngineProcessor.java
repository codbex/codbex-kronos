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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.dirigible.commons.api.scripting.ScriptingException;
import org.eclipse.dirigible.engine.js.api.IJavascriptEngineProcessor;

/**
 * The HANA XS Classic Javascript Engine Processor.
 */
public class KronosJavascriptEngineProcessor implements IJavascriptEngineProcessor {

  /** The engine executor. */
  private KronosJavascriptEngineExecutor engineExecutor = new KronosJavascriptEngineExecutor();

  /**
   * Execute service.
   *
   * @param module the module
   * @throws ScriptingException the scripting exception
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.engine.js.api.IJavascriptEngineProcessor#executeService(java.lang.String)
   */
  @Override
  public Object executeService(String module, Map<Object, Object> parameters) throws ScriptingException {
    return engineExecutor.executeServiceModule(module, parameters);
  }

}
