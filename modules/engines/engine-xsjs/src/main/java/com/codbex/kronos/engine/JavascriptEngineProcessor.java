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
public class JavascriptEngineProcessor implements IJavascriptEngineProcessor {

  private JavascriptEngineExecutor engineExecutor = new JavascriptEngineExecutor();

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.engine.js.api.IJavascriptEngineProcessor#executeService(java.lang.String)
   */
  @Override
  public void executeService(String module) throws ScriptingException {
    Map<Object, Object> executionContext = new HashMap<Object, Object>();
    engineExecutor.executeServiceModule(module, executionContext);
  }

}
