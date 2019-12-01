package org.java.core.advanced.jvm;

/**
 * 堆内存调优
 *
 * 默认的情况下分配的内存是总内存的“1 / 4”、而初始化的内存为“1 / 64”
 */
public class HeadAdjustDemo {

    // 教学环境可以随便配置
    // 生产环境下,Xms和Xmx必须要调整成一致的,防止jvm堆内存忽高忽低,避免gc和应用程序争抢内存.
    // -Xms1024m -Xmx1024m -XX:+PrintGCDetails
    public static void main(String[] args) {

        // maxMemory等价于Xmx参数,如果你是16G的内存,大概只有15.9G是可用的
        // jvm的最大分配内存,默认为物理内存的1/4(大概是3600MB),也就是jvm堆内存能使用的最大内存上限 ----> Xmx
        // 我不调,默认3618MB就是你堆内存的上限
        long maxMemory = Runtime.getRuntime().maxMemory();// 返回java虚拟机试图使用的最大内存量

        // Xms 代表jvm堆内存的初始大小,默认为物理内存的1/64
        // totalMemory就相当于Xms
        long totalMemory = Runtime.getRuntime().totalMemory(); // 返回java虚拟机中的内存总量

        System.out.println("Xmx ----> max memory = " + maxMemory + "(字节)," + (maxMemory/(double)1024/1024) + "MB");
        System.out.println("Xms ---->total memory = " + totalMemory + "(字节)," + (totalMemory/(double)1024/1024) + "MB");

        // freeMemory: 空闲内存,在totalMemory基础之上,减去使用掉的,剩下的就是空闲内存
        long freeMemory = Runtime.getRuntime().freeMemory();
        System.out.println("jvm空闲内存: " + (freeMemory / 1024 / 1024) + "MB");
    }
}
