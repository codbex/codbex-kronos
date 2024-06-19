package com.codbex.kronos.api;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.dirigible.graalium.core.JavascriptSourceProvider;
import org.eclipse.dirigible.graalium.core.modules.ExternalModuleResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class KronosAPIResolver implements ExternalModuleResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(KronosAPIResolver.class);

    private static final List<String> PURE_ESM_MODULES = List.of("sdk/abap");

    private static final Pattern DIRIGIBLE_CORE_MODULE_SIGNATURE_PATTERN = Pattern.compile("(sdk)/(\\w+)(?:/(.+))?"); // e.g.

    private final JavascriptSourceProvider sourceProvider;

    /**
     * Instantiates a new dirigible esm module resolver.
     *
     * @param sourceProvider the source provider
     */
    KronosAPIResolver(JavascriptSourceProvider sourceProvider) {
        this.sourceProvider = sourceProvider;
    }

    @Override
    public boolean isResolvable(String moduleToResolve) {
        return isPureEsmModule(moduleToResolve);
    }

    private boolean isPureEsmModule(String module) {
        return PURE_ESM_MODULES.stream()
                               .anyMatch(module::startsWith);
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
        Matcher modulePathMatcher = DIRIGIBLE_CORE_MODULE_SIGNATURE_PATTERN.matcher(moduleToResolve);
        if (!modulePathMatcher.matches()) {
            throw new RuntimeException("Found invalid core modules with path [" + moduleToResolve + "] since it doesn't match pattern: "
                    + DIRIGIBLE_CORE_MODULE_SIGNATURE_PATTERN);
        }

        String moduleDir = modulePathMatcher.group(2);
        String moduleFile = modulePathMatcher.group(3);

        boolean hasModuleFile = !StringUtils.isEmpty(moduleFile);

        Path resolvedPath = sourceProvider.getAbsoluteProjectPath("kronos")
                                          .resolve("dist")
                                          .resolve("esm")
                                          .resolve(moduleDir)
                                          .resolve(hasModuleFile ? moduleFile + ".mjs" : "index.mjs");

        LOGGER.debug("Path for [{}] is resolved to [{}]", moduleToResolve, resolvedPath);
        return resolvedPath;
    }
}
