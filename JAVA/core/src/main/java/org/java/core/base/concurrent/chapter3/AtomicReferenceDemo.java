package org.java.core.base.concurrent.chapter3;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {

    public static void main(String[] args) {
        AtomicReference<AtomicClass> atomicReference = new AtomicReference<>();

        AtomicClass atomicClass = new AtomicClass();
        atomicClass.setV1(10);
        atomicClass.setV1(20);

        atomicReference.set(atomicClass);
    }

    /**
     * 利用AtomicReference类把多个共享变量合并成一个共享变量来操作
     */
    private static class AtomicClass {
        private int v1; // 共享变量1
        private int v2; // 共享变量2

        public int getV1() {
            return v1;
        }

        public void setV1(int v1) {
            this.v1 = v1;
        }

        public int getV2() {
            return v2;
        }

        public void setV2(int v2) {
            this.v2 = v2;
        }
    }
}
