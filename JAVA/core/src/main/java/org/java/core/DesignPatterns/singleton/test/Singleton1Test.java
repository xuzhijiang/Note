package org.java.core.DesignPatterns.singleton.test;

import org.java.core.DesignPatterns.singleton.Singleton1;

public class Singleton1Test {
    public static void main(String[] args) {
        Singleton1 obj = Singleton1.INSTANCE;
        System.out.println(obj);
    }
}
