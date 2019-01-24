package org.java.core.advanced.HeapStack;

/**
 * 打印堆内存
 */
public class JvmXmxArgs {
	
	// input: java -Xmx33m org.jvmcore.heap.JvmXmxArgs a b
	//打印始终小于 33M , 因为GC在不同区域采用不同回收算法，可用内存的减少为了其使用空间换时间的策略。
    public static void main(String[] args) {
        for (String arg : args)
            System.out.println("参数为" + arg);
        
        // 堆内存
        System.out.println("-Xmx:" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");
    }
}
