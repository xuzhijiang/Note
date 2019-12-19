package org.java.core.advanced.jvm.StringTable;

/**
 * jdk6的String intern()示例
 */
public class StringInternDemoJava6 {
    public static void main(String[] args) {
        test01();
    }

    private static void test01() {
        String s = new String("a") + new String("b");

        // 如何把动态拼接的"ab"存放到串池中? intern方法,jdk6和jdk8有差异
        // jdk6的做法:
        // 将字符串对象尝试放入串池,如果串池中已经有ab字符串对象,则不会再放入,如果串池中没有,
        // 则将堆中的ab字符串对象拷贝一份,放入到串池中,
        // 不管串池中有没有ab字符串对象,intern()都会把串池StringTable中的ab字符串对象返回,赋值给s2
        String s2 = s.intern();

        System.out.println(s2 == "ab"); // true
        // 把ab对象拷贝一份放入到串池之后,s依然指向堆中的对象
        System.out.println(s == "ab"); // false
    }
}
