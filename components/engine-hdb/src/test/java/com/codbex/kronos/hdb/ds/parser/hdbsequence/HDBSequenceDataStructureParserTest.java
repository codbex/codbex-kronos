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
package com.codbex.kronos.hdb.ds.parser.hdbsequence;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertThrows;
//
//import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
//import com.codbex.kronos.engine.hdb.parser.HDBSequenceDataStructureParser;
//import com.codbex.kronos.exceptions.ArtifactParserException;
//import com.codbex.kronos.hdb.ds.model.DBContentType;
//import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
//import com.codbex.kronos.hdb.ds.model.hdbsequence.DataStructureHDBSequenceModel;
//import com.codbex.kronos.hdb.ds.module.HDBTestModule;
//import com.codbex.kronos.parser.hdbsequence.exceptions.HDBSequenceDuplicatePropertyException;
//import com.codbex.kronos.parser.hdbsequence.exceptions.HDBSequenceMissingPropertyException;
//
//import java.nio.charset.StandardCharsets;
//import org.junit.Before;
//import org.junit.Test;

public class HDBSequenceDataStructureParserTest {

//  @Before
//  public void setUp() {
//    HDBTestModule testModule = new HDBTestModule();
//    testModule.configure();
//  }
//
//  @Test
//  public void parseHanaXSClassicContent() throws Exception {
//    String content = org.apache.commons.io.IOUtils
//        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/SampleSequence_HanaXSClassic.hdbsequence"),
//            StandardCharsets.UTF_8);
//
//    DataStructureParametersModel parametersModel =
//        new DataStructureParametersModel(null, "/test/com/codbex/kronos/SampleSequence_HanaXSClassic.hdbsequence", content, null);
//    DataStructureHDBSequenceModel model = (DataStructureHDBSequenceModel) new HDBSequenceDataStructureParser()
//        .parse(parametersModel);
//
//    assertEquals("MYSCHEMA", model.getSchema());
//    assertEquals(Integer.valueOf(10), model.getStartWith());
//    assertEquals(Integer.valueOf(30), model.getMaxValue());
//    assertEquals(false, model.getNoMaxValue());
//    assertEquals(true, model.getNoMinValue());
//    assertEquals(false, model.getCycles());
//    assertEquals(DBContentType.XS_CLASSIC, model.getDBContentType());
//  }
//
//  @Test
//  public void parseDefaultValues() throws Exception {
//    String content = org.apache.commons.io.IOUtils
//        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/SchemaOnlySequence.hdbsequence"),
//            StandardCharsets.UTF_8);
//    DataStructureParametersModel parametersModel =
//        new DataStructureParametersModel(null, "/test/com/codbex/kronos/SchemaOnlySequence.hdbsequence", content, null);
//    DataStructureHDBSequenceModel model = (DataStructureHDBSequenceModel) new HDBSequenceDataStructureParser()
//        .parse(parametersModel);
//    assertFalse(model.isPublic());
//    assertEquals(Integer.valueOf(1), model.getStartWith());
//    assertEquals(Integer.valueOf(1), model.getIncrementBy());
//    assertEquals(Integer.valueOf(1), model.getMinValue());
//  }
//
//  @Test
//  public void parseDependsOnContent() throws Exception {
//    String content = org.apache.commons.io.IOUtils
//        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/DependsOnSequence.hdbsequence"),
//            StandardCharsets.UTF_8);
//    DataStructureParametersModel parametersModel =
//        new DataStructureParametersModel(null, "/test/com/codbex/kronos/DependsOnSequence.hdbsequence", content, null);
//    DataStructureHDBSequenceModel model = (DataStructureHDBSequenceModel) new HDBSequenceDataStructureParser()
//        .parse(parametersModel);
//    assertEquals("sap.ino.db.iam::t_identity", model.getDependsOnTable());
//    assertEquals("sap.ino.db.iam::t_view", model.getDependsOnView());
//  }
//
//  @Test
//  public void parseHanaXSAdvancedContent() throws Exception {
//    String content = org.apache.commons.io.IOUtils
//        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/CustomerId_HanaXSAdvanced.hdbsequence"),
//            StandardCharsets.UTF_8);
//    DataStructureParametersModel parametersModel =
//        new DataStructureParametersModel(null, "/test/com/codbex/kronos/CustomerId_HanaXSAdvanced.hdbsequence", content, null);
//    DataStructureHDBSequenceModel model = (DataStructureHDBSequenceModel) new HDBSequenceDataStructureParser()
//        .parse(parametersModel);
//    assertEquals(DBContentType.OTHERS, model.getDBContentType());
//    assertEquals(content, model.getRawContent());
//  }
//
//
//  @Test
//  public void parseGrammarUnreadableContent() throws Exception {
//    String content = "Some invalid content.";
//    DataStructureParametersModel parametersModel =
//        new DataStructureParametersModel(null, "/test/com/codbex/kronos/InvalidContent.hdbsequence", content, null);
//    assertThrows(ArtifactParserException.class, () -> new HDBSequenceDataStructureParser().parse(parametersModel));
//  }
//
//
//  @Test
//  public void parseRepetitiveProperties() throws Exception {
//    String content = org.apache.commons.io.IOUtils
//        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/RepetitivePropsSequence.hdbsequence"),
//            StandardCharsets.UTF_8);
//    DataStructureParametersModel parametersModel =
//        new DataStructureParametersModel(null, "/test/com/codbex/kronos/RepetitivePropsSequence.hdbsequence", content, null);
//    assertThrows(HDBSequenceDuplicatePropertyException.class, () -> new HDBSequenceDataStructureParser().parse(parametersModel));
//  }
//
//  @Test
//  public void parseRandomlyOrderedContent() throws Exception {
//    String content = org.apache.commons.io.IOUtils
//        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/RandomlyOrderedSequence.hdbsequence"),
//            StandardCharsets.UTF_8);
//    DataStructureParametersModel parametersModel =
//        new DataStructureParametersModel(null, "/test/com/codbex/kronos/RandomlyOrderedSequence.hdbsequence", content, null);
//    DataStructureHDBSequenceModel model = (DataStructureHDBSequenceModel) new HDBSequenceDataStructureParser()
//        .parse(parametersModel);
//
//    assertEquals("USR_9TCD6SXS67DP7AFLFE420B8EB", model.getSchema());
//    assertEquals(Integer.valueOf(10), model.getStartWith());
//    assertEquals(Integer.valueOf(30), model.getMaxValue());
//    assertEquals(false, model.getNoMaxValue());
//    assertEquals(true, model.getNoMinValue());
//    assertEquals(false, model.getCycles());
//    assertFalse(model.isPublic());
//    assertEquals(Integer.valueOf(-2), model.getIncrementBy());
//    assertEquals(DBContentType.XS_CLASSIC, model.getDBContentType());
//  }
//
//
//  @Test
//  public void parseMissingMandatoryProperty() throws Exception {
//    String content = org.apache.commons.io.IOUtils
//        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/MissingPropSequence.hdbsequence"),
//            StandardCharsets.UTF_8);
//
//    DataStructureParametersModel parametersModel =
//        new DataStructureParametersModel(null, "/test/com/codbex/kronos/MissingPropSequence.hdbsequence", content, null);
//    assertThrows(HDBSequenceMissingPropertyException.class, () -> new HDBSequenceDataStructureParser().parse(parametersModel));
//  }
//
//  @Test
//  public void parseNonQuotedSeq() throws Exception {
//    String content = "  SEQUENCE seq";
//    DataStructureParametersModel parametersModel =
//        new DataStructureParametersModel(null, "/test/com/codbex/kronos/someFileName.hdbsequence", content, null);
//    DataStructureHDBSequenceModel model = (DataStructureHDBSequenceModel) new HDBSequenceDataStructureParser()
//        .parse(parametersModel);
//    assertEquals(DBContentType.OTHERS, model.getDBContentType());
//  }
//
//  @Test(expected = ArtifactParserException.class)
//  public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception {
//    String content = "start_with= 10;\n" +
//        "nomaxvalue=dddddfalse;";
//    HDBDataStructureModelFactory.parseView("db/test.hdbsequence", content);
//  }
//
//  @Test(expected = ArtifactParserException.class)
//  public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
//    String content = "start_with= 10;\n" +
//        "nomaxvalue=";
//    HDBDataStructureModelFactory.parseView("db/test.hdbsequence", content);
//  }

}
