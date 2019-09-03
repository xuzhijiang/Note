package org.java.core.base.util;

import org.junit.Test;

import java.util.Objects;

public class ObjectsUtils {

    @Test
    public void testGetHashCode() {
        Object obj = new Object();
        // Objects类是1.7引入
        int i = Objects.hashCode(obj);
        System.out.println(i);
    }
}
