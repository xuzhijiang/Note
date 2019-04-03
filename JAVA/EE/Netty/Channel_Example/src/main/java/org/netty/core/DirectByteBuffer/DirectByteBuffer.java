package org.netty.core.DirectByteBuffer;

/*
class DirectByteBuffer extends MappedByteBuffer  implements DirectBuffer
{
    ....
    //构造方法
    DirectByteBuffer(int cap) {                   // package-private

        super(-1, 0, cap, cap);
        boolean pa = VM.isDirectMemoryPageAligned();
        int ps = Bits.pageSize();
        long size = Math.max(1L, (long)cap + (pa ? ps : 0));//对申请的直接内存大小，进行重新计算
        Bits.reserveMemory(size, cap);

        long base = 0;
        try {
        // 可以看到构造方法中的确是用了unsafe.allocateMemory方法帮我们分配了直接内存，
            base = unsafe.allocateMemory(size); //分配直接内存，base表示的是直接内存的开始地址
        } catch (OutOfMemoryError x) {
            Bits.unreserveMemory(size, cap);
            throw x;
        }
        unsafe.setMemory(base, size, (byte) 0);
        if (pa && (base % ps != 0)) {
            // Round up to page boundary
            address = base + ps - (base & (ps - 1));
        } else {
            address = base;
        }

     //另外，在构造方法的最后，通过 Cleaner.create方法注册了一个钩子函数，用于清除直接内存的引用。
     // 我们可以看到在DirectByteBuffer的最后一行中，传入的这两个参数分别是this，
     // 和一个Deallocator(实现了Runnable接口)，其中this表示就是当前DirectByteBuffer实例，
     // 也就是当前DirectByteBuffer被GC回收的时候，回调Deallocator的run方法
     //Deallocator就是用于清除DirectByteBuffer引用的直接内存
    cleaner = Cleaner.create(this, new Deallocator(base, size, cap));//注册钩子函数，释放直接内存
    att = null;

    }
      ....
}
*/