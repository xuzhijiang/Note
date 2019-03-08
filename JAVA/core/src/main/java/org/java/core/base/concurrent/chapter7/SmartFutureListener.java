package org.java.core.base.concurrent.chapter7;

public interface SmartFutureListener<V> {
    public void onSuccess(V result);
    public void onError(Throwable throwable);
}