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
package com.codbex.kronos.parser.hdbcalculationview.ndb;

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

import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcalculation.CalculationScenario;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcalculation.CalculationViewType;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcalculation.DataSource;
import com.codbex.kronos.parser.hdbcalculationview.ndb.bimodelcalculation.DataSources;

public class HDBCalculationViewTest {

    private static final Logger logger = LoggerFactory.getLogger(HDBCalculationViewTest.class);

    @Test
    public void testSerialize() throws JAXBException {
        CalculationScenario calculationScenario = new CalculationScenario();
        calculationScenario.setId("com.codbex.kronos.samples::KRONOS_HDI_SIMPLE_CALC_VIEW");
        calculationScenario.setOutputViewType(CalculationViewType.PROJECTION);
        DataSource dataSource = new DataSource();
        dataSource.setId("KRONOS_HDI_SIMPLE_TABLE");
        dataSource.setResourceUri("KRONOS_HDI_SIMPLE_TABLE");
        DataSources dataSources = new DataSources();
        dataSources.getDataSource()
                   .add(dataSource);
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
    public void testDeserialize() throws JAXBException {
        // String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"
        // standalone=\"yes\"?><ns2:calculationScenario outputViewType=\"Projection\"
        // id=\"com.codbex.kronos.samples::KRONOS_HDI_SIMPLE_CALC_VIEW\"
        // xmlns:ns2=\"http://www.sap.com/ndb/BiModelCalculation.ecore\"><dataSources><DataSource
        // id=\"KRONOS_HDI_SIMPLE_TABLE\"><resourceUri>KRONOS_HDI_SIMPLE_TABLE</resourceUri></DataSource></dataSources></ns2:calculationScenario>";

        String xml = "";
        try {
            xml = org.apache.commons.io.IOUtils.toString(HDBCalculationViewTest.class.getResourceAsStream("/test.hdbcalculationview"));
        } catch (IOException e) {
            fail("Parsing of calculation view failed.");
            logger.error(e.getMessage(), e);
        }

        xml = xml.replace("<Calculation:scenario", "<Calculation:calculationScenario");
        xml = xml.replace("</Calculation:scenario>", "</Calculation:calculationScenario>");

        JAXBContext context = JAXBContext.newInstance(CalculationScenario.class);
        Unmarshaller um = context.createUnmarshaller();
        CalculationScenario calculationScenario = (CalculationScenario) um.unmarshal(new StringReader(xml));

        assertEquals(calculationScenario.getId(), "com.sap.hana.example::projection");
        assertEquals(calculationScenario.getDataSources()
                                        .getDataSource()
                                        .get(0)
                                        .getResourceUri(),
                "com.sap.hana.example::TAB1");
    }

}
