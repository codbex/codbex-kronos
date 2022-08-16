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
package com.codbex.kronos.integration.tests.applications;

import com.codbex.kronos.integration.tests.applications.deployment.ProjectDeploymentRule;
import com.codbex.kronos.integration.tests.applications.status.ProjectHealthCheckRule;
import com.codbex.kronos.integration.tests.applications.status.ProjectHttpCheck;
import com.codbex.kronos.integration.tests.applications.status.ProjectSqlCheck;
import com.codbex.kronos.integration.tests.applications.utils.HanaDataSourceFactory;
import com.codbex.kronos.integration.tests.applications.utils.HttpClientFactory;
import com.codbex.kronos.integration.tests.core.client.http.HttpClient;
import com.codbex.kronos.integration.tests.core.hdb.utils.HanaITestUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.codbex.kronos.integration.tests.applications.deployment.ProjectType.CUSTOM;
import static org.junit.Assert.assertEquals;

public class XSODataWithXSJSHandlersTest {

  private static DataSource dataSource;
  private static HttpClientFactory httpClientFactory = new HttpClientFactory();

  private static final String APPLICATION_NAME = "xsodata-with-xsjs-handlers";
  private static final String APPLICATION_SCHEMA = "TEST_SCHEMA";

  private static final String EMPLOYEE_TABLE = "xsodata-with-xsjs-handlers::Entities.Employee";
  private static final String STATUS_TABLE = "xsodata-with-xsjs-handlers::Entities.Status";
  private static final String SALARY_TABLE = "xsodata-with-xsjs-handlers::Entities.Salary";

  private static final String XSODATA_SERVICE_PATH = "/services/v4/web/xsodata-with-xsjs-handlers/Service.xsodata";

  private static final String XSODATA_EMPLOYEE_PATH = XSODATA_SERVICE_PATH + "/Employee?$format=json";
  private static final String XSODATA_STATUS_PATH = XSODATA_SERVICE_PATH + "/Status?$format=json";
  private static final String XSODATA_SALARY_PATH = XSODATA_SERVICE_PATH + "/Salary?$format=json";

  private static final List<ProjectHttpCheck> HTTP_CHECKS = Arrays.asList(
      new ProjectHttpCheck(XSODATA_SERVICE_PATH + "/Employee", 200),
      new ProjectHttpCheck(XSODATA_SERVICE_PATH + "/Status", 200),
      new ProjectHttpCheck(XSODATA_SERVICE_PATH + "/Salary", 200)
  );

  private static final List<ProjectSqlCheck> SQL_CHECKS = Arrays.asList(
      new ProjectSqlCheck(APPLICATION_SCHEMA, EMPLOYEE_TABLE, true, false),
      new ProjectSqlCheck(APPLICATION_SCHEMA, STATUS_TABLE, true, false),
      new ProjectSqlCheck(APPLICATION_SCHEMA, SALARY_TABLE, true, false)
  );

  public static final ProjectDeploymentRule projectDeploymentRule = new ProjectDeploymentRule(APPLICATION_NAME, CUSTOM);

  public static final ProjectHealthCheckRule projectHealthCheckRule = new ProjectHealthCheckRule(HTTP_CHECKS, SQL_CHECKS);

  @ClassRule
  public static TestRule chain = RuleChain.outerRule(projectDeploymentRule).around(projectHealthCheckRule);

  @BeforeClass
  public static void getDataSource() {
    dataSource = HanaDataSourceFactory.getDataSource();
  }

  class Employee {

    private Integer Id;
    private String Name;
    private Integer Age;
    private String Country;

    public Employee(String name, Integer age, String country) {
      Name = name;
      Age = age;
      Country = country;
    }

    public Employee(Integer id, String name, Integer age, String country) {
      Id = id;
      Name = name;
      Age = age;
      Country = country;
    }
  }

