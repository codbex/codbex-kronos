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
package com.codbex.kronos.parser.hdbdd.symbols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.codbex.kronos.parser.hdbdd.annotation.metadata.AnnotationObj;
import com.codbex.kronos.parser.hdbdd.core.CdsLexer;
import com.codbex.kronos.parser.hdbdd.exception.CDSRuntimeException;
import com.codbex.kronos.parser.hdbdd.factory.AnnotationTemplateFactory;
import com.codbex.kronos.parser.hdbdd.symbols.context.CDSFileScope;
import com.codbex.kronos.parser.hdbdd.symbols.entity.EntitySymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.BuiltInTypeSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.custom.StructuredDataTypeSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.view.ViewSymbol;

/**
 * The Class SymbolTable.
 */
public class SymbolTable {

  /** The global built in type scope. */
  private CDSFileScope globalBuiltInTypeScope = new CDSFileScope();
  
  /** The symbols by full name. */
  private Map<String, Symbol> symbolsByFullName = new HashMap<>();
  
  /** The annotations. */
  private Map<String, AnnotationObj> annotations;
  
  /** The entity graph. */
  private Map<String, List<String>> entityGraph = new HashMap<>();
  
  /** The view graph. */
  private Map<String, List<String>> viewGraph = new HashMap<>();
  
  /** The hana built in types. */
  private Map<String, String> hanaBuiltInTypeScope = new HashMap<>();
  
  /** The annotation template factory. */
  private AnnotationTemplateFactory annotationTemplateFactory = new AnnotationTemplateFactory();

  /**
   * Instantiates a new symbol table.
   */
  public SymbolTable() {
    initTypeSystem();
  }

  /**
   * Inits the type system.
   */
  protected void initTypeSystem() {
    cdsTypeScope.define(new BuiltInTypeSymbol("String", 1, Arrays.asList(CdsLexer.STRING)));
    cdsTypeScope.define(new BuiltInTypeSymbol("LargeString", 0, Arrays.asList(CdsLexer.STRING)));
    cdsTypeScope.define(new BuiltInTypeSymbol("Binary", 1, Arrays.asList(CdsLexer.VARBINARY)));
    cdsTypeScope.define(new BuiltInTypeSymbol("LargeBinary", 0, Arrays.asList(CdsLexer.VARBINARY)));
    cdsTypeScope.define(new BuiltInTypeSymbol("Integer", 0, Arrays.asList(CdsLexer.INTEGER)));
    cdsTypeScope.define(new BuiltInTypeSymbol("Integer64", 0, Arrays.asList(CdsLexer.INTEGER)));
    cdsTypeScope.define(new BuiltInTypeSymbol("Decimal", 2, Arrays.asList(CdsLexer.DECIMAL, CdsLexer.INTEGER)));
    cdsTypeScope.define(new BuiltInTypeSymbol("DecimalFloat", 0, Arrays.asList(CdsLexer.DECIMAL)));
    cdsTypeScope.define(new BuiltInTypeSymbol("BinaryFloat", 0, Arrays.asList(CdsLexer.DECIMAL)));
    cdsTypeScope.define(new BuiltInTypeSymbol("LocalDate", 0, Arrays.asList(CdsLexer.LOCAL_DATE, CdsLexer.DATETIME_VALUE_FUNCTION)));
    cdsTypeScope.define(new BuiltInTypeSymbol("LocalTime", 0, Arrays.asList(CdsLexer.LOCAL_TIME, CdsLexer.DATETIME_VALUE_FUNCTION)));
    cdsTypeScope.define(new BuiltInTypeSymbol("UTCDateTime", 0, Arrays.asList(CdsLexer.UTC_DATE_TIME, CdsLexer.DATETIME_VALUE_FUNCTION)));
    cdsTypeScope.define(new BuiltInTypeSymbol("UTCTimestamp", 0, Arrays.asList(CdsLexer.UTC_TIMESTAMP, CdsLexer.DATETIME_VALUE_FUNCTION)));
    cdsTypeScope.define(new BuiltInTypeSymbol("Boolean", 0, Arrays.asList(CdsLexer.BOOLEAN)));
    cdsTypeScope.define(new BuiltInTypeSymbol("hana.ALPHANUMERIC", 0, Arrays.asList(CdsLexer.STRING), true));
    cdsTypeScope.define(new BuiltInTypeSymbol("hana.SMALLINT", 0, Arrays.asList(CdsLexer.INTEGER), true));
    cdsTypeScope.define(new BuiltInTypeSymbol("hana.TINYINT", 0, Arrays.asList(CdsLexer.INTEGER), true));
    cdsTypeScope.define(new BuiltInTypeSymbol("hana.REAL", 0, Arrays.asList(CdsLexer.DECIMAL), true));
    cdsTypeScope.define(new BuiltInTypeSymbol("hana.SMALLDECIMAL", 0, Arrays.asList(CdsLexer.DECIMAL), true));
    cdsTypeScope.define(new BuiltInTypeSymbol("hana.NVARCHAR", 1, Arrays.asList(CdsLexer.STRING), true));
    cdsTypeScope.define(new BuiltInTypeSymbol("hana.CLOB", 0, Arrays.asList(CdsLexer.VARBINARY), true));
    cdsTypeScope.define(new BuiltInTypeSymbol("hana.BINARY", 0, Arrays.asList(CdsLexer.VARBINARY), true));
    cdsTypeScope.define(new BuiltInTypeSymbol("hana.ST_POINT", 0, Arrays.asList(CdsLexer.STRING), true));
    cdsTypeScope.define(new BuiltInTypeSymbol("hana.ST_GEOMETRY", 0, Arrays.asList(CdsLexer.STRING), true));

    hanaTypeScope.define("String");
    hanaTypeScope.define("LargeString");
    hanaTypeScope.define("Binary");
    hanaTypeScope.define("LargeBinary");
    hanaTypeScope.define("Integer");
    hanaTypeScope.define("Integer64");
    hanaTypeScope.define("Decimal");
    hanaTypeScope.define("DecimalFloat");
    hanaTypeScope.define("BinaryFloat");
    hanaTypeScope.define("LocalDate");
    hanaTypeScope.define("LocalTime");
    hanaTypeScope.define("UTCDateTime");
    hanaTypeScope.define("UTCTimestamp");
    hanaTypeScope.define("Boolean");
    hanaTypeScope.define("hana.ALPHANUMERIC");
    hanaTypeScope.define("hana.SMALLINT");
    hanaTypeScope.define("hana.TINYINT");
    hanaTypeScope.define("hana.REAL");
    hanaTypeScope.define("hana.SMALLDECIMAL");
    hanaTypeScope.define("hana.NVARCHAR");
    hanaTypeScope.define("hana.CLOB");
    hanaTypeScope.define("hana.BINARY");
    hanaTypeScope.define("hana.ST_POINT");
    hanaTypeScope.define("hana.ST_GEOMETRY");

    initAnnotations();
  }

