package org.java.core.base.concurrent.chapter7.CustomFutureTask;

interface SmartFutureListener<V> {
    void onSuccess(V result);
    void onError(Throwable throwable);
}