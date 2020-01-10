package org.java.core.DesignPatterns.singleton.test;

import org.java.core.DesignPatterns.singleton.Singleton7;

public class Singleton7Test {
    public static void main(String[] args) {
        Singleton7.method();

        try { Thread.sleep(3000L); } catch (InterruptedException e) { e.printStackTrace();}

        System.out.println("*************");
        Singleton7 s = Singleton7.getInstance();
        System.out.println(s);
    }
}
