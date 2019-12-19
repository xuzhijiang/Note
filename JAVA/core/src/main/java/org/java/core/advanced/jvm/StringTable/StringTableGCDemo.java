package org.java.core.advanced.jvm.StringTable;

/**
 * 演示 StringTable 垃圾回收
 * -Xmx10m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails -verbose:gc
 *
 * -XX:+PrintStringTableStatistics: 打印字符串表的统计信息,通过这个参数可以看到StringTable中字符串的个数,
 * 以及占用的大小信息
 *
 * -XX:+PrintGCDetails -verbose:gc: 打印垃圾回收的详细信息
 */
public class StringTableGCDemo {
    public static void main(String[] args) {
        int j = 0;
        try {
            for (int i = 0; i < 100; i++) { // i=10000,就会触发垃圾回收,把没有人引用的字符串对象给回收了,通过修改i,证明StringTable也是可以被垃圾回收的
                // 把字符串对象入池
                // 通过StringTable statistics:可以看到字符串常量的个数增加了100个
                String.valueOf(i).intern();
                j++;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(j);
        }
    }
}

/*
// StringTable的统计信息,StringTable底层是类似于哈希表的结构
StringTable statistics:
// 桶的个数
Number of buckets       :     60013 =    480104 bytes, avg   8.000
// 条目的个数(键值对的个数)
Number of entries       :      1702 =     40848 bytes, avg  24.000
// 字符串常量的个数
Number of literals      :      1702 =    153840 bytes, avg  90.388
// StringTable总的占用空间
Total footprint         :           =    674792 bytes
Average bucket size     :     0.028
Variance of bucket size :     0.028
Std. dev. of bucket size:     0.169
Maximum bucket size     :         2
* */
