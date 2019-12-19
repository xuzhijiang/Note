package org.java.core.advanced.jvm.StringTable;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * StringTable的性能调优
 * 通过打印PrintStringTableStatistics信息可以看到,bucket默认为: 60013
 *
 * -XX:StringTableSize=200000 -XX:+PrintStringTableStatistics (StringTable的桶个数为20w)
 * -XX:StringTableSize=1009 -XX:+PrintStringTableStatistics (StringTable的桶个数为1009)
 */
public class StringTableOptimization {

    // 大概42w个单词,如果bucket个数为20w,要把这42w个单词分散到20w个桶中,也就是每个桶大概存放2个词,所以效率非常快
    // 修改桶大小为1009,明显看到速度变慢了
    // 词典下载: http://lastbit.com/dict.asp
    public static String file = "C:\\Users\\xu\\Desktop\\dict\\DICT.TXT";

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8.name()))) {
            String line = null;
            long start = System.nanoTime(); // 纳秒
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                // 读到的内容入池
                line.intern();
            }
            System.out.println("count: " + (System.nanoTime() - start) / 1000000 + "毫秒");
        }
    }
}
