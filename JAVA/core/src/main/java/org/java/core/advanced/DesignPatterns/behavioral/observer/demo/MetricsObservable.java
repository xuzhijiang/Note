package org.java.core.advanced.DesignPatterns.behavioral.observer.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * 观察者设计模式
 *
 * 观察者设计模式主要的使用场景在于一个对象变化之后，依赖该对象的对象会收到通知。
 * 典型的例子就是rss的订阅，当订阅了博客的rss之后，当博客更新之后，订阅者就会收到新的订阅信息。
 *
 * jdk内置提供了Observable和Observer，用来实现观察者模式：
 */

// Metrics：指标
// 定义一个Observable
public class MetricsObservable extends Observable {

    private Map<String, Long> counterMap = new HashMap<>();

    public void updateCounter(String key, Long value) {
        counterMap.put(key, value);
        setChanged();
        notifyObservers(counterMap);
    }

}
