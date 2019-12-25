package org.java.core.advanced.jvm.StringTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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

    @Test
    public void testStringCollection() {
        String s1 = new String("abc");
        String s2 = new String("abc");

        System.out.println(s1 == s2);
        // 要么不覆写,要么hashcode和equals一起覆写
        System.out.println(s1.equals(s2));

        Set<String> set01 = new HashSet<>();
        set01.add(s1);
        set01.add(s2);
        System.out.println(s1.hashCode() + "\t " + s2.hashCode());
        System.out.println(set01.size());

        System.out.println("=====================================");
        Person p1 = new Person("abc");
        Person p2 = new Person("abc");
        System.out.println(p1 == p2);
        System.out.println(p1.equals(p2));
        Set<Person> set02 = new HashSet<>();
        set02.add(p1);
        set02.add(p2);
        System.out.println(p1.hashCode() + "\t " + p2.hashCode());
        System.out.println(set02.size());
    }

    // @Data
    @Getter
    @Setter
    @AllArgsConstructor
    private static class Person {
        private String personName;
    }

}
