package org.java.core.base.ObjectClass.ToStringMethod;

public class ToStringMethodExample {

    public static void main(String[] args) {

    }
}

/**
 * 展示Object中的源码
 */
class MyObject {

    /**
     * 如果子类没有重写toString()方法，当我们打印一个对象的引用时，
     * 实际调用的就是Object类中的toString()方法。输出此对象所在类及对应的堆空间对象的首地址
     * @return
     */
    public String toString() {
        // 把hashCode转换成16进制的字符串.
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    // 像String类，File类，Date类，包装类都重写了toString()方法
}

class Cat {

    String color;

    String type;

    /**
     * 如果子类重写了toString()方法，那么打印一个对象引用时,实际上调用的就是当前对象的toString()方法
     * @return
     */
    @Override
    public String toString() {
        return "Cat [color=" + color + ", type=" + type + "]";
    }

}