package org.java.core.advanced.jvm.StringTable;

public class StringTableDemo01 {

    /**
     * 常见StringTable面试题
     * 需要从字节码和常量池的角度去分析底层原理
     *
     * 通过javap -v xx.class 反编译查看xx.class的常量池长什么样子
     * 反编译的结果: StringTableDemo01反编译信息.md
     *
     * StringTable ["a",   "b",   "ab"] hashtable 结构,不能扩容
     *
     * 常量池中的信息,都会被加载到运行时常量池中,这时a,b,ab,都是常量池中的符号,还没有变为 java 字符串对象
     * // ldc #2 会把 a 符号变为 "a" 字符串对象
     * // ldc #3 会把 b 符号变为 "b" 字符串对象
     * // ldc #4 会把 ab 符号变为 "ab" 字符串对象
     */
    public static void main(String[] args) {
        // 懒惰的行为(第一点: 执行到具体代码才创建具体的字符串对象 第二点: 创建好字符串对象之后,会把它放入StringTable串池)
        // 以后会现在串池中找,串池中有了就直接使用,没有再创建,串池中的字符串对象只会存在一份.
        String s1 = "a"; // 直接采用 "a" 这种字面量的形式创建字符串，会自动地将字符串放入 StringTable串池 中.
        String s2 = "b";
        String s3 = "ab";
    }

}