  @Test
  public void testXsodataXsjsEventHandlers() throws IOException, URISyntaxException, ExecutionException, InterruptedException {

    HttpClient httpClient = httpClientFactory.createHttpClient();

    URL xsodataEmployeeGetUrl = new URL(httpClient.getBaseHost() + XSODATA_EMPLOYEE_PATH);
    URL xsodataStatusGetUrl = new URL(httpClient.getBaseHost() + XSODATA_STATUS_PATH);
    URL xsodataSalaryGetUrl = new URL(httpClient.getBaseHost() + XSODATA_SALARY_PATH);

    makeCreateRequest(httpClient);

    assertHandlerResults(httpClient, xsodataStatusGetUrl, "/expected-results/xsodata-with-xsjs-handlers/EmployeeStatusInserted.json");
    assertHandlerResults(httpClient, xsodataEmployeeGetUrl, "/expected-results/xsodata-with-xsjs-handlers/EmployeeInserted.json");
    assertHandlerResults(httpClient, xsodataSalaryGetUrl, "/expected-results/xsodata-with-xsjs-handlers/EmployeeSalaryInserted.json");

    makeUpdateRequest(httpClient);

    assertHandlerResults(httpClient, xsodataStatusGetUrl, "/expected-results/xsodata-with-xsjs-handlers/EmployeeStatusUpdated.json");
    assertHandlerResults(httpClient, xsodataEmployeeGetUrl, "/expected-results/xsodata-with-xsjs-handlers/EmployeeUpdated.json");
    assertHandlerResults(httpClient, xsodataSalaryGetUrl, "/expected-results/xsodata-with-xsjs-handlers/EmployeeSalaryUpdated.json");

    makeDeleteRequest(httpClient);

    assertHandlerResults(httpClient, xsodataStatusGetUrl, "/expected-results/xsodata-with-xsjs-handlers/EmployeeStatusDeleted.json");
    assertHandlerResults(httpClient, xsodataEmployeeGetUrl, "/expected-results/xsodata-with-xsjs-handlers/EmployeeDeleted.json");
    assertHandlerResults(httpClient, xsodataSalaryGetUrl, "/expected-results/xsodata-with-xsjs-handlers/EmployeeSalaryDeleted.json");
  }

  private void makeCreateRequest(HttpClient httpClient)
      throws IOException, URISyntaxException, ExecutionException, InterruptedException {
    URL xsodataEmployeeCreateUrl = new URL(httpClient.getBaseHost() + XSODATA_SERVICE_PATH + "/Employee");

    Employee employee = new Employee(1, "Ben", 22, "Bulgaria");
    Gson gson = new Gson();
    StringEntity body = new StringEntity(gson.toJson(employee), ContentType.APPLICATION_JSON);

    HttpUriRequest xsodataRequest = RequestBuilder.post(xsodataEmployeeCreateUrl.toURI()).setEntity(body).build();
    httpClient.executeRequestAsync(xsodataRequest).get();
  }

  private void makeUpdateRequest(HttpClient httpClient)
      throws MalformedURLException, URISyntaxException, ExecutionException, InterruptedException {
    URL xsodataEmployeeCreateUrl = new URL(httpClient.getBaseHost() + XSODATA_SERVICE_PATH + "/Employee(1)");

    Employee employee = new Employee("John", 30, "USA");
    Gson gson = new Gson();
    StringEntity body = new StringEntity(gson.toJson(employee), ContentType.APPLICATION_JSON);

    HttpUriRequest xsodataRequest = RequestBuilder.put(xsodataEmployeeCreateUrl.toURI()).setEntity(body).build();
    httpClient.executeRequestAsync(xsodataRequest).get();
  }

  private void makeDeleteRequest(HttpClient httpClient)
      throws MalformedURLException, URISyntaxException, ExecutionException, InterruptedException {
    URL xsodataEmployeeCreateUrl = new URL(httpClient.getBaseHost() + XSODATA_SERVICE_PATH + "/Employee(1)");

    HttpUriRequest xsodataRequest = RequestBuilder.delete(xsodataEmployeeCreateUrl.toURI()).build();
    httpClient.executeRequestAsync(xsodataRequest).get();
  }

  private String makeGetRequest(HttpClient httpClient, URL url)
      throws IOException, URISyntaxException, ExecutionException, InterruptedException {
    HttpUriRequest xsodataRequest = RequestBuilder.get(url.toURI()).build();

    HttpResponse xsodataHttpResponse = httpClient.executeRequestAsync(xsodataRequest).get();
    HttpEntity xsodataEntity = xsodataHttpResponse.getEntity();
    return IOUtils.toString(xsodataEntity.getContent(), StandardCharsets.UTF_8);
  }

  private void assertHandlerResults(HttpClient httpClient, URL xsodataStatusGetUrl, String expectedXsodataResultPath)
      throws IOException, URISyntaxException, ExecutionException, InterruptedException {
    String xsodataResult = makeGetRequest(httpClient, xsodataStatusGetUrl);

    String expectedXsodataResult = IOUtils.toString(
        XSODataWithXSJSHandlersTest.class.getResourceAsStream(expectedXsodataResultPath),
        StandardCharsets.UTF_8);

    JsonElement xsodataJson = JsonParser.parseString(xsodataResult);
    JsonElement expectedXsodataJson = JsonParser.parseString(expectedXsodataResult);

    assertEquals("The xsodata request response did not match the expected result!", expectedXsodataJson, xsodataJson);
  }

  @AfterClass
  public static void dropSchema() throws SQLException {
    try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
      HanaITestUtils.dropSchema(statement, APPLICATION_SCHEMA);
    }
  }
}
