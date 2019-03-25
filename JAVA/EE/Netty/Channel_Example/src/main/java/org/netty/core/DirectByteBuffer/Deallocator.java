package org.netty.core.DirectByteBuffer;

import oracle.jrockit.jfr.events.Bits;
import sun.misc.Unsafe;

/*
private static class Deallocator implements Runnable
{

    private static Unsafe unsafe = Unsafe.getUnsafe();

    private long address;
    private long size;
    private int capacity;

    private Deallocator(long address, long size, int capacity) {
        assert (address != 0);
        this.address = address;
        this.size = size;
        this.capacity = capacity;
    }

// 可以看到run方法中调用了unsafe.freeMemory方法释放了直接内存的引用。
    public void run() {
        if (address == 0) {
            // Paranoia
            return;
        }
        unsafe.freeMemory(address);//清除直接内存
        address = 0;
        Bits.unreserveMemory(size, capacity);
    }

}
*/