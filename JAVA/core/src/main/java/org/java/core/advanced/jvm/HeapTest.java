package org.java.core.advanced.jvm;

import java.util.ArrayList;

// 使用jdk自带的jvisualvm,直接cmd里面输入即可启动
// jvisualvm这个plugin默认没有安装，需要我们安装
// 参考：https://blog.csdn.net/shuai825644975/article/details/78970371
public class HeapTest {

    byte[] a = new byte[1024 * 100]; // 100KB

    public static void main(String[] args) throws InterruptedException {
        ArrayList<HeapTest> heapTests = new ArrayList<>();
        while (true) {
            // 每new HeapTest()，堆里面至少会被分配100KB内存
            // 这些对象最终都会在堆里面存活，最终会被挪到老年代
            heapTests.add(new HeapTest());
            Thread.sleep(20);
        }
    }

}
