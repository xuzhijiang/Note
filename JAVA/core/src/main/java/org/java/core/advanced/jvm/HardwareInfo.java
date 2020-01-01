package org.java.core.advanced.jvm;

import org.junit.Test;

/**
 * 打印机器的硬件信息
 */
public class HardwareInfo {

    @Test
    public void getAvailableProcessors() {
        // java把运行时数据区抽象成了Runtime这个类
        int availableProcessors = Runtime.getRuntime().availableProcessors(); // 返回逻辑核数
        System.out.println("逻辑核的个数: " + availableProcessors);
        // 如果逻辑核数和cpu cores相等,说明不支持超线程技术.(也就是看siblings和cpu cores值是否相等)
        // 我自己的电脑是支持超线程技术的,所以逻辑核数为4,cpu cores为2
    }

}
