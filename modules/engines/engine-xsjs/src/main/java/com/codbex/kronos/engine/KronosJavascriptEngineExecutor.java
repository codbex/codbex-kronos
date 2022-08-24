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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.engine.api.script.IScriptEngineExecutor;
import org.eclipse.dirigible.graalium.core.DirigibleJavascriptHooksProvider;
import org.eclipse.dirigible.graalium.core.graal.GraalJSInterceptor;
import org.eclipse.dirigible.graalium.engine.GraaliumJavascriptEngineExecutor;
import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.eclipse.dirigible.repository.api.IResource;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Context.Builder;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The HANA XS Classic Javascript Engine Executor.
 */
public class KronosJavascriptEngineExecutor extends GraaliumJavascriptEngineExecutor
		implements IScriptEngineExecutor, DirigibleJavascriptHooksProvider, Consumer<Context>, GraalJSInterceptor {
	
	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(KronosJavascriptEngineExecutor.class);

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

	/**
	 * Before eval.
	 *
	 * @return the kronos api
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
//  @Override
//  protected void beforeEval(Context context) throws IOException {
//    String kronosApi = getKronosApi();
//    context.getBindings(ENGINE_JAVA_SCRIPT).putMember("KRONOS_API", kronosApi);
//    Value loadScriptStringResult = context.eval(
//        Source
//            .newBuilder(ENGINE_JAVA_SCRIPT, "mainModule.loadScriptString(KRONOS_API)", "internal-module-load-string-code.js")
//            .build()
//    );
//    context.getBindings(ENGINE_JAVA_SCRIPT).putMember("$", loadScriptStringResult);
//    context.eval(getJSErrorFileNamePolyfillSource());
//    super.beforeEval(context);
//  }

  /**
   * Gets the JS error file name polyfill source.
   *
   * @return the JS error file name polyfill source
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static Source getJSErrorFileNamePolyfillSource() throws IOException {
    String errorFileNamePolyfillName = "/ErrorFileNamePolyfill.js";
    InputStream errorFileNamePolyfillInputStream = KronosJavascriptEngineExecutor.class
        .getResourceAsStream("/js/polyfills" + errorFileNamePolyfillName);
    String errorFileNamePolyfillCode = IOUtils.toString(Objects.requireNonNull(errorFileNamePolyfillInputStream), StandardCharsets.UTF_8);
    return Source
        .newBuilder(ENGINE_JAVA_SCRIPT, errorFileNamePolyfillCode, errorFileNamePolyfillName)
        .internal(true)
        .build();

  }

	/**
	 * Gets the kronos api.
	 *
	 * @return the kronos api
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String getKronosApi() throws IOException {
		if (KRONOS_API_CONTENT == null) {
			String API_PATH = IRepositoryStructure.PATH_REGISTRY_PUBLIC + KRONOS_API_LOCATION;
			IResource resource = getRepository().getResource(API_PATH);
			if (resource.exists()) {
				KRONOS_API_CONTENT = new String(resource.getContent(), DEFAULT_CHARSET);
			} else {
				KRONOS_API_CONTENT = IOUtils.toString(KronosJavascriptEngineExecutor.class
					.getResourceAsStream("/META-INF/dirigible" + KRONOS_API_LOCATION), DEFAULT_CHARSET);
				resource = getRepository().createResource(API_PATH, KRONOS_API_CONTENT.getBytes());
			}
			
			
		}
		return KRONOS_API_CONTENT;
	}

//  /**
//   * Exists module.
//   *
//   * @param root the root
//   * @param module the module
//   * @return true, if successful
//   * @throws RepositoryException the repository exception
//   */
//  @Override
//  public boolean existsModule(String root, String module) throws RepositoryException {
//    if (SUPPORTED_MODULE_EXTENSIONS.stream().anyMatch(module::endsWith)) {
//      return super.existsModule(root, module, null);
//    }
//
//    for (String supportedExtension : SUPPORTED_MODULE_EXTENSIONS) {
//      if (super.existsModule(root, module, supportedExtension)) {
//        return true;
//      }
//    }
//
//    return false;
//  }

//  /**
//   * Execute service module.
//   *
//   * @param module the module
//   * @param executionContext the execution context
//   * @return the object
//   * @throws ScriptingException the scripting exception
//   */
//  @Override
//  public Object executeServiceModule(String module, Map<Object, Object> executionContext) throws ScriptingException {
//    return super.executeServiceModule(module, executionContext);
//  }
//
//  /**
//   * Execute service code.
//   *
//   * @param code the code
//   * @param executionContext the execution context
//   * @return the object
//   * @throws ScriptingException the scripting exception
//   */
//  @Override
//  public Object executeServiceCode(String code, Map<Object, Object> executionContext) throws ScriptingException {
//    return super.executeServiceCode(code, executionContext);
//  }
//
//  /**
//   * Eval code.
//   *
//   * @param code the code
//   * @param executionContext the execution context
//   * @return the object
//   * @throws ScriptingException the scripting exception
//   */
//  @Override
//  public Object evalCode(String code, Map<Object, Object> executionContext) throws ScriptingException {
//    return super.evalCode(code, executionContext);
//  }
//
//  /**
//   * Eval module.
//   *
//   * @param module the module
//   * @param executionContext the execution context
//   * @return the object
//   * @throws ScriptingException the scripting exception
//   */
//  @Override
//  public Object evalModule(String module, Map<Object, Object> executionContext) throws ScriptingException {
//    return super.evalModule(module, executionContext);
//  }
//
//  /**
//   * Execute service.
//   *
//   * @param moduleOrCode the module or code
//   * @param executionContext the execution context
//   * @param isModule the is module
//   * @param commonJSModule the common JS module
//   * @return the object
//   * @throws ScriptingException the scripting exception
//   */
//  @Override
//  public Object executeService(String moduleOrCode, Map<Object, Object> executionContext, boolean isModule, boolean commonJSModule)
//      throws ScriptingException {
//    return super.executeService(moduleOrCode, executionContext, isModule, commonJSModule);
//  }

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

	/**
	 * Gets the on before context created listener.
	 *
	 * @return the on before context created listener
	 */
	@Override
	public Consumer<Builder> getOnBeforeContextCreatedListener() {
		return null;
	}

	/**
	 * Gets the on after context created listener.
	 *
	 * @return the on after context created listener
	 */
	@Override
	public Consumer<Context> getOnAfterContextCreatedListener() {
		return this;
	}

	private static final String SOURCE_PROVIDER = "SourceProvider";
	private RepositoryModuleSourceProvider sourceProvider = new RepositoryModuleSourceProvider(this, IRepositoryStructure.PATH_REGISTRY_PUBLIC);
	
	/**
	 * Accept.
	 *
	 * @param context the context
	 */
	@Override
	public void accept(Context context) {
		try {
			
			// Source Provider
			context.getBindings(ENGINE_JAVA_SCRIPT).putMember(SOURCE_PROVIDER, sourceProvider);
			
			// Console
			//context.eval(Source.newBuilder(ENGINE_JAVA_SCRIPT, Require.LOAD_CONSOLE_CODE, "internal-console.js").internal(true).build());
			
			// Module
            context.eval(Source.newBuilder(ENGINE_JAVA_SCRIPT, Require.MODULE_CODE(), "Module.js").build());
            Object mainModule = context.eval(Source.newBuilder(ENGINE_JAVA_SCRIPT, Require.MODULE_CREATE_CODE, "internal-module-create-code.js").build()).as(Object.class);
            Map executionContext = new HashMap();
            context.getBindings(ENGINE_JAVA_SCRIPT).putMember("__context", executionContext);
            executionContext.put("main_module", mainModule);
            
            
			String kronosApi = getKronosApi();
			context.getBindings(ENGINE_JAVA_SCRIPT).putMember("KRONOS_API", kronosApi);
			Value loadScriptStringResult = context.eval(
			    Source
			        .newBuilder(ENGINE_JAVA_SCRIPT, "mainModule.loadScriptString(KRONOS_API)", "internal-module-load-string-code.js")
			        .build()
			);
			context.getBindings(ENGINE_JAVA_SCRIPT).putMember("$", loadScriptStringResult);
	    context.eval(getJSErrorFileNamePolyfillSource());
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			new RuntimeException(e);
		}
		
	}
	
	
	
	@Override
	public void onBeforeRun(String sourcePath, Path absoluteSourcePath, Source source, Context context) {
		
//		// Require
//		try {
//			if (sourcePath.endsWith(".mjs")) {
//				checkIfExists(sourcePath);
//				context.eval(Source.newBuilder(ENGINE_JAVA_SCRIPT, Require.CODE, "internal-require.js").internal(true).build());
//	            context.eval(Source.newBuilder(ENGINE_JAVA_SCRIPT, Require.DIRIGIBLE_REQUIRE_CODE, "internal-dirigible-require.js").internal(true).build()); // alias of Require.CODE
////	            context.eval(Source.newBuilder(ENGINE_JAVA_SCRIPT, "globalThis.console = require('core/v4/console');", "internal-console.js").internal(true).build());
//			} else {
//		        context.getBindings(ENGINE_JAVA_SCRIPT).putMember("MODULE_FILENAME", sourcePath);
//				context.eval(Source.newBuilder(ENGINE_JAVA_SCRIPT, Require.MODULE_LOAD_CODE, "internal-require.js").internal(true).build());
//			}
//		} catch (IOException e) {
//			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
//			new RuntimeException(e);
//		}
		
	}

//	private void checkIfExists(String sourcePath) throws RepositoryWriteException, IOException {
//		IResource resource = getRepository().getResource(IRepositoryStructure.PATH_REGISTRY_PUBLIC + IRepository.SEPARATOR + sourcePath);
//		if (!resource.exists()) {
//			InputStream in = KronosJavascriptEngineExecutor.class.getResourceAsStream(sourcePath);
//			if (in != null) {
//				resource.create();
//				resource.setContent(IOUtils.toByteArray(in));
//			}
//		}
//	}

	@Override
	public void onAfterRun(String arg0, Path arg1, Source arg2, Context arg3, Value arg4) {
		// TODO Auto-generated method stub
		
	}

}
