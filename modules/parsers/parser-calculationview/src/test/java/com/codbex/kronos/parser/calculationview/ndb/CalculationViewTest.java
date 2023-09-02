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
package com.codbex.kronos.parser.calculationview.ndb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.parser.calculationview.ndb.CalculationScenario;
import com.codbex.kronos.parser.calculationview.ndb.DataSources;
import com.codbex.kronos.parser.calculationview.ndb.DataSources.DataSource;

public class CalculationViewTest {

  private static final Logger logger = LoggerFactory.getLogger(CalculationViewTest.class);

  @Test
  public void serialize() throws JAXBException {
    CalculationScenario calculationScenario = new CalculationScenario();
    calculationScenario.setId("com.codbex.kronos.samples::KRONOS_SIMPLE_CALC_VIEW");
    calculationScenario.setOutputViewType("Projection");
    DataSource dataSource = new DataSource();
    dataSource.setId("KRONOS_SIMPLE_TABLE");
    dataSource.setResourceUri("KRONOS_SIMPLE_TABLE");
    DataSources dataSources = new DataSources();
    dataSources.getDataSource().add(dataSource);
    calculationScenario.setDataSources(dataSources);

    StringWriter writer = new StringWriter();
    JAXBContext context = JAXBContext.newInstance(CalculationScenario.class);
    Marshaller m = context.createMarshaller();
    m.marshal(calculationScenario, writer);

    String serializedContent = writer.toString();
	System.out.println(serializedContent);
	assertNotNull("The serialized content should not be null", serializedContent);

  }

  @Test
  public void deserialize() throws JAXBException {
    String xml = "";
    try {
      xml = org.apache.commons.io.IOUtils.toString(CalculationViewTest.class.getResourceAsStream("/test.calculationview"));
    } catch (IOException e) {
      fail("Parsing of calculation view failed.");
      logger.error(e.getMessage(), e);
    }

    xml = xml.replace("<Calculation:scenario", "<CalculationScenario");
    xml = xml.replace("</Calculation:scenario>", "</CalculationScenario>");

    JAXBContext context = JAXBContext.newInstance(CalculationScenario.class);
    Unmarshaller um = context.createUnmarshaller();
    CalculationScenario calculationScenario = (CalculationScenario) um.unmarshal(new StringReader(xml));

    assertEquals(calculationScenario.getId(), "PO_HEADER");
  }

}
