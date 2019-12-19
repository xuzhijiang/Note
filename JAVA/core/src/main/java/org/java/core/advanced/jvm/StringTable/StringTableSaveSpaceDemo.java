package org.java.core.advanced.jvm.StringTable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * -Xms500m -Xmx500m -XX:+PrintStringTableStatistics -XX:StringTableSize=200000
 * 结合VisualVM来分析
 */
public class StringTableSaveSpaceDemo {

    public static String file = "C:\\Users\\xu\\Desktop\\dict\\DICT.TXT";

    // 值相同的重复的字符串对象,intern()之后,只会在串池StringTable中存储一份,这样可以减少字符串对内存的占用
    // 这个示例演示了有大量的字符串对象被创建,入池与不入池它的前后对内存占用的对比
    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();

        // 按任何键开始执行,在之前之前连接VisualVM,通过VisualVM的抽样其(sampler)来查看内存的变化
        System.in.read();

        // 同一个文件读取10次,读取到list中,也就是dict.txt文件中的每个单词,至少有10次重复
        for (int i = 0; i < 10; i++) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8.name()))) {
                String line = null;
                long start = System.nanoTime(); // 纳秒
                while (true) {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    // 把入池和不入池的内存占用进行对比
                    // 第一种情况: 不入池,将line放入到List中,防止被垃圾回收,循环10次,大概有420w个单词存放到List中
                    list.add(line);

                    // 第二种情况: 读到的line入池,line.intern()入池之后,返回的是串池中的字符串对象,串池外的字符串对象没有被添加到list,所以就可以被垃圾回收掉
                    // 串池外的会被垃圾回收掉,串池中的因为有list引用着,不会被回收
//                    list.add(line.intern());
                }
                System.out.println("count: " + (System.nanoTime() - start) / 1000000 + "毫秒");
            }
        }
        System.in.read();
    }
}
