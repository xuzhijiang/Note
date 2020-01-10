package org.java.core.OOPDesignPrinciples.lsp;

/**
 * B类违反了里氏替换原则,subtract是父类的方法,功能是减法,但是B类却改成了加法.
 * B类改变了父类定义的方法的功能.
 */
public class B extends A{
    @Override
    public int subtract(int a, int b) {
        return a + b;
    }
}
