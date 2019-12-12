package org.java.core.advanced.jvm;

/**
 * 生产环境服务器变慢了,诊断思路和性能评估
 *
 * 将这个程序运行在linux下进行练习
 */
public class ProdServerSlowDemo {
    public static void main(String[] args) {
        while (true) {
            System.out.println(new java.util.Random().nextInt(77778888));
        }
    }
}
