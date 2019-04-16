package org.java.core.base.ObjectClass;

/**
 * Java中的Object类是所有类的父类，它提供了以下11个方法:
 *
 * public final native Class<?> getClass()
 * public native int hashCode()
 * public boolean equals(Object obj)
 * protected native Object clone() throws CloneNotSupportedException
 * public String toString()
 * public final native void notify()
 * public final native void notifyAll()
 * public final native void wait(long timeout) throws InterruptedException
 * public final void wait(long timeout, int nanos) throws InterruptedException
 * public final void wait() throws InterruptedException
 * protected void finalize() throws Throwable {}
 *
 *
 */
public class ObjectMain {

    public static void main(String[] args){

        testGetClass();

    }

    private static void testGetClass() {
        // getClass方法是一个final方法，不允许子类重写，并且也是一个native方法。

        // getClass()返回当前运行时对象的Class对象，注意这里是运行时，
        // 比如以下代码中n是一个Number类型的实例，但是java中数值默认是Integer类型，
        // 所以getClass方法返回的是java.lang.Integer：

        System.out.println("111111---------" + "str".getClass());// class java.lang.String
        System.out.println("222222---------" + ("str".getClass() == String.class));// true

        Number n = 0;
        Class<? extends Number> c = n.getClass();
        System.out.println("Number n: " + c);// class java.lang.Integer

        // 得到的是ObjectMain.class，然后转成toString输出
        System.out.println("33333333-----------" + new ObjectMain().getClass());
        // true
        System.out.println("44444-------" + (new ObjectMain().getClass() == ObjectMain.class));

    }
}