  /**
   * Inits the annotations.
   */
  protected void initAnnotations() {
    annotations = new HashMap<>();
    AnnotationObj catalogObj = annotationTemplateFactory.buildTemplateForCatalogAnnotation();
    annotations.put(catalogObj.getName(), catalogObj);

    AnnotationObj schemaObj = annotationTemplateFactory.buildTemplateForSchemaAnnotation();
    annotations.put(schemaObj.getName(), schemaObj);

    AnnotationObj noKeyObj = annotationTemplateFactory.buildTemplateForNoKeyAnnotation();
    annotations.put(noKeyObj.getName(), noKeyObj);

    AnnotationObj generateTableTypeObj = annotationTemplateFactory.buildTemplateForGenerateTableTypeAnnotation();
    annotations.put(generateTableTypeObj.getName(), generateTableTypeObj);

    AnnotationObj generateSearchIndexObj = annotationTemplateFactory.buildTemplateForSearchIndexAnnotation();
    annotations.put(generateSearchIndexObj.getName(), generateSearchIndexObj);
  }

  /**
   * Adds the entity to graph.
   *
   * @param fullName the full name
   */
  public void addEntityToGraph(String fullName) {
    this.entityGraph.put(fullName, null);
  }

  /**
   * Adds the view to graph.
   *
   * @param fullName the full name
   */
  public void addViewToGraph(String fullName) {
    this.viewGraph.put(fullName, null);
  }

  /**
   * Adds the child to entity.
   *
   * @param entityName the entity name
   * @param childName the child name
   */
  public void addChildToEntity(String entityName, String childName) {
    this.entityGraph.computeIfAbsent(entityName, k -> new ArrayList<>());
    this.entityGraph.get(entityName).add(childName);
  }

  /**
   * Adds the child to view.
   *
   * @param viewName the view name
   * @param childName the child name
   */
  public void addChildToView(String viewName, String childName) {
    this.viewGraph.computeIfAbsent(viewName, k -> new ArrayList<>());
    this.viewGraph.get(viewName).add(childName);
  }

  /**
   * Adds the symbol.
   *
   * @param symbol the symbol
   */
  public void addSymbol(Symbol symbol) {
    symbolsByFullName.put(symbol.getFullName(), symbol);
  }

