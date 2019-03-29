package org.java.core.advanced.DesignPatterns.behavioral.observer;

/**
 * 观察者接口
 */
public interface Observer {
    //当气象观测值改变时，主题就会将这些状态值当做方法的参数，传送给观察者
    //所有的的观察者都必须实现update方法，以实现观察者接口
    public void update(float temperature,float humidity,float pressure);
}