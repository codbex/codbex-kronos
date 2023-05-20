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
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.eclipse.dirigible.graalium.core.JavascriptSourceProvider;
import org.eclipse.dirigible.graalium.core.javascript.CalledFromJS;
import org.eclipse.dirigible.graalium.core.modules.DirigibleSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The GraalVM Repository Module Source Provider.
 */
@CalledFromJS
public class KronosSourceProvider implements JavascriptSourceProvider {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(KronosSourceProvider.class);

	/** The Constant XSJS_EXTENSION. */
	private static final String XSJS_EXTENSION = ".xsjs"; //$NON-NLS-1$

	/** The Constant XSJSLIB_EXTENSION. */
	private static final String XSJSLIB_EXTENSION = ".xsjslib"; //$NON-NLS-1$

	/** The Constant XSJSLIB_EXPORTS_RESERVED_EXTENSION. */
	private static final String XSJSLIB_EXPORTS_RESERVED_EXTENSION = ".generated_exports";

	/** The Constant JS_EXTENSION. */
	private static final String JS_EXTENSION = ".js"; //$NON-NLS-1$

	/** The Constant JS_EXTENSION. */
	private static final String MJS_EXTENSION = ".mjs"; //$NON-NLS-1$

	private DirigibleSourceProvider dirigibleSourceProvider = new DirigibleSourceProvider();

	@Override
	public Path getAbsoluteSourcePath(String projectName, String projectFileName) {
		return dirigibleSourceProvider.getAbsoluteSourcePath(projectName, projectFileName);
	}

	@Override
	public String getSource(String sourceFilePath) {
		try {
			return loadSource(sourceFilePath);
		} catch (IOException | URISyntaxException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public Path unpackedToFileSystem(Path pathToUnpack, Path pathToLookup) {
		return dirigibleSourceProvider.unpackedToFileSystem(pathToUnpack, pathToLookup);
	}

  /**
   * Load source.
   *
   * @param module the module
   * @return the string
   * @throws IOException        Signals that an I/O exception has occurred.
   * @throws URISyntaxException the URI syntax exception
   */
  public String loadSource(String module) throws IOException, URISyntaxException {

    if (module == null) {
      throw new IOException("Module location cannot be null");
    }

    String sourceCode = null;
    if (module.endsWith(XSJS_EXTENSION)) {
      sourceCode = dirigibleSourceProvider.getSource(module);
    } else if (module.endsWith(XSJSLIB_EXTENSION)) {
      sourceCode = dirigibleSourceProvider.getSource(module + XSJSLIB_EXPORTS_RESERVED_EXTENSION);
    } else if (module.endsWith(JS_EXTENSION)) {
      sourceCode = dirigibleSourceProvider.getSource(module);
    } else if (module.endsWith(MJS_EXTENSION)) {
        sourceCode = dirigibleSourceProvider.getSource(module);
    } else if (module.indexOf(XSJS_EXTENSION + "/") > 0) {
      module = module.substring(0, module.indexOf(XSJS_EXTENSION + "/") + 5);
      sourceCode = dirigibleSourceProvider.getSource(module);
    } else if (module.indexOf(XSJSLIB_EXTENSION + "/") > 0) {
      module = module.substring(0, module.indexOf(XSJSLIB_EXTENSION + "/") + 8);
      sourceCode = dirigibleSourceProvider.getSource(module + XSJSLIB_EXPORTS_RESERVED_EXTENSION);
    } else if (module.indexOf(JS_EXTENSION + "/") > 0) {
      module = module.substring(0, module.indexOf(XSJS_EXTENSION + "/") + 3);
      sourceCode = dirigibleSourceProvider.getSource(module);
    } else {
      if (dirigibleSourceProvider.getSource(module + XSJS_EXTENSION) != null) {
        sourceCode = dirigibleSourceProvider.getSource(module + XSJS_EXTENSION);
        if (sourceCode == null) {
          sourceCode = dirigibleSourceProvider.getSource(module + XSJS_EXTENSION);
        }
      } else if (dirigibleSourceProvider.getSource(module + XSJSLIB_EXTENSION + XSJSLIB_EXPORTS_RESERVED_EXTENSION) != null) {
        sourceCode = dirigibleSourceProvider.getSource(module + XSJSLIB_EXTENSION + XSJSLIB_EXPORTS_RESERVED_EXTENSION);
        if (sourceCode == null) {
          sourceCode = dirigibleSourceProvider.getSource(module + XSJSLIB_EXTENSION + XSJSLIB_EXPORTS_RESERVED_EXTENSION);
        }
      } else if (dirigibleSourceProvider.getSource(module + JS_EXTENSION) != null) {
        sourceCode = dirigibleSourceProvider.getSource(module + JS_EXTENSION);
        if (sourceCode == null) {
          sourceCode = dirigibleSourceProvider.getSource(module + JS_EXTENSION);
        }
      } else {
        throw new IOException("Module: " + module + " does not exist.");
      }
    }

    return sourceCode;
  }

}
