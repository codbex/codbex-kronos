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
package com.codbex.kronos.modificators;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * The Class CalculationViewTransformation.
 */
public class CalculationViewTransformation {

    /** The Constant CALCULATION_VIEW_DATA_SOURCE_TRANSFORMATION_XSLT. */
    private static final String CALCULATION_VIEW_DATA_SOURCE_TRANSFORMATION_XSLT =
            "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n"
                    + "    <xsl:template match=\"DataSource/@type\" />\n" + "    <xsl:template match=\"@*|node()\">\n"
                    + "        <xsl:copy>\n" + "            <xsl:apply-templates select=\"@*|node()\"/>\n" + "        </xsl:copy>\n"
                    + "    </xsl:template>\n" + "    <xsl:template match=\"logicalJoin/@associatedObjectUri\">\n"
                    + "         <xsl:attribute name=\"associatedObjectUri\">\n"
                    + "           <xsl:value-of select=\"concat(substring-before(substring-after(., '/'), '/'), '::', substring-after(substring-after(substring-after(., '/'), '/'), '/'))\" disable-output-escaping=\"yes\"  />\n"
                    + "         </xsl:attribute>\n" + "     </xsl:template>\n"
                    + "     <xsl:template match=\"measureMapping/@schemaName\">\n"
                    + "         <xsl:copy-of select=\"/@*[name(.)!='schemaName']|node()\" />\n" + "     </xsl:template>\n"
                    + "</xsl:stylesheet>";

    /**
     * Removes the type artifact.
     *
     * @param bytes the bytes
     * @return the byte[]
     * @throws TransformerException the transformer exception
     */
    public byte[] removeTypeArtifact(byte[] bytes) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Source source = new StreamSource(new StringReader(CALCULATION_VIEW_DATA_SOURCE_TRANSFORMATION_XSLT));
        Transformer transformer = factory.newTransformer(source);
        StreamSource text = new StreamSource(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        transformer.transform(text, new StreamResult(bout));
        return bout.toByteArray();
    }
}
