package org.netty.core.DirectByteBuffer;

/*
public class Bits {

    static void reserveMemory(long size, int cap) {

        //如果直接有足够多的直接内存可以用，直接增加直接内存引用的计数
        synchronized (Bits.class) {
            if (!memoryLimitSet && VM.isBooted()) {
                maxMemory = VM.maxDirectMemory();
                memoryLimitSet = true;
            }
            // -XX:MaxDirectMemorySize limits the total capacity rather than the
            // actual memory usage, which will differ when buffers are page
            // aligned.
            if (cap <= maxMemory - totalCapacity) {//维护已经使用的直接内存的数量
                reservedMemory += size;
                totalCapacity += cap;
                count++;
                return;
            }
        }
        //如果没有有足够多的直接内存可以用，先进行垃圾回收
        System.gc();
        try {
            Thread.sleep(100);//休眠100秒，等待垃圾回收完成
        } catch (InterruptedException x) {
            // Restore interrupt status
            Thread.currentThread().interrupt();
        }
        synchronized (Bits.class) {//休眠100毫秒后，增加直接内存引用的计数
            if (totalCapacity + cap > maxMemory)
                throw new OutOfMemoryError("Direct buffer memory");
            reservedMemory += size;
            totalCapacity += cap;
            count++;
        }

    }
    //释放内存时，减少引用直接内存的计数
    static synchronized void unreserveMemory(long size, int cap) {
        if (reservedMemory > 0) {
            reservedMemory -= size;
            totalCapacity -= cap;
            count--;
            assert (reservedMemory > -1);
        }
    }

}
*/