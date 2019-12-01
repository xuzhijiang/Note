package org.java.core.base.generics;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {

    @Test
    public void testGenericType() {
        GenericsType<String> type = new GenericsType<>("xxxx");

        System.out.println(type.get());
    }

    @Test
    public void testGenericMethod() {
        GenericsType<String> g1 = new GenericsType<>("aaaa");
        GenericsType<String> g2 = new GenericsType<>("aaa");

        System.out.println(GenericMethod.isEqual(g1, g2));
    }

    @Test
    public void testGenericWildcard() {
        GenericWildcard genericsWildcards = new GenericWildcard();
        List<Integer> ints = new ArrayList<>();
        ints.add(3); ints.add(5); ints.add(10);
        System.out.println("Sum: "+genericsWildcards.sum(ints));

        List<Number> listNumber = new ArrayList<>();
        genericsWildcards.addInteger(listNumber);
        System.out.println("listNumber: " + listNumber);

        List<Object> listObject = new ArrayList<>();
        genericsWildcards.addInteger(listObject);
    }
}
