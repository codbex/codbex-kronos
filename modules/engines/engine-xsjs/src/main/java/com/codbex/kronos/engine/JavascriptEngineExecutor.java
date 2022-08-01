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

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.api.scripting.ScriptingException;
import org.eclipse.dirigible.engine.api.script.IScriptEngineExecutor;
import org.eclipse.dirigible.engine.js.api.IJavascriptModuleSourceProvider;
import org.eclipse.dirigible.engine.js.graalvm.processor.GraalVMJavascriptEngineExecutor;
import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The HANA XS Classic Javascript Engine Executor.
 */
public class JavascriptEngineExecutor extends GraalVMJavascriptEngineExecutor implements IScriptEngineExecutor {

  public static final String ENGINE_NAME = "HANA XS Classic JavaScript Engine";
  private static final String ENGINE_JAVA_SCRIPT = "js";
  private static final String KRONOS_API_LOCATION = "/kronos/api.js";
  private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
  private static String KRONOS_API_CONTENT = null;
  private final RepositoryModuleSourceProvider sourceProvider = new RepositoryModuleSourceProvider(this,
      IRepositoryStructure.PATH_REGISTRY_PUBLIC);

  @Override
  protected void beforeEval(Context context) throws IOException {
    String kronosApi = getKronosApi();
    context.getBindings(ENGINE_JAVA_SCRIPT).putMember("KRONOS_API", kronosApi);
    Value loadScriptStringResult = context.eval(
        Source
            .newBuilder(ENGINE_JAVA_SCRIPT, "mainModule.loadScriptString(KRONOS_API)", "internal-module-load-string-code.js")
            .build()
    );
    context.getBindings(ENGINE_JAVA_SCRIPT).putMember("$", loadScriptStringResult);
    context.eval(getJSErrorFileNamePolyfillSource());
    super.beforeEval(context);
  }

  private static Source getJSErrorFileNamePolyfillSource() throws IOException {
    String errorFileNamePolyfillName = "/ErrorFileNamePolyfill.js";
    InputStream errorFileNamePolyfillInputStream = JavascriptEngineExecutor.class
        .getResourceAsStream("/js/polyfills" + errorFileNamePolyfillName);
    String errorFileNamePolyfillCode = IOUtils.toString(Objects.requireNonNull(errorFileNamePolyfillInputStream), StandardCharsets.UTF_8);
    return Source
        .newBuilder(ENGINE_JAVA_SCRIPT, errorFileNamePolyfillCode, errorFileNamePolyfillName)
        .internal(true)
        .build();

  }

  @Override
  protected String loadSource(String module) throws IOException, URISyntaxException {
    return sourceProvider.loadSource(module);
  }

  private String getKronosApi() throws IOException {
    if (KRONOS_API_CONTENT == null) {
      KRONOS_API_CONTENT = IOUtils
          .toString(JavascriptEngineExecutor.class.getResourceAsStream("/META-INF/dirigible" + KRONOS_API_LOCATION), DEFAULT_CHARSET);
    }
    return KRONOS_API_CONTENT;
  }

  @Override
  public Object executeServiceModule(String module, Map<Object, Object> executionContext) throws ScriptingException {
    return super.executeServiceModule(module, executionContext);
  }

  @Override
  public Object executeServiceCode(String code, Map<Object, Object> executionContext) throws ScriptingException {
    return super.executeServiceCode(code, executionContext);
  }

  @Override
  public Object evalCode(String code, Map<Object, Object> executionContext) throws ScriptingException {
    return super.evalCode(code, executionContext);
  }

  @Override
  public Object evalModule(String module, Map<Object, Object> executionContext) throws ScriptingException {
    return super.evalModule(module, executionContext);
  }

  @Override
  public Object executeService(String moduleOrCode, Map<Object, Object> executionContext, boolean isModule, boolean commonJSModule)
      throws ScriptingException {
    return super.executeService(moduleOrCode, executionContext, isModule, commonJSModule);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.engine.api.script.IEngineExecutor#getType()
   */
  @Override
  public String getType() {
    return "kronosjavascript";
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.engine.api.script.IEngineExecutor#getName()
   */
  @Override
  public String getName() {
    return ENGINE_NAME;
  }

  @Override
  public IJavascriptModuleSourceProvider getSourceProvider() {
    return sourceProvider;
  }
}
