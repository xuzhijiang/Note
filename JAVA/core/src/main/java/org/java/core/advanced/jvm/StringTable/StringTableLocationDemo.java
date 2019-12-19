package org.java.core.advanced.jvm.StringTable;

import java.util.ArrayList;
import java.util.List;

/**
 * StringTable在java6/java7/java8中的位置,结论:
 * java6: StringTable存放在永久代
 * java7: 存放在堆中
 * java8: 存放在堆中
 */
public class StringTableLocationDemo {
    /**
     * 演示StringTable 位置
     * 在jdk8下设置: -Xmx10m -XX:-UseGCOverheadLimit
     * jdk6下设置: -XX:MaxPermSize=10m
     *
     * 结果:
     * jdk6: (会引发: java.lang.OutOfMemory: PermGen space), 验证了java6: StringTable存放在永久代
     *
     * jdk8: 如果只添加-Xmx10m,会引发java.lang.OutOfMemoryError: GC overhead limit exceeded,
     * 这个错误的意思是 当UseGCOverheadLimit这个参数是是打开的时候,如果超过98%的时间花在了垃圾回收上
     * 只有2%的堆空间可以被回收,这个时候就会抛GC overhead limit exceeded这个错误.
     * 我们可以把这个开关关掉-XX:-UseGCOverheadLimit,这样就不会报GC overhead limit exceeded这个错误.
     * 此时会变成这个错误: java.lang.OutOfMemoryError: Java heap space
     */
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int count =0;
        try {
            for (int i = 0; i < 260000; i++) {
                list.add(String.valueOf(i).intern());
                count++;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println("count***********" + count);
        }
    }
}
