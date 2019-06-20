package org.java.core.advanced.jvm.classloader;

public class UseFinalField {
    public static void main(String[] args) {
        System.out.println(FinalFieldClass.CONST_STR);
    }
}

class FinalFieldClass {
    public static String CONST_STR = "CONSTSTR";

    /**
     * 如果使用了 final 修饰的常量，使用时,FinalFieldClass类不会初始化(也就是不调用static中的代码)
     */
    // public static final String CONST_STR = "CONSTSTR";

    static { // FinalFieldClass.class的初始化过程
        System.out.println("FinalFieldClass init");
    }
}