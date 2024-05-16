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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.3.0
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2020.11.26 at 10:54:28 AM EET
//

package com.codbex.kronos.parser.hdbcalculationview.ndb.datamodelgraph;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the com.sap.ndb.datamodelgraph package. An ObjectFactory allows you to
 * programatically construct new instances of the Java representation for XML content. The Java
 * representation of XML content can consist of schema derived interfaces and classes representing
 * the binding of schema type definitions, element declarations and model groups. Factory methods
 * for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for
     * package: com.sap.ndb.datamodelgraph
     */
    public ObjectFactory() {}

    /**
     * Create an instance of {@link PatternMatching }.
     *
     * @return the pattern matching
     */
    public PatternMatching createPatternMatching() {
        return new PatternMatching();
    }

    /**
     * Create an instance of {@link ScriptPatternMatching }.
     *
     * @return the script pattern matching
     */
    public ScriptPatternMatching createScriptPatternMatching() {
        return new ScriptPatternMatching();
    }

    /**
     * Create an instance of {@link VertexVariable }.
     *
     * @return the vertex variable
     */
    public VertexVariable createVertexVariable() {
        return new VertexVariable();
    }

    /**
     * Create an instance of {@link EdgeVariable }.
     *
     * @return the edge variable
     */
    public EdgeVariable createEdgeVariable() {
        return new EdgeVariable();
    }

    /**
     * Create an instance of {@link PathVariable }.
     *
     * @return the path variable
     */
    public PathVariable createPathVariable() {
        return new PathVariable();
    }

    /**
     * Create an instance of {@link VariableElement }.
     *
     * @return the variable element
     */
    public VariableElement createVariableElement() {
        return new VariableElement();
    }

    /**
     * Create an instance of {@link GraphVariableOrderBy }.
     *
     * @return the graph variable order by
     */
    public GraphVariableOrderBy createGraphVariableOrderBy() {
        return new GraphVariableOrderBy();
    }

    /**
     * Create an instance of {@link GraphVariableMapping }.
     *
     * @return the graph variable mapping
     */
    public GraphVariableMapping createGraphVariableMapping() {
        return new GraphVariableMapping();
    }

    /**
     * Create an instance of {@link CompoundOperation }.
     *
     * @return the compound operation
     */
    public CompoundOperation createCompoundOperation() {
        return new CompoundOperation();
    }

    /**
     * Create an instance of {@link PredicateOperation }.
     *
     * @return the predicate operation
     */
    public PredicateOperation createPredicateOperation() {
        return new PredicateOperation();
    }

    /**
     * Create an instance of {@link Argument }.
     *
     * @return the argument
     */
    public Argument createArgument() {
        return new Argument();
    }

    /**
     * Create an instance of {@link ConstantArgument }.
     *
     * @return the constant argument
     */
    public ConstantArgument createConstantArgument() {
        return new ConstantArgument();
    }

    /**
     * Create an instance of {@link ParameterArgument }.
     *
     * @return the parameter argument
     */
    public ParameterArgument createParameterArgument() {
        return new ParameterArgument();
    }

    /**
     * Create an instance of {@link GetNeighborhoodParameterization }.
     *
     * @return the gets the neighborhood parameterization
     */
    public GetNeighborhoodParameterization createGetNeighborhoodParameterization() {
        return new GetNeighborhoodParameterization();
    }

    /**
     * Create an instance of {@link GetShortestPathsParameterization }.
     *
     * @return the gets the shortest paths parameterization
     */
    public GetShortestPathsParameterization createGetShortestPathsParameterization() {
        return new GetShortestPathsParameterization();
    }

    /**
     * Create an instance of {@link GetShortestPathParameterization }.
     *
     * @return the gets the shortest path parameterization
     */
    public GetShortestPathParameterization createGetShortestPathParameterization() {
        return new GetShortestPathParameterization();
    }

    /**
     * Create an instance of {@link GetStronglyConnectedComponents }.
     *
     * @return the gets the strongly connected components
     */
    public GetStronglyConnectedComponents createGetStronglyConnectedComponents() {
        return new GetStronglyConnectedComponents();
    }

}
