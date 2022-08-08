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

package com.codbex.kronos.api.test.int64;
import com.codbex.kronos.api.int64.Int64;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Int64Test {
    long value = 9223372036854775802L;
    String hex = "7FFFFFFFFFFFFFFA";
    String valueAsString = "9223372036854775802";

    @Test
    public void joinTest() {
        long sum = Int64.join(-0x12345678, 0x90ABCDEF);
        System.out.println(sum);
        assertEquals(-1311768466735510033L, sum);
    }

    @Test
    public void compareTest() {
        Int64 a = new Int64("9223372036854775802");
        Int64 b = new Int64("7FFFFFFFFFFFFFFA", 16);
        int result = Int64.compare(a, b);
        assertEquals(0, result);
    }

    @Test
    public void getValueFromHEX() {
        Int64 number = new Int64(hex, 16);
        long numLow = Int64.getLow(number);
        long numHi = Int64.getHi(number);
        long join = Int64.join(numHi, numLow);
        assertEquals(number.getValue(), join);
    }

    @Test
    public void getValueFromStringPositive() {
        Int64 int64 = new Int64(value);
        long numLow = Int64.getLow(int64);
        long numHi = Int64.getHi(int64);
        long join = Int64.join(numHi, numLow);
        assertEquals(int64.getValue(), join);
    }


    @Test
    public void getValueFromStringNegative() {
        Int64 int64 = new Int64(valueAsString);
        long numLow = Int64.getLow(int64);
        int numHi = Int64.getHi(int64);
        long join = Int64.join(numHi, numLow);
        assertEquals(int64.getValue(), join);
    }
    @Test
    public void getValueFromInstance(){
      Int64 int64 = new Int64(valueAsString);
      Int64 newInstance = new Int64(int64);
      assertEquals(int64.getValue(),newInstance.getValue());
    }
}

