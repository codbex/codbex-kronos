package com.codbex.kronos.hdbdd.listener;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.codbex.kronos.parser.hdbdd.core.CdsLexer;
import com.codbex.kronos.parser.hdbdd.core.CdsParser;
import com.codbex.kronos.parser.hdbdd.custom.ArtifactDefinitionListener;
import com.codbex.kronos.parser.hdbdd.custom.ReferenceResolvingListener;
import com.codbex.kronos.parser.hdbdd.custom.XSKHdbddErrorListener;
import com.codbex.kronos.parser.hdbdd.exception.CDSRuntimeException;
import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.SymbolTable;
import com.codbex.kronos.parser.hdbdd.symbols.entity.EntitySymbol;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.codbex.kronos.parser.hdbdd.symbols.type.custom.DataTypeSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.field.Typeable;
import com.codbex.kronos.parser.hdbdd.symbols.view.ViewSymbol;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

public class ArtifactDefinitionListenerTest {

  private final SymbolTable symbolTable = new SymbolTable();

  @Test
  public void testParseCaseInsensitiveKeysSuccessfully() throws Exception {
    CdsParser parsedFile = parseSampleFile("/CaseInsensitiveTest.hdbdd", "acme/table/CaseInsensitiveTest.hdbdd");
    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
  }

  @Test
  public void testParseDefaultValuesSuccessfully() throws Exception {
    CdsParser parsedFile = parseSampleFile("/DefaultValues.hdbdd", "acme/table/DefaultValues.hdbdd");
    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
  }

  @Test
  public void testParseInvalidDefaultValueType() throws Exception {
    try {
      parseSampleFile("/InvalidDefaultValueType.hdbdd", "acme/table/InvalidDefaultValueType.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals(
          "Failed to parse file: acme/table/InvalidDefaultValueType.hdbdd. Error at line: 8 col: 50. Incompatible types! Expected NVARCHAR, Provided 1",
          e.getMessage());
    }
  }

  @Test
  public void testParseEntitySuccessfully() throws Exception {
    CdsParser parsedFile = parseSampleFile("/ParseEntity.hdbdd", "acme/table/ParseEntity.hdbdd");
    List<EntitySymbol> parsedEntities = this.symbolTable.getSortedEntities();

    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
    assertEquals(1, parsedEntities.size());
    assertEquals("TEST_SCHEMA", parsedEntities.get(0).getSchema());
  }

  @Test
  public void testParseContextSuccessfully() throws Exception {
    CdsParser parsedFile = parseSampleFile("/ParseContext.hdbdd", "acme/table/ParseContext.hdbdd");
    List<EntitySymbol> parsedEntities = this.symbolTable.getSortedEntities();//get only Entities

    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
    assertEquals(10, parsedEntities.size());//-> must be 13 after type is implemented
    parsedEntities.forEach(el -> assertEquals("TEST_SCHEMA", el.getSchema()));
  }

  @Test
  public void testParseAssociationsSuccessfully() throws Exception {
    CdsParser parsedFile = parseSampleFile("/Products.hdbdd", "acme/db/Products.hdbdd");
    List<EntitySymbol> parsedEntities = this.symbolTable.getSortedEntities();//get only Entities

    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
    assertEquals(0, parsedEntities.get(1).getAssociations().get(0).getForeignKeys().size());
    assertEquals(1, parsedEntities.get(1).getAssociations().get(1).getForeignKeys().size());
    assertEquals(0, parsedEntities.get(1).getAssociations().get(2).getForeignKeys().size());
    assertEquals(0, parsedEntities.get(1).getAssociations().get(3).getForeignKeys().size());
    assertEquals(0, parsedEntities.get(1).getAssociations().get(4).getForeignKeys().size());
    assertEquals(0, parsedEntities.get(1).getAssociations().get(5).getForeignKeys().size());
  }

