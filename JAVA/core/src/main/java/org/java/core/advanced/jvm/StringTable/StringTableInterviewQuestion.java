package org.java.core.advanced.jvm.StringTable;

public class StringTableInterviewQuestion {

    /**
     * 常见StringTable面试题
     * 需要从字节码和常量池的角度去分析底层原理
     */
    public static void main(String[] args) {
        String s1 = "a";
        String s2 = "b";
        String s3 = "a" + "b"; // javac编译器优化
        String s4 = s1 + s2;
        String s5 = "ab";
        String s6 = s4.intern();

        // 问
        System.out.println(s3 == s4); // false
        System.out.println(s3 == s5); // true
        System.out.println(s3 == s6); // true

        String x2 = new String("c") + new String("d");
        String x1 = "cd";
        x2.intern();

        // 问,如果调换了[最后两行代码]的位置呢?如果是jdk1.6呢
        System.out.println(x1 == x2);
    }

}
