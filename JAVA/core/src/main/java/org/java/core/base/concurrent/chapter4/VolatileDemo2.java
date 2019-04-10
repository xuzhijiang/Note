package org.java.core.base.concurrent.chapter4;

public class VolatileDemo2 {

    volatile long vl = 0L;

    public void set(long l){
        this.vl = l;
    }

    public void getAndIncrement(){
        vl++;
    }

    public long get(){
        return vl;
    }

}
