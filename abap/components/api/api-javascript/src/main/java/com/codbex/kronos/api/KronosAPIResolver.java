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
package com.codbex.kronos.api;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.dirigible.graalium.core.JavascriptSourceProvider;
import org.eclipse.dirigible.graalium.core.modules.ExternalModuleResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KronosAPIResolver implements ExternalModuleResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(KronosAPIResolver.class);

    private static final String API_MODULE_PREFIX = "@aerokit/sdk/";

    private static final String ABAP_API_MODULE_PREFIX = API_MODULE_PREFIX + "abap";
    private static final Pattern API_MODULE_PATTERN = Pattern.compile(API_MODULE_PREFIX + "(\\w+)(?:/(.+))?");
    private static final Pattern ABAP_RUN_FILE_PATTERN = Pattern.compile("^(?:.*/)?([^/]+)/src/run\\.mjs$");

    // must match the folder name where the KRONOS apis are located
    // src/main/resources/META-INF/dirigible/kronos
    // this folder is copied to the Dirigible registry, and it is resolved on runtime
    private static final String KRONOS_DIRIGIBLE_PROJECT_FOLDER = "kronos";

    private final JavascriptSourceProvider sourceProvider;

    KronosAPIResolver(JavascriptSourceProvider sourceProvider) {
        this.sourceProvider = sourceProvider;
    }

    @Override
    public boolean isResolvable(String moduleToResolve) {
        return isKronosModule(moduleToResolve);
    }

    private boolean isKronosModule(String module) {
        return module.startsWith(ABAP_API_MODULE_PREFIX);
    }

    /**
     * Resolve.
     *
     * @param moduleToResolve the module to resolve
     * @return the path
     */
    @Override
    public Path resolve(String moduleToResolve) {
        LOGGER.debug("Resolving actual path for [{}]", moduleToResolve);

        Matcher apiModuleMatcher = API_MODULE_PATTERN.matcher(moduleToResolve);
        boolean apiModule = apiModuleMatcher.matches();
        if (apiModule) {
            String moduleDir = apiModuleMatcher.group(1);
            String moduleFile = apiModuleMatcher.group(2);

            boolean hasModuleFile = !StringUtils.isEmpty(moduleFile);
            String file = hasModuleFile ? moduleFile + ".mjs" : "index.mjs";
            return resolvePath(KRONOS_DIRIGIBLE_PROJECT_FOLDER, "dist", "esm", moduleDir, file);
        }

        throw new IllegalStateException("Couldn't resolve Kronos API module with path [" + moduleToResolve
                + "] since it doesn't match patterns: [" + ABAP_RUN_FILE_PATTERN + "] and [" + API_MODULE_PATTERN + "]");
    }

    private Path resolvePath(String projectPath, String... folders) {
        Path path = sourceProvider.getAbsoluteProjectPath(projectPath);
        for (String folder : folders) {
            path = path.resolve(folder);
        }
        LOGGER.debug("Path resolved to [{}]", path);
        return path;
    }

}
