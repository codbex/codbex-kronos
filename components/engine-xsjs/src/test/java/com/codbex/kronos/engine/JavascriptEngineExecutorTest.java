/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
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

//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Objects;
//
//import org.apache.commons.io.IOUtils;
//import org.eclipse.dirigible.commons.config.StaticObjects;
//import org.eclipse.dirigible.repository.api.IRepository;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.codbex.kronos.XSJSTest;

/**
 * The Class JavascriptEngineExecutorTest.
 */
public class JavascriptEngineExecutorTest { //extends XSJSTest {

//  @Before
//  public void setup() {
//    insertJSTestCodeInRepository("throwRegularRootLevelError.js");
//    insertJSTestCodeInRepository("throwRegularErrorWithMissingStack.js");
//    insertJSTestCodeInRepository("throwRegularNestedLevelError.js");
//    insertJSTestCodeInRepository("throwNativeJavaException.js");
//    insertJSTestCodeInRepository("throwJSObject.js");
//  }
//
//  @Test
//  public void testJSErrorFileNamePolyfillWithRegularRootLevelError() {
//    executeJSTest("throwRegularRootLevelError.js");
//  }
//
//  @Test
//  public void testJSErrorFileNamePolyfillWithErrorWithNoStack() {
//    executeJSTest("throwRegularErrorWithMissingStack.js");
//  }
//
//  @Test
//  public void testJSErrorFileNamePolyfillWithRegularNestedLevelError() {
//    executeJSTest("throwRegularNestedLevelError.js");
//  }
//
//  @Test
//  public void testJSErrorFileNamePolyfillWithNativeJavaExceptionPropagation() {
//    executeJSTest("throwNativeJavaException.js");
//  }
//
//  @Test
//  public void testJSErrorFileNamePolyfillWithJSObjectThrownAsException() {
//    executeJSTest("throwJSObject.js");
//  }
//
//  private static void executeJSTest(String testSourceFileName) {
//    new KronosJavascriptEngineExecutor().executeServiceModule(testSourceFileName, new HashMap<>());
//  }
//
//  private static void insertJSTestCodeInRepository(String testFileName) {
//      String jsTestCode = getJSTestCode(testFileName);
//      IRepository repository = (IRepository) StaticObjects.get(StaticObjects.REPOSITORY);
//      repository.createResource("/registry/public/" + testFileName, jsTestCode.getBytes(StandardCharsets.UTF_8));
//  }
//
//  private static String getJSTestCode(String testFileName) {
//    InputStream testFileStream = JavascriptEngineExecutorTest.class
//        .getResourceAsStream("/META-INF/dirigible/test/kronos/error_polyfills/" + testFileName);
//    try {
//      return IOUtils.toString(Objects.requireNonNull(testFileStream), StandardCharsets.UTF_8);
//    } catch (Exception e) {
//      throw new RuntimeException("Test file '" + testFileName + "' could not be loaded!", e);
//    }
//  }
}