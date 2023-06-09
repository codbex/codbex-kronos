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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.api.scripting.ScriptingException;
import org.eclipse.dirigible.engine.api.script.IScriptEngineExecutor;
import org.eclipse.dirigible.engine.js.api.IJavascriptModuleSourceProvider;
import org.eclipse.dirigible.engine.js.graalvm.processor.GraalVMJavascriptEngineExecutor;
import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.eclipse.dirigible.repository.api.RepositoryException;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

/**
 * The HANA XS Classic Javascript Engine Executor.
 */
public class JavascriptEngineExecutor extends GraalVMJavascriptEngineExecutor implements IScriptEngineExecutor {

  /** The Constant ENGINE_NAME. */
  public static final String ENGINE_NAME = "HANA XS Classic JavaScript Engine";
  
  /** The Constant ENGINE_JAVA_SCRIPT. */
  private static final String ENGINE_JAVA_SCRIPT = "js";
  
  /** The Constant KRONOS_API_LOCATION. */
  private static final String KRONOS_API_LOCATION = "/kronos/api.js";
  
  /** The Constant SUPPORTED_MODULE_EXTENSIONS. */
  private static final List<String> SUPPORTED_MODULE_EXTENSIONS = List.of(".xsjslib", ".xsjs", ".js", ".mjs");
  
  /** The Constant DEFAULT_CHARSET. */
  private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
  
  /** The kronos api content. */
  private static String KRONOS_API_CONTENT = null;
  
  /** The source provider. */
  private final RepositoryModuleSourceProvider sourceProvider = new RepositoryModuleSourceProvider(this,
      IRepositoryStructure.PATH_REGISTRY_PUBLIC);

  /**
   * Before eval.
   *
   * @param context the context
   * @throws IOException Signals that an I/O exception has occurred.
   */
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

  /**
   * Gets the JS error file name polyfill source.
   *
   * @return the JS error file name polyfill source
   * @throws IOException Signals that an I/O exception has occurred.
   */
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

  /**
   * Load source.
   *
   * @param module the module
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws URISyntaxException the URI syntax exception
   */
  @Override
  protected String loadSource(String module) throws IOException, URISyntaxException {
    return sourceProvider.loadSource(module);
  }

  /**
   * Gets the kronos api.
   *
   * @return the kronos api
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String getKronosApi() throws IOException {
    if (KRONOS_API_CONTENT == null) {
      KRONOS_API_CONTENT = IOUtils
          .toString(JavascriptEngineExecutor.class.getResourceAsStream("/META-INF/dirigible" + KRONOS_API_LOCATION), DEFAULT_CHARSET);
    }
    return KRONOS_API_CONTENT;
  }

  /**
   * Exists module.
   *
   * @param root the root
   * @param module the module
   * @return true, if successful
   * @throws RepositoryException the repository exception
   */
  @Override
  public boolean existsModule(String root, String module) throws RepositoryException {
    if (SUPPORTED_MODULE_EXTENSIONS.stream().anyMatch(module::endsWith)) {
      return super.existsModule(root, module, null);
    }

    for (String supportedExtension : SUPPORTED_MODULE_EXTENSIONS) {
      if (super.existsModule(root, module, supportedExtension)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Execute service module.
   *
   * @param module the module
   * @param executionContext the execution context
   * @return the object
   * @throws ScriptingException the scripting exception
   */
  @Override
  public Object executeServiceModule(String module, Map<Object, Object> executionContext) throws ScriptingException {
    return super.executeServiceModule(module, executionContext);
  }

  /**
   * Execute service code.
   *
   * @param code the code
   * @param executionContext the execution context
   * @return the object
   * @throws ScriptingException the scripting exception
   */
  @Override
  public Object executeServiceCode(String code, Map<Object, Object> executionContext) throws ScriptingException {
    return super.executeServiceCode(code, executionContext);
  }

  /**
   * Eval code.
   *
   * @param code the code
   * @param executionContext the execution context
   * @return the object
   * @throws ScriptingException the scripting exception
   */
  @Override
  public Object evalCode(String code, Map<Object, Object> executionContext) throws ScriptingException {
    return super.evalCode(code, executionContext);
  }

  /**
   * Eval module.
   *
   * @param module the module
   * @param executionContext the execution context
   * @return the object
   * @throws ScriptingException the scripting exception
   */
  @Override
  public Object evalModule(String module, Map<Object, Object> executionContext) throws ScriptingException {
    return super.evalModule(module, executionContext);
  }

  /**
   * Execute service.
   *
   * @param moduleOrCode the module or code
   * @param executionContext the execution context
   * @param isModule the is module
   * @param commonJSModule the common JS module
   * @return the object
   * @throws ScriptingException the scripting exception
   */
  @Override
  public Object executeService(String moduleOrCode, Map<Object, Object> executionContext, boolean isModule, boolean commonJSModule)
      throws ScriptingException {
    return super.executeService(moduleOrCode, executionContext, isModule, commonJSModule);
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.engine.api.script.IEngineExecutor#getType()
   */
  @Override
  public String getType() {
    return "kronosjavascript";
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.engine.api.script.IEngineExecutor#getName()
   */
  @Override
  public String getName() {
    return ENGINE_NAME;
  }

  /**
   * Gets the source provider.
   *
   * @return the source provider
   */
  @Override
  public IJavascriptModuleSourceProvider getSourceProvider() {
    return sourceProvider;
  }
}
