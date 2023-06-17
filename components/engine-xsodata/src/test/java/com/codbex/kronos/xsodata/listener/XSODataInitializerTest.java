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
package com.codbex.kronos.xsodata.listener;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.core.edm.provider.EdmNamedImplProv;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * The Class XSODataInitializerTest.
 */
public class XSODataInitializerTest {

  /** The xso data initializer. */
  private XSODataInitializer xsoDataInitializer;

  /**
	 * Sets the up.
	 */
  @BeforeEach
  public void setUp() {
    xsoDataInitializer = new XSODataInitializer();
  }

  /**
	 * Test when validation pattern is patched names can have dot.
	 *
	 * @throws EdmException the edm exception
	 */
  @Test
  public void testWhenValidationPatternIsPatchedNamesCanHaveDot() throws EdmException {
    xsoDataInitializer.contextInitialized(null);

    var expectedName = "test.name";
    var dummyEdmNamedImplProv = new DummyEdmNamedImplProv(expectedName);

    assertEquals("test.name", dummyEdmNamedImplProv.getName(), "Unexpected name");
  }

  /**
	 * Test when validation pattern is not patched names can not have dot.
	 *
	 * @throws EdmException the edm exception
	 */
  @Test
  public void testWhenValidationPatternIsNotPatchedNamesCanNotHaveDot() throws EdmException {
    assertThrows(EdmException.class, () -> new DummyEdmNamedImplProv("test.name"));
  }

  /**
	 * Test when validation patcher throws exception new illegal state exception is
	 * thrown.
	 *
	 * @throws ReflectiveOperationException the reflective operation exception
	 */
  @Test
  public void testWhenValidationPatcherThrowsExceptionNewIllegalStateExceptionIsThrown() throws ReflectiveOperationException {
    var odataInternalValidationPatternPatcherMock = Mockito.mock(ODataNamesValidationPatternPatcher.class);
    doThrow(ReflectiveOperationException.class).when(odataInternalValidationPatternPatcherMock).patch();
    FieldUtils.writeField(xsoDataInitializer, "odataNamesValidationPatternPatcher",odataInternalValidationPatternPatcherMock,true);

    var illegalStateException = assertThrows(
        IllegalStateException.class,
        () -> xsoDataInitializer.contextInitialized(null)
    );

    assertEquals(
        "Failed to replace default Olingo OData parameter name pattern.",
        illegalStateException.getMessage(),
        "Unexpected exception message"
    );
    MatcherAssert.assertThat(
        "Unexpected exception cause",
        illegalStateException.getCause(),
        instanceOf(ReflectiveOperationException.class)
    );
  }

  /**
	 * The Class DummyEdmNamedImplProv.
	 */
  class DummyEdmNamedImplProv extends EdmNamedImplProv {

    /**
	 * Instantiates a new dummy edm named impl prov.
	 *
	 * @param name the name
	 * @throws EdmException the edm exception
	 */
    public DummyEdmNamedImplProv(String name) throws EdmException {
      super(null, name);
    }
  }
}