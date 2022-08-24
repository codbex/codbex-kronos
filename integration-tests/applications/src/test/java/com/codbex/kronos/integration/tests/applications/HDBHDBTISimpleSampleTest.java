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
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.codbex.kronos.integration.tests.applications.deployment.ProjectType.SAMPLE;
import static org.junit.Assert.assertEquals;

public class HDBHDBTISimpleSampleTest {

  private static DataSource dataSource;
  private static HttpClientFactory httpClientFactory = new HttpClientFactory();

  private static final String APPLICATION_NAME = "hdb-hdbti-simple";
  private static final String APPLICATION_SCHEMA = "SAMPLES_HDB_HDBTI_SIMPLE";

  private static final String ORDERS_TABLE = "hdb-hdbti-simple::Products.Orders";

  private static final String XSJS_SERVICE_PATH = "/services/v4/xsjs/hdb-hdbti-simple/Service.xsjs";
  private static final String XSODATA_SERVICE_PATH = "/services/v4/web/hdb-hdbti-simple/Service.xsodata";

  private static final String XSJS_PRODUCTS_ORDERS_PATH = XSJS_SERVICE_PATH + "/productsOrders";
  private static final String XSODATA_PRODUCTS_ORDERS_PATH = XSODATA_SERVICE_PATH + "/ProductsOrders?$format=json";

  private static final List<ProjectHttpCheck> HTTP_CHECKS = Arrays.asList(
      new ProjectHttpCheck(XSJS_SERVICE_PATH + "/status", 200, "OK"),
      new ProjectHttpCheck(XSODATA_SERVICE_PATH + "/ProductsOrders", 200)
  );

  private static final List<ProjectSqlCheck> SQL_CHECKS = Arrays.asList(
      new ProjectSqlCheck(APPLICATION_SCHEMA, ORDERS_TABLE, true, true)
  );

  public static final ProjectDeploymentRule projectDeploymentRule = new ProjectDeploymentRule(APPLICATION_NAME, SAMPLE);

  public static final ProjectHealthCheckRule projectHealthCheckRule = new ProjectHealthCheckRule(HTTP_CHECKS, SQL_CHECKS);

  @ClassRule
  public static TestRule chain = RuleChain.outerRule(projectDeploymentRule).around(projectHealthCheckRule);

  @BeforeClass
  public static void getDataSource() {
    dataSource = HanaDataSourceFactory.getDataSource();
  }

  @Test
  public void testHdbHdbtiSimpleSampleXsjsService() throws IOException, URISyntaxException, ExecutionException, InterruptedException {

    HttpClient httpClient = httpClientFactory.createHttpClient();

    URL xsjsUrl = new URL(httpClient.getBaseHost() + XSJS_PRODUCTS_ORDERS_PATH);

    HttpUriRequest xsjsRequest = RequestBuilder.get(xsjsUrl.toURI()).build();

    HttpResponse xsjsHttpResponse = httpClient.executeRequestAsync(xsjsRequest).get();
    HttpEntity xsjsEntity = xsjsHttpResponse.getEntity();
    String xsjsResult = IOUtils.toString(xsjsEntity.getContent(), StandardCharsets.UTF_8);

    String expectedXsjsResult = IOUtils.toString(
        HDBHDBTISimpleSampleTest.class.getResourceAsStream("/expected-results/hdb-hdbti-simple/HdbHdbtiSimpleSampleXsjsResult.json"),
        StandardCharsets.UTF_8);

    JsonElement xsjsJson = JsonParser.parseString(xsjsResult);
    JsonElement expectedXsjsJson = JsonParser.parseString(expectedXsjsResult);

    assertEquals("The xsjs request response did not match the expected result!", expectedXsjsJson, xsjsJson);
  }

  @Test
  public void testHdbHdbtiSimpleSampleXsodataService() throws IOException, URISyntaxException, ExecutionException, InterruptedException {

    HttpClient httpClient = httpClientFactory.createHttpClient();

    URL xsodataUrl = new URL(httpClient.getBaseHost() + XSODATA_PRODUCTS_ORDERS_PATH);

    HttpUriRequest xsodataRequest = RequestBuilder.get(xsodataUrl.toURI()).build();

    HttpResponse xsodataHttpResponse = httpClient.executeRequestAsync(xsodataRequest).get();
    HttpEntity xsodataEntity = xsodataHttpResponse.getEntity();
    String xsodataResult = IOUtils.toString(xsodataEntity.getContent(), StandardCharsets.UTF_8);

    String expectedXsodataResult = IOUtils.toString(
        HDBHDBTISimpleSampleTest.class.getResourceAsStream("/expected-results/hdb-hdbti-simple/HdbHdbtiSimpleSampleXsodataResult.json"),
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
