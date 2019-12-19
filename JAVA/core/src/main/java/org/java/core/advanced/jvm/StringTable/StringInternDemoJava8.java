package org.java.core.advanced.jvm.StringTable;

/**
 * jdk8的String intern示例
 */
public class StringInternDemoJava8 {
    public static void main(String[] args) {
        test01();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("**********************");
        test02();
    }

    private static void test01() {
        // StringBuilder拼接a和b => new String("ab") ,这个new String("ab")仅仅存在于堆中,并没有存在于串池中
        // 串池中存放的是常量, 动态拼接的字符串是存放在堆中,并没有存放在StringTable串池中
        String s = new String("a") + new String("b");

        // 如何把动态拼接的"ab"字符串对象 存放到串池StringTable中?
        // 将字符串对象尝试放入串池,如果串池中已经有ab字符串对象了,则不会再放入,如果串池中没有则放入串池
        // 不管串池中有没有,都会把串池StringTable中的ab字符串对象返回回来,赋值给s2
        // 把ab对象放入到串池之后,s也就指向了串池中的ab
        String s2 = s.intern();

        System.out.println(s2 == "ab"); // true
        System.out.println(s == "ab"); // true
    }

    private static void test02() {
        String x = "ab";
        // StringBuilder拼接a和b => new String("ab") ,这个new String("ab")仅仅存在于堆中,并没有存在于串池中
        // 串池中存放的是常量, 动态拼接的字符串是存放在堆中,并没有存放在StringTable串池中
        String s = new String("a") + new String("b");

        // StringTable中已经有了ab,就没有把ab字符串对象放进StringTable中去,所以s还是引用堆中的ab字符串对象
        // s2 引用了常量池中的ab
        String s2 = s.intern();

        System.out.println(s2 == x); // true
        System.out.println(s == x); // false
    }

}
