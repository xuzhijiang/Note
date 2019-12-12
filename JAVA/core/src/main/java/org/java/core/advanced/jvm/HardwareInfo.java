package org.java.core.advanced.jvm;

import org.junit.Test;

/**
 * 打印机器的硬件信息
 */
public class HardwareInfo {

    @Test
    public void getAvailableProcessors() {
        // java把运行时数据区抽象成了Runtime这个类
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("处理器的个数: " + availableProcessors);
    }

}