  @Test
  public void testParseAssociationsWithKeyAndNullConstraint() throws Exception {
    CdsParser parsedFile = parseSampleFile("/AssociationWithKeyAndNullConstraint.hdbdd",
        "acme/db/AssociationWithKeyAndNullConstraint.hdbdd");
    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
  }

  @Test
  public void testParseKeyAssociationIsNull() throws Exception {
    try {
      parseSampleFile("/KeyAssociationIsNull.hdbdd", "acme/db/KeyAssociationIsNull.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 10 col: 82. Association - part of composite key cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testParseAssociationMissingElementForForeignKey() throws Exception {
    try {
      parseSampleFile("/AssociationMissingElementForForeignKey.hdbdd", "acme/db/AssociationMissingElementForForeignKey.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals(
          "Failed to parse file: acme/db/AssociationMissingElementForForeignKey.hdbdd. Error at line: 10. No such element found in entity: AssociationMissingElementForForeignKey.Country.Id.",
          e.getMessage());
    }
  }

  @Test
  public void testParseAssociationMissingElementForUnmanagedForeignKey() throws Exception {
    try {
      parseSampleFile("/AssociationMissingElementForUnmanagedForeignKey.hdbdd",
          "acme/db/AssociationMissingElementForUnmanagedForeignKey.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals(
          "Failed to parse file: acme/db/AssociationMissingElementForUnmanagedForeignKey.hdbdd. Error at line: 10. No such element found in entity: Country.Id.",
          e.getMessage());
    }
  }

  @Test
  public void testParseAssociationElementNotPKForUnmanagedForeignKey() throws Exception {
    try {
      parseSampleFile("/AssociationElementNotPKForUnmanagedForeignKey.hdbdd", "acme/db/AssociationElementNotPKForUnmanagedForeignKey.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals(
          "Failed to parse file: acme/db/AssociationElementNotPKForUnmanagedForeignKey.hdbdd. Error at line: 10. No such element: Id found in: Country or the element is not a primary key.",
          e.getMessage());
    }
  }

  @Test
  public void testParseElementNameAsEntityName() throws Exception {
    CdsParser parsedFile = parseSampleFile("/ElementNameAsEntityName.hdbdd", "acme/db/ElementNameAsEntityName.hdbdd");
    List<EntitySymbol> parsedEntities = this.symbolTable.getSortedEntities();//get only Entities

    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
    assertEquals(parsedEntities.get(0).getName(), parsedEntities.get(0).getElements().get(0).getName());
  }

  @Test
  public void testParseParseStructuredTypeSuccessfully() throws Exception {
    CdsParser parsedFile = parseSampleFile("/ParseStructuredType.hdbdd", "acme/table/ParseStructuredType.hdbdd");
    this.symbolTable.getSortedEntities();//get only Entities

    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
  }

  @Test
  public void testParseUnmanagedAssociationSuccessfully() throws Exception {
    CdsParser parsedFile = parseSampleFile("/ProjectProducts.hdbdd", "acme/db/ProjectProducts.hdbdd");
    this.symbolTable.getSortedEntities();//get only Entities

    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
  }

  @Test
  public void testParseMinimumCardinalityGreaterThanMaximum() throws Exception {
    try {
      parseSampleFile("/MinimumCardinalityGreaterThanMaximum.hdbdd", "acme/db/MinimumCardinalityGreaterThanMaximum.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 9 col: 46. Maximum cardinality should be greater than minimum cardinality", e.getMessage());
    }
  }

  @Test
  public void testParseMaximumCardinalityNegativeNumber() throws Exception {
    try {
      parseSampleFile("/MaximumCardinalityNegativeNumber.hdbdd", "acme/db/MaximumCardinalityNegativeNumber.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 9 col: 47. Maximum cardinality should be a positive number.", e.getMessage());
    }
  }

  @Test
  public void testParseMinimumCardinalityNegativeNumber() throws Exception {
    try {
      parseSampleFile("/MinimumCardinalityNegativeNumber.hdbdd", "acme/db/MinimumCardinalityNegativeNumber.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 9. Minimum cardinality should be equal or greater than zero.", e.getMessage());
    }
  }

  @Test
  public void testParseInvalidAnnotationValueType() throws Exception {
    try {
      parseSampleFile("/InvalidAnnotationValueType.hdbdd", "acme/db/InvalidAnnotationValueType.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("ERROR: Invalid value type provided", e.getMessage());
    }
  }

  @Test
  public void testParseInvalidAnnotationEnumValue() throws Exception {
    try {
      parseSampleFile("/InvalidAnnotationEnumValue.hdbdd", "acme/db/InvalidAnnotationEnumValue.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("ERROR: No such value available for enum!", e.getMessage());
    }
  }

  @Test
  public void testParseAnnotationAlreadyAssigned() throws Exception {
    try {
      parseSampleFile("/AnnotationAlreadyAssigned.hdbdd", "acme/db/AnnotationAlreadyAssigned.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 6 col: 3. Annotation Catalog already assigned for acme.db::AnnotationAlreadyAssigned.Test.",
          e.getMessage());
    }
  }

  @Test
  public void testParseNoValueAnnotation() throws Exception {
    try {
      parseSampleFile("/NoValueAnnotation.hdbdd", "acme/db/NoValueAnnotation.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 5 col: 3. Values cannot be assigned to annotation nokey.", e.getMessage());
    }
  }

  @Test
  public void testParseInvalidValueTypeForAnnotation() throws Exception {
    try {
      parseSampleFile("/InvalidValueTypeForAnnotation.hdbdd", "acme/db/InvalidValueTypeForAnnotation.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 5 col: 3. Invalid value type provided for annotation Catalog.", e.getMessage());
    }
  }

  @Test
  public void testParsePropertyForAnnotationAlreadyProvided() throws Exception {
    try {
      parseSampleFile("/PropertyForAnnotationAlreadyProvided.hdbdd", "acme/db/PropertyForAnnotationAlreadyProvided.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 6 col: 11. Value for property name: tableType has already been provided.", e.getMessage());
    }
  }

  @Test
  public void testParseInvalidAnnotationProperty() throws Exception {
    try {
      parseSampleFile("/InvalidAnnotationProperty.hdbdd", "acme/db/InvalidAnnotationProperty.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 6 col: 11. No field with name: myTableType exists in annotation Catalog..", e.getMessage());
    }
  }

  @Test
  public void testParseAnnotationCannotBeUsedAsMarker() throws Exception {
    try {
      parseSampleFile("/AnnotationCannotBeUsedAsMarker.hdbdd", "acme/db/AnnotationCannotBeUsedAsMarker.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 6 col: 3.Annotation with name: Catalog cannot be used as a marker.", e.getMessage());
    }
  }

  @Test
  public void testParseNotAllowedAnnotation() throws Exception {
    try {
      parseSampleFile("/NotAllowedAnnotation.hdbdd", "acme/db/NotAllowedAnnotation.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 6 col: 3. Annotation Catalog not allowed for acme.db::NotAllowedAnnotation.Test", e.getMessage());
    }
  }

  @Test
  public void testParseTopLevelAnnotation() throws Exception {
    try {
      parseSampleFile("/TopLevelAnnotation.hdbdd", "acme/db/TopLevelAnnotation.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 6 col: 3. Annotation Schema is only allowed for top level entities.", e.getMessage());
    }
  }

  @Test
  public void testParseNotSupportedAnnotation() throws Exception {
    try {
      parseSampleFile("/NotSupportedAnnotation.hdbdd", "acme/db/NotSupportedAnnotation.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 6 col: 3. Annotation with name: Something not supported.", e.getMessage());
    }
  }

  @Test
  public void testParseNoKeyAnnotationForEntityWithKeys() throws Exception {
    try {
      parseSampleFile("/NoKeyAnnotationForEntityWithKeys.hdbdd", "acme/db/NoKeyAnnotationForEntityWithKeys.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 6 col: 3. Annotation nokey has been specified for entity with keys.", e.getMessage());
    }
  }

  @Test
  public void testParseNoKeyAnnotationForEntityWithoutKeys() throws Exception {
    CdsParser parsedFile = parseSampleFile("/NoKeyAnnotationForEntityWithoutKeys.hdbdd",
        "acme/db/NoKeyAnnotationForEntityWithoutKeys.hdbdd");
    List<EntitySymbol> parsedEntities = this.symbolTable.getSortedEntities();
    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
    assertEquals(1, parsedEntities.size());
  }

  @Test
  public void testParseHanaBuiltInTypesSuccessfully() throws Exception {
    CdsParser parsedFile = parseSampleFile("/HanaBuiltInTypes.hdbdd", "acme/db/HanaBuiltInTypes.hdbdd");
    List<EntitySymbol> parsedEntities = this.symbolTable.getSortedEntities();

    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
    assertEquals(1, parsedEntities.size());
    EntitySymbol entity = parsedEntities.get(0);
    assertEquals(10, entity.getElements().size());

    assertEquals("ALPHANUMERIC", entity.getElements().get(1).getType().getName());
    assertEquals("NVARCHAR", entity.getElements().get(0).getType().getName());
    assertEquals("SMALLINT", entity.getElements().get(2).getType().getName());
    assertEquals("TINYINT", entity.getElements().get(3).getType().getName());
    assertEquals("REAL", entity.getElements().get(4).getType().getName());
    assertEquals("SMALLDECIMAL", entity.getElements().get(5).getType().getName());
    assertEquals("CLOB", entity.getElements().get(6).getType().getName());
    assertEquals("BINARY", entity.getElements().get(7).getType().getName());
    assertEquals("ST_POINT", entity.getElements().get(8).getType().getName());
    assertEquals("ST_GEOMETRY", entity.getElements().get(9).getType().getName());

  }

  @Test
  public void testParseGlobalBuiltInTypesSuccessfully() throws Exception {
    CdsParser parsedFile = parseSampleFile("/GlobalBuiltInTypes.hdbdd", "acme/db/GlobalBuiltInTypes.hdbdd");
    List<EntitySymbol> parsedEntities = this.symbolTable.getSortedEntities();

    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
    assertEquals(1, parsedEntities.size());
    EntitySymbol entity = parsedEntities.get(0);
    assertEquals(16, entity.getElements().size());

    assertEquals("String", entity.getElements().get(0).getType().getName());
    assertEquals("LargeString", entity.getElements().get(1).getType().getName());
    assertEquals("String", entity.getElements().get(2).getType().getName());
    assertEquals("Binary", entity.getElements().get(3).getType().getName());
    assertEquals("LargeBinary", entity.getElements().get(4).getType().getName());
    assertEquals("Integer", entity.getElements().get(5).getType().getName());
    assertEquals("Integer64", entity.getElements().get(6).getType().getName());
    assertEquals("Decimal", entity.getElements().get(7).getType().getName());
    assertEquals("DecimalFloat", entity.getElements().get(8).getType().getName());
    assertEquals("BinaryFloat", entity.getElements().get(9).getType().getName());
    assertEquals("LocalDate", entity.getElements().get(10).getType().getName());
    assertEquals("LocalTime", entity.getElements().get(11).getType().getName());
    assertEquals("UTCDateTime", entity.getElements().get(12).getType().getName());
    assertEquals("UTCTimestamp", entity.getElements().get(13).getType().getName());
    assertEquals("Boolean", entity.getElements().get(14).getType().getName());
    assertEquals("Decimal", entity.getElements().get(15).getType().getName());

  }

  @Test
  public void testParseArtifactsNamedAsKeywordsSuccessfully() throws Exception {
    parseSampleFile("/Context.hdbdd", "acme/db/Context.hdbdd");
    assertEquals(4, this.symbolTable.getSymbolsByFullName().size());

    List<EntitySymbol> entitiesParsed = this.symbolTable.getSortedEntities();
    assertEquals(2, entitiesParsed.size());

    EntitySymbol parsedEntity = entitiesParsed.get(1);
    assertNotNull(parsedEntity.resolve("key"));
    assertNotNull(parsedEntity.resolve("entity"));
    assertNotNull(parsedEntity.resolve("Type"));

    EntitySymbol to = entitiesParsed.get(0);
    assertNotNull(to.resolve("id"));
    assertNotNull(to.resolve("to"));

    assertNotNull(this.symbolTable.getSymbolsByFullName().get("acme.db::Context.Type"));
    Symbol resolvedType = this.symbolTable.getSymbolsByFullName().get("acme.db::Context.Type");
    assertEquals(DataTypeSymbol.class, resolvedType.getClass());
  }

  @Test
  public void testParseInvalidElementDeclarationShouldThrowException() throws Exception {
    try {
      parseSampleFile("/InvalidElementDeclaration.hdbdd", "acme/db/InvalidElementDeclaration.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 5 - Element declarations are only allowed for entity scope.", e.getMessage());
    }
  }

  @Test
  public void testParseInvalidBuiltInHanaType() throws Exception {
    try {
      parseSampleFile("/InvalidBuiltInHanaType.hdbdd", "acme/db/InvalidBuiltInHanaType.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 7. No such hana type found.", e.getMessage());
    }
  }

  @Test
  public void testParseInvalidBuiltInHanaTypeWithArgs() throws Exception {
    try {
      parseSampleFile("/InvalidBuiltInHanaTypeWithArgs.hdbdd", "acme/db/InvalidBuiltInHanaTypeWithArgs.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 7 col: 19. No such type:  String.", e.getMessage());
    }
  }

  @Test
  public void testParseTypeWithoutArguments() throws Exception {
    try {
      parseSampleFile("/TypeWithoutArguments.hdbdd", "acme/db/TypeWithoutArguments.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 10 col: 14. Constructor usage is only allowed for some built in types.", e.getMessage());
    }
  }

  @Test
  public void testParseUserDefinedTypeWithArguments() throws Exception {
    try {
      parseSampleFile("/UserDefinedTypeWithArguments.hdbdd", "acme/db/UserDefinedTypeWithArguments.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 9 col: 11. No such type:  MyCustomType.", e.getMessage());
    }
  }

  @Test
  public void testParseNoSuchTypeWhenUsingTypeOf() throws Exception {
    try {
      parseSampleFile("/NoSuchTypeWhenUsingTypeOf.hdbdd", "acme/db/NoSuchTypeWhenUsingTypeOf.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Failed to parse file: acme/db/NoSuchTypeWhenUsingTypeOf.hdbdd. Error at line: 10. No such type existing.",
          e.getMessage());
    }
  }

  @Test
  public void testParseEntityForTypeOf() throws Exception {
    try {
      parseSampleFile("/EntityForTypeOf.hdbdd", "acme/db/EntityForTypeOf.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Failed to parse file: acme/db/EntityForTypeOf.hdbdd. Error at line: 6. Entities could not be used as a type.",
          e.getMessage());
    }
  }

  @Test
  public void testParseReferenceIsFieldNotType() throws Exception {
    try {
      parseSampleFile("/ReferenceIsFieldNotType.hdbdd", "acme/db/ReferenceIsFieldNotType.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals(
          "Failed to parse file: acme/db/ReferenceIsFieldNotType.hdbdd. Error at line: 10. The reference provided is not a type, but a field. Use 'type of' keyword.",
          e.getMessage());
    }
  }

  @Test
  public void testParseInvalidNumberOfTypeArguments() throws Exception {
    try {
      parseSampleFile("/InvalidNumberOfTypeArguments.hdbdd", "acme/db/InvalidNumberOfTypeArguments.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 7 col: 19. Invalid number of constructor arguments passed.", e.getMessage());
    }
  }

  @Test
  public void testParseInvalidBuiltInType() throws Exception {
    try {
      parseSampleFile("/InvalidBuiltInType.hdbdd", "acme/db/InvalidBuiltInType.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 7 col: 7 - No such type: Text", e.getMessage());
    }
  }

  @Test
  public void testParseInvalidReferenceAssociation() throws Exception {
    try {
      parseSampleFile("/InvalidReferenceAssociation.hdbdd", "acme/db/InvalidReferenceAssociation.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals(
          "Failed to parse file: acme/db/InvalidReferenceAssociation.hdbdd. Error at line: 21 col: 10. The provided reference must be an Entity!",
          e.getMessage());
    }
  }

  @Test
  public void testParseCircularDependency() throws Exception {
    try {
      parseSampleFile("/CircularDependency.hdbdd", "acme/db/CircularDependency.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals(
          "Failed to parse file: acme/db/CircularDependency.hdbdd. Circular dependency", e.getMessage());
    }
  }

  @Test
  public void testParseKeyElementConstraintIsNull() throws Exception {
    try {
      parseSampleFile("/KeyElementConstraintNull.hdbdd", "acme/table/KeyElementConstraintNull.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 6 col: 34. Element - part of composite key cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testParseMissingSchemaAnnotation() throws Exception {
    try {
      parseSampleFile("/MissingSchema.hdbdd", "acme/table/MissingSchema.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 3. Missing '@Schema' annotation for top level symbol definition: Test", e.getMessage());
    }
  }

  @Test
  public void testParseTopLevelArtifactNotMatchingFileName() throws Exception {
    try {
      parseSampleFile("/TopLevelArtifactNotMatchingFileName.hdbdd", "acme/db/TopLevelArtifactNotMatchingFileName.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 5. Top level symbol name does not match the filename.", e.getMessage());
    }
  }

  @Test
  public void testParseNonExistingUserDefinedType() throws Exception {
    try {
      parseSampleFile("/NonExistingUserDefinedType.hdbdd", "acme/db/NonExistingUserDefinedType.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Failed to parse file: acme/db/NonExistingUserDefinedType.hdbdd. Error at line: 9. No such type found: MyCustomType",
          e.getMessage());
    }
  }

  @Test
  public void testParseWithUsingDirectiveSuccessfully() throws Exception {
    CdsParser parsedDataType = parseSampleFile("/UserDefinedDataType.hdbdd", "acme/db/myapp/UserDefinedDataType.hdbdd");
    CdsParser parsedEntity = parseSampleFile("/UsingDirective.hdbdd", "acme/db/UsingDirective.hdbdd");

    assertEquals(0, parsedDataType.getNumberOfSyntaxErrors());
    assertEquals(0, parsedEntity.getNumberOfSyntaxErrors());
  }

  @Test
  public void testParseWithUsingDirectiveWithAliasSuccessfully() throws Exception {
    CdsParser parsedDataType = parseSampleFile("/UserDefinedDataType.hdbdd", "acme/db/myapp/UserDefinedDataType.hdbdd");
    CdsParser parsedEntity = parseSampleFile("/UsingDirectiveWithAlias.hdbdd", "acme/db/UsingDirectiveWithAlias.hdbdd");

    assertEquals(0, parsedDataType.getNumberOfSyntaxErrors());
    assertEquals(0, parsedEntity.getNumberOfSyntaxErrors());
  }

  @Test
  public void testParseWithNonExistingUsingDirective() throws Exception {
    try {
      parseSampleFile("/UsingDirective.hdbdd", "acme/db/UsingDirective.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals(
          "Failed to parse file: acme/db/UsingDirective.hdbdd. Error at line: 2 col: 41. Non existing entity in package: acme.db.myapp::acme.db.myapp::UserDefinedDataType.MyType",
          e.getMessage());
    }
  }

  @Test
  public void testParseElementInStructuredType() throws Exception {
    CdsParser parsedFile = parseSampleFile("/ElementInStructuredType.hdbdd", "acme/db/ElementInStructuredType.hdbdd");
    assertEquals(1, parsedFile.getNumberOfSyntaxErrors());
  }

  @Test
  public void testParseEntitiesWithEscapedNamesShouldResolveCorrectly() throws Exception {
    parseSampleFile("/EscapedNamesExample.hdbdd", "com/acme/EscapedNamesExample.hdbdd");
    List<EntitySymbol> sortedEntities = this.symbolTable.getSortedEntities();

    EntitySymbol school = sortedEntities.get(0);
    EntitySymbol student = sortedEntities.get(1);

    assertEquals("Escaped-Student", student.getName());
    assertNotNull(student.resolve("n@me!"));
    assertNotNull(student.resolve("from"));

    assertEquals("Escaped-School", school.getName());
    assertNotNull(((Typeable) school.resolve("name")).getType());
    assertNotNull(((Typeable) school.resolve("to")).getType());
  }

  @Test
  public void testParseNamespaceNotMatchingPackageName() throws Exception {
    try {
      parseSampleFile("/NamespaceNotMatchingPackageName.hdbdd", "com/acme/NamespaceNotMatchingPackageName.hdbdd");
    } catch (RuntimeException e) {
      assertEquals(CDSRuntimeException.class, e.getClass());
      assertEquals("Error at line: 1. Namespace does not match the file directory.", e.getMessage());
    }
  }

  @Test
  public void testParseEntitiesWithCatalogTableTypeAnnotations() throws Exception {
    CdsParser parsedFile = parseSampleFile("/CatalogTableTypes.hdbdd", "acme/table/CatalogTableTypes.hdbdd");
    List<EntitySymbol> parsedEntities = this.symbolTable.getSortedEntities();

    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
    assertEquals(4, parsedEntities.size());
  }

  @Test
  public void testParseViewsSuccessfully() throws Exception {
    CdsParser parsedFile = parseSampleFile("/ParseViews.hdbdd", "acme/db/ParseViews.hdbdd");
    List<ViewSymbol> parsedViews = this.symbolTable.getSortedViews();

    assertEquals(0, parsedFile.getNumberOfSyntaxErrors());
    assertEquals(4, parsedViews.size());
  }

  private CdsParser parseSampleFile(String sampleFileName, String location) throws Exception {
    String content =
        org.apache.commons.io.IOUtils.toString(
            ArtifactDefinitionListener.class.getResourceAsStream(sampleFileName),
            StandardCharsets.UTF_8);

    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    CdsLexer hdbddLexer = new CdsLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbddLexer);

    XSKHdbddErrorListener lexerErrorListener = new XSKHdbddErrorListener();
    hdbddLexer.removeErrorListeners(); // Remove the ConsoleErrorListener
    hdbddLexer.addErrorListener(lexerErrorListener);

    CdsParser hdbddParser = new CdsParser(tokenStream);
    hdbddParser.setBuildParseTree(true);
    XSKHdbddErrorListener parserErrorListener = new XSKHdbddErrorListener();
    hdbddParser.removeErrorListeners();
    hdbddParser.addErrorListener(parserErrorListener);

    ParseTree parseTree = hdbddParser.cdsFile();

    ArtifactDefinitionListener artifactDefinitionListener = new ArtifactDefinitionListener();
    artifactDefinitionListener.setSymbolTable(symbolTable);
    artifactDefinitionListener.setFileLocation(location);

    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(artifactDefinitionListener, parseTree);

    ReferenceResolvingListener referenceResolvingListener = new ReferenceResolvingListener();
    referenceResolvingListener.setSymbolTable(symbolTable);
    referenceResolvingListener.setEntityElements(artifactDefinitionListener.getEntityElements());
    referenceResolvingListener.setTypeables(artifactDefinitionListener.getTypeables());
    referenceResolvingListener.setAssociations(artifactDefinitionListener.getAssociations());

    try {
      parseTreeWalker.walk(referenceResolvingListener, parseTree);
    } catch (CDSRuntimeException e) {
      throw new CDSRuntimeException(String.format("Failed to parse file: %s. %s", location, e.getMessage()));
    }

    return hdbddParser;
  }
}