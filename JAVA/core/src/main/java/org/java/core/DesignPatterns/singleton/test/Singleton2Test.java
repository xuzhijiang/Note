package org.java.core.DesignPatterns.singleton.test;

import org.java.core.DesignPatterns.singleton.Singleton2;

public class Singleton2Test {
    public static void main(String[] args) {
        Singleton2 obj = Singleton2.INSTANCE;
        System.out.println(obj);
    }
}
