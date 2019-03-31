package org.java.core.advanced.DesignPatterns.behavioral.observer;

/**
 * 主题接口
 */
public interface Subject {

    //观察者注册
    public void registerObserver(Observer o);

    //移除观察者
    public void removeObserver(Observer o);

    //当主题状态该变时，这个方法就会被调用，以通知所有的观察者
    public void notifyObserver();
}
