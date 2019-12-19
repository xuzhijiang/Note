package org.java.core.advanced.jvm.StringTable;

public class StringTableDemo02 {

    /**
     * 反编译的结果: StringTableDemo02反编译信息.md
     */
    public static void main(String[] args) {
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab"; // 在串池(StringTable)中
        String s4 = s1 + s2; // new StringBuilder().append("a").append("b").toString() => new String("ab")

        // javac 在编译期间的优化,它会认为你a和b都是常量,他们的内容不会变了,所以a和b拼接的结果是确定的,既然
        // 是确定的,在编译期间就知道s5肯定是ab了
        // 而s4和s5的不同之处是: s4中的s1和s2是变量,既然是变量,就有可能修改,
        // 所以只能通过StringBuilder在运行期间动态拼接修改
        // s5就不同了,因为在编译期间已经确定为ab了,就不需要通过StringBuilder在运行期间动态拼接了.
        // 这个就是常量字符串底层拼接的原理
        String s5 = "a" + "b";



        // StringBuilder的toString方法:
        /*
            @Override
            public String toString() {
                // Create a copy, don't share the array
                // 创建的是一个新的字符串对象
                return new String(value, 0, count);
            }
        * */

        // 根据上面StringBuilder的toString方法,可以看到是创建了一个值为ab的新的字符串对象,然后存入s4
        // new String("ab")是一个新的字符串对象,虽然和s3的值相同,但是s4存放在堆中
        // System.out.println(s3 == s4); // false

        // System.out.println(s3 == s5);// true
    }

}
