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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.ibatis.scripting.ScriptingException;
import org.eclipse.dirigible.commons.api.context.ContextException;
import org.eclipse.dirigible.commons.api.context.ThreadContextFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class JavascriptEngineRestServiceTest {

  private static final String TEST_RESOURCE_NAME = "/test.js";

  @Test
  public void getTypeTest() {
    KronosJavascriptEngineRestService restService = new KronosJavascriptEngineRestService();
    assertEquals(KronosJavascriptEngineRestService.class, restService.getType());
  }

  @Test
  public void getLoggerTest() {
    KronosJavascriptEngineRestService restService = new KronosJavascriptEngineRestService();
    assertNotNull(restService.getLogger());
  }

  private Response selectAndExecuteMethod(
      KronosJavascriptEngineRestService restService,
      HttpServletRequest servletRequest,
      String requestType
  ) throws ContextException {
    switch (requestType) {
      case "GET":
        return restService.get(servletRequest, TEST_RESOURCE_NAME);
      case "PUT":
        return restService.put(servletRequest, TEST_RESOURCE_NAME);
      case "HEAD":
        return restService.head(servletRequest, TEST_RESOURCE_NAME);
      case "DELETE":
        return restService.delete(servletRequest, TEST_RESOURCE_NAME);
      case "PATCH":
        return restService.patch(servletRequest, TEST_RESOURCE_NAME);
      case "POST":
        return restService.post(servletRequest, TEST_RESOURCE_NAME);
      default:
        throw new RuntimeException("Unexpected request type '" + requestType + "'");
    }
  }

  @Test
  @Parameters({"GET", "PUT", "POST", "PATCH", "DELETE", "HEAD"})
  public void executeServicePostTest(String requestType) throws ContextException {
    KronosJavascriptEngineProcessor mockProcessor = Mockito.mock(KronosJavascriptEngineProcessor.class);
    HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);

    try (MockedStatic<ThreadContextFacade> mockedThreadContextFacade = mockStatic(ThreadContextFacade.class)) {
      KronosJavascriptEngineRestService restService = new KronosJavascriptEngineRestService(mockProcessor);
      Response response = selectAndExecuteMethod(restService, mockRequest, requestType);

      mockedThreadContextFacade.verify(ThreadContextFacade::setUp, times(1));
      mockedThreadContextFacade.verify(
          () -> ThreadContextFacade.set(
              HttpServletRequest.class.getCanonicalName(),
              mockRequest
          ), times(1)
      );
      verify(mockProcessor, times(1)).executeService(TEST_RESOURCE_NAME, null);
      assertEquals(Status.OK, response.getStatusInfo().toEnum());
    }
  }

  @Test(expected = ContextException.class)
  @Parameters({"GET", "PUT", "POST", "PATCH", "DELETE", "HEAD"})
  public void executeServicePostWithThreadContextExceptionTest(String requestType) throws ContextException {
    KronosJavascriptEngineProcessor mockProcessor = Mockito.mock(KronosJavascriptEngineProcessor.class);
    HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);

    try (MockedStatic<ThreadContextFacade> mockedThreadContextFacade = mockStatic(ThreadContextFacade.class)) {
      mockedThreadContextFacade.when(
          () -> ThreadContextFacade.set(
              HttpServletRequest.class.getCanonicalName(),
              mockRequest
          )
      ).thenThrow(new ContextException());

      KronosJavascriptEngineRestService restService = new KronosJavascriptEngineRestService(mockProcessor);
      selectAndExecuteMethod(restService, mockRequest, requestType);
    }
  }

  @Test(expected = ScriptingException.class)
  @Parameters({"GET", "PUT", "POST", "PATCH", "DELETE", "HEAD"})
  public void executeServicePostWithOtherExceptionTest(String requestType) throws ContextException {
    KronosJavascriptEngineProcessor mockProcessor = Mockito.mock(KronosJavascriptEngineProcessor.class);
    HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
    doThrow(new ScriptingException()).when(mockProcessor).executeService(TEST_RESOURCE_NAME, null);

    try (MockedStatic<ThreadContextFacade> mockedThreadContextFacade = mockStatic(ThreadContextFacade.class)) {
      KronosJavascriptEngineRestService restService = new KronosJavascriptEngineRestService(mockProcessor);
      selectAndExecuteMethod(restService, mockRequest, requestType);
    }
  }

  @Test
  public void assertServiceClassHasCorrectAnnotations() {
    Class<KronosJavascriptEngineRestService> serviceClass = KronosJavascriptEngineRestService.class;
    Path pathAnnotation = serviceClass.getAnnotation(Path.class);
    assertNotNull("Class does not have a path annotation", pathAnnotation);
    assertEquals("Class does not have correct path", "/xsjs", pathAnnotation.value());
  }

  @Test
  public void assertGetMethodHasCorrectAnnotations() throws NoSuchMethodException {
    assertMethodHasCorrectAnnotations("get", GET.class);
  }

  @Test
  public void assertPostMethodHasCorrectAnnotations() throws NoSuchMethodException {
    assertMethodHasCorrectAnnotations("post", POST.class);
  }

  @Test
  public void assertPutMethodHasCorrectAnnotations() throws NoSuchMethodException {
    assertMethodHasCorrectAnnotations("put", PUT.class);
  }

  @Test
  public void assertPatchMethodHasCorrectAnnotations() throws NoSuchMethodException {
    assertMethodHasCorrectAnnotations("patch", PATCH.class);
  }

  @Test
  public void assertDeleteMethodHasCorrectAnnotations() throws NoSuchMethodException {
    assertMethodHasCorrectAnnotations("delete", DELETE.class);
  }

  @Test
  public void assertHeadMethodHasCorrectAnnotations() throws NoSuchMethodException {
    assertMethodHasCorrectAnnotations("head", HEAD.class);
  }

  private static void assertMethodHasCorrectAnnotations(String methodName, Class<? extends Annotation> expectedHttpVerbAnnotation)
      throws NoSuchMethodException {
    Class<KronosJavascriptEngineRestService> serviceClass = KronosJavascriptEngineRestService.class;
    Method headMethod = serviceClass.getMethod(methodName, HttpServletRequest.class, String.class);
    assertCorrectHttpVerbAnnotation(headMethod, expectedHttpVerbAnnotation);
    assertCorrectPathAnnotation(headMethod);
  }

  private static void assertCorrectHttpVerbAnnotation(Method serviceMethod, Class<? extends Annotation> expectedHttpVerbAnnotation) {
    Annotation httpVerbAnnotation = serviceMethod.getAnnotation(expectedHttpVerbAnnotation);
    assertNotNull("Method " + serviceMethod.getName() + " does not have an HTTP verb annotation", httpVerbAnnotation);
  }

  private static void assertCorrectPathAnnotation(Method serviceMethod) {
    String methodName = serviceMethod.getName();
    Path pathAnnotation = serviceMethod.getAnnotation(Path.class);
    assertNotNull("Method " + methodName + " does not have a path annotation", pathAnnotation);
    assertEquals("Method " + methodName + " does not have correct path", "/{path:.*}", pathAnnotation.value());
  }
}
