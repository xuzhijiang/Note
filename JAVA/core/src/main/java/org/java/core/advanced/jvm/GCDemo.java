package org.java.core.advanced.jvm;

import java.util.Random;
import java.util.UUID;

/**
 * 验证各种垃圾收集器
 *
 * 注意: 一般新生代配了垃圾收集器,不用再特别强调老年代配哪种垃圾收集器,老年代会自动选择相对应的.因为他们2个是相关联的.
 *
 * 假设young使用了Serial,那么老年代会自动使用Serial Old和young区遥相呼应,一一配合,所以young的垃圾回收机制很重要
 *
 * ------------------------------------新生代---------------------------------------------
 * 1. 串行GC (Serial)/(Serial Coping) :
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC  (DefNew+Tenured)
 * 我们设置了-XX:+UseSerialGC,所以DefNew(default new generation),新生代使用默认的串行垃圾回收器
 * Tenured意思是老年代使用的是Serial Old.
 * 效果: Serial(young区使用) + Serial Old(old区使用)
 * 注意这个组合现在工作中也不会用了,知道就好
 * young(复制算法) + old(标记整理算法)
 *
 *
 * 2. 并行GC(ParNew) : -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC  (ParNew+Tenured)
 * 效果: ParNew(young区用) + Serial Old(Old区用)
 * young(复制算法) + old(标记整理算法)
 * 注意这个jvm会有警告信息,意思是ParNew(young区用)不再建议和Serial Old(Old区用)一起使用,
 * 如果配置young区使用ParNew,那么old区默认是使用Serial Old,但是不建议这么用了,
 * 而是ParNew要和CMS一起使用
 *
 *
 * 3. 并行回收GC (Parallel Scavenge)/(Parallel Old) :
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC (PSYoungGen+ParOldGen)
 * 效果: PSYoungGen (Parallel Scavenge) + ParOldGen (Parallel Old)
 *      -XX:+UseParallelGC 这个也是java8默认采用的配置
 * ------------------------------------新生代---------------------------------------------
 *
 * ------------------------------------老年代---------------------------------------------
 * 4. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC (PSYoungGen+ParOldGen)
 *
 * 5. 默认什么都不配置垃圾收集器:  -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags  (PSYoungGen+ParOldGen)
 *
 * 6. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC (ParNew + CMS)
 * young(ParNew) + old(CMS)
 * 第一步: CMS Initial Mark 第二步: concurrent-mark-start
 * 第三步: CMS Final Remark 第四步: concurrent-sweep-start
 *
 * 7. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialOldGC
 * (Serial Old java8中已经被优化掉了,理论知道即可)
 * ------------------------------------老年代---------------------------------------------
 *
 * 8. 下面是故意这么繁琐的配置,主要是为了学习,生产过程中不这么配置一般,
 * 因为配2个和配一个效果一样,因为是关联激活型类型的参数,也就是指定了young区使用的垃圾收集器之后,老年代的也就确认了一般
 *
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC -XX:+UseParallelOldGC (PSYoungGen + ParOldGen)
 *
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC -XX:+UseConcMarkSweepGC (ParNew + CMS)
 *
 * 9. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
 */
public class GCDemo {

    public static void main(String[] args) {
        // byte[] byteArray = new byte[30 * 1024 * 1024];
        String str = "aaaaaaaa";
        while (true) {
            str += str + new Random().nextInt(88888888) + new Random().nextInt(99999999);
        }
    }
}
