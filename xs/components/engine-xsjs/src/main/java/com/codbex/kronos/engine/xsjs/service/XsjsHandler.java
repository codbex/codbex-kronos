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
package com.codbex.kronos.engine.xsjs.service;

import com.codbex.kronos.engine.KronosSourceProvider;
import com.codbex.kronos.engine.Require;
import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.components.base.http.access.UserRequestVerifier;
import org.eclipse.dirigible.components.engine.javascript.service.JavascriptHandler;
import org.eclipse.dirigible.graalium.core.DirigibleJavascriptCodeRunner;
import org.eclipse.dirigible.graalium.core.JavascriptSourceProvider;
import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.eclipse.dirigible.repository.api.IResource;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.eclipse.dirigible.graalium.core.graal.ValueTransformer.transformValue;

/**
 * The Class XsjsHandler.
 */
public class XsjsHandler extends JavascriptHandler {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(XsjsHandler.class);

    /** The Constant ENGINE_JAVA_SCRIPT. */
    private static final String ENGINE_JAVA_SCRIPT = "js";

    /** The Constant KRONOS_API_LOCATION. */
    private static final String KRONOS_API_LOCATION = "/kronos/api.js";

    /** The Constant SOURCE_PROVIDER */
    private static final String SOURCE_PROVIDER = "SourceProvider";
    /** The Constant DEFAULT_CHARSET. */
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
    /** The kronos api content. */
    private static String KRONOS_API_CONTENT = null;
    /** The repository. */
    // TODO remove local vars once the corresponding getters are implemented
    private final IRepository repository;

    /** The source provider. */
    private final KronosSourceProvider sourceProvider;

    /**
     * Instantiates a new xsjs handler.
     *
     * @param repository the repository
     * @param sourceProvider the source provider
     */
    public XsjsHandler(IRepository repository, KronosSourceProvider sourceProvider) {
        super(repository, sourceProvider);
        this.repository = repository;
        this.sourceProvider = sourceProvider;
    }

    /**
     * Gets the source provider.
     *
     * @return the source provider
     */
    // TODO remove this
    public JavascriptSourceProvider getSourceProvider() {
        return sourceProvider;
    }

    /**
     * Handle request.
     *
     * @param projectName the project name
     * @param projectFilePath the project file path
     * @param projectFilePathParam the project file path param
     * @param parameters the parameters
     * @param debug the debug
     * @return the object
     */
    @Override
    public Object handleRequest(String projectName, String projectFilePath, String projectFilePathParam, Map<Object, Object> parameters,
            boolean debug) {
        try {
            if (UserRequestVerifier.isValid()) {
                UserRequestVerifier.getRequest()
                                   .setAttribute("dirigible-rest-resource-path", projectFilePathParam);
            }

            String sourceFilePath = Path.of(projectName, projectFilePath)
                                        .toString();
            String maybeJSCode = sourceProvider.getSource(sourceFilePath);
            if (maybeJSCode == null) {
                throw new IOException("JavaScript source code for project name '" + projectName + "' and file name '" + projectFilePath
                        + "' could not be found, consider publishing it.");
            }

            Path absoluteSourcePath = sourceProvider.getAbsoluteSourcePath(projectName, projectFilePath);
            try (DirigibleJavascriptCodeRunner runner = new DirigibleJavascriptCodeRunner(parameters, debug, sourceProvider)) {
                Source source = runner.prepareSource(absoluteSourcePath);

                // Source Provider
                runner.getCodeRunner()
                      .getGraalContext()
                      .getBindings(ENGINE_JAVA_SCRIPT)
                      .putMember(SOURCE_PROVIDER, sourceProvider);

                // Module
                runner.getCodeRunner()
                      .getGraalContext()
                      .eval(Source.newBuilder(ENGINE_JAVA_SCRIPT, Require.MODULE_CODE(), "Module.js")
                                  .build());
                Object mainModule = runner.getCodeRunner()
                                          .getGraalContext()
                                          .eval(Source.newBuilder(ENGINE_JAVA_SCRIPT, Require.MODULE_CREATE_CODE,
                                                  "internal-module-create-code.js")
                                                      .build())
                                          .as(Object.class);
                Map executionContext = new HashMap();
                runner.getCodeRunner()
                      .getGraalContext()
                      .getBindings(ENGINE_JAVA_SCRIPT)
                      .putMember("__context", executionContext);
                executionContext.put("main_module", mainModule);

                String kronosApi = getKronosApi();
                runner.getCodeRunner()
                      .getGraalContext()
                      .getBindings(ENGINE_JAVA_SCRIPT)
                      .putMember("KRONOS_API", kronosApi);
                Value loadScriptStringResult = runner.getCodeRunner()
                                                     .getGraalContext()
                                                     .eval(Source.newBuilder(ENGINE_JAVA_SCRIPT, kronosApi,
                                                             "internal-module-load-string-code.js")
                                                                 .mimeType("application/javascript+module")
                                                                 .build());

                runner.getCodeRunner()
                      .getGraalContext()
                      .getBindings(ENGINE_JAVA_SCRIPT)
                      .putMember("$", loadScriptStringResult.getMember("$"));
                runner.getCodeRunner()
                      .getGraalContext()
                      .eval(getJSErrorFileNamePolyfillSource());

                runner.getGraalJSInterceptor()
                      .onBeforeRun(sourceFilePath, absoluteSourcePath, source, runner.getCodeRunner()
                                                                                     .getGraalContext());
                Value value = runner.run(source);
                runner.getGraalJSInterceptor()
                      .onAfterRun(sourceFilePath, absoluteSourcePath, source, runner.getCodeRunner()
                                                                                    .getGraalContext(),
                              value);
                return transformValue(value);
            }
        } catch (Exception ex) {
            if (ex.getMessage()
                  .contains("consider publish")) {
                logger.error("File [/{}}/{}}] not published", projectName, projectFilePath, ex);
                return ex.getMessage();
            }
            String message = String.format("Error on processing JavaScript service: [/%s/%s], with parameters: [%s]", projectName,
                    projectFilePath, projectFilePathParam);
            logger.error(message, ex);
            throw new RuntimeException(message, ex);
        }
    }

    /**
     * Gets the JS error file name polyfill source.
     *
     * @return the JS error file name polyfill source
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static Source getJSErrorFileNamePolyfillSource() throws IOException {
        String errorFileNamePolyfillName = "/ErrorFileNamePolyfill.js";
        InputStream errorFileNamePolyfillInputStream = XsjsHandler.class.getResourceAsStream("/js/polyfills" + errorFileNamePolyfillName);
        String errorFileNamePolyfillCode =
                IOUtils.toString(Objects.requireNonNull(errorFileNamePolyfillInputStream), StandardCharsets.UTF_8);
        return Source.newBuilder(ENGINE_JAVA_SCRIPT, errorFileNamePolyfillCode, errorFileNamePolyfillName)
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
                KRONOS_API_CONTENT = IOUtils.toString(XsjsHandler.class.getResourceAsStream("/META-INF/dirigible" + KRONOS_API_LOCATION),
                        DEFAULT_CHARSET);
                resource = getRepository().createResource(API_PATH, KRONOS_API_CONTENT.getBytes());
            }

        }
        return KRONOS_API_CONTENT;
    }

    /**
     * Gets the repository.
     *
     * @return the repository
     */
    // TODO remove this
    @Override
    public IRepository getRepository() {
        return repository;
    }

}
