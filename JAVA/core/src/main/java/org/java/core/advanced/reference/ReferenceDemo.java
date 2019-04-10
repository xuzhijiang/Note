package org.java.core.advanced.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class ReferenceDemo {
    private static ReferenceQueue<BigFile> referenceQueue = new ReferenceQueue<>();

    public static void checkQueue() {
        Reference<? extends BigFile> reference = null;
        while ((reference = referenceQueue.poll()) != null) {
            if (reference != null) {
                System.out.println("In queue: " + ((BigFileWeakReference) (reference)).getVideo());
            }
        }
    }

    public static void main(String[] args) {
        int size = 3;
        LinkedList<WeakReference<BigFile>> weakList = new LinkedList<WeakReference<BigFile>>();
        for (int i = 0; i < size; i++) {
            weakList.add(new BigFileWeakReference(new BigFile("－－－－－Weak Video－－－－－" + i), referenceQueue));
            System.out.println("Just created weak: " + weakList.getLast());
        }
        System.gc();
        try { // 下面休息8秒钟，让上面的垃圾回收线程运行完成
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkQueue();
    }
}

class BigFile {
    // 占用空间，让线程进行回收
    byte[] b = new byte[2 * 1024];
    private String video;

    public BigFile(String video) {
        this.video = video;
    }

    public String getVideo() {
        return video;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalizing BigFile" + video);
    }
}

class BigFileWeakReference extends WeakReference<BigFile> {
    private String video;

    public BigFileWeakReference(BigFile bigFile, ReferenceQueue<BigFile> referenceQueue) {
        super(bigFile, referenceQueue);
        this.video = bigFile.getVideo();
    }

    public String getVideo() {
        return video;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalizing BigFileWeakReference" + video);
    }
}
