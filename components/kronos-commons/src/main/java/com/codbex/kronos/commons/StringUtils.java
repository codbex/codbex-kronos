package com.codbex.kronos.commons;

import java.nio.charset.StandardCharsets;

public final class StringUtils {

    private StringUtils() {}

    public static String toString(byte[] array) {
        return null == array ? null : new String(array, StandardCharsets.UTF_8);
    }
}
