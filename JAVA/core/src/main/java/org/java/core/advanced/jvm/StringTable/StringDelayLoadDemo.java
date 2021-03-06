package org.java.core.advanced.jvm.StringTable;

/**
 * 验证: 字符串的延迟加载
 */
public class StringDelayLoadDemo {
    public static void main(String[] args) {
        int x = args.length;
        System.out.println(); // 字符串个数 2571
        // 在第9行打断点,然后通过idea的debug,可以看到java.lang.String创建的对象的数量
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
        System.out.println("6");
        System.out.println("7");
        System.out.println("8");
        System.out.println("9");
        System.out.println("0");
        // 在20行断点
        System.out.println("1");  // 字符串个数 2581
        // 可以证明不是一下子把这么多字符串对象全部放入串池StringTable,而是执行一行代码,遇到一个没见过的字符串对象,才会放入串池
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
        System.out.println("6");
        System.out.println("7");
        System.out.println("8");
        System.out.println("9");
        System.out.println("0");
        System.out.println(x); // 字符串的个数  2581

        // 这句话创建了几个对象？,在36打个断点
        String s1 = new String("xxxooooxxxoo");
        // 答; 创建了两个对象,先有字符串"xxxooooxxxoo"放入字符串常量池，
        // 然后 new 了一个字符串对象"xxxooooxxxoo"放入堆中
        // (字符串常量"xxxooooxxxoo"在编译期就已经确定放入字符串常量池，而堆上的字符串对象是在运行时才确定
        String s2 = "xxxooooxxxoo"; // 看看String的个数变化没有 (答: 没有,上面new的时候,StringTable中的xxxooooxxxoo就已经被创建好了)
        System.out.println(s1 == s2); // false
    }
}
