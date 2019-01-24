package org.java.core.advanced.jvm.classloader;

/**
 *  Final字段不会被引起初始化
 */
public class UseFinalField {
    public static void main(String[] args) {
        System.out.println(FinalFieldClass.CONST_STR);
    }
}

class FinalFieldClass {
    public static final String CONST_STR = "CONSTSTR";

    static {
        System.out.println("FinalFieldClass init");
    }
}