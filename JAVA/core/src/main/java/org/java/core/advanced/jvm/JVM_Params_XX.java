package org.java.core.advanced.jvm;

/**
 * jvm参数中的XX参数下的Boolean类型,这里以PrintGCDetails和MetaspaceSize为例
 *
 * jps -l 获得进程号
 * jinfo -flag PrintGCDetails 进程号
 * jinfo -flag MetaspaceSize 进程号
 * jinfo -flags 进程号
 * (查看所有的相关参数,打印出的内容分为2部分,Non-default VM flags:为jvm启动默认的参数,Command line:为用户自己指定的)
 *
 * jinfo -flag ThreadStackSize pid
 */
public class JVM_Params_XX {

    // VM Options: -XX:+PrintGCDetails -XX:MetaspaceSize=128m -Xss128k -Xms128m -Xmx1024m -XX:MetaspaceSize=512m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseSerialGC
    public static void main(String[] args) throws InterruptedException {
        System.out.println("***********JVM_Params_XX_Boolean");

//        Thread.sleep(Integer.MAX_VALUE);

        // -XX:+PrintGCDetails -Xms10m -Xmx10m
        byte[] byteArray = new byte[50 * 1024 * 1024];
        // 由于GC垃圾回收后,没有足够的内存,所以内存分配失败
        // [GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] 739K->739K(9728K), 0.0003582 secs]
        // [Times: user=0.00 sys=0.00, real=0.00 secs]

        // 由于Full GC后,没有足够的内存,所以内存分配失败,full gc 老年代都扛不住了,会报我们的oom
        //[Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 739K->721K(7168K)] 739K->721K(9728K), [Metaspace: 3289K->3289K(1056768K)], 0.0084914 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
        //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        //	at org.java.core.advanced.jvm.JVM_Params_XX.main(JVM_Params_XX.java:23)
    }
}
