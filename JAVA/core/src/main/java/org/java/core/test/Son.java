package org.java.core.test;

public class Son extends Parent{

    private final char[] value;

    public Son(String val) {
        super(20);
        value = new char[]{'a', 'b'};
    }

    public void test () {
        this.age = 40;
        value[0] = 'd';
    }
}
