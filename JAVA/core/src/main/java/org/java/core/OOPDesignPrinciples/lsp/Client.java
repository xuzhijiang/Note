package org.java.core.OOPDesignPrinciples.lsp;

/**
 * 里氏替换原则(LSP)
 */
public class Client {
    public static void main(String[] args) {
        A a = new A();
        System.out.println("8 - 5 = " + a.subtract(8, 5));

        B b = new B();
        System.out.println("8 - 5 = " + b.subtract(8, 5));
    }
}