  /**
   * Gets the symbol.
   *
   * @param symbolFullName the symbol full name
   * @return the symbol
   */
  public Symbol getSymbol(String symbolFullName) {
    return this.symbolsByFullName.get(symbolFullName);
  }

  /**
   * Gets the hana type.
   *
   * @param hanaType the hana type
   * @return the hana type
   */
  public BuiltInTypeSymbol getHanaBuiltInTypeScope(String hanaType) {
    return this.hanaBuiltInTypeScope.get(hanaType);
  }

  /**
   * Gets the sorted entities.
   *
   * @return the sorted entities
   */
  public List<EntitySymbol> getSortedEntities() {
    Set<String> passedEntities = new HashSet<>();
    List<EntitySymbol> orderedEntities = new ArrayList<>();
    entityGraph.keySet().forEach(entityName -> traverseEntityGraph(entityName, orderedEntities, passedEntities));

    return orderedEntities;
  }

  /**
   * Gets the sorted views.
   *
   * @return the sorted views
   */
  public List<ViewSymbol> getSortedViews() {
    Set<String> passedViews = new HashSet<>();
    List<ViewSymbol> orderedViews = new ArrayList<>();
    viewGraph.keySet().forEach(viewName -> traverseViewGraph(viewName, orderedViews, passedViews));

    return orderedViews;
  }

  /**
   * Gets the table types.
   *
   * @return the table types
   */
  public List<StructuredDataTypeSymbol> getTableTypes() {
    return this.symbolsByFullName.values().stream().filter(StructuredDataTypeSymbol.class::isInstance)
        .map(StructuredDataTypeSymbol.class::cast)
        .collect(Collectors.toList());
  }

  /**
   * Clear symbols by full name.
   */
  public void clearSymbolsByFullName() {
    this.symbolsByFullName.clear();
  }

  /**
   * Clear entity graph.
   */
  public void clearEntityGraph() {
    this.entityGraph.clear();
  }

  /**
   * Clear view graph.
   */
  public void clearViewGraph() {
    this.viewGraph.clear();
  }

  /**
   * Gets the global built in type scope.
   *
   * @return the global built in type scope
   */
  public CDSFileScope getGlobalBuiltInTypeScope() {
    return globalBuiltInTypeScope;
  }

  /**
   * Gets the annotation.
   *
   * @param name the name
   * @return the annotation
   */
  public AnnotationObj getAnnotation(String name) {
    return this.annotations.get(name);
  }

  /**
   * Gets the symbols by full name.
   *
   * @return the symbols by full name
   */
  public Map<String, Symbol> getSymbolsByFullName() {
    return symbolsByFullName;
  }

  /**
   * Traverse entity graph.
   *
   * @param entityName the entity name
   * @param orderedSymbol the ordered symbol
   * @param passedEntities the passed entities
   */
  private void traverseEntityGraph(String entityName, List<EntitySymbol> orderedSymbol, Set<String> passedEntities) {
    if (passedEntities.contains(entityName)) {
      return;
    }

    passedEntities.add(entityName);
    List<String> children = entityGraph.get(entityName);
    if (children == null) {
      EntitySymbol bottomEntity = (EntitySymbol) this.symbolsByFullName.get(entityName);
      if (bottomEntity == null) {
        throw new CDSRuntimeException(String.format("No entity with name: %s found in symbol table.", entityName));
      }

      orderedSymbol.add(bottomEntity);
      return;
    }

    children.forEach(child -> traverseEntityGraph(child, orderedSymbol, passedEntities));

    orderedSymbol.add((EntitySymbol) this.symbolsByFullName.get(entityName));
  }

  /**
   * Traverse view graph.
   *
   * @param viewName the view name
   * @param orderedSymbol the ordered symbol
   * @param passedViews the passed views
   */
  private void traverseViewGraph(String viewName, List<ViewSymbol> orderedSymbol, Set<String> passedViews) {
    if (passedViews.contains(viewName)) {
      return;
    }

    passedViews.add(viewName);
    List<String> children = viewGraph.get(viewName);
    if (children == null) {

      ViewSymbol bottomView = null;

      if (!(this.symbolsByFullName.get(viewName) instanceof ViewSymbol)) {
        return;
      } else {
        bottomView = (ViewSymbol) this.symbolsByFullName.get(viewName);
      }

      if (bottomView == null) {
        throw new CDSRuntimeException(String.format("No view with name: %s found in symbol table.", viewName));
      }

      orderedSymbol.add(bottomView);
      return;
    }

    children.forEach(child -> traverseViewGraph(child, orderedSymbol, passedViews));

    orderedSymbol.add((ViewSymbol) this.symbolsByFullName.get(viewName));
  }
}
